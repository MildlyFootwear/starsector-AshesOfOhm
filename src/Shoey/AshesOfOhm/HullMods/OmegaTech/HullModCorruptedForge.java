package Shoey.AshesOfOhm.HullMods.OmegaTech;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.util.IntervalUtil;

import java.awt.*;

public class HullModCorruptedForge extends BaseHullMod {

    private static float ammoRegenBonus = 75f;
    private static float missileRegenBonus = 75f;

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
//        stats.getEnergyAmmoRegenMult().modifyPercent(id, ammoRegenBonus);
        stats.getBallisticAmmoRegenMult().modifyPercent(id, ammoRegenBonus);
        stats.getMissileAmmoRegenMult().modifyPercent(id, missileRegenBonus);
    }

    @Override
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0)
        {
            return ""+(int)ammoRegenBonus+"%";
        }
        return super.getDescriptionParam(index, hullSize);
    }


}
