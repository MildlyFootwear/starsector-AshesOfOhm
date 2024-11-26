package Shoey.AshesOfOhm.ResearchProjects;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Submarkets;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.kaysaar.aotd.vok.scripts.research.models.ResearchProject;

import static Shoey.AshesOfOhm.MainPlugin.*;
import static Shoey.AshesOfOhm.ResearchProjects.ResearchUtils.getProjectWeaponID;
import static Shoey.AshesOfOhm.ResearchProjects.ResearchUtils.setProjectWeaponID;

public class AssembleOmegaWeapons extends ResearchProject {

    @Override
    public boolean haveMetReqForProjectToAppear() {
        return getPlayerMemoryBool("deconstructedOmegaWeapons") || getPlayerMemoryInt("omegaWeaponPoints") > 0;
    }

    @Override
    public boolean haveMetReqForProject() {

        if (!getPlayerMemoryBool("harvestingShuntWithResearch"))
            return false;

        if (getPlayerMemoryInt("omegaWeaponPoints") > 0)
        {
            return true;
        }
        return false;

    }

    @Override
    public void generateTooltipInfoForProject(TooltipMakerAPI tooltipMakerAPI) {
        if (haveMetReqForProject()){
            tooltipMakerAPI.addPara("We are ready to being working on assembly of the weapons. We have "+getPlayerMemory("omegaWeaponPoints")+" components in storage.", Misc.getPositiveHighlightColor(), 10f);
        } else if (!getPlayerMemoryBool("harvestingShuntWithResearch")) {
            tooltipMakerAPI.addPara("We will need a research facility with access to a Hypershunt to begin.", Misc.getNegativeHighlightColor(), 10f);
        } else {
            tooltipMakerAPI.addPara("We will more Omega components to begin.", Misc.getNegativeHighlightColor(), 10f);
        }
    }

    @Override
    public void payForProjectIfNecessary() {

    }

    @Override
    public boolean haveMetReqForOption(String optionId) {
        if (omegaWeaponComponentMap.containsKey(optionId) && getPlayerMemoryInt("omegaWeaponPoints") < omegaWeaponComponentMap.get(optionId))
            return false;
        return true;
    }

    @Override
    public void payForOption(String optionId) {

    }

    @Override
    public void applyOptionResults(String optionId) {
        currentValueOfOptions = 0;
        super.applyOptionResults(optionId);
        if (optionId.equals("cancel")) {
            this.currentProgress = this.totalDays;
            this.haveDoneIt = true;
            this.applyProjectOutcomeWhenCompleted();
        } else if (omegaWeaponIDs.contains(optionId))
        {
            setProjectWeaponID(currentValueOfOptions, optionId);
        }
    }

    @Override
    public void applyProjectOutcomeWhenCompleted() {
        super.applyProjectOutcomeWhenCompleted();
        setPlayerMemory("omegaWeaponPoints", getPlayerMemoryInt("omegaWeaponPoints") - omegaWeaponComponentMap.get(getProjectWeaponID(currentValueOfOptions)));
        Global.getSector().getPlayerFaction().getProduction().getGatheringPoint().getSubmarket(Submarkets.SUBMARKET_STORAGE).getCargo().addWeapons(getProjectWeaponID(currentValueOfOptions), 1);
    }

    @Override
    public void generateTooltipForOption(String optionId, TooltipMakerAPI tooltip) {

        if (omegaWeaponComponentMap.containsKey(optionId)) {
            tooltip.addPara("Building this option will cost "+omegaWeaponComponentMap.get(optionId)+" components.", Misc.getTooltipTitleAndLightHighlightColor(), 10.0F);
        } else if (optionId.equals("cancel"))
        {
            tooltip.addPara("We can back out now and lose nothing other than our preparation time.", Misc.getTooltipTitleAndLightHighlightColor(), 10.0F);
        } else if (optionId.equals("org"))
        {
            tooltip.addPara("We will begin preparations to assemble the Omega weapons.", Misc.getTooltipTitleAndLightHighlightColor(), 10.0F);
        }

    }

    @Override
    public void generateDescriptionForCurrentResults(TooltipMakerAPI tooltipMakerAPI) {
         tooltipMakerAPI.addPara("We possess "+getPlayerMemory("omegaWeaponPoints")+" components for Omega weapons.", Misc.getPositiveHighlightColor(), 10f);
         if (currentValueOfOptions != 0f)
             tooltipMakerAPI.addPara("Currently constructing "+Global.getSettings().getWeaponSpec(getProjectWeaponID(currentValueOfOptions)).getWeaponName(),Misc.getTooltipTitleAndLightHighlightColor(), 10.0F);
    }


}
