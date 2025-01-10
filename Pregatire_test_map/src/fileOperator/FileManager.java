package fileOperator;
import domain.FlightInstrument;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String SAVE_FILE = "flight_instruments.bin";
    private static final String TEXT_SAVE = "flight_instruments.txt";

    public static void saveToBinaryFile(List<FlightInstrument> instruments) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(instruments);
        }
    }

    public static void saveToTextFile(List<FlightInstrument> instruments) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TEXT_SAVE))) {
            for (FlightInstrument instrument : instruments) {
                writer.println(instrument);
            }
        }
    }

    public static List<FlightInstrument> loadFromFile() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            return (List<FlightInstrument>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
