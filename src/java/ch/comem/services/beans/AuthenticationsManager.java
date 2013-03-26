package ch.comem.services.beans;

import ch.comem.model.Authentication;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Pierre-Alexandre
 */
@Stateless
public class AuthenticationsManager implements AuthenticationsManagerLocal {
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;
    
    private String setAccountParameters(Authentication a, String password) {
        a.setPassword(password);
        persist(a);
        em.flush();
        return a.getEmail();
    }
    
    @Override
    public String createAccount(String email, String password) {
        Authentication newA = new Authentication();
        newA.setEmail(email);
        return setAccountParameters(newA, password);
    }
    
    @Override
    public Long modifyAccount(String email, String newPassword) {
        Long id = null;
        Authentication prevA = em.find(Authentication.class, email);
        if (prevA != null) {
            String emailStored = setAccountParameters(prevA, newPassword);
            id = findMemberIdFromEmail(emailStored);
        }
        return id;
    }

    @Override
    public Long login(String email, String password) {
        Long id = null;
        Authentication a = em.find(Authentication.class, email);
        if (a != null) {
            if (a.getPassword().equals(password))
                id = findMemberIdFromEmail(email);
        }
        return id;
    }

    @Override
    public Long findMemberIdFromEmail(String email) {
        return (Long) em.createNamedQuery("findMemberIdFromEmail").setParameter("email", email).getSingleResult();
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
