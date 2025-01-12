package Shoey.AshesOfOhm.HullMods.OmegaTech;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

public class HullModTimeAccelerator extends BaseHullMod {

    float timeMultiplier = 1.15f;

    @Override
    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getTimeMult().modifyMult(id, timeMultiplier);
    }

    @Override
    public void advanceInCombat(ShipAPI ship, float amount) {
        super.advanceInCombat(ship, amount);
        if (ship == Global.getCombatEngine().getPlayerShip())
        {
            Global.getCombatEngine().getTimeMult().modifyMult(this.getClass().getName(), 1 / timeMultiplier);
        } else {
            Global.getCombatEngine().getTimeMult().unmodify(this.getClass().getName());
        }
    }

    @Override
    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return timeMultiplier+"x";
        return null;
    }

}
