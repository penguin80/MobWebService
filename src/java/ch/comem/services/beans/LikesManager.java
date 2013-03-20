package ch.comem.services.beans;

import ch.comem.model.Liking;
import ch.comem.model.Membership;
import ch.comem.model.Publication;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Pierre-Alexandre
 */
@Stateless
public class LikesManager implements LikesManagerLocal {
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;

    @Override
    public Long createLike(Long memberId, Long publicationId, boolean status) {
        Liking l = new Liking();
        Membership m = em.find(Membership.class, memberId);
        if (m != null)
            l.setMemberLiking(m);
        Publication p = em.find(Publication.class, publicationId);
        if (p != null)
            l.setPublication(p);
        l.setStatus(status);
        persist(l);
        em.flush();
        return l.getId();
    }
    
    @Override
    public void modifyLike(Long likeId) {
        Liking l = em.find(Liking.class, likeId);
        if (l != null) {
            boolean newStatus = !l.isStatus();
            l.setStatus(newStatus);
            persist(l);
            em.flush();
        }
    }
    
    public void persist(Object object) {
        em.persist(object);
    }
    
}
