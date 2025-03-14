package vista;

import javax.swing.*;
import java.awt.*;

public class InterfazGrafica extends JFrame {
    private JTextArea areaCodigo;
    private JButton analizarBtn;

    public InterfazGrafica() {
        setTitle("Compilador - Analizador Léxico");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        areaCodigo = new JTextArea();
        add(new JScrollPane(areaCodigo), BorderLayout.CENTER);

        analizarBtn = new JButton("Analizar Código");
        add(analizarBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    public JTextArea getAreaCodigo() {
        return areaCodigo;
    }

    public JButton getAnalizarBtn() {
        return analizarBtn;
    }
}
