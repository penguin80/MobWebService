/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.services.beans;

import ch.comem.model.Recipie;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author raphaelbaumann
 */
@Local
public interface RecipieManagerLocal {

    Long createRecipie(String name, Collection<Long> ingredientIds, 
                        Collection<Long> stepIds);

    String deleteRecipie(Long recipieId);

    String modifyRecipie(Long recipieId, String name, 
                          Collection<Long> ingredientIds, 
                          Collection<Long> stepIds);
    
    List<Recipie> findAllRecipies();
    
}
