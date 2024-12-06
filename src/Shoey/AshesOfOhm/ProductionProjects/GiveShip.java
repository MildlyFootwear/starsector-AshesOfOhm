package Shoey.AshesOfOhm.ProductionProjects;

import Shoey.AshesOfOhm.MemoryShortcuts;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import data.kaysaar.aotd.vok.campaign.econ.globalproduction.scripts.AoTDSpecialProjBaseListener;

import static Shoey.AshesOfOhm.MainPlugin.*;

public class GiveShip extends AoTDSpecialProjBaseListener {

    @Override
    public FleetMemberAPI receiveReward(ShipHullSpecAPI specOfShip, CargoAPI cargo) {
        String shipName = specOfShip.getHullId().replace("ashesofohm_","");
        shipName = Character.toUpperCase(shipName.charAt(0))+shipName.substring(1);
        if (shipName.contains("Shard")) {
            shipName = shipName.replace("_left", "").replace("_right", "");
        }
        MemoryShortcuts.setPlayerMemory("canConstructSalvaged" + shipName, false);
        log.info("Player has completed construction project for: "+shipName+":"+specOfShip.getHullId());
        MemoryShortcuts.setPlayerMemory("constructed" + shipName + "Count", MemoryShortcuts.getPlayerMemoryInt("constructed" + shipName + "Count") + 1);
        return super.receiveReward(specOfShip, cargo);
    }
}
