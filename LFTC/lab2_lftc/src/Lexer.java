import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private static final int MAX_IDENTIFIER_LENGTH = 8;
    private SymbolTable symbolTable;
    private List<FIPEntry> FIP;

    public Lexer() {
        this.symbolTable = new SymbolTable();
        this.FIP = new ArrayList<>();
    }

    public void tokenize(String sourceCode) {
        int pos = 0;

        while (pos < sourceCode.length()) {
            char currentChar = sourceCode.charAt(pos);

            if (Character.isWhitespace(currentChar)) {
                pos++;
                continue;
            }

            if (Character.isLetter(currentChar) || currentChar == '_') {
                String word = extractSpecialWords(sourceCode, pos);
                if (word != null && isSpecialWord(word)) { // Dacă este cuvânt special
                    addToFIP(word);  // Adaugă-l în FIP
                    pos += word.length();  // Mergi mai departe după cuvântul special
                } else {  // Dacă nu este cuvânt special, tratează-l ca un identificator
                    String identifier = extractIdentifier(sourceCode, pos);
                    addToSymbolTable(identifier, "IDENTIFICATOR");
                    pos += identifier.length();
                }
            }else if (isSpecialToken(currentChar)) {
                String token = extractSpecialToken(sourceCode, pos);
                if (token != null) {
                    addToFIP(token);
                    pos += token.length();
                } else {
                    System.err.println("Eroare lexicală: Token necunoscut la poziția " + pos);
                    pos++;
                }
            }
            // Identificatori și constante
            else if (Character.isLetter(currentChar) || currentChar == '_') {
                String identifier = extractIdentifier(sourceCode, pos);
                if(getTokenCode(identifier) != -1)
                    addToFIP(identifier);
                else{
                    addToSymbolTable(identifier,"IDENTIFICATOR");
                }
                pos += identifier.length();
            }
            else if (Character.isDigit(currentChar)) {
                String constant = extractConstant(sourceCode, pos);
                addToSymbolTable(constant, "CONSTANT");
                pos += constant.length();
            }
            else{
                System.err.println("Eroare lexicala la pozitia " + pos);
                pos++;
            }
        }
    }

    private boolean isSpecialWord(String word){
        String[] specialWords = {"array", "int", "char","string","input","prinTudor","while","do","if","else","for",
                "in","break"};

        for(String token : specialWords)
            if(word == token)
                return true;
        return false;
    }

    private String extractSpecialWords(String sourceCode, int pos){
        String[] specialWords = {"array", "int", "char","string","input","prinTudor","while","do","if","else","for",
                "in","break"};

        for (String token : specialWords) {
            if (sourceCode.startsWith(token, pos)) {
                return token;
            }
        }
        return null;
    }

    private boolean isSpecialToken(char currentChar) {
        char[] specialTokens = {
                '+', '-', '*', '/', '%', '[', ']', '{', '}', '(', ')', ':', ';', ',', '.', ' ', '=', '<', '>', '$', '!'
        };

        for(char token : specialTokens)
            if(currentChar == token)
                return true;
        return false;
    }


    private void addToFIP(String token) {

        int code = getTokenCode(token);
        if (code == -1) {
            System.err.println("Eroare lexicală: Token necunoscut '" + token + "'");
            return;
        }
        FIP.add(new FIPEntry(token, code));
    }

    private void addToSymbolTable(String value, String type) {
        if (value.length() > MAX_IDENTIFIER_LENGTH) {
            value = value.substring(0, MAX_IDENTIFIER_LENGTH);
        }

        if (symbolTable.get(value) == null) {
            symbolTable.add(value, type);
        }
        addToFIP(value);
    }


    private String extractSpecialToken(String sourceCode, int pos) {
        String[] specialTokens = {
                "+", "-", "*", "/", "%", "<<", ">>", "<=", ">=", "==", "!=", "=", "$$$",
                "[", "]", "{", "}", "(", ")", ":", ";", ",", ".", " "
        };


        for (String token : specialTokens) {
            if (sourceCode.startsWith(token, pos)) {
                return token;
            }
        }
        return null;
    }


    private String extractIdentifier(String sourceCode, int pos) {
        StringBuilder identifier = new StringBuilder();
        char currentChar = sourceCode.charAt(pos);

        if (!Character.isLetter(currentChar) && currentChar != '_') {
            return null;
        }

        while (pos < sourceCode.length() &&
                (Character.isLetterOrDigit(sourceCode.charAt(pos)) || sourceCode.charAt(pos) == '_')) {
            identifier.append(sourceCode.charAt(pos));
            pos++;
        }

        if (identifier.length() > 8) {
            return identifier.substring(0, 8);
        }

        return identifier.toString();
    }


    private String extractConstant(String sourceCode, int pos) {
        StringBuilder constant = new StringBuilder();
        char currentChar = sourceCode.charAt(pos);

        // Verificăm dacă este un întreg
        if (Character.isDigit(currentChar) || currentChar == '+' || currentChar == '-') {
            while (pos < sourceCode.length() && Character.isDigit(sourceCode.charAt(pos))) {
                constant.append(sourceCode.charAt(pos));
                pos++;
            }
            return constant.toString();
        }


        if (currentChar == '"') {
            constant.append(currentChar); // Adaugăm ghilimelele de început
            pos++;
            while (pos < sourceCode.length() && sourceCode.charAt(pos) != '"') {
                constant.append(sourceCode.charAt(pos));
                pos++;
            }
            if (pos < sourceCode.length() && sourceCode.charAt(pos) == '"') {
                constant.append('"'); // Adaugăm ghilimelele de final
                pos++;
                return constant.toString();
            }
        }

        return null;
    }


    private int getTokenCode(String token) {
        return switch (token) {
            case "identificator" -> 0;
            case "constanta" -> 1;
            case "program" -> 2;
            case "array" -> 3;
            case "int" -> 4;
            case "char" -> 5;
            case "string" -> 6;
            case "input" -> 7;
            case "prinTudor" -> 8;
            case "while" -> 9;
            case "do" -> 10;
            case "if" -> 11;
            case "else" -> 12;
            case "for" -> 13;
            case "in" -> 14;
            case "break" -> 15;
            case "+" -> 16;
            case "-" -> 17;
            case "*" -> 18;
            case "/" -> 19;
            case "%" -> 20;
            case "<<" -> 21;
            case ">>" -> 22;
            case "<=" -> 23;
            case ">=" -> 24;
            case "!=" -> 25;
            case "==" -> 26;
            case "=" -> 27;
            case "$$$" -> 28;
            case "[" -> 29;
            case "]" -> 30;
            case "{" -> 31;
            case "}" -> 32;
            case "(" -> 33;
            case ")" -> 34;
            case ":" -> 35;
            case ";" -> 36;
            case "," -> 37;
            case "." -> 38;
            case " " -> 39;
            default -> -1; // Token necunoscut
        };
    }
    public void printTSsiFIP() {
        System.out.println("Tabela de Simboluri (TS):");
        symbolTable.print();

        System.out.println("\nForma Internă a Programului (FIP):");
        for (FIPEntry entry : FIP) {
            System.out.println(entry);
        }
    }
}
