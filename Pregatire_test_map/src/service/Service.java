package service;
import domain.*;
import repo.Repo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Service {
    private static final String SAVE_FILE = "flight_instruments.bin";
    private static final String TEXT_SAVE = "flight_instruments.txt";
    private static final String TEXT_LOAD = "load.txt";
    private final Repo repository;

    public Service(Repo repository) {
        this.repository = repository;
    }

    public void add(FlightInstrument instrument) {
        repository.add(instrument);
    }

    public ArrayList<FlightInstrument> filterByPrice(double price) {
        ArrayList<FlightInstrument> instrumenteFiltrate = new ArrayList<FlightInstrument>();
        for(FlightInstrument instrument : repository.getInstrumente()) {
            if(instrument.getPrice() < price) {
                instrumenteFiltrate.add(instrument);
            }
        }
        return instrumenteFiltrate;
    }

    public ArrayList<FlightInstrument> getAll() {
        return this.repository.getInstrumente();
    }

    public void removeByCode(String code) {
        repository.removeByCode(code);
    }

    public void loadFromTextFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(TEXT_LOAD))) {
            String line = reader.readLine();
            while(line != null){
                String[] parts = line.split(", ");
                if(parts.length == 3 && (parts[2].equals("altitudine") || parts[2].equals("directia") || parts[2].equals("stare_motor"))){
                    repository.add(new HardInstrument(parts[0], Boolean.parseBoolean(parts[1]), parts[2]));
                } else if(parts.length == 3){
                    repository.add(new SoftInstrument(parts[0], Boolean.parseBoolean(parts[1]), Integer.parseInt(parts[2])));
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found!");
        } catch (IOException e) {
            throw new IOException("IO Exception!");
        }
    }

    public void loadFromBinaryFile() throws  IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            //Aici intra intr-o lista noua lista de instrumente din binary file
            //castul acela "()" transforma ce citeste ois.readObject() in lista de flightInstrumente
            List<FlightInstrument> instruments = (List<FlightInstrument>) ois.readObject();
            for (FlightInstrument instrument : instruments) {
                repository.add(instrument);
            }
        } catch (EOFException e) {
            throw new EOFException("File is empty");
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Class not found!");
        }catch (FileNotFoundException e){
            throw new FileNotFoundException("File not found!");
        }
    }

    public void saveToBinaryFile() throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(this.getAll());
        }
        catch (Exception e) {
            throw new Exception("File not found");
        }
    }

    public void saveToTextFile() throws Exception {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TEXT_SAVE))) {
            for (FlightInstrument instrument : this.getAll()) {
                writer.println(instrument);
            }
        }catch (Exception e) {
            throw new Exception("File not found");
        }
    }

    public boolean codDejaExistent(String cod) {
        for (FlightInstrument instrument : repository.getInstrumente()) {
            if (instrument.getCode().equals(cod)) {
                return true;
            }
        }
        return false;
    }

    public FlightInstrument getInstrumentByCode(String cod) throws Exception {
        for(FlightInstrument instrument : repository.getInstrumente()){
            if(instrument.getCode().equals(cod)) {
                return instrument;
            }
        }
        throw new Exception("Instrumentul nu exista!");

    }

    public void updateInstrument(String code, FlightInstrument updatedInstrument) {
        repository.updateInstrument(code, updatedInstrument);
    }

}
