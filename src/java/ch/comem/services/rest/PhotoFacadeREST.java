package ch.comem.services.rest;

import ch.comem.model.Photo;
import ch.comem.services.beans.PhotosManagerLocal;
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
    public Photo find(@PathParam("id") Long id) {
        return getEntityManager().find(Photo.class, id);
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
