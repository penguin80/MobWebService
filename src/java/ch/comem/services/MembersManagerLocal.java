/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.services;

import javax.ejb.Local;

/**
 *
 * @author Pierre-Alexandre
 */
@Local
public interface MembersManagerLocal {

    Long createMember(String firstName, String lastName, int age, 
                       String pseudo, String email);

    String deleteMember(Long id);

    String modifyMember(Long id, String firstName, String lastName, int age, 
                         String pseudo, String email);
    
    void chooseLike(Long id, Long likeId);
    
    String postComment(Long id, Long commentId);
    
    String ownPublication(Long id, Long publicationId);
    
}
