package utilities;

import generalstuff.FerryConfigDetail;
import java.util.HashMap;
import java.util.Map;

public class FerryConfigListManagement {

    private static Map<Long, FerryConfigDetail> ferryConfigsDetailMap = new HashMap<>();
    private static long nextIdferryConfig = 0;

    public void addFerryConfigsDetail( FerryConfigDetail ferryConfigDetail ) {
        ferryConfigsDetailMap.put( nextIdferryConfig, ferryConfigDetail );
    }

    public Map<Long, FerryConfigDetail> getFerryConfigs() {
        return ferryConfigsDetailMap;
    }

    public static long getNextIdFerryConfigsDetail() {
        return nextIdferryConfig++;
    }
}
