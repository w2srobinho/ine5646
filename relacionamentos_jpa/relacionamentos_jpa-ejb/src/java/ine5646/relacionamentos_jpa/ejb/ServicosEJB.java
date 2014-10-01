package ine5646.relacionamentos_jpa.ejb;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Leandro
 */
@Stateless
@LocalBean
public class ServicosEJB {
    @PersistenceContext
    private EntityManager em;

}
