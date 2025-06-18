package Shoey.AshesOfOhm.ProcessorAssistant.IngameScripts;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorEntityToken;

import static Shoey.AshesOfOhm.MainPlugin.BypassTimer;
import static Shoey.AshesOfOhm.MemoryShortcuts.setPlayerMemory;

public class ConstructShip implements EveryFrameScript {

    public int dayCounter = 0, previousDay = 0, daysUntilDone = 0;
    boolean done;
    public SectorEntityToken entityToken;
    public String kID;

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
        Global.getSector().getCampaignUI().addMessage(entityToken.getMarket().getPrimaryEntity().getName()+"'s Research Facility has prepared the components required for "+kID+".");
        setPlayerMemory("canConstructSalvaged" + kID, true);
        entityToken.getMarket().getMemory().set("$ashesofohm_marketBusyShip", false);
        done = true;
    }
}
