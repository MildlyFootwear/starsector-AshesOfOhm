package Shoey.AshesOfOhm;

import lunalib.lunaSettings.LunaSettingsListener;

import static Shoey.AshesOfOhm.MainPlugin.updateLunaSettings;


public class LunaListener implements LunaSettingsListener {

    @Override
    public void settingsChanged(String s) {
        if (s.equals("ShoeyAshesOfOhm"))
        {
            updateLunaSettings();
        }
    }
}
