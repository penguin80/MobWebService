package ch.comem.services.beans;

import javax.ejb.Local;

/**
 *
 * @author Pierre-Alexandre
 */
@Local
public interface StepsManagerLocal {

    Long createStep(int stepNumber, String description);

    String modifyStep(Long stepId, int stepNumber, String description);

    String deleteStep(Long stepId);
    
}
