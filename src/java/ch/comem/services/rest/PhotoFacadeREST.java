package ch.comem.services.rest;

import ch.comem.model.Category;
import ch.comem.model.Ingredient;
import ch.comem.model.Membership;
import ch.comem.model.Photo;
import ch.comem.model.Publication;
import ch.comem.model.Recipie;
import ch.comem.model.Step;
import ch.comem.services.beans.PhotosManagerLocal;
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Pierre-Alexandre
 */
@Stateless
@Path("photos")
public class PhotoFacadeREST {
    @EJB
    private PhotosManagerLocal pm;
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Photo entity) {
        pm.createPhoto(entity.getSource(), entity.getAlt());
    }

//    @PUT
//    @Consumes({"application/xml", "application/json"})
//    public void edit(Photo entity) {}

//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Long id) {}

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public PublicationDTO find(@PathParam("id") Long id) {
        Photo ph = getEntityManager().find(Photo.class, id);
        PublicationDTO pDTO = null;
        if (ph != null) {
            PhotoDTO phDTO = new PhotoDTO();
            phDTO.setSource(ph.getSource());
            phDTO.setAlt(ph.getAlt());
            Publication p = ph.getPublicationConcerned();
            if (p != null) {
                pDTO = new PublicationDTO();
                pDTO.setId(p.getId());
                pDTO.setDateOfPublication(p.getDateOfPublication());
                pDTO.setLongDate(p.getLongDate());
                Category c = p.getCategory();
                CategoryDTO cDTO = null;
                if (c != null) {
                    cDTO = new CategoryDTO();
                    cDTO.setName(c.getName());
                }
                pDTO.setCategory(cDTO);
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
                Membership m = p.getPublisher();
                MembershipDTO mDTO = null;
                if (m != null) {
                    mDTO = new MembershipDTO();
                    mDTO.setId(m.getId());
                    mDTO.setFirstName(m.getFirstName());
                    mDTO.setLastName(m.getLastName());
                }
                pDTO.setPublisher(mDTO);
            }
        }
        return pDTO;
    }

//    @GET
//    @Produces({"application/xml", "application/json"})
//    public List<Photo> findAll() {
//        return super.findAll();
//    }

//    @GET
//    @Path("{from}/{to}")
//    @Produces({"application/xml", "application/json"})
//    public List<Photo> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
//        return super.findRange(new int[]{from, to});
//    }

//    @GET
//    @Path("count")
//    @Produces("text/plain")
//    public String countREST() {
//        return String.valueOf(super.count());
//    }

    protected EntityManager getEntityManager() {
        return em;
    }
    
}
