import java.util.*;

public class AutomatFinit {
    private Set<String> stari;
    private Set<String> alfabet;
    private String stareInitiala;
    private Set<String> stariFinale;
    private Map<String, Map<String, Set<String>>> tranzitii;

    public AutomatFinit() {
        stari = new HashSet<>();
        alfabet = new HashSet<>();
        stariFinale = new HashSet<>();
        tranzitii = new HashMap<>();
    }

    public void adaugaStare(String stare) {
        stari.add(stare);
    }

    public void adaugaSimbol(String simbol) {
        alfabet.add(simbol);
    }

    public void setStareInitiala(String stare) {
        stareInitiala = stare;
    }

    public void adaugaStareFinala(String stare) {
        stariFinale.add(stare);
    }

    public void adaugaTranzitie(String stare, String simbol, String stareNoua) {
        tranzitii.putIfAbsent(stare, new HashMap<>());
        tranzitii.get(stare).putIfAbsent(simbol, new HashSet<>());
        tranzitii.get(stare).get(simbol).add(stareNoua);
    }

    public void afisare() {
        System.out.println("Stări: " + stari);
        System.out.println("Alfabet: " + alfabet);
        System.out.println("Stare inițială: " + stareInitiala);
        System.out.println("Stări finale: " + stariFinale);
        System.out.println("Tranziții:");
        for (Map.Entry<String, Map<String, Set<String>>> entry : tranzitii.entrySet()) {
            String stare = entry.getKey();
            for (Map.Entry<String, Set<String>> tranzitiiEntry : entry.getValue().entrySet()) {
                String simbol = tranzitiiEntry.getKey();
                for (String stareNoua : tranzitiiEntry.getValue()) {
                    System.out.println("  " + stare + " --" + simbol + "--> " + stareNoua);
                }
            }
        }
    }
}
