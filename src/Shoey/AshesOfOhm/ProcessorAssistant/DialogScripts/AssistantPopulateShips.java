package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

import static Shoey.AshesOfOhm.MainPlugin.omegaShipComponentMap;
import static Shoey.AshesOfOhm.MainPlugin.omegaShips;
import static Shoey.AshesOfOhm.MemoryShortcuts.getPlayerMemoryBool;
import static Shoey.AshesOfOhm.MemoryShortcuts.getPlayerMemoryInt;

public class AssistantPopulateShips extends BaseCommandPlugin {

    @Override
    public boolean doesCommandAddOptions() {
        return true;
    }

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        String t = "\"Confirmed, here is a list of the ships we can prepare to produce.\"";
        String tt = "We have "+ getPlayerMemoryInt("omegaWeaponPoints") +" components available.\n\nComponent cost:";
        for (String s : omegaShips) {
            if (getPlayerMemoryBool("haveSalvaged" + s)) {
                dialog.getOptionPanel().addOption(s, "ashesofohm_beginConstruction"+s);
                tt += "\n"+s+": "+(omegaShipComponentMap.get(s) * 2);
                if (getPlayerMemoryInt("omegaWeaponPoints") < omegaShipComponentMap.get(s) * 2) {
                    dialog.getOptionPanel().setEnabled("ashesofohm_beginConstruction"+s, false);
                }
                if (getPlayerMemoryBool("canConstructSalvaged" + s)) {
                    dialog.getOptionPanel().setEnabled("ashesofohm_beginConstruction"+s, false);
                    tt += ". Already prepared for construction or construction is in progress.";
                }
            }
        }
        if (!tt.equals("We have "+ getPlayerMemoryInt("omegaWeaponPoints") +" components available.\n\nComponent cost:"))
        {
            dialog.getTextPanel().addPara(t);
            dialog.getTextPanel().beginTooltip().addPara(tt, 0);
            dialog.getTextPanel().addTooltip();
        } else {
            dialog.getTextPanel().addPara("\"Unfortunately, we do not know of any ships to begin working on. Once we have salvaged some in our facilities we will be able to start the process of replicating their technology.\"");
        }
        dialog.getOptionPanel().addOption("Cancel", "ashesofohm_assistantPrepareOptionShipCancel");
        return false;
    }
}
