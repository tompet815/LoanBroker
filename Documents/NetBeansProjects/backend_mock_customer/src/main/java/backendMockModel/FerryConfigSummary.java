package backendMockModel;

public class FerryConfigSummary extends FerryConfigIdentifier{
    private String name;
    
    public FerryConfigSummary(String name, int id) {
        super(id);
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
