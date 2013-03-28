package ch.comem.services.rest;

import ch.comem.model.Authentication;
import ch.comem.model.Category;
import ch.comem.model.Ingredient;
import ch.comem.model.Membership;
import ch.comem.model.Photo;
import ch.comem.model.Publication;
import ch.comem.model.Recipie;
import ch.comem.model.Step;
import ch.comem.services.beans.AuthenticationsManagerLocal;
import ch.comem.services.beans.CategoriesManagerLocal;
import ch.comem.services.beans.IngredientsManagerLocal;
import ch.comem.services.beans.MembersManagerLocal;
import ch.comem.services.beans.PhotosManagerLocal;
import ch.comem.services.beans.PublicationsManagerLocal;
import ch.comem.services.beans.RecipieManagerLocal;
import ch.comem.services.beans.StepsManagerLocal;
import ch.comem.services.dto.CategoryDTO;
import ch.comem.services.dto.IngredientDTO;
import ch.comem.services.dto.MembershipDTO;
import ch.comem.services.dto.PhotoDTO;
import ch.comem.services.dto.PublicationDTO;
import ch.comem.services.dto.RecipieDTO;
import ch.comem.services.dto.StepDTO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Pierre-Alexandre
 */
@Stateless
@Path("memberships")
public class MembershipFacadeREST {
    @EJB
    private AuthenticationsManagerLocal am;
    @EJB
    private IngredientsManagerLocal im;
    @EJB
    private StepsManagerLocal sm;
    @EJB
    private PhotosManagerLocal phm;
    @EJB
    private CategoriesManagerLocal cm;
    @EJB
    private RecipieManagerLocal rm;
    @EJB
    private PublicationsManagerLocal pum;
    @EJB
    private MembersManagerLocal mm;
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;

    private MembershipDTO setInitialMembershipDTO(Membership m) {
        MembershipDTO mDTO = new MembershipDTO();
        mDTO.setId(m.getId());
        mDTO.setFirstName(m.getFirstName());
        mDTO.setLastName(m.getLastName());
        mDTO.setAge(m.getAge());
        mDTO.setPseudo(m.getPseudo());
        mDTO.setEmail(m.getEmail());
        return mDTO;
    }
    
