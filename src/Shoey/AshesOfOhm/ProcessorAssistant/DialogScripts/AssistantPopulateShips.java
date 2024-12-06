package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import Shoey.AshesOfOhm.MemoryShortcuts;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

import static Shoey.AshesOfOhm.MainPlugin.*;

public class AssistantPopulateShips extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        String tt = "We have "+ MemoryShortcuts.getPlayerMemoryInt("omegaWeaponPoints") +" components available.\n\nComponent cost:";
        for (String s : omegaShips) {
            if (MemoryShortcuts.getPlayerMemoryBool("haveSalvaged" + s)) {
                dialog.getOptionPanel().addOption(s, "ashesofohm_beginConstruction"+s);
                tt += "\n"+s+": "+(omegaShipComponentMap.get(s) * 2);
                if (MemoryShortcuts.getPlayerMemoryInt("omegaWeaponPoints") < omegaShipComponentMap.get(s) * 2) {
                    dialog.getOptionPanel().setEnabled("ashesofohm_beginConstruction"+s, false);
                }
                if (MemoryShortcuts.getPlayerMemoryBool("canConstructSalvaged" + s)) {
                    dialog.getOptionPanel().setEnabled("ashesofohm_beginConstruction"+s, false);
                    tt += ". Already prepared for construction or construction is in progress.";
                }
            }
        }
        if (!tt.equals("We have "+ MemoryShortcuts.getPlayerMemoryInt("omegaWeaponPoints") +" components available.\n\nComponent cost:"))
        {
            dialog.getTextPanel().beginTooltip().addPara(tt, 0);
            dialog.getTextPanel().addTooltip();
        } else {
            dialog.getTextPanel().addPara("\"Unfortunately, we do not know of any ships to begin working on.\n");
        }
        return false;
    }
}
