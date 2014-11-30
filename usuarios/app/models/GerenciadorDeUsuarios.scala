package models

object GerenciadorDeUsuarios {

  def facaLogin(login: String, senha: String): Either[String, (Usuario, Option[List[Usuario]])] = {
    val optUsuario = Banco.leia(login)
    optUsuario match {
      case None => Left(s"Não existe usuário com login $login")
      case Some(usuario) => {
        if (!usuario.senha.equals(senha)) Left("Senha incorreta") else {
          val optUsuarios = if (usuario.admin) Some(Banco.leiaUsuarios) else None
          Right((usuario, optUsuarios))
        }
      }
    }
  }
  
  
  def registreUsuario(nome: String, login: String, senha: String): Either[String, Usuario] = {
    val novoUsuario = Usuario(login, senha, false, nome)
    Banco.cadastre(novoUsuario) match {
      case true => Right(novoUsuario)
      case false => Left(s"Ops! Já existe usuário cadastrado com login $login")
    }    
  }
  
  def altereUsuario(nome: String, login: String, senha: String): Either[String, Usuario] = {
    Banco.atualize(login, nome, senha) match {
      case None => Left("Usuário foi removido pelo administrador")
      case Some(usuario) => Right(usuario)
    }
  }
  
  def apagueUsuarios {Banco.apagueNaoAdministradores}
}