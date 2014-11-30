package models

case class Previsao (
  cidade: String,
  optPais: Option[String],
  imgs: Option[Array[String]],
  datas: Option[Array[Long]],
  descricoes: Option[Array[String]],
  minimas: Option[Array[Double]],
  maximas: Option[Array[Double]],
  humidades: Option[Array[Double]],
  erro: Option[String]) {

  val optDatas: Array[Long] = datas match{
    case None => new Array(1)
    case Some(d) => d
  }
  val optImgs: Array[String] = imgs match{
    case None => new Array(1)
    case Some(i) => i
  }
  val optDescricoes: Array[String] = descricoes match{
    case None => new Array(1)
    case Some(d) => d
  }
  val optMinimas: Array[Double] = minimas match{
    case None => new Array(1)
    case Some(m) => m
  }
  val optMaximas: Array[Double] = maximas match{
    case None => new Array(1)
    case Some(m) => m
  }
  val optHumidades: Array[Double] = humidades match{
    case None => new Array(1)
    case Some(h) => h
  }
  val optErro = erro match{
    case None => None
    case Some(e) => e
  }

  def optDataFormatoPadrao(epoch: Long): String = {
    val sdf = new java.text.SimpleDateFormat("dd/MM/yyyy")
    val dia: String = sdf.format(new java.util.Date(1000L * epoch))
    return dia
  }
}
