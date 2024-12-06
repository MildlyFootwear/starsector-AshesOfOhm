package Shoey.AshesOfOhm.ProcessorAssistant;

import Shoey.AshesOfOhm.MemoryShortcuts;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.InteractionDialogPlugin;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static Shoey.AshesOfOhm.MainPlugin.omegaWeaponComponentMap;

public class WeaponAssemblyDialogPlugin implements InteractionDialogPlugin {

    InteractionDialogAPI d;
    List<String> weaponS = new ArrayList<>();
    List<String> weaponM = new ArrayList<>();
    List<String> weaponL = new ArrayList<>();

    @Override
    public void init(InteractionDialogAPI dialog) {
        d = dialog;

        for (String k : omegaWeaponComponentMap.keySet())
        {
            if (omegaWeaponComponentMap.get(k) == 1) {
                weaponS.add(k);
            } else if (omegaWeaponComponentMap.get(k) == 2) {
                weaponM.add(k);
            } else if (omegaWeaponComponentMap.get(k) == 3) {
                weaponL.add(k);
            }
        }
        TooltipMakerAPI tooltipMakerAPI = dialog.getTextPanel().beginTooltip();
        int playerComponents = MemoryShortcuts.getPlayerMemoryInt("omegaWeaponPoints");
        tooltipMakerAPI.addPara("You have " + playerComponents + " components available.", 0);
        tooltipMakerAPI.addPara("Component cost:\n" +
                "    Small weapons: 1\n" +
                "    Medium weapons: 2\n" +
                "    Large weapons: 3", 0);
        dialog.getTextPanel().addTooltip();
        dialog.getOptionPanel().addOption("Cancel","Cancel");
    }

    @Override
    public void optionSelected(String optionText, Object optionData) {

        switch ((String) optionData) {
            case "Cancel":
                d.dismiss();
                break;

        }

    }

    @Override
    public void optionMousedOver(String optionText, Object optionData) {

    }

    @Override
    public void advance(float amount) {

    }

    @Override
    public void backFromEngagement(EngagementResultAPI battleResult) {

    }

    @Override
    public Object getContext() {
        return null;
    }

    @Override
    public Map<String, MemoryAPI> getMemoryMap() {
        return Collections.emptyMap();
    }
}
