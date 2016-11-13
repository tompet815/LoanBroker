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
    private static ReservationIdentifier reservationIdentifier2 = new ReservationIdentifier( 2 );

    public static void main( String[] args ) {
        //tests the seeReservation method
        System.out.println( dummyCustomerBackend.getReservation( reservationIdentifier ).getCustomerName() );
        
        //tests the saveReservation method
        dummyCustomerBackend.saveReservation( departureId, 14, 14, true,  1, 0);
        System.out.println( dummyCustomerBackend.getReservation( reservationIdentifier2 ).getNumberOfPeople());
        
        //tests the updateReservation method
        dummyCustomerBackend.updateReservation( reservationIdentifier, departureId, 4, 0, true );
        System.out.println( dummyCustomerBackend.getReservation( reservationIdentifier ).getNumberOfPeople() );
        
        //tests the deleteReservation method
        System.out.println( "Initially, the list size is " 
                + reservationDetailListManagement.getReservationDetails().size());
        dummyCustomerBackend.deleteReservation( reservationIdentifier );
        System.out.println( "After deletion, the list size is " 
                + reservationDetailListManagement.getReservationDetails().size());
    }

}
