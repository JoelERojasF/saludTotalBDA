/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.saludtotalpresentacion;

import DTO.UsuarioDTO;
import static com.saludtotalpresentacion.LoginFrame.COLOR_FONDO;
import static com.saludtotalpresentacion.LoginFrame.COLOR_TEXTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pirown
 */
public class SolicitudesFrame extends JFrame {

    private UsuarioDTO usuarioActual;

    private final Color COLOR_BOTON = new Color(51, 51, 51);
    private final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 22);

    public SolicitudesFrame(UsuarioDTO usuario) {
        this.usuarioActual = usuario;

        setTitle("Salud Total - Solicitudes");
        setSize(900, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(480, 520));

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(COLOR_FONDO);
        panelPrincipal.setBorder(new EmptyBorder(20, 30, 20, 30));

        panelPrincipal.add(crearBarraNavegacion());
        panelPrincipal.add(Box.createVerticalStrut(15));

        panelPrincipal.add(crearSeccionBuscador());
        panelPrincipal.add(Box.createVerticalStrut(15));

        panelPrincipal.add(crearTablaSolicitudes());
        panelPrincipal.add(Box.createVerticalStrut(10));

        JPanel pnlBtnRegistrar = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlBtnRegistrar.setBackground(COLOR_FONDO);

        ////////////////////////////////////////////
        JButton btnRegistrar = crearBotonOscuro("Registrar solicitud");

        pnlBtnRegistrar.add(btnRegistrar);

        panelPrincipal.add(pnlBtnRegistrar);
        panelPrincipal.add(Box.createVerticalStrut(15));

        panelPrincipal.add(crearSeccionDetalles());
        panelPrincipal.add(Box.createVerticalStrut(20));

        panelPrincipal.add(crearBotonesInferiores());

        setContentPane(panelPrincipal);
    }

    private JPanel crearBarraNavegacion() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 0));
        panel.setBackground(COLOR_FONDO);

        panel.add(crearBotonOscuro("Estudios"));
        panel.add(crearBotonOscuro("Solicitudes"));
        panel.add(crearBotonOscuro("Resultado"));
        panel.add(crearBotonOscuro("Reportes"));

        return panel;
    }

    private JPanel crearSeccionBuscador() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_FONDO);

        JLabel lblTitulo = new JLabel("Solicitudes");
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel pnlBusqueda = new JPanel(new BorderLayout(15, 0));
        pnlBusqueda.setBackground(COLOR_FONDO);
        pnlBusqueda.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlBusqueda.setMaximumSize(new Dimension(850, 40));

        JTextField txtBuscar = new JTextField();
        txtBuscar.setForeground(Color.GRAY);
        txtBuscar.setPreferredSize(new Dimension(450, 35));

        JButton btnBuscarFolio = new JButton("Buscar folio");
        btnBuscarFolio.setPreferredSize(new Dimension(130, 35));

        pnlBusqueda.add(txtBuscar, BorderLayout.CENTER);
        pnlBusqueda.add(btnBuscarFolio, BorderLayout.EAST);

        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(8));
        panel.add(pnlBusqueda);

        return panel;
    }

    private JPanel crearTablaSolicitudes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_FONDO);

        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Sel", "Datos de Solicitud"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class;
                } else {
                    return String.class;
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.setShowGrid(false);
        tabla.getColumnModel().getColumn(0).setMaxWidth(40);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(830, 180));
        scroll.getViewport().setBackground(Color.WHITE);

        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearSeccionDetalles() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_FONDO);

        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY),
                new EmptyBorder(10, 0, 0, 0)
        ));

        String[] columnas = {"Paciente", "Doctor", "Solicitud", "Resultado"};

        DefaultTableModel modeloDetalles = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tablaDetalles = new JTable(modeloDetalles);
        tablaDetalles.setRowHeight(24);
        tablaDetalles.setShowGrid(false);
        tablaDetalles.setBackground(COLOR_FONDO);
        tablaDetalles.setOpaque(true);

        tablaDetalles.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaDetalles.getTableHeader().setForeground(Color.GRAY);
        tablaDetalles.getTableHeader().setBackground(COLOR_FONDO);
        tablaDetalles.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        JScrollPane scrollDetalles = new JScrollPane(tablaDetalles);
        scrollDetalles.setPreferredSize(new Dimension(830, 130));
        scrollDetalles.getViewport().setBackground(COLOR_FONDO);
        scrollDetalles.setBorder(BorderFactory.createEmptyBorder());

        panel.add(scrollDetalles, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearBotonesInferiores() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panel.setBackground(COLOR_FONDO);

        JButton btnCancelar = new JButton("Cancelar Solicitud");
        btnCancelar.setPreferredSize(new Dimension(160, 40));

        JButton btnEditar = crearBotonOscuro("Editar solicitud");
        btnEditar.setPreferredSize(new Dimension(160, 40));

        panel.add(btnCancelar);
        panel.add(btnEditar);

        return panel;
    }

    private JButton crearBotonOscuro(String texto) {
        JButton boton = new JButton(texto);
        boton.setForeground(Color.WHITE);
        boton.setBackground(COLOR_BOTON);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(140, 40));
        return boton;
    }

}
