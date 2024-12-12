package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import Shoey.AshesOfOhm.MemoryShortcuts;
import Shoey.AshesOfOhm.ProcessorAssistant.IngameScripts.ConstructShip;
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

public class AssistantOpenShipProject extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        String option = params.get(0).getString(memoryMap).replace("ashesofohm_beginConstruction","");
        if (omegaShipComponentMap.containsKey(option))
        {
            MarketAPI m = dialog.getInteractionTarget().getMarket();
            int comp = (omegaShipComponentMap.get(option));
            dialog.getTextPanel().addPara("\"Confirmed, beginning preparation for "+option+".\"");
            ConstructShip script = new ConstructShip();
            script.kID = option;
            script.daysUntilDone = comp * (7+(m.getMemory().getInt("$ashesofohm_marketRateOffset"))) / 3;
            script.entityToken = dialog.getInteractionTarget();
            Global.getSector().addScript(script);
            MemoryShortcuts.setPlayerMemory("omegaWeaponPoints", MemoryShortcuts.getPlayerMemoryInt("omegaWeaponPoints") - (comp * 2));
            m.getMemory().set("$ashesofohm_marketBusyShip", true);
            m.getMemory().set("$ashesofohm_marketBusyShipWith", option+" preparation.");
            m.getMemory().expire("$ashesofohm_marketBusyShip", script.daysUntilDone);
            m.getMemory().set("$ashesofohm_marketBusyShipStart", Global.getSector().getClock().getTimestamp());
            m.getMemory().set("$ashesofohm_marketBusyShipDuration", script.daysUntilDone);
        } else if (option.contains("Cancel")) {
            dialog.getTextPanel().addPara("\"Confirmed, order canceled. Returning to root directory.\"");
        } else {
            Logger log = Global.getLogger(this.getClass());
            log.error(ruleId+" not recognized.");
        }
        return false;
    }
}
