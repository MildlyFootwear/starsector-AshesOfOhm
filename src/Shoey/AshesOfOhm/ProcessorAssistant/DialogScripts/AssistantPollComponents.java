package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

import static Shoey.AshesOfOhm.MemoryShortcuts.getPlayerMemoryInt;

public class AssistantPollComponents extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        int playerComponents = getPlayerMemoryInt("omegaWeaponPoints");
        dialog.getInteractionTarget().getMarket().getMemory().set("$ashesofohm_Components", playerComponents);
        TooltipMakerAPI tooltipMakerAPI = dialog.getTextPanel().beginTooltip();
        tooltipMakerAPI.addPara("You have " + playerComponents + " components available.\n", 0);
        tooltipMakerAPI.addPara("Component cost:\n" +
                "    Small weapons: 1\n" +
                "    Medium weapons: 2\n" +
                "    Large weapons: 3", 0);
        dialog.getTextPanel().addTooltip();
        return false;
    }
}
