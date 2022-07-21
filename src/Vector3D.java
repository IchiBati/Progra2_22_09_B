import java.io.*;
import java.nio.*;
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

        //hasValidFormat(file);

        List<Vector3D> vectors = new LinkedList<>();

        try(DataInputStream bytesOfVectors = new DataInputStream(new FileInputStream(file))) {

            bytesOfVectors.skipBytes(7);

            int numberOfVectorBytes = bytesOfVectors.readAllBytes().length;
            bytesOfVectors.close();

            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
            byte[] fileIdentifier = new byte[3];
            dataInputStream.read(fileIdentifier, 0, 3);

            int numberOfVectors = dataInputStream.readInt() * 12;

            for (int i = 0; i < numberOfVectors / 12; i++) {
                vectors.add(new Vector3D(dataInputStream.readFloat(), dataInputStream.readFloat(), dataInputStream.readFloat()));
            }

            return vectors;
        }



    }

    private static void hasValidFormat(File file ) throws IOException, FileFormatException{

        try(DataInputStream in = new DataInputStream(new FileInputStream(file))) {

            if (in.readByte() == 86 && in.readByte() == 69 && in.readByte() == 67) {

                if ((in.readInt() * 12) == (in.readAllBytes().length)){ // failed

                }else{
                    throw new FileFormatException("invalid numbers of vectors");
                }

            } else {
                throw new FileFormatException("No b3v file");
            }
        }


    }

    public static void writeToFile(File file, List<Vector3D> vectors) throws IOException, FileFormatException{
        if (file.exists()){
            hasValidFormat(file);
            try(DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
               ){

                   byte[] vec = inputStream.readNBytes(3);
                   int numberOfVec = inputStream.readInt() + vectors.size();
                   byte[] vecData = inputStream.readAllBytes();

                    /**
                   outputStream.write(vec);
                   outputStream.writeInt(numberOfVec);
                   outputStream.write(vecData);
                   for (Vector3D vector : vectors){
                       outputStream.writeFloat(vector.x);
                       outputStream.writeFloat(vector.y);
                       outputStream.writeFloat(vector.z);
                   }**/
               }

        }
    }

    public String toString(){
        return "[" + x + ", " + y + ", " + z + "]";
    }

}
