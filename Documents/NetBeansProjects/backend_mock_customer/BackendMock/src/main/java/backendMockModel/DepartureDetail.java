package backendMockModel;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DepartureDetail extends DepartureSummary{

    private static Map<Long, DepartureDetail> departures = new HashMap<>();
    private static long nextIdDeparture = 0;

    private long pricePerPerson;
    private long pricePerCar;
    private long pricePerLorry;
    private long pricePerHeavy;
    private long pricePerResident;
    private int remainingPeople;
    private int remainingCars;
    private int remainingLorries;
    private int remainingHeavy;

    public DepartureDetail( long pricePerPerson, long pricePerCar, long pricePerLorry, long pricePerHeavy, long pricePerResident, int remainingPeople, int remainingCars, int remainingLorries, int remainingHeavy, Date departureTime, LineSummary lineSummary, FerrySummary ferrySummary, long id) {
        super(departureTime, lineSummary, ferrySummary, id);
        this.pricePerPerson = pricePerPerson;
        this.pricePerCar = pricePerCar;
        this.pricePerLorry = pricePerLorry;
        this.pricePerHeavy = pricePerHeavy;
        this.pricePerResident = pricePerResident;
        this.remainingPeople = remainingPeople;
        this.remainingCars = remainingCars;
        this.remainingLorries = remainingLorries;
        this.remainingHeavy = remainingHeavy;
        departures.put( nextIdDeparture++, this );
    }

    public static Map<Long, DepartureDetail> getDepartures() {
        return departures;
    }

    public static long getNextIdDeparture() {
        return nextIdDeparture;
    }

    public long getPricePerPerson() {
        return pricePerPerson;
    }

    public long getPricePerCar() {
        return pricePerCar;
    }

    public long getPricePerLorry() {
        return pricePerLorry;
    }

    public long getPricePerHeavy() {
        return pricePerHeavy;
    }

    public long getPricePerResident() {
        return pricePerResident;
    }

    public int getRemainingPeople() {
        return remainingPeople;
    }

    public int getRemainingCars() {
        return remainingCars;
    }

    public int getRemainingLorries() {
        return remainingLorries;
    }

    public int getRemainingHeavy() {
        return remainingHeavy;
    }

}
