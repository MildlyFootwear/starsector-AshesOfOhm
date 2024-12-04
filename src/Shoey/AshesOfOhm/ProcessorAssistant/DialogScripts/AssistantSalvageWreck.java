package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import Shoey.AshesOfOhm.ProcessorAssistant.IngameScripts.AddComponents;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

import static Shoey.AshesOfOhm.MainPlugin.*;
import static Shoey.AshesOfOhm.MainPlugin.getPlayerMemoryInt;


public class AssistantSalvageWreck extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        MarketAPI m = dialog.getInteractionTarget().getMarket();
        String shipName = params.get(0).getString(memoryMap).replace("ashesofohm_salvage","");
        if (omegaShipComponentMap.containsKey(shipName))
        {
            AddComponents script = new AddComponents();
            script.componentsLeft = omegaShipComponentMap.get(shipName);
            script.entityToken = m.getPrimaryEntity();
            script.forShip = true;
            script.shipName = shipName;
            script.offset = m.getMemory().getInt("$ashesofohm_marketRateOffset");
            setPlayerMemory("salvaged"+shipName+"Count", getPlayerMemoryInt("salvaged"+shipName+"Count")+1);
            Global.getSector().addScript(script);
            dialog.getTextPanel().addPara("Confirmed. Salvaging of a "+shipName+" has been initiated, expect ship facilities to be occupied for the near future.");

            long busyDur = script.componentsLeft * (7L + script.offset);
            if (!getPlayerMemoryBool("haveSalvaged"+shipName))
            {
                busyDur *= 4;
            }
            m.getMemory().set("$ashesofohm_marketBusyShip", true);
            m.getMemory().expire("$ashesofohm_marketBusyShip", busyDur);
            m.getMemory().set("$ashesofohm_marketBusyShipStart", Global.getSector().getClock().getTimestamp());
            m.getMemory().set("$ashesofohm_marketBusyShipDuration", busyDur);
        } else {
            dialog.getTextPanel().addPara(shipName+" not found in ship map. Please bug report on forum thread.", Misc.getNegativeHighlightColor());
        }
        return false;
    }
}
