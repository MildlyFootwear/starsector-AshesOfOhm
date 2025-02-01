package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import Shoey.AshesOfOhm.MemoryShortcuts;
import Shoey.AshesOfOhm.ProcessorAssistant.IngameScripts.ConstructWeapon;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

import static Shoey.AshesOfOhm.MainPlugin.omegaWeaponComponentMap;

public class AssistantCheatAddComponents extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        int cnt = Integer.parseInt(params.get(0).getString(memoryMap).replace("ashesofohm_assistantCheatOptionAddComponents",""));
        MemoryShortcuts.addComponents(cnt);
        dialog.getTextPanel().addPara("Components: "+MemoryShortcuts.getComponents());
        return false;
    }
}
