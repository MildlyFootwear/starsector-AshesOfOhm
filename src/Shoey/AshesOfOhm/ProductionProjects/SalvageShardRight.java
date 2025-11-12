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

        if (!canDoProject())
        {
            tooltip.addPara("Speak to the assistant to prepare this project.", Misc.getNegativeHighlightColor(), 5.0F);
        }
    }

    @Override
    public boolean checkIfProjectShouldUnlock() {
        return MemoryShortcuts.getPlayerMemoryBool("haveSalvagedShard", true);
    }

    @Override
    public boolean shouldShowOnUI() {
        return MemoryShortcuts.getPlayerMemoryBool("canConstructSalvagedDextral Shard", true);
    }

    @Override
    public boolean canDoProject() {
        return MemoryShortcuts.getPlayerMemoryBool("canConstructSalvagedDextral Shard", true);
    }

    @Override
    public Object grantReward() {

        MemoryShortcuts.setPlayerMemory("canConstructSalvagedDextral Shard", false);

        MarketAPI gatheringPoint = Global.getSector().getPlayerFaction().getProduction().getGatheringPoint();
        if (gatheringPoint == null) {
            gatheringPoint = Misc.getPlayerMarkets(true).get(0);
        }

        CargoAPI cargo = gatheringPoint.getSubmarket("storage").getCargo();
        FleetMemberAPI fleet = cargo.getMothballedShips().addFleetMember(AoTDMisc.getVaraint(Global.getSettings().getHullSpec("ashesofohm_shard_right")));
        fleet.getVariant().clear();
        return fleet;
    }

}
