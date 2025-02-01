package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import Shoey.AshesOfOhm.MemoryShortcuts;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

import static Shoey.AshesOfOhm.MainPlugin.omegaShipComponentMap;
import static Shoey.AshesOfOhm.MemoryShortcuts.*;


public class AssistantCheatAddWrecks extends BaseCommandPlugin {

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        String shipName = params.get(0).getString(memoryMap).replace("ashesofohm_addWreck","");
        if (omegaShipComponentMap.containsKey(shipName))
        {
            setPlayerMemory("destroyed"+shipName+"Count", MemoryShortcuts.getPlayerMemoryInt("destroyed"+shipName+"Count") + 1);
        } else {
            dialog.getTextPanel().addPara(shipName+" not found in ship map. Please bug report on forum thread.", Misc.getNegativeHighlightColor());
        }
        return false;
    }
}
