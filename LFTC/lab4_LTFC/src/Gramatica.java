import java.util.*;

public class Gramatica {
    private Set<String> terminale;
    private Set<String> neterminale;
    private String simbolStart;
    private Map<String, List<String>> productii;

    public Gramatica() {
        terminale = new HashSet<>();
        neterminale = new HashSet<>();
        productii = new HashMap<>();
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

    // Verificarea regularității gramaticale
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

    // Conversia gramatica regulată -> automat finit
    public AutomatFinit conversieInAF() {
        AutomatFinit af = new AutomatFinit();
        af.adaugaStare("Sf"); // stare finală fictivă
        af.setStareInitiala(simbolStart);

        for (String neterminal : neterminale) {
            af.adaugaStare(neterminal);
        }

        for (String terminal : terminale) {
            af.adaugaSimbol(terminal);
        }

        for (Map.Entry<String, List<String>> entry : productii.entrySet()) {
            String lhs = entry.getKey();
            for (String rhs : entry.getValue()) {
                if (rhs.length() == 1) {
                    af.adaugaTranzitie(lhs, rhs, "Sf");
                } else if (rhs.length() == 2) {
                    af.adaugaTranzitie(lhs, String.valueOf(rhs.charAt(0)), String.valueOf(rhs.charAt(1)));
                }
            }
        }
        return af;
    }
}
