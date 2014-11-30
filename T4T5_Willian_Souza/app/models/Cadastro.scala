package models

import akka.actor.{Actor, Props}

/**
 * Created by robinho on 30/11/14.
 */
object Cadastro {
  // facilita a instanciação do ator
  def props(qtdMaximaPessoas: Int) = Props(classOf[Cadastro], qtdMaximaPessoas)

  // msgs recebidas

  case class LeiaPessoa(cpf: Int)
  case object ApagarTudo
  case object InformePessoas



  // msgs enviadas
  trait RespostaCadastro

  case class PessoasCadastradas(pessoas: List[Pessoa]) extends RespostaCadastro

  case class PessoaLida(pessoa: Pessoa) extends RespostaCadastro

  case class PessoaNaoCadastrada(cpf: Int) extends RespostaCadastro
  case class PessoasRemovidas(qtd: Int) extends RespostaCadastro
}

class Cadastro(qtdMaximaPessoas: Int) extends Actor {
  import Cadastro._

  // cpf da pessoa -> pessoa
  var pessoas = Map[Int, Pessoa]()


  def receive = {


    case ApagarTudo => {
      sender ! PessoasRemovidas(pessoas.size)
      pessoas = Map()
    }

    case LeiaPessoa(cpf) => {
      if (pessoas.contains(cpf))
        sender ! PessoaLida(pessoas(cpf))
      else
        sender ! PessoaNaoCadastrada(cpf)
    }


    case InformePessoas => sender ! PessoasCadastradas(pessoas.values.toList)

  }
}