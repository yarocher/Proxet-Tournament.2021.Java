package proxet.tournament;

import org.junit.Test;
import proxet.tournament.generator.TeamGenerator;
import proxet.tournament.generator.dto.Player;
import proxet.tournament.generator.dto.TeamGeneratorResult;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TournamentTests {

    private static final String STAT_FILE = "wait-time.stat";
    private static final int EXPECTED_TEAM_SIZE = 9;
    private static final int EXPECTED_VEHICLE_CLASS_COUNT_PER_TEAM = 3;
    private static final int EXPECTED_TEAM_PLAYERS_INTERSECT_COUNT = 0;

    private static final List<String> EXPECTED_CLASS_1_USERS = List.of(
            "_{Basil$@",
            "o-Ben#-",
            "+oBarney_+",
            "#%Gerard@%",
            "-@Grant]%",
            "<OEmil%#"
    );

    private static final List<String> EXPECTED_CLASS_2_USERS = List.of(
            "O$Mickey$@",
            "$-Dereko-",
            "o@Rusty#*",
            "^[Andres_$",
            "*<Tomas$_",
            "##Dwayne_o"
    );

    private static final List<String> EXPECTED_CLASS_3_USERS = List.of(
            "O#Truman#@",
            "*%Wesley-+",
            "-{Sidney_o",
            "[#Andre]O",
            "-[Oliver}@",
            "^[Gene*+"
    );

    @Test
    public void itEnsuresThatFileIsPresent() {
        File statFile = new File(STAT_FILE);
        assertTrue(statFile.exists());
    }

    @Test
    public void itEnsuresThatIdealTeamsWasReturned() {
        TeamGenerator generator = new TeamGenerator();

        TeamGeneratorResult teams = generator.generateTeams(STAT_FILE);

        //assert
        assertNotNull(teams);
        List<Player> firstTeam = teams.getFirstTeam();
        List<Player> secondTeam = teams.getSecondTeam();

        assertNotNull(firstTeam);
        assertNotNull(secondTeam);

        assertEquals(EXPECTED_TEAM_SIZE, firstTeam.size());
        assertEquals(EXPECTED_TEAM_SIZE, secondTeam.size());
        assertTeamIntersect(teams);

        assertVehicleClass(firstTeam, secondTeam, EXPECTED_CLASS_1_USERS);
        assertVehicleClass(firstTeam, secondTeam, EXPECTED_CLASS_2_USERS);
        assertVehicleClass(firstTeam, secondTeam, EXPECTED_CLASS_3_USERS);
    }

    private void assertVehicleClass(List<Player> firstTeam,
                                    List<Player> secondTeam,
                                    List<String> expectedClassNicknames) {
        assertClassIntersect(firstTeam, expectedClassNicknames);
        assertClassIntersect(secondTeam, expectedClassNicknames);
    }

    private void assertClassIntersect(List<Player> firstTeam, List<String> expectedClassNicknames) {
        long count = firstTeam.stream()
                .filter(p -> expectedClassNicknames.contains(p.getNickname()))
                .count();

        assertEquals(EXPECTED_VEHICLE_CLASS_COUNT_PER_TEAM, count);
    }

    private void assertTeamIntersect(TeamGeneratorResult teams) {
        long intersectCount = teams.getFirstTeam().stream()
                .filter(teams.getSecondTeam()::contains)
                .count();
        assertEquals(EXPECTED_TEAM_PLAYERS_INTERSECT_COUNT, intersectCount);
    }
}
