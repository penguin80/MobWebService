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
    
    Long createPublication(Long memberId, Long photoId, Long categoryId, 
                             Long recipieId);
    
    Long modifyPublication(Long publicationId, Long recipieId);
    
    List<Publication> findAllPublications();

    List<Publication> findPublicationsFromRecipieName(String name);

    List<Publication> findLatestPublications();
}
