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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author raphaelbaumann
 */
@Entity
@XmlRootElement
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String currentCategoryNameSelected;
    private Collection<String> categoryNames;
    @OneToMany(mappedBy="categoryConcerned")
    private Collection<Publication> categorizedPublications = new ArrayList<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrentCategoryNameSelected() {
        return currentCategoryNameSelected;
    }

    public void setCurrentCategoryNameSelected(String name) {
        this.currentCategoryNameSelected = name;
    }

    public Collection<String> getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNamesByDefault() {
        this.categoryNames = new ArrayList<>();
        this.categoryNames.add("Biscuits");
        this.categoryNames.add("Gâteaux");
        this.categoryNames.add("Petits gâteaux");
        this.categoryNames.add("Gâteaux d'anniversaire");
        this.categoryNames.add("Macarons");
        this.categoryNames.add("Cake pops");
        this.categoryNames.add("Cupcakes");
        this.categoryNames.add("Gâteaux au yaourt");
        this.categoryNames.add("Pâte à sucre");
        this.categoryNames.add("Crèmes et Flans");
        this.categoryNames.add("Vacherins glacés");
        this.categoryNames.add("Tartes");
        this.categoryNames.add("Muffins");
    }
    
    public void addCategoryName(String name) {
        this.categoryNames.add(name);
    }
    
    public void removeCategoryName(String name) {
        this.categoryNames.remove(name);
    }

    @XmlTransient
    @JsonIgnore
    public Collection<Publication> getCategorizedPublications() {
        return categorizedPublications;
    }

    public void addPublication(Publication publication) {
        getCategorizedPublications().add(publication);
        publication.setCategoryConcerned(this);
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
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.comem.model.Category[ id=" + id + " ]";
    }
    
}
