package ch.comem.services;

import javax.ejb.Local;

/**
 *
 * @author Pierre-Alexandre
 */
@Local
public interface IngredientsManagerLocal {

    public long createIngredient(String name, int quantity, String quantityUnit);
    
    public long createIngredient(String name, int quantity, String quantityUnit, 
                                   long recipieId);
    
    public String modifyIngredient(int ingredientId, String name, int quantity, 
                                     String quantityUnit);
    
    public String modifyIngredient(int ingredientId, String name, int quantity, 
                                     String quantityUnit, long recipieId);
    
    public String deleteIngredient(int ingredientId);
}
