package backendMockModel;

public class ReservationSummary extends ReservationIdentifier{
    private double totalPrice;
    
    public ReservationSummary(double totalPrice, int id) {
        super(id);
        this.totalPrice = totalPrice;
    }
    
}
