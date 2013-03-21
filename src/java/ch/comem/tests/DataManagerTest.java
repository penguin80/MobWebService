package ch.comem.tests;

import ch.comem.services.beans.CategoriesManagerLocal;
import ch.comem.services.beans.CommentsManagerLocal;
import ch.comem.services.beans.IngredientsManagerLocal;
import ch.comem.services.beans.LikesManagerLocal;
import ch.comem.services.beans.MembersManagerLocal;
import ch.comem.services.beans.PhotosManagerLocal;
import ch.comem.services.beans.PublicationsManagerLocal;
import ch.comem.services.beans.RecipieManagerLocal;
import ch.comem.services.beans.StepsManagerLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 *
 * @author Pierre-Alexandre
 */
@Stateless
@WebService
public class DataManagerTest implements DataManagerTestLocal {
    @EJB
    private StepsManagerLocal sm;
    @EJB
    private PublicationsManagerLocal pum;
    @EJB
    private PhotosManagerLocal phm;
    @EJB
    private LikesManagerLocal lm;
    @EJB
    private CategoriesManagerLocal cam;
    @EJB
    private MembersManagerLocal mm;
    @EJB
    private RecipieManagerLocal rm;
    @EJB
    private IngredientsManagerLocal im;
    @EJB
    private CommentsManagerLocal com;
    
    
    private String[] firstNames = {"Paul", "Arthur", "Danielle", "Georges",
                                   "Isabelle", "Chuck", "Mario", "Joëlle",
                                   "Léa", "Walter"};
    private String[] lastNames = {"Hogan", "Lion", "Müller", "Pasche",
                                  "Adjani", "Norris", "Kart", "Wyss",
                                  "Teoni", "Baertchi"};
    private int[] ages = {55, 432, 28, 33, 54, 94, 230, 33, 23, 18};
    
    private String[] sources = {"www.heig-vd.ch/assets/bg_logo.png", 
                                "www.heig-vd.ch/assets/hesso-logo.png",
                                "labelattitude.comem.ch/img/logoComem.png"};

    @Override
    public void testMethods() {
        for (int index = 0; index < 10; index++) {
            long x = mm.createMember(firstNames[index], lastNames[index], ages[index], 
                                     "pseudo" + index, firstNames[index] + "." + 
                                     lastNames[index] + "@test.org");
        }
    }

    @Override
    public void testRelationRecipieIngredients() {
        String name = "TestRecette";
        Long i1 = im.createIngredient("farine", 300, "grammes");
        Long i2 = im.createIngredient("sucre", 120, "grammes");
        Long i3 = im.createIngredient("oeuf", 2, "unités");
        Long i4 = im.createIngredient("cacao en poudre", 3, "c.s");
        Long i5 = im.createIngredient("poudre à lever", 1, "c.c");
        List<Long> ingredientsId = new ArrayList<>();
        ingredientsId.add(i1);
        ingredientsId.add(i2);
        ingredientsId.add(i3);
        ingredientsId.add(i4);
        ingredientsId.add(i5);
        Long s1 = sm.createStep(1, "Battre les oeufs");
        Long s2 = sm.createStep(2, "Ajouter le sucre aux oeufs");
        Long s3 = sm.createStep(3, "Blanchir les oeufs");
        List<Long> stepsId = new ArrayList<>();
        stepsId.add(s1);
        stepsId.add(s2);
        stepsId.add(s3);
        Long recipieId = rm.createRecipie(name, ingredientsId, stepsId);
        
    }

    @Override
    public void testPublicationRelations() {
        String name = "TestEcole";
        Long i1 = im.createIngredient("étudiants", 3000, "personnes");
        Long i2 = im.createIngredient("professeurs", 200, "personnes");
        Long i3 = im.createIngredient("cours", 80, "branches");
        List<Long> stepsId = new ArrayList<>();
        List<Long> ingredientsId = new ArrayList<>();
        ingredientsId.add(i1);
        ingredientsId.add(i2);
        ingredientsId.add(i3);
        Long r1 = rm.createRecipie(name, ingredientsId, stepsId);
        Long ph1 = phm.createPhoto(sources[2], "Logo de Comem+");
        Long m1 = mm.createMember(firstNames[5], lastNames[5], ages[5], "Chuck", 
                                  "I.destroy@everything.com");
        Long cam1 = cam.createCategory("Crèmes et Flans");
//        Long cam1 = cam.createCategory("Cancres et Surdoués");
        Long pum1 = pum.createPublication(ph1, cam1, m1, r1);
    }
    
}
