package controllers

import models.oauth2.GoogleOAuth2
import models.oauth2.Usuario
import models.Pesquisador
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.ws.WS
import play.api.mvc._
import play.api.Play.current
import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success
import scala.util.Try

object Application extends Controller 
{
  var usuarios = Map[String, Usuario]()
  
  def index = Action { implicit request =>
    val host = request.host
    print("\n\n"+host+"\n\n")
    Ok(views.html.index(GoogleOAuth2.loginURL, host)).withNewSession
  }
  
  def callback = Action.async { implicit request =>
    val optCode = request.getQueryString("code")
    optCode match {
    	case None => Future.successful(Redirect(routes.Application.index).withNewSession)
    	case Some(code) => {
    	  GoogleOAuth2.usuario(code).map(optUsuario => 
        {
    	    optUsuario match {
    	      case None => Redirect(routes.Application.index).withNewSession
    	      case Some(usuario) => {
    	        usuarios += usuario.chave -> usuario
    	        Ok(views.html.form(usuario)).withSession("chave" -> usuario.chave)
    	      }
    	    }
    	  })
    	}
    }
  }
  
  def processe = Action.async { implicit request =>
    val optChave = request.session.get("chave")

    if (optChave.isEmpty || !usuarios.contains(optChave.get))
      Future.successful(Redirect(routes.Application.index).withNewSession)
    else 
    {
      val cidade = request.body.asFormUrlEncoded.get("cidade").head
      val quantidade = request.body.asFormUrlEncoded.get("quantidade").head

      var msgErro = ""
      if (cidade.isEmpty)
        msgErro = "Cidade não preenchida"
      else if (quantidade.isEmpty)
        msgErro = "Número de dias não preenchido"

      var dias = 0
      print(msgErro)
      Try(quantidade.toInt) match {
        case Failure(_) => msgErro = s"$quantidade não é um número inteiro"
        case Success(d) => dias = d
      }
      
      if(!msgErro.isEmpty)
        Future.successful(Ok(views.html.form(usuarios(optChave.get), None, Some(cidade), Some(quantidade), Some(msgErro))))
      else
        Pesquisador.pesquise(cidade, dias).map(previsao => Ok(views.html.form(usuarios(optChave.get), Some(previsao))))
    }
  }
  
  def sair = Action.async { request =>
    val optChave = request.session.get("chave")
    if (optChave.isEmpty || !usuarios.contains(optChave.get))
      Future.successful(Redirect(routes.Application.index).withNewSession)
    else 
    {
      val chave = optChave.get
      val usuario = usuarios(chave)
      val at = usuario.at
      val url = s"https://accounts.google.com/o/oauth2/revoke?token=$at"
      usuarios -= chave
      WS.url(url).get.map (_ => Redirect(routes.Application.index).withNewSession)
    }
  }

}