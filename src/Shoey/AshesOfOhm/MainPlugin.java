package Shoey.AshesOfOhm;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.loading.WeaponSpecAPI;
import lunalib.lunaSettings.LunaSettings;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static Shoey.AshesOfOhm.MemoryShortcuts.setPlayerMemory;


public class MainPlugin extends BaseModPlugin {
    
    public static Logger log = Global.getLogger(MainPlugin.class);

    public static MemoryAPI pfMem;

    List<String> memKeysNums = new ArrayList<>();
    public static List<String> omegaWeaponIDs = new ArrayList<>();
    public static List<String> omegaShips = new ArrayList<>();
    public static List<String> onlyOneOfHullmods = Arrays.asList("");
    public static HashMap<String, Integer> omegaWeaponComponentMap = new HashMap<>();
    public static HashMap<String, Integer> omegaShipComponentMap = new HashMap<>();
    public static boolean Debugging = false, BypassTimer = false, BypassProcessor, BypassShunt, ListCheat, disableBlueprintCheck = false;

    public static void updateLunaSettings()
    {
        Debugging = Boolean.TRUE.equals(LunaSettings.getBoolean("ShoeyAshesOfOhm", "Debugging"));
        if (Debugging)
        {
            log.setLevel(Level.DEBUG);
        } else {
            log.setLevel(Level.INFO);
        }

        BypassTimer = Boolean.TRUE.equals(LunaSettings.getBoolean("ShoeyAshesOfOhm", "BypassTimer"));
        BypassProcessor = Boolean.TRUE.equals(LunaSettings.getBoolean("ShoeyAshesOfOhm", "BypassProcessor"));
        BypassShunt = Boolean.TRUE.equals(LunaSettings.getBoolean("ShoeyAshesOfOhm", "BypassShunt"));
        ListCheat = Boolean.TRUE.equals(LunaSettings.getBoolean("ShoeyAshesOfOhm", "ListCheat"));
        disableBlueprintCheck = Boolean.TRUE.equals(LunaSettings.getBoolean("ShoeyAshesOfOhm", "disableBlueprintCheck"));
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
        omegaShips.add("Sinistral Shard");
        omegaShips.add("Dextral Shard");
        omegaShipComponentMap.put("Tesseract", 12);
        omegaShipComponentMap.put("Facet", 6);
        omegaShipComponentMap.put("Shard", 3);
        omegaShipComponentMap.put("Sinistral Shard", omegaShipComponentMap.get("Shard"));
        omegaShipComponentMap.put("Dextral Shard", omegaShipComponentMap.get("Shard"));

    }

    @Override
    public void onApplicationLoad() throws Exception {
        super.onApplicationLoad();
        updateLunaSettings();
        LunaSettings.addSettingsListener(new LunaListener());

        updateOmegaShips();
        for (String key : omegaShips)
        {
            memKeysNums.add("$ashesofohm_destroyed"+key+"Count");
            memKeysNums.add("$ashesofohm_constructed"+key+"Count");
            memKeysNums.add("$ashesofohm_salvaged"+key+"Count");
        }
        memKeysNums.add("$ashesofohm_omegaWeaponPoints");
    }

    @Override
    public void onGameLoad(boolean b) {
        super.onGameLoad(b);
        updateLunaSettings();
        pfMem = Global.getSector().getPlayerFaction().getMemory();

        Global.getSector().addTransientScript(new CampaignEFS());
        Global.getSector().addTransientListener(new CampaignListener());

        for (String s : memKeysNums)
        {
            MemoryShortcuts.insertMemoryNumber(s);
        }

        if (MemoryShortcuts.getPlayerMemoryInt("omegaWeaponPoints") > 0) {
            setPlayerMemory("deconstructedOmegaWeapons", true);
        }

        CheckMethods.playerStatusChecks();
        updateOmegaWeaponIDs();
//        omegaShipPatchwork();
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
