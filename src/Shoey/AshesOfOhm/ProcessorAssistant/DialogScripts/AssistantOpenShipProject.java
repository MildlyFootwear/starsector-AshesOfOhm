package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import Shoey.AshesOfOhm.ProcessorAssistant.IngameScripts.ConstructShip;
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

import static Shoey.AshesOfOhm.MainPlugin.omegaShipComponentMap;
import static Shoey.AshesOfOhm.MainPlugin.omegaWeaponComponentMap;

public class AssistantOpenShipProject extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        String option = params.get(0).getString(memoryMap).replace("ashesofohm_beginConstruction","");
        if (omegaShipComponentMap.containsKey(option))
        {
            MarketAPI m = dialog.getInteractionTarget().getMarket();
            int weeks = (omegaShipComponentMap.get(option)/3);
            dialog.getTextPanel().addPara("\"Confirmed, beginning preparation for "+option+".\"");
            ConstructShip script = new ConstructShip();
            script.kID = option;
            script.daysUntilDone = weeks * (7+m.getMemory().getInt("$ashesofohm_marketRateOffset"));
            script.entityToken = dialog.getInteractionTarget();
            Global.getSector().addScript(script);
            m.getMemory().set("$ashesofohm_marketBusyShip", true);
            m.getMemory().set("$ashesofohm_marketBusyShipWith", option+" preparation.");
            m.getMemory().expire("$ashesofohm_marketBusyShip", weeks*(7+m.getMemory().getInt("$ashesofohm_marketRateOffset")));
            m.getMemory().set("$ashesofohm_marketBusyShipStart", Global.getSector().getClock().getTimestamp());
            m.getMemory().set("$ashesofohm_marketBusyShipDuration", weeks*(7+m.getMemory().getInt("$ashesofohm_marketRateOffset")));
        } else if (option.contains("Cancel")) {
            dialog.getTextPanel().addPara("\"Confirmed, order canceled. Returning to root directory.\"");
        } else {
            Logger log = Global.getLogger(this.getClass());
            log.error(ruleId+" not recognized.");
        }
        return false;
    }
}
