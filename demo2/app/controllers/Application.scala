package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  val jogadores = List(
    "Valdir Peres",
    "Leandro",
    "Oscar",
    "Falcão",
    "Luizinho",
    "Júnior",
    "Sócrates",
    "Toninho Cerezzo",
    "Serginho Chulapa",
    "Zico",
    "Éder")
  def index = Action {
    Ok(views.html.index(jogadores))
  }

}