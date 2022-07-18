import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Vector3D {
    private float x;
    private float y;
    private float z;

    public Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public static List<Vector3D> loadFromFile(File file) throws IOException, FileFormatException {
        List<Vector3D> vectors = new LinkedList<>();
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
        byte[] fileIdentifier = new byte[3];
        dataInputStream.read(fileIdentifier, 0, 3);
        if (fileIdentifier[0] == 'V' && fileIdentifier[1] == 'E' && fileIdentifier[2] == 'C'){
            int numberOfVectors;
            if ((numberOfVectors = dataInputStream.readInt()) > 0){
                for (int i = 0 ; i < numberOfVectors ; i++){
                    vectors.add(new Vector3D(dataInputStream.readFloat(), dataInputStream.readFloat(), dataInputStream.readFloat()));
                }
            }else{
                throw new FileFormatException("no vector in " + file.getAbsolutePath());
            }

        }else{
            throw new FileFormatException("missing VEC identifier in " + file.getAbsolutePath());
        }
        return vectors;



    }

    public String toString(){
        return "[" + x + ", " + y + ", " + z + "]";
    }

}
