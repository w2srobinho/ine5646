package models

import play.api.libs.json._

case class Resposta (city: Option[String], country: Option[String], datas: Option[Array[String]], 
                      min: Option[Array[Float]], max: Option[Array[Float]], icone: Option[Array[String]],
                      descricao: Option[Array[String]], humidade: Option[Array[Float]], error: Option[Int]) {
  
  val cidade = city
  val pais = country

  val dt: Array[String] = datas match{
              case None => new Array(1)
              case Some(d) => d
            }
   val minimo: Array[Float] = min match{
              case None => new Array(1)
              case Some(m) => m
            }
  val maximo: Array[Float] = max match{
              case None => new Array(1)
              case Some(m) => m
            }
  val icones: Array[String] = icone match{
              case None => new Array(1)
              case Some(i) => i
            }
  val descricoes: Array[String] = descricao match{
              case None => new Array(1)
              case Some(d) => d
            }
  val humidades: Array[Float] = humidade match{
              case None => new Array(1)
              case Some(h) => h
            }
  val erro = error match{
              case None => None
              case Some(e) => e
            }
  
 def montarUrlIcone(icon: String): String = {
    val url_icon = s"http://openweathermap.org/img/w/$icon.png"
    return url_icon
 }

 def formatarData(data: String): String = {
    val sdf = new java.text.SimpleDateFormat("dd/MM/yyyy") 
    val dia: String = sdf.format(new java.util.Date(1000L * data.toLong))
    return dia
  }
}