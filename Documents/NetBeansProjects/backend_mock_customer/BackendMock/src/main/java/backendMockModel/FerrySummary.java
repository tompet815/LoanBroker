package backendMockModel;

import java.util.List;

public class FerrySummary extends FerryIdentifier{
    private String name;
    private List<LineIdentifier> supportedLines;
    
    public FerrySummary(String name, List<LineIdentifier> supportedLines, String id) {
        super(id);
        this.name = name;
        this.supportedLines = supportedLines;
    }

    public String getName() {
        return name;
    }

    public List<LineIdentifier> getSupportedLines() {
        return supportedLines;
    }
}
