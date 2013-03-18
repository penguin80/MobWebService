/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.tests;

import javax.ejb.Local;

/**
 *
 * @author Pierre-Alexandre
 */
@Local
public interface DataManagerTestLocal {

    void testMethods();

    void testRelationRecipieIngredients(long recipieId, String ingredientId);
    
}
