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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author raphaelbaumann
 */
@NamedQueries({
    @NamedQuery(name="findAllRecipies", query="SELECT r FROM Recipie r"),
    @NamedQuery(name="findAllIngredientsFromRecipieId", 
                query="SELECT i FROM Recipie r JOIN r.ingredients i WHERE r.name = :name"),
    @NamedQuery(name="findAllStepsFromRecipieId", 
                query="SELECT s FROM Recipie r JOIN r.steps s WHERE r.name = :name")
})
@Entity
@XmlRootElement
public class Recipie implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinTable(name="recipie_step",
               joinColumns=@JoinColumn(name="Recipie_ID"),
               inverseJoinColumns=@JoinColumn(name="steps_ID"))
    private List<Step> steps = new ArrayList<>();
    @OneToMany(fetch=FetchType.LAZY)
    @JoinTable(name="recipie_ingredient",
               joinColumns=@JoinColumn(name="Recipie_ID"),
               inverseJoinColumns=@JoinColumn(name="ingredients_ID"))
    private List<Ingredient> ingredients = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @XmlTransient
    @JsonIgnore
    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    @XmlTransient
    @JsonIgnore
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
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
