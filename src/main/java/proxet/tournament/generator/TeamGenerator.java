package proxet.tournament.generator;

import proxet.tournament.generator.dto.Player;
import proxet.tournament.generator.dto.TeamGeneratorResult;

import java.util.List;

public class TeamGenerator {

    public TeamGeneratorResult generateTeams(String filePath) {
        //Please implement your algorithm there.
        return new TeamGeneratorResult(
                List.of(new Player("Dummy1", 1)),
                List.of(new Player("Dummy2", 2))
        );
    }


}
