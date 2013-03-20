/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.services.beans;

import ch.comem.model.Step;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Pierre-Alexandre
 */
@Stateless
public class StepsManager implements StepsManagerLocal {
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;

    private void setStepParameters(Step s, int stepNumber, String description) {
        s.setStepNumber(stepNumber);
        s.setDescription(description);
    }
    
    @Override
    public Long createStep(int stepNumber, String description) {
        Step s = new Step();
        setStepParameters(s, stepNumber, description);
        persist(s);
        em.flush();
        return s.getId();
    }
    
    @Override
    public String modifyStep(Long stepId, int stepNumber, String description) {
        String str = "";
        Step s = em.find(Step.class, stepId);
        if (s != null) {
            setStepParameters(s, stepNumber, description);
            persist(s);
            em.flush();
            str = str.concat("Etape modifiée!");
        } else
            str = str.concat("Impossible de modifier l'étape demandée!");
        return str;
    }

    @Override
    public String deleteStep(Long stepId) {
        String str = "";
        Step s = em.find(Step.class, stepId);
        if (s != null) {
            em.remove(s);
            str = str.concat("Etape supprimée!");
        } else
            str = str.concat("Impossible de supprimer l'étape demandée!");
        return str;
    }

    @Override
    public List<Step> findAllSteps() {
        return em.createNamedQuery("findAllSteps").getResultList();
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
