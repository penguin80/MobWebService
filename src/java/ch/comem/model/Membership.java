/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author raphaelbaumann
 */
@NamedQueries({
    @NamedQuery(name="findAllMembers", query="SELECT m FROM Membership m"),
    @NamedQuery(name="findAllPublicationsFromMemberId", 
                query="SELECT p FROM Membership m JOIN m.publicationsConcerned p WHERE m.id = :id")
})
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

    @OneToMany(mappedBy="publisher", fetch=FetchType.LAZY)    
    private List<Publication> publicationsConcerned = new ArrayList<>();
    @OneToMany(mappedBy="memberCommenting", fetch=FetchType.LAZY)
    private List<Comment> commentsConcerned = new ArrayList<>();
    @OneToMany(mappedBy="memberLiking", fetch=FetchType.LAZY)
    private List<Liking> likesConcerned = new ArrayList<>();
    @OneToOne(cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
    private Authentication authenticate;
    
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

    public List<Publication> getPublicationsConcerned() {
        return publicationsConcerned;
    }

    public void addPublication(Publication publication) {
        getPublicationsConcerned().add(publication);
    }
    
    public List<Comment> getCommentsConcerned() {
        return commentsConcerned;
    }

    public void addComment(Comment comment) {
        getCommentsConcerned().add(comment);
        comment.setMemberCommenting(this);
    }

    public List<Liking> getLikesConcerned() {
        return likesConcerned;
    }

    public void addLike(Liking like) {
        getLikesConcerned().add(like);
        like.setMemberLiking(this);
    }

    public Authentication getAuthenticate() {
        return authenticate;
    }

    public void setAuthenticate(Authentication autenticate) {
        this.authenticate = autenticate;
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
