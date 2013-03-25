package ch.comem.services.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pierre-Alexandre
 */
@XmlRootElement(name="membership")
public class MembershipDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String pseudo;
//    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*"
//                    +"@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
//                    message="{invalid.email}")
    protected String email;

    private List<PublicationDTO> publicationsConcerned = new ArrayList<>();
    private List<CommentDTO> commentsConcerned = new ArrayList<>();
    private List<LikingDTO> likesConcerned = new ArrayList<>();
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    @XmlTransient
//    @JsonIgnore
    public List<PublicationDTO> getPublicationsConcerned() {
        return publicationsConcerned;
    }

    public void addPublication(PublicationDTO publication) {
        publicationsConcerned.add(publication);
    }

//    @XmlTransient
//    @JsonIgnore
    public List<CommentDTO> getCommentsConcerned() {
        return commentsConcerned;
    }

    public void addComment(CommentDTO comment) {
        commentsConcerned.add(comment);
        comment.setMemberCommenting(this);
    }

//    @XmlTransient
//    @JsonIgnore
    public List<LikingDTO> getLikesConcerned() {
        return likesConcerned;
    }

    public void addLike(LikingDTO like) {
        likesConcerned.add(like);
        like.setMemberLiking(this);
    }

    public Long getId() {
        return id;
    }

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
        if (!(object instanceof MembershipDTO)) {
            return false;
        }
        MembershipDTO other = (MembershipDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.comem.web.Member[ id=" + id + " ]";
    }
    
}
