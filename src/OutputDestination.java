import java.io.File;

public class OutputDestination {

    private File file;

    public OutputDestination(File f) {
        this.file = f;
    }


    public File getFile() {
        return file;
    }
}
