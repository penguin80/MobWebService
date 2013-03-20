package ch.comem.services.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonAutoDetect;

/**
 *
 * @author raphaelbaumann
 */
@XmlRootElement(name="likingS")
public class LikingDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private boolean status;
    private PublicationDTO publicationLiked;
    private MembershipDTO memberLiking;

//    public boolean isStatus() {
//        return status;
//    }

    public void setStatus(boolean status) {
        this.status = status;
    }

//    public PublicationDTO getPublication() {
//        return publicationLiked;
//    }

    public void setPublication(PublicationDTO publication) {
        this.publicationLiked = publication;
    }

//    public MembershipDTO getMemberLiking() {
//        return memberLiking;
//    }

    public void setMemberLiking(MembershipDTO memberLiking) {
        this.memberLiking = memberLiking;
    }

//    public Long getId() {
//        return id;
//    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LikingDTO)) {
            return false;
        }
        LikingDTO other = (LikingDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.comem.model.Like[ id=" + id + " ]";
    }
    
}
