public enum ServerLevel {
    LOCAL(0),
    WIFI(1),
    INTERNET(2);

    private final int level;

    ServerLevel(int level) {
        this.level = level;
    }
}
