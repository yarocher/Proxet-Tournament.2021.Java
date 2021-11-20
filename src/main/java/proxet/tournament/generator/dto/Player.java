package proxet.tournament.generator.dto;

public class Player {

    private final String nickname;
    private final int vehicleType;
    private final int waitTime;

    public Player(String nickname, int vehicleType, int waitTime) {
        this.nickname = nickname;
        this.vehicleType = vehicleType;
        this.waitTime = waitTime;
    }

    public String getNickname() {
        return nickname;
    }

    public int getVehicleType() {
        return vehicleType;
    }
    public int getWaitTime() {
        return waitTime;
    }
}
