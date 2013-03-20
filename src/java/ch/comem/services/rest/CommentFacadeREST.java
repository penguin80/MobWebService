package ch.comem.services.rest;

import ch.comem.model.Comment;
import ch.comem.services.beans.CommentsManagerLocal;
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
@Path("comments")
public class CommentFacadeREST {
    @EJB
    private CommentsManagerLocal cm;
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Comment entity) {
        cm.createComment(entity.getTexte(), entity.getPublication());
    }

//    @PUT
//    @Consumes({"application/xml", "application/json"})
//    public void edit(Comment entity) {}

//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Long id) {}

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Comment find(@PathParam("id") Long id) {
        return getEntityManager().find(Comment.class, id);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Comment> findAll() {
        return cm.findAllComments();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Comment> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        List<Comment> allComments = cm.findAllComments();
        List<Comment> subSelection = allComments.subList(from.intValue(), 
                                                         to.intValue());
        return subSelection;
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(cm.findAllComments().size());
    }

    protected EntityManager getEntityManager() {
        return em;
    }
    
}
