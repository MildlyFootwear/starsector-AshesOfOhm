package Shoey.AshesOfOhm;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.GameState;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.loading.WeaponSpecAPI;
import lunalib.lunaSettings.LunaSettings;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static Shoey.AshesOfOhm.MemoryShortcuts.getPlayerMemoryBool;
import static Shoey.AshesOfOhm.MemoryShortcuts.setPlayerMemory;


public class MainPlugin extends BaseModPlugin {
    
    public static Logger log = Global.getLogger(MainPlugin.class);

    public static MemoryAPI pfMem;

    List<String> memKeysNums = new ArrayList<>();
    public static List<String> omegaWeaponIDs = new ArrayList<>();
    public static List<String> omegaShips = new ArrayList<>();
    public static HashMap<String, Integer> omegaWeaponComponentMap = new HashMap<>();
    public static HashMap<String, Integer> omegaShipComponentMap = new HashMap<>();
    public static boolean Debugging = false, GiveTesseract = false, BypassTimer = false, BypassProcessor;

    public static void updateLunaSettings()
    {
        Debugging = Boolean.TRUE.equals(LunaSettings.getBoolean("ShoeyAshesOfOhm", "Debugging"));
        if (Debugging)
        {
            log.setLevel(Level.DEBUG);
        } else {
            log.setLevel(Level.INFO);
        }

        GiveTesseract = Boolean.TRUE.equals(LunaSettings.getBoolean("ShoeyAshesOfOhm", "GiveTesseract"));
        if (Global.getCurrentState() == GameState.CAMPAIGN)
        {
            if (GiveTesseract != getPlayerMemoryBool("hasDebugTesseract", true))
            {
                if (!getPlayerMemoryBool("hasDebugTesseract", true)) {
                    Global.getSector().getPlayerFleet().getFleetData().addFleetMember("ashesofohm_tesseract_Attack");
                }
                setPlayerMemory("hasDebugTesseract", GiveTesseract);
            }
        }
        BypassTimer = Boolean.TRUE.equals(LunaSettings.getBoolean("ShoeyAshesOfOhm", "BypassTimer"));
        BypassProcessor = Boolean.TRUE.equals(LunaSettings.getBoolean("ShoeyAshesOfOhm", "BypassProcessor"));
    }

    public static void updateOmegaWeaponIDs()
    {
        omegaWeaponIDs.clear();
        omegaWeaponComponentMap.clear();
        for(WeaponSpecAPI w : Global.getSettings().getAllWeaponSpecs())
        {
            if (w.hasTag("omega"))
            {
                omegaWeaponIDs.add(w.getWeaponId());
                int componentValue = 0;
                if (w.getSize() == WeaponAPI.WeaponSize.SMALL) {
                    componentValue = 1;
                } else if (w.getSize() == WeaponAPI.WeaponSize.MEDIUM) {
                    componentValue = 2;
                } else if (w.getSize() == WeaponAPI.WeaponSize.LARGE) {
                    componentValue = 3;
                }
                log.debug("Omega components: "+componentValue+" for weapon "+w.getWeaponId()+":"+w.getWeaponName());
                omegaWeaponComponentMap.put(w.getWeaponId(), componentValue);
            }
        }
    }
    public static void updateOmegaShips()
    {
        omegaShipComponentMap.clear();
        omegaShips.clear();
        omegaShips.add("Tesseract");
        omegaShips.add("Facet");
        omegaShips.add("Shard");
        omegaShipComponentMap.put("Tesseract", 12);
        omegaShipComponentMap.put("Facet", 6);
        omegaShipComponentMap.put("Shard", 3);

    }

    @Override
    public void onApplicationLoad() throws Exception {
        super.onApplicationLoad();
        updateLunaSettings();

        updateOmegaShips();
        memKeysNums.add("$ashesofohm_destroyedTesseractCount");
        memKeysNums.add("$ashesofohm_constructedTesseractCount");
        memKeysNums.add("$ashesofohm_salvagedTesseractCount");
        memKeysNums.add("$ashesofohm_destroyedFacetCount");
        memKeysNums.add("$ashesofohm_constructedFacetCount");
        memKeysNums.add("$ashesofohm_salvagedFacetCount");
        memKeysNums.add("$ashesofohm_destroyedShardCount");
        memKeysNums.add("$ashesofohm_constructedShardCount");
        memKeysNums.add("$ashesofohm_salvagedShardCount");
        memKeysNums.add("$ashesofohm_omegaWeaponPoints");
        LunaSettings.addSettingsListener(new LunaListener());
    }

    @Override
    public void onGameLoad(boolean b) {
        super.onGameLoad(b);
        updateLunaSettings();
        Global.getSector().addTransientScript(new CampaignEFS());
        Global.getSector().addTransientListener(new CampaignListener());
        pfMem = Global.getSector().getPlayerFaction().getMemory();

        for (String s : memKeysNums)
        {
            MemoryShortcuts.insertMemoryNumber(s);
        }

        if (MemoryShortcuts.getPlayerMemoryInt("omegaWeaponPoints") > 0)
            setPlayerMemory("deconstructedOmegaWeapons", true);

        CheckMethods.playerStatusChecks();
        updateOmegaWeaponIDs();
    }

    @Override
    public void beforeGameSave()
    {
        super.beforeGameSave();
    }

    @Override
    public void afterGameSave()
    {
        super.afterGameSave();
    }
}
