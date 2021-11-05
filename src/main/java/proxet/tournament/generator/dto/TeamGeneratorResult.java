package proxet.tournament.generator.dto;

import java.util.List;

public class TeamGeneratorResult {

    private final List<Player> firstTeam;
    private final List<Player> secondTeam;

    public TeamGeneratorResult(List<Player> firstTeam, List<Player> secondTeam) {
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
    }

    public List<Player> getFirstTeam() {
        return firstTeam;
    }

    public List<Player> getSecondTeam() {
        return secondTeam;
    }
}
