package vista;

import javax.swing.*;
import java.awt.*;
import modelo.AnalizadorLexico;

public class Interfaz extends JFrame {
    private JTextArea codigoFuente;
    private JButton analizarBtn;
    private AnalizadorLexico analizador;

    public Interfaz() {
        setTitle("Analizador Léxico");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        codigoFuente = new JTextArea();
        analizarBtn = new JButton("Analizar Código");

        add(new JScrollPane(codigoFuente), BorderLayout.CENTER);
        add(analizarBtn, BorderLayout.SOUTH);

        analizador = new AnalizadorLexico();

        analizarBtn.addActionListener(e -> {
            String codigo = codigoFuente.getText();
            analizador.analizarCodigo(codigo);
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Interfaz();
    }
}

