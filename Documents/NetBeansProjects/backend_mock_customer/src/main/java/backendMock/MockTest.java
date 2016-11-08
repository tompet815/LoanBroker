package backendMock;

import backendMock.DummyCustomerBackend;
import generalstuff.DepartureIdentifier;
import generalstuff.FerryIdentifier;
import generalstuff.ReservationIdentifier;
import java.util.Date;
import utilities.ReservationDetailListManagement;

public class MockTest {

    private static DummyCustomerBackend dummyCustomerBackend = new DummyCustomerBackend();
    private static ReservationDetailListManagement reservationDetailListManagement = new ReservationDetailListManagement();
    private static DepartureIdentifier departureId = new DepartureIdentifier( 1 );
    private static ReservationIdentifier reservationIdentifier = new ReservationIdentifier( 1 );

    public static void main( String[] args ) {
        //tests the seeReservation method
        System.out.println( dummyCustomerBackend.seeReservation( 1 ).getCustomerName() );
        
        //tests the saveReservation method
        dummyCustomerBackend.saveReservation( departureId, 14, "small" );
        System.out.println( dummyCustomerBackend.seeReservation( 2 ).getNumberOfPeople());
        
        //tests the updateReservation method
        dummyCustomerBackend.updateReservation( reservationIdentifier, departureId, 25, "lorry" );
        System.out.println( dummyCustomerBackend.seeReservation( 1 ).getNumberOfPeople() );
        
        //tests the deleteReservation method
        System.out.println( reservationDetailListManagement.getReservationDetails().values() );
        dummyCustomerBackend.deleteReservation( reservationIdentifier );
        System.out.println( reservationDetailListManagement.getReservationDetails().values() );
    }

}