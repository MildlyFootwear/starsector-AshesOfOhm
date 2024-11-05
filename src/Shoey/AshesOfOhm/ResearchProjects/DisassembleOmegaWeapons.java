package Shoey.AshesOfOhm.ResearchProjects;

import data.kaysaar.aotd.vok.scripts.research.models.ResearchProject;

import static Shoey.AshesOfOhm.MainPlugin.*;

public class DisassembleOmegaWeapons extends ResearchProject {

    @Override
    public boolean haveMetReqForProjectToAppear() {
        return getPlayerMemoryBool("hasCoronalShunt");
    }

    @Override
    public boolean haveMetReqForProject() {

        if (!getPlayerMemoryBool("harvestingShuntWithResearch"))
            return false;

        return true;
    }
}
