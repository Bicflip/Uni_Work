package domain;

import java.io.Serializable;

public abstract class FlightInstrument implements Serializable {
    private String code;
    private boolean maintenance;
    private static final long serialVersionUID = 1L; //asta e neaparat necesar pentru a functiona serializarea din Java

    public FlightInstrument(String code, boolean maintenance) {
        this.code = code;
        this.maintenance = maintenance;
    }

    public boolean getMaintenance() {
        return maintenance;
    }

    public abstract double getPrice();

    public String getCode() {
        return code;
    }

    public void setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
            return "FlightInstrument cu codul " + code + ", are mentenanta: " + maintenance + ", cu pretul: " + this.getPrice();
    }
}
