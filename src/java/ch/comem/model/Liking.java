/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author raphaelbaumann
 */
@Entity
public class Liking implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private boolean status;
    @ManyToOne
    private Publication publicationLiked;
    @ManyToOne
    private Membership memberLiking;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Publication getPublication() {
        return publicationLiked;
    }

    public void setPublication(Publication publication) {
        this.publicationLiked = publication;
    }

    public Membership getMemberLiking() {
        return memberLiking;
    }

    public void setMemberLiking(Membership memberLiking) {
        this.memberLiking = memberLiking;
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
        if (!(object instanceof Liking)) {
            return false;
        }
        Liking other = (Liking) object;
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
