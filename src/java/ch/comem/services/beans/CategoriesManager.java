package ch.comem.services.beans;

import ch.comem.model.Category;
import java.util.List;
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

    @Override
    public List<Category> findAllCategories() {
        return em.createNamedQuery("findAllCategories").getResultList();
    }
    
    public void persist(Object object) {
        em.persist(object);
    }
    
}
