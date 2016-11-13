package backendMock;

//import backendMockModel.*;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import interfaces.*;
import generalstuff.*;
import utilities.*;
import java.util.Map;

public class DummyCustomerBackend implements CustomerInterface { //should implement the interface from the contract

    private DepartureDetail departureDetail;
    private LineDetail lineDetail;
    private LineSummary lineSummary;
    ReservationDetail reservationDetail;
    DepartureSummary departureSummary;
    Date departureDate;
    DepartureDetailListManagement departureDetailListManagement;
    FerryConfigListManagement ferryConfigListManagement;
    FerryDetailListManagement ferryDetailListManagement;
    LineSummaryListManagement lineSummarylListManagement;
    ReservationDetailListManagement reservationDetailListManagement;
    ReservationDetail dummyReservationDetail;
    private static Map<Long, DepartureDetail> departuresForLineAndDate;
    private static Map<Long, DepartureDetail> departuresForLineAndDateGeneralStuff;//needed just to return the same type as in the interface

    public DummyCustomerBackend() {
//        the lineDetail object is needed just to get the total amount of lineDetails, 
//        so I remove it from the collection of line details
        departureDetailListManagement = new DepartureDetailListManagement();
        ferryConfigListManagement = new FerryConfigListManagement();
        ferryDetailListManagement = new FerryDetailListManagement();
        lineSummarylListManagement = new LineSummaryListManagement();
        reservationDetailListManagement = new ReservationDetailListManagement();
        lineDetail = new LineDetail( "", "", 0, "" );
        lineSummary = new LineSummary( "", "", 0, "" );
        reservationDetail = new ReservationDetail( null, null, "", null, 0, 0, 0, 0, 0, 0.0, 0 );
        departureDate = new Date();
        departureSummary = new DepartureSummary( departureDate, lineDetail, null, 0 );
        departuresForLineAndDate = new HashMap<>();
        departuresForLineAndDateGeneralStuff = new HashMap<>();
        departureDetail = new DepartureDetail( 50, 100, 120, 150, 10, 100, 20, 1, 1, departureDate, lineSummary, null, 1 );
        departureDetailListManagement.getDepartures().put(
                departureDetailListManagement.getNextIdDeparture(), departureDetail );
        dummyReservationDetail = new ReservationDetail( departureDate, departureSummary,
                                                        "Patrick Huston", departureSummary, 4, 0, 1, 0, 0, 80,
                                                        Math.toIntExact( reservationDetailListManagement.getNextIdReservationDetail() ) );
        reservationDetailListManagement.addReservationDetail( dummyReservationDetail );
    }

    //to fix
    @Override
    public Collection<LineSummary> getLines() {
        return lineSummarylListManagement.getLineSummaries().values();
    }


    //finds the departures for a specific line and date (Note: the time is not taken into consideration!)
    @Override
    public Collection<DepartureDetail> getDepartures( LineIdentifier lineIdentifier, Date departureDate ) {
        Calendar calendardepartureDate = Calendar.getInstance();
        calendardepartureDate.setTime( departureDate );
        Calendar calendar = Calendar.getInstance();
        int departureDateDay = calendardepartureDate.get( Calendar.DAY_OF_YEAR );
        for ( DepartureDetail departure : departureDetailListManagement.getDepartures().values() ) {
            calendar.setTime( departure.getDepartureTime() );
            if ( departure.getLineSummary().getId().equals( lineIdentifier )
                    && departureDateDay == calendar.get( Calendar.DAY_OF_YEAR ) ) {
                departuresForLineAndDate.put( departure.getId(), departure );
            }
        }
        return departuresForLineAndDate.values();
    }

    @Override
    public ReservationDetail getReservation( ReservationIdentifier id ) {
        for ( Long l : reservationDetailListManagement.getReservationDetails().keySet() ) {
//            System.out.println( "" );
            if ( Math.toIntExact( l ) == id.getId() ) {
                return reservationDetailListManagement.getReservationDetails().get( l );
            }
        }
        return null;
    }

    @Override
    public ReservationSummary saveReservation( DepartureIdentifier departureIdentifier, 
            int passengersNb, int numberOfResidents, boolean car, int numberOfHeavyMachinery, int numberOfLorries ) {

        ReservationDetail newReservationDetail = new ReservationDetail( departureDate, departureSummary,
                                                                        "Mark Johnson", departureSummary,
                                                                        passengersNb, 2, 1, 0, 40, 100, Math.toIntExact(
                                                                                reservationDetailListManagement.getNextIdReservationDetail() ) );
        reservationDetailListManagement.addReservationDetail( newReservationDetail );

        return newReservationDetail;
    }

    @Override
    public ReservationSummary updateReservation( ReservationIdentifier reservationIdentifier, 
            DepartureIdentifier departureIdentifier, int passengersNb, int numberOfResidents, boolean car ) {
        Date departureDate = new Date();
        //for each key, compare the values; then take key and update the value
        for ( Long l : reservationDetailListManagement.getReservationDetails().keySet() ) {
            if ( Math.toIntExact( 1 ) == reservationIdentifier.getId() ) {
                return reservationDetailListManagement.getReservationDetails().replace(
                        l, new ReservationDetail( departureDate,
                                                  reservationDetailListManagement.getReservationDetails().get( l ).getDepartureSummary(),
                                                  "new customer", reservationDetailListManagement.getReservationDetails().get( l ).getDepartureSummary(),
                                                  passengersNb, reservationDetailListManagement.getReservationDetails().get( l ).getNumberOfResidents(),
                                                  reservationDetailListManagement.getReservationDetails().get( l ).getNumberOfCars(),
                                                  reservationDetailListManagement.getReservationDetails().get( l ).getNumberOfLorries(), 40,
                                                  200,
                                                  reservationIdentifier.getId() ) );
            }
        }
        return null;
    }

    @Override
    public Boolean deleteReservation( ReservationIdentifier reservationIdentifier ) {
        for ( Long l : reservationDetailListManagement.getReservationDetails().keySet() ) {
            if ( Math.toIntExact( 1 ) == reservationIdentifier.getId() ) {
                reservationDetailListManagement.getReservationDetails().remove( l );
                return true;
            }
        }
        return false;
    }
}
