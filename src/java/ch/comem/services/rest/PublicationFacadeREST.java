package ch.comem.services.rest;

import ch.comem.model.Category;
import ch.comem.model.Ingredient;
import ch.comem.model.Membership;
import ch.comem.model.Photo;
import ch.comem.model.Publication;
import ch.comem.model.Recipie;
import ch.comem.model.Step;
import ch.comem.services.beans.CategoriesManagerLocal;
import ch.comem.services.beans.IngredientsManagerLocal;
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
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
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
@Path("publications")
public class PublicationFacadeREST {
    @EJB
    private RecipieManagerLocal rm;
    @EJB
    private StepsManagerLocal sm;
    @EJB
    private IngredientsManagerLocal im;
    @EJB
    private CategoriesManagerLocal cam;
    @EJB
    private PhotosManagerLocal phm;
    @EJB
    private PublicationsManagerLocal pum;
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;

    @POST
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public Publication create(Publication entity) {
        Membership m = entity.getPublisher();
        Long memberId = null;
        if (m != null)
            memberId = m.getId();
        Photo ph = entity.getImagingPhoto();
        Long photoId = null;
        if (ph != null)
            photoId = phm.createPhoto(ph.getSource(), ph.getAlt());
        Category c = entity.getCategory();
        Long categoryId = null;
        if (c != null)
            categoryId = cam.useCategory(c.getName());
        Recipie r = entity.getRecepie();
        Long recipieId = null;
        if (r != null) {
            List<Ingredient> iList = r.getIngredients();
            List<Long> ingredientIds = null;
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
            List<Long> stepIds = null;
            if (sList != null && !sList.isEmpty()) {
                stepIds = new ArrayList<>();
                for(Step s : sList) {
                    Long sId = sm.createStep(s.getStepNumber(), 
                                             s.getDescription());
                    stepIds.add(sId);
                }
            }
            recipieId = rm.createRecipie(r.getName(), ingredientIds, stepIds);
        }
        Long pId = pum.createPublication(memberId,
                                        photoId,
                                        categoryId,
                                        recipieId);
        return getEntityManager().find(Publication.class, pId);
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public Publication edit(Publication entity) {
        pum.modifyPublication(entity.getId(), entity.getRecepie().getId());
        return getEntityManager().find(Publication.class, entity.getId());
    }

//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Long id) {}

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public PublicationDTO find(@PathParam("id") Long id) {
        Publication p = getEntityManager().find(Publication.class, id);
        PublicationDTO pDTO = null;
        if (p != null) {
            pDTO = new PublicationDTO();
            pDTO.setId(p.getId());
            pDTO.setDateOfPublication(p.getDateOfPublication());
            Membership m = p.getPublisher();
            MembershipDTO mDTO = null;
            if (m != null) {
                mDTO = new MembershipDTO();
                mDTO.setId(m.getId());
                mDTO.setFirstName(m.getFirstName());
                mDTO.setLastName(m.getLastName());
            }
            pDTO.setPublisher(mDTO);
            Photo ph = p.getImagingPhoto();
            PhotoDTO phDTO = null;
            if (ph != null) {
                phDTO = new PhotoDTO();
                phDTO.setSource(ph.getSource());
                phDTO.setAlt(ph.getAlt());
            }
            pDTO.setImagingPhoto(phDTO);
            Recipie r = p.getRecepie();
            RecipieDTO rDTO = null;
            if (r != null) {
                rDTO = new RecipieDTO();
                rDTO.setName(r.getName());
                List<Ingredient> iList = r.getIngredients();
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
                List<Step> sList = r.getSteps();
                List<StepDTO> sDTOList = null;
                if (sList != null && !sList.isEmpty()) {
                    sDTOList = new ArrayList<>();
                    for (Step s : sList) {
                        StepDTO sDTO = new StepDTO();
                        sDTO.setStepNumber(s.getStepNumber());
                        sDTO.setDescription(s.getDescription());
                        sDTOList.add(sDTO);
                    }
                }
                rDTO.setSteps(sDTOList);
            }
            pDTO.setRecepie(rDTO);
            Category c = p.getCategory();
            CategoryDTO cDTO = null;
            if (c != null) {
                cDTO = new CategoryDTO();
                cDTO.setId(c.getId());
                cDTO.setName(c.getName());
            }
            pDTO.setCategory(cDTO);
        }
        return pDTO;
    }
    
    private List<PublicationDTO> setPublicationList(List<Publication> pList) {
        List<PublicationDTO> pDTOList = null;
        if (pList != null && !pList.isEmpty()) {
            pDTOList = new ArrayList<>();
            for (Publication p : pList) {
                PublicationDTO pDTO = new PublicationDTO();
                pDTO.setId(p.getId());
                Recipie r = p.getRecepie();
                RecipieDTO rDTO = null;
                if (r != null) {
                    rDTO = new RecipieDTO();
                    rDTO.setName(r.getName());
                }
                pDTO.setRecepie(rDTO);
                Category c = p.getCategory();
                CategoryDTO cDTO = new CategoryDTO();
                if (c != null)
                    cDTO.setName(c.getName());                            
                else
                    cDTO.setName("Pas catégorisée");
                pDTO.setCategory(cDTO);
                pDTOList.add(pDTO);
            }
        }
        return pDTOList;
    }

    @GET
    @Path("searchRecipie/{name}")
    @Produces({"application/xml", "application/json"})
    public List<PublicationDTO> findPublicationsFromRecipieName(@PathParam("name") String name) {
        List<Publication> pList = pum.findPublicationsFromRecipieName(name);
        return setPublicationList(pList);
    }
    
    @GET
    @Path("searchLatestPublications")
    @Produces({"application/xml", "application/json"})
    public List<PublicationDTO> findLatestPublications() {
        List<Publication> pList = pum.findLatestPublications();
        return setPublicationList(pList);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<PublicationDTO> findAll() {
        List<Publication> pList = pum.findAllPublications();
        return setPublicationList(pList);
    }  

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<PublicationDTO> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        List<Publication> allPublications = pum.findAllPublications();
        List<PublicationDTO> subSelectionDTO = null;
        if (allPublications != null && !allPublications.isEmpty()) {
            List<Publication> subSelection = allPublications.subList(from.intValue(), to.intValue());
            if (subSelection != null && !subSelection.isEmpty())
                subSelectionDTO = setPublicationList(subSelection);
        }
        return subSelectionDTO;
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(pum.findAllPublications().size());
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    public void persist(Object object) {
        em.persist(object);
    }
    
}
