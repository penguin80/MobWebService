/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.services.beans;

import javax.ejb.Local;

/**
 *
 * @author raphaelbaumann
 */
@Local
public interface PhotosManagerLocal {

    Long createPhoto(String source, String alt);
    
}
