package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import Shoey.AshesOfOhm.ProcessorAssistant.IngameScripts.ConstructShip;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Shoey.AshesOfOhm.MainPlugin.*;

public class AssistantOpenShipProjectOptions extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        String tt = "We have "+getPlayerMemoryInt("omegaWeaponPoints")+" components available.\n\nComponent cost:";
        for (String s : omegaShipComponentMap.keySet()) {
            if (getPlayerMemoryBool("haveSalvaged" + s)) {
                dialog.getOptionPanel().addOption(s, "ashesofohm_beginConstruction"+s);
                tt += "\n"+s+": "+omegaShipComponentMap.get(s);
                if (getPlayerMemoryInt("omegaWeaponPoints") < omegaShipComponentMap.get(s)) {
                    dialog.getOptionPanel().setEnabled("ashesofohm_beginConstruction"+s, false);
                }
                if (getPlayerMemoryBool("canConstructSalvaged"+s)) {
                    dialog.getOptionPanel().setEnabled("ashesofohm_beginConstruction"+s, false);
                    tt += ". Already prepared for construction or construction is in progress.";
                }
            }
        }
        if (!tt.equals("We have "+getPlayerMemoryInt("omegaWeaponPoints")+" components available.\n\nComponent cost:"))
        {
            dialog.getTextPanel().beginTooltip().addPara(tt, 0);
            dialog.getTextPanel().addTooltip();
        } else {
            dialog.getTextPanel().addPara("\"Unfortunately, we do not know of any ships to begin working on.\n");
        }
        return false;
    }
}
