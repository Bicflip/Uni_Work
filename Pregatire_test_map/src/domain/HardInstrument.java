package domain;
import java.util.Objects;

public class HardInstrument extends FlightInstrument{
    String measurementType;

    public HardInstrument(String code, boolean maintenance, String measurementType) {
        super(code, maintenance);
        this.measurementType = measurementType;
    }

    @Override
    public double getPrice() {
        double price = 1;
        if (Objects.equals(measurementType, "altitudine") || Objects.equals(measurementType, "directia")) {
            price =50;
            if(super.getMaintenance()) {
                return price*2;
            }
            else return price;
        }
        else if (Objects.equals(measurementType, "stare_motor")) {
            price = 500;
            return price;
        }
        else return price;
    }

    @Override
    public String toString() {
        return super.toString() + ", Instrument de masurare pentru: " + measurementType;
    }
}
