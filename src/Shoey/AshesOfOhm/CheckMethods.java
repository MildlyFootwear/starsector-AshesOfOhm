package Shoey.AshesOfOhm;

import Shoey.AshesOfOhm.ProcessorAssistant.AssistantMethods;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.util.Misc;

import java.util.Objects;

import static Shoey.AshesOfOhm.MainPlugin.BypassProcessor;
import static Shoey.AshesOfOhm.MainPlugin.log;
import static Shoey.AshesOfOhm.MemoryShortcuts.setPlayerMemory;
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
        if (m.hasIndustry("researchfacility") && (BypassProcessor || (m.getIndustry("researchfacility").getSpecialItem() != null && Objects.equals(m.getIndustry("researchfacility").getSpecialItem().getId(), "omega_processor"))))
        {
            hasResearch = true;
//            log.debug(m.getName() + " passed research with processor check.");
        } else {
//            log.debug(m.getName() + " failed research with processor check.");
        }
        if (hasResearch)
        {
            boolean hasAssist = false;
            for (PersonAPI p : m.getPeopleCopy())
            {
                if (p.hasTag(assistantID)) {
                    hasAssist = true;
//                    log.debug(m.getName() + " already has assistant.");
                    break;
                }
            }
            if (!hasAssist) {
//                log.debug(m.getName() + " is receiving the assistant.");
                AssistantMethods.createAssistant(m);
            }
        } else {
            for (PersonAPI p : m.getPeopleCopy())
            {
                if (p.hasTag("ashesofohm_omegaProcessorAssistant")) {
//                    log.debug(m.getName() + " is losing the assistant.");
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
        for (MarketAPI m : Misc.getPlayerMarkets(true) )
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

        for (MarketAPI m : Misc.getPlayerMarkets(true) )
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
        setPlayerMemory("harvestingShunt", checkShuntHarvest());
        setPlayerMemory("harvestingShuntWithResearch", checkShuntWithResearch());
    }
}
