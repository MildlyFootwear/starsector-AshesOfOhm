package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import Shoey.AshesOfOhm.MemoryShortcuts;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.kaysaar.aotd.vok.Ids.AoTDSubmarkets;

import java.util.List;
import java.util.Map;

import static Shoey.AshesOfOhm.MainPlugin.*;
import static Shoey.AshesOfOhm.MemoryShortcuts.getPlayerMemoryBool;

public class AssistantPollComponentsFacility extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        MarketAPI m = dialog.getInteractionTarget().getMarket();
        int totalWeaponComponents = 0;
        String s = "";
        if (m.hasSubmarket(AoTDSubmarkets.RESEARCH_FACILITY_MARKET))
        {
            CargoAPI cargoAPI = m.getSubmarket(AoTDSubmarkets.RESEARCH_FACILITY_MARKET).getCargo();
            for (String id : omegaWeaponIDs)
            {
                int num = cargoAPI.getNumWeapons(id);
                int componentCnt = omegaWeaponComponentMap.get(id);
                log.debug("Found "+num+" of "+id);
                if (num * componentCnt > 0) {
                    totalWeaponComponents += num * componentCnt;
                    s += "    "+Global.getSettings().getWeaponSpec(id).getWeaponName()+" (x"+num+")";
                    if (!getPlayerMemoryBool("haveDisassembled" + id, true))
                    {
                        s += "*";
                    }
                    s += "\n";
                }
            }
        }
        if (totalWeaponComponents > 0) {
            TooltipMakerAPI tooltipMakerAPI = dialog.getTextPanel().beginTooltip();
            tooltipMakerAPI.addPara("Found the following weapons in the facility:", 0);
            tooltipMakerAPI.addPara(s, 0);
            tooltipMakerAPI.addPara("You will receive "+totalWeaponComponents+" components in exchange for all listed items.", Misc.getHighlightColor(), 0);
            if (s.contains("*"))
                tooltipMakerAPI.addPara("You will learn how to produce new items based off of those that have been marked.", Misc.getHighlightColor(), 0);
            dialog.getTextPanel().addTooltip();
        }
        m.getMemory().set("$ashesofohm_omegaWeaponPointsForDialog", totalWeaponComponents);
        return false;
    }
}
