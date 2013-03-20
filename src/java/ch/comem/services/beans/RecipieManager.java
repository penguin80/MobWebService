/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.services.beans;

import ch.comem.model.Ingredient;
import ch.comem.model.Recipie;
import ch.comem.model.Step;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    
    private void setRecipieParameters(Recipie r, String name, 
                                        Collection<Long> ingredientIds, 
                                        Collection<Long> stepIds) {
        r.setName(name);
        List<Ingredient> ingredients = new ArrayList<>();
        for(Long id : ingredientIds) {
            Ingredient ing = em.find(Ingredient.class, id);
            ingredients.add(ing);
        }
        r.setIngredients(ingredients);
        List<Step> steps = new ArrayList<>();
        for (Long id : stepIds) {
            Step s = em.find(Step.class, id);
            steps.add(s);
        }
        r.setSteps(steps);
    }

    @Override
    public Long createRecipie(String name, Collection<Long> ingredientIds, 
                               Collection<Long> stepIds) {
        Recipie recipie = new Recipie();
        setRecipieParameters(recipie, name, ingredientIds, stepIds);
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
    public String modifyRecipie(Long recipieId, String name, 
                                 Collection<Long> ingredientIds, 
                                 Collection<Long> stepIds) {
                    String str = "";
        Recipie r  = em.find(Recipie.class, recipieId);
        if (r != null) {
            setRecipieParameters(r, name, ingredientIds, stepIds);
            persist(r);
            em.flush();
            str = str.concat("Recette modifiée!");
        } else {
            str = str.concat("Impossible de modifier la recette demandée!");
        }
        return str;
    }

    @Override
    public List<Recipie> findAllRecipies() {

        return em.createNamedQuery("findAllRecipies").getResultList();
    }

    public void persist(Object object) {
        em.persist(object);
    }    

}
