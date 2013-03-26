package ch.comem.services.beans;

import ch.comem.model.Authentication;
import ch.comem.model.Comment;
import ch.comem.model.Liking;
import ch.comem.model.Membership;
import ch.comem.model.Photo;
import ch.comem.model.Publication;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Pierre-Alexandre
 */
@Stateless
public class MembersManager implements MembersManagerLocal {
    @EJB
    private AuthenticationsManagerLocal am;
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;
    
    private void setMemberParameters(Membership m, Authentication a, 
                                        String firstName, String lastName, 
                                        int age, String pseudo, String email, 
                                        String password) {
        m.setFirstName(firstName);
        m.setLastName(lastName);
        m.setAge(age);
        m.setPseudo(pseudo);
        m.setEmail(email);
        a.setPassword(password);
        m.setAuthenticate(a);
        a.setAuthenticatedMember(m);
    }

    @Override
    public Long createMember(String firstName, String lastName, int age, 
                              String pseudo, String email, String password) {
        Membership member = new Membership();
        Authentication a = em.find(Authentication.class, email);
        if (a == null)
            a = new Authentication();
        a.setEmail(email);
        setMemberParameters(member, a, firstName, lastName, age, pseudo, email, 
                            password);
        persist(member);
        em.flush();
        return member.getId();
    }
    
    @Override
    public String deleteMember(Long id) {
        String str = "";
        Membership m = em.find(Membership.class, id);
        if (m != null) {
            Authentication a = m.getAuthenticate();
            em.remove(a);
            em.remove(m);
            str = str.concat("Membre supprimé!");
        } else {
            str = str.concat("Impossible de supprimer le membre demandé!");
        }
        return str;
    }

    @Override
    public Long modifyMember(Long id, String firstName, String lastName, 
                                int age, String pseudo, String email, 
                                String password) {
        Long idModifiedMember = null;
        Membership m  = em.find(Membership.class, id);
        if (m != null) {
            Authentication a = m.getAuthenticate();
            setMemberParameters(m, a, firstName, lastName, age, pseudo, email, 
                                password);
            persist(m);
            em.flush();
            idModifiedMember = m.getId();
        }
        return idModifiedMember;
    }
    
    @Override
    public List<Membership> findAllMembers() {
        return em.createNamedQuery("findAllMembers").getResultList();
    }

    @Override
    public List<Photo> findAllPhotosFromMemberId(Long memberId) {
        List<Publication> puList = em.createNamedQuery("findAllPublicationsFromMemberId").setParameter("id", memberId).getResultList();
        List<Photo> phList = null;
        if (puList != null && !puList.isEmpty()) {
            phList = new ArrayList<>();
            for (Publication pu : puList) {
                Photo ph = new Photo();
                ph.setId(pu.getImagingPhoto().getId());
                ph.setSource(pu.getImagingPhoto().getSource());
                ph.setAlt(pu.getImagingPhoto().getAlt());
                phList.add(ph);
            }
        }
        return phList;
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
