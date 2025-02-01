package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

import static Shoey.AshesOfOhm.MainPlugin.omegaWeaponComponentMap;
import static Shoey.AshesOfOhm.MemoryShortcuts.getPlayerMemoryBool;

public class AssistantCheatPopulateWeapons extends BaseCommandPlugin {

    @Override
    public boolean doesCommandAddOptions() {
        return true;
    }

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        String option = params.get(0).getString(memoryMap).replace("ashesofohm_assistantCheatOptionWeapon","");

        int mode = 0;

        if (option.contains("Small")) {
            mode = 1;
        } else if (option.contains("Medium")) {
            mode = 2;
        } else if (option.contains("Large")) {
            mode = 3;
        }

        for (String k : omegaWeaponComponentMap.keySet())
        {
            if (getPlayerMemoryBool("haveDisassembled" + k, true))
                continue;

            if (omegaWeaponComponentMap.get(k) == mode) {
                dialog.getOptionPanel().addOption(Global.getSettings().getWeaponSpec(k).getWeaponName(), "ashesofohm_"+k);
            }
        }

        dialog.getOptionPanel().addOption("Cancel", "ashesofohm_assistantCheatOptionUnlockWeaponsCancel");

        return false;
    }
}
