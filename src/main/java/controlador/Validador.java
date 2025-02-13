/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javax.swing.JOptionPane;

/**
 *
 * @author ezequ
 */
public class Validador {

    public Validador() {

    }

    public boolean validaCajaTextoCadena(javax.swing.JTextField cajaTexto) {
        if (cajaTexto.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "You must fill in all fields");
            return false;
        }
        return true;
    }

    public boolean validaCajaTextoEntero(javax.swing.JTextField cajaTexto) {
        if (cajaTexto.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "You must fill in all fields");
            return false;
        } else {
            try {
                int a = Integer.parseInt(cajaTexto.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Value not allowed, must be a number");
                System.err.println("Error de conversión");
                return false;
            }
            return true;
        }
    }

    /* La siguiente función no fue hecha en clases, sino como tarea (validar
    la edad)*/
    public boolean validaCajaTextoEnteroEdad(javax.swing.JTextField cajaTexto) {
        int a = 0;
        if (cajaTexto.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "You must fill in all fields");
            return false;
        } else {
            try {
                a = Integer.parseInt(cajaTexto.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Value not allowed, must be a number");
                System.err.println("Error de conversión");
                return false;
            }
            if (a > 0 && a < 100) {
                //bien
            } else {
                JOptionPane.showMessageDialog(null, "Age not allowed");
                return false;
            }
            return true;
        }
    }

    public boolean validaCadena(String cadena) {
        if (cadena.isBlank()) {
            JOptionPane.showMessageDialog(null, "Campo requerido de la cadena");
            return false;
        }
        return true;
    }

}