package vista;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import modelo.AnalizadorLexico;
import modelo.Token;

public class Interfaz extends JFrame {
    private JTextArea codigoFuente;
    private JTextArea tokensArea, tablaSimbolosArea, erroresArea;
    private JButton analizarBtn;
    private AnalizadorLexico analizador;

    public Interfaz() {
        setTitle("Analizador Léxico");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior: Código fuente
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Código Fuente"));
        codigoFuente = new JTextArea(10, 50);
        panelSuperior.add(new JScrollPane(codigoFuente), BorderLayout.CENTER);
        add(panelSuperior, BorderLayout.NORTH);

        // Crear pestañas para mostrar los reportes
        JTabbedPane tabbedPane = new JTabbedPane();

        tokensArea = new JTextArea();
        tokensArea.setEditable(false);
        tabbedPane.addTab("Tokens", new JScrollPane(tokensArea));

        tablaSimbolosArea = new JTextArea();
        tablaSimbolosArea.setEditable(false);
        tabbedPane.addTab("Tabla de Símbolos", new JScrollPane(tablaSimbolosArea));

        erroresArea = new JTextArea();
        erroresArea.setEditable(false);
        tabbedPane.addTab("Errores Léxicos", new JScrollPane(erroresArea));

        add(tabbedPane, BorderLayout.CENTER);

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

        // Mostrar tokens en la pestaña "Tokens"
        StringBuilder tokensTexto = new StringBuilder("Lista de Tokens:\n");
        for (Token token : analizador.getTokens()) {
            tokensTexto.append(token).append("\n");
        }
        tokensArea.setText(tokensTexto.toString());

        // Mostrar tabla de símbolos en la pestaña "Tabla de Símbolos"
        StringBuilder tablaTexto = new StringBuilder("Tabla de Símbolos:\n");
        tablaTexto.append("Identificador | Tipo de Token\n");
        for (Token token : analizador.getTokens()) {
            if (token.getTipo().equals("Identificador")) {
                tablaTexto.append(token.getValor()).append(" | ").append(token.getTipo()).append("\n");
            }
        }
        tablaSimbolosArea.setText(tablaTexto.toString());

        // Mostrar errores en la pestaña "Errores Léxicos"
        StringBuilder erroresTexto = new StringBuilder("Errores Léxicos:\n");
        for (String error : analizador.getErrores()) {
            erroresTexto.append(error).append("\n");
        }
        erroresArea.setText(erroresTexto.toString());

        if (analizador.getErrores().isEmpty()) {
            erroresArea.setText("No se encontraron errores léxicos.");
        }
    }

    public static void main(String[] args) {
        new Interfaz();
    }
}
