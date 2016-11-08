package utilities;

import generalstuff.DepartureDetail;
import java.util.HashMap;
import java.util.Map;

public class DepartureDetailListManagement {

    private static Map<Long, DepartureDetail> departuresMap = new HashMap<>();
    private static long nextIdDeparture = 0;

    public void addDeparture( DepartureDetail departureDetail ) {
        departuresMap.put( nextIdDeparture, departureDetail );
    }

    public Map<Long, DepartureDetail> getDepartures() {
        return departuresMap;
    }

    public static long getNextIdDeparture() {
        return nextIdDeparture++;
    }
}
