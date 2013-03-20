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
    private String dateOfLastPublication;
    private PhotoDTO imagingPhoto;
    private RecipieDTO recepie;
    private List<CommentDTO> comments = new ArrayList<>();
    private CategoryDTO categoryConcerned;
    private MembershipDTO memberInvolved;
    private List<LikingDTO> likes = new ArrayList<>();

    public String getDateOfPublication() {
        return dateOfPublication;
    }

    public PhotoDTO getImagingPhoto() {
        return imagingPhoto;
    }

    public void setImagingPhoto(PhotoDTO imagingPhoto) {
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

    public RecipieDTO getRecepie() {
        return recepie;
    }

    public void setRecepie(RecipieDTO recepie) {
        this.recepie = recepie;
    }

//    @XmlTransient
//    @JsonIgnore
    public List<CommentDTO> getComment() {
        return comments;
    }

    public void addComment(CommentDTO comment) {
        comments.add(comment);
        comment.addPublication(this);
    }

    public CategoryDTO getCategoryConcerned() {
        return categoryConcerned;
    }

    public void setCategoryConcerned(CategoryDTO categoryConcerned) {
        this.categoryConcerned = categoryConcerned;
    }

    public MembershipDTO getMemberInvolved() {
        return memberInvolved;
    }

    public void setMemberInvolved(MembershipDTO memberInvolved) {
        this.memberInvolved = memberInvolved;
    }

//    @XmlTransient
//    @JsonIgnore
    public List<LikingDTO> getLikes() {
        return likes;
    }

    public void addLike(LikingDTO like) {
        likes.add(like);
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
