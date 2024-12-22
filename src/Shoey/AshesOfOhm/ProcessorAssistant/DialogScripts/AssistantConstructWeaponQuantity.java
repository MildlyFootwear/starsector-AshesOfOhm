package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import Shoey.AshesOfOhm.MemoryShortcuts;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

import static Shoey.AshesOfOhm.MainPlugin.*;

public class AssistantConstructWeaponQuantity extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        MemoryAPI mem = dialog.getInteractionTarget().getActivePerson().getMemory();
        int quantity = mem.getInt("$weaponQuantity");
        int quantToBe;
        if (params.get(0).string.equals("Set"))
        {
            quantToBe = Integer.parseInt(params.get(1).string);
        } else if (params.get(0).string.equals("Max"))
        {
            quantToBe = MemoryShortcuts.getPlayerMemoryInt("omegaWeaponPoints") / omegaWeaponComponentMap.get(mem.getString("$selectedWeapon").replace("ashesofohm_",""));
        } else if (params.get(0).string.equals("Add"))
        {
            quantToBe = quantity + Integer.parseInt(params.get(1).string);
        } else {
            log = Global.getLogger(this.getClass());
            log.error(params.get(0).string + " is not a recognized command.");
            return false;
        }
        mem.set("$weaponQuantity", quantToBe);
        mem.set("$weaponQuantityComponents", quantToBe * omegaWeaponComponentMap.get(mem.getString("$selectedWeapon").replace("ashesofohm_","")));
        return false;
    }
}
