import java.util.ArrayList;
import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ArrayList<LogType> lstType = new ArrayList<>(Arrays.asList(LogType.Text, LogType.System, LogType.Database));
        lstType.forEach(type -> {
            LogReader tmp = new FinalReaderCreator()
                    .createLogReader(type);
            System.out.println(tmp.toString());
        });
    }
}