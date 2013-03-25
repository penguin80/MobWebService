/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @NamedQuery(name="findLatestPublications", query="SELECT p FROM Publication p ORDER BY p.longDate DESC"),
    @NamedQuery(name="findPublicationsByRecipieName",
                query="SELECT p FROM Publication p JOIN p.recepie r WHERE r.name = :name")
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
    @NotNull
    private long longDate;
    @OneToOne(fetch=FetchType.LAZY)
    private Photo imagingPhoto;
    @OneToOne(fetch=FetchType.LAZY)
    private Recipie recepie;
    @OneToMany(mappedBy="publicationCom", fetch=FetchType.LAZY)
    private List<Comment> comment = new ArrayList<>();
    @OneToMany(mappedBy="publicationLiked", fetch=FetchType.LAZY)
    private List<Liking> likes = new ArrayList<>();
    @ManyToOne(fetch=FetchType.LAZY)
    private Category category;

    public String getDateOfPublication() {
        return dateOfPublication;
    }

    public long getLongDate() {
        return longDate;
    }

    public Photo getImagingPhoto() {
        return imagingPhoto;
    }

    public Recipie getRecepie() {
        return recepie;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public List<Liking> getLikes() {
        return likes;
    }

    public Category getCategory() {
        return category;
    }

    public void setDateOfPublication(String dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }
    
    public void setLongDate(long longDate) {
        this.longDate = longDate;
    }

    public void setImagingPhoto(Photo imagingPhoto) {
        this.imagingPhoto = imagingPhoto;
    }

    public void setRecepie(Recipie recepie) {
        this.recepie = recepie;
    }

    public void addComment(Comment comment) {
        getComment().add(comment);
        comment.addPublication(this);
    }

    public void addLike(Liking like) {
        getLikes().add(like);
        like.setPublication(this);
    }

    public void setCategory(Category category) {
        this.category = category;
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
