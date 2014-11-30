package models.oauth2

import play.api.libs.ws.WS
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.libs.json.JsObject
import scala.concurrent.Future


object GoogleOAuth2 extends OAuth2 {

  // definido em:
  // https://developers.google.com/
  // https://console.developers.google.com/project
  //

  override val clientID = "233312176650-mr9grnekikdkhgjb5t2tusen601u988l.apps.googleusercontent.com"
  override val clientSecret = "4kiUrtZlWNWufi9uCvLt-19a"
  override val callbackURL = "http://localhost:9000/callback"
  override val loginURL = s"https://accounts.google.com/o/oauth2/auth?client_id=$clientID&response_type=code&scope=openid email&redirect_uri=$callbackURL"
  

  def usuario(code: String): Future[Option[Usuario]] = {
    for (
      optAccessToken <- obtenhaAccessToken(code);
      optDados <- obtenhaDadosDoUsuario(optAccessToken);
      optUsuario <- obtenhaUsuario(optDados)
    ) yield optUsuario
  }

  private def obtenhaUsuario(optDados: Option[JsObject]) = {
    optDados match {
      case None => Future.successful(None)
      case Some(dados) => {
    
        val nome = (dados \ "nome").as[String]
        val at = (dados \ "at").as[String]
        val pic = (dados \ "pic").as[String]
        val email = (dados \ "email").as[String]
        val chave = email   // poderia ser uma informação codificada
        Future.successful(Some(Usuario(chave, at, nome, pic, email)))
      }
    }
}

  private def obtenhaDadosDoUsuario(optAccessToken: Option[String]): Future[Option[JsObject]] = {
    optAccessToken match {
      case None => Future.successful(None)
      case Some(accessToken) => {
        import play.api.Play.current // necessário por causa do WS

        val url = s"https://www.googleapis.com/oauth2/v2/userinfo?access_token=$accessToken"
        WS.url(url).get.map(resposta => {
          val respJson = resposta.json
          Some(Json.obj("at" -> accessToken, "nome" -> respJson \ "name", "pic" -> respJson \ "picture", "email" -> respJson \ "email"))
        })

      }
    }
  }

  private def obtenhaAccessToken(code: String): Future[Option[String]] = {
    import play.api.Play.current
    val params = Map("code" -> Seq(code),
      "client_id" -> Seq(clientID),
      "client_secret" -> Seq(clientSecret),
      "redirect_uri" -> Seq(callbackURL),
      "grant_type" -> Seq("authorization_code"))

    WS.url("https://accounts.google.com/o/oauth2/token").post(params).map(resposta => (resposta.json \ "access_token").asOpt[String])
  }
}