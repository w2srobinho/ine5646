package controllers

//import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import scala.concurrent.Future
import play.api.libs.ws.WS
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import java.net.URLEncoder
import models.Resposta
import models.oauth2.Usuario
import play.api.libs.json.JsArray
import models.oauth2.GoogleOAuth2
import models.Pesquisador

object Application extends Controller {

  // Usuario.chave -> Usuario
  var usuarios = Map[String, Usuario]()

  def index = Action {
    Ok(views.html.index(GoogleOAuth2.loginURL)).withNewSession
  }

  def callback = Action.async { implicit request =>
    val optCode = request.getQueryString("code")
    optCode match {
      case None => Future.successful(Redirect(routes.Application.index).withNewSession)
      case Some(code) => {
        GoogleOAuth2.usuario(code).map(optUsuario => {
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
    else {
      val origem = request.body.asFormUrlEncoded.get("origem").head
      val destino = request.body.asFormUrlEncoded.get("destino").head
      Pesquisador.pesquise(origem, destino).map(resposta => Ok(views.html.form(usuarios(optChave.get), Some(resposta))))
    }
  }

  def sair = Action.async { request =>
    val optChave = request.session.get("chave")
    if (optChave.isEmpty || !usuarios.contains(optChave.get))
      Future.successful(Redirect(routes.Application.index).withNewSession)
    else {
      import play.api.Play.current // necessário por causa do WS
      val chave = optChave.get
      val usuario = usuarios(chave)
      val at = usuario.at
      val url = s"https://accounts.google.com/o/oauth2/revoke?token=$at"
      usuarios -= chave
      WS.url(url).get.map (_ => Redirect(routes.Application.index).withNewSession)
    }
  }

}