package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

import static Shoey.AshesOfOhm.MainPlugin.omegaWeaponComponentMap;

public class AssistantConstructWeapon extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        String option = params.get(0).getString(memoryMap).replace("ashesofohm_","");
        if (omegaWeaponComponentMap.containsKey(option))
        {
            dialog.getTextPanel().addPara("\"Confirmed, order placed for "+ Global.getSettings().getWeaponSpec(option).getWeaponName()+". Estimated time to completion is "+(omegaWeaponComponentMap.get(option)*2)+" weeks.\"");
        } else if (option.contains("Cancel")) {
            dialog.getTextPanel().addPara("\"Confirmed, order canceled. Returning to root directory.\"");
        } else {
            dialog.getTextPanel().addPara("\"I'm afraid I can't do that.\"");
            dialog.getTextPanel().addPara(option+" not recognized. Please report this as a bug on the forum, thank you.", Misc.getHighlightColor());
        }
        return false;
    }
}
