package Shoey.AshesOfOhm.ProductionProjects;

import Shoey.AshesOfOhm.MemoryShortcuts;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.kaysaar.aotd.vok.misc.AoTDMisc;
import data.kaysaar.aotd.vok.scripts.research.AoTDMainResearchManager;
import data.kaysaar.aotd.vok.scripts.specialprojects.models.AoTDSpecialProject;

public class SalvageTesseract extends AoTDSpecialProject {

    public void createRewardSection(TooltipMakerAPI tooltip, float width) {
        tooltip.addPara("Gain " + Global.getSettings().getHullSpec("ashesofohm_tesseract").getHullNameWithDashClass() + " vessel.", Misc.getPositiveHighlightColor(), 5.0F);
    }

    public boolean checkIfProjectShouldUnlock() {
        return MemoryShortcuts.getPlayerMemoryBool("canConstructSalvagedTesseract");
    }

    public void grantReward() {
        MarketAPI gatheringPoint = Global.getSector().getPlayerFaction().getProduction().getGatheringPoint();
        if (gatheringPoint == null) {
            gatheringPoint = (MarketAPI)Misc.getPlayerMarkets(true).get(0);
        }

        CargoAPI cargo = gatheringPoint.getSubmarket("storage").getCargo();
        FleetMemberAPI fleet = cargo.getMothballedShips().addFleetMember(AoTDMisc.getVaraint(Global.getSettings().getHullSpec("ashesofohm_tesseract")));
        fleet.getVariant().clear();
    }

    public String getNameOverride() {
        return this.countOfCompletion > 0 ? "Construction of Tesseract" : super.getNameOverride();
    }
}
