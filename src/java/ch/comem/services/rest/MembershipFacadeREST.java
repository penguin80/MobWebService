package ch.comem.services.rest;

import ch.comem.model.Membership;
import ch.comem.model.Photo;
import ch.comem.services.beans.MembersManagerLocal;
import ch.comem.services.dto.MembershipDTO;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Pierre-Alexandre
 */
@Stateless
@Path("memberships")
public class MembershipFacadeREST {
    @EJB
    private MembersManagerLocal mm;
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;

    @POST
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public void create(Membership entity) {
        mm.createMember(entity.getFirstName(), entity.getLastName(), 
                        entity.getAge(), entity.getPseudo(), entity.getEmail());
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public void edit(Membership entity) {
        mm.modifyMember(entity.getId(), entity.getFirstName(), 
                        entity.getLastName(), entity.getAge(), 
                        entity.getPseudo(), entity.getEmail());
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        mm.deleteMember(id);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public MembershipDTO find(@PathParam("id") Long id) {
        Membership m = getEntityManager().find(Membership.class, id);
        MembershipDTO mDTO = null;
        if (m != null) {
            mDTO = new MembershipDTO();
            mDTO.setId(m.getId());
            mDTO.setFirstName(m.getFirstName());
            mDTO.setLastName(m.getLastName());
            mDTO.setAge(m.getAge());
            mDTO.setPseudo(m.getPseudo());
            mDTO.setEmail(m.getEmail());
        }
        return mDTO;
    }

    private List<MembershipDTO> setMembershipList(List<Membership> mList) {
        List<MembershipDTO> mDTOList = null;
        if (mList != null && !mList.isEmpty()) {
            mDTOList = new ArrayList<>();
            for (Membership m : mList) {
                MembershipDTO mDTO = new MembershipDTO();
                mDTO.setId(m.getId());
                mDTO.setFirstName(m.getFirstName());
                mDTO.setLastName(m.getLastName());
                mDTO.setPseudo(m.getPseudo());
                mDTOList.add(mDTO);
            }
        }
        return mDTOList;
    }
    
    @GET 
    @Path("searchPhotos/{id}")
    @Produces({"application/xml", "application/json"})
    public List<Photo> findPhotosByMemberId(@PathParam("id") Long id) {
        return mm.findAllPhotosFromMemberId(id);
    }
    
    @GET
    @Produces({"application/xml", "application/json"})
    public List<MembershipDTO> findAll() {
        List<Membership> mList = mm.findAllMembers();
        return setMembershipList(mList);
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<MembershipDTO> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        List<Membership> allMembers = mm.findAllMembers();
        List<MembershipDTO> subSelectionDTO = null;
        if (allMembers != null && !allMembers.isEmpty()) {
            List<Membership> subSelection = allMembers.subList(from.intValue(), to.intValue());
            subSelectionDTO = setMembershipList(subSelection);
        }
        return subSelectionDTO;
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(mm.findAllMembers().size());
    }

    protected EntityManager getEntityManager() {
        return em;
    }
    
}
