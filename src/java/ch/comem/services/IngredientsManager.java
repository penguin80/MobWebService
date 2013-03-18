package ch.comem.services;

import ch.comem.model.Ingredient;
import ch.comem.model.Recipie;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Pierre-Alexandre
 */
@Stateless
public class IngredientsManager implements IngredientsManagerLocal {
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;

    private void setIngredientParameters(Ingredient i, String name, 
                                            int quantity, String quantityUnit) {
        i.setName(name);
        i.setQuantity(quantity);
        i.setQuantityUnit(quantityUnit);
    }
    
    @Override
    public Long createIngredient(String name, int quantity, String quantityUnit) {
        Ingredient i = new Ingredient();
        setIngredientParameters(i, name, quantity, quantityUnit);
        persist(i);
        em.flush();
        return i.getId();
    }

    @Override
    public Long createIngredient(String name, int quantity, String quantityUnit, Long recipieId) {
        Ingredient i = new Ingredient();
        setIngredientParameters(i, name, quantity, quantityUnit);
        Recipie r = em.find(Recipie.class, recipieId);
        if (r != null) {
            i.setRecipieInvolved(r);
            persist(i);
            em.flush();
        }
        return i.getId();
    }

    @Override
    public String modifyIngredient(Long ingredientId, String name, int quantity, 
                                     String quantityUnit) {
        String str = "";
        Ingredient i  = em.find(Ingredient.class, ingredientId);
        if (i != null) {
            setIngredientParameters(i, name, quantity, quantityUnit);
            persist(i);
            em.flush();
            str = str.concat("Ingrédient modifié!");
        } else {
            str = str.concat("Impossible de modifier l'ingrédient demandé!");
        }
        return str;
    }

    @Override
    public String modifyIngredient(Long ingredientId, String name, int quantity, 
                                     String quantityUnit, Long recipieId) {
        String str = "";
        Ingredient i  = em.find(Ingredient.class, ingredientId);
        if (i != null) {
            setIngredientParameters(i, name, quantity, quantityUnit);
            Recipie r = em.find(Recipie.class, recipieId);
            if (r != null) {
                i.setRecipieInvolved(r);
            }
            persist(i);
            em.flush();
            str = str.concat("Ingrédient modifié!");
        } else {
            str = str.concat("Impossible de modifier l'ingrédient demandé!");
        }
        return str;
    }

    @Override
    public String deleteIngredient(Long id) {
        String str = "";
        Ingredient i = em.find(Ingredient.class, id);
        if (i != null) {
            em.remove(i);
            str = str.concat("Ingrédient supprimé!");
        } else {
            str = str.concat("Impossible de supprimer l'ingrédient demandé!");
        }
        return str;
    }
    
    public void persist(Object object) {
        em.persist(object);
    }

}
