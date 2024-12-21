package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import Shoey.AshesOfOhm.MemoryShortcuts;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;


public class AssistantGreetingUpdate extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        MarketAPI m = dialog.getInteractionTarget().getMarket();
        int productionTickOffset = 0;

        if (m.hasIndustry("triheavy") || m.hasIndustry("hegeheavy"))
        {
            productionTickOffset -= 5;
        } else if (m.hasIndustry("orbitalheavy"))
        {
            productionTickOffset -= 4;
        } else if (m.hasIndustry("orbitalworks") || m.hasIndustry("stella_manufactorium"))
        {
            productionTickOffset -= 3;
        } else if (m.hasIndustry("heavyindustry") || m.hasIndustry("supplyheavy"))
        {
            productionTickOffset -= 2;
        } else if (m.hasIndustry("shityheavy"))
        {
            productionTickOffset -= 1;
        }

        m.getMemory().set("$ashesofohm_Components", MemoryShortcuts.getPlayerMemoryInt("omegaWeaponPoints"));
        m.getMemory().set("$ashesofohm_productionRateOffset", productionTickOffset);
        m.getMemory().set("$ashesofohm_marketRateOffset", 3-Math.min(m.getSize(), 6));

        return false;
    }
}
