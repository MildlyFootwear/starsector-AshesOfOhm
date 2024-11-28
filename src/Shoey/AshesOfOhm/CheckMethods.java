package Shoey.AshesOfOhm;

import Shoey.AshesOfOhm.ProcessorAssistant.AssistantMethods;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.util.Misc;

import java.util.Objects;

import static Shoey.AshesOfOhm.ProcessorAssistant.AssistantMethods.assistantID;

public class CheckMethods {
    public static boolean checkShuntPossess()
    {
        for (MarketAPI m : Misc.getPlayerMarkets(false) )
        {
            if (m.hasIndustry("coronal_network"))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean checkShuntHarvest()
    {
        if (!checkShuntPossess())
            return false;

        for (MarketAPI m : Misc.getPlayerMarkets(false) )
        {
            if (m.hasIndustry("coronal_pylon") && m.getIndustry("coronal_pylon").getSpecialItem() != null && Objects.equals(m.getIndustry("coronal_pylon").getSpecialItem().getId(), "coronal_portal"))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean checkShuntWithResearch()
    {

        if (!checkShuntPossess())
            return false;

        boolean bothOnOne = false;

        for (MarketAPI m : Misc.getPlayerMarkets(false) )
        {
            boolean hasResearch = false;
            boolean haarvestingShunt = false;
            if (m.hasIndustry("coronal_pylon") && m.getIndustry("coronal_pylon").getSpecialItem() != null && Objects.equals(m.getIndustry("coronal_pylon").getSpecialItem().getId(), "coronal_portal"))
            {
                haarvestingShunt = true;
            }
            if (m.hasIndustry("researchfacility") && m.getIndustry("researchfacility").getSpecialItem() != null && Objects.equals(m.getIndustry("researchfacility").getSpecialItem().getId(), "omega_processor"))
            {
                hasResearch = true;
                boolean hasAssist = false;
                for (PersonAPI p : m.getPeopleCopy())
                {
                    if (p.hasTag(assistantID)) {
                        MainPlugin.log.info(m.getName()+" ("+m.getId()+") has assistant.");
                        hasAssist = true;
                    }
                }
                if (!hasAssist) {
                    AssistantMethods.createAssistant(m);
                    MainPlugin.log.info("Added assistant to "+m.getName()+" ("+m.getId()+")");
                }
            }
            if (haarvestingShunt && hasResearch) {
                bothOnOne = true;
                MainPlugin.log.info(m.getName()+" ("+m.getId()+") has shunt and research.");
            }
            if ((!hasResearch)) {
                for (PersonAPI p : m.getPeopleCopy())
                {
                    if (p.hasTag("ashesofohm_omegaProcessorAssistant")) {
                        m.getCommDirectory().removePerson(p);
                        m.removePerson(p);
                    }
                }
            }
        }
        return bothOnOne;

    }

    public static void playerStatusChecks()
    {
        MainPlugin.setPlayerMemory("hasCoronalShunt", checkShuntPossess());
        MainPlugin.setPlayerMemory("harvestingShunt", checkShuntHarvest());
        MainPlugin.setPlayerMemory("harvestingShuntWithResearch", checkShuntWithResearch());
        if (MainPlugin.getPlayerMemoryInt("destroyedTesseractCount") > MainPlugin.getPlayerMemoryInt("constructedTesseractCount") && MainPlugin.getPlayerMemoryBool("harvestingShuntWithResearch"))
        {
            MainPlugin.setPlayerMemory("canConstructReplicaTesseract", true);
        } else {
            MainPlugin.setPlayerMemory("canConstructReplicaTesseract", false);
        }
    }
}
