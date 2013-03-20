package ch.comem.services.rest;

import ch.comem.model.Ingredient;
import ch.comem.model.Recipie;
import ch.comem.model.Step;
import ch.comem.services.beans.RecipieManagerLocal;
import ch.comem.services.dto.IngredientDTO;
import ch.comem.services.dto.RecipieDTO;
import ch.comem.services.dto.StepDTO;
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
@Path("recipies")
public class RecipieFacadeREST {
    @EJB
    private RecipieManagerLocal rm;
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;

    @POST
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public Recipie create(Recipie entity) {
        List<Ingredient> ingredients = entity.getIngredients();
        List<Long> ingredientIds = new ArrayList<>();
        for (Ingredient i : ingredients)
            ingredientIds.add(i.getId());
        List<Step> steps = entity.getSteps();
        List<Long> stepIds = new ArrayList<>();
        for (Step s : steps)
            stepIds.add(s.getId());
        Long recipieId = rm.createRecipie(entity.getName(), ingredientIds, stepIds);
        return getEntityManager().find(Recipie.class, recipieId);
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public Recipie edit(Recipie entity) {
        List<Ingredient> ingredients = entity.getIngredients();
        List<Long> ingredientIds = new ArrayList<>();
        for (Ingredient i : ingredients)
            ingredientIds.add(i.getId());
        List<Step> steps = entity.getSteps();
        List<Long> stepIds = new ArrayList<>();
        for (Step s : steps)
            stepIds.add(s.getId());
        rm.modifyRecipie(entity.getId(), entity.getName(), ingredientIds, stepIds);
        return getEntityManager().find(Recipie.class, entity.getId());
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        rm.deleteRecipie(id);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public RecipieDTO find(@PathParam("id") Long id) {
         Recipie r = getEntityManager().find(Recipie.class, id);
         RecipieDTO rDTO = null;
         if (r != null) {
             rDTO = new RecipieDTO();
             rDTO.setId(r.getId());
             rDTO.setName(r.getName());
             List<Ingredient> iList = r.getIngredients();
             List<IngredientDTO> iDTOList = null;
             if (iList != null && !iList.isEmpty()) {
                 iDTOList = new ArrayList<>();
                 for (Ingredient i : iList) {
                     IngredientDTO iDTO = new IngredientDTO();
                     iDTO.setId(i.getId());
                     iDTO.setName(i.getName());
                     iDTO.setQuantity(i.getQuantity());
                     iDTO.setQuantityUnit(i.getQuantityUnit());
                     iDTOList.add(iDTO);
                 }
             }
             rDTO.setIngredients(iDTOList);
             List<Step> sList = r.getSteps();
             List<StepDTO> sDTOList = null;
             if (sList != null && !sList.isEmpty()) {
                 sDTOList = new ArrayList<>();
                 for (Step s : sList) {
                     StepDTO sDTO = new StepDTO();
                     sDTO.setId(s.getId());
                     sDTO.setStepNumber(s.getStepNumber());
                     sDTO.setDescription(s.getDescription());
                     sDTOList.add(sDTO);
                 }
             }
             rDTO.setSteps(sDTOList);
         }
         return rDTO;
    }

    private List<RecipieDTO> setRecipieList(List<Recipie> rList) {
        List<RecipieDTO> rDTOList = null;
        if (rList != null && !rList.isEmpty()) {
            rDTOList = new ArrayList<>();
            for (Recipie r : rList) {
                RecipieDTO rDTO = new RecipieDTO();
                rDTO.setId(r.getId());
                rDTO.setName(r.getName());
                rDTOList.add(rDTO);
            }
        }
        return rDTOList;
    }
    
    @GET 
    @Path("searchIngredients/{name}")
    @Produces({"application/xml", "application/json"})
    public List<Ingredient> findIngredientsByName(@PathParam("name") String name) {
        return rm.findAllIngredientsFromRecipieName(name);
    }
    
    @GET
    @Path("searchSteps/{name}")
    @Produces({"application/xml", "application/json"})
    public List<Step> findStepsByName(@PathParam("name") String name) {
        return rm.findAllStepsFromRecipieName(name);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<RecipieDTO> findAll() {
        List<Recipie> rList = rm.findAllRecipies();
        return setRecipieList(rList);
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<RecipieDTO> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        List<Recipie> allRecipies = rm.findAllRecipies();
        List<RecipieDTO> subSelectionDTO = null;
        if (allRecipies != null && !allRecipies.isEmpty()) {
            List<Recipie> subSelection = allRecipies.subList(from.intValue(), 
                                                             to.intValue());
            subSelectionDTO = setRecipieList(subSelection);
        }
        return subSelectionDTO;
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(rm.findAllRecipies().size());
    }

    protected EntityManager getEntityManager() {
        return em;
    }
    
}
