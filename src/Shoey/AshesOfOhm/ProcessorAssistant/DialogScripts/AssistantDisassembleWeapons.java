package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import Shoey.AshesOfOhm.MemoryShortcuts;
import Shoey.AshesOfOhm.ProcessorAssistant.IngameScripts.AddComponents;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;
import data.kaysaar.aotd.vok.Ids.AoTDSubmarkets;

import java.util.List;
import java.util.Map;

import static Shoey.AshesOfOhm.MainPlugin.*;

public class AssistantDisassembleWeapons extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        MarketAPI m = dialog.getInteractionTarget().getMarket();
        int totalWeaponComponents = 0;
        if (m.hasSubmarket(AoTDSubmarkets.RESEARCH_FACILITY_MARKET))
        {
            CargoAPI cargoAPI = m.getSubmarket(AoTDSubmarkets.RESEARCH_FACILITY_MARKET).getCargo();
            for (String id : omegaWeaponIDs)
            {
                int num = cargoAPI.getNumWeapons(id);
                int componentCnt = omegaWeaponComponentMap.get(id);
                if (num * componentCnt > 0) {
                    totalWeaponComponents += num * componentCnt;
                    MemoryShortcuts.setPlayerMemory("haveDisassembled" + id, true);
                    cargoAPI.removeWeapons(id, num);
                    log.debug("Disassembling "+num+" of "+id);
                }
            }
        }
        if (totalWeaponComponents > 0) {
            m.getMemory().set("$ashesofohm_marketBusy", true);
            m.getMemory().set("$ashesofohm_marketBusyWith", "Disassembly");
            m.getMemory().expire("$ashesofohm_marketBusy", totalWeaponComponents*(7+m.getMemory().getInt("$ashesofohm_marketRateOffset")));
            m.getMemory().set("$ashesofohm_marketBusyStart", Global.getSector().getClock().getTimestamp());
            m.getMemory().set("$ashesofohm_marketBusyDuration", totalWeaponComponents*(7+m.getMemory().getInt("$ashesofohm_marketRateOffset")));
            AddComponents script = new AddComponents();
            script.entityToken = m.getPrimaryEntity();
            script.componentsLeft = totalWeaponComponents;
            script.offset = m.getMemory().getInt("$ashesofohm_marketRateOffset");
            Global.getSector().addScript(script);
        }
        return false;
    }
}
