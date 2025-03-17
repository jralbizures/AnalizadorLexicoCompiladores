package modelo;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalizadorLexico {
    private ArrayList<Token> tokens;
    private ArrayList<String> errores;

    // Expresiones regulares mejoradas
    private static final String PALABRA_RESERVADA = "\\b(Entero|Real|Cadena|Booleano|if|else|while|for|return)\\b";
    private static final String IDENTIFICADOR = "\\b[a-zA-Z_][a-zA-Z0-9_]*\\b";
    private static final String NUMERO = "\\b-?\\d+(\\.\\d+)?\\b"; // Soporta números negativos
    private static final String OPERADOR = "[+\\-*/=<>!&|^#]+";
    private static final String SIMBOLO = "[{}();,]";
    private static final String CADENA = "\"[^\"]+\"";
    private static final String COMENTARIO = "//.*|/\\*.*?\\*/"; // Maneja ambos tipos de comentarios

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
        while (matcher.find()) {
            String tokenEncontrado = matcher.group();
            Token token = clasificarToken(tokenEncontrado);
            if (token != null) {
                tokens.add(token);
                // Solo agregar identificadores únicos a la tabla de símbolos
                if (token.getTipo().equals("Identificador") && !identificadoresUnicos.contains(token.getValor())) {
                    identificadoresUnicos.add(token.getValor());
                }
            }
        }
    
        // Detectar caracteres inválidos
        detectarErrores(codigo);
    }
    

    private Token clasificarToken(String tokenEncontrado) {
        // Si el token es una palabra reservada
        if (tokenEncontrado.matches(PALABRA_RESERVADA)) 
            return new Token("Palabra Reservada", tokenEncontrado);
    
        // Si el token es un identificador válido
        if (tokenEncontrado.matches(IDENTIFICADOR)) 
            return new Token("Identificador", tokenEncontrado);
    
        // Si el token es un número
        if (tokenEncontrado.matches(NUMERO)) 
            return new Token("Número", tokenEncontrado);
    
        // Si el token es un operador
        if (tokenEncontrado.matches(OPERADOR)) 
            return new Token("Operador", tokenEncontrado);
    
        // Si el token es un símbolo especial
        if (tokenEncontrado.matches(SIMBOLO)) 
            return new Token("Símbolo Especial", tokenEncontrado);
    
        // Si el token es una cadena
        if (tokenEncontrado.matches(CADENA)) 
            return new Token("Cadena", tokenEncontrado);
    
        // Si el token no encaja en ninguna categoría válida, es un error
        errores.add("Error léxico: Token inválido '" + tokenEncontrado + "'");
        return null;
    }
    

    private void detectarErrores(String codigo) {
        // Extraer todas las cadenas de texto y eliminarlas temporalmente del código analizado
        String codigoSinCadenas = codigo.replaceAll("\"[^\"]+\"", "");

        for (char c : codigoSinCadenas.toCharArray()) {
            // Permitir caracteres alfanuméricos, símbolos comunes y espacios
            if (!Character.isWhitespace(c) && !Character.toString(c).matches("[a-zA-Z0-9_{}();,+\\-*/=<>!&|^#]")) {
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
