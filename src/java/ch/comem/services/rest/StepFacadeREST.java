package ch.comem.services.rest;

import ch.comem.model.Step;
import ch.comem.services.beans.StepsManagerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
@Path("steps")
public class StepFacadeREST {
    @EJB
    private StepsManagerLocal sm;
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;

    @POST
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public void create(Step entity) {
        sm.createStep(entity.getStepNumber(), entity.getDescription());
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public void edit(Step entity) {
        sm.modifyStep(entity.getId(), entity.getStepNumber(), 
                      entity.getDescription());
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        sm.deleteStep(id);
    }

    protected EntityManager getEntityManager() {
        return em;
    }
    
}
