package ch.comem.services.rest;

import ch.comem.model.Publication;
import ch.comem.services.PublicationsManagerLocal;
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
@Path("ch.comem.model.publication")
public class PublicationFacadeREST {
    @EJB
    private PublicationsManagerLocal pm;
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Publication entity) {
        pm.createPublication(entity.getImagingPhoto().getId(), 
                             entity.getCategoryConcerned().getId(), 
                             entity.getMemberInvolved().getId(), 
                             entity.getRecepie().getId());
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public void edit(Publication entity) {
        pm.modifyPublication(entity.getId(),
                             entity.getCategoryConcerned().getId(), 
                             entity.getRecepie().getId());
    }

//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Long id) {}

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Publication find(@PathParam("id") Long id) {
        return getEntityManager().find(Publication.class, id);
    }

//    @GET
//    @Produces({"application/xml", "application/json"})
//    public List<Publication> findAll() {
//        return super.findAll();
//    }

//    @GET
//    @Path("{from}/{to}")
//    @Produces({"application/xml", "application/json"})
//    public List<Publication> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
