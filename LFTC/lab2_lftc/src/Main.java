public class Main {
    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        String sourceCode = "{int num1 = 10; int num2 = 20; if (num1 >> num2): maximum = num1; else: maximum = num2; prinTudor(maximum);}";
        lexer.tokenize(sourceCode);
        lexer.printTSsiFIP();
    }
}
