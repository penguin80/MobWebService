package ch.comem.services.beans;

import ch.comem.model.Membership;
import ch.comem.model.Publication;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Pierre-Alexandre
 */
@Local
public interface MembersManagerLocal {

    Long createMember(String firstName, String lastName, int age, 
                       String pseudo, String email, String password);

    String deleteMember(Long id);

    Long modifyMember(Long id, String firstName, String lastName, int age, 
                         String pseudo, String email, String password);
        
    List<Membership> findAllMembers();

    List<Publication> findAllPublicationsFromMemberId(Long memberId);
    
}
