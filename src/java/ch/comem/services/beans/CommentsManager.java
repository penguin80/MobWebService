/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.services.beans;

import ch.comem.model.Comment;
import ch.comem.model.Publication;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author raphaelbaumann
 */
@Stateless
public class CommentsManager implements CommentsManagerLocal {
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;
    
    @Override
    public Long createComment(String text, Publication publication) {
        Comment comment = new Comment();
        comment.setTexte(text);
        comment.addPublication(publication);
        persist(comment);
        em.flush();
        return comment.getId();
    }
    
    @Override
    public List<Comment> findAllComments() {
        return em.createNamedQuery("findAllComments").getResultList();
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
