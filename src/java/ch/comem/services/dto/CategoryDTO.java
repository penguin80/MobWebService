package ch.comem.services.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pierre-Alexandre
 */
@XmlRootElement(name="category")
public class CategoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private List<PublicationDTO> categorizedPublications = new ArrayList<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
//    @XmlTransient
//    @JsonIgnore
    public List<PublicationDTO> getCategorizedPublications() {
        return categorizedPublications;
    }

    public void addPublication(PublicationDTO publication) {
        categorizedPublications.add(publication);
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
        if (!(object instanceof CategoryDTO)) {
            return false;
        }
        CategoryDTO other = (CategoryDTO) object;
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
