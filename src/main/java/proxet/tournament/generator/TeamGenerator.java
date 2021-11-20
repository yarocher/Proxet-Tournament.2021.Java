package proxet.tournament.generator;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;

import proxet.tournament.generator.dto.Player;
import proxet.tournament.generator.dto.TeamGeneratorResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TeamGenerator {

    public TeamGeneratorResult generateTeams(String filePath) {
        final Map<Integer, Integer> totalVehicleTypesCount =
                new HashMap<>(Map.of(1, 0, 2, 0, 3, 0));
        List<Player> allPlayers = readFileLines(filePath)
                .map(l -> {
                    String[] fields = l.split("\t");
                    return new Player(fields[0], Integer.parseInt(fields[2]), Integer.parseInt(fields[1]));
                })
                .sorted(Comparator.comparingInt(Player::getWaitTime).reversed())
                //getting only 18 players with needing vehicle type requirement (for both teams)
                .filter(player -> {
                    int vehicleTypeCount = totalVehicleTypesCount.get(player.getVehicleType());
                    if (vehicleTypeCount < 6) {
                        totalVehicleTypesCount.put(player.getVehicleType(), vehicleTypeCount + 1);
                        return true;
                    }
                    return false;
                })
                //sorting by vehicle type in order to easily split into two teams later
                .sorted(Comparator.comparingInt(Player::getVehicleType))
                .collect(Collectors.toList());
        return generateResult(allPlayers);
    }

    private TeamGeneratorResult generateResult(List<Player> allPlayers) {
        List<Player> team1 = new ArrayList<>();
        List<Player> team2 = new ArrayList<>();
        int i = 0;
        for (Player player : allPlayers) {
            //assuming that sorting by vehicle type is provided
            if ((i / 3) % 2 == 0) team1.add(player);
            else team2.add(player);
            i++;
        }
        return new TeamGeneratorResult(team1, team2);
    }

    private Stream<String> readFileLines(String filePath) {
        try {
            return Files.lines(new File(filePath).toPath());
        } catch (IOException ioe) {
            //we are not responsible for catching such exception; throw unchecked due to compatibility with tests
            throw new RuntimeException(ioe);
        }
    }

}
