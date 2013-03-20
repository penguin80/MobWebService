package ch.comem.services.rest;

import ch.comem.model.Category;
import ch.comem.services.beans.CategoriesManagerLocal;
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
@Path("ch.comem.model.category")
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
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Category find(@PathParam("id") Long id) {
        return getEntityManager().find(Category.class, id);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Category> findAll() {
        return cm.findAllCategories();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Category> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        List<Category> allCategories = cm.findAllCategories();
        List<Category> subSelection = allCategories.subList(from.intValue(), 
                                                            to.intValue());
        return subSelection;
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
