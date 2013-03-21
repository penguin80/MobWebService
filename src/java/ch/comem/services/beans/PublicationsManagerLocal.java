package ch.comem.services.beans;

import ch.comem.model.Photo;
import ch.comem.model.Publication;
import ch.comem.model.Recipie;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Pierre-Alexandre
 */
@Local
public interface PublicationsManagerLocal {
    
    Long createPublication(Long photoId, Long categoryId, Long publisherId, 
                             Long recipieId);
    
    String modifyPublication(Long publicationId, Long photoId,
                               Long categoryId, Long recipieId);
    
    String addComment(Long publicationId, Long commentId);
    
    String addLike(Long publicationId, Long likeId);

    List<Publication> findAllPublications();

    Photo findPhotoFromPublicationId(Long publicationId);

    Recipie findRecipieFromPublicationId(Long publicationId);
}