    @POST
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public MembershipDTO create(Membership entity) {
        Authentication a = entity.getAuthenticate();
        String emailStored = am.createAccount(entity.getEmail(), a.getPassword());
        Long mId =  mm.createMember(entity.getFirstName(), entity.getLastName(), 
                                    entity.getAge(), entity.getPseudo(), 
                                    emailStored, a.getPassword());
        Membership m = getEntityManager().find(Membership.class, mId);

        Calendar c = new GregorianCalendar();
        try {

            Client client = Client.create();

            WebResource webResource = client.resource("http://localhost:8080/PastryChefGamification/webresources/player");
            String input = "{\"firstName\":\""+ m.getFirstName()
                            + "\",\"lastName\": \""+ m.getLastName() 
                            + "\",\"email\": \""+ m.getEmail() 
                            + "\",\"memberId\": "+ m.getId()
                            + ",\"application\": {\"id\": 1 }}";

                webResource.type("application/json").post(ClientResponse.class, input);

            WebResource webResource2 = client.resource("http://localhost:8080/PastryChefGamification/webresources/event");
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

        return setInitialMembershipDTO(m);
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public MembershipDTO edit(Membership entity) {
        Authentication a = entity.getAuthenticate();
        am.modifyAccount(entity.getEmail(), a.getPassword());
        Long mId =mm.modifyMember(entity.getId(), entity.getFirstName(), 
                                  entity.getLastName(), entity.getAge(), 
                                  entity.getPseudo(), entity.getEmail(), 
                                  a.getPassword());
        Membership m = getEntityManager().find(Membership.class, mId);
        return setInitialMembershipDTO(m);
    }
    
    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public PublicationDTO publish(@PathParam("id") Long id, Publication p) {
        Membership m = getEntityManager().find(Membership.class, id);
        PublicationDTO pDTO = null;
        String publicationType = null;
        if (m != null && p != null) {
            Photo ph = p.getImagingPhoto();
            Long photoId = null;
            if (ph != null)
                photoId = phm.createPhoto(ph.getSource(), ph.getAlt());
            Long categoryId = null;
            Category c = p.getCategory();
            if (c != null)
                categoryId = cm.useCategory(c.getName());
            List<Long> ingredientIds = null;
            List<Long> stepIds = null;
            Long recipieId = null; 
            Recipie r = p.getRecepie();
            if (r != null) {
                List<Ingredient> iList = r.getIngredients();
                if (iList != null && !iList.isEmpty()) {
                    ingredientIds = new ArrayList<>();
                    for(Ingredient i : iList) {
                        Long iId = im.createIngredient(i.getName(), 
                                                       i.getQuantity(), 
                                                       i.getQuantityUnit());
                        ingredientIds.add(iId);
                    }
                }
                List<Step> sList = r.getSteps();
                if (sList != null && !sList.isEmpty()) {
                    stepIds = new ArrayList<>();
                    for(Step s : sList) {
                        Long sId = sm.createStep(s.getStepNumber(), 
                                                 s.getDescription());
                        stepIds.add(sId);
                    }
                }
                recipieId = rm.createRecipie(p.getRecepie().getName(), 
                                             ingredientIds, stepIds);
            }
            Long pId = pum.createPublication(m.getId(), photoId, categoryId, 
                                             recipieId);
            Publication pCreated = getEntityManager().find(Publication.class, pId);
            pDTO = new PublicationDTO();
            pDTO.setId(pCreated.getId());
            pDTO.setDateOfPublication(pCreated.getDateOfPublication());
            Photo phCreated = getEntityManager().find(Photo.class, photoId);
            PhotoDTO phDTO = null;
            if (phCreated != null) {
                phDTO = new PhotoDTO();
                phDTO.setSource(phCreated.getSource());
                phDTO.setAlt(phCreated.getAlt());
                publicationType = "Publication Photo";
            }
            pDTO.setImagingPhoto(phDTO);
            Recipie rCreated = getEntityManager().find(Recipie.class, recipieId);
            RecipieDTO rDTO = null;
            if (rCreated != null) {
                rDTO = new RecipieDTO();
                rDTO.setName(rCreated.getName());
                List<Ingredient> iList = rCreated.getIngredients();
                List<IngredientDTO> iDTOList = null;
                if (iList != null && !iList.isEmpty()) {
                    iDTOList = new ArrayList<>();
                    for (Ingredient i : iList) {
                        IngredientDTO iDTO = new IngredientDTO();
                        iDTO.setName(i.getName());
                        iDTO.setQuantity(i.getQuantity());
                        iDTO.setQuantityUnit(i.getQuantityUnit());
                        iDTOList.add(iDTO);
                    }
                }
                rDTO.setIngredients(iDTOList);
                List<Step> sList = rCreated.getSteps();
                List<StepDTO> sDTOList = null;
                if (sList != null && !sList.isEmpty()) {
                    sDTOList = new ArrayList<>();
                    for (Step s : sList) {
                        StepDTO sDTO = new StepDTO();
                        sDTO.setStepNumber(s.getStepNumber());
                        sDTO.setDescription(s.getDescription());
                        sDTOList.add(sDTO);
                    }
                    publicationType = "Publication Photo + Recette Complète";
                }
                rDTO.setSteps(sDTOList);
            }
            pDTO.setRecepie(rDTO);
            MembershipDTO mDTO = new MembershipDTO();
            mDTO.setId(m.getId());
            mDTO.setFirstName(m.getFirstName());
            mDTO.setLastName(m.getLastName());
            pDTO.setPublisher(mDTO);
            Category cCreated = getEntityManager().find(Category.class, categoryId);
            CategoryDTO cDTO = null;
            if (cCreated != null) {
                cDTO = new CategoryDTO();
                cDTO.setId(cCreated.getId());
                cDTO.setName(cCreated.getName());
            }
            pDTO.setCategory(cDTO);
        }

        Calendar cal = new GregorianCalendar();
        try {

            Client client = Client.create();

            WebResource webResource = client.resource("http://localhost:8080/PastryChefGamification/webresources/event");
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

//		if (response.getStatus() != 201) {
//			throw new RuntimeException("Failed : HTTP error code : "
//			     + response.getStatus());
//		}


        } catch (Exception e) {

                e.printStackTrace();

        }
        return pDTO;
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        mm.deleteMember(id);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public MembershipDTO find(@PathParam("id") Long id) {
        Membership m = getEntityManager().find(Membership.class, id);
        MembershipDTO mDTO = null;
        if (m != null) {
            mDTO = new MembershipDTO();
            mDTO.setId(m.getId());
            mDTO.setFirstName(m.getFirstName());
            mDTO.setLastName(m.getLastName());
            mDTO.setAge(m.getAge());
            mDTO.setPseudo(m.getPseudo());
            mDTO.setEmail(m.getEmail());
        }
        return mDTO;
    }

    private List<MembershipDTO> setMembershipList(List<Membership> mList) {
        List<MembershipDTO> mDTOList = null;
        if (mList != null && !mList.isEmpty()) {
            mDTOList = new ArrayList<>();
            for (Membership m : mList) {
                MembershipDTO mDTO = new MembershipDTO();
                mDTO.setId(m.getId());
                mDTO.setFirstName(m.getFirstName());
                mDTO.setLastName(m.getLastName());
                mDTO.setPseudo(m.getPseudo());
                mDTOList.add(mDTO);
            }
        }
        return mDTOList;
    }
    
    @GET 
    @Path("searchPhotos/{id}")
    @Produces({"application/xml", "application/json"})
    public List<PublicationDTO> findPublicationsByMemberId(@PathParam("id") Long id) {
        List<Publication> puList = mm.findAllPublicationsFromMemberId(id);
        List<PublicationDTO> puDTOList = null;
        if (puList != null && !puList.isEmpty()) {
            puDTOList = new ArrayList<>();
            for (Publication pu : puList) {
                PublicationDTO puDTO = new PublicationDTO();
                Photo ph = pu.getImagingPhoto();
                PhotoDTO phDTO = null;
                if (ph != null) {
                    phDTO = new PhotoDTO();
                    phDTO.setId(ph.getId());
                    phDTO.setSource(ph.getSource());
                    phDTO.setAlt(ph.getAlt());
                } 
                puDTO.setImagingPhoto(phDTO);
                Recipie r = pu.getRecepie();
                RecipieDTO rDTO = null;
                if (r != null) {
                    rDTO = new RecipieDTO();
                    rDTO.setName(r.getName());
                }
                puDTO.setRecepie(rDTO);
                puDTOList.add(puDTO);
            }
        }
        return puDTOList;
    }
    
    @GET
    @Produces({"application/xml", "application/json"})
    public List<MembershipDTO> findAll() {
        List<Membership> mList = mm.findAllMembers();
        return setMembershipList(mList);
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<MembershipDTO> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        List<Membership> allMembers = mm.findAllMembers();
        List<MembershipDTO> subSelectionDTO = null;
        if (allMembers != null && !allMembers.isEmpty()) {
            List<Membership> subSelection = allMembers.subList(from.intValue(), to.intValue());
            subSelectionDTO = setMembershipList(subSelection);
        }
        return subSelectionDTO;
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(mm.findAllMembers().size());
    }

    protected EntityManager getEntityManager() {
        return em;
    }
    
}
