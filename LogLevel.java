public enum LogLevel {
    INFO(0),
    ERROR(1),
    FATAL(2);

    private final int level;

    LogLevel(int level) {
        this.level = level;
    }

    public boolean isEqual(LogLevel comparison) {
        return this.level == comparison.level;
    }

    public boolean isLess(LogLevel comparison) {
        return this.level < comparison.level;
    }

    public boolean isGreater(LogLevel comparison) {
        return this.level > comparison.level;
    }
}