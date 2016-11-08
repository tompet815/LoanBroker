package utilities;

import generalstuff.LineSummary;
import java.util.HashMap;
import java.util.Map;

public class LineSummaryListManagement {

    private static Map<Long, LineSummary> lineSummaryMap = new HashMap<>();
    private static long nextIdLineSummary = 0;

    public void addLineSummary( LineSummary lineSummary ) {
        lineSummaryMap.put( nextIdLineSummary, lineSummary );
    }

    public Map<Long, LineSummary> getLineSummaries() {
        return lineSummaryMap;
    }

    public static long getNextIdLineSummary() {
        return nextIdLineSummary++;
    }
}
