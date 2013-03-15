package ch.comem.services;

import ch.comem.model.Member;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Pierre-Alexandre
 */
@Stateless
public class MembersManager implements MembersManagerLocal {
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;

    @Override
    public long createMember(String firstName, String lastName, int age) {
        Member member = new Member();
        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setAge(age);
        em.flush();
        persist(member);
        return member.getId();
    }
    
    @Override
    public String deleteMember(Long id) {
        String str = "";
        Member m = em.find(Member.class, id);
        if (m != null) {
            em.remove(m);
            str = str.concat("Membre supprimé!");
        } else {
            str = str.concat("Impossible de supprimer le membre demandé!");
        }
        return str;
    }

    @Override
    public String modifyMember(Long id, String firstName, String lastName, int age) {
        String str = "";
        Member m  = em.find(Member.class, id);
        if (m != null) {
            m.setFirstName(firstName);
            m.setLastName(lastName);
            m.setAge(age);
            persist(m);
            em.flush();
            str = str.concat("Membre modifié!");
        } else {
            str = str.concat("Impossible de modifier le membre demandé!");
        }
        return str;
    }
    
    
    public void persist(Object object) {
        em.persist(object);
    }
    
}
