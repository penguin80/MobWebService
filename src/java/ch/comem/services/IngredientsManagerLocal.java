package ch.comem.services;

import javax.ejb.Local;

/**
 *
 * @author Pierre-Alexandre
 */
@Local
public interface IngredientsManagerLocal {

    public Long createIngredient(String name, int quantity, String quantityUnit);
    
    public Long createIngredient(String name, int quantity, String quantityUnit, 
                                   Long recipieId);
    
    public String modifyIngredient(Long ingredientId, String name, int quantity, 
                                     String quantityUnit);
    
    public String modifyIngredient(Long ingredientId, String name, int quantity, 
                                     String quantityUnit, Long recipieId);
    
    public String deleteIngredient(Long ingredientId);
}
