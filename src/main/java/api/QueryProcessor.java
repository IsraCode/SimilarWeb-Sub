package api;

public class QueryProcessor {

    private static final QueryProcessor instance = new QueryProcessor();

    private QueryProcessor() {
    }

    public static QueryProcessor getInstance() {
        return instance;
    }

    public Query handler(String function, String parameter) {
        switch (function.toLowerCase().replaceAll("\\s+", "")) {
            case "num_unique_visited_sites":
                return new UniqueSessionCountQuery(parameter);
            case "median_session_length":
                return new MedianSessionLengthQuery(parameter);
            case "num_sessions":
                return new SessionNumQuery(parameter);
            default:
                throw new IllegalArgumentException("Invalid function name");
        }
    }
}



