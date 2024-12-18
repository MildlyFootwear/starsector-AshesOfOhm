package Shoey.AshesOfOhm.HullMods.OmegaTech;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.util.IntervalUtil;

import java.awt.*;

public class HullModPristineForge extends BaseHullMod {


    protected static Object STATUSKEY = new Object();
    public static String MA_DATA_KEY = "$ashesofohm_core_reloader_data_key";
    private static float ammoRegenBonus = 75f;
    private static float missileRegenBonus = 75f;
    private static float reloadInterval = 60f;
    private static float reloadMin = 1f;
    private static float reloadPercentage = 25f;
    private static int TEXT_SIZE = 25;

    public static class MissileAutoforger {
        IntervalUtil interval = new IntervalUtil(reloadInterval, reloadInterval);
    }

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getBallisticAmmoRegenMult().modifyPercent(id, ammoRegenBonus);
//        stats.getEnergyAmmoRegenMult().modifyPercent(id, ammoRegenBonus);
        stats.getMissileAmmoRegenMult().modifyPercent(id, missileRegenBonus);
    }

    @Override
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if (index == 0)
        {
            return ""+(int)ammoRegenBonus+"%";
        } else if (index == 1) {
            return ""+(int)ammoRegenBonus;
        } else if (index == 2) {
            return ""+(int)reloadPercentage+"%";
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
        MissileAutoforger data = (MissileAutoforger)engine.getCustomData().get(key);
        if (data == null) {
            data = new MissileAutoforger();
            engine.getCustomData().put(key, data);
        }
        boolean canReload = false;
        for (WeaponAPI w : ship.getAllWeapons()) {
            if (w.getType() != WeaponAPI.WeaponType.MISSILE) continue;
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
                    if (w.getType() != WeaponAPI.WeaponType.MISSILE) continue;
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
                    Global.getSoundPlayer().playSound("system_forgevats", 1f, 1f, ship.getLocation(), ship.getVelocity());
                }
            } else if (ship == Global.getCombatEngine().getPlayerShip()) {
                Global.getCombatEngine().maintainStatusForPlayerShip(
                        STATUSKEY,
                        "graphics/icons/hullsys/missile_autoforge.png",
                        "Status: Forging Missiles",
                        ((int) reloadInterval - elapsed) + " until reload",
                        false
                );
            }
        }
    }

}
