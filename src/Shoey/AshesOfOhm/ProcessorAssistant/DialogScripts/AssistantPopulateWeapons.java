package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

import static Shoey.AshesOfOhm.MainPlugin.getPlayerMemoryBool;
import static Shoey.AshesOfOhm.MainPlugin.omegaWeaponComponentMap;

public class AssistantPopulateWeapons extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        dialog.getTextPanel().addPara("\"Querying known "+params.get(0).string.toLowerCase()+" arms...\"");
        dialog.getTextPanel().addPara("A list of weapons shows up on your display.");

        for (String k : omegaWeaponComponentMap.keySet())
        {
            if (!getPlayerMemoryBool("haveDisassembled"+k, true))
                continue;

            if (omegaWeaponComponentMap.get(k) == 1 && params.get(0).string.contains("Small")) {
                dialog.getOptionPanel().addOption(Global.getSettings().getWeaponSpec(k).getWeaponName(), "ashesofohm_"+k);
            } else if (omegaWeaponComponentMap.get(k) == 2 && params.get(0).string.contains("Medium")) {
                dialog.getOptionPanel().addOption(Global.getSettings().getWeaponSpec(k).getWeaponName(), "ashesofohm_"+k);
            } else if (omegaWeaponComponentMap.get(k) == 3 && params.get(0).string.contains("Large")) {
                dialog.getOptionPanel().addOption(Global.getSettings().getWeaponSpec(k).getWeaponName(), "ashesofohm_"+k);
            }
        }

        return false;
    }
}
