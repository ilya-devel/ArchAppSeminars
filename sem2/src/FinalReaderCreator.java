public class FinalReaderCreator extends  BaseLogReaderCreator {
    @Override
    protected LogReader createLogReaderInstance(LogType logType) {
        return switch(logType) {
            case Text -> new TextLogReader();
            case Database -> new DatabaseLogReader();
            case System -> new SystemLogReader();
            default -> null;
        };
    }
}
