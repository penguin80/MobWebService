/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.tests;

import ch.comem.model.Ingredient;
import ch.comem.services.IngredientsManagerLocal;
import ch.comem.services.MembersManagerLocal;
import ch.comem.services.RecipieManagerLocal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Pierre-Alexandre
 */
@Stateless
@WebService
public class DataManagerTest implements DataManagerTestLocal {

    @EJB
    private MembersManagerLocal mm;
    @EJB
    private RecipieManagerLocal rm;
    @EJB
    private IngredientsManagerLocal im;
    
    private String[] firstNames = {"Paul", "Arthur", "Danielle", "Georges",
                                   "Isabelle", "Chuck", "Mario", "Joëlle",
                                   "Léa", "Walter"};
    private String[] lastNames = {"Hogan", "Lion", "Müller", "Pasche",
                                  "Adjani", "Norris", "Kart", "Wyss",
                                  "Teoni", "Baertchi"};
    private int[] ages = {55, 432, 28, 33, 24, 94, 230, 33, 23, 18};

    @Override
    public void testMethods() {
        for (int index = 0; index < 10; index++) {
            long x = mm.createMember(firstNames[index], lastNames[index], ages[index]);
            System.out.println(x);
        }
    }

    @Override
    public void testRelationRecipieIngredients() {
        Long i1 = im.createIngredient("farine", 300, "grammes");
        Long i2 = im.createIngredient("sucre", 120, "grammes");
        Long i3 = im.createIngredient("oeuf", 2, "unités");
        Long i4 = im.createIngredient("cacao en poudre", 3, "c.s");
        Set<String> steps = new HashSet<>();
        steps.add("Battre les oeufs");
        steps.add("Ajouter le sucre aux oeufs");
        steps.add("Blanchir les oeufs");
        Collection<Long> ingredientsId = new ArrayList<>();
        ingredientsId.add(i1);
        ingredientsId.add(i2);
        ingredientsId.add(i3);
        ingredientsId.add(i4);
        Long recipieId = rm.createRecipie(steps, ingredientsId);
        
        im.createIngredient("poudre à lever", 1, "c.c", recipieId);
    }
}
