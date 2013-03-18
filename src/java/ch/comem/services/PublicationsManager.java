package ch.comem.services;

import ch.comem.model.Category;
import ch.comem.model.Membership;
import ch.comem.model.Photo;
import ch.comem.model.Publication;
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

    @Override
    public Long createPublication(Photo photo, Category categoryConcerned, 
                                    Membership publisher) {
        Publication p = new Publication();
        p.setDateOfPublication(buildDate());
        p.setPhoto(photo);
        return null;
    }

    
}
