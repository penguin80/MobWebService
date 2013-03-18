package ch.comem.services;

import ch.comem.model.Category;
import ch.comem.model.Comment;
import ch.comem.model.Liking;
import ch.comem.model.Membership;
import ch.comem.model.Photo;
import ch.comem.model.Publication;
import ch.comem.model.Recipie;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Pierre-Alexandre
 */
@Stateless
public class PublicationsManager implements PublicationsManagerLocal {
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;
    
    private String buildDate() {
        String str = "";
        Calendar c = GregorianCalendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        String mString = "";
        int month = c.get(Calendar.MONTH) + 1;
        switch(month) {
            case 1 :
                mString = "janvier";
                break;
            case 2 :
                mString = "février";
                break;
            case 3 :
                mString = "mars";
                break;
            case 4 :
                mString = "avril";
                break;
            case 5 :
                mString = "mai";
                break;
            case 6 :
                mString = "juin";
                break;
            case 7 :
                mString = "juillet";
                break;
            case 8 :
                mString = "août";
                break;
            case 9 :
                mString = "septembre";
                break;
            case 10 :
                mString = "octobre";
                break;
            case 11 :
                mString = "novembre";
                break;
            case 12 :
                mString = "décembre";
                break;
        }
        int year = c.get(Calendar.YEAR);
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        int seconds = c.get(Calendar.SECOND);
        str = str.concat(day + " " + mString + " " + year + " à " + hours + ":" + 
                           minutes  + ":" + seconds);
        System.out.println(str);
        return str;
    }
    
    private void setPublicationParameters(Publication p, Long photoId, 
                                             Long categoryId, Long publisherId) {
        p.setDateOfPublication(buildDate());
        p.setDateOfLastPublication(null);
        Photo photo = em.find(Photo.class, photoId);
        if (photo != null)
            p.setImagingPhoto(photo);
        Category categoryConcerned = em.find(Category.class, categoryId);
        if (categoryConcerned != null)
            p.setCategoryConcerned(categoryConcerned);
        Membership publisher = em.find(Membership.class, publisherId);
        if (publisher != null)
            p.setMemberInvolved(publisher);
    }

    @Override
    public Long createPublication(Long photoId, Long categoryId, 
                                    Long publisherId) {
        Publication p = new Publication();
        setPublicationParameters(p, photoId, categoryId, publisherId);
        p.setRecepie(null);
        persist(p);
        em.flush();
        return p.getId();
    }

    @Override
    public Long createPublication(Long photoId, Long categoryId, 
                                    Long publisherId, Long recipieId) {
        Publication p = new Publication();
        setPublicationParameters(p, photoId, categoryId, publisherId);
        Recipie r = em.find(Recipie.class, recipieId);
        if (r != null)
            p.setRecepie(r);
        persist(p);
        em.flush();
        return p.getId();
    }
    
    @Override
    public String modifyPublication(Long publicationId, Long categoryId, 
                                      Long recipieId) {
        String str = "";
        Publication p = em.find(Publication.class, publicationId);
        if (p != null) {
            p.setDateOfLastPublication(buildDate());
            Category categoryConcerned = em.find(Category.class, categoryId);
            if (categoryConcerned != null)
                p.setCategoryConcerned(categoryConcerned);
            Recipie r = em.find(Recipie.class, recipieId);
            if (r != null)
                p.setRecepie(r);
            persist(p);
            em.flush();
            str = str.concat("Publication modifiée");
        } else
            str = str.concat("Impossible de modifier la publication demandée!");
        return str;
    }

    @Override
    public String addComment(Long publicationId, Long commentId) {
        String str = "";
        Publication p = em.find(Publication.class, publicationId);
        if (p != null) {
            Comment c = em.find(Comment.class, commentId);
            if (c != null) {
                p.addComment(c);
                persist(p);
                em.flush();
                str = str.concat("Commentaire ajouté");
            } else
                str = str.concat("Commentaire inexistant!");
        } else
            str = str.concat("Impossible d'ajouter un commentaire à la " + 
                             "publication demandée!");
        return str;
    }

    @Override
    public String addLike(Long publicationId, Long likeId) {
        String str = "";
        Publication p = em.find(Publication.class, publicationId);
        if (p != null) {
            Liking l = em.find(Liking.class, likeId);
            if (l != null) {
                p.addLike(l);
                persist(p);
                em.flush();
                str = str.concat("Like ajouté");
            } else
                str = str.concat("Like inexistant!");
        } else
            str = str.concat("Impossible d'ajouter un like à la publication demandée!");
        return str;
    }

    public void persist(Object object) {
        em.persist(object);
    }
    
}
