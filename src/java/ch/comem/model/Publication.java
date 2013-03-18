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
import javax.persistence.OneToOne;

/**
 *
 * @author raphaelbaumann
 */
@Entity
public class Publication implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dateOfPublication;
    private String dateOfLastPublication;
    @OneToOne
    private Photo photo;
    @OneToOne
    private Recipie recepie;
    @OneToMany(mappedBy="publicationCom")
    private Collection<Comment> comment = new ArrayList<>();
//    @OneToOne
//    private Member memeber;
//    @ManyToMany
//    private Like likes;

    public Collection<Comment> getComment() {
        return comment;
    }

    public void setComment(Collection<Comment> comment) {
        this.comment = comment;
    }

    public String getDateOfPublication() {
        return dateOfPublication;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Recipie getRecepie() {
        return recepie;
    }

    public void setRecepie(Recipie recepie) {
        this.recepie = recepie;
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
