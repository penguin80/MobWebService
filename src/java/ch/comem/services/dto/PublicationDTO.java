package ch.comem.services.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pierre-Alexandre
 */
@XmlRootElement(name="publication")
public class PublicationDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String dateOfPublication;
    private long longDate;
    private PhotoDTO imagingPhoto;
    private RecipieDTO recepie;
    private List<CommentDTO> comments = new ArrayList<>();
    private List<LikingDTO> likes = new ArrayList<>();
    private CategoryDTO category;
    
    public String getDateOfPublication() {
        return dateOfPublication;
    }

    public long getLongDate() {
        return longDate;
    }

    public PhotoDTO getImagingPhoto() {
        return imagingPhoto;
    }

    public RecipieDTO getRecepie() {
        return recepie;
    }

    public List<CommentDTO> getComment() {
        return comments;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public List<LikingDTO> getLikes() {
        return likes;
    }

    public void setDateOfPublication(String dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }
    
    public void setLongDate(long longDate) {
        this.longDate = longDate;
    }

    public void setImagingPhoto(PhotoDTO imagingPhoto) {
        this.imagingPhoto = imagingPhoto;
    }

    public void setRecepie(RecipieDTO recepie) {
        this.recepie = recepie;
    }

    public void addComment(CommentDTO comment) {
        getComment().add(comment);
        comment.addPublication(this);
    }

    public void setCategory(CategoryDTO categoryConcerned) {
        this.category = categoryConcerned;
    }

    public void addLike(LikingDTO like) {
        getLikes().add(like);
        like.setPublication(this);
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
        if (!(object instanceof PublicationDTO)) {
            return false;
        }
        PublicationDTO other = (PublicationDTO) object;
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
