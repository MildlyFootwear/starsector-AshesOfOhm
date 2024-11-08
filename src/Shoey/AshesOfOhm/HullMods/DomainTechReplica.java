package Shoey.AshesOfOhm.HullMods;

import Shoey.AshesOfOhm.MainPlugin;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

public class DomainTechReplica extends BaseHullMod {

    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getSuppliesToRecover().modifyPercent(id, 100f);
        stats.getSuppliesPerMonth().modifyPercent(id, -50f);
        stats.getBreakProb().modifyPercent(id, -100f);
        stats.getDynamic().getMod(Stats.INDIVIDUAL_SHIP_RECOVERY_MOD).modifyFlat(id, 1000f);
    }

    public String getDescriptionParam(int index, HullSize hullSize) {
//        if (index == 0) return "" + (int) stats + "%";
        return null;
    }

    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {

    }

}
