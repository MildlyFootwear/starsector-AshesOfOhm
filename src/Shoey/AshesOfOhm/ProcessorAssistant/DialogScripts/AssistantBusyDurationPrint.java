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
        float busyDays = (m.getMemory().getLong("$ashesofohm_marketBusyDuration")-Global.getSector().getClock().getElapsedDaysSince(m.getMemory().getLong("$ashesofohm_marketBusyStart")));
        if (busyDays > 1) {
            dialog.getTextPanel().addPara("\"The facilities will be busy for " + (((float) Math.round(busyDays*4))/4) + " more days.\"");
        }
        else if (busyDays > 0) {
            dialog.getTextPanel().addPara("\"The facilities will be ready by tomorrow.\"");
        }
        else {
            dialog.getTextPanel().addPara("\"The facilities are ready.\"");
            m.getMemory().set("ashesofohm_marketBusy", false);
        }
        return false;
    }
}
