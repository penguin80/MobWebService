/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.services;

import javax.ejb.Local;

/**
 *
 * @author Pierre-Alexandre
 */
@Local
public interface MembersManagerLocal {

    long createMember(String firstName, String lastName, int age);

    String deleteMember(Long id);

    String modifyMember(Long id, String firstName, String lastName, int age);
    
}
