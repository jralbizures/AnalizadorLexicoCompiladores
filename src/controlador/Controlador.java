package controlador;

import vista.InterfazGrafica;
import modelo.AnalizadorLexico;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador {
    private InterfazGrafica vista;
    private AnalizadorLexico analizador;

    public Controlador() {
        vista = new InterfazGrafica();
        analizador = new AnalizadorLexico();

        vista.getAnalizarBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = vista.getAreaCodigo().getText();
                analizador.analizar(codigo);
            }
        });
    }
}