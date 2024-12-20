package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.util.List;
import java.util.Map;

import static Shoey.AshesOfOhm.MainPlugin.omegaShipComponentMap;
import static Shoey.AshesOfOhm.MemoryShortcuts.getPlayerMemoryInt;

public class AssistantPopulateWrecks extends BaseCommandPlugin {

    @Override
    public boolean doesCommandAddOptions() {
        return true;
    }

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        boolean textMode = false;
        int foundWrecks = 0;
        if (params.get(0).string.contains("Text")) {
            textMode = true;
        }
        boolean canLearn = false;
        TooltipMakerAPI tooltip = null;

        if (textMode) {
            tooltip = dialog.getTextPanel().beginTooltip();
            tooltip.addPara("Wrecks available:", 0);
        }

        if (getPlayerMemoryInt("destroyedTesseractCount") > getPlayerMemoryInt("salvagedTesseractCount"))
        {
            int w = getPlayerMemoryInt("destroyedTesseractCount") - getPlayerMemoryInt("salvagedTesseractCount");
            foundWrecks += w;
            if (textMode) {
                String s = "    Tesseract (x" + (w) + ")";
                if (getPlayerMemoryInt("salvagedTesseractCount") == 0) {
                    s += "*";
                    canLearn = true;
                }
                s += ": "+omegaShipComponentMap.get("Tesseract");
                tooltip.addPara(s, 0);
            } else {
                dialog.getOptionPanel().addOption("Tesseract", "ashesofohm_salvageTesseract");
            }
        }

        if (getPlayerMemoryInt("destroyedFacetCount") > getPlayerMemoryInt("salvagedFacetCount"))
        {
            int w = getPlayerMemoryInt("destroyedFacetCount") - getPlayerMemoryInt("salvagedFacetCount");
            foundWrecks += w;

            if (textMode) {
                String s = "    Facet (x"+(w)+")";
                if (getPlayerMemoryInt("salvagedFacetCount") == 0) {
                    s += "*";
                    canLearn = true;
                }
                s += ": "+omegaShipComponentMap.get("Facet");
                tooltip.addPara(s, 0);
            } else {
                dialog.getOptionPanel().addOption("Facet", "ashesofohm_salvageFacet");
            }
        }

        if (getPlayerMemoryInt("destroyedShardCount") > getPlayerMemoryInt("salvagedShardCount"))
        {
            int w = getPlayerMemoryInt("destroyedShardCount") - getPlayerMemoryInt("salvagedShardCount");
            foundWrecks += w;

            if (textMode) {
                String s = "    Shard (x"+(w)+")";
                if (getPlayerMemoryInt("salvagedShardCount") == 0) {
                    s += "*";
                    canLearn = true;
                }
                s += ": "+omegaShipComponentMap.get("Shard");
                tooltip.addPara(s, 0);
            } else {
                dialog.getOptionPanel().addOption("Shard", "ashesofohm_salvageShard");
            }
        }

        if (textMode) {
            if (foundWrecks > 0) {
                dialog.getTextPanel().addPara("A list of ships that you have defeated and can salvage shows up on your display as well as projected salvage results.");
                if (canLearn) {
                    tooltip.addPara("\nWrecks denoted with * will take longer to salvage but grant new opportunities", 0);
                }
                dialog.getTextPanel().addTooltip();
            } else {
                dialog.getTextPanel().addPara("\"Unfortunately, there are no wrecks available to salvage.\"");
            }
        } else {
            dialog.getOptionPanel().addOption("Cancel", "ashesofohm_assistantSalvageOptionShipCancel");
        }
        return false;
    }
}
