package backendMock;

import backendMockModel.*;
import java.util.Calendar; 
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import interfaces.FerryInterface;
import generalstuff.*;
 
public class DummyCustomerBackend implements FerryInterface { //should implement the interface from the contract

    private backendMockModel.DepartureDetail departureDetail;
    private backendMockModel.LineDetail lineDetail;
    private backendMockModel.LineSummary lineSummary;
    backendMockModel.ReservationDetail reservationDetail;
    backendMockModel.DepartureSummary departureSummary;
    Date departureDate;
    private static Map<Long, backendMockModel.DepartureDetail> departuresForLineAndDate;
    private static Map<Long, generalstuff.DepartureDetail> departuresForLineAndDateGeneralStuff;//needed just to return the same type as in the interface

    public DummyCustomerBackend() {
//        the lineDetail object is needed just to get the total amount of lineDetails, 
//        so I remove it from the collection of line details
        lineDetail = new backendMockModel.LineDetail( "", "", 0, "" );
        lineDetail.getLines().remove( 0 );
        lineSummary = new backendMockModel.LineSummary( "", "", 0, "" );
        reservationDetail = new backendMockModel.ReservationDetail( null, null, "", null, 0, 0, 0, 0, 0.0, 0 );
        departureDate = new Date();
        departureSummary = new backendMockModel.DepartureSummary( departureDate, lineDetail, null, 0 );
        departuresForLineAndDate = new HashMap<>();
        departuresForLineAndDateGeneralStuff = new HashMap<>();
        departureDetail= new backendMockModel.DepartureDetail(50, 100, 120, 150, 10, 100, 20, 1, 1, departureDate, lineSummary, null, 1);
        saveReservation( departureDetail, 4, "small" );
    }
    
    @Override
    public Collection<generalstuff.LineSummary> createLine( String name, String departurePort,
            String destinationPort, double personPrice, double carPrice,
            double lorryPrice ) {
        return null;
    }

    @Override
    public Boolean updateLine( generalstuff.LineIdentifier lineIdentifier, String name,
            String departurePort, String destinationPort, double personPrice,
            double carPrice, double lorryPrice ) {
        return null;
    }

    @Override
    public Boolean deleteLine( generalstuff.LineIdentifier lineIdentifier ) {
        return null;
    }

    @Override
    public generalstuff.LineDetail getLineDetail( generalstuff.LineIdentifier lineIdentifier ) {
        return null;
    }

    //to fix
    @Override
    public Collection<generalstuff.LineSummary> getLines() {
//        return (generalstuff.LineSummary)lineDetail.getLines().values();
    }

    @Override
    public Collection<generalstuff.FerrySummary> listFerries() {
        return null;
    }

    //finds the departures for a specific line and date (Note: the time is not taken into consideration!)
    @Override
    public Collection<generalstuff.DepartureDetail> getDepartures( long lineIdentifier, Date departureDate ) {
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

    @Override
    public generalstuff.FerryDetail getFerryInfo( generalstuff.FerryIdentifier ferry ) {
        return null;
    }

    @Override
    public Collection<generalstuff.FerrySummary> createFerry( String ferryName, String config ) {
       return null;
    }

    @Override
    public Boolean removeFerry( generalstuff.FerryIdentifier ferryIdentifier ) {
       return null;
    }

    @Override
    public Boolean updateFerry( generalstuff.FerryIdentifier ferryIdentifier, String ferryName,
            String config ) {
        return null;
    }

    @Override
    public Collection<generalstuff.DepartureDetail> listDepatureInfo() {
        return null;
    }

    @Override
    public Boolean deleteDeparture( generalstuff.DepartureIdentifier departureIdentifier ) {
        return null;
    }

    @Override
    public Boolean createDeparture( generalstuff.LineIdentifier lineIdentifier,
            generalstuff.FerryIdentifier ferryIdentifier, Boolean alternateConfig,
            Date departureDate ) {
        return null;
    }

    @Override
    public Boolean updateDeparture( generalstuff.DepartureIdentifier departureIdentifier,
            generalstuff.LineIdentifier lineIdentifier, generalstuff.FerryIdentifier ferryIdentifier,
            Boolean alternateConfig, Date departureDate ) {
        return null;
    }

    @Override
    public ReservationDetail seeReservation( int id ) {
        for ( Long l : reservationDetail.getReservations().keySet() ) {
            if ( reservationDetail.getReservations().get( l ).getId() == id ) {
                return reservationDetail.getReservations().get( l );
            }
        }
        return null;
    }

    @Override
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

    @Override
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

    @Override
    public Boolean deleteReservation( ReservationIdentifier reservationIdentifier ) {
        for ( Long l : reservationDetail.getReservations().keySet() ) {
            if ( reservationDetail.getReservations().get( l ).getId() == reservationIdentifier.getId() ) {
                reservationDetail.getReservations().remove( l );
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean removeFerryConfig( FerryConfigIdentifier ferryConfigIdentifier ) {
        return null;
    }

    @Override
    public Boolean updateFerryConfig( FerryConfigIdentifier ferryConfigIdentifier,
            String ferryConfigName, int peopleCapacity, int vehicleCapacity,
            int weightCapacity ) {
        return null;
    }
}
