import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Vector3D vector = new Vector3D(1, 2, 3);
        List<Vector3D> vectors = Vector3D.loadFromFile(Paths.get(System.getProperty("user.home"), "Downloads", "example.b3v").toFile());


    }
}
