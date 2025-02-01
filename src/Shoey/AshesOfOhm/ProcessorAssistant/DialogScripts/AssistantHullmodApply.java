package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import Shoey.AshesOfOhm.CheckMethods;
import Shoey.AshesOfOhm.MainPlugin;
import Shoey.AshesOfOhm.MemoryShortcuts;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

import static Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts.AssistantHullmodInfo.Hullmod;


public class AssistantHullmodApply extends BaseCommandPlugin {

    public static void removePlayerSpecialItem(String id) {
        CampaignFleetAPI playerFleet = Global.getSector().getPlayerFleet();
        if (playerFleet == null) {
            return;
        }
        List<CargoStackAPI> playerCargoStacks = playerFleet.getCargo().getStacksCopy();
        for (final CargoStackAPI cargoStack : playerCargoStacks) {
            if (cargoStack.isSpecialStack() && cargoStack.getSpecialDataIfSpecial().getId().equals(id)) {
                cargoStack.subtract(1);
                if (cargoStack.getSize() <= 0) {
                    playerFleet.getCargo().removeStack(cargoStack);
                }
                return;
            }
        }
    }

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        String option = Hullmod;
        MainPlugin.log.info("Applying "+option+" to "+AssistantModificationSelectShip.modificationSubject.getShipName());
        if (Global.getSettings().getHullModSpec(option) != null) {
            AssistantModificationSelectShip.modificationSubject.getVariant().addPermaMod(option);
        }
        if (Hullmod.equals("ashesofohm_timeAccelerator"))
        {
            MemoryShortcuts.removeComponents(10);
        } else if (Hullmod.equals("ashesofohm_integratedNanoforge"))
        {
            MemoryShortcuts.removeComponents(5);
            removePlayerSpecialItem("corrupted_nanoforge");
        } else if (Hullmod.equals("ashesofohm_integratedPristineNanoforge"))
        {
            MemoryShortcuts.removeComponents(5);
            removePlayerSpecialItem("corrupted_nanoforge");
        } else if (Hullmod.equals("ashesofohm_integratedHypershuntTap"))
        {
            MemoryShortcuts.removeComponents(5);
            removePlayerSpecialItem("corrupted_nanoforge");
        }
        return false;
    }
}
