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

    @Override
    public void createRewardSection(TooltipMakerAPI tooltip, float width) {
        tooltip.addPara("Gain salvaged variant of the " + Global.getSettings().getHullSpec("ashesofohm_tesseract").getHullNameWithDashClass() + " vessel.", Misc.getPositiveHighlightColor(), 5.0F);
    }

    @Override
    public boolean checkIfProjectShouldUnlock() {
        return MemoryShortcuts.getPlayerMemoryBool("haveSalvagedTesseract");
    }

    @Override
    public boolean shouldShowOnUI() {
        return MemoryShortcuts.getPlayerMemoryBool("canConstructSalvagedTesseract");
    }

    public boolean canDoProject() {
        return MemoryShortcuts.getPlayerMemoryBool("canConstructSalvagedTesseract");
    }

    @Override
    public void grantReward() {

        MemoryShortcuts.setPlayerMemory("canConstructSalvagedTesseract", false);

        MarketAPI gatheringPoint = Global.getSector().getPlayerFaction().getProduction().getGatheringPoint();
        if (gatheringPoint == null) {
            gatheringPoint = Misc.getPlayerMarkets(true).get(0);
        }

        CargoAPI cargo = gatheringPoint.getSubmarket("storage").getCargo();
        FleetMemberAPI fleet = cargo.getMothballedShips().addFleetMember(AoTDMisc.getVaraint(Global.getSettings().getHullSpec("ashesofohm_tesseract")));
        fleet.getVariant().clear();
    }

}
