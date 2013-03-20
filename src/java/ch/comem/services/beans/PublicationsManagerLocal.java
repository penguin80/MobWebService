package ch.comem.services.beans;

import ch.comem.model.Category;
import ch.comem.model.Membership;
import ch.comem.model.Photo;
import javax.ejb.Local;

/**
 *
 * @author Pierre-Alexandre
 */
@Local
public interface PublicationsManagerLocal {

    Long createPublication(Long photoId, Long categoryId, Long publisherId);
    
    Long createPublication(Long photoId, Long categoryId, Long publisherId, 
                             Long recipieId);
    
    String modifyPublication(Long publicationId, Long categoryId, 
                               Long recipieId);
    
    String addComment(Long publicationId, Long commentId);
    
    String addLike(Long publicationId, Long likeId);
}
