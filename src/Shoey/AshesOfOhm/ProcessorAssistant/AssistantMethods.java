package Shoey.AshesOfOhm.ProcessorAssistant;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;

import static Shoey.AshesOfOhm.MainPlugin.log;
import static Shoey.AshesOfOhm.MainPlugin.omegaWeaponComponentMap;

public class AssistantMethods {
    public static String assistantID = "ashesofohm_omegaProcessorAssistant";
    public static void createAssistant(MarketAPI m)
    {
        log.debug(m.getName() + " is receiving an assistant.");
        PersonAPI person = Global.getFactory().createPerson();
        person.setId(assistantID);
        person.setFaction(m.getFactionId());
        person.setGender(FullName.Gender.ANY);
        person.setPostId(Ranks.POST_SCIENTIST);
        person.setImportance(PersonImportance.VERY_HIGH);
        person.getName().setFirst("Processor Assistant");
        person.getName().setLast("");
        person.setPortraitSprite("graphics/portraits/portrait_ai2.png");
        person.setVoice(assistantID);
        person.addTag(assistantID);

        person.setVoice(assistantID);
        m.addPerson(person);
        m.getCommDirectory().addPerson(person);
        Global.getSector().getCampaignUI().addMessage(m.getName() + " has received an assistant to facilitate research of highly experimental technology. They can be contacted through " + (m.getName()) +"'s comms directory");
    }

    public static int getWeaponConstructionDuration(int componentCount, MarketAPI m)
    {
        int weeks = componentCount*2;
        return weeks * (7+m.getMemory().getInt("$ashesofohm_productionRateOffset"));
    }

    public static int getWeaponConstructionDuration(String option, MarketAPI m)
    {
        return getWeaponConstructionDuration(omegaWeaponComponentMap.get(option), m);
    }
}