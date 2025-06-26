package Shoey.AshesOfOhm.ProcessorAssistant.IngameScripts;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorEntityToken;

import static Shoey.AshesOfOhm.MainPlugin.BypassTimer;
import static Shoey.AshesOfOhm.MemoryShortcuts.setPlayerMemory;

public class ConstructShip implements EveryFrameScript {

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
        setPlayerMemory("canConstructSalvaged" + kID, true);
        done = true;
    }
}
