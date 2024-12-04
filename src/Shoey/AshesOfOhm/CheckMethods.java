package Shoey.AshesOfOhm;

import Shoey.AshesOfOhm.ProcessorAssistant.AssistantMethods;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.util.Misc;

import java.util.Objects;

import static Shoey.AshesOfOhm.ProcessorAssistant.AssistantMethods.assistantID;

public class CheckMethods {

    public static boolean marketHarvestShunt(MarketAPI m)
    {
        if (m.hasIndustry("coronal_pylon") && m.getIndustry("coronal_pylon").getSpecialItem() != null && Objects.equals(m.getIndustry("coronal_pylon").getSpecialItem().getId(), "coronal_portal"))
        {
            m.getMemory().set("$ashesofohm_MarketHarvestingShunt", true);
            return true;
        }
        m.getMemory().set("$ashesofohm_MarketHarvestingShunt", false);
        return false;
    }

    public static boolean marketOmegaResearch(MarketAPI m)
    {
        boolean hasResearch = false;
        if (m.hasIndustry("researchfacility") && m.getIndustry("researchfacility").getSpecialItem() != null && Objects.equals(m.getIndustry("researchfacility").getSpecialItem().getId(), "omega_processor"))
        {
            hasResearch = true;
            boolean hasAssist = false;
            for (PersonAPI p : m.getPeopleCopy())
            {
                if (p.hasTag(assistantID)) {
                    hasAssist = true;
                }
            }
            if (!hasAssist) {
                AssistantMethods.createAssistant(m);
            }
        }
        if (!hasResearch) {
            for (PersonAPI p : m.getPeopleCopy())
            {
                if (p.hasTag("ashesofohm_omegaProcessorAssistant")) {
                    m.getCommDirectory().removePerson(p);
                    m.removePerson(p);
                }
            }
        }
        m.getMemory().set("$ashesofohm_MarketOmegaResearch", hasResearch);
        return hasResearch;
    }

    public static boolean checkShuntHarvest()
    {
        for (MarketAPI m : Misc.getPlayerMarkets(false) )
        {
            if (marketHarvestShunt(m))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean checkShuntWithResearch()
    {

        boolean bothOnOne = false;

        for (MarketAPI m : Misc.getPlayerMarkets(false) )
        {
            boolean hasResearch = false;
            boolean haarvestingShunt = false;
            if (marketHarvestShunt(m))
            {
                haarvestingShunt = true;
            }
            if (marketOmegaResearch(m)) {
                hasResearch = true;
            }
            if (haarvestingShunt && hasResearch) {
                bothOnOne = true;
                MainPlugin.log.info(m.getName()+" ("+m.getId()+") has shunt and research.");
            }
        }
        return bothOnOne;

    }

    public static void playerStatusChecks()
    {
        MainPlugin.setPlayerMemory("harvestingShunt", checkShuntHarvest());
        MainPlugin.setPlayerMemory("harvestingShuntWithResearch", checkShuntWithResearch());
    }
}
