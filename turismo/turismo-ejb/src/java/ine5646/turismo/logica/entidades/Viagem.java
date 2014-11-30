package ine5646.turismo.logica.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author Leandro
 */
@Entity
public class Viagem implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  Integer numDias;
  Integer preco;
  @ManyToOne
  Cidade cidade;
  @ManyToMany
  List<Passageiro> passageiros;
  

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setCidade(Cidade cidade) {
    this.cidade = cidade;
  }

  public Cidade getCidade() {
    return cidade;
  }

  public void setNumDias(Integer numDias) {
    this.numDias = numDias;
  }

  public Integer getNumDias() {
    return numDias;
  }

  public void setPassageiros(List<Passageiro> passageiros) {
    this.passageiros = passageiros;
  }

  public List<Passageiro> getPassageiros() {
    return passageiros;
  }

  public void setPreco(Integer preco) {
    this.preco = preco;
  }

  public Integer getPreco() {
    return preco;
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
    if (!(object instanceof Viagem)) {
      return false;
    }
    Viagem other = (Viagem) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ine5646.turismo.entidades.Viagem[ id=" + id + " ]";
  }

  public void adicionePassageiro(Passageiro p) {
    passageiros.add(p);
    List<Viagem> viagens = p.getViagens();
    viagens.add(this);
    p.setViagens(viagens);
  }
  
}
