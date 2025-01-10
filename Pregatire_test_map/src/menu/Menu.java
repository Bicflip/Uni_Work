package menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import domain.FlightInstrument;
import domain.HardInstrument;
import domain.SoftInstrument;
import repo.Repo;
import service.Service;

public class Menu {
    
    private Service service;

    public Menu() {
        this.service = new Service(new Repo());
    }
        

    public void printMenu() {
        System.out.println("1. Afisare instrumente cu pret mai mic decat o valoare data.");
        System.out.println("2. Salvare instrumente in fisier binar.");
        System.out.println("3. Salvare instrumente in fisier text.");
        System.out.println("4. Sterge instrument din lista, dupa cod.");
        System.out.println("5. Adaugare instrument.");
        System.out.println("6. Incarcare intrumente din fisier text.");
        System.out.println("7. Update instrumente.");
        System.out.println("0. Exit");
    }

    public void run() throws IOException {
        boolean start = true;
        try{
            this.service.loadFromBinaryFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if(this.service.getAll().isEmpty()){
            System.out.println("Lista este goala, se adauga instrumente hardcodate.");
            service.add(new SoftInstrument("SFT001", true, 5));
            service.add(new SoftInstrument("SFT002", false, 12));
            service.add(new HardInstrument("HARD001", true, "altitudine"));
            service.add(new HardInstrument("HARD002", false, "stare_motor"));
            service.add(new HardInstrument("HARD003", true, "directia"));
            service.add(new HardInstrument("HARD004", false, "altitudine"));
        }
        while (start) {
            this.printMenu();
            Scanner scanner = new Scanner(System.in);
            int optiune = scanner.nextInt();
            switch (optiune) {
                case 1:
                    System.out.println("Introduceti pretul: ");
                    Scanner scannerPret = new Scanner(System.in);
                    double pret = scannerPret.nextDouble();
                    ArrayList<FlightInstrument> instrumenteFiltrate = service.filterByPrice(pret);
                    for (FlightInstrument instrument : instrumenteFiltrate) {
                        System.out.println(instrument);
                    }
                    break;
                case 2:
                    try{
                        this.service.saveToBinaryFile();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Introduceti codul: ");
                    Scanner code = new Scanner(System.in);
                    String cod = code.nextLine();
                    this.service.removeByCode(cod);
                    ArrayList<FlightInstrument> instrumente = service.getAll();
                    for (FlightInstrument instrument : instrumente) {
                        System.out.println(instrument);
                    }
                    break;
                case 3:
                    try{
                        this.service.saveToTextFile();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Introduceti tipul instrumentului (Hard = 1; Soft = 2) ");
                    Scanner scannerTip = new Scanner(System.in);
                    int tip = scannerTip.nextInt();
                    if(tip == 1){
                        System.out.println("Introdceti codul: ");
                        Scanner scannerCod = new Scanner(System.in);
                        String codHard = scannerCod.nextLine();
                        if(service.codDejaExistent(codHard)){
                            System.out.println("Cod deja existent!");
                            break;
                        }
                        System.out.println("Introduceti tipul de masurare (altitudine, stare_motor, directia): ");
                        Scanner scannerTipMasurare = new Scanner(System.in);
                        String tipMasurare = scannerTipMasurare.nextLine();
                        if(tipMasurare.equals("altitudine") || tipMasurare.equals("stare_motor") || tipMasurare.equals("directia")) {
                            System.out.println("Introduceti daca este achizitionat cu mentenanta (true/false): ");
                            Scanner scannerMentenanta = new Scanner(System.in);
                            boolean mentenanta = scannerMentenanta.nextBoolean();
                            service.add(new HardInstrument(codHard, mentenanta, tipMasurare));
                        }else {
                            System.out.println("Tip de masurare invalid!");
                        }
                    }else if(tip == 2) {
                        System.out.println("Introduceti codul: ");
                        Scanner scannerCod = new Scanner(System.in);
                        String codSoft = scannerCod.nextLine();
                        if(service.codDejaExistent(codSoft)){
                            System.out.println("Cod deja existent!");
                            break;
                        }
                        System.out.println("Introduceti versiunea: ");
                        Scanner scannerVersiune = new Scanner(System.in);
                        int versiune = scannerVersiune.nextInt();
                        System.out.println("Introduceti daca este achizitionat cu mentenanta (true/false): ");
                        Scanner scannerMentenanta = new Scanner(System.in);
                        boolean mentenanta = scannerMentenanta.nextBoolean();
                        service.add(new SoftInstrument(codSoft, mentenanta, versiune));
                    }else {
                        System.out.println("Tip invalid!");
                    }
                    break;
                case 6:
                    try{
                        this.service.loadFromTextFile();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 7:
                    try{
                        System.out.println("Introdceti codul: ");
                        Scanner scannerCod = new Scanner(System.in);
                        String codInstr = scannerCod.nextLine();
                        FlightInstrument instrumentUpdated = service.getInstrumentByCode(codInstr);
                        System.out.println("Introduceti noul tipul de mentenanta (true/false): ");
                        Scanner scannerMentenanta = new Scanner(System.in);
                        boolean updatedMaintenance = scannerMentenanta.nextBoolean();

                        if(instrumentUpdated instanceof SoftInstrument){
                            System.out.println("Introduceti noua versiune: ");
                            Scanner scannerNewVersion = new Scanner(System.in);
                            int newVersion = scannerNewVersion.nextInt();
                            SoftInstrument updatedSoftInstrument = new SoftInstrument(codInstr, updatedMaintenance, newVersion);
                            service.updateInstrument(codInstr, updatedSoftInstrument);
                        }else if(instrumentUpdated instanceof HardInstrument){
                            System.out.println("Introduceti noul parametru de masurat: ");
                            Scanner scannerNewMeasurement = new Scanner(System.in);
                            String newMeasurement = scannerNewMeasurement.nextLine();
                            if(newMeasurement.equals("altitudine") || newMeasurement.equals("stare_motor") || newMeasurement.equals("directia")) {
                                HardInstrument updatedHardInstrument = new HardInstrument(codInstr, updatedMaintenance, newMeasurement);
                                service.updateInstrument(codInstr, updatedHardInstrument);
                            }
                            else {
                                System.out.println("Parametru de masurat invalid!");

                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("La revedere!");
                    start = false;
                    break;
                default:
                    System.out.println("Optiune invalida!");
            }
        }
    }
}