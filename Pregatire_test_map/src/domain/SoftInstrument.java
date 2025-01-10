package domain;

public class SoftInstrument extends FlightInstrument {
    int version;

    public SoftInstrument(String code, boolean maintenance, int version) {
        super(code, maintenance);
        this.version = version;
    }

    @Override
    public double getPrice() {
        double price = 200.0;
        if(version > 10) {
            price = 100.0;
            if(super.getMaintenance()) {
                return price*2;
            }
            return price;
        }
        else{
            if(super.getMaintenance()) {
                return price * 2;
            }
            return price;
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", Soft de versiune: " + version;
    }
}
