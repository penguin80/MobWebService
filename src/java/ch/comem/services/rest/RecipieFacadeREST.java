package ch.comem.services.rest;

import ch.comem.model.Ingredient;
import ch.comem.model.Recipie;
import ch.comem.model.Step;
import ch.comem.services.beans.IngredientsManagerLocal;
import ch.comem.services.beans.RecipieManagerLocal;
import ch.comem.services.beans.StepsManagerLocal;
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
    private StepsManagerLocal sm;
    @EJB
    private IngredientsManagerLocal im;
    @EJB
    private RecipieManagerLocal rm;
    @PersistenceContext(unitName = "PastyChefPU")
    private EntityManager em;

    private List<Long> setIngredientIds(Recipie r) {
        List<Long> ingredientIds = new ArrayList<>();
        List<Ingredient> ingredients = r.getIngredients();
        for (Ingredient i : ingredients) {
            Long iId = im.createIngredient(i.getName(), i.getQuantity(), i.getQuantityUnit());
            ingredientIds.add(iId);
        }
        return ingredientIds;
    }
    
    private List<Long> setStepIds(Recipie r)  {
        List<Long> stepIds = null;
        List<Step> steps = r.getSteps();
        if (steps != null) {
            stepIds = new ArrayList<>();
            for (Step s : steps) {
                Long sId = sm.createStep(s.getStepNumber(), s.getDescription());
                stepIds.add(sId);
            }
        }
        return stepIds;
    }
    
    private List<Long> mergeIngredientIds(Recipie previousR, Recipie updatedR) {
        List<Long> ingredientIds = new ArrayList<>();
        List<Ingredient> previousIngredients = previousR.getIngredients();
        List<Ingredient> updatedIngredients = updatedR.getIngredients();
        if (previousIngredients != null && updatedIngredients != null) {
            for (Ingredient upI : updatedIngredients) {
                Long iId = null;
                for (Ingredient prevI : previousIngredients) {
                    if (iId == null) {
                        if (upI.getName().equals(prevI.getName()) &&
                            upI.getQuantity() == prevI.getQuantity() &&
                            upI.getQuantityUnit().equals(prevI.getQuantityUnit()))
                            iId = prevI.getId();
                        else if ((upI.getName().equals(prevI.getName()) &&
                                 (upI.getQuantity() == prevI.getQuantity() ||
                                 upI.getQuantityUnit().equals(prevI.getQuantityUnit()))) ||
                                
                                 (upI.getQuantity() == prevI.getQuantity() && 
                                 (upI.getName().equals(prevI.getName())) || 
                                 upI.getQuantityUnit().equals(prevI.getQuantityUnit())) ||
                                
                                 (upI.getQuantityUnit().equals(prevI.getQuantityUnit()) &&
                                 (upI.getName().equals(prevI.getName()) || 
                                 upI.getQuantity() == prevI.getQuantity()))) {
                            im.modifyIngredient(prevI.getId(), upI.getName(), 
                                                upI.getQuantity(), upI.getQuantityUnit());
                            iId = prevI.getId();
                        }
                    }
                }
                if (iId == null)
                    iId = im.createIngredient(upI.getName(), upI.getQuantity(), 
                                              upI.getQuantityUnit());
                ingredientIds.add(iId);
            }
        }
        return ingredientIds;
    }
    
    private List<Long> mergeStepIds(Recipie previousR, Recipie updatedR) {
        List<Long> stepIds = null;
        List<Step> previousSteps = previousR.getSteps();
        List<Step> updatedSteps = updatedR.getSteps();
        if (previousSteps != null && updatedSteps != null) {
            stepIds = new ArrayList<>();
            for (Step upS : updatedSteps) {
                Long sId = null;
                for (Step prevS : previousSteps) {
                    if (sId == null) {
                        if (upS.getStepNumber() == prevS.getStepNumber() &&
                            upS.getDescription().equals(prevS.getDescription()))
                            sId = prevS.getId();
                        else if ((upS.getStepNumber() == prevS.getStepNumber() && 
                                 !upS.getDescription().equals(prevS.getDescription())) ||
                                 (upS.getDescription().equals(prevS.getDescription()) &&
                                 upS.getStepNumber() != prevS.getStepNumber())) {
                            sm.modifyStep(prevS.getId(), upS.getStepNumber(), upS.getDescription());
                            sId = prevS.getId();
                        }
                    }
                }
                if (sId == null)
                    sId = sm.createStep(upS.getStepNumber(), upS.getDescription());
                stepIds.add(sId);
            }
        }
        return stepIds;
    }
    
    @POST
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public Recipie create(Recipie entity) {
        List<Long> iIds = setIngredientIds(entity);
        List<Long> sIds = setStepIds(entity);
        Long recipieId = rm.createRecipie(entity.getName(), iIds, sIds);
        return getEntityManager().find(Recipie.class, recipieId);
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public Recipie edit(Recipie entity) {
//        Recipie r = getEntityManager().find(Recipie.class, entity.getId());
//        List<Long> iIds = mergeIngredientIds(r, entity);
//        List<Long> sIds = mergeStepIds(r, entity);
        List<Long> iIds = setIngredientIds(entity);
        List<Long> sIds = setStepIds(entity);
        rm.modifyRecipie(entity.getId(), entity.getName(), iIds, sIds);
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

    public void persist(Object object) {
        em.persist(object);
    }
    
}
