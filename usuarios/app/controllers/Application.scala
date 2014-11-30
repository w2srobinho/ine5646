package controllers

import play.api.mvc._
import models.Banco
import models.Usuario
import play.api.data._
import play.api.data.Forms._
import models.GerenciadorDeUsuarios

object Application extends Controller {

  Banco.cadastre(Usuario("a", "a", true, "Administrador do Sistema"))

  def index = Action {
    Ok(views.html.index()).withNewSession
  }

  def facaLogin = Action { implicit request =>
    val form = Form(tuple("login" -> text, "senha" -> text))
    val (login, senha) = form.bindFromRequest.get
    
    GerenciadorDeUsuarios.facaLogin(login, senha) match {
      case Left(msg) => Ok(views.html.index(Some(msg)))
      case Right((usuario, optUsuarios)) => {
        val pagina = views.html.logado(usuario, optUsuarios) 
        Ok(pagina).withSession("logado" -> login)
      }
    }
  }

  def registreUsuario = Action { implicit request =>
    val form = Form(tuple("nome" -> text, "login" -> text, "senha" -> text))
    val (nome, login, senha) = form.bindFromRequest.get

    GerenciadorDeUsuarios.registreUsuario(nome, login, senha) match {
      case Left(msg) => Ok(views.html.index(Some(msg)))
      case Right(novoUsuario) => {
        val pagina = views.html.logado(novoUsuario) 
        Ok(pagina).withSession("logado" -> login)
      }
    }
  }

  private def extraiaUsuario(request: Request[AnyContent]): Option[Usuario] = {
    request.session.get("logado").flatMap(login => Banco.leia(login))
  }

  private def ActionAutenticado(f: (Request[AnyContent], Usuario) => Result): Action[AnyContent] =
    Action { request =>
      extraiaUsuario(request) match {
        case None => Ok(views.html.index(Some("Usuário não autenticado ou removido pelo administrador!")))
        case Some(usuarioLogado) => f(request, usuarioLogado)
      }
    }

  def altereUsuario = ActionAutenticado { (request, usuarioLogado) =>
    val form = Form(tuple("nome" -> text, "senha" -> text))
    val (nome, senha) = form.bindFromRequest()(request).get
    
    GerenciadorDeUsuarios.altereUsuario(nome, usuarioLogado.login, senha) match {
      case Left(msg) => Ok(views.html.index(Some(msg))).withNewSession
      case Right(usuarioAlterado) => Ok(views.html.logado(usuarioAlterado))
    }
  }

  def apagueTudo = ActionAutenticado { (_, usuarioLogado) =>
    GerenciadorDeUsuarios.apagueUsuarios
    Ok(views.html.logado(usuarioLogado, Some(List())))
  }

  def encerreSessao = ActionAutenticado { (_,_) =>
    Redirect(routes.Application.index).withNewSession
  }
}