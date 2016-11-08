package backendMockModel;

public class LineSummary extends LineIdentifier {

    private String destinationPort;
    private String departurePort;
    private int duration;

    public LineSummary( String destinationPort, String departurePort, int duration, String id ) {
        super( id );
        this.destinationPort = destinationPort;
        this.departurePort = departurePort;
        this.duration = duration;
    }

    public String getDestinationPort() {
        return destinationPort;
    }

    public String getDeparturePort() {
        return departurePort;
    }

    public int getDuration() {
        return duration;
    }
}
