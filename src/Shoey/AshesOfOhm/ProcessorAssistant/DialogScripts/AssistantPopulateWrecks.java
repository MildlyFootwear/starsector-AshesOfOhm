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
        return false;
    }

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        boolean textMode = false;

        if (params.get(0).string.contains("Text")) {
            textMode = true;
        }
        if (textMode) {
            dialog.getTextPanel().addPara("A list of ships that you have defeated and can salvage shows up on your display as well as projected salvage results.");
        }
        boolean canLearn = false;
        TooltipMakerAPI tooltip = null;

        if (textMode) {
            tooltip = dialog.getTextPanel().beginTooltip();
            tooltip.addPara("Wrecks available:", 0);
        }

        if (getPlayerMemoryInt("destroyedTesseractCount") > getPlayerMemoryInt("salvagedTesseractCount"))
        {
            if (textMode) {
                String s = "    Tesseract (x" + (getPlayerMemoryInt("destroyedTesseractCount") - getPlayerMemoryInt("salvagedTesseractCount")) + ")";
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
            if (textMode) {
                String s = "    Facet (x"+(getPlayerMemoryInt("destroyedFacetCount") - getPlayerMemoryInt("salvagedFacetCount"))+")";
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
            if (textMode) {
                String s = "    Shard (x"+(getPlayerMemoryInt("destroyedShardCount") - getPlayerMemoryInt("salvagedShardCount"))+")";
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
            if (canLearn) {
                tooltip.addPara("\nWrecks denoted with * will take longer to salvage but grant new opportunities", 0);
            }
            dialog.getTextPanel().addTooltip();
        }
        return false;
    }
}
