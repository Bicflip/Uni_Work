import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Automata {

    private String initialState;

    private List<String> states;

    private List<String> alphabet;

    private List<String> finalStates;

    private final Map<Pair<String, String>, Set<String>> transitions;



    private void readFromFile(String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {

            String ELEM_SEPARATOR = ";";
            this.states = new ArrayList<>(List.of(scanner.nextLine().split(ELEM_SEPARATOR)));

            this.initialState = scanner.nextLine();

            this.alphabet = new ArrayList<>(List.of(scanner.nextLine().split(ELEM_SEPARATOR)));

            this.finalStates = new ArrayList<>(List.of(scanner.nextLine().split(ELEM_SEPARATOR)));

            while (scanner.hasNextLine()) {

                String transitionLine = scanner.nextLine();
                String[] transitionComponents = transitionLine.split(" ");

                if (states.contains(transitionComponents[0]) && states.contains(transitionComponents[2]) && alphabet.contains(transitionComponents[1])) {

                    Pair<String, String> transitionStates = new Pair<>(transitionComponents[0], transitionComponents[1]);

                    if (!transitions.containsKey(transitionStates)) {
                        Set<String> transitionStatesSet = new HashSet<>();
                        transitionStatesSet.add(transitionComponents[2]);
                        transitions.put(transitionStates, transitionStatesSet);
                    } else {
                        transitions.get(transitionStates).add(transitionComponents[2]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Automata(List<String> states, String initialState, List<String> alphabet, List<String> finalStates, Map<Pair<String, String>, Set<String>> transitions) {
        this.states = states;
        this.initialState = initialState;
        this.alphabet = alphabet;
        this.finalStates = finalStates;
        this.transitions = transitions;
    }



    public Automata(String fileName) {
        this.transitions = new HashMap<>();
        readFromFile(fileName);
    }

    public List<String> getStates() {
        return states;
    }

    public String getInitialState() {
        return initialState;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public List<String> getFinalStates() {
        return finalStates;
    }

    public Map<Pair<String, String>, Set<String>> getTransitions() {
        return transitions;
    }

    public String getTransitionsToString() {
        return transitions.toString();
    }

    @Override
    public String toString() {
        StringBuilder transitionsString = new StringBuilder();
        transitionsString.append("Transitions:\n");
        this.transitions.forEach((K, V) -> {
            transitionsString.append("<").append(K.getFirst()).append(",").append(K.getSecond()).append("> -> ").append(V).append("\n");
        });

        return transitionsString.toString();
    }


    public boolean acceptsSequence(String sequence) {
        String currentState = this.initialState;

        for (int i = 0; i < sequence.length(); i++) {
            String currentSymbol = sequence.substring(i, i + 1);

            // Creez perechea pentru tranziție
            Pair<String, String> transition = new Pair<>(currentState, currentSymbol);

            // Verific dacă există o tranziție pentru simbolul curent
            if (!this.transitions.containsKey(transition)) {
                return false; // Dacă nu există tranziția, secvența NU este acceptată
            } else {
                // Actualizez starea curentă
                currentState = this.transitions.get(transition).iterator().next();
            }
        }

        // După procesarea întregii secvențe, verific dacă sunt într-o stare finală
        return this.finalStates.contains(currentState);
    }

    public Set<String> getNextStates(String currentState, String symbol) {
        // Creez perechea pentru tranziție
        Pair<String, String> transition = new Pair<>(currentState, symbol);

        // Returnez stările în care se ajunge, dacă tranziția există
        if (this.transitions.containsKey(transition)) {
            return this.transitions.get(transition);
        } else {
            // Dacă nu există tranziția, returnez un set gol
            return new HashSet<>();
        }
    }

    public boolean isDeterministic() {
        for (Map.Entry<Pair<String, String>, Set<String>> entry : transitions.entrySet()) {
            if (entry.getValue().size() > 1) {
                return false; // Există mai multe tranziții pentru aceeași pereche (stare, simbol).
            }
        }
        return true;
    }



    public Gramatica conversieInGramatica() {
        // Seturi pentru terminale și neterminale
        Set<String> terminale = new HashSet<>();
        Set<String> neterminale = new HashSet<>();
        Map<String, List<String>> productii = new HashMap<>();

        // Adăugăm simbolul de start
        String simbolStart = this.initialState;
        neterminale.add(simbolStart);

        // Adăugăm stările
        this.states.forEach(neterminale::add);

        // Adăugăm terminalele din alfabet
        this.alphabet.forEach(terminale::add);

        // Adăugăm producțiile
        for (Map.Entry<Pair<String, String>, Set<String>> entry : this.transitions.entrySet()) {
            String stareCurenta = entry.getKey().getFirst();
            String simbol = entry.getKey().getSecond();
            Set<String> stariTinta = entry.getValue();

            for (String stareTinta : stariTinta) {
                // Dacă există o tranziție de tipul stareCurenta -> simbol -> stareTinta
                String productie = simbol + stareTinta;
                productii.putIfAbsent(stareCurenta, new ArrayList<>());
                productii.get(stareCurenta).add(productie);
            }
        }

        // Adăugăm producția pentru stările finale
        for (String stareFinala : this.finalStates) {
            productii.putIfAbsent(stareFinala, new ArrayList<>());
            productii.get(stareFinala).add("ε");  // Tranzit la cuvântul vid pentru stările finale
        }

        // Returnăm o nouă gramatică
        return new Gramatica(terminale, neterminale, simbolStart, productii);
    }




}