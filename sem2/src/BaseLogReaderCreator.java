public abstract class BaseLogReaderCreator {

    protected LogReader createLogReader(LogType logType) {
        LogReader logReader = createLogReaderInstance(logType);
        System.out.println(logReader.getClass());
        //TODO: doing something
        return logReader;
    }

    protected abstract LogReader createLogReaderInstance(LogType logType);
}
