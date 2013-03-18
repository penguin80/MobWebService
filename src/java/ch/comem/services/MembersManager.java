package ch.comem.services;

import ch.comem.model.Comment;
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
public class MembersManager implements MembersManagerLocal {
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;
    
    private void setMemberParameters(Membership m, String firstName, 
                                        String lastName, int age, String pseudo, 
                                        String email) {
        m.setFirstName(firstName);
        m.setLastName(lastName);
        m.setAge(age);
        m.setPseudo(pseudo);
        m.setEmail(email);
    }

    @Override
    public long createMember(String firstName, String lastName, int age, 
                              String pseudo, String email) {
        Membership member = new Membership();
        setMemberParameters(member, firstName, lastName, age, pseudo, email);
        persist(member);
        em.flush();
        return member.getId();
    }
    
    @Override
    public String deleteMember(Long id) {
        String str = "";
        Membership m = em.find(Membership.class, id);
        if (m != null) {
            em.remove(m);
            str = str.concat("Membre supprimé!");
        } else {
            str = str.concat("Impossible de supprimer le membre demandé!");
        }
        return str;
    }

    @Override
    public String modifyMember(Long id, String firstName, String lastName, 
                                int age, String pseudo, String email) {
        String str = "";
        Membership m  = em.find(Membership.class, id);
        if (m != null) {
            setMemberParameters(m, firstName, lastName, age, pseudo, email);
            persist(m);
            em.flush();
            str = str.concat("Membre modifié!");
        } else {
            str = str.concat("Impossible de modifier le membre demandé!");
        }
        return str;
    }
    
    @Override
    public void chooseLike(Long id, Long likeId) {
        Membership m = em.find(Membership.class, id);
        if (m != null) {
            Liking l = em.find(Liking.class, likeId);
            if (l != null) {
                m.addLike(l);
                persist(m);
                em.flush();
            }
        }
    }

    @Override
    public String postComment(Long id, Long commentId) {
        String str = "";
        Membership m = em.find(Membership.class, id);
        if (m != null) {
            Comment c = em.find(Comment.class, commentId);
            if (c != null) {
                m.addComment(c);
                persist(m);
                em.flush();
                str = str.concat("Votre commentaire est publié");
            } else
                str = str.concat("Commentaire inexistant!");
        } else
            str = str.concat("Impossible de publier votre commentaire!");
        return str;
    }

    @Override
    public String ownPublication(Long id, Long publicationId) {
        String str = "";
        Membership m = em.find(Membership.class, id);
        if (m != null) {
            Publication p = em.find(Publication.class, publicationId);
            if (p != null) {
                m.addPublication(p);
                persist(m);
                em.flush();
                str = str.concat("Vous avez publié une recette");
            } else
                str = str.concat("Publication inexistante!");
        } else
            str = str.concat("Impossible de publier votre recette!");
        return str;
    }
        
    public void persist(Object object) {
        em.persist(object);
    }

}
