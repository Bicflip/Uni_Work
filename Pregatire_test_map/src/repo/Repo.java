package repo;

import domain.FlightInstrument;

import java.util.ArrayList;

public class Repo {
    private final ArrayList<FlightInstrument> listaInstrumente;

    public Repo() {
        listaInstrumente = new ArrayList<>();
    }

    public void add(FlightInstrument instrument) {
        listaInstrumente.add(instrument);
    }

    public void removeByCode(String code) {
        for (FlightInstrument instrument : listaInstrumente) {
            if (instrument.getCode().equals(code)) {
                listaInstrumente.remove(instrument);
                break;
            }
        }
    }

    public void updateInstrument(String code, FlightInstrument updatedInstrument) {
        for (FlightInstrument instrument : listaInstrumente){
            if(instrument.getCode().equals(code)){
                listaInstrumente.set(listaInstrumente.indexOf(instrument), updatedInstrument);
            }
        }
    }

    public ArrayList<FlightInstrument> getInstrumente() {
        return listaInstrumente;
    }
}
