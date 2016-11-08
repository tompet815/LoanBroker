package backendMockModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReservationDetail extends ReservationSummary{

    private Date reservationMade;
    private DepartureSummary departureSummary;
    private String customerName;
    private DepartureDetail departure;
    private int numberOfPeople;
    private int numberOfResidents;
    private int numberOfCars;
    private int numberOfLorries;
    private static long nextIdReservation = 0;
    private static Map<Long, ReservationDetail> reservations = new HashMap<>();

    public ReservationDetail( Date reservationMade, DepartureSummary  departureSummary, 
            String customerName, DepartureDetail departure, int numberOfPeople, 
            int numberOfResidents, int numberOfCars, int numberOfLorries,  
            double totalPrice, int id ) {
        super(totalPrice, id);
        this.reservationMade = reservationMade;
        this.departureSummary = departureSummary;
        this.customerName = customerName;
        this.departure = departure;
        this.numberOfPeople = numberOfPeople;
        this.numberOfResidents = numberOfResidents;
        this.numberOfCars = numberOfCars;
        this.numberOfLorries = numberOfLorries;
        reservations.put( nextIdReservation++, this );
    }

    public static Map<Long, ReservationDetail> getReservations() {
        return reservations;
    }

    public Date getReservationMade() {
        return reservationMade;
    }

    public DepartureSummary getDepartureSummary() {
        return departureSummary;
    }

    public String getCustomerName() {
        return customerName;
    }

    public DepartureDetail getDeparture() {
        return departure;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public int getNumberOfResidents() {
        return numberOfResidents;
    }

    public int getNumberOfCars() {
        return numberOfCars;
    }

    public int getNumberOfLorries() {
        return numberOfLorries;
    }

}
