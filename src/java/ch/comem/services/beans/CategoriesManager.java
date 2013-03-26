package ch.comem.services.beans;

import ch.comem.model.Category;
import ch.comem.model.Publication;
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
        List<String> allNamesAvailable = findAllCategoryNames();
        Category c = new Category();
        Long id = null;
        if (allNamesAvailable == null || allNamesAvailable.isEmpty()) {
            c.setName(name);
            persist(c);
            em.flush();
            id = c.getId();
        } else {
            if (!allNamesAvailable.contains(name)) {
                c.setName(name);
                persist(c);
                em.flush();
                id = c.getId();
            }
        }
        return id;
    }    
    
    @Override
    public Long useCategory(String name) {
        List<String> allNamesAvailable = findAllCategoryNames();
        Long id = null;
        if (allNamesAvailable.contains(name)) {
            List<Category> cList = findAllCategories();
            for (Category c : cList) {
                if (id == null) {
                    if (c.getName().equals(name))
                        id = c.getId();
                }
            }
        }
        return id;
    }

    @Override
    public List<String> findAllCategoryNames() {
        return em.createNamedQuery("findAllCategoryNames").getResultList();
    }

    @Override
    public List<Category> findAllCategories() {
        return em.createNamedQuery("findAllCategories").getResultList();
    }
    
    @Override
    public List<Publication> findAllPublicationsFromCategoryName(String name) {
        return em.createNamedQuery("findAllPublicationsFromCategoryName").setParameter("name", name).getResultList();
    }

    public void persist(Object object) {
        em.persist(object);
    }
    
}
