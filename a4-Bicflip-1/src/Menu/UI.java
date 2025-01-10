package Menu;

import Entities.Pacient;
import Entities.Programare;
import Repository.Repo;
import Service.Service;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Scanner;



public class UI {
    public static void printMenu() {
        System.out.println("1. Gestiune pacienti");
        System.out.println("2. Gestiune programari");
        System.out.println("0. Exit");
    }

    public static void printPacientMenu() {
        System.out.println("1. Adauga pacient");
        System.out.println("2. Sterge pacient");
        System.out.println("3. Modifica pacient");
        System.out.println("4. Afiseaza pacienti");
        System.out.println("0. Inapoi");
    }

    public static void printProgramareMenu() {
        System.out.println("1. Adauga programare");
        System.out.println("2. Sterge programare");
        System.out.println("3. Modifica programare");
        System.out.println("4. Afiseaza programari");
        System.out.println("0. Inapoi");
    }

    public static void printError() {
        System.out.println("Optiune invalida!");
    }

    public static void printExit() {
        System.out.println("La revedere!");
    }

    public void run(){
        boolean start = true;
        Service service = new Service(new Repo());
        int id = 0;
        while (start) {
            printMenu();
            Scanner scanner = new Scanner(System.in);
            int optiuneaIntrodusaDeLaTastatura = scanner.nextInt();

            switch (optiuneaIntrodusaDeLaTastatura) {
                case 1:
                    printPacientMenu();
                    int optiuneaPacienti = scanner.nextInt();
                    switch (optiuneaPacienti) {
                        case 1:
                            System.out.println("Introduceti numele pacientului: ");
                            String nume = scanner.next();
                            System.out.println("Introduceti prenumele pacientului: ");
                            String prenume = scanner.next();
                            System.out.println("Introduceti anul nasterii pacientului: ");
                            int anulNasterii = scanner.nextInt();

                            id++;

                            Pacient pacient = new Pacient(id, nume, prenume, anulNasterii);
                            service.addEntity(pacient);
                            break;
                        case 2:
                            System.out.println("Introduceti ID-ul pacientului pe care doriti sa il stergeti: ");
                            int IdPacientToBeDeleted = scanner.nextInt();
                            service.removeEntityByID(IdPacientToBeDeleted);
                            break;
                        case 3:
                            System.out.println("Introduceti ID-ul pacientului pe care doriti sa il editati: ");
                            int idPacientDeModificat = scanner.nextInt();
                            System.out.println("Introduceti numele nou al pacientului: ");
                            String numeNou = scanner.next();
                            System.out.println("Introduceti prenumele nou al pacientului: ");
                            String prenumeNou = scanner.next();
                            System.out.println("Introduceti anul nasterii nou al pacientului: ");
                            int anulNasteriiNou = scanner.nextInt();
                            Pacient pacientNou = new Pacient(idPacientDeModificat, numeNou, prenumeNou, anulNasteriiNou);
                            service.updateEntityByID(idPacientDeModificat, pacientNou);
                            break;
                        case 4:
                            System.out.println(service.getAllPacienti());
                            break;
                        case 0:
                            break;
                        default:
                            printError();
                    }
                    break;
                case 2:
                    printProgramareMenu();
                    int optiuneaProgramare = scanner.nextInt();
                    switch (optiuneaProgramare){
                        case 1:
                            System.out.println("Introduceti ID-ul pacientului programat: ");
                            int idPacient = scanner.nextInt();
                            scanner.nextLine();

                            System.out.println("Introduceti data programarii (format dd-MM-yyyy hh:mm): ");
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

                            String data = scanner.nextLine();
                            LocalDateTime dateTime = LocalDateTime.parse(data, formatter);

                            System.out.println("Introduceti scopul programarii: ");
                            String scopulProgramarii = scanner.nextLine();

                            id++;

                            Programare programare = new Programare(id, (Pacient) service.findByID(idPacient), dateTime, scopulProgramarii);
                            service.addEntity(programare);
                            break;
                        case 2:
                            System.out.println("Introduceti ID-ul programarii pe care doriti sa o stergeti: ");
                            int IdProgramareToBeDeleted = scanner.nextInt();
                            service.removeEntityByID(IdProgramareToBeDeleted);
                            break;
                        case 3:
                            System.out.println("Introduceti ID-ul programarii pe care doriti sa o editati: ");
                            int idProgramareDeModificat = scanner.nextInt();
                            System.out.println("Introduceti Id-ul pacientului modificat: ");
                            int idPacientDeModificat = scanner.nextInt();
                            scanner.nextLine(); //citeste "\n" lasat in urma de nextInt()
                            System.out.println("Introduceti noua data si ora ale programarii: ");
                            String dataN = scanner.nextLine();

                            LocalDateTime dataNoua = LocalDateTime.parse(dataN, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

                            System.out.println("Introduceti noul scop al programarii: ");
                            String scopulProgramariiNou = scanner.nextLine();

                            if(service.findByID(idPacientDeModificat) == null || !(service.findByID(idPacientDeModificat) instanceof Pacient)){
                                System.out.println("Pacientul cu id-ul introdus nu exista!");
                                break;
                            }

                            Programare programareNoua= new Programare(idProgramareDeModificat, (Pacient) service.findByID(idPacientDeModificat), dataNoua, scopulProgramariiNou);
                            service.updateEntityByID(idProgramareDeModificat, programareNoua);
                            break;
                        case 4:
                            System.out.println(service.getAllProgramari());
                            break;
                        case 0:
                            break;
                        default:
                            printError();
                    }
                    break;
                case 0:
                    printExit();
                    start = false;
                    break;
                default:
                    printError();
            }
        }
    }








}
