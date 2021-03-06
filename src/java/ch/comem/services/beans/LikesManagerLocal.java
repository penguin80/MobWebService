package ch.comem.services.beans;

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
