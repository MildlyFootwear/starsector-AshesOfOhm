package Shoey.AshesOfOhm;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CoreUITabId;

public class CampaignEFS implements EveryFrameScript {

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean runWhilePaused() {
        return true;
    }

    @Override
    public void advance(float amount) {
        if (CampaignListener.isAtPlayerMarket && CampaignListener.currentMarket != null && Global.getSector().getCampaignUI().getCurrentCoreTab() == CoreUITabId.CARGO)
        {
            CheckMethods.marketOmegaResearch(CampaignListener.currentMarket);
            CheckMethods.marketHarvestShunt(CampaignListener.currentMarket);
        }
    }
}
