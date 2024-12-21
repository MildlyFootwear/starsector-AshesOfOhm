package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

public class AssistantBusyDurationPrint extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        MarketAPI m = dialog.getInteractionTarget().getMarket();
        if (params.get(0).string.contains("Equip")) {
            float busyDays = (m.getMemory().getLong("$ashesofohm_marketBusyDuration")-Global.getSector().getClock().getElapsedDaysSince(m.getMemory().getLong("$ashesofohm_marketBusyStart")));
            if (busyDays > 1) {
                dialog.getTextPanel().addPara(("\"Our equipment facilities are currently occupied so some options will be unavailable. The equipment facilities will be busy for " + (((float) Math.round(busyDays*4))/4) + " more days.\"").replace(".0",""));
            }
            else if (busyDays > 0) {
                dialog.getTextPanel().addPara("\"Our equipment facilities are currently occupied so some options will be unavailable. The equipment facilities will be ready by tomorrow.\"");
            }
            else {
                dialog.getTextPanel().addPara("\"The equipment facilities are ready.\"");
                m.getMemory().set("ashesofohm_marketBusy", false);
            }
        } else if (params.get(0).string.contains("Ship"))
        {
            float busyDays = (m.getMemory().getLong("$ashesofohm_marketBusyShipDuration")-Global.getSector().getClock().getElapsedDaysSince(m.getMemory().getLong("$ashesofohm_marketBusyShipStart")));
            if (busyDays > 1) {
                dialog.getTextPanel().addPara(("\"Our ship facilities are currently occupied so some options will be unavailable. The ship facilities will be busy for " + (((float) Math.round(busyDays*4))/4) + " more days.\"").replace(".0",""));
            }
            else if (busyDays > 0) {
                dialog.getTextPanel().addPara("\"Our ship facilities are currently occupied so some options will be unavailable. The ship facilities will be ready by tomorrow.\"");
            }
            else {
                dialog.getTextPanel().addPara("\"The ship facilities are ready.\"");
                m.getMemory().set("ashesofohm_marketBusyShip", false);
            }
        }
        return false;
    }
}
