package Shoey.AshesOfOhm.ProcessorAssistant;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;

public class AssistantMethods {
    public static String assistantID = "ashesofohm_omegaProcessorAssistant";
    public static void createAssistant(MarketAPI m)
    {
        PersonAPI person = Global.getFactory().createPerson();
        person.setId(assistantID);
        person.setFaction(Factions.PLAYER);
        person.setGender(FullName.Gender.ANY);
        person.setPostId(Ranks.POST_SCIENTIST);
        person.setImportance(PersonImportance.VERY_HIGH);
        person.getName().setFirst("Processor Assistant");
        person.getName().setLast("");
        person.setPortraitSprite("graphics/portraits/portrait_ai2.png");
        person.addTag(assistantID);

        person.setVoice(assistantID);
        m.addPerson(person);
        m.getCommDirectory().addPerson(person);
    }
}
