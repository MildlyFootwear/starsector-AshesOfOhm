package Shoey.AshesOfOhm.ProductionProjects;

import Shoey.AshesOfOhm.MemoryShortcuts;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import data.kaysaar.aotd.vok.campaign.econ.globalproduction.scripts.AoTDSpecialProjBaseListener;

public class TessReplica extends AoTDSpecialProjBaseListener {

    @Override
    public FleetMemberAPI receiveReward(ShipHullSpecAPI specOfShip, CargoAPI cargo) {
        MemoryShortcuts.setPlayerMemory("constructedTesseractCount", MemoryShortcuts.getPlayerMemoryInt("constructedTesseractCount") + 1);
        return super.receiveReward(specOfShip, cargo);
    }
}
