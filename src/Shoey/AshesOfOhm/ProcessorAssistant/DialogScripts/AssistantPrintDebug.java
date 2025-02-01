package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import Shoey.AshesOfOhm.MainPlugin;
import Shoey.AshesOfOhm.MemoryShortcuts;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

import static Shoey.AshesOfOhm.MemoryShortcuts.getComponents;

public class AssistantPrintDebug extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        String s = "Wrecks:\n";

        for (String ship : MainPlugin.omegaShips)
        {
            s += "\ndestroyed"+ship+"Count: "+ MemoryShortcuts.getPlayerMemoryInt("destroyed"+ship+"Count");
            s += "\nsalvaged"+ship+"Count: "+ MemoryShortcuts.getPlayerMemoryInt("salvaged"+ship+"Count");
            s += "\nhaveSalvaged"+ship+": "+ MemoryShortcuts.getPlayerMemoryBool("haveSalvaged"+ship);
        }

        s += "\n";

        for (String weapon : MainPlugin.omegaWeaponIDs)
        {
            s += "\nhaveDisassembled"+weapon+": "+MemoryShortcuts.getPlayerMemoryBool("haveDisassembled"+weapon);
        }

        s += "\nComponents: "+getComponents();

        dialog.getTextPanel().addPara(s);
        return false;
    }
}
