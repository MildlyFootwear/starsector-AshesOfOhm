package Shoey.AshesOfOhm.HullMods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class DomainTechReplica extends BaseHullMod {

    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getSuppliesToRecover().modifyPercent(id, 100f);
        stats.getSuppliesPerMonth().modifyPercent(id, -50f);
        stats.getBreakProb().modifyPercent(id, -100f);
    }

    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return "" + (int) -50f + "%";
        return null;
    }

}
