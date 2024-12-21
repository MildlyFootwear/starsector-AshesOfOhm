package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import Shoey.AshesOfOhm.ProcessorAssistant.AssistantMethods;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

import static Shoey.AshesOfOhm.MainPlugin.omegaWeaponComponentMap;
import static Shoey.AshesOfOhm.MemoryShortcuts.getPlayerMemoryBool;
import static Shoey.AshesOfOhm.MemoryShortcuts.getPlayerMemoryInt;

public class AssistantPopulateWeapons extends BaseCommandPlugin {

    @Override
    public boolean doesCommandAddOptions() {
        return true;
    }

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        int mode = 0;

        if (params.get(0).string.contains("Small")) {
            mode = 1;
        } else if (params.get(0).string.contains("Medium")) {
            mode = 2;
        } else if (params.get(0).string.contains("Large")) {
            mode = 3;
        }

        dialog.getTextPanel().addPara("\"Querying known "+params.get(0).string.toLowerCase()+" arms...\"");
        dialog.getTextPanel().addPara("A list of weapons shows up on your display.");
        dialog.getTextPanel().addPara("\"Listed arms will take approximately "+ AssistantMethods.getWeaponConstructionDuration(mode, dialog.getInteractionTarget().getMarket())+" days to produce.\"");

        for (String k : omegaWeaponComponentMap.keySet())
        {
            if (!getPlayerMemoryBool("haveDisassembled" + k, true))
                continue;

            if (omegaWeaponComponentMap.get(k) == mode) {
                dialog.getOptionPanel().addOption(Global.getSettings().getWeaponSpec(k).getWeaponName(), "ashesofohm_"+k);
            }
        }

        dialog.getOptionPanel().addOption("Cancel", "ashesofohm_assistantAssembleOrderOptionWeaponCancel");

        return false;
    }
}
