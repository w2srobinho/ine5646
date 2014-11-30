package ine5646.turismo.logica.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author Leandro
 */
@Entity
public class Passageiro implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  private Long id;
  String nome;
  @ManyToMany(mappedBy = "passageiros")
  List<Viagem> viagens;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getNome() {
    return nome;
  }

  public void setViagens(List<Viagem> viagens) {
    this.viagens = viagens;
  }

  public List<Viagem> getViagens() {
    return viagens;
  }

  
  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Passageiro)) {
      return false;
    }
    Passageiro other = (Passageiro) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ine5646.turismo.entidades.Passageiro[ id=" + id + " ]";
  }
  
}
