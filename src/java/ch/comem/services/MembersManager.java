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
        persist(member);
        em.flush();
        return member.getId();
    }
    
    @Override
    public String deleteMember(Long id) {
        Member m = em.find(Member.class, id);
        em.remove(m);
        return "Membre supprimé";
    }

    @Override
    public String modifyMember(Long id, String firstName, String lastName, int age) {
        Member m = em.find(Member.class, id);
        m.setFirstName(firstName);
        m.setLastName(lastName);
        m.setAge(age);
        persist(m);
        em.flush();
        return "Membre modifié";
    }
    
    
    public void persist(Object object) {
        em.persist(object);
    }
    
}
