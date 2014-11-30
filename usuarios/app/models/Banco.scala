package models

/*
 * Observação: uso do método synchronized faz com que apenas uma thread
 * acesse o objeto usuarios de cada vez
 * 
 */

object Banco {
  // login -> Usuario
  protected var usuarios: Map[String, Usuario] = Map()

  def leia(login: String) =
    usuarios.synchronized {
      if (usuarios.contains(login)) Some(usuarios(login)) else None
    }

  def cadastre(usuario: Usuario): Boolean = {
    usuarios.synchronized {
      if (usuarios.contains(usuario.login)) false else {
        usuarios += (usuario.login -> usuario)
        true
      }
    }
  }

  def atualize(login: String, nome: String, senha: String): Option[Usuario] = {
    usuarios.synchronized {
      if (!usuarios.contains(login)) None else {
        val usuario = Usuario(login, senha, false, nome)
        usuarios += (login -> usuario)
        Some(usuario)
      }
    }
  }

  def apagueNaoAdministradores: Unit = {
    usuarios.synchronized {
      val admin = usuarios.filter({ case (_, usuario) => usuario.admin })
      usuarios = admin
    }
  }

  def leiaUsuarios = usuarios.synchronized(usuarios.values.toList.filter(u => !u.admin))

}