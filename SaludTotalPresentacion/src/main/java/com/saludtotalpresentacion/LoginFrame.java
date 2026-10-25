package com.saludtotalpresentacion;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;


public class LoginFrame extends JFrame{
    
    static final Color COLOR_FONDO = Color.WHITE;
    static final Color COLOR_TEXTO = new Color(0x1E1E1E);
    static final Color COLOR_BORDE = new Color(0xD9D9D9);
    static final Color COLOR_PLACEHOLDER = new Color(0xB3B3B3);
    static final Color COLOR_BOTON = new Color(0x2C2C2C);
    static final Color COLOR_BOTON_HOVER = new Color(0x444444);
 
    static final String FUENTE = "Segoe UI";
 
    private final CampoTexto txtNombre = new CampoTexto("Value");
    private final CampoPassword txtPassword = new CampoPassword("Value");
    private final BotonRedondeado btnRegistrar = new BotonRedondeado("Registrar");
    private final BotonRedondeado btnIniciarSesion = new BotonRedondeado("Iniciar Sesion");
 
    public LoginFrame() {
        initComponentes();
        initEventos();
    }
 
    private void initComponentes() {
        setTitle("Salud Total - Iniciar Sesion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(660, 630);
        setMinimumSize(new Dimension(480, 520));
        setLocationRelativeTo(null);
 
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));
        setContentPane(panel);
 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
 
        // Título
        JLabel lblTitulo = new JLabel("Iniciar Sesion", SwingConstants.CENTER);
        lblTitulo.setFont(new Font(FUENTE, Font.PLAIN, 18));
        lblTitulo.setForeground(COLOR_TEXTO);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 50, 0);
        panel.add(lblTitulo, gbc);
 
        // Nombre
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 8, 0);
        panel.add(crearEtiqueta("Nombre:"), gbc);
 
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 40, 0);
        panel.add(txtNombre, gbc);
 
        // Password
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 8, 0);
        panel.add(crearEtiqueta("Password"), gbc);
 
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(txtPassword, gbc);
 
        // Botones
        JPanel panelBotones = new JPanel(new GridBagLayout());
        panelBotones.setOpaque(false);
        GridBagConstraints b = new GridBagConstraints();
        b.gridy = 0;
        b.weightx = 1;
        b.insets = new Insets(0, 0, 0, 0);
        b.gridx = 0;
        panelBotones.add(btnRegistrar, b);
        b.gridx = 1;
        panelBotones.add(btnIniciarSesion, b);
 
        gbc.gridy = 5;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 0, 0, 0);
        panel.add(panelBotones, gbc);
    }
 
    private JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font(FUENTE, Font.PLAIN, 16));
        lbl.setForeground(COLOR_TEXTO);
        return lbl;
    }
 
    private void initEventos() {
        btnIniciarSesion.addActionListener(e -> iniciarSesion());
        btnRegistrar.addActionListener(e -> registrar());
 
        // Enter en los campos inicia sesión
        txtNombre.addActionListener(e -> txtPassword.requestFocusInWindow());
        txtPassword.addActionListener(e -> iniciarSesion());
        getRootPane().setDefaultButton(btnIniciarSesion);
    }
 
    // ------------------------------------------------------------------
    // Acciones
    // ------------------------------------------------------------------
 
    private void iniciarSesion() {
        String nombre = txtNombre.getText().trim();
        String password = new String(txtPassword.getPassword());
 
        if (nombre.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingresa tu nombre y tu password.",
                    "Campos vacios", JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        // TODO: llamar a la capa de Negocio para validar las credenciales.
        // Ejemplo (cuando exista):
        //   UsuarioDTO u = usuarioBO.iniciarSesion(nombre, password);
        //   if (u != null) { new MenuFrame(u).setVisible(true); dispose(); }
        //   else { JOptionPane.showMessageDialog(this, "Credenciales incorrectas"); }
        JOptionPane.showMessageDialog(this, "Pendiente: conectar con la capa de Negocio.");
    }
 
    private void registrar() {
        new RegistrarClientePresentacion().setVisible(true); // abre el otro frame
        dispose();                            // cierra el login
    }
 
    // ------------------------------------------------------------------
    // Componentes personalizados (bordes redondeados + placeholder)
    // ------------------------------------------------------------------
 
    private static void dibujarFondoYBorde(Graphics g, JTextComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 14, 14);
        g2.dispose();
    }
 
    private static void dibujarBordeYPlaceholder(Graphics g, JTextComponent c,
            String placeholder, boolean vacio) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
 
        if (vacio && !c.isFocusOwner()) {
            g2.setColor(COLOR_PLACEHOLDER);
            g2.setFont(c.getFont());
            Insets in = c.getInsets();
            int y = (c.getHeight() + g2.getFontMetrics().getAscent()
                    - g2.getFontMetrics().getDescent()) / 2;
            g2.drawString(placeholder, in.left, y);
        }
 
        g2.setColor(c.isFocusOwner() ? COLOR_BOTON : COLOR_BORDE);
        g2.setStroke(new BasicStroke(1.2f));
        g2.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 14, 14);
        g2.dispose();
    }
 
    static class CampoTexto extends JTextField {
 
        private final String placeholder;
 
        CampoTexto(String placeholder) {
            this.placeholder = placeholder;
            setOpaque(false);
            setFont(new Font(FUENTE, Font.PLAIN, 16));
            setForeground(COLOR_TEXTO);
            setCaretColor(COLOR_TEXTO);
            setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
            setPreferredSize(new Dimension(100, 42));
        }
 
        @Override
        protected void paintComponent(Graphics g) {
            dibujarFondoYBorde(g, this);
            super.paintComponent(g);
            dibujarBordeYPlaceholder(g, this, placeholder, getText().isEmpty());
        }
    }
 
    static class CampoPassword extends JPasswordField {
 
        private final String placeholder;
 
        CampoPassword(String placeholder) {
            this.placeholder = placeholder;
            setOpaque(false);
            setFont(new Font(FUENTE, Font.PLAIN, 16));
            setForeground(COLOR_TEXTO);
            setCaretColor(COLOR_TEXTO);
            setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
            setPreferredSize(new Dimension(100, 42));
        }
 
        @Override
        protected void paintComponent(Graphics g) {
            dibujarFondoYBorde(g, this);
            super.paintComponent(g);
            dibujarBordeYPlaceholder(g, this, placeholder, getPassword().length == 0);
        }
    }
 
    static class BotonRedondeado extends JButton {
 
        private boolean hover = false;
 
        BotonRedondeado(String texto) {
            super(texto);
            setFont(new Font(FUENTE, Font.PLAIN, 16));
            setForeground(Color.WHITE);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(133, 57));
 
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    hover = true;
                    repaint();
                }
 
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    hover = false;
                    repaint();
                }
            });
        }
 
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(hover ? COLOR_BOTON_HOVER : COLOR_BOTON);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
            g2.dispose();
            super.paintComponent(g);
        }
    }
    
    
}
