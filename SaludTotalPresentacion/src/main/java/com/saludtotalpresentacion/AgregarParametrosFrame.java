package com.saludtotalpresentacion;

import com.saludtotalpresentacion.LoginFrame.BotonRedondeado;
import static com.saludtotalpresentacion.LoginFrame.COLOR_BORDE;
import static com.saludtotalpresentacion.LoginFrame.COLOR_FONDO;
import static com.saludtotalpresentacion.LoginFrame.COLOR_PLACEHOLDER;
import static com.saludtotalpresentacion.LoginFrame.COLOR_TEXTO;
import com.saludtotalpresentacion.LoginFrame.CampoTexto;
import static com.saludtotalpresentacion.LoginFrame.FUENTE;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class AgregarParametrosFrame extends JFrame{
    
    private final Font FUENTE_ETIQUETA = new Font(FUENTE, Font.PLAIN, 16);
 
    private final Consumer<AgregarParametrosFrame> alRegistrar;
 
    private final CampoTexto txtNombre = new CampoTexto("nombre");
    private final CampoTexto txtDescripcion = new CampoTexto("descripcion");
    private final CampoTexto txtOrden = new CampoTexto("orden");
    private final AreaTexto txtRango = new AreaTexto("[]4.8  -  10.8 g/dL 20 - 25 años");
 
    // Cada rango: {min, max, unidad, edadMin, edadMax}
    private final List<String[]> rangos = new ArrayList<>();
 
    public AgregarParametrosFrame(JFrame padre, int siguienteOrden, Consumer<AgregarParametrosFrame> alRegistrar) {
        this.alRegistrar = alRegistrar;
 
        setTitle("Salud Total - Agregar parametro");
        setSize(400, 570);
        setMinimumSize(new Dimension(360, 540));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(padre);
 
        // el padre se deshabilita mientras este frame esta abierto
        padre.setEnabled(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                padre.setEnabled(true);
                padre.toFront();
            }
        });
 
        txtOrden.setText(String.valueOf(siguienteOrden));
 
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
 
        agregarCampo(panel, "Nombre:", txtNombre);
        agregarCampo(panel, "Descripcion", txtDescripcion);
        agregarCampo(panel, "Orden", txtOrden);
 
        panel.add(crearEtiqueta("Rango"));
        panel.add(Box.createVerticalStrut(4));
        panel.add(crearCuadroRango());
        panel.add(Box.createVerticalStrut(8));
 
        JButton btnRegistrar = new BotonRedondeado("Registrar");
        JButton btnRegistrarRango = new BotonGris("Registrar Rango");
        configurarBoton(btnRegistrar);
        configurarBoton(btnRegistrarRango);
 
        panel.add(btnRegistrar);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnRegistrarRango);
 
        btnRegistrar.addActionListener(e -> registrar());
        btnRegistrarRango.addActionListener(e -> registrarRango());
 
        setContentPane(panel);
    }
 
    // ------------------------------------------------------------------
    // Resultado
    // ------------------------------------------------------------------
    public String getNombre() {
        return txtNombre.getText().trim();
    }
 
    public String getDescripcion() {
        return txtDescripcion.getText().trim();
    }
 
    public int getOrden() {
        return Integer.parseInt(txtOrden.getText().trim());
    }
 
    /** Cada rango: {min, max, unidad, edadMin, edadMax}. */
    public List<String[]> getRangos() {
        return new ArrayList<>(rangos);
    }
 
    // ------------------------------------------------------------------
    // Construccion de la UI
    // ------------------------------------------------------------------
    private JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FUENTE_ETIQUETA);
        lbl.setForeground(COLOR_TEXTO);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }
 
    private void agregarCampo(JPanel panel, String texto, CampoTexto campo) {
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        panel.add(crearEtiqueta(texto));
        panel.add(Box.createVerticalStrut(4));
        panel.add(campo);
        panel.add(Box.createVerticalStrut(8));
    }
 
    /** Cuadro grande con bordes redondeados donde se van mostrando los rangos. */
    private JPanel crearCuadroRango() {
        txtRango.setEditable(false);
        txtRango.setLineWrap(true);
        txtRango.setWrapStyleWord(true);
 
        JScrollPane scroll = new JScrollPane(txtRango);
        scroll.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
 
        CuadroRedondeado cuadro = new CuadroRedondeado();
        cuadro.add(scroll, BorderLayout.CENTER);
        cuadro.setAlignmentX(Component.LEFT_ALIGNMENT);
        cuadro.setPreferredSize(new Dimension(100, 130));
        cuadro.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));
        return cuadro;
    }
 
    private void configurarBoton(JButton boton) {
        boton.setFont(FUENTE_ETIQUETA);
        boton.setAlignmentX(Component.LEFT_ALIGNMENT);
        boton.setPreferredSize(new Dimension(100, 42));
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
    }
 
    // ------------------------------------------------------------------
    // Acciones
    // ------------------------------------------------------------------
    private void registrarRango() {
        JTextField txtMin = new JTextField();
        JTextField txtMax = new JTextField();
        JTextField txtUnidad = new JTextField();
        JTextField txtEdadMin = new JTextField();
        JTextField txtEdadMax = new JTextField();
 
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.add(new JLabel("Valor minimo:"));
        form.add(txtMin);
        form.add(new JLabel("Valor maximo:"));
        form.add(txtMax);
        form.add(new JLabel("Unidad (ej. g/dL):"));
        form.add(txtUnidad);
        form.add(new JLabel("Edad minima:"));
        form.add(txtEdadMin);
        form.add(new JLabel("Edad maxima:"));
        form.add(txtEdadMax);
 
        int opcion = JOptionPane.showConfirmDialog(this, form, "Registrar rango",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }
 
        String min = txtMin.getText().trim();
        String max = txtMax.getText().trim();
        String unidad = txtUnidad.getText().trim();
        String edadMin = txtEdadMin.getText().trim();
        String edadMax = txtEdadMax.getText().trim();
 
        try {
            double vMin = Double.parseDouble(min);
            double vMax = Double.parseDouble(max);
            int eMin = Integer.parseInt(edadMin);
            int eMax = Integer.parseInt(edadMax);
            if (vMin > vMax || eMin > eMax) {
                JOptionPane.showMessageDialog(this, "El minimo no puede ser mayor que el maximo.",
                        "Rango invalido", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Los valores y las edades deben ser numeros (las edades, enteros).",
                    "Rango invalido", JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        rangos.add(new String[]{min, max, unidad, edadMin, edadMax});
        txtRango.append(min + "  -  " + max + " " + unidad + " " + edadMin + " - " + edadMax + " años\n");
    }
 
    private void registrar() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio.",
                    "Campos vacios", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            if (Integer.parseInt(txtOrden.getText().trim()) < 1) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El orden debe ser un numero entero mayor a 0.",
                    "Orden invalido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (rangos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Registra al menos un rango.",
                    "Sin rangos", JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        alRegistrar.accept(this);
        dispose();
    }
 
    // ------------------------------------------------------------------
    // Componentes personalizados
    // ------------------------------------------------------------------
    /** Panel blanco con borde redondeado (contenedor del cuadro de rangos). */
    private static class CuadroRedondeado extends JPanel {
 
        CuadroRedondeado() {
            super(new BorderLayout());
            setOpaque(false);
        }
 
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
            g2.setColor(COLOR_BORDE);
            g2.setStroke(new BasicStroke(1.2f));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
            g2.dispose();
            super.paintComponent(g);
        }
    }
 
    /** Area de texto transparente que muestra un placeholder cuando esta vacia. */
    private static class AreaTexto extends JTextArea {
 
        private final String placeholder;
 
        AreaTexto(String placeholder) {
            this.placeholder = placeholder;
            setOpaque(false);
            setFont(new Font(FUENTE, Font.PLAIN, 16));
            setForeground(COLOR_TEXTO);
        }
 
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setColor(COLOR_PLACEHOLDER);
                g2.setFont(getFont());
                g2.drawString(placeholder, getInsets().left,
                        getInsets().top + g2.getFontMetrics().getAscent());
                g2.dispose();
            }
        }
    }
 
    /** Boton gris redondeado (Registrar Rango). */
    private static class BotonGris extends JButton {
 
        private boolean hover = false;
 
        BotonGris(String texto) {
            super(texto);
            setForeground(Color.WHITE);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
            g2.setColor(hover ? new Color(0x7A7A7A) : new Color(0x666666));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
            g2.dispose();
            super.paintComponent(g);
        }
    }
    
}
