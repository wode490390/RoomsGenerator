package cn.wode490390.nukkit.roomsgen;

public class RoomsGeneratorSettings {

    private final int wallId;
    private final int wallMeta;

    public RoomsGeneratorSettings(int wallId, int wallMeta) {
        this.wallId = wallId;
        this.wallMeta = wallMeta;
    }

    public int getWallId() {
        return this.wallId;
    }

    public int getWallMeta() {
        return this.wallMeta;
    }
}
