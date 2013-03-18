/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.services;

import ch.comem.model.Ingredient;
import java.util.Collection;
import java.util.Set;
import javax.ejb.Local;

/**
 *
 * @author raphaelbaumann
 */
@Local
public interface RecipieManagerLocal {

    Long createRecipie(Set<String> steps, Collection<Long> ingredientIds);

    String deleteRecipie(String recipieId);

    String modifyRecipie(String recipieId, Set<String> steps, Collection<Long> ingredientIds);
    
}
