package src;
import java.util.List;
import java.util.Map;

public class DataSource {

    private List<List<Integer>> data;
    private String outputPath;

    public DataSource(List<List<Integer>> data, String outputPath) {
        this.data = data;
        this.outputPath = outputPath;
    }

    public List<List<Integer>> getData() {
        // TODO Auto-generated method stub
        return data;
    }
    
    public String getOutput() {
    	return this.outputPath;
    }
}
