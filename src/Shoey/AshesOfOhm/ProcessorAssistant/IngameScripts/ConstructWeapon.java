package Shoey.AshesOfOhm.ProcessorAssistant.IngameScripts;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import data.kaysaar.aotd.vok.Ids.AoTDSubmarkets;

import static Shoey.AshesOfOhm.MainPlugin.BypassTimer;

public class ConstructWeapon implements EveryFrameScript {

    public int dayCounter = 0, previousDay = 0, daysUntilDone = 0;
    boolean done;
    public SectorEntityToken entityToken;
    public String wID;

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public boolean runWhilePaused() {
        return BypassTimer;
    }

    @Override
    public void advance(float amount) {
        int curDay = Global.getSector().getClock().getDay();
        if (curDay != previousDay || BypassTimer)
        {
            if (previousDay != 0)
            {
                dayCounter++;
                if (dayCounter > daysUntilDone || BypassTimer)
                {
                    entityToken.getMarket().getSubmarket(AoTDSubmarkets.RESEARCH_FACILITY_MARKET).getCargo().addWeapons(wID, 1);
                    Global.getSector().getCampaignUI().addMessage(Global.getSettings().getWeaponSpec(wID).getWeaponName() + " has been constructed and is waiting in "+entityToken.getMarket().getPrimaryEntity().getName()+"'s Research Facility.");
                    entityToken.getMarket().getMemory().set("$ashesofohm_marketBusy", false);
                    done = true;
                }
            }
            previousDay = curDay;
        }
    }
}
