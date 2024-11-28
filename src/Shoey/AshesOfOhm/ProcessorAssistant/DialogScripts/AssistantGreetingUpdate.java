package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

import static Shoey.AshesOfOhm.MainPlugin.getPlayerMemoryBool;
import static Shoey.AshesOfOhm.MainPlugin.omegaWeaponComponentMap;

public class AssistantGreetingUpdate extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        MarketAPI m = dialog.getInteractionTarget().getMarket();
        m.getMemory().set("$ashesofohm_marketRateOffset", 3-Math.min(m.getSize(), 7));

        return false;
    }
}
