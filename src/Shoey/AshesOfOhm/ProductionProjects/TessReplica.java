package Shoey.AshesOfOhm.ProductionProjects;

import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import data.kaysaar.aotd.vok.campaign.econ.globalproduction.scripts.AoTDSpecialProjBaseListener;

import static Shoey.AshesOfOhm.MainPlugin.getPlayerMemory;
import static Shoey.AshesOfOhm.MainPlugin.setPlayerMemory;

public class TessReplica extends AoTDSpecialProjBaseListener {

    @Override
    public FleetMemberAPI receiveReward(ShipHullSpecAPI specOfShip, CargoAPI cargo) {
        setPlayerMemory("constructedTesseractCount", (int) getPlayerMemory("constructedTesseractCount")+1);
        return super.receiveReward(specOfShip, cargo);
    }
}
