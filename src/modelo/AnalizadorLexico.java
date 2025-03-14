package modelo;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalizadorLexico {
    private ArrayList<Token> tokens = new ArrayList<>();
    private ArrayList<String> errores = new ArrayList<>();

    // Definir las expresiones regulares para los diferentes tokens
    private static final String PALABRA_RESERVADA = "\\b(Entero|Real|Cadena|Booleano|if|else|while|for|return)\\b";
    private static final String IDENTIFICADOR = "\\b[a-zA-Z_][a-zA-Z0-9_]*\\b";
    private static final String NUMERO = "\\b\\d+(\\.\\d+)?\\b";
    private static final String OPERADOR = "[+\\-*/=<>!&|^#]+";
    private static final String SIMBOLO = "[{}();,]";
    private static final String CADENA = "\"[^\"]*\"";
    private static final String COMENTARIO_SIMPLE = "//.*";
    private static final String COMENTARIO_MULTILINEA = "/\\*.*?\\*/";

    public void analizarCodigo(String codigo) {
        tokens.clear();
        errores.clear();

        // Remover comentarios del código antes de analizar
        codigo = codigo.replaceAll(COMENTARIO_MULTILINEA, "").replaceAll(COMENTARIO_SIMPLE, "");

        // Patrón combinado de tokens
        String regex = String.join("|",
            PALABRA_RESERVADA, IDENTIFICADOR, NUMERO, OPERADOR, SIMBOLO, CADENA
        );

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(codigo);

        while (matcher.find()) {
            String tokenEncontrado = matcher.group();
            Token token;

            if (tokenEncontrado.matches(PALABRA_RESERVADA)) {
                token = new Token("Palabra Reservada", tokenEncontrado);
            } else if (tokenEncontrado.matches(IDENTIFICADOR)) {
                token = new Token("Identificador", tokenEncontrado);
            } else if (tokenEncontrado.matches(NUMERO)) {
                token = new Token("Número", tokenEncontrado);
            } else if (tokenEncontrado.matches(OPERADOR)) {
                token = new Token("Operador", tokenEncontrado);
            } else if (tokenEncontrado.matches(SIMBOLO)) {
                token = new Token("Símbolo Especial", tokenEncontrado);
            } else if (tokenEncontrado.matches(CADENA)) {
                token = new Token("Cadena", tokenEncontrado);
            } else {
                errores.add("Error léxico: Token desconocido '" + tokenEncontrado + "'");
                continue;
            }

            tokens.add(token);
        }
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public ArrayList<String> getErrores() {
        return errores;
    }
}