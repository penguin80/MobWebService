package ch.comem.tests;

import ch.comem.config.Config;
import ch.comem.model.Membership;
import ch.comem.model.Photo;
import ch.comem.model.Publication;
import ch.comem.services.beans.CategoriesManagerLocal;
import ch.comem.services.beans.CommentsManagerLocal;
import ch.comem.services.beans.IngredientsManagerLocal;
import ch.comem.services.beans.LikesManagerLocal;
import ch.comem.services.beans.MembersManagerLocal;
import ch.comem.services.beans.PhotosManagerLocal;
import ch.comem.services.beans.PublicationsManagerLocal;
import ch.comem.services.beans.RecipieManagerLocal;
import ch.comem.services.beans.StepsManagerLocal;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
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
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;
    
    
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
            Long x = mm.createMember(firstNames[index], lastNames[index], ages[index], 
                                     "pseudo" + index, firstNames[index] + "." + 
                                     lastNames[index] + "@test.org", "toto" + index);
            
            Membership m = em.find(Membership.class, x);

            Calendar c = new GregorianCalendar();
            try {
 
                Client client = Client.create();

               WebResource webResource = client.resource("http://"+Config.IP_ADDRESS+"/"+Config.APP_LOCATION+"/player");

                String input = "{\"firstName\":\""+ m.getFirstName()
                                + "\",\"lastName\": \""+ m.getLastName() 
                                + "\",\"email\": \""+ m.getEmail() 
                                + "\",\"memberId\": "+ m.getId()
                                + ",\"application\": {\"id\": 1 }}";

                    webResource.type("application/json").post(ClientResponse.class, input);

                WebResource webResource2 = client.resource("http://"+Config.IP_ADDRESS+"/"+Config.APP_LOCATION+"/event");

                String input2 = "{\"type\":\""+ "Création de compte"
                                + "\",\"timeInMillis\": " + c.getTimeInMillis()
                                + ",\"player\": {\"memberId\": " + m.getId() + "}"
                                + ",\"application\": {\"id\": 1 }}";

                    webResource2.type("application/json").post(ClientResponse.class, input2);

    //		if (response.getStatus() != 201) {
    //			throw new RuntimeException("Failed : HTTP error code : "
    //			     + response.getStatus());
    //		}

            } catch (Exception e) {

                    e.printStackTrace();

            }
        }
    }

    @Override
    public void addNewCompletePublication(Long memberId) {
        if (memberId != null) {
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
            Long photoId = phm.createPhoto(sources[1], "Logo HES-SO");
            Long categoryId = cam.useCategory("Cupcakes");
//            Long categoryId = cam.useCategory("Gâteaux");
            Long publicationId = pum.createPublication(memberId, photoId, categoryId, recipieId);

            String publicationType = null;
            Photo ph = em.find(Photo.class, photoId);
            Membership m = em.find(Membership.class, memberId);
            Publication pDTO = em.find(Publication.class, publicationId);
            if (ph != null) {
                publicationType = "Publication Photo";
                if (!stepsId.isEmpty())
                    publicationType = "Publication Photo + Recette Complète";
            }

            Calendar cal = new GregorianCalendar();
            try {

                Client client = Client.create();

                WebResource webResource = client.resource("http://"+Config.IP_ADDRESS+"/"+Config.APP_LOCATION+"/event");
          
                if (pDTO != null && m != null && 
                    pDTO.getCategory() != null &&
                    pDTO.getCategory().getName() != null && 
                    !pDTO.getCategory().getName().isEmpty()) {

                    String input = "{\"type\":\"" + pDTO.getCategory().getName();
                    input = input.concat("\",\"timeInMillis\": "+ cal.getTimeInMillis());
                    input = input.concat(",\"player\": {\"memberId\": " + m.getId() + "}");
                    input = input.concat(",\"application\": {\"id\": 1 }}");
                    webResource.type("application/json").post(ClientResponse.class, input);
                 }

                if (m != null) {
                    int nbPublication = m.getPublicationsConcerned().size();
                    String input2 = "";
                    if (nbPublication == 1)
                        input2 = input2.concat("{\"type\":\"Première publication");
                    else
                        input2 = input2.concat("{\"type\":\""+ nbPublication + "ème publication");
                    input2 = input2.concat("\",\"timeInMillis\": "+ cal.getTimeInMillis());
                    input2 = input2.concat(",\"player\": {\"memberId\": " + m.getId() + "}");
                    input2 = input2.concat(",\"application\": {\"id\": 1 }}");
                    webResource.type("application/json").post(ClientResponse.class, input2);
                }

                if (m != null && publicationType != null) {
                    String input3 = "{\"type\":\""+ publicationType;
                    input3 = input3.concat("\",\"timeInMillis\": "+ cal.getTimeInMillis());
                    input3 = input3.concat(",\"player\": {\"memberId\": " + m.getId() + "}");
                    input3 = input3.concat(",\"application\": {\"id\": 1 }}");
                    webResource.type("application/json").post(ClientResponse.class, input3);

                }


            } catch (Exception e) {

                    e.printStackTrace();

            }
        }
    }

    @Override
    public void testPublicationRelations() {
        String name = "TestDestructor";
        Long i1 = im.createIngredient("méchants", 3000, "personnes");
        Long i2 = im.createIngredient("M1 Abrams", 200, "tanks");
        Long i3 = im.createIngredient("caches de méchants", 8, "buildings");
        Long s1 = sm.createStep(1, "Donner un coup de pied dans un M1 Abrams");
        Long s2 = sm.createStep(2, "Si le tank se retourne et explose sur un " + 
                                "autre tank, continuer sur la lancée en " + 
                                "retournant à l'étape 1");
        Long s3 = sm.createStep(3, "Si le tank se retourne et explose contre un " + 
                                "building, continuer sur la lancée en " + 
                                "retournant à l'étape 1");
        Long s4 = sm.createStep(4, "Si le tank se retourne et n'explose pas, " + 
                                "alors il faut prendre le M1 Abrams par le canon " + 
                                "et utiliser le reste du tank comme batte de " + 
                                "baseball");
        Long s5 = sm.createStep(5, "La destruction totale se termine quand il " + 
                                "n'y a plus de méchants debouts, ni tanks en " + 
                                "état de marche. Norrisproof");
        List<Long> ingredientsId = new ArrayList<>();
        ingredientsId.add(i1);
        ingredientsId.add(i2);
        ingredientsId.add(i3);
        List<Long> stepsId = new ArrayList<>();
        stepsId.add(s1);
        stepsId.add(s2);
        stepsId.add(s3);
        stepsId.add(s4);
        stepsId.add(s5);
        Long r1 = rm.createRecipie(name, ingredientsId, stepsId);
        Long ph1 = phm.createPhoto(sources[2], "Chuck Norris was here");
        Long m1 = new Long(6);
        Long cam1 = cam.useCategory("Gâteaux");
//        Long cam1 = cam.createCategory("Cancres et Surdoués");
        Long pum1 = pum.createPublication(m1, ph1, cam1, r1);
        
        String publicationType = null;
        Photo ph = em.find(Photo.class, ph1);
        Membership m = em.find(Membership.class, m1);
        Publication pDTO = em.find(Publication.class, pum1);
        if (ph != null) {
            publicationType = "Publication Photo";
            if (!stepsId.isEmpty())
                publicationType = "Publication Photo + Recette Complète";
        }
        
        Calendar cal = new GregorianCalendar();
        try {
 
            Client client = Client.create();

            WebResource webResource = client.resource("http://"+Config.IP_ADDRESS+"/"+Config.APP_LOCATION+"/event");
            if (pDTO != null && m != null && 
                pDTO.getCategory() != null &&
                pDTO.getCategory().getName() != null && 
                !pDTO.getCategory().getName().isEmpty()) {
                
                String input = "{\"type\":\"" + pDTO.getCategory().getName();
                input = input.concat("\",\"timeInMillis\": "+ cal.getTimeInMillis());
                input = input.concat(",\"player\": {\"memberId\": " + m.getId() + "}");
                input = input.concat(",\"application\": {\"id\": 1 }}");
                webResource.type("application/json").post(ClientResponse.class, input);
             }
              
            if (m != null) {
                int nbPublication = m.getPublicationsConcerned().size();
                String input2 = "";
                if (nbPublication == 1)
                    input2 = input2.concat("{\"type\":\"Première publication");
                else
                    input2 = input2.concat("{\"type\":\""+ nbPublication + "ème publication");
                input2 = input2.concat("\",\"timeInMillis\": "+ cal.getTimeInMillis());
                input2 = input2.concat(",\"player\": {\"memberId\": " + m.getId() + "}");
                input2 = input2.concat(",\"application\": {\"id\": 1 }}");
                webResource.type("application/json").post(ClientResponse.class, input2);
            }
            
            if (m != null && publicationType != null) {
                String input3 = "{\"type\":\""+ publicationType;
                input3 = input3.concat("\",\"timeInMillis\": "+ cal.getTimeInMillis());
                input3 = input3.concat(",\"player\": {\"memberId\": " + m.getId() + "}");
                input3 = input3.concat(",\"application\": {\"id\": 1 }}");
                webResource.type("application/json").post(ClientResponse.class, input3);

            }
            
 

        } catch (Exception e) {
 
		e.printStackTrace();
                
        }

    }

    @Override
    public void populateCategoryTable() {
        cam.createCategory("Biscuits");
        cam.createCategory("Gâteaux");
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

    public void persist(Object object) {
        em.persist(object);
    }
    
}
