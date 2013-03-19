/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.services;

import ch.comem.model.Ingredient;
import ch.comem.model.Recipie;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author raphaelbaumann
 */
@Stateless
public class RecipieManager implements RecipieManagerLocal {
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;

    @Override
    public Long createRecipie(Set<String> steps, Collection<Long> ingredientIds) {
        Recipie recipie = new Recipie();
        for(Long id : ingredientIds){
            Ingredient ing = em.find(Ingredient.class, id);
            recipie.addIngredient(ing);
        }
        recipie.setSteps(steps);
        persist(recipie);
        em.flush();
        return recipie.getId();
    }

    @Override
    public String deleteRecipie(Long recipieId) {
                String str = "";
        Recipie m = em.find(Recipie.class, recipieId);
        if (m != null) {
            em.remove(m);
            str = str.concat("Recette supprimée!");
        } else {
            str = str.concat("Impossible de supprimer la recette demandée!");
        }
        return str;
    }
    
    @Override
    public String modifyRecipie(Long recipieId, Set<String> steps, Collection<Long> ingredientIds) {
                    String str = "";
        Recipie r  = em.find(Recipie.class, recipieId);
        if (r != null) {
            r.getIngredients().clear();
            for(Long id : ingredientIds){
                Ingredient ing = em.find(Ingredient.class, id);
                r.addIngredient(ing);
            }
            r.setSteps(steps);
            persist(r);
            em.flush();
            str = str.concat("Recette modifiée!");
        } else {
            str = str.concat("Impossible de modifier la recette demandée!");
        }
        return str;
    }

    public void persist(Object object) {
        em.persist(object);
    }
    
    

}
