package Shoey.AshesOfOhm.HullMods.OmegaTech;

import Shoey.AshesOfOhm.MainPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.IntervalUtil;
import com.fs.starfarer.api.util.Misc;

import java.awt.*;

public class HullModHypershuntTap extends BaseHullMod {


    protected static Object STATUSKEY = new Object();
    public static String MA_DATA_KEY = "$ashesofohm_tap_reloader_data_key";
    private static float ammoRegenBonus = 50f;
    private static float ammoCapBonus = 100f;
    private static float frigateFluxCapBonus = 50f;
    private static float destroyerFluxCapBonus = 40f;
    private static float cruiserFluxCapBonus = 30f;
    private static float capitalFluxCapBonus = 20f;
    private static float reloadInterval = 60f;
    private static float reloadMin = 1f;
    private static float reloadPercentage = 25f;
    private static int TEXT_SIZE = 25;

    public static class ChargeReplenishment {
        IntervalUtil interval = new IntervalUtil(reloadInterval, reloadInterval);
    }

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getEnergyAmmoRegenMult().modifyPercent(id, ammoRegenBonus);
        stats.getEnergyAmmoBonus().modifyPercent(id, ammoCapBonus);
        switch (hullSize) {
            case FIGHTER:
            case FRIGATE:
                stats.getFluxCapacity().modifyPercent(id, frigateFluxCapBonus);
                break;
            case DESTROYER:
                stats.getFluxCapacity().modifyPercent(id, destroyerFluxCapBonus);
                break;
            case CRUISER:
                stats.getFluxCapacity().modifyPercent(id, cruiserFluxCapBonus);
                break;
            case DEFAULT:
            case CAPITAL_SHIP:
                stats.getFluxCapacity().modifyPercent(id, capitalFluxCapBonus);
                break;
        }

    }

    @Override
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        if (ship.getVariant().getSMods().contains("magazines") || ship.getVariant().getSModdedBuiltIns().contains("magazines"))
        {
            ship.getMutableStats().getEnergyAmmoRegenMult().unmodify(id);
        }
    }

    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
        if (ship != null) {
            if (ship.getVariant().getSMods().contains("magazines") || ship.getVariant().getSModdedBuiltIns().contains("magazines")) {
                tooltip.addPara("\nRegeneration bonus not applicable due to the presence of S-modded Expanded Magazines.", Misc.getNegativeHighlightColor(), 0);
            }
        }
        super.addPostDescriptionSection(tooltip, hullSize, ship, width, isForModSpec);
    }

    @Override
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0)
        {
            return (int) ammoRegenBonus+"%";
        } else if (index == 1) {
            return (int) ammoCapBonus+"%";
        } else if (index == 2) {
            return (int) frigateFluxCapBonus+"%";
        } else if (index == 3) {
            return (int) destroyerFluxCapBonus+"%";
        } else if (index == 4) {
            return (int) cruiserFluxCapBonus+"%";
        } else if (index == 5) {
            return (int) capitalFluxCapBonus+"%";
        }
        return super.getDescriptionParam(index, hullSize);
    }

    @Override
    public void advanceInCombat (ShipAPI ship, float amount) {
        CombatEngineAPI engine = Global.getCombatEngine();
        String key = MA_DATA_KEY + "_" + ship.getId();
        if (engine.isPaused() || !ship.isAlive()) {
            return;
        }
        ChargeReplenishment data = (ChargeReplenishment)engine.getCustomData().get(key);
        if (data == null) {
            data = new ChargeReplenishment();
            engine.getCustomData().put(key, data);
        }
        boolean canReload = false;
        for (WeaponAPI w : ship.getAllWeapons()) {
            if (w.getType() != WeaponAPI.WeaponType.ENERGY) continue;
            if (w.usesAmmo() && w.getAmmo() < w.getMaxAmmo() && w.getAmmoPerSecond() == 0) {
                canReload = true;
                break;
            }
        }
        if (canReload) {
            data.interval.advance(amount);
            int elapsed = Math.round(data.interval.getElapsed());
            if (data.interval.intervalElapsed()) {
                for (WeaponAPI w : ship.getAllWeapons()) {
                    if (w.getType() != WeaponAPI.WeaponType.ENERGY) continue;
                    int currentAmmo = w.getAmmo();
                    int maxAmmo = w.getMaxAmmo();
                    if (w.usesAmmo() && currentAmmo < maxAmmo && w.getAmmoPerSecond() == 0) {
                        int reloadCount = (int) Math.max(reloadMin, w.getSpec().getMaxAmmo() * (reloadPercentage / 100));
                        int newAmmo = currentAmmo + reloadCount;
                        if (newAmmo >= maxAmmo) {
                            reloadCount = maxAmmo - currentAmmo;
                            w.setAmmo(maxAmmo);
                        } else {
                            w.setAmmo(newAmmo);
                        }
                        engine.addFloatingText(w.getLocation(), "+" + reloadCount, TEXT_SIZE, Color.GREEN, ship, 0f, 0f);
                    }
                    Global.getSoundPlayer().playSound("system_entropy", 1f, 1f, ship.getLocation(), ship.getVelocity());
                }
            } else if (ship == Global.getCombatEngine().getPlayerShip()) {
                Global.getCombatEngine().maintainStatusForPlayerShip(
                        STATUSKEY,
                        "graphics/icons/hullsys/ammo_feeder.png",
                        "Status: Replenishing Energy Ammo",
                        ((int) reloadInterval - elapsed) + " until partial replenishment",
                        false
                );
            }
        }
    }

}
