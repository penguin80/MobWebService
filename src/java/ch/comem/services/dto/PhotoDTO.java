package ch.comem.services.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonAutoDetect;

/**
 *
 * @author raphaelbaumann
 */
@XmlRootElement(name="photo")
public class PhotoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String source;
    private String alt;
    PublicationDTO publication;

    public void setPublication(PublicationDTO publication) {
        this.publication = publication;
    }

//    public String getSource() {
//        return source;
//    }

    public void setSource(String source) {
        this.source = source;
    }

//    public String getAlt() {
//        return alt;
//    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

//    public Long getId() {
//        return id;
//    }

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
        if (!(object instanceof PhotoDTO)) {
            return false;
        }
        PhotoDTO other = (PhotoDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.comem.model.Photo[ id=" + id + " ]";
    }
    
}
