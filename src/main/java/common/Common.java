package common;

import java.util.Arrays;
import java.util.List;

import static java.io.File.separator;

public class Common {

    public static final String CSV_SPLIT_BY = ",";
    private static final String path = "src" + separator + "main" + separator + "resources" + separator;
    private static final String INPUT_1 = path + "input_1.csv";
    private static final String INPUT_2 = path + "input_2.csv";
    private static final String INPUT_3 = path + "input_3.csv";
    public static final List<String> INPUT_PATHS = Arrays.asList(INPUT_1, INPUT_2, INPUT_3);
    private static final int SESSION_LENGTH_LIMIT = 30;
    private static final int MINUTES_TO_SECONDS = 60;
    public static final int SESSION_MAX_SECONDS_GAP = SESSION_LENGTH_LIMIT * MINUTES_TO_SECONDS;

}
