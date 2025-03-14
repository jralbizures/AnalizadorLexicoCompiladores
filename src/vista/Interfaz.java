package vista;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import modelo.AnalizadorLexico;
import modelo.Token;

public class Interfaz extends JFrame {
    private JTextArea codigoFuente;
    private JTextArea salidaTokens;
    private JButton analizarBtn;
    private AnalizadorLexico analizador;

    public Interfaz() {
        setTitle("Analizador Léxico");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior: Código fuente
        codigoFuente = new JTextArea(10, 50);
        add(new JScrollPane(codigoFuente), BorderLayout.NORTH);

        // Panel central: Salida de tokens
        salidaTokens = new JTextArea(10, 50);
        salidaTokens.setEditable(false);
        add(new JScrollPane(salidaTokens), BorderLayout.CENTER);

        // Botón de análisis
        analizarBtn = new JButton("Analizar Código");
        add(analizarBtn, BorderLayout.SOUTH);

        // Inicializar analizador
        analizador = new AnalizadorLexico();

        analizarBtn.addActionListener(e -> analizarCodigo());

        setVisible(true);
    }

    private void analizarCodigo() {
        String codigo = codigoFuente.getText();
        analizador.analizarCodigo(codigo);
        
        // Mostrar tokens y errores
        StringBuilder salida = new StringBuilder("Tokens encontrados:\n");
        for (Token token : analizador.getTokens()) {
            salida.append(token).append("\n");
        }

        if (!analizador.getErrores().isEmpty()) {
            salida.append("\nErrores léxicos:\n");
            for (String error : analizador.getErrores()) {
                salida.append(error).append("\n");
            }
        }

        salidaTokens.setText(salida.toString());
    }

    public static void main(String[] args) {
        new Interfaz();
    }
}

