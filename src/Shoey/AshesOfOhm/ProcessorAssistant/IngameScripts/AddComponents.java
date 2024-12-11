package Shoey.AshesOfOhm.ProcessorAssistant.IngameScripts;

import Shoey.AshesOfOhm.MemoryShortcuts;
import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import static Shoey.AshesOfOhm.MainPlugin.*;
import static Shoey.AshesOfOhm.MemoryShortcuts.getPlayerMemoryBool;
import static Shoey.AshesOfOhm.MemoryShortcuts.setPlayerMemory;

public class AddComponents implements EveryFrameScript {

    public int dayCounter = 0, componentsLeft = 0, previousDay = 0, offset = 0, daysPerTick = 0;
    boolean done = false;
    public boolean forShip = false;
    public String shipName = "";
    public SectorEntityToken entityToken;

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
                if (forShip && !getPlayerMemoryBool("haveSalvaged" + shipName))
                {
                    daysPerTick = (7+offset) * 4;
                } else {
                    daysPerTick = (7+offset);
                }
                if (dayCounter > daysPerTick)
                {
                    dayCounter = 0;
                    setPlayerMemory("omegaWeaponPoints", MemoryShortcuts.getPlayerMemoryInt("omegaWeaponPoints") + 1);
                    componentsLeft--;
                }
                if (componentsLeft == 0)
                {
                    if (!forShip) {
                        Global.getSector().getCampaignUI().addMessage(entityToken.getName() + " has finished disassembling the requested items.");
                        entityToken.getMarket().getMemory().set("$ashesofohm_marketBusy", false);
                    } else {
                        setPlayerMemory("haveSalvaged" + shipName, true);
                        Global.getSector().getCampaignUI().addMessage(entityToken.getName() + " has finished salvaging a "+shipName+".");
                        entityToken.getMarket().getMemory().set("$ashesofohm_marketBusyShip", false);
                    }
                    done = true;
                }
            }
            previousDay = curDay;
        }
    }
}
