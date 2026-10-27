package com.saludtotalpresentacion;

import DTO.UsuarioDTO;
import com.saludtotalpresentacion.LoginFrame.BotonRedondeado;
import static com.saludtotalpresentacion.LoginFrame.COLOR_BORDE;
import static com.saludtotalpresentacion.LoginFrame.COLOR_FONDO;
import static com.saludtotalpresentacion.LoginFrame.COLOR_PLACEHOLDER;
import static com.saludtotalpresentacion.LoginFrame.COLOR_TEXTO;
import com.saludtotalpresentacion.LoginFrame.CampoTexto;
import static com.saludtotalpresentacion.LoginFrame.FUENTE;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


public class AgregarEstudioFrame extends JFrame{
    
    private final Font FUENTE_TITULO = new Font(FUENTE, Font.BOLD, 22);
    private final Font FUENTE_NORMAL = new Font(FUENTE, Font.PLAIN, 14);
 
    private final UsuarioDTO usuarioActual;
 
    protected final CampoTexto txtNombre = new CampoTexto("ej.Biometria hematica");
    protected final CampoTexto txtMuestra = new CampoTexto("ej.Sangre o orina");
    protected JTextArea txtNota;
    protected final JLabel lblTitulo = new JLabel("Nuevo estudio");
    protected String mensajeExito = "Estudio guardado correctamente.";
 
    protected final DefaultTableModel modeloParametros = new DefaultTableModel(
            new Object[]{"Orden", "Nombre", "Unidad", "Rango", "Edad", "Descripcion"}, 0) {
        @Override
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };
 
    public AgregarEstudioFrame(UsuarioDTO usuario) {
        this.usuarioActual = usuario;
 
        setTitle("Salud Total - Nuevo Estudio");
        setSize(900, 720);
        setMinimumSize(new Dimension(480, 520));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
 
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(COLOR_FONDO);
        panelPrincipal.setBorder(new EmptyBorder(20, 30, 20, 30));
 
        panelPrincipal.add(crearBarraNavegacion());
        panelPrincipal.add(Box.createVerticalStrut(15));
 
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(Box.createVerticalStrut(20));
 
        panelPrincipal.add(crearFilaNombreYMuestra());
        panelPrincipal.add(Box.createVerticalStrut(20));
 
        panelPrincipal.add(crearSeccionNota());
        panelPrincipal.add(Box.createVerticalStrut(25));
 
        JButton btnAgregarParametro = crearBoton("Agregar parametro");
        btnAgregarParametro.addActionListener(e -> agregarParametro());
        panelPrincipal.add(crearFilaDerecha(btnAgregarParametro));
        panelPrincipal.add(Box.createVerticalStrut(10));
 
        panelPrincipal.add(crearTablaParametros());
        panelPrincipal.add(Box.createVerticalGlue());
 
        BotonClaro btnCancelar = new BotonClaro("Cancelar");
        JButton btnGuardar = crearBoton("Guardar Estudio");
        btnCancelar.addActionListener(e -> volver());
        btnGuardar.addActionListener(e -> guardar());
 
        JPanel panelBotones = crearFilaDerecha(btnCancelar);
        panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panelBotones.add(btnGuardar);
        panelPrincipal.add(panelBotones);
 
        setContentPane(panelPrincipal);
    }
 
