package ch.comem.services.rest;

import ch.comem.model.Authentication;
import ch.comem.services.beans.AuthenticationsManagerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author Pierre-Alexandre
 */
@Stateless
@Path("authentications")
public class AuthenticationFacadeREST {
    @EJB
    private AuthenticationsManagerLocal am;
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;
    
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @POST
    @Consumes({"application/xml", "application/json"})
    public Long login(Authentication entity) {
        return am.login(entity.getEmail(), entity.getPassword());
    }

}
