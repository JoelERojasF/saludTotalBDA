package Pantallas;

import DTO.Crear.CrearParametroDTO;
import DTO.Crear.CrearRangoDTO;
import Pantallas.LoginFrame.BotonRedondeado;
import static Pantallas.LoginFrame.COLOR_BORDE;
import static Pantallas.LoginFrame.COLOR_FONDO;
import static Pantallas.LoginFrame.COLOR_PLACEHOLDER;
import static Pantallas.LoginFrame.COLOR_TEXTO;
import Pantallas.LoginFrame.CampoTexto;
import static Pantallas.LoginFrame.FUENTE;
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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RegistrarParametrosFrame extends JFrame {

    private final Font FUENTE_ETIQUETA = new Font(FUENTE, Font.PLAIN, 16);

    private final Consumer<CrearParametroDTO> alRegistrar;

    private final CampoTexto txtNombre = new CampoTexto("nombre");
    private final CampoTexto txtDescripcion = new CampoTexto("descripcion");
    private final CampoTexto txtOrden = new CampoTexto("orden");
    private final CampoTexto txtUnidadMedida = new CampoTexto("unidad de medida");
    private final AreaTexto txtRango = new AreaTexto("4  -  10 g/dL  20 - 25 años");
    private final JCheckBox chkNumerico = new JCheckBox("Parametro numerico", true);
    JButton btnRegistrar = new BotonRedondeado("Registrar");
    JButton btnRegistrarRango = new BotonGris("Registrar Rango");

    private final List<CrearRangoDTO> rangos = new ArrayList<>();

    public RegistrarParametrosFrame(JFrame padre, int siguienteOrden, Consumer<CrearParametroDTO> alRegistrar) {
        this(padre, siguienteOrden, null, alRegistrar);
    }

    public RegistrarParametrosFrame(JFrame padre, int siguienteOrden, CrearParametroDTO existente,
            Consumer<CrearParametroDTO> alRegistrar) {
        this.alRegistrar = alRegistrar;

        setTitle("Salud Total - Agregar parametro");
        setSize(400, 570);
        setMinimumSize(new Dimension(360, 540));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(padre);

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
        agregarCampo(panel, "unidad de medida", txtUnidadMedida);

        chkNumerico.setFont(FUENTE_ETIQUETA);
        chkNumerico.setForeground(COLOR_TEXTO);
        chkNumerico.setBackground(COLOR_FONDO);
        chkNumerico.setAlignmentX(Component.LEFT_ALIGNMENT);
        chkNumerico.addActionListener(e -> alCambiarNumerico());
        panel.add(chkNumerico);
        panel.add(Box.createVerticalStrut(8));

        panel.add(crearEtiqueta("Rango"));
        panel.add(Box.createVerticalStrut(4));
        panel.add(crearCuadroRango());
        panel.add(Box.createVerticalStrut(8));

        configurarBoton(btnRegistrar);
        configurarBoton(btnRegistrarRango);

        panel.add(btnRegistrar);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnRegistrarRango);

        btnRegistrar.addActionListener(e -> registrar());
        btnRegistrarRango.addActionListener(e -> registrarRango());

        setContentPane(panel);

        if (existente != null) {
            precargar(existente);
        }
    }

    
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


    private void registrarRango() {
        JTextField txtMin = new JTextField();
        JTextField txtMax = new JTextField();
        JTextField txtEdadMin = new JTextField();
        JTextField txtEdadMax = new JTextField();

        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.add(new JLabel("Valor minimo:"));
        form.add(txtMin);
        form.add(new JLabel("Valor maximo:"));
        form.add(txtMax);
        form.add(new JLabel("Edad minima:"));
        form.add(txtEdadMin);
        form.add(new JLabel("Edad maxima:"));
        form.add(txtEdadMax);

        int opcion = JOptionPane.showConfirmDialog(this, form, "Registrar rango",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }

        double vMin;
        double vMax;
        int eMin;
        int eMax;
        try {
            vMin = Double.parseDouble(txtMin.getText().trim());
            vMax = Double.parseDouble(txtMax.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Los valores deben ser numeros.",
                    "Rango invalido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            eMin = Integer.parseInt(txtEdadMin.getText().trim());
            eMax = Integer.parseInt(txtEdadMax.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Las edades deben ser numeros enteros.",
                    "Rango invalido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (vMin > vMax || eMin > eMax) {
            JOptionPane.showMessageDialog(this, "El minimo no puede ser mayor que el maximo.",
                    "Rango invalido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (vMin < 0 || vMax < 0 || eMin < 0 || eMax < 0) {
            JOptionPane.showMessageDialog(this, "Los valores y las edades no pueden ser negativos.",
                    "Rango invalido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (vMin > vMax || eMin > eMax) {
            JOptionPane.showMessageDialog(this, "El minimo no puede ser mayor que el maximo.",
                    "Rango invalido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (vMin == vMax) {
            JOptionPane.showMessageDialog(this, "El valor minimo y el maximo no pueden ser iguales.",
                    "Rango invalido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        rangos.add(new CrearRangoDTO(vMin, vMax, eMin, eMax, null));
        txtRango.append(vMin + "  -  " + vMax + "  " + eMin + " - " + eMax + " años\n");
    }

    private void registrar() {
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        boolean numerico = chkNumerico.isSelected();
        String unidadMedida = txtUnidadMedida.getText().trim();

        if (nombre.isEmpty() || descripcion.isEmpty() || (numerico && unidadMedida.isEmpty())) {
            JOptionPane.showMessageDialog(this, "El nombre, la descripcion y la unidad de medida son obligatorios.",
                    "Campos vacios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int orden;
        try {
            orden = Integer.parseInt(txtOrden.getText().trim());
            if (orden < 1) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El orden debe ser un numero entero mayor a 0.",
                    "Orden invalido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (numerico && rangos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Registra al menos un rango.",
                    "Sin rangos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<CrearRangoDTO> rangosFinales = new ArrayList<>(rangos);
        if (!numerico) {
            rangosFinales.clear();
            rangosFinales.add(new CrearRangoDTO(0.0, 1.0, 0, 1000, null));
        }

        CrearParametroDTO parametro = new CrearParametroDTO(
                nombre, descripcion, orden, unidadMedida, numerico, null);
        parametro.setRangos(rangosFinales);

        alRegistrar.accept(parametro);
        dispose();
    }

    private void alCambiarNumerico() {
        boolean numerico = chkNumerico.isSelected();
        txtUnidadMedida.setEnabled(numerico);
        btnRegistrarRango.setEnabled(numerico);
        txtUnidadMedida.setText("");
        if (!numerico) {
            txtUnidadMedida.setText("positivo/negativo");
            rangos.clear();
            txtRango.setText("");
        }
    }


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

    private void precargar(CrearParametroDTO e) {
        txtNombre.setText(e.getNombre());
        txtDescripcion.setText(e.getDescripcion());
        txtOrden.setText(String.valueOf(e.getOrdenAparicion()));

        boolean numerico = Boolean.TRUE.equals(e.getNumerico());
        chkNumerico.setSelected(numerico);
        alCambiarNumerico();                     
        txtUnidadMedida.setText(e.getUnidadMedida() == null ? "" : e.getUnidadMedida());

        if (numerico && e.getRangos() != null) {
            for (CrearRangoDTO r : e.getRangos()) {
                rangos.add(new CrearRangoDTO(r.getInicio(), r.getFin(), r.getEdadInicio(), r.getEdadFin(), null));
                txtRango.append(r.getInicio() + "  -  " + r.getFin() + "  "
                        + r.getEdadInicio() + " - " + r.getEdadFin() + " años\n");
            }
        }
    }

}
