/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.services;

import ch.comem.model.Publication;
import javax.ejb.Local;

/**
 *
 * @author raphaelbaumann
 */
@Local
public interface CommentsManagerLocal {
    
    Long createComment(String text, Publication publication);
}
