package ch.comem.services;

import ch.comem.model.Category;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Pierre-Alexandre
 */
@Stateless
public class CategoriesManager implements CategoriesManagerLocal {
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;
    
    @Override
    public Long createCategory(String name) {
        Category c = new Category();
        c.setName(name);
        persist(c);
        em.flush();
        return c.getId();
    }

    public void persist(Object object) {
        em.persist(object);
    }
    
}
