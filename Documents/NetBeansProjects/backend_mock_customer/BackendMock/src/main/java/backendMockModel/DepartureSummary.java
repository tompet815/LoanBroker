package backendMockModel;

import java.util.Date;

public class DepartureSummary extends DepartureIdentifier {

    private Date departureTime;
    private LineSummary lineSummary;
    private FerrySummary ferrySummary;

    public DepartureSummary( Date departureTime, LineSummary lineSummary, FerrySummary ferrySummary, long id ) {
        super( id );
        this.departureTime = departureTime;
        this.lineSummary = lineSummary;
        this.ferrySummary = ferrySummary;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public LineSummary getLineSummary() {
        return lineSummary;
    }

    public FerrySummary getFerrySummary() {
        return ferrySummary;
    }

}
