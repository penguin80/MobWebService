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
        Collection<String> categoryNames = c.getCategoryNames();
        if (categoryNames.contains(name))
            c.setCurrentCategoryNameSelected(name);
        else
            c.setCurrentCategoryNameSelected("Pas encore catégorisée");
        persist(c);
        em.flush();
        return c.getId();
    }

    public void persist(Object object) {
        em.persist(object);
    }
    
}
