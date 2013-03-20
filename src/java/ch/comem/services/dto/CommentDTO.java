package ch.comem.services.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonAutoDetect;

/**
 *
 * @author raphaelbaumann
 */
@XmlRootElement(name="comment")
public class CommentDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String texte;
    private PublicationDTO publicationCom;
    private MembershipDTO memberCommenting;

//    public String getTexte() {
//        return texte;
//    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

//    public PublicationDTO getPublication() {
//        return publicationCom;
//    }

    public void addPublication(PublicationDTO publication) {
        this.publicationCom = publication;
    }
    
//    public MembershipDTO getMemberCommenting() {
//        return memberCommenting;
//    }

    public void setMemberCommenting(MembershipDTO memberCommenting) {
        this.memberCommenting = memberCommenting;
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
        if (!(object instanceof CommentDTO)) {
            return false;
        }
        CommentDTO other = (CommentDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.comem.model.Comment[ id=" + id + " ]";
    }
    
}
