/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author raphaelbaumann
 */
@Entity
@XmlRootElement
public class Membership implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private int age;
    private String pseudo;
//    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*"
//                    +"@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
//                    message="{invalid.email}")
    protected String email;

    @OneToMany(mappedBy="memberInvolved")
    private Collection<Publication> publicationsConcerned = new ArrayList<>();
    @OneToMany(mappedBy="memberCommenting")
    private Collection<Comment> commentsConcerned = new ArrayList<>();
    @OneToMany(mappedBy="memberLiking")
    private Collection<Liking> likesConcerned = new ArrayList<>();
    
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

    @XmlTransient
    @JsonIgnore
    public Collection<Publication> getPublicationsConcerned() {
        return publicationsConcerned;
    }

    public void addPublication(Publication publication) {
        getPublicationsConcerned().add(publication);
        publication.setMemberInvolved(this);
    }

    @XmlTransient
    @JsonIgnore
    public Collection<Comment> getCommentsConcerned() {
        return commentsConcerned;
    }

    public void addComment(Comment comment) {
        getCommentsConcerned().add(comment);
        comment.setMemberCommenting(this);
    }

    @XmlTransient
    @JsonIgnore
    public Collection<Liking> getLikesConcerned() {
        return likesConcerned;
    }

    public void addLike(Liking like) {
        getLikesConcerned().add(like);
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
        if (!(object instanceof Membership)) {
            return false;
        }
        Membership other = (Membership) object;
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
