package ch.comem.services.beans;

import javax.ejb.Local;

/**
 *
 * @author Pierre-Alexandre
 */
@Local
public interface AuthenticationsManagerLocal {

    String createAccount(String email, String password);

    Long login(String email, String password);

    Long findMemberIdFromEmail(String email);

    Long modifyAccount(String email, String newPassword);
    
}
