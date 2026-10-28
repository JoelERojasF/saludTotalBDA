package com.saludtotalpresentacion;

import DTO.ClienteDTO;
import DTO.DoctorDTO;
import DTO.EstudioDTO;
import DTO.ParametroDTO;
import DTO.RangoDTO;
import DTO.SolicitudDTO;
import DTO.UsuarioDTO;
import DTO.Crear.CrearResultadoDTO;
import Exception.NegocioException;
import Negocio.ClienteNegocio;
import Negocio.DoctorNegocio;
import Negocio.EstudioNegocio;
import Negocio.ParametroNegocio;
import Negocio.RangoNegocio;
import Negocio.ResultadoNegocio;
import Negocio.SolicitudNegocio;
import com.saludtotalpresentacion.LoginFrame.BotonRedondeado;
import static com.saludtotalpresentacion.LoginFrame.COLOR_FONDO;
import static com.saludtotalpresentacion.LoginFrame.COLOR_TEXTO;
import com.saludtotalpresentacion.LoginFrame.CampoTexto;
import static com.saludtotalpresentacion.LoginFrame.FUENTE;
import java.awt.BorderLayout;
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
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class ResultadosFrame extends JFrame {

    private final Font FUENTE_TITULO = new Font(FUENTE, Font.BOLD, 22);
    private final Font FUENTE_NORMAL = new Font(FUENTE, Font.PLAIN, 14);
    private final Font FUENTE_CHICA = new Font(FUENTE, Font.PLAIN, 12);

    private final UsuarioDTO usuarioActual;

    // Dependencias de Negocio
    private final SolicitudNegocio solicitudNegocio;
    private final ClienteNegocio clienteNegocio;
    private final DoctorNegocio doctorNegocio;
    private final EstudioNegocio estudioNegocio;
    private final ParametroNegocio parametroNegocio;
    private final RangoNegocio rangoNegocio;
    private final ResultadoNegocio resultadoNegocio;

    // Cada fila visual contendrá: {SolicitudDTO, folio, paciente, doctor, estudio, ClienteDTO}
    private final List<Object[]> todas = new ArrayList<>();
    private final List<Object[]> visibles = new ArrayList<>();
    private SolicitudDTO solicitudSeleccionada;
    private final List<ParametroDTO> parametrosSeleccionados = new ArrayList<>();
    private boolean cargando = false;

    private final CampoTexto txtBuscar = new CampoTexto("Buscar folio o paciente...");

    private final JLabel lblCliente = new JLabel("", SwingConstants.CENTER);
    private final JLabel lblEdad = new JLabel("", SwingConstants.CENTER);
    private final JLabel lblDoctor = new JLabel("", SwingConstants.CENTER);
    private final JLabel lblEstudio = new JLabel("", SwingConstants.CENTER);

    // Lista de solicitudes con checkbox y columnas completas
    private final DefaultTableModel modeloLista = new DefaultTableModel(
            new Object[]{"Sel", "Folio", "Paciente", "Doctor", "Estudio"}, 0) {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columnIndex == 0 ? Boolean.class : String.class;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 0;
        }
    };

    // Tabla de parametros: solo se pueden editar Resultado (1) y Observacion (4)
    private final DefaultTableModel modeloParametros = new DefaultTableModel(
            new Object[]{"Parametro", "Resultado", "Unidad", "Rango Normal", "Observacion"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 1 || column == 4;
        }
    };

    private JTable tablaParametros;

    public ResultadosFrame(UsuarioDTO usuario) {
        this.usuarioActual = usuario;

        // Inicializar Negocios
        this.solicitudNegocio = new SolicitudNegocio();
        this.clienteNegocio = new ClienteNegocio();
        this.doctorNegocio = new DoctorNegocio();
        this.estudioNegocio = new EstudioNegocio();
        this.parametroNegocio = new ParametroNegocio();
        this.rangoNegocio = new RangoNegocio();
        this.resultadoNegocio = new ResultadoNegocio();

        setTitle("Salud Total - Nuevo Resultado");
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

        JLabel lblTitulo = new JLabel("Nuevo resultado");
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(Box.createVerticalStrut(12));

        panelPrincipal.add(crearBuscador());
        panelPrincipal.add(Box.createVerticalStrut(12));

        panelPrincipal.add(crearListaSolicitudes());
        panelPrincipal.add(Box.createVerticalStrut(10));

        panelPrincipal.add(crearDatosSolicitud());
        panelPrincipal.add(Box.createVerticalStrut(10));

        panelPrincipal.add(crearTablaParametros());
        panelPrincipal.add(Box.createVerticalGlue());

        BotonClaro btnCancelar = new BotonClaro("Cancelar");
        JButton btnGuardar = crearBoton("Guardar Resultado");
        btnCancelar.addActionListener(e -> limpiar());
        btnGuardar.addActionListener(e -> guardar());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelBotones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);
        panelPrincipal.add(panelBotones);

        setContentPane(panelPrincipal);

        cargarDatos();
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

        btnEstudio.addActionListener(e -> {
            new EstudiosFrame(usuarioActual).setVisible(true);
            dispose();
        });
        btnSolicitudes.addActionListener(e -> {
            new SolicitudesFrame(usuarioActual).setVisible(true);
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

    private JPanel crearBuscador() {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setBackground(COLOR_FONDO);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                buscar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                buscar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                buscar();
            }
        });

        panel.add(txtBuscar, BorderLayout.CENTER);
        return panel;
    }

    private JScrollPane crearListaSolicitudes() {
        JTable tabla = new JTable(modeloLista);
        tabla.setRowHeight(28);
        tabla.setShowGrid(false);
        tabla.setFont(FUENTE_NORMAL);
        tabla.setForeground(COLOR_TEXTO);

        // Configuramos la cabecera visible y estilizada para mostrar las columnas separadas
        tabla.getTableHeader().setFont(FUENTE_NORMAL);
        tabla.getTableHeader().setForeground(Color.GRAY);
        tabla.getTableHeader().setBackground(COLOR_FONDO);
        tabla.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        tabla.getTableHeader().setReorderingAllowed(false);

        // Ajustamos anchos de columnas
        tabla.getColumnModel().getColumn(0).setMaxWidth(40); // Sel
        tabla.getColumnModel().getColumn(1).setPreferredWidth(100); // Folio
        tabla.getColumnModel().getColumn(2).setPreferredWidth(230); // Paciente
        tabla.getColumnModel().getColumn(3).setPreferredWidth(180); // Doctor
        tabla.getColumnModel().getColumn(4).setPreferredWidth(200); // Estudio

        modeloLista.addTableModelListener(e -> {
            if (cargando) {
                return;
            }
            if (e.getColumn() == 0) {
                alSeleccionar(e.getFirstRow());
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        scroll.setPreferredSize(new Dimension(830, 200));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        return scroll;
    }

    private JPanel crearDatosSolicitud() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 10, 0));
        panel.setBackground(COLOR_FONDO);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        panel.add(crearDato("Cliente", lblCliente));
        panel.add(crearDato("Edad", lblEdad));
        panel.add(crearDato("Doctor", lblDoctor));
        panel.add(crearDato("Estudio", lblEstudio));
        return panel;
    }

    private JPanel crearDato(String titulo, JLabel valor) {
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(FUENTE_CHICA);
        lblTitulo.setForeground(Color.GRAY);

        valor.setFont(FUENTE_CHICA);
        valor.setForeground(COLOR_TEXTO);

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBackground(COLOR_FONDO);
        panel.add(lblTitulo);
        panel.add(valor);
        return panel;
    }

    private JScrollPane crearTablaParametros() {
        tablaParametros = new JTable(modeloParametros);
        tablaParametros.setRowHeight(30);
        tablaParametros.setShowGrid(false);
        tablaParametros.setBackground(COLOR_FONDO);
        tablaParametros.setFont(FUENTE_NORMAL);
        tablaParametros.setForeground(COLOR_TEXTO);

        int[] anchos = {140, 90, 70, 110, 250};
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
        scroll.setPreferredSize(new Dimension(830, 110));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
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

    // ------------------------------------------------------------------
    // Logica de base de datos
    // ------------------------------------------------------------------
    private void cargarDatos() {
        try {
            // Solo mostrar solicitudes que NO tengan resultados aún (pendientes)
            List<SolicitudDTO> pendientes = solicitudNegocio.obtenerTodosPendientes();

            todas.clear();
            for (SolicitudDTO sol : pendientes) {
                // Filtrar canceladas si es necesario (asumimos que obtenerTodosPendientes no trae canceladas)
                if (sol.getCancelada()) {
                    continue;
                }

                // Consulta bajo demanda en la BD por cada ID asociado a la solicitud
                ClienteDTO cli = clienteNegocio.buscarPorId(sol.getIdCliente());
                DoctorDTO doc = sol.getIdDoctor() != null ? doctorNegocio.buscarPorId(sol.getIdDoctor()) : null;
                EstudioDTO est = estudioNegocio.buscarPorId(sol.getIdEstudio());

                String nombrePaciente = cli != null ? cli.getNombre() + " " + (cli.getApellidoPaterno() != null ? cli.getApellidoPaterno() : "") : "Desconocido";
                String nombreDoctor = doc != null ? "DR. " + doc.getNombre() + " " + (doc.getApellidoPaterno() != null ? doc.getApellidoPaterno() : "") : "Cuenta propia";
                String nombreEstudio = est != null ? est.getNombre() : "Desconocido";

                todas.add(new Object[]{sol, sol.getFolio(), nombrePaciente, nombreDoctor, nombreEstudio, cli});
            }
            buscar();

        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscar() {
        String texto = txtBuscar.getText().trim().toLowerCase();
        if (texto.equals("buscar folio o paciente...")) {
            texto = "";
        }

        cargando = true;
        modeloLista.setRowCount(0);
        visibles.clear();
        for (Object[] s : todas) {
            String folio = s[1].toString().toLowerCase();
            String paciente = s[2].toString().toLowerCase();
            if (folio.contains(texto) || paciente.contains(texto)) {
                visibles.add(s);
                // Las columnas son: "Sel", "Folio", "Paciente", "Doctor", "Estudio"
                modeloLista.addRow(new Object[]{false, s[1], s[2], s[3], s[4]});
            }
        }
        cargando = false;

        limpiarDetalle();
    }

    private void alSeleccionar(int fila) {
        boolean marcada = (Boolean) modeloLista.getValueAt(fila, 0);

        cargando = true;
        for (int i = 0; i < modeloLista.getRowCount(); i++) {
            if (i != fila) {
                modeloLista.setValueAt(false, i, 0);
            }
        }
        cargando = false;

        limpiarDetalle();
        if (marcada) {
            Object[] data = visibles.get(fila);
            solicitudSeleccionada = (SolicitudDTO) data[0];
            ClienteDTO cli = (ClienteDTO) data[5];

            lblCliente.setText(data[2].toString());
            lblEdad.setText(calcularEdad(cli != null ? cli.getFechaNacimiento() : ""));
            lblDoctor.setText(data[3].toString());
            lblEstudio.setText(data[4].toString());

            parametrosSeleccionados.clear();

            try {
                // Obtener parámetros y rangos de la BD solo al seleccionar el estudio específico
                List<ParametroDTO> todosParametrosBD = parametroNegocio.obtenerTodos();
                List<RangoDTO> todosRangosBD = rangoNegocio.obtenerTodos();

                for (ParametroDTO p : todosParametrosBD) {
                    if (p.getIdEstudio().equals(solicitudSeleccionada.getIdEstudio())) {
                        parametrosSeleccionados.add(p);
                        String unidad = "-";
                        String rangoStr = "-";

                        if (Boolean.TRUE.equals(p.getNumerico())) {
                            for (RangoDTO r : todosRangosBD) {
                                if (r.getIdParametro().equals(p.getId())) {
                                    unidad = p.getUnidadMedida();
                                    rangoStr = r.getInicio() + " - " + r.getFin();
                                    break; // Solo tomamos el primero de forma visual
                                }
                            }
                        }

                        modeloParametros.addRow(new Object[]{
                            p.getOrdenAparicion() + " " + p.getNombre(), "", unidad, rangoStr, ""
                        });
                    }
                }
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, "Error al obtener los parámetros de la base de datos.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarDetalle() {
        if (tablaParametros != null && tablaParametros.isEditing()) {
            tablaParametros.getCellEditor().cancelCellEditing();
        }
        solicitudSeleccionada = null;
        parametrosSeleccionados.clear();
        lblCliente.setText("");
        lblEdad.setText("");
        lblDoctor.setText("");
        lblEstudio.setText("");
        modeloParametros.setRowCount(0);
    }

    private void limpiar() {
        txtBuscar.setText("Buscar folio o paciente...");
        buscar();
    }

    private void guardar() {
        if (solicitudSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una solicitud.",
                    "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (tablaParametros.isEditing()) {
            tablaParametros.getCellEditor().stopCellEditing();
        }

        for (int i = 0; i < modeloParametros.getRowCount(); i++) {
            String resultado = String.valueOf(modeloParametros.getValueAt(i, 1)).trim();
            if (resultado.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Captura el resultado de todos los parametros.",
                        "Campos vacios", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        try {
            for (int i = 0; i < parametrosSeleccionados.size(); i++) {
                ParametroDTO param = parametrosSeleccionados.get(i);
                String resStr = String.valueOf(modeloParametros.getValueAt(i, 1)).trim();
                String obsStr = String.valueOf(modeloParametros.getValueAt(i, 4)).trim();

                CrearResultadoDTO dto = new CrearResultadoDTO(resStr, obsStr, solicitudSeleccionada.getId(), param.getId());
                resultadoNegocio.registrar(dto);
            }
            JOptionPane.showMessageDialog(this, "Resultado guardado correctamente.");

            // Actualizamos la lista, la base de datos y limpiamos la pantalla
            limpiar();
            cargarDatos();

        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar el resultado: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ------------------------------------------------------------------
    // Utilidades
    // ------------------------------------------------------------------
    private String calcularEdad(String fechaNacimiento) {
        if (fechaNacimiento == null || fechaNacimiento.isBlank()) {
            return "N/A";
        }
        try {
            LocalDate nacimiento = LocalDate.parse(fechaNacimiento);
            return Period.between(nacimiento, LocalDate.now()).getYears() + " años";
        } catch (Exception e) {
            return fechaNacimiento;
        }
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
