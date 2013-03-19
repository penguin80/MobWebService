package ch.comem.services.rest;

import ch.comem.model.Ingredient;
import ch.comem.model.Recipie;
import ch.comem.services.RecipieManagerLocal;
import java.util.ArrayList;
import java.util.Collection;
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
@Path("ch.comem.model.recipie")
public class RecipieFacadeREST {
    @EJB
    private RecipieManagerLocal rm;
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Recipie entity) {
        Collection<Ingredient> ingredients = entity.getIngredients();
        Collection<Long> ingredientIds = new ArrayList<>();
        for (Ingredient i : ingredients)
            ingredientIds.add(i.getId());
        rm.createRecipie(entity.getSteps(), ingredientIds);
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public void edit(Recipie entity) {
        Collection<Ingredient> ingredients = entity.getIngredients();
        Collection<Long> ingredientIds = new ArrayList<>();
        for (Ingredient i : ingredients)
            ingredientIds.add(i.getId());
        rm.modifyRecipie(entity.getId(), entity.getSteps(), ingredientIds);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        rm.deleteRecipie(id);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Recipie find(@PathParam("id") Long id) {
        return getEntityManager().find(Recipie.class, id);
    }

//    @GET
//    @Produces({"application/xml", "application/json"})
//    public List<Recipie> findAll() {
//        return super.findAll();
//    }

//    @GET
//    @Path("{from}/{to}")
//    @Produces({"application/xml", "application/json"})
//    public List<Recipie> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
