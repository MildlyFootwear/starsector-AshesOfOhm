package Shoey.AshesOfOhm.ProcessorAssistant.DialogScripts;

import Shoey.AshesOfOhm.MainPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FleetMemberPickerListener;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemKeys;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AssistantModificationSelectShip extends BaseCommandPlugin {

    public static FleetMemberAPI modificationSubject;

    @Override
    public boolean execute(String ruleId, final InteractionDialogAPI dialog, List<Misc.Token> params, final Map<String, MemoryAPI> memoryMap) {

        List<FleetMemberAPI> members = new ArrayList<>();

        for (FleetMemberAPI memberAPI : Global.getSector().getPlayerFleet().getFleetData().getMembersListCopy())
        {
            boolean valid = true;
            for (String id : memberAPI.getVariant().getHullMods())
            {
                if (id.startsWith("ashesofohm_")) {
                    valid = false;
                    break;
                }
            }
            if (valid)
            {
                members.add(memberAPI);
            }
        }

        if (members.size() == 0)
        {
            dialog.getTextPanel().addPara("\"All the ships in your fleet already contain experimental technology.\"");
            return false;
        }

        int cols = Math.max(Math.min(members.size(), 7), 4);
        if (members.size() > 30) {
            cols = 12;
        }
        int rows = (members.size() - 1) / cols + 1;

        dialog.showFleetMemberPickerDialog("Select Ship to Modify", "Confirm", "Cancel", rows, cols, 96, true, false, members, new FleetMemberPickerListener() {
            @Override
            public void pickedFleetMembers(List<FleetMemberAPI> members) {
                if (members.size() == 0) {
                    return;
                }
                dialog.getVisualPanel().showFleetMemberInfo(members.get(0));
                modificationSubject = members.get(0);
                dialog.getPlugin().optionSelected("", "ashesofohm_assistantModifyOptionShipSelected");
            }

            @Override
            public void cancelledFleetMemberPicking() {

            }
        });

        return false;
    }
}
