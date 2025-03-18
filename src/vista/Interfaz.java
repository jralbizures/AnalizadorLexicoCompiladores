package vista;

import javax.swing.*;
import java.awt.*;
//import java.util.ArrayList;
import modelo.AnalizadorLexico;
import modelo.Token;

public class Interfaz extends JFrame {
    private JTextArea codigoFuente;
    private JTextArea tokensArea, tablaSimbolosArea, erroresArea;
    private JButton analizarBtn;
    private AnalizadorLexico analizador;
    private JTabbedPane tabbedPane;

    public Interfaz() {
        setTitle("Analizador Léxico");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(171, 44, 29));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Agrega márgenes generales
        add(mainPanel, BorderLayout.CENTER);
        
        // Panel de código fuente
        JPanel panelCodigo = new JPanel(new BorderLayout());
        panelCodigo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(null, "Código Fuente", 
                        2, 2, new Font("Serif", Font.BOLD, 20), Color.WHITE),
                BorderFactory.createEmptyBorder(10, 10, 10, 10))); // Margen interno
        panelCodigo.setBackground(new Color(171, 44, 29));
        
        codigoFuente = new JTextArea(10, 50);
        codigoFuente.setFont(new Font("Serif", Font.PLAIN, 14));
        codigoFuente.setBackground(new Color(236, 240, 241));
        JScrollPane scrollCodigo = new JScrollPane(codigoFuente);
        scrollCodigo.setPreferredSize(new Dimension(780, 180)); // Ajusta tamaño
        panelCodigo.add(scrollCodigo, BorderLayout.CENTER);
        mainPanel.add(panelCodigo, BorderLayout.NORTH);
        
        // Panel para el botón y las pestañas alineadas
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(new Color(171, 44, 29));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Margen superior e inferior
        mainPanel.add(panelCentral, BorderLayout.CENTER);
        
        // Panel del botón de análisis
        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(171, 44, 29));
        analizarBtn = new JButton("Analizar Código");
        analizarBtn.setFont(new Font("Serif", Font.BOLD, 16));
        analizarBtn.setForeground(Color.WHITE);
        analizarBtn.setBackground(new Color(39, 174, 96));
        analizarBtn.setFocusPainted(false);
        analizarBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelBoton.add(analizarBtn);
        panelCentral.add(panelBoton, BorderLayout.NORTH);
        
        // Panel de pestañas alineado con el panel de código
        JPanel panelResultados = new JPanel(new BorderLayout());
        panelResultados.setBackground(new Color(171, 44, 29));
        panelResultados.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen interno
        panelCentral.add(panelResultados, BorderLayout.CENTER);
        
        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        tabbedPane.setFont(new Font("Serif", Font.BOLD, 14));
        tabbedPane.setBackground(new Color(200, 107, 80));
        tabbedPane.setPreferredSize(new Dimension(220, 400)); // Ajustar tamaño
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Agrega margen a pestañas
        
        tokensArea = new JTextArea();
        tokensArea.setEditable(false);
        tokensArea.setFont(new Font("Serif", Font.PLAIN, 14));
        tokensArea.setBackground(new Color(236, 240, 241));
        JScrollPane scrollTokens = new JScrollPane(tokensArea);
        tabbedPane.addTab("Tokens", scrollTokens);
        
        tablaSimbolosArea = new JTextArea();
        tablaSimbolosArea.setEditable(false);
        tablaSimbolosArea.setFont(new Font("Serif", Font.PLAIN, 14));
        tablaSimbolosArea.setBackground(new Color(236, 240, 241));
        JScrollPane scrollTabla = new JScrollPane(tablaSimbolosArea);
        tabbedPane.addTab("Tabla de Símbolos", scrollTabla);
        
        erroresArea = new JTextArea();
        erroresArea.setEditable(false);
        erroresArea.setFont(new Font("Serif", Font.PLAIN, 14));
        erroresArea.setForeground(Color.RED);
        erroresArea.setBackground(new Color(236, 240, 241));
        JScrollPane scrollErrores = new JScrollPane(erroresArea);
        tabbedPane.addTab("Errores Léxicos", scrollErrores);
        
        panelResultados.add(tabbedPane, BorderLayout.CENTER);
        
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
        
        // Asegurar que se muestre la pestaña de Tokens por defecto
        tabbedPane.setSelectedIndex(0);

        // Mostrar tabla de símbolos en la pestaña "Tabla de Símbolos"
        StringBuilder tablaTexto = new StringBuilder("Tabla de Símbolos:\n");
        tablaTexto.append("Identificador | Tipo de Token\n");
        for (Token token : analizador.getTokens()) {
            if ("Identificador".equals(token.getTipo())) {
                tablaTexto.append(token.getValor()).append(" | ").append(token.getTipo()).append("\n");
            }
        }
        tablaSimbolosArea.setText(tablaTexto.toString());

        // Mostrar errores en la pestaña "Errores Léxicos"
        StringBuilder erroresTexto = new StringBuilder("Errores Léxicos:\n");
        for (String error : analizador.getErrores()) {
            erroresTexto.append(error).append("\n");
        }
        erroresArea.setText(erroresTexto.length() > 16 ? erroresTexto.toString() : "No se encontraron errores léxicos.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Interfaz::new);
    }
}