/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.services.beans;

import ch.comem.model.Comment;
import ch.comem.model.Publication;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author raphaelbaumann
 */
@Local
public interface CommentsManagerLocal {
    
    Long createComment(String text, Publication publication);
    
    List<Comment> findAllComments();

}
