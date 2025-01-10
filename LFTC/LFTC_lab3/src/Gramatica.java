import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Gramatica {
    private Set<String> terminale;
    private Set<String> neterminale;
    private String simbolStart;
    private Map<String, List<String>> productii;

    public Gramatica(Set<String> terminals, Set <String> nonterminals, String startSymbol, Map<String, List<String>>productions) {
        this.terminale = terminals;
        this.neterminale = nonterminals;
        this.simbolStart = startSymbol;
        this.productii = productions;
    }

    public Gramatica(String fileName) throws Exception {
        this.terminale = new HashSet<>();
        this.neterminale = new HashSet<>();
        this.productii = new HashMap<>();
        citireDinFisier(fileName);

    }

    public void citireDinFisier(String fisier) throws Exception {
        Scanner sc = new Scanner(new java.io.File(fisier));
        String[] neterminaleArray = sc.nextLine().split("\\s+");
        Collections.addAll(neterminale, neterminaleArray);

        String[] terminaleArray = sc.nextLine().split("\\s+");
        Collections.addAll(terminale, terminaleArray);

        simbolStart = sc.nextLine().trim();

        int nrProductii = Integer.parseInt(sc.nextLine().trim());
        for (int i = 0; i < nrProductii; i++) {
            String linie = sc.nextLine();
            String[] parts = linie.split("->");
            String lhs = parts[0].trim();
            String[] rhsArray = parts[1].trim().split("\\|");
            productii.putIfAbsent(lhs, new ArrayList<>());
            productii.get(lhs).addAll(Arrays.asList(rhsArray));
        }
        sc.close();
    }

    public void afisare() {
        System.out.println("Non-terminale: " + neterminale);
        System.out.println("Terminale: " + terminale);
        System.out.println("Simbol de start: " + simbolStart);
        System.out.println("Producții:");
        for (Map.Entry<String, List<String>> entry : productii.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + String.join(" | ", entry.getValue()));
        }
    }

    public boolean verificareRegularitate() {
        for (String lhs : productii.keySet()) {
            if (!neterminale.contains(lhs)) return false;
            for (String rhs : productii.get(lhs)) {
                if (rhs.length() == 1) {
                    if (!terminale.contains(rhs)) return false;
                } else if (rhs.length() == 2) {
                    char t = rhs.charAt(0);
                    char n = rhs.charAt(1);
                    if (!terminale.contains(String.valueOf(t)) || !neterminale.contains(String.valueOf(n))) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public Automata conversieInAF() {

        // Seturi pentru stări, alfabet (simboluri terminale) și stări finale
        Set<String> stari = new HashSet<>();
        Set<String> alfabet = new HashSet<>();
        Set<String> stariFinale = new HashSet<>();
        Map<String, Map<String, Set<String>>> tranzitii = new HashMap<>();

        // Starea initiala este simbolul de start
        String stareInitiala = simbolStart;

        // Adăugăm stările și simbolurile
        stari.add(stareInitiala); // Simbolul de start este adăugat în stări
        stari.add("Sf"); // Adăugăm o stare finală fictivă
        stari.addAll(neterminale); // Adăugăm neterminalele în stări
        alfabet.addAll(terminale); // Adăugăm terminalele în alfabet

        // Adăugăm tranzițiile pe baza producțiilor
        for (Map.Entry<String, List<String>> entry : productii.entrySet()) {
            String lhs = entry.getKey();
            for (String rhs : entry.getValue()) {
                // Inițializăm tranzițiile pentru fiecare neterminal
                tranzitii.putIfAbsent(lhs, new HashMap<>());

                if (rhs.length() == 1) {
                    // Dacă partea dreaptă are un singur simbol, este un terminal
                    String terminal = String.valueOf(rhs.charAt(0));
                    tranzitii.get(lhs).putIfAbsent(terminal, new HashSet<>());
                    tranzitii.get(lhs).get(terminal).add("Sf"); // Tranziție către starea finală fictivă
                } else if (rhs.length() == 2) {
                    // Dacă partea dreaptă are două simboluri, este un terminal urmat de un neterminal
                    String terminal = String.valueOf(rhs.charAt(0));
                    String neterminal = String.valueOf(rhs.charAt(1));

                    tranzitii.get(lhs).putIfAbsent(terminal, new HashSet<>());
                    tranzitii.get(lhs).get(terminal).add(neterminal); // Tranziție către neterminal
                }
            }
        }

        // Adăugăm stările finale în setul de stări finale
        stariFinale.add("Sf");

        // Construim Automata cu constructorul specificat
        List<String> statesList = new ArrayList<>(stari);
        List<String> finalStatesList = new ArrayList<>(stariFinale);
        List<String> alphabetList = new ArrayList<>(alfabet);

        // Cream obiectul Automata
        Map<Pair<String, String>, Set<String>> transitionsMap = new HashMap<>();

        for (Map.Entry<String, Map<String, Set<String>>> entry : tranzitii.entrySet()) {
            String state = entry.getKey();
            for (Map.Entry<String, Set<String>> innerEntry : entry.getValue().entrySet()) {
                String symbol = innerEntry.getKey();
                Set<String> targets = innerEntry.getValue();
                transitionsMap.put(new Pair<>(state, symbol), targets);
            }
        }

        // Returnăm automatul creat
        return new Automata(statesList, stareInitiala, alphabetList, finalStatesList, transitionsMap);
    }

    @Override
    public String toString() {
        return "Non-terminale: " + neterminale + "\n" +
               "Terminale: " + terminale + "\n" +
               "Simbol de start: " + simbolStart + "\n" +
               "Producții: " + productii;
    }

}
