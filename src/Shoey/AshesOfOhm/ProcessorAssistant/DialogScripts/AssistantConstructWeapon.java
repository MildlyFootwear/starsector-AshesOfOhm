package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import Shoey.AshesOfOhm.ProcessorAssistant.IngameScripts.ConstructWeapon;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

import static Shoey.AshesOfOhm.MainPlugin.omegaWeaponComponentMap;

public class AssistantConstructWeapon extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        String option = params.get(0).getString(memoryMap).replace("ashesofohm_","");
        if (omegaWeaponComponentMap.containsKey(option))
        {
            MarketAPI m = dialog.getInteractionTarget().getMarket();
            int weeks = (omegaWeaponComponentMap.get(option)*2);
            dialog.getTextPanel().addPara("\"Confirmed, order placed for "+ Global.getSettings().getWeaponSpec(option).getWeaponName()+".\"");
            ConstructWeapon script = new ConstructWeapon();
            script.wID = option;
            script.daysUntilDone = weeks * (7+m.getMemory().getInt("$ashesofohm_productionRateOffset"));
            script.entityToken = dialog.getInteractionTarget();
            Global.getSector().addScript(script);
            m.getMemory().set("$ashesofohm_marketBusy", true);
            m.getMemory().set("$ashesofohm_marketBusyWith", "Disassembly");
            m.getMemory().expire("$ashesofohm_marketBusy", script.daysUntilDone);
            m.getMemory().set("$ashesofohm_marketBusyStart", Global.getSector().getClock().getTimestamp());
            m.getMemory().set("$ashesofohm_marketBusyDuration", script.daysUntilDone);
        } else if (option.contains("Cancel")) {
            dialog.getTextPanel().addPara("\"Confirmed, order canceled. Returning to root directory.\"");
        } else {
            Logger log = Global.getLogger(this.getClass());
            log.error(ruleId+" not recognized.");
        }
        return false;
    }
}
