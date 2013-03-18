package ch.comem.services;

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

    Long createPublication(Photo photo, Category categoryConcerned, 
                             Membership publisher);
    
}
