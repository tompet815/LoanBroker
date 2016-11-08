package utilities;

import generalstuff.FerryDetail;
import java.util.HashMap;
import java.util.Map;

public class FerryDetailListManagement {
    
    
    private static Map<Long, FerryDetail> ferryDetailMap = new HashMap<>();
    private static long nextIdferry = 0;

    public void addFerryDetail( FerryDetail ferryDetail ) {
        ferryDetailMap.put( nextIdferry, ferryDetail );
    }

    public Map<Long, FerryDetail> getFerries() {
        return ferryDetailMap;
    }

    public static long getNextIdFerryDetail() {
        return nextIdferry++;
    }
    
}
