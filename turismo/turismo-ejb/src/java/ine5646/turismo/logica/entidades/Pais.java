package ine5646.turismo.logica.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Leandro
 */
@Entity
public class Pais implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  private String id;
  String nome;
  @OneToMany(mappedBy = "pais")
  List<Cidade> cidades;

  public Pais() {
  }

  public Pais(String id, String nome) {
    this.id = id;
    this.nome = nome;
  }

  public List<Cidade> getCidades() {
    return cidades;
  }

  public void setCidades(List<Cidade> cidades) {
    this.cidades = cidades;
  }

  
  
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
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
    if (!(object instanceof Pais)) {
      return false;
    }
    Pais other = (Pais) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ine5646.turismo.entidades.Pais[ id=" + id + " ]";
  }

  public void adicioneCidade(Cidade c) {
    cidades.add(c);
    c.setPais(this);
  }
  
}
