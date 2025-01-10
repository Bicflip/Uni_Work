import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void printMenuFA() {
        System.out.println("Options");
        System.out.println("1. Print States");
        System.out.println("2. Print Alphabet");
        System.out.println("3. Print Initial State");
        System.out.println("4. Print Final States");
        System.out.println("5. Print Transitions");
        System.out.println("6. Print Sequence Acceptance");
        System.out.println("7. Print what state another state gets through a symbol");
        System.out.println("8. Check if grammar is regular");
        System.out.println("9. Convert AF to GR");
        System.out.println("10. Convert RG to AF");
        System.out.println("0. Exit");
    }

    public static void runFiniteAutomata() {
        String fisier_gramatica = "C://Facultate//LFTC//LFTC_lab3//src//gramatica.txt";
        String fisier_automata = "C://Facultate//LFTC//LFTC_lab3//src//AutomataFinita.txt";
        Automata FA = new Automata(fisier_automata);

        boolean run = true;

        while(run) {
            printMenuFA();
            Scanner scanner = new Scanner(System.in);
            int input = scanner.nextInt();
            switch (input) {
                case 1:
                    System.out.println("States:");
                    System.out.println(FA.getStates());
                    break;
                case 2:
                    System.out.println("Alphabet:");
                    System.out.println(FA.getAlphabet());
                    break;
                case 3:
                    System.out.println("Initial state:");
                    System.out.println(FA.getInitialState());
                    break;
                case 4:
                    System.out.println("Final states:");
                    System.out.println(FA.getFinalStates());
                    break;
                case 5:
                    System.out.println("Transitions:");
                    System.out.println(FA.getTransitionsToString());
                    break;
                case 6:
                    System.out.println("\nWrite the sequence:");
                    Scanner sequenceScanner = new Scanner(System.in);
                    String sequence = sequenceScanner.nextLine();

                    if (FA.acceptsSequence(sequence)) {
                        System.out.println("Sequence is accepted by the FA!");
                    } else {
                        System.out.println("Sequence is NOT accepted by the FA!");
                    }
                    break;
                case 7:
                    System.out.print("Write state and symbol (separated by space): ");
                    Scanner linie = new Scanner(System.in);
                    String line = linie.nextLine();
                    String[] components = line.split(" ");

                    if (components.length != 2) {
                        System.out.println("Invalid Input! You need to introduce a state and a symbol separated by space");
                        continue;
                    }

                    String state = components[0];
                    String symbol = components[1];
                    Set<String> nextStates = FA.getNextStates(state, symbol);

                    if (nextStates.isEmpty()) {
                        System.out.println("There is no '" + state + "' for '" + symbol + "'.");
                    } else {
                        System.out.println("From state '" + state + "' through '" + symbol + "' we get the states: " + nextStates);
                    }
                    break;
                case 8:
                    Gramatica grammar;
                    try {
                        grammar = new Gramatica(fisier_gramatica);
                    } catch (Exception e) {
                        System.out.println("Error reading grammar from file");
                        break;
                    }

                    grammar.afisare();

                    if(grammar.verificareRegularitate()){
                        System.out.println("Grammar is regular");
                    } else {
                        System.out.println("Grammar is not regular");
                    }
                    break;
                case 9:
                    Gramatica gramatica;
                    try {
                        gramatica = new Gramatica(fisier_gramatica);
                    } catch (Exception e) {
                        System.out.println("Error reading grammar from file");
                        break;
                    }
                    System.out.println("Starile");
                    System.out.println(gramatica.conversieInAF().getStates());
                    System.out.println("Alfabetul");
                    System.out.println(gramatica.conversieInAF().getAlphabet());
                    System.out.println("Starea initiala");
                    System.out.println(gramatica.conversieInAF().getInitialState());
                    System.out.println("Starile finale");
                    System.out.println(gramatica.conversieInAF().getFinalStates());

                    System.out.println(gramatica.conversieInAF());
                    break;
                case 10:

                    Gramatica g1;
                    try {
                        g1 = new Gramatica(fisier_gramatica);
                    } catch (Exception e) {
                        System.out.println("Error reading grammar from file");
                        break;
                    }
                    System.out.println(g1.conversieInAF());

//                    Automata automat;
//                    try {
//                        automat = new Automata(fisier_automata);
//                    } catch (Exception e) {
//                        System.out.println("Error reading grammar from file");
//                        break;
//                    }
                    System.out.println("Automata din gramatica regulata:");
                    System.out.println(g1.conversieInAF().conversieInGramatica().toString());
                    break;

                case 0:
                    run = false;
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }

        }
    }

    public static void main(String[] args) {
        runFiniteAutomata();
    }
}
