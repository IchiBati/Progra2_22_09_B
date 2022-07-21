import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        File file = Path.of(System.getProperty("user.home"), "Downloads", "example.b3v").toFile();
        List<Vector3D> vectorList = new LinkedList<>();
        vectorList.add(new Vector3D(11, 11, 11));
        Vector3D.writeToFile(file, vectorList);






    }
}
