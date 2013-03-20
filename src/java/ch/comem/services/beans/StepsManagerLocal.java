/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.services.beans;

import ch.comem.model.Step;
import java.util.List;
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

    List<Step> findAllSteps();
    
}
