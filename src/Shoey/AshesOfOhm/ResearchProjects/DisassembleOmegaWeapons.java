package Shoey.AshesOfOhm.ResearchProjects;

import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.kaysaar.aotd.vok.Ids.AoTDSubmarkets;
import data.kaysaar.aotd.vok.scripts.research.models.ResearchProject;
import org.apache.log4j.Logger;

import static Shoey.AshesOfOhm.MainPlugin.*;

public class DisassembleOmegaWeapons extends ResearchProject {

    public int getStoredComponentCount(boolean convert)
    {
        int totalWeaponComponents = 0;

        for (MarketAPI m : Misc.getPlayerMarkets(false))
        {
            if (m.hasSubmarket(AoTDSubmarkets.RESEARCH_FACILITY_MARKET) && m.hasIndustry("coronal_pylon") && m.getIndustry("coronal_pylon").getSpecialItem() != null && m.hasIndustry("researchfacility") && m.getIndustry("researchfacility").getSpecialItem() != null)
            {
                CargoAPI cargoAPI = m.getSubmarket(AoTDSubmarkets.RESEARCH_FACILITY_MARKET).getCargo();
                if (cargoAPI.isEmpty())
                    continue;
                for (String id : omegaWeaponIDs)
                {
                    totalWeaponComponents += cargoAPI.getNumWeapons(id) * omegaWeaponComponentMap.get(id);
                    if (convert)
                    {
                        cargoAPI.removeWeapons(id, cargoAPI.getNumWeapons(id));
                        log.debug("Removed all of "+id+" from "+m.getName());
                    }
                }
            }
        }
        log.info("Found "+totalWeaponComponents+" potential components at appropriate facilities");
        if (convert) {
            setPlayerMemory("omegaWeaponPoints", (int) getPlayerMemory("omegaWeaponPoints") + totalWeaponComponents);
            setPlayerMemory("deconstructedOmegaWeapons", true);
            log.info("Player now has "+getPlayerMemory("omegaWeaponPoints"));
        }
        return totalWeaponComponents;
    }

    @Override
    public boolean haveMetReqForProjectToAppear() {
        return getPlayerMemoryBool("hasCoronalShunt");
    }

    @Override
    public boolean haveMetReqForProject() {

        if (!getPlayerMemoryBool("harvestingShuntWithResearch"))
            return false;

        if (getStoredComponentCount(false) > 0)
        {
            return true;
        }
        return false;
    }

    @Override
    public void generateTooltipInfoForProject(TooltipMakerAPI tooltipMakerAPI) {
        if (haveMetReqForProject()){
            tooltipMakerAPI.addPara("We are ready to being working on disassembly of the weapons.", Misc.getPositiveHighlightColor(), 10f);
        } else if (!getPlayerMemoryBool("harvestingShuntWithResearch")) {
            tooltipMakerAPI.addPara("We will need a research facility with access to a Hypershunt to begin.", Misc.getNegativeHighlightColor(), 10f);
        } else {
            tooltipMakerAPI.addPara("We will need our research facility with access to a Hypershunt to have stored \"Omega\" weapons to begin.", Misc.getNegativeHighlightColor(), 10f);
        }
    }

    @Override
    public void payForProjectIfNecessary() {

    }

    @Override
    public boolean haveMetReqForOption(String optionId) {
        return true;
    }

    @Override
    public void payForOption(String optionId) {

    }

    @Override
    public void applyOptionResults(String optionId) {
        super.applyOptionResults(optionId);
        if (optionId.equals("confirm"))
        {
            getStoredComponentCount(true);
        }
        if (optionId.equals("cancel")) {
            this.currentProgress = this.totalDays;
            this.haveDoneIt = true;
            this.applyProjectOutcomeWhenCompleted();
        }
    }

    @Override
    public void applyProjectOutcomeWhenCompleted() {
        super.applyProjectOutcomeWhenCompleted();
    }

    @Override
    public void generateTooltipForOption(String optionId, TooltipMakerAPI tooltip) {

        if (optionId.equals("confirm")) {
            tooltip.addPara("With our current stockpiles, we will get "+getStoredComponentCount(false)+" components that we will be able to use to produce Omega weapons of our choosing.", Misc.getTooltipTitleAndLightHighlightColor(), 10.0F);
        } else if (optionId.equals("cancel"))
        {
            tooltip.addPara("We can back out now and lose nothing other than our preparation time.", Misc.getTooltipTitleAndLightHighlightColor(), 10.0F);
        } else if (optionId.equals("org"))
        {
            tooltip.addPara("We will begin preparations to disassemble the Omega weapons.", Misc.getTooltipTitleAndLightHighlightColor(), 10.0F);
        }

    }

    @Override
    public void generateDescriptionForCurrentResults(TooltipMakerAPI tooltipMakerAPI) {
        tooltipMakerAPI.addPara("We possess "+getPlayerMemory("omegaWeaponPoints")+" components for Omega weapons.", Misc.getPositiveHighlightColor(), 10f);

    }


}