    // ------------------------------------------------------------------
    // Construccion de la UI
    // ------------------------------------------------------------------
    private JPanel crearBarraNavegacion() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 0));
        panel.setBackground(COLOR_FONDO);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
 
        JButton btnEstudio = crearBoton("Estudio");
        JButton btnSolicitudes = crearBoton("Solicitudes");
        JButton btnResultado = crearBoton("Resultado");
        JButton btnReportes = crearBoton("Reportes");
 
        btnEstudio.addActionListener(e -> volver());
        btnSolicitudes.addActionListener(e -> {
            new SolicitudesFrame(usuarioActual).setVisible(true);
            dispose();
        });
        btnResultado.addActionListener(e -> {
            new ResultadosFrame(usuarioActual).setVisible(true);
            dispose();
        });
        // TODO: btnReportes cuando exista ese frame
 
        panel.add(btnEstudio);
        panel.add(btnSolicitudes);
        panel.add(btnResultado);
        panel.add(btnReportes);
        return panel;
    }
 
    private JPanel crearFilaNombreYMuestra() {
        JPanel fila = new JPanel(new GridLayout(1, 2, 30, 0));
        fila.setBackground(COLOR_FONDO);
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
 
        fila.add(crearCampo("Nombre del etudio", txtNombre));
        fila.add(crearCampo("Tipo de muestra", txtMuestra));
        return fila;
    }
 
    private JPanel crearCampo(String texto, Component campo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_FONDO);
 
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FUENTE_NORMAL);
        lbl.setForeground(COLOR_TEXTO);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
 
        ((javax.swing.JComponent) campo).setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
 
        panel.add(lbl);
        panel.add(Box.createVerticalStrut(8));
        panel.add(campo);
        return panel;
    }
 
    private JPanel crearSeccionNota() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_FONDO);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
 
        JLabel lbl = new JLabel("Nota descriptiva:");
        lbl.setFont(FUENTE_NORMAL);
        lbl.setForeground(COLOR_TEXTO);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
 
        // JTextArea con placeholder "Nota"
        txtNota = new JTextArea(4, 20) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g2.setColor(COLOR_PLACEHOLDER);
                    g2.setFont(getFont());
                    g2.drawString("Nota", getInsets().left,
                            getInsets().top + g2.getFontMetrics().getAscent());
                    g2.dispose();
                }
            }
        };
        txtNota.setFont(new Font(FUENTE, Font.PLAIN, 16));
        txtNota.setForeground(COLOR_TEXTO);
        txtNota.setLineWrap(true);
        txtNota.setWrapStyleWord(true);
        txtNota.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        txtNota.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtNota.repaint();
            }
 
            @Override
            public void focusLost(FocusEvent e) {
                txtNota.repaint();
            }
        });
 
        JScrollPane scroll = new JScrollPane(txtNota);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_BORDE));
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
 
        panel.add(lbl);
        panel.add(Box.createVerticalStrut(8));
        panel.add(scroll);
        return panel;
    }
 
    private JScrollPane crearTablaParametros() {
        JTable tabla = new JTable(modeloParametros);
        tabla.setRowHeight(28);
        tabla.setShowGrid(false);
        tabla.setBackground(COLOR_FONDO);
        tabla.setFont(FUENTE_NORMAL);
        tabla.setForeground(COLOR_TEXTO);
 
        int[] anchos = {60, 150, 80, 100, 80, 250};
        for (int i = 0; i < anchos.length; i++) {
            tabla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
 
        tabla.getTableHeader().setFont(FUENTE_NORMAL);
        tabla.getTableHeader().setForeground(Color.GRAY);
        tabla.getTableHeader().setBackground(COLOR_FONDO);
        tabla.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        tabla.getTableHeader().setReorderingAllowed(false);
 
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        scroll.setPreferredSize(new Dimension(830, 150));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        scroll.getViewport().setBackground(COLOR_FONDO);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        return scroll;
    }
 
    private JPanel crearFilaDerecha(Component boton) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panel.setBackground(COLOR_FONDO);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        panel.add(boton);
        return panel;
    }
 
    private JButton crearBoton(String texto) {
        BotonRedondeado boton = new BotonRedondeado(texto);
        boton.setFont(FUENTE_NORMAL);
        boton.setPreferredSize(new Dimension(150, 40));
        return boton;
    }
 
    // ------------------------------------------------------------------
    // Acciones
    // ------------------------------------------------------------------
    private void agregarParametro() {
        new AgregarParametrosFrame(this, modeloParametros.getRowCount() + 1, p -> {
            // una fila por cada rango registrado
            for (String[] r : p.getRangos()) {
                modeloParametros.addRow(new Object[]{
                    p.getOrden(),
                    p.getNombre(),
                    r[2],               // unidad
                    r[0] + "-" + r[1],  // rango
                    r[3] + "-" + r[4],  // edad
                    p.getDescripcion()
                });
            }
        }).setVisible(true);
    }
 
    private void guardar() {
        if (txtNombre.getText().trim().isEmpty() || txtMuestra.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Llena el nombre y el tipo de muestra.",
                    "Campos vacios", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (modeloParametros.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Agrega al menos un parametro.",
                    "Sin parametros", JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        // TODO: construir el DTO (nombre, muestra, nota, parametros) y llamar a la capa de negocio
        JOptionPane.showMessageDialog(this, mensajeExito);
        volver();
    }
 
    private void volver() {
        new EstudiosFrame(usuarioActual).setVisible(true);
        dispose();
    }
 
    // ------------------------------------------------------------------
    // Boton claro redondeado (Cancelar)
    // ------------------------------------------------------------------
    private static class BotonClaro extends JButton {
 
        BotonClaro(String texto) {
            super(texto);
            setFont(new Font(FUENTE, Font.PLAIN, 14));
            setForeground(COLOR_TEXTO);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(150, 40));
        }
 
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
            g2.setColor(COLOR_TEXTO);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
            g2.dispose();
            super.paintComponent(g);
        }
    }
    
}
