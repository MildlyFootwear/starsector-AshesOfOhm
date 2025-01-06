package Shoey.AshesOfOhm.HullMods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

public class DomainTechReplica extends BaseHullMod {

    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getSuppliesPerMonth().modifyMult(id, 0.5f);
        stats.getBreakProb().modifyMult(id, 0f);
        stats.getDynamic().getMod(Stats.INDIVIDUAL_SHIP_RECOVERY_MOD).modifyFlat(id, 1000f);
    }

    @Override
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        super.applyEffectsAfterShipCreation(ship, id);
        if (ship.getVariant().hasHullMod("safetyoverrides")) {
            ship.getVariant().removeMod("safetyoverrides");
            Global.getSoundPlayer().playUISound("ui_button_disabled_pressed", 1f, 1f);
        }
    }

    public String getDescriptionParam(int index, HullSize hullSize) {
//        if (index == 0) return "" + (int) stats + "%";
        return null;
    }

}
