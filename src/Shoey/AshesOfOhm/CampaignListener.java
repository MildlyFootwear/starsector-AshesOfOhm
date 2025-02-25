package Shoey.AshesOfOhm;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.AbilityPlugin;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.util.Misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static Shoey.AshesOfOhm.MemoryShortcuts.setPlayerMemory;

public class CampaignListener implements CampaignEventListener {

    public static MarketAPI currentMarket = null;
    public static boolean isAtPlayerMarket = false;
    public static int componentsToAdd = 0;

    public static void parseHullID(String hullID)
    {
        if (Objects.equals(hullID, "tesseract"))
        {
            setPlayerMemory("destroyedTesseractCount", MemoryShortcuts.getPlayerMemoryInt("destroyedTesseractCount") + 1);
            MainPlugin.log.info("Incrementing available Tesseract Replicas");
        } else if (Objects.equals(hullID, "facet"))
        {
            setPlayerMemory("destroyedFacetCount", MemoryShortcuts.getPlayerMemoryInt("destroyedFacetCount") + 1);
            MainPlugin.log.info("Incrementing available Facet Replicas");
        } else if (Objects.equals(hullID, "shard_right"))
        {
            setPlayerMemory("destroyedShardCount", MemoryShortcuts.getPlayerMemoryInt("destroyedShardCount") + 1);
            MainPlugin.log.info("Incrementing available Shard Replicas");
        } else if (Objects.equals(hullID, "shard_left"))
        {
            setPlayerMemory("destroyedShardCount", MemoryShortcuts.getPlayerMemoryInt("destroyedShardCount") + 1);
            MainPlugin.log.info("Incrementing available Shard Replicas");
        }
    }

    public static void addComponentsFromEntity(String hullID) {
        if (hullID.equals("remnant_station2"))
        {
            componentsToAdd += 1;
        }
    }

    @Override
    public void reportPlayerOpenedMarket(MarketAPI market) {
        currentMarket = market;
        if (market.isPlayerOwned()) {
            isAtPlayerMarket = true;
        }
    }

    @Override
    public void reportPlayerClosedMarket(MarketAPI market) {
        currentMarket = null;
        isAtPlayerMarket = false;
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

        List<FleetMemberAPI> fleetMemberGoned = new ArrayList<>();

        if (result.getWinnerResult().isPlayer()) {

            fleetMemberGoned.addAll(result.getLoserResult().getDisabled());
            fleetMemberGoned.addAll(result.getLoserResult().getDestroyed());

        } else if (result.getLoserResult().isPlayer()) {

            fleetMemberGoned.addAll(result.getWinnerResult().getDisabled());
            fleetMemberGoned.addAll(result.getWinnerResult().getDestroyed());

        }

        for (FleetMemberAPI fleetMemberAPI : fleetMemberGoned) {
            String hullID = fleetMemberAPI.getHullSpec().getBaseHullId();
            parseHullID(hullID);
            addComponentsFromEntity(hullID);
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

        CheckMethods.playerStatusChecks();
        if (componentsToAdd > 0) {
            MemoryShortcuts.addComponents(componentsToAdd);
            componentsToAdd = 0;
        }

        dialog.getTextPanel().addPara("You have recovered "+componentsToAdd+" Omega components from defeated entities.", Misc.getHighlightColor());
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

        CheckMethods.playerStatusChecks();

    }
}
