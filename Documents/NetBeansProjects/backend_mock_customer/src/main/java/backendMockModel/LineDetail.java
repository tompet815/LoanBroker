package backendMockModel;

import java.util.HashMap;
import java.util.Map;

public class LineDetail extends LineSummary {

    private static long nextIdLine = 0;
    private static Map<Long, LineSummary> lineDetails = new HashMap<>();

    public LineDetail( String destinationPort, String departurePort, int duration, String id ) {
        super( destinationPort, departurePort, duration, id );
        lineDetails.put( nextIdLine++, this );
    }

    public static Map<Long, LineSummary> getLines() {
        return lineDetails;
    }
}
