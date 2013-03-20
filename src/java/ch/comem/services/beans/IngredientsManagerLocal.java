package ch.comem.services.beans;

import ch.comem.model.Ingredient;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Pierre-Alexandre
 */
@Local
public interface IngredientsManagerLocal {

    public Long createIngredient(String name, int quantity, String quantityUnit);
    
    public String modifyIngredient(Long ingredientId, String name, int quantity, 
                                     String quantityUnit);
    
    public String deleteIngredient(Long ingredientId);
    
    List<Ingredient> findAllIngredients();
}
