package ch.comem.services.rest;

import ch.comem.model.Category;
import ch.comem.model.Photo;
import ch.comem.model.Publication;
import ch.comem.model.Recipie;
import ch.comem.services.beans.CategoriesManagerLocal;
import ch.comem.services.dto.CategoryDTO;
import ch.comem.services.dto.PhotoDTO;
import ch.comem.services.dto.PublicationDTO;
import ch.comem.services.dto.RecipieDTO;
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
@Path("categories")
public class CategoryFacadeREST {
    @EJB
    private CategoriesManagerLocal cm;
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Category entity) {
        cm.createCategory(entity.getName());
    }

//    @PUT
//    @Consumes({"application/xml", "application/json"})
//    public void edit(Category entity) {}

//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Long id) {}

    @GET
    @Path("{name}")
    @Produces({"application/xml", "application/json"})
    public List<PublicationDTO> find(@PathParam("name") String name) {
        List<Publication> pList = cm.findAllPublicationsFromCategoryName(name);
        List<PublicationDTO> pDTOList = null;
        if (pList != null && !pList.isEmpty()) {
            pDTOList = new ArrayList<>();
            for (Publication p : pList) {
                PublicationDTO pDTO = new PublicationDTO();
                pDTO.setId(p.getId());
                Photo ph = p.getImagingPhoto();
                PhotoDTO phDTO = new PhotoDTO();
                phDTO.setSource(ph.getSource());
                phDTO.setAlt(ph.getAlt());
                pDTO.setImagingPhoto(phDTO);
                CategoryDTO cDTO = new CategoryDTO();
                cDTO.setName(name);
                pDTO.setCategory(cDTO);
                Recipie r = p.getRecepie();
                RecipieDTO rDTO = null;
                if (r != null) {
                    rDTO = new RecipieDTO();
                    rDTO.setName(r.getName());
                }
                pDTO.setRecepie(rDTO);
                pDTOList.add(pDTO);
            }
        }
        return pDTOList;
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<PublicationDTO> findAll() {
        List<Category> cList = cm.findAllCategories();
        List<PublicationDTO> pDTOList = null;
        if (cList != null && !cList.isEmpty()) {
            for (Category c : cList) {
                CategoryDTO cDTO = new CategoryDTO();
                cDTO.setName(c.getName());
                List<Publication> pList = c.getCategorizedPublications();
                if (pList != null && !pList.isEmpty()) {
                    pDTOList = new ArrayList<>();
                    for (Publication p : pList) {
                        PublicationDTO pDTO = new PublicationDTO();
                        pDTO.setId(p.getId());
                        pDTO.setCategory(cDTO);
                        Photo ph = p.getImagingPhoto();
                        PhotoDTO phDTO = new PhotoDTO();
                        phDTO.setSource(ph.getSource());
                        phDTO.setAlt(ph.getAlt());
                        pDTO.setImagingPhoto(phDTO);
                        Recipie r = p.getRecepie();
                        RecipieDTO rDTO = null;
                        if (r != null) {
                            rDTO = new RecipieDTO();
                            rDTO.setName(r.getName());
                        }
                        pDTO.setRecepie(rDTO);
                        pDTOList.add(pDTO);
                    }
                }
            }
        }
        return pDTOList;
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(cm.findAllCategories().size());
    }

    protected EntityManager getEntityManager() {
        return em;
    }
    
}
