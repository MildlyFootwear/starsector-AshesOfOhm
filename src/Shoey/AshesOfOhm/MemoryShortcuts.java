package Shoey.AshesOfOhm;

public class MemoryShortcuts {
    public static void insertMemoryNumber(String key) {
        if (!MainPlugin.pfMem.getKeys().contains(key)) {
            MainPlugin.pfMem.set(key, 0);
            MainPlugin.log.info("Set " + key + " to 0.");
        } else {
            MainPlugin.log.info(key + " is " + MainPlugin.pfMem.get(key));
        }
    }

    public static void insertMemoryBool(String key) {
        if (!MainPlugin.pfMem.getKeys().contains(key)) {
            MainPlugin.pfMem.set(key, false);
            MainPlugin.log.info("Set " + key + " to false.");
        } else {
            MainPlugin.log.info(key + " is " + MainPlugin.pfMem.get(key));
        }
    }

    public static Object getPlayerMemory(String key) {
        return getPlayerMemory(key, false);
    }

    public static Object getPlayerMemory(String key, boolean suppressError) {
        if (MainPlugin.pfMem.contains("$ashesofohm_" + key)) {
            return MainPlugin.pfMem.get("$ashesofohm_" + key);
        }
        if (!suppressError) {
            MainPlugin.log.error("Player faction memory does not contain key " + "$ashesofohm_" + key);
        }
        return null;
    }

    public static boolean getPlayerMemoryBool(String key) {
        return getPlayerMemoryBool(key, false);
    }

    public static boolean getPlayerMemoryBool(String key, boolean suppressError) {
        if (getPlayerMemory(key, suppressError) != null) {
            return (boolean) getPlayerMemory(key, suppressError);
        }
        return false;
    }

    public static int getPlayerMemoryInt(String key) {
        return getPlayerMemoryInt(key, false);
    }

    public static int getPlayerMemoryInt(String key, boolean suppressError) {
        if (getPlayerMemory(key, suppressError) != null) {
            return (int) getPlayerMemory(key, suppressError);
        }
        return 0;
    }

    public static String getPlayerMemoryString(String key) {
        return getPlayerMemoryString(key, false);
    }

    public static String getPlayerMemoryString(String key, boolean suppressError) {
        if (getPlayerMemory(key, suppressError) != null) {
            return (String) getPlayerMemory(key, suppressError);
        }
        return "";
    }

    public static void setPlayerMemory(String key, Object val) {
        MainPlugin.pfMem.set("$ashesofohm_" + key, val);
        MainPlugin.log.debug("Set $ashesofohm_" + key + " to " + val);
    }

    public static int getComponents()
    {
        return getPlayerMemoryInt("omegaWeaponPoints");
    }

    public static void addComponents(int i)
    {
        setPlayerMemory("omegaWeaponPoints", getComponents() + i);
    }

    public static void removeComponents(int i)
    {
        setPlayerMemory("omegaWeaponPoints", getComponents() - i);
    }
}