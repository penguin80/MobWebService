/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.comem.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author raphaelbaumann
 */
@Entity
@XmlRootElement
public class Recipie implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Set<String> steps;
    @OneToMany(mappedBy="recipieInvolved")
    private Collection<Ingredient> ingredientsNeeded = new ArrayList<>();
    @OneToOne
    @JoinColumn(name="PUBLICATIONID", referencedColumnName="ID")
    private Publication publicationConcerned;

//    public Publication getPublication() {
//        return publication;
//    }
//
//    public void setPublication(Publication publication) {
//        this.publication = publication;
//    }

    public Set<String> getSteps() {
        return steps;
    }

    public void setSteps(Set<String> steps) {
        this.steps = steps;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<Ingredient> getIngredients() {
        return ingredientsNeeded;
    }

    public void addIngredient(Ingredient ingredient) {
        getIngredients().add(ingredient);
        ingredient.setRecipieInvolved(this);
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
        if (!(object instanceof Recipie)) {
            return false;
        }
        Recipie other = (Recipie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.comem.model.Recipie[ id=" + id + " ]";
    }
    
}
