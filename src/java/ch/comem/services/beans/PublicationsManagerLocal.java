package ch.comem.services.beans;

import ch.comem.model.Publication;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Pierre-Alexandre
 */
@Local
public interface PublicationsManagerLocal {
    
    Long createPublication(Long photoId, Long categoryId, Long recipieId);
    
    String modifyPublication(Long publicationId, Long categoryId, Long recipieId);
    
    String addComment(Long publicationId, Long commentId);
    
    String addLike(Long publicationId, Long likeId);

    List<Publication> findAllPublications();

    List<Publication> findPublicationsFromRecipieName(String name);
}
