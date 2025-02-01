package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

import static Shoey.AshesOfOhm.MainPlugin.omegaShips;
import static Shoey.AshesOfOhm.MemoryShortcuts.getPlayerMemoryBool;
import static Shoey.AshesOfOhm.MemoryShortcuts.getPlayerMemoryInt;

public class AssistantCheatPopulateShips extends BaseCommandPlugin {

    @Override
    public boolean doesCommandAddOptions() {
        return true;
    }

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        for (String s : omegaShips) {
                dialog.getOptionPanel().addOption(s, "ashesofohm_addWreck"+s);
        }
        dialog.getOptionPanel().addOption("Cancel", "ashesofohm_assistantCheatOptionShipCancel");
        return false;
    }
}
