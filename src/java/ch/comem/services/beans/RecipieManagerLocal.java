/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.services.beans;

import ch.comem.model.Ingredient;
import ch.comem.model.Recipie;
import ch.comem.model.Step;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author raphaelbaumann
 */
@Local
public interface RecipieManagerLocal {

    Long createRecipie(String name, List<Long> ingredientIds, 
                        List<Long> stepIds);

    String deleteRecipie(Long recipieId);

    String modifyRecipie(Long recipieId, String name, 
                          List<Long> ingredientIds, 
                          List<Long> stepIds);
    
    List<Recipie> findAllRecipies();

    List<Ingredient> findAllIngredientsFromRecipieName(String name);

    List<Step> findAllStepsFromRecipieName(String name);
    
}
