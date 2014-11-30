package models

import java.net.URLEncoder
import play.api.libs.ws.WS
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._

object Pesquisador {

  def pesquise(cidade: String, dias: Int) = {
    import play.api.Play.current // necessário por causa do WS
      val days = dias + 1
      val city = URLEncoder.encode(cidade, "UTF-8")
      val appid = URLEncoder.encode("40a574e1e3e14ddf2758a546b4245308", "UTF-8")
      val url = s"http://api.openweathermap.org/data/2.5/forecast/daily/?q=$city&lang=pt&units=metric&cnt=$days&APPID=$appid"

      WS.url(url).get.map { resposta =>
        val respostaJson = resposta.json

        if(days > 11 || days <= 1){
          Resposta(Some(cidade), None, None, None, None, None, None, None, Some(1))
        }
        else if((respostaJson \ "cod").as[String] != "200"){
          Resposta(Some(cidade), None, None, None, None, None, None, None, Some(2))
        }
        else{

          val cidadeWeather = (respostaJson \ "city" \ "name").as[String]
          val paisWeather = (respostaJson \ "city" \ "country").as[String]
          var datas = new Array[String](dias)
          var min = new Array[Float](dias)
          var max = new Array[Float](dias)
          var icone = new Array[String](dias)
          var descricao = new Array[String](dias)
          var humidade = new Array[Float](dias)

          val elements = (respostaJson \ "list").asInstanceOf[JsArray].value.tail

          for(i <- 0 until dias){
            var weather = (elements(i) \ "weather").asInstanceOf[JsArray](0)

            datas(i) = (elements(i) \ "dt").toString()
            humidade(i) = (elements(i) \ "humidity").as[Float]

            icone(i) = (weather \ "icon").as[String]
            descricao(i) = (weather \ "description").as[String]
            
            min(i) = (elements(i) \ "temp" \ "min").as[Float]
            max(i) = (elements(i) \ "temp" \ "max").as[Float]

          }
          
          Resposta(Some(cidadeWeather), Some(paisWeather), Some(datas), Some(min), Some(max), Some(icone), Some(descricao), Some(humidade), None)
        }
      }
  }
}