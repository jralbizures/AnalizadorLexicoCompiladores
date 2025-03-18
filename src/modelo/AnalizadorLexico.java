package modelo;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalizadorLexico {
    private ArrayList<Token> tokens;
    private ArrayList<String> errores;

    // Expresiones regulares mejoradas
    private static final String PALABRA_RESERVADA = "\\b(Entero|Real|Cadena|Booleano|Si|Sino|Mientras|Para|EscribirLinea|Longitud|aCadena)\\b";
    private static final String IDENTIFICADOR = "\\b(?!Entero|Real|Cadena|Booleano|Si|Sino|Mientras|Para|EscribirLinea|Longitud|aCadena)[a-zA-Z_][a-zA-Z0-9_]*\\b";
    private static final String NUMERO = "-?\\d+(\\.\\d+)?"; // Soporta números enteros y decimales negativos
    private static final String OPERADOR = "[+\\-*/=<>!&|]+";
    private static final String SIMBOLO = "[{}();,]";
    private static final String CADENA = "\"[^\"]*\""; // Cadenas entre comillas dobles
    private static final String COMENTARIO = "//.*|/\\*.*?\\*/"; 

    private static final Pattern TOKEN_PATTERN = Pattern.compile(
            String.join("|", PALABRA_RESERVADA, IDENTIFICADOR, NUMERO, OPERADOR, SIMBOLO, CADENA));

    public AnalizadorLexico() {
        tokens = new ArrayList<>();
        errores = new ArrayList<>();
    }

    public void analizarCodigo(String codigo) {
        tokens.clear();
        errores.clear();
        ArrayList<String> identificadoresUnicos = new ArrayList<>();

        // Remover comentarios antes de analizar
        codigo = codigo.replaceAll(COMENTARIO, "");

        Matcher matcher = TOKEN_PATTERN.matcher(codigo);
        int lastIndex = 0;
        while (matcher.find()) {
            // Detectar caracteres no reconocidos entre los tokens
            if (matcher.start() > lastIndex) {
                detectarErrores(codigo.substring(lastIndex, matcher.start()));
            }

            String tokenEncontrado = matcher.group();
            Token token = clasificarToken(tokenEncontrado);
            if (token != null) {
                tokens.add(token);
                if (token.getTipo().equals("Identificador") && !identificadoresUnicos.contains(token.getValor())) {
                    identificadoresUnicos.add(token.getValor());
                }
            }
            lastIndex = matcher.end();
        }

        // Revisar si quedaron caracteres no reconocidos al final
        if (lastIndex < codigo.length()) {
            detectarErrores(codigo.substring(lastIndex));
        }
    }

    private Token clasificarToken(String tokenEncontrado) {
        if (tokenEncontrado.matches(PALABRA_RESERVADA)) 
            return new Token("Palabra Reservada", tokenEncontrado);
        if (tokenEncontrado.matches(IDENTIFICADOR)) 
            return new Token("Identificador", tokenEncontrado);
        if (tokenEncontrado.matches(NUMERO)) 
            return new Token("Número", tokenEncontrado);
        if (tokenEncontrado.matches(OPERADOR)) 
            return new Token("Operador", tokenEncontrado);
        if (tokenEncontrado.matches(SIMBOLO)) 
            return new Token("Símbolo Especial", tokenEncontrado);
        if (tokenEncontrado.matches(CADENA)) 
            return new Token("Cadena", tokenEncontrado);

        errores.add("Error léxico: Token inválido '" + tokenEncontrado + "'");
        return null;
    }

    private void detectarErrores(String fragmento) {
        for (char c : fragmento.toCharArray()) {
            if (!Character.isWhitespace(c) && !Character.toString(c).matches("[a-zA-Z0-9_{}();,+\\-*/=<>!&|^#.\"]")) {
                errores.add("Error léxico: Carácter inválido '" + c + "'");
            }
        }
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public ArrayList<String> getErrores() {
        return errores;
    }
}


