/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.saludtotalpresentacion;

import javax.swing.SwingUtilities;

/**
 *
 * @author le0jx
 */
public class SaludTotalPresentacion {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
       //SwingUtilities.invokeLater(() -> new EstudiosFrame(null).setVisible(true));

    }
}
