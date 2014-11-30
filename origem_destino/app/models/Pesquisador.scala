package models

import java.net.URLEncoder
import play.api.libs.ws.WS
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.JsArray

object Pesquisador {

  def pesquise(origem: String, destino: String) = {
    import play.api.Play.current // necessário por causa do WS
    
    val origins = URLEncoder.encode(origem, "UTF-8")
    val destinations = URLEncoder.encode(destino, "UTF-8")
    val url = s"http://maps.googleapis.com/maps/api/distancematrix/json?origins=$origins&destinations=$destinations&sensor=false"

    WS.url(url).get.map { resposta =>
      val respostaJson = resposta.json

      val arrayDestination = (respostaJson \ "destination_addresses").asInstanceOf[JsArray]
      val arrayOrigin = (respostaJson \ "origin_addresses").asInstanceOf[JsArray]
      val elements = ((respostaJson \ "rows").asInstanceOf[JsArray](0) \ "elements").asInstanceOf[JsArray](0)
      val semCaminho = !(elements \ "status").as[String].equals("OK")
      if (arrayDestination.value.isEmpty || arrayOrigin.value.isEmpty || semCaminho)
        Resposta(origem, destino, None, None)
      else {
        val destinoGoogle = arrayDestination(0).as[String]
        val origemGoogle = arrayOrigin(0).as[String]
        val distancia = (elements \ "distance" \ "value").as[Long]
        val tempo = (elements \ "duration" \ "value").as[Long]
        Resposta(origemGoogle, destinoGoogle, Some(distancia), Some(tempo))
      }
    }
  }
}