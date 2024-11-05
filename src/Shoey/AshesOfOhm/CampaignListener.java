package Shoey.AshesOfOhm;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.AbilityPlugin;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;

import java.util.Objects;

import static Shoey.AshesOfOhm.MainPlugin.*;

public class CampaignListener implements CampaignEventListener {

    void parseHullID(String hullID)
    {
        if (Objects.equals(hullID, "tesseract"))
        {
            setPlayerMemory("destroyedTesseractCount", (int) getPlayerMemory("destroyedTesseractCount")+1);
            MainPlugin.log.info("Incrementing available Tesseract Replicas");
        } else if (Objects.equals(hullID, "facet"))
        {
            setPlayerMemory("destroyedFacetCount", (int) getPlayerMemory("destroyedFacetCount")+1);
            MainPlugin.log.info("Incrementing available Facet Replicas");
        } else if (Objects.equals(hullID, "shard_right"))
        {
            setPlayerMemory("destroyedShardCount", (int) getPlayerMemory("destroyedShardCount")+1);
            MainPlugin.log.info("Incrementing available Shard Replicas");
        } else if (Objects.equals(hullID, "shard_left"))
        {
            setPlayerMemory("destroyedShardCount", (int) getPlayerMemory("destroyedShardCount")+1);
            MainPlugin.log.info("Incrementing available Shard Replicas");
        }
    }

    @Override
    public void reportPlayerOpenedMarket(MarketAPI market) {

    }

    @Override
    public void reportPlayerClosedMarket(MarketAPI market) {

    }

    @Override
    public void reportPlayerOpenedMarketAndCargoUpdated(MarketAPI market) {

    }

    @Override
    public void reportEncounterLootGenerated(FleetEncounterContextPlugin plugin, CargoAPI loot) {

    }

    @Override
    public void reportPlayerMarketTransaction(PlayerMarketTransaction transaction) {

    }

    @Override
    public void reportBattleOccurred(CampaignFleetAPI primaryWinner, BattleAPI battle) {

    }

    @Override
    public void reportBattleFinished(CampaignFleetAPI primaryWinner, BattleAPI battle) {

    }

    @Override
    public void reportPlayerEngagement(EngagementResultAPI result) {

        if (result.getWinnerResult().isPlayer()) {
            for (FleetMemberAPI fleetMemberAPI : result.getLoserResult().getDisabled()) {
                String hullID = fleetMemberAPI.getHullSpec().getBaseHullId();
                parseHullID(hullID);
            }
            for (FleetMemberAPI fleetMemberAPI : result.getLoserResult().getDestroyed()) {
                String hullID = fleetMemberAPI.getHullSpec().getBaseHullId();
                parseHullID(hullID);
            }
        } else if (result.getLoserResult().isPlayer()) {
            for (FleetMemberAPI fleetMemberAPI : result.getWinnerResult().getDisabled()) {
                String hullID = fleetMemberAPI.getHullSpec().getBaseHullId();
                parseHullID(hullID);
            }
            for (FleetMemberAPI fleetMemberAPI : result.getWinnerResult().getDestroyed()) {
                String hullID = fleetMemberAPI.getHullSpec().getBaseHullId();
                parseHullID(hullID);
            }
        }

    }

    @Override
    public void reportFleetDespawned(CampaignFleetAPI fleet, FleetDespawnReason reason, Object param) {

    }

    @Override
    public void reportFleetSpawned(CampaignFleetAPI fleet) {

    }

    @Override
    public void reportFleetReachedEntity(CampaignFleetAPI fleet, SectorEntityToken entity) {

    }

    @Override
    public void reportFleetJumped(CampaignFleetAPI fleet, SectorEntityToken from, JumpPointAPI.JumpDestination to) {

    }

    @Override
    public void reportShownInteractionDialog(InteractionDialogAPI dialog) {

    }

    @Override
    public void reportPlayerReputationChange(String faction, float delta) {

    }

    @Override
    public void reportPlayerReputationChange(PersonAPI person, float delta) {

    }

    @Override
    public void reportPlayerActivatedAbility(AbilityPlugin ability, Object param) {

    }

    @Override
    public void reportPlayerDeactivatedAbility(AbilityPlugin ability, Object param) {

    }

    @Override
    public void reportPlayerDumpedCargo(CargoAPI cargo) {

    }

    @Override
    public void reportPlayerDidNotTakeCargo(CargoAPI cargo) {

    }

    @Override
    public void reportEconomyTick(int iterIndex) {

    }

    @Override
    public void reportEconomyMonthEnd() {

        shuntChecks();

    }
}
