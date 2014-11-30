package models

/**
 * Created by robinho on 10/10/14.
 */
case class Corpo(nome: String, distancia: Float, tempo: Int) {
  def velocidadeMedia = distancia / tempo
}
