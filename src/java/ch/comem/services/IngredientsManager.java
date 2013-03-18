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

    @Override
    public long createIngredient(String name, int quantity, String quantityUnit) {
        Ingredient i = new Ingredient();
        i.setName(name);
        i.setQuantity(quantity);
        i.setQuantityUnit(quantityUnit);
        i.setRecipieInvolved(null);
        persist(i);
        em.flush();
        return i.getId();
    }

    @Override
    public long createIngredient(String name, int quantity, String quantityUnit, long recipieId) {
        long ingredientId = createIngredient(name, quantity, quantityUnit);
        Recipie r = em.find(Recipie.class, recipieId);
        if (r != null) {
            Ingredient i = em.find(Ingredient.class, ingredientId);
            i.setRecipieInvolved(r);
            persist(i);
            em.flush();
        }
        return ingredientId;
    }

    @Override
    public String modifyIngredient(int ingredientId, String name, int quantity, 
                                     String quantityUnit) {
        String str = "";
        Ingredient i  = em.find(Ingredient.class, ingredientId);
        if (i != null) {
            i.setName(name);
            i.setQuantity(quantity);
            i.setQuantityUnit(quantityUnit);
            persist(i);
            em.flush();
            str = str.concat("Ingrédient modifié!");
        } else {
            str = str.concat("Impossible de modifier l'ingrédient demandé!");
        }
        return str;
    }

    @Override
    public String modifyIngredient(int ingredientId, String name, int quantity, 
                                     String quantityUnit, long recipieId) {
        String str = "";
        Ingredient i  = em.find(Ingredient.class, ingredientId);
        if (i != null) {
            i.setName(name);
            i.setQuantity(quantity);
            i.setQuantityUnit(quantityUnit);
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
    public String deleteIngredient(int id) {
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
