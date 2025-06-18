package Shoey.AshesOfOhm.ProductionProjects;

import Shoey.AshesOfOhm.MemoryShortcuts;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.kaysaar.aotd.vok.misc.AoTDMisc;
import data.kaysaar.aotd.vok.scripts.specialprojects.models.AoTDSpecialProject;

public class SalvageShardRight extends AoTDSpecialProject {

    @Override
    public void createRewardSection(TooltipMakerAPI tooltip, float width) {
        tooltip.addPara("Gain salvaged variant of the " + Global.getSettings().getHullSpec("ashesofohm_shard_right").getHullNameWithDashClass() + " vessel.", Misc.getPositiveHighlightColor(), 5.0F);
    }

    @Override
    public boolean checkIfProjectShouldUnlock() {
        return MemoryShortcuts.getPlayerMemoryBool("haveSalvagedShard");
    }

    @Override
    public boolean shouldShowOnUI() {
        return MemoryShortcuts.getPlayerMemoryBool("canConstructSalvagedShard");
    }

    @Override
    public void grantReward() {

        MemoryShortcuts.setPlayerMemory("canConstructSalvagedShard", false);

        MarketAPI gatheringPoint = Global.getSector().getPlayerFaction().getProduction().getGatheringPoint();
        if (gatheringPoint == null) {
            gatheringPoint = Misc.getPlayerMarkets(true).get(0);
        }

        CargoAPI cargo = gatheringPoint.getSubmarket("storage").getCargo();
        FleetMemberAPI fleet = cargo.getMothballedShips().addFleetMember(AoTDMisc.getVaraint(Global.getSettings().getHullSpec("ashesofohm_shard_right")));
        fleet.getVariant().clear();
    }

}
