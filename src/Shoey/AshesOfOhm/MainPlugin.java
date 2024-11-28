package Shoey.AshesOfOhm;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.GameState;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.loading.WeaponSpecAPI;
import lunalib.lunaSettings.LunaSettings;
import lunalib.lunaUtil.LunaCommons;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainPlugin extends BaseModPlugin {
    
    public static Logger log = Global.getLogger(MainPlugin.class);

    static MemoryAPI pfMem;

    List<String> memKeysNums = new ArrayList<>();
    public static List<String> omegaWeaponIDs = new ArrayList<>();
    public static HashMap<String, Integer> omegaWeaponComponentMap = new HashMap<>();
    public static boolean Debugging = false, GiveTesseract = false, BypassTimer = false;

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
    }

    void insertMemoryNumber(MemoryAPI pfMem, String key)
    {
        if (!pfMem.getKeys().contains(key))
        {
            pfMem.set(key, 0);
            log.info("Set "+key+" to 0.");
        } else {
            log.info(key+" is "+pfMem.get(key));
        }
    }

    void insertMemoryBool(MemoryAPI pfMem, String key)
    {
        if (!pfMem.getKeys().contains(key))
        {
            pfMem.set(key, false);
            log.info("Set "+key+" to false.");
        } else {
            log.info(key+" is "+pfMem.get(key));
        }
    }

    public static Object getPlayerMemory(String key)
    {
        if (pfMem.contains("$ashesofohm_"+key)) {
            return pfMem.get("$ashesofohm_" + key);
        }
        log.error("Player faction memory does not contain key "+"$ashesofohm_"+key);
        return null;
    }

    public static boolean getPlayerMemoryBool(String key)
    {
        return getPlayerMemoryBool(key, false);
    }

    public static boolean getPlayerMemoryBool(String key, boolean suppressError)
    {
        if (pfMem.contains("$ashesofohm_"+key)) {
            return (boolean) pfMem.get("$ashesofohm_" + key);
        }
        if (!suppressError) {
            log.error("Player faction memory does not contain key " + "$ashesofohm_" + key);
        }
        return false;
    }

    public static int getPlayerMemoryInt(String key)
    {
        if (pfMem.contains("$ashesofohm_"+key)) {
            return (int) pfMem.get("$ashesofohm_" + key);
        }
        log.error("Player faction memory does not contain key "+"$ashesofohm_"+key);
        return 0;
    }

    public static String getPlayerMemoryString(String key)
    {
        if (pfMem.contains("$ashesofohm_"+key)) {
            return (String) pfMem.get("$ashesofohm_" + key);
        }
        log.error("Player faction memory does not contain key "+"$ashesofohm_"+key);
        return "";
    }

    public static void setPlayerMemory(String key, Object val)
    {
        pfMem.set("$ashesofohm_"+key, val);
        log.debug("Set $ashesofohm_"+key+" to "+val);
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

    @Override
    public void onApplicationLoad() throws Exception {
        super.onApplicationLoad();
        updateLunaSettings();

        log.setLevel(Level.DEBUG);

        memKeysNums.add("$ashesofohm_destroyedTesseractCount");
        memKeysNums.add("$ashesofohm_constructedTesseractCount");
        memKeysNums.add("$ashesofohm_destroyedFacetCount");
        memKeysNums.add("$ashesofohm_constructedFacetCount");
        memKeysNums.add("$ashesofohm_destroyedShardCount");
        memKeysNums.add("$ashesofohm_constructedShardCount");
        memKeysNums.add("$ashesofohm_omegaWeaponPoints");
        LunaSettings.addSettingsListener(new LunaListener());

    }

    @Override
    public void onGameLoad(boolean b) {
        super.onGameLoad(b);
        updateLunaSettings();

        Global.getSector().addTransientListener(new CampaignListener());
        pfMem = Global.getSector().getPlayerFaction().getMemory();

        for (String s : memKeysNums)
        {
            insertMemoryNumber(pfMem, s);
        }

        if (getPlayerMemoryInt("omegaWeaponPoints") > 0)
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
