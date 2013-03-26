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
                                "ec.pond5.com/s3/000732169_iconv.jpeg"};

    @Override
    public void testMethods() {
        for (int index = 0; index < 10; index++) {
            long x = mm.createMember(firstNames[index], lastNames[index], ages[index], 
                                     "pseudo" + index, firstNames[index] + "." + 
                                     lastNames[index] + "@test.org", "toto" + index);
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
        String name = "TestDestructor";
        Long i1 = im.createIngredient("méchants", 3000, "personnes");
        Long i2 = im.createIngredient("M1 Abrams", 200, "tanks");
        Long i3 = im.createIngredient("caches de méchants", 8, "buildings");
        List<Long> stepsId = new ArrayList<>();
        List<Long> ingredientsId = new ArrayList<>();
        ingredientsId.add(i1);
        ingredientsId.add(i2);
        ingredientsId.add(i3);
        Long r1 = rm.createRecipie(name, ingredientsId, stepsId);
        Long ph1 = phm.createPhoto(sources[2], "Chuck Norris was here");
        Long m1 = mm.createMember(firstNames[5], lastNames[5], ages[5], "Chuck", 
                                  "i.destroy@everything.com", "invincible");
        Long cam1 = cam.useCategory("Crèmes et flans");
//        Long cam1 = cam.createCategory("Cancres et Surdoués");
        Long pum1 = pum.createPublication(m1, ph1, cam1, r1);
    }

    @Override
    public void populateCategoryTable() {
        cam.createCategory("Biscuits");
        cam.createCategory("Gateaux");
        cam.createCategory("Petits gâteaux");
        cam.createCategory("Gâteaux d'anniversaire");
        cam.createCategory("Macarons");
        cam.createCategory("Cake pops");
        cam.createCategory("Cupcakes");
        cam.createCategory("Gâteaux au yaourt");
        cam.createCategory("Pâtes à sucre");
        cam.createCategory("Crèmes et flans");
        cam.createCategory("Vacherins glacés");
        cam.createCategory("Tartes");
        cam.createCategory("Muffins");
    }
    
}
