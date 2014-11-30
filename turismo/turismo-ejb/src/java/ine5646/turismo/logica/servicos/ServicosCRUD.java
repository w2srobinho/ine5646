package ine5646.turismo.logica.servicos;

import ine5646.turismo.logica.entidades.Cidade;
import ine5646.turismo.logica.entidades.Pais;
import ine5646.turismo.logica.entidades.Passageiro;
import ine5646.turismo.logica.entidades.Viagem;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Leandro
 */
@Stateless
@LocalBean
public class ServicosCRUD {

    @PersistenceContext
    EntityManager em;

    public Pais cadastrePais(Pais p) {
        em.persist(p);
        return p;
    }

    public Cidade cadastreCidade(Cidade c) {
        em.persist(c);
        em.merge(c.getPais());
        return c;
    }

    public Viagem cadastreViagem(Viagem v) {
        em.persist(v);
        return v;
    }

    public Pais encontrePaisPorCodigo(String codigo) {
        return em.find(Pais.class, codigo);
    }

    public Cidade encontreCidadePorNome(String nome) {
        final String QUERY = "select c from Cidade c where c.nome = :nome";

        try {
            return (Cidade) em.createQuery(QUERY).setParameter("nome", nome).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public Passageiro cadastrePassageiro(Passageiro p) {
        em.persist(p);
        return p;
    }

    public void agendeViagem(Long idPassageiro, Long idViagem) {
        Passageiro p = em.find(Passageiro.class, idPassageiro);
        Viagem v = em.find(Viagem.class, idViagem);
        v.adicionePassageiro(p);
    }

    public List<Pais> encontreTodosPaises() {
        final String QUERY = "select p from Pais p order by p.nome";
        return em.createQuery(QUERY, Pais.class).getResultList();
    }

    public List<Cidade> encontreTodasCidades() {
        final String QUERY = "select c from Cidade c order by c.pais.nome";
        return em.createQuery(QUERY, Cidade.class).getResultList();
    }

    public List<Viagem> encontreTodasViagens() {
        final String QUERY = "select v from Viagem v order by v.id";
        return em.createQuery(QUERY, Viagem.class).getResultList();
    }

    public List<Passageiro> encontreTodosPassageiros() {
        final String QUERY = "select p from Passageiro p order by p.id";
        return em.createQuery(QUERY, Passageiro.class).getResultList();
    }

    public void apagueDados() {
        final String Q1 = "delete from Passageiro";
        final String Q2 = "delete from Viagem";
        final String Q3 = "delete from Cidade";
        final String Q4 = "delete from Pais";

        em.createQuery(Q1).executeUpdate();
        em.createQuery(Q2).executeUpdate();
        em.createQuery(Q3).executeUpdate();
        em.createQuery(Q4).executeUpdate();
    }
}
