import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Videogamemanager {

    private List<Videogame> videogames;

    public Videogamemanager(){
        this.videogames = new LinkedList<>();
    }

    public List<Videogame> getVideogames() {
        return new LinkedList<>(videogames);
    }

    public void addGame(Videogame videogame){
        videogames.add(videogame);
    }

    public void deleteGame(Videogame videogame){
        videogames.remove(videogame);
    }

    public void printVideogames(){
        for (Videogame videogame : videogames){
            System.out.println(videogame);
        }
    }

    public int getNumberOfGames(){
        return videogames.size();
    }

    public void readFromCSVFile(File filename, String fileEncoding) throws IOException{
        String line;
        LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(new FileInputStream(filename), fileEncoding));
        while((line = lineNumberReader.readLine()) != null){
            if (lineNumberReader.getLineNumber() > 1){
                 String[] splittedLine = line.split(",");
                 if (splittedLine.length == 3){
                     String[] studios = splittedLine[1].split(";");
                     try {
                         Videogame game = new Videogame(splittedLine[0], new LinkedList<>(Arrays.asList(studios)), splittedLine[2]);
                         this.addGame(game);
                     }catch (IllegalArgumentException e){
                         ParseLineInCSVException.addException(lineNumberReader.getLineNumber(), e.getMessage());
                     }
                 }else {
                     ParseLineInCSVException.addException(lineNumberReader.getLineNumber(), "data-fields must be three");
                 }
            }
        }
        lineNumberReader.close();
    }

    public void readFromCSVFile(String filename, String fileEncoding) throws IOException{
        readFromCSVFile(Paths.get(filename).toFile(), fileEncoding);
    }

    public void writeToCSVFile(String filename) {
        File file = Paths.get(System.getProperty("user.home"), "Downloads", filename).toFile();
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8.name()))){
            writer.write("name,developers,releasedate");
            writer.newLine();
            for (Videogame game : getVideogames()){
                writer.write(game.getName() + ",");
                int numberOfStudios = game.getStudios().size();
                for (String studio : game.getStudios()){
                    writer.write(studio);
                    if (--numberOfStudios > 0){
                        writer.write(";");
                    }
                }
                writer.write(",");
                writer.write(game.releaseDate);
                writer.newLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }














    public static class ParseLineInCSVException extends Exception {
        private static final Map<Integer, String> lineToError = new TreeMap<>();

        private ParseLineInCSVException(int line, String message) {
            lineToError.put(line, message);
        }

        private static void addException(int line, String message) {
            lineToError.put(line, message);
        }

        public static void printExceptionMap(){
            for (int key : lineToError.keySet()){
                System.out.println("Line: " + key + " Message: " + lineToError.get(key));
            }
        }

    }




    /**
         * Videogame class presents videogames and stores their metadata
         *
         * @author ichibati
         */
    public static class Videogame {

            /**
             * name of the game
             */
            private String name;

            /**
             * list of all developing studios as Strings
             */
            private List<String> studios;

            /**
             * date games was published
             */
            private String releaseDate;

            /**
             * Constructor sets basic values
             *
             * @param name        name of game non-null and not empty string
             * @param studios     list of studios which developed game non-null / not empty
             * @param releaseDate date of release non-null
             * @throws IllegalArgumentException if one of passed arguments are not valid
             */
            public Videogame(String name, List<String> studios, String releaseDate) throws IllegalArgumentException {

                this.setName(name);
                this.setStudios(studios);
                this.setReleaseDate(releaseDate);

            }

            /**
             * return name of game
             *
             * @return name
             */
            private String getName() {
                return name;
            }

            /**
             * sets name of game
             *
             * @param name of game non-null and lenght > 1
             */
            private void setName(String name) {
                if (name.isBlank()) {
                    throw new IllegalArgumentException("invalid name");
                }
                this.name = name;
            }

            /**
             * delivers linked-list of developing studios of game
             *
             * @return linked-list
             */
            private List<String> getStudios() {
                return new LinkedList<>(studios);
            }

            /**
             * sets studios of game
             *
             * @param studios list of String of developing studios non-null
             * @throws IllegalArgumentException if studios == null or empty
             */
            private void setStudios(List<String> studios) {
                if (studios == null || studios.isEmpty()) {
                    throw new IllegalArgumentException("invalid studios");
                }
                this.studios = new LinkedList<>(studios);
            }

            /**
             * delivers release date of game
             *
             * @return release-date in form of string
             */
            private String getReleaseDate() {
                return releaseDate;
            }

            /**
             * sets date of release of game
             *
             * @param releaseDate date of release d.mm.yy / d-mmm-yy
             * @throws IllegalArgumentException @throws IllegalArgumentException if date has not following format d.mm.yy / d-mmm-yy or is null
             */
            private void setReleaseDate(String releaseDate) throws IllegalArgumentException {
                SimpleDateFormat csvFormat = new SimpleDateFormat("d-MMM-yy", Locale.ENGLISH);
                SimpleDateFormat defaultFormat = new SimpleDateFormat("d.MM.yy");
                defaultFormat.setLenient(false);

                try {
                    Date date = csvFormat.parse(releaseDate);
                    this.releaseDate = releaseDate;
                } catch (ParseException e1) {
                    try {
                        Date date = defaultFormat.parse(releaseDate);
                        this.releaseDate = csvFormat.format(date);
                    } catch (ParseException e2) {
                        throw new IllegalArgumentException("Argument 'releaseDate' invalid");
                    }
                }
            }

            /**
             * delivers string view of object
             *
             * @return String of objects attributes-values
             */
            @Override
            public String toString() {
                return "Videogame{" +
                        "name='" + name + '\'' +
                        ", studios=" + studios +
                        ", releaseDate='" + releaseDate + '\'' +
                        '}';
            }

        }



}



