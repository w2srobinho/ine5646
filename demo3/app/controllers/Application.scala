package controllers

import models.Corpo
import play.api.mvc._

object Application extends Controller {

  val corpos = List(Corpo("A", 100.3f, 12), Corpo("B", 200.4f, 23))

  def index = Action {
    Ok(views.html.index(corpos))
  }

}