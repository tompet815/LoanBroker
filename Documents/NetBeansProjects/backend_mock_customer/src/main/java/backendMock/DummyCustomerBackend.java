package backendMock;

import backendMockModel.*;
import java.util.Calendar; 
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
 
public class DummyCustomerBackend { //should implement the interface from the contract

    private DepartureDetail departureDetail;
    private LineDetail lineDetail;
    private LineSummary lineSummary;
    ReservationDetail reservationDetail;
    DepartureSummary departureSummary;
    Date departureDate;
    private static Map<Long, DepartureDetail> departuresForLineAndDate;

    public DummyCustomerBackend() {
//        the lineDetail object is needed just to get the total amount of lineDetails, 
//        so I remove it from the collection of line details
        lineDetail = new LineDetail( "", "", 0, "" );
        lineDetail.getLines().remove( 0 );
        lineSummary = new LineSummary( "", "", 0, "" );
        reservationDetail = new ReservationDetail( null, null, "", null, 0, 0, 0, 0, 0.0, 0 );
        departureDate = new Date();
        departureSummary = new DepartureSummary( departureDate, lineDetail, null, 0 );
        departuresForLineAndDate = new HashMap<>();
    }

    public Collection<LineSummary> createLine( String name, String departurePort,
            String destinationPort, double personPrice, double carPrice,
            double lorryPrice ) throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    public Boolean updateLine( LineIdentifier lineIdentifier, String name,
            String departurePort, String destinationPort, double personPrice,
            double carPrice, double lorryPrice ) throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    public Boolean deleteLine( LineIdentifier lineIdentifier ) throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    public LineDetail getLineDetail( LineIdentifier lineIdentifier ) throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    public Collection<LineSummary> getLines() {
        return lineDetail.getLines().values();
    }

    public Collection<FerrySummary> listFerries() throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    //finds the departures for a specific line and date (Note: the time is not taken into consideration!)
    public Collection<DepartureDetail> getDepartures( long lineIdentifier, Date departureDate ) {
        Calendar calendardepartureDate = Calendar.getInstance();
        calendardepartureDate.setTime( departureDate );
        Calendar calendar = Calendar.getInstance();
        int departureDateDay = calendardepartureDate.get( Calendar.DAY_OF_YEAR );
        for ( DepartureDetail departure : departureDetail.getDepartures().values() ) {
            calendar.setTime( departure.getDepartureTime() );
            if ( departure.getLineSummary().getId().equals( lineIdentifier )
                    && departureDateDay == calendar.get( Calendar.DAY_OF_YEAR ) ) {
                departuresForLineAndDate.put( departure.getId(), departure );
            }
        }
        return departuresForLineAndDate.values();
    }

    public FerryDetail getFerryInfo( FerryIdentifier ferry ) throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    public Collection<FerrySummary> createFerry( String ferryName, String config ) throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    public Boolean removeFerry( FerryIdentifier ferryIdentifier ) throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    public Boolean updateFerry( FerryIdentifier ferryIdentifier, String ferryName,
            String config ) throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    public Collection<DepartureDetail> listDepatureInfo() throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    public Boolean deleteDeparture( DepartureIdentifier departureIdentifier ) throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    public Boolean createDeparture( LineIdentifier lineIdentifier,
            FerryIdentifier ferryIdentifier, Boolean alternateConfig,
            Date departureDate ) throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    public Boolean updateDeparture( DepartureIdentifier departureIdentifier,
            LineIdentifier lineIdentifier, FerryIdentifier ferryIdentifier,
            Boolean alternateConfig, Date departureDate ) throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    public ReservationDetail seeReservation( int id ) {
        for ( Long l : reservationDetail.getReservations().keySet() ) {
            if ( reservationDetail.getReservations().get( l ).getId() == id ) {
                return reservationDetail.getReservations().get( l );
            }
        }
        return null;
    }

    public ReservationSummary saveReservation( DepartureIdentifier departureIdentifier, int passengersNb, String Cartype ) {
        int maxId = 0;
        for ( Long l : reservationDetail.getReservations().keySet() ) {
            if ( reservationDetail.getReservations().get( l ).getId() > maxId ) {
                maxId = reservationDetail.getReservations().get( l ).getId();
            }
        }
        return new ReservationDetail( departureDate, departureSummary,
                                      "Mark Johnson", departureDetail,
                                      2, 2, 1, 0, 40, maxId + 1 );
    }

    public ReservationSummary updateReservation( ReservationIdentifier reservationIdentifier,
            DepartureIdentifier departureIdentifier, int passengersNb, String Cartype ) {
        Date departureDate = new Date();
        //for each key, compare the values; then take key and update the value
        for ( Long l : reservationDetail.getReservations().keySet() ) {
            if ( reservationDetail.getReservations().get( l ).getId() == reservationIdentifier.getId() ) {
                return reservationDetail.getReservations().replace(
                        l, new ReservationDetail( reservationDetail.getReservations().get( l )
                                .getDepartureSummary().getDepartureTime(),
                                                  reservationDetail.getReservations().get( l ).getDepartureSummary(),
                                                  reservationDetail.getReservations().get( l ).getCustomerName(),
                                                  reservationDetail.getReservations().get( l ).getDeparture(),
                                                  passengersNb,
                                                  reservationDetail.getReservations().get( l ).getNumberOfResidents(),
                                                  reservationDetail.getReservations().get( l ).getNumberOfCars(),
                                                  reservationDetail.getReservations().get( l ).getNumberOfLorries(),
                                                  40, reservationIdentifier.getId() ) );
            }
        }
        return null;
    }

    public Boolean deleteReservation( ReservationIdentifier reservationIdentifier ) {
        for ( Long l : reservationDetail.getReservations().keySet() ) {
            if ( reservationDetail.getReservations().get( l ).getId() == reservationIdentifier.getId() ) {
                reservationDetail.getReservations().remove( l );
                return true;
            }
        }
        return false;
    }

    public Boolean removeFerryConfig( FerryConfigIdentifier ferryConfigIdentifier ) throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    public Boolean updateFerryConfig( FerryConfigIdentifier ferryConfigIdentifier,
            String ferryConfigName, int peopleCapacity, int vehicleCapacity,
            int weightCapacity ) throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    public Boolean createFerryConfig( String ferryConfigName, int peopleCapacity,
            int vehicleCapacity, int weightCapacity ) throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    public Collection<FerryConfigSummary> listFerryConfigs() throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    public FerryConfigDetail getFerryConfigDetail( FerryConfigIdentifier ferryConfigIdentifier )
            throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }
}
