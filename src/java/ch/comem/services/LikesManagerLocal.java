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
public interface LikesManagerLocal {

    Long createLike(Long memberId, Long publicationId, boolean status);
    
    void modifyLike(Long likeId);
    
}
