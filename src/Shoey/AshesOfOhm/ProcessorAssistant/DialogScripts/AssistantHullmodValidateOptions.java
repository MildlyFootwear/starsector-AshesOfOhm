package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import Shoey.AshesOfOhm.CheckMethods;
import Shoey.AshesOfOhm.MainPlugin;
import Shoey.AshesOfOhm.MemoryShortcuts;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;


public class AssistantHullmodValidateOptions extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        return false;
    }
}
