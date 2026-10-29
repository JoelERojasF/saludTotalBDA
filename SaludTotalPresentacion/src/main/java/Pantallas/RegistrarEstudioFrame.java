package Pantallas;

import DTO.Crear.CrearEstudioDTO;
import DTO.Crear.CrearParametroDTO;
import DTO.Crear.CrearRangoDTO;
import DTO.TipoMuestraDTO;
import DTO.UsuarioDTO;
import Exception.NegocioException;
import Negocio.FabricaNegocio;
import Negocio.Interfaces.IEstudioNegocio;
import Negocio.Interfaces.ITipoMuestraNegocio;
import Pantallas.LoginFrame.BotonRedondeado;
import static Pantallas.LoginFrame.COLOR_BORDE;
import static Pantallas.LoginFrame.COLOR_FONDO;
import static Pantallas.LoginFrame.COLOR_PLACEHOLDER;
import static Pantallas.LoginFrame.COLOR_TEXTO;
import Pantallas.LoginFrame.CampoTexto;
import static Pantallas.LoginFrame.FUENTE;
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
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class RegistrarEstudioFrame extends JFrame {

    private static final String TEXTO_SELECCION = "Seleccionar tipo de muestra...";

    private final Font FUENTE_TITULO = new Font(FUENTE, Font.BOLD, 22);
    private final Font FUENTE_NORMAL = new Font(FUENTE, Font.PLAIN, 14);

    private final UsuarioDTO usuarioActual;

    private final IEstudioNegocio estudioNegocio;
    private final ITipoMuestraNegocio tipoMuestraNegocio;

    private List<TipoMuestraDTO> tiposMuestra = new ArrayList<>();

    private final List<CrearParametroDTO> parametros = new ArrayList<>();
    private final List<Integer> filaAParametro = new ArrayList<>();

    protected final CampoTexto txtNombre = new CampoTexto("ej.Biometria hematica");
    protected final JComboBox<String> cmbMuestra = new JComboBox<>(new String[]{TEXTO_SELECCION});
    protected JTextArea txtNota;
    protected final JLabel lblTitulo = new JLabel("Nuevo estudio");
    protected String mensajeExito = "Estudio guardado correctamente.";

    private JTable tablaParametros;

    protected final DefaultTableModel modeloParametros = new DefaultTableModel(
            new Object[]{"Orden", "Nombre", "Unidad", "Rango", "Edad", "Descripcion"}, 0) {
        @Override
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };

    public RegistrarEstudioFrame(UsuarioDTO usuario) {
        this.usuarioActual = usuario;
        this.estudioNegocio = FabricaNegocio.crearEstudioNegocio();
        this.tipoMuestraNegocio = FabricaNegocio.crearTipoMuestraNegocio();

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

        panelPrincipal.add(crearFilaBotonesParametros());
        panelPrincipal.add(Box.createVerticalStrut(10));

        panelPrincipal.add(crearTablaParametros());
        panelPrincipal.add(Box.createVerticalGlue());

        BotonClaro btnCancelar = new BotonClaro("Cancelar");
        JButton btnGuardar = crearBoton("Guardar Estudio");
        btnCancelar.addActionListener(e -> volver());
        btnGuardar.addActionListener(e -> guardar());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelBotones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);
        panelPrincipal.add(panelBotones);

        setContentPane(panelPrincipal);

        cargarTiposMuestra();
    }

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
        btnReportes.addActionListener(e -> {
            new ReportesFrame(usuarioActual).setVisible(true);
            dispose();
        });

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

        cmbMuestra.setBackground(Color.WHITE);
        cmbMuestra.setFont(FUENTE_NORMAL);
        cmbMuestra.setPreferredSize(new Dimension(100, 42));

        fila.add(crearCampo("Nombre del estudio", txtNombre));
        fila.add(crearCampo("Tipo de muestra", cmbMuestra));
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

    private JPanel crearFilaBotonesParametros() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.setBackground(COLOR_FONDO);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        JButton btnAgregar = crearBoton("Agregar parametro");
        JButton btnEditar = crearBoton("Editar parametro");
        JButton btnEliminar = crearBoton("Eliminar parametro");

        btnAgregar.addActionListener(e -> agregarParametro());
        btnEditar.addActionListener(e -> editarParametro());
        btnEliminar.addActionListener(e -> eliminarParametro());

        panel.add(btnAgregar);
        panel.add(btnEditar);
        panel.add(btnEliminar);
        return panel;
    }

    private JScrollPane crearTablaParametros() {
        tablaParametros = new JTable(modeloParametros);
        tablaParametros.setRowHeight(28);
        tablaParametros.setShowGrid(false);
        tablaParametros.setBackground(COLOR_FONDO);
        tablaParametros.setFont(FUENTE_NORMAL);
        tablaParametros.setForeground(COLOR_TEXTO);
        tablaParametros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        int[] anchos = {60, 150, 80, 100, 80, 250};
        for (int i = 0; i < anchos.length; i++) {
            tablaParametros.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

        tablaParametros.getTableHeader().setFont(FUENTE_NORMAL);
        tablaParametros.getTableHeader().setForeground(Color.GRAY);
        tablaParametros.getTableHeader().setBackground(COLOR_FONDO);
        tablaParametros.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        tablaParametros.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tablaParametros);
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        scroll.setPreferredSize(new Dimension(830, 150));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        scroll.getViewport().setBackground(COLOR_FONDO);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        return scroll;
    }

    private JButton crearBoton(String texto) {
        BotonRedondeado boton = new BotonRedondeado(texto);
        boton.setFont(FUENTE_NORMAL);
        boton.setPreferredSize(new Dimension(150, 40));
        return boton;
    }

    private void cargarTiposMuestra() {
        try {
            tiposMuestra = tipoMuestraNegocio.obtenerTodos();

            cmbMuestra.removeAllItems();
            cmbMuestra.addItem(TEXTO_SELECCION);
            for (TipoMuestraDTO tipo : tiposMuestra) {
                cmbMuestra.addItem(tipo.getNombre());
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "No se pudieron cargar los tipos de muestra", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void recargarTabla() {
        modeloParametros.setRowCount(0);
        filaAParametro.clear();
        for (int i = 0; i < parametros.size(); i++) {
            CrearParametroDTO p = parametros.get(i);
            if (Boolean.TRUE.equals(p.getNumerico()) && !p.getRangos().isEmpty()) {
                for (CrearRangoDTO r : p.getRangos()) {
                    modeloParametros.addRow(new Object[]{
                        p.getOrdenAparicion(), p.getNombre(), p.getUnidadMedida(),
                        r.getInicio() + "-" + r.getFin(),
                        r.getEdadInicio() + "-" + r.getEdadFin(),
                        p.getDescripcion()
                    });
                    filaAParametro.add(i);
                }
            } else {
                modeloParametros.addRow(new Object[]{
                    p.getOrdenAparicion(), p.getNombre(), p.getUnidadMedida(),
                    "-", "-", p.getDescripcion()
                });
                filaAParametro.add(i);
            }
        }
    }

    private void agregarParametro() {
        new RegistrarParametrosFrame(this, maxOrden() + 1, p -> {
            parametros.add(p);
            recargarTabla();
        }).setVisible(true);
    }

    private void editarParametro() {
        int indice = indiceParametroSeleccionado();
        if (indice < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un parametro de la tabla para editarlo.",
                    "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }
        CrearParametroDTO actual = parametros.get(indice);
        new RegistrarParametrosFrame(this, actual.getOrdenAparicion(), actual, p -> {
            parametros.set(indice, p);
            recargarTabla();
        }).setVisible(true);
    }

    private void eliminarParametro() {
        int indice = indiceParametroSeleccionado();
        if (indice < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un parametro de la tabla para eliminarlo.",
                    "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }
        CrearParametroDTO p = parametros.get(indice);
        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el parametro '" + p.getNombre() + "'?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            parametros.remove(indice);
            recargarTabla();
        }
    }

    private int indiceParametroSeleccionado() {
        int fila = tablaParametros.getSelectedRow();
        if (fila < 0 || fila >= filaAParametro.size()) {
            return -1;
        }
        return filaAParametro.get(fila);
    }

    private int maxOrden() {
        int max = 0;
        for (CrearParametroDTO p : parametros) {
            if (p.getOrdenAparicion() != null && p.getOrdenAparicion() > max) {
                max = p.getOrdenAparicion();
            }
        }
        return max;
    }

    private void guardar() {
        String nombre = txtNombre.getText().trim();
        String descripcion = txtNota.getText().trim();
        int indiceMuestra = cmbMuestra.getSelectedIndex();

        if (nombre.isEmpty() || descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Llena el nombre y la nota descriptiva.",
                    "Campos vacios", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (indiceMuestra <= 0 || indiceMuestra - 1 >= tiposMuestra.size()) {
            JOptionPane.showMessageDialog(this, "Selecciona un tipo de muestra.",
                    "Sin tipo de muestra", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (parametros.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Agrega al menos un parametro.",
                    "Sin parametros", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer idTipoMuestra = tiposMuestra.get(indiceMuestra - 1).getId();
        CrearEstudioDTO dto = new CrearEstudioDTO(nombre, descripcion, idTipoMuestra);
        dto.setParametros(new ArrayList<>(parametros));

        try {
            estudioNegocio.registrarCompleto(dto);
            JOptionPane.showMessageDialog(this, mensajeExito);
            volver();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "No se pudo guardar el estudio", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(),
                    "No se pudo guardar el estudio", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void volver() {
        new EstudiosFrame(usuarioActual).setVisible(true);
        dispose();
    }

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
