package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import Shoey.AshesOfOhm.MemoryShortcuts;
import Shoey.AshesOfOhm.ProcessorAssistant.IngameScripts.ConstructShip;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

import static Shoey.AshesOfOhm.MainPlugin.omegaShipComponentMap;
import static Shoey.AshesOfOhm.MemoryShortcuts.setPlayerMemory;

public class AssistantOpenShipProject extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        String option = params.get(0).getString(memoryMap).replace("ashesofohm_beginConstruction","");
        if (omegaShipComponentMap.containsKey(option))
        {
            int comp = (omegaShipComponentMap.get(option));
            dialog.getTextPanel().addPara("\"Confirmed. The project for "+option+" has been opened. You will be able to direct the efforts through your standard Production Project interface.\"");
            setPlayerMemory("canConstructSalvaged" + option, true);
            MemoryShortcuts.removeComponents(comp * 2);
        } else if (option.contains("Cancel")) {
            dialog.getTextPanel().addPara("\"Confirmed, order canceled. Returning to root directory.\"");
        } else {
            Logger log = Global.getLogger(this.getClass());
            log.error(ruleId+" not recognized.");
        }
        return false;
    }
}
