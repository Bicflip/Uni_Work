public class Main {
    public static void main(String[] args) throws Exception {
        Gramatica gramatica = new Gramatica();
        gramatica.citireDinFisier("gramatica.txt");
        gramatica.afisare();

        if (gramatica.verificareRegularitate()) {
            System.out.println("Gramatica este regulată!");
            AutomatFinit af = gramatica.conversieInAF();
            System.out.println("Automatul finit generat:");
            af.afisare();
        } else {
            System.out.println("Gramatica nu este regulată!");
        }
    }
}
