package ch.comem.services.rest;

import ch.comem.model.Membership;
import ch.comem.services.MembersManagerLocal;
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
@Path("ch.comem.model.membership")
public class MembershipFacadeREST {
    @EJB
    private MembersManagerLocal mm;
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Membership entity) {
        mm.createMember(entity.getFirstName(), entity.getLastName(), 
                        entity.getAge(), entity.getPseudo(), entity.getEmail());
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public void edit(Membership entity) {
        mm.modifyMember(entity.getId(), entity.getFirstName(), 
                        entity.getLastName(), entity.getAge(), 
                        entity.getPseudo(), entity.getEmail());
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        mm.deleteMember(id);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Membership find(@PathParam("id") Long id) {
        return getEntityManager().find(Membership.class, id);
    }

//    @GET
//    @Produces({"application/xml", "application/json"})
//    public List<Membership> findAll() {
//        return super.findAll();
//    }

//    @GET
//    @Path("{from}/{to}")
//    @Produces({"application/xml", "application/json"})
//    public List<Membership> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
