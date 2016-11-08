package backendMockModel;

import java.util.List;

public class FerryDetail extends FerrySummary {

    private int peopleCapacity;
    private int vehicleCapacity;
    private int weightCapacity;
    private String ferryType;
    private static long nextIdFerry = 0;

    public FerryDetail( int capacityPeople, int capacityCars, int capacityLorries,
            String ferryType, String name, List<LineIdentifier> supportedLines, String id ) {
        super( name, supportedLines, id );
        this.peopleCapacity = peopleCapacity;
        this.vehicleCapacity = vehicleCapacity;
        this.weightCapacity = weightCapacity;
    }

    public int getPeopleCapacity() {
        return peopleCapacity;
    }

    public int getVehicleCapacity() {
        return vehicleCapacity;
    }

    public int getWeightCapacity() {
        return weightCapacity;
    }

    public String getFerryType() {
        return ferryType;
    }

}
