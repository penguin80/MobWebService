/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.services;

import ch.comem.model.Photo;
import ch.comem.model.Publication;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author raphaelbaumann
 */
@Stateless
public class PhotosManager implements PhotosManagerLocal {
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;
    
    @Override
    public Long createPhoto(String source, String alt, Publication publication) {
        Photo photo = new Photo();
        photo.setSource(source);
        photo.setAlt(alt);
        photo.setPublication(publication);
        persist(photo);
        em.flush();
        return photo.getId();
    }

    public void persist(Object object) {
        em.persist(object);
    }

    

}
