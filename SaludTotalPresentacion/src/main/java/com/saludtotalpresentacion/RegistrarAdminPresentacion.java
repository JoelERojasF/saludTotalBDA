package com.saludtotalpresentacion;

import DTO.Crear.CrearUsuarioDTO;
import Exception.NegocioException;
import Negocio.Interfaces.IUsuarioNegocio;
import Negocio.UsuarioNegocio;
import com.saludtotalpresentacion.LoginFrame.BotonRedondeado;
import com.saludtotalpresentacion.LoginFrame.CampoPassword;
import com.saludtotalpresentacion.LoginFrame.CampoTexto;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class RegistrarAdminPresentacion extends JFrame {

    private final IUsuarioNegocio usuarioNegocio;

    private final CampoTexto txtNombre = new CampoTexto("Value");
    private final CampoPassword txtPassword = new CampoPassword("Value");
    private final CampoPassword txtConfirmar = new CampoPassword("Value");
    private final BotonRedondeado btnVolver = new BotonRedondeado("Volver");
    private final BotonRedondeado btnRegistrar = new BotonRedondeado("Registrar");

    public RegistrarAdminPresentacion() {
        initComponentes();
        initEventos();

        this.usuarioNegocio = new UsuarioNegocio();
    }

    private void initComponentes() {
        setTitle("Salud Total - Registro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(660, 630);
        setMinimumSize(new Dimension(480, 520));
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(0xF5F5F5));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));
        setContentPane(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        JLabel lblTitulo = new JLabel("Registrar Usuario", SwingConstants.CENTER);
        lblTitulo.setFont(new Font(LoginFrame.FUENTE, Font.PLAIN, 18));
        lblTitulo.setForeground(LoginFrame.COLOR_TEXTO);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 40, 0);
        panel.add(lblTitulo, gbc);

        agregarCampo(panel, gbc, 1, "Nombre:", txtNombre, 25);
        agregarCampo(panel, gbc, 3, "Password", txtPassword, 25);
        agregarCampo(panel, gbc, 5, "Confirmar Password", txtConfirmar, 0);

        JPanel panelBotones = new JPanel(new GridBagLayout());
        panelBotones.setOpaque(false);
        GridBagConstraints b = new GridBagConstraints();
        b.gridy = 0;
        b.weightx = 1;
        b.gridx = 0;
        panelBotones.add(btnVolver, b);
        b.gridx = 1;
        panelBotones.add(btnRegistrar, b);

        gbc.gridy = 7;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 0, 0, 0);
        panel.add(panelBotones, gbc);
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int fila,
            String texto, java.awt.Component campo, int espacioAbajo) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font(LoginFrame.FUENTE, Font.PLAIN, 16));
        lbl.setForeground(LoginFrame.COLOR_TEXTO);
        gbc.gridy = fila;
        gbc.insets = new Insets(0, 0, 8, 0);
        panel.add(lbl, gbc);
        gbc.gridy = fila + 1;
        gbc.insets = new Insets(0, 0, espacioAbajo, 0);
        panel.add(campo, gbc);
    }

    private void initEventos() {
        btnVolver.addActionListener(e -> volver());
        btnRegistrar.addActionListener(e -> registrar());
    }

    private void volver() {
        new LoginFrame().setVisible(true);
        dispose();
    }

    private void registrar() {
        String nombre = txtNombre.getText().trim();
        String pass = new String(txtPassword.getPassword());
        String confirmar = new String(txtConfirmar.getPassword());

        if (nombre.isEmpty() || pass.isEmpty() || confirmar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Llena todos los campos.",
                    "Campos vacios", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!pass.equals(confirmar)) {
            JOptionPane.showMessageDialog(this, "Los passwords no coinciden.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            CrearUsuarioDTO dto = new CrearUsuarioDTO(nombre, pass);
            usuarioNegocio.registrarUsuario(dto);

            JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.");
            volver();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "No se pudo registrar", JOptionPane.ERROR_MESSAGE);
        }
    }
}
