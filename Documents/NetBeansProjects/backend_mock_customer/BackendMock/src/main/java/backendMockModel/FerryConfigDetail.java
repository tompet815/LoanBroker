package backendMockModel;

import java.util.List;

public class FerryConfigDetail extends FerryConfigSummary{

    private int peopleCapacity;
    private int vehicleCapacity;
    private int weightCapacity;
    private static long nextIdFerryConfig = 0;

    public FerryConfigDetail( int id, String name, int peopleCapacity, int vehicleCapacity, int weightCapacity ) {
        super( name, id );
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

}
