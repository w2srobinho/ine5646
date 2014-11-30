package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index(hora: Int) = Action {
    var msg = ""
    if(hora >= 0 && hora < 12) msg = "manhã"
    else if(hora < 18) msg = "tarde"
    else if(hora < 24) msg = "noite"
    else msg = "hora inválida"

    Ok(views.html.index(hora, msg))
  }

}