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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @NamedQuery(name="findAllPublications", query="SELECT p FROM Publication p"),
    @NamedQuery(name="findPhotoByPublicationId", 
                query="SELECT ph FROM Publication p JOIN p.imagingPhoto ph WHERE p.id = :id"),
    @NamedQuery(name="findNameByPublicationId",
                query="SELECT r FROM Publication p JOIN p.recepie r WHERE p.id = :id")
})
@Entity
@XmlRootElement
public class Publication implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String dateOfPublication;
    private String dateOfLastPublication;
    @OneToOne
    @JoinColumn(name="PHOTOID", referencedColumnName="ID")
    private Photo imagingPhoto;
    @OneToOne
    private Recipie recepie;
    @OneToMany(mappedBy="publicationCom")
    private Collection<Comment> comment = new ArrayList<>();
    @ManyToOne
    private Category categoryConcerned;
    @ManyToOne
    private Membership memberInvolved;
    @OneToMany(mappedBy="publicationLiked")
    private Collection<Liking> likes = new ArrayList<>();

    public String getDateOfPublication() {
        return dateOfPublication;
    }

    public Photo getImagingPhoto() {
        return imagingPhoto;
    }

    public void setImagingPhoto(Photo imagingPhoto) {
        this.imagingPhoto = imagingPhoto;
    }

    public void setDateOfPublication(String dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public String getDateOfLastPublication() {
        return dateOfLastPublication;
    }

    public void setDateOfLastPublication(String dateOfLastPublication) {
        this.dateOfLastPublication = dateOfLastPublication;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipie getRecepie() {
        return recepie;
    }

    public void setRecepie(Recipie recepie) {
        this.recepie = recepie;
    }

    public Collection<Comment> getComment() {
        return comment;
    }

    public void addComment(Comment comment) {
        getComment().add(comment);
        comment.addPublication(this);
    }

    public Category getCategoryConcerned() {
        return categoryConcerned;
    }

    public void setCategoryConcerned(Category categoryConcerned) {
        this.categoryConcerned = categoryConcerned;
    }

    public Membership getMemberInvolved() {
        return memberInvolved;
    }

    public void setMemberInvolved(Membership memberInvolved) {
        this.memberInvolved = memberInvolved;
    }

    public Collection<Liking> getLikes() {
        return likes;
    }

    public void addLike(Liking like) {
        getLikes().add(like);
        like.setPublication(this);
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
        if (!(object instanceof Publication)) {
            return false;
        }
        Publication other = (Publication) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.comem.model.Publication[ id=" + id + " ]";
    }
    
}
