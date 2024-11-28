package Shoey.AshesOfOhm.ProcessorAssistant.IngameScripts;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import static Shoey.AshesOfOhm.MainPlugin.*;
public class AddComponents implements EveryFrameScript {

    public int dayCounter = 0, componentsLeft = 0, previousDay = 0, offset = 0;
    boolean done = false;
    public SectorEntityToken entityToken;

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public boolean runWhilePaused() {
        return false;
    }

    @Override
    public void advance(float amount) {
        int curDay = Global.getSector().getClock().getDay();
        if (curDay != previousDay)
        {
            if (previousDay != 0)
            {
                dayCounter++;
                if (dayCounter > (7+offset))
                {
                    dayCounter = 0;
                    setPlayerMemory("omegaWeaponPoints", getPlayerMemoryInt("omegaWeaponPoints")+1);
                    componentsLeft--;
                }
                if (componentsLeft == 0)
                {
                    Global.getSector().getCampaignUI().addMessage(entityToken.getName() + " has finished disassembling the requested items.");
                    entityToken.getMarket().getMemory().set("$ashesofohm_marketBusy", false);
                    done = true;
                }
            }
            previousDay = curDay;
        }
    }
}
