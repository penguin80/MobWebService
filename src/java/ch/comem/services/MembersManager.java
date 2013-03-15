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
    public String deleteMember(Member m) {
        em.detach(m);
        em.remove(m);
        em.flush();
        return "Membre supprimé";
    }

    @Override
    public String modifyMember(Member m, String firstName, String lastName, int age) {
        em.detach(m);
        m.setFirstName(firstName);
        m.setLastName(lastName);
        m.setAge(age);
        em.flush();
        persist(m);
        return "Membre modifié";
    }
    
    
    public void persist(Object object) {
        em.persist(object);
    }
    
}
