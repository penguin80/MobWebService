package ch.comem.services.beans;

import ch.comem.model.Category;
import ch.comem.model.Publication;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Pierre-Alexandre
 */
@Local
public interface CategoriesManagerLocal {

    Long createCategory(String name);
    
    List<Category> findAllCategories();

    List<Publication> findAllPublicationsFromCategoryName(String name);
    
}
