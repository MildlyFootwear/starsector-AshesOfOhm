package Shoey.AshesOfOhm;

import Shoey.AshesOfOhm.ProcessorAssistant.AssistantMethods;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.util.Misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static Shoey.AshesOfOhm.MainPlugin.*;
import static Shoey.AshesOfOhm.MemoryShortcuts.addComponents;
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
                AssistantMethods.createAssistant(m);
            }
        } else {
            for (PersonAPI p : m.getPeopleCopy())
            {
                if (p.hasTag(assistantID)) {
                    log.debug(m.getName() + " is losing its assistant.");
                    Global.getSector().getCampaignUI().addMessage("Due to changes in the facilities on "+m.getName()+", the Research Assistant will no longer be available.");
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

    public static void checkBlueprints()
    {
        List<String> foundBPs = new ArrayList<>();
        int components = 0;
        for(String wID : MainPlugin.omegaWeaponIDs) {
            if (Global.getSector().getPlayerFaction().knowsWeapon(wID)) {
                setPlayerMemory("haveDisassembled"+wID, true);
                setPlayerMemory("haveBlueprintFor"+wID, true);
                Global.getSector().getPlayerFaction().removeKnownWeapon(wID);
                components += omegaWeaponComponentMap.get(wID)*10;
                foundBPs.add(Global.getSettings().getWeaponSpec(wID).getWeaponName());
            }
        }
        if (components != 0)
        {
            addComponents(components);
            Global.getSector().getCampaignUI().addMessage(components + " components related to the unknown AI entities have been retrieved from disassembled technology.");
        }
    }

    public static void playerStatusChecks()
    {
        setPlayerMemory("harvestingShunt", checkShuntHarvest());
        setPlayerMemory("harvestingShuntWithResearch", checkShuntWithResearch());
        checkBlueprints();
    }

    public static boolean playerHasSpecialItem(String id) {
        final CampaignFleetAPI playerFleet = Global.getSector().getPlayerFleet();
        if (playerFleet == null) {
            return false;
        }
        final List<CargoStackAPI> playerCargoStacks = playerFleet.getCargo().getStacksCopy();
        for (final CargoStackAPI cargoStack : playerCargoStacks) {
            if (cargoStack.isSpecialStack() && cargoStack.getSpecialDataIfSpecial().getId().equals(id) && cargoStack.getSize() > 0) {
                return true;
            }
        }
        return false;
    }
}
