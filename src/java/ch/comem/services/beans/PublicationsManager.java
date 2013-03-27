package ch.comem.services.beans;

import ch.comem.model.Category;
import ch.comem.model.Membership;
import ch.comem.model.Photo;
import ch.comem.model.Publication;
import ch.comem.model.Recipie;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
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
    
    private String buildDate(Publication p) {
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
        if (p.getId() == null)
            p.setLongDate(c.getTimeInMillis());
        return str;
    }
    
    private void setPublicationParameters(Publication p, Long photoId, 
                                             Long categoryId, Long recipieId) {
        Photo photo = em.find(Photo.class, photoId);
        if (photo != null) {
            p.setImagingPhoto(photo);
            photo.setPublicationConcerned(p);
        }
        Category categoryConcerned = em.find(Category.class, categoryId);
        if (categoryConcerned != null) {
            p.setCategory(categoryConcerned);
            categoryConcerned.addPublication(p);
        }
        Recipie r = em.find(Recipie.class, recipieId);
        if (r != null)
            p.setRecepie(r);
    }

    @Override
    public Long createPublication(Long memberId, Long photoId, Long categoryId, 
                                    Long recipieId) {
        Membership m = em.find(Membership.class, memberId);
        Long id = null;
        if (m != null) {
            Publication p = new Publication();
            p.setDateOfPublication(buildDate(p));
            setPublicationParameters(p, photoId, categoryId, recipieId);
            p.setPublisher(m);
            m.addPublication(p);
            persist(p);
            em.flush();
            id = p.getId();
        }
        return id;
    }
    
    @Override
    public Long modifyPublication(Long publicationId, Long recipieId) {
        Publication p = em.find(Publication.class, publicationId);
        Long id = null;
        if (p != null) {
            Recipie r = em.find(Recipie.class, recipieId);
            if (r != null)
                p.setRecepie(r);
            persist(p);
            em.flush();
            id = p.getId();
        }
        return id;
    }

    @Override
    public List<Publication> findAllPublications() {
        return em.createNamedQuery("findAllPublications").getResultList();
    }
    
    @Override
    public List<Publication> findLatestPublications() {
        return em.createNamedQuery("findLatestPublications").setMaxResults(5).getResultList();
    }

    @Override
    public List<Publication> findPublicationsFromRecipieName(String name) {
        return em.createNamedQuery("findPublicationsByRecipieName").setParameter("name", name).getResultList();
    }

    public void persist(Object object) {
        em.persist(object);
    }
    
}
