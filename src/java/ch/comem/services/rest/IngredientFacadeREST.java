package ch.comem.services.rest;

import ch.comem.model.Ingredient;
import ch.comem.services.IngredientsManagerLocal;
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
@Path("ch.comem.model.ingredient")
public class IngredientFacadeREST {
    @EJB
    private IngredientsManagerLocal im;
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Ingredient entity) {
        im.createIngredient(entity.getName(), entity.getQuantity(), 
                            entity.getQuantityUnit());
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public void edit(Ingredient entity) {
        im.modifyIngredient(entity.getId(), entity.getName(), 
                            entity.getQuantity(), entity.getQuantityUnit());
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        im.deleteIngredient(id);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Ingredient find(@PathParam("id") Long id) {
        return getEntityManager().find(Ingredient.class, id);
    }

//    @GET
//    @Produces({"application/xml", "application/json"})
//    public List<Ingredient> findAll() {
//        return super.findAll();
//    }

//    @GET
//    @Path("{from}/{to}")
//    @Produces({"application/xml", "application/json"})
//    public List<Ingredient> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
