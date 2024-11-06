package Shoey.AshesOfOhm.ResearchProjects;

import com.fs.starfarer.api.Global;
import org.apache.log4j.Logger;

import static Shoey.AshesOfOhm.MainPlugin.getPlayerMemoryString;
import static Shoey.AshesOfOhm.MainPlugin.setPlayerMemory;

public class ResearchUtils {

    static Logger logger = Global.getLogger(ResearchUtils.class);

    public static String getProjectWeaponID(float f)
    {
        int i = Math.round(f);
        if (i == 1)
        {
            return getPlayerMemoryString("omegaWeaponProjectSmall");
        } else if (i == 2) {
            return getPlayerMemoryString("omegaWeaponProjectMedium");
        } else if (i == 3) {
            return getPlayerMemoryString("omegaWeaponProjectLarge");
        }
        logger.error("Could not parse retrieval with "+i);
        return "";
    }

    public static void setProjectWeaponID(float f, String id)
    {
        int i = Math.round(f);
        if (i == 1)
        {
            setPlayerMemory("omegaWeaponProjectSmall", id);
        } else if (i == 2) {
            setPlayerMemory("omegaWeaponProjectMedium", id);
        } else if (i == 3) {
            setPlayerMemory("omegaWeaponProjectLarge", id);
        }
    }

}
