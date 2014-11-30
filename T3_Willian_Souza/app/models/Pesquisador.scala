package models

import java.net.URLEncoder
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.JsArray
import play.api.libs.ws.WS
import play.api.Play.current // necessÃ¡rio por causa do WS
import scala.util.{Try, Failure, Success}

object Pesquisador 
{
       
  def pesquise(cidade: String, quantidade: Int) = {    
    val APPID = "2c803c8ef97d4c10bfa4191a5a5ffdcd"
    val city = URLEncoder.encode(cidade, "UTF-8")
    val diasMaisUm = quantidade + 1
    val url = s"http://api.openweathermap.org/data/2.5/forecast/daily/?q=$city&lang=pt&units=metric&cnt=$diasMaisUm&APPID=$APPID"

    WS.url(url).get.map { previsao =>
      val jsonResp = previsao.json
      val cod: Int = (jsonResp \ "cod").as[String].toInt
      
      if(quantidade < 1 || quantidade > 10) 
        Previsao(city, None, None, None, None, None, None, None, Some("Número de dias deve ser entre 1 e 10"))
      else if(cod != 200) 
        Previsao(city, None, None, None, None, None, None, None, Some("Não foi possível identificar a cidade"))
      else 
      {
        val cidadeEncontrada: String = (jsonResp \ "city" \ "name").as[String]
        val paisEncontrado: String = (jsonResp \ "city" \ "country").as[String]

     		val lista = (jsonResp \ "list").asInstanceOf[JsArray].value.tail

     		var imgs = new Array[String](quantidade)
        var datas = new Array[Long](quantidade)
        var descricoes = new Array[String](quantidade)
        var minimas = new Array[Double](quantidade)
        var maximas = new Array[Double](quantidade)
        var humidades = new Array[Double](quantidade)

        for (i <- 0 to (quantidade - 1)) 
        {
          val weather = (lista(i) \ "weather").asInstanceOf[JsArray](0)
        
          val icon = (weather \ "icon").as[String]
          imgs(i) = s"http://openweathermap.org/img/w/$icon.png"
          datas(i) = (lista(i) \ "dt").as[Long]
          descricoes(i) = (weather \ "description").as[String]
          minimas(i) = (lista(i) \ "temp" \ "min").as[Double]
          maximas(i) = (lista(i) \ "temp" \ "max").as[Double]
          humidades(i) = (lista(i) \ "humidity").as[Double]
        }
      
        Previsao(cidadeEncontrada, Some(paisEncontrado), Some(imgs), Some(datas), Some(descricoes), Some(minimas), Some(maximas), Some(humidades), None)
      }
    }
  }
}