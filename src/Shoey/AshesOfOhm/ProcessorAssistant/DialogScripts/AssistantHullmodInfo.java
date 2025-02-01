package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import Shoey.AshesOfOhm.CheckMethods;
import Shoey.AshesOfOhm.MemoryShortcuts;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.loading.HullModSpecAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;


public class AssistantHullmodInfo extends BaseCommandPlugin {

    public static String Hullmod;

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        Hullmod = params.get(0).getString(memoryMap);
        HullModSpecAPI m = Global.getSettings().getHullModSpec(Hullmod);
        if (m != null)
        {
            TooltipMakerAPI tooltipMakerAPI = dialog.getTextPanel().beginTooltip();
            tooltipMakerAPI.addPara(m.getDescription(AssistantModificationSelectShip.modificationSubject.getHullSpec().getHullSize()), 0);
            dialog.getTextPanel().addTooltip();
        }
        boolean valid = true;
        if (Hullmod.equals("ashesofohm_timeAccelerator"))
        {
            if (MemoryShortcuts.getComponents() < 10) {
                dialog.getTextPanel().addPara("Requires 10 components. You only have " + MemoryShortcuts.getComponents() + ".", Misc.getHighlightColor());
                valid = false;
            }
        } else if (Hullmod.equals("ashesofohm_integratedNanoforge"))
        {
            if (MemoryShortcuts.getComponents() < 5) {
                dialog.getTextPanel().addPara("Requires 5 components. You only have " + MemoryShortcuts.getComponents() + ".", Misc.getHighlightColor());
                valid = false;
            }
            if (!CheckMethods.playerHasSpecialItem("corrupted_nanoforge"))
            {
                dialog.getTextPanel().addPara("Requires a Corrupted Nanoforge.", Misc.getHighlightColor());
                valid = false;
            }
        } else if (Hullmod.equals("ashesofohm_integratedPristineNanoforge"))
        {
            if (MemoryShortcuts.getComponents() < 5) {
                dialog.getTextPanel().addPara("Requires 5 components. You only have " + MemoryShortcuts.getComponents() + ".", Misc.getHighlightColor());
                valid = false;
            }
            if (!CheckMethods.playerHasSpecialItem("pristine_nanoforge"))
            {
                dialog.getTextPanel().addPara("Requires a Pristine Nanoforge.", Misc.getHighlightColor());
                valid = false;
            }
        } else if (Hullmod.equals("ashesofohm_integratedHypershuntTap"))
        {
            if (MemoryShortcuts.getComponents() < 5) {
                dialog.getTextPanel().addPara("Requires 5 components. You only have " + MemoryShortcuts.getComponents() + ".", Misc.getHighlightColor());
                valid = false;
            }
            if (!CheckMethods.playerHasSpecialItem("coronal_portal"))
            {
                dialog.getTextPanel().addPara("Requires a Hypershunt Tap.", Misc.getHighlightColor());
                valid = false;
            }
        }
        dialog.getOptionPanel().setEnabled("ashesofohm_assistantModifyOptionShipConfirm", valid);
        return false;
    }
}
