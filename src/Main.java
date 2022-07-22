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
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.print("Filename: ");
        String filename = new Scanner(System.in).nextLine();
        File file = Path.of(System.getProperty("user.home"), "Downloads", filename).toFile();
        List<Vector3D> vectorList = new LinkedList<>();
        vectorList.add(new Vector3D(42, 42, 42));
        Vector3D.writeToFile(file, vectorList);



        List<Vector3D> newVectorList = Vector3D.loadFromFile(file);

        for (Vector3D vector : newVectorList){
            System.out.println(vector);
        }







    }
}
