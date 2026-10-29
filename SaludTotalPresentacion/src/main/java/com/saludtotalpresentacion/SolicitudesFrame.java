/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.saludtotalpresentacion;

import DTO.ClienteDTO;
import DTO.DoctorDTO;
import DTO.EstudioDTO;
import DTO.ResultadoDTO;
import DTO.SolicitudDTO;
import DTO.UsuarioDTO;
import Exception.NegocioException;
import Negocio.FabricaNegocio;
import Negocio.Interfaces.IClienteNegocio;
import Negocio.Interfaces.IDoctorNegocio;
import Negocio.Interfaces.IEstudioNegocio;
import Negocio.Interfaces.ISolicitudNegocio;
import Negocio.ResultadoNegocio;
import Negocio.SolicitudNegocio;
import com.saludtotalpresentacion.LoginFrame.CampoTexto;
import static com.saludtotalpresentacion.LoginFrame.COLOR_FONDO;
import static com.saludtotalpresentacion.LoginFrame.COLOR_TEXTO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author pirown
 */
public class SolicitudesFrame extends JFrame {

    private UsuarioDTO usuarioActual;

    private final IClienteNegocio clienteNegocio;
    private final IDoctorNegocio doctorNegocio;
    private final IEstudioNegocio estudioNegocio;
    private final ISolicitudNegocio solicitudNegocio;

    private List<ClienteDTO> listaClientes = new ArrayList<>();
    private List<DoctorDTO> listaDoctores = new ArrayList<>();
    private List<EstudioDTO> listaEstudios = new ArrayList<>();

    private final List<Object[]> todasLasFilas = new ArrayList<>();
    private final List<Object[]> filasVisibles = new ArrayList<>();

    private final Color COLOR_BOTON = new Color(51, 51, 51);
    private final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 22);

    private DefaultTableModel modeloTabla;
    private DefaultTableModel modeloDetalles;

    private CampoTexto txtBuscar;

    private boolean ajustandoSeleccion = false;

    public SolicitudesFrame(UsuarioDTO usuario) {
        this.usuarioActual = usuario;

        this.clienteNegocio = FabricaNegocio.crearClienteNegocio();
        this.doctorNegocio = FabricaNegocio.crearDoctorNegocio();
        this.estudioNegocio = FabricaNegocio.crearEstudioNegocio();
        this.solicitudNegocio = new SolicitudNegocio();

        setTitle("Salud Total - Solicitudes");
        setSize(900, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(500, 600));

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

        JButton btnRegistrar = crearBotonOscuro("Registrar solicitud");

        btnRegistrar.addActionListener(e -> {
            RegistrarSolicitudFrame registrarFrame = new RegistrarSolicitudFrame(usuarioActual);
            registrarFrame.setVisible(true);
            this.dispose();
        });

        pnlBtnRegistrar.add(btnRegistrar);

        panelPrincipal.add(pnlBtnRegistrar);
        panelPrincipal.add(Box.createVerticalStrut(15));

        panelPrincipal.add(crearSeccionDetalles());
        panelPrincipal.add(Box.createVerticalStrut(20));

        panelPrincipal.add(crearBotonesInferiores());

        setContentPane(panelPrincipal);

        cargarDatos();
    }

    private void cargarDatos() {
        try {
            listaClientes = clienteNegocio.obtenerTodos();
            listaDoctores = doctorNegocio.obtenerTodos();
            listaEstudios = estudioNegocio.obtenerTodos();

            List<SolicitudDTO> listaSolicitudes = solicitudNegocio.obtenerTodosActivos();

            todasLasFilas.clear();

            for (SolicitudDTO sol : listaSolicitudes) {
                ClienteDTO cli = getClientePorId(sol.getIdCliente());
                DoctorDTO doc = getDoctorPorId(sol.getIdDoctor());
                EstudioDTO est = getEstudioPorId(sol.getIdEstudio());

                String nombreCliente = cli != null ? nombreCompletoCliente(cli) : "Desconocido";
                String nombreDoctor = doc != null ? "DR. " + nombreCompletoDoctor(doc) : "N/A (Cuenta propia)";
                String nombreEstudio = est != null ? est.getNombre() : "Desconocido";
                

                Object[] filaData = new Object[]{
                    sol, false, sol.getFolio(), nombreCliente, nombreEstudio, nombreDoctor
                };

                todasLasFilas.add(filaData);
            }

            filtrar();

        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "No se pudieron cargar los registros", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtrar() {
        String textoBusqueda = txtBuscar.getText().trim().toLowerCase();

        ajustandoSeleccion = true;
        modeloTabla.setRowCount(0);
        filasVisibles.clear();
        modeloDetalles.setRowCount(0);

        for (Object[] fila : todasLasFilas) {
            String folio = fila[2].toString().toLowerCase();
            String paciente = fila[3].toString().toLowerCase();
            String estudio = fila[4].toString().toLowerCase();
            String doctor = fila[5].toString().toLowerCase();

            if (folio.toLowerCase().contains(textoBusqueda) || paciente.toLowerCase().contains(textoBusqueda) || estudio.toLowerCase().contains(textoBusqueda) || doctor.toLowerCase().contains(textoBusqueda)) {
                filasVisibles.add(fila);
                modeloTabla.addRow(new Object[]{
                    fila[1], fila[2], fila[3], fila[4], fila[5]
                });
            }
        }
        ajustandoSeleccion = false;
    }

    private void mostrarDetallesFila(int filaVisibleIndex) {
        modeloDetalles.setRowCount(0);

        if (filaVisibleIndex < 0 || filaVisibleIndex >= filasVisibles.size()) {
            return;
        }

        Object[] filaData = filasVisibles.get(filaVisibleIndex);
        SolicitudDTO solicitud = (SolicitudDTO) filaData[0];

        ClienteDTO cliente = getClientePorId(solicitud.getIdCliente());
        DoctorDTO doctor = getDoctorPorId(solicitud.getIdDoctor());
        EstudioDTO estudio = getEstudioPorId(solicitud.getIdEstudio());

        String textoPaciente = "N/A";
        if (cliente != null) {
            String sexo = cliente.getSexo() != null ? cliente.getSexo() : "N/A";
            String fechaNac = cliente.getFechaNacimiento() != null ? cliente.getFechaNacimiento() : "N/A";
            String sangre = cliente.getTipoSangre() != null ? cliente.getTipoSangre() : "N/A";

            textoPaciente = nombreCompletoCliente(cliente) + "\n\n"
                    + "Sexo: " + sexo + "\n\n"
                    + "Fecha nacimiento:\n" + fechaNac + "\n\n"
                    + "Tipo Sangre: " + sangre;
        }

        String textoDoctor = "Por cuenta propia";
        if (doctor != null) {
            String sexoDoc = doctor.getSexo() != null ? doctor.getSexo() : "N/A";
            textoDoctor = "DR. " + nombreCompletoDoctor(doctor) + "\n\nSexo: " + sexoDoc;
        }

        String textoSolicitud = "Folio: " + solicitud.getFolio() + "\n\n"
                + "Fecha:\n" + solicitud.getFechaHoraGeneracion() + "\n\n"
                + "Estudio:\n" + (estudio != null ? estudio.getNombre() : "N/A");

        boolean tieneResultado = false;
        try {
            for (ResultadoDTO r : new ResultadoNegocio().obtenerTodos()) {
                if (r.getIdSolicitud().equals(solicitud.getId())) {
                    tieneResultado = true;
                }
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        String textoEstado = "PENDIENTE";
        if (solicitud.getCancelada()) {
            textoEstado = "CANCELADA";
        } else if (tieneResultado) {
            textoEstado = "CON RESULTADO";
        }

        modeloDetalles.addRow(new Object[]{
            textoPaciente,
            textoDoctor,
            textoSolicitud,
            textoEstado
        });
    }

    private ClienteDTO getClientePorId(Integer id) {
        if (id == null) {
            return null;
        }
        for (ClienteDTO c : listaClientes) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    private DoctorDTO getDoctorPorId(Integer id) {
        if (id == null) {
            return null;
        }
        for (DoctorDTO d : listaDoctores) {
            if (d.getId().equals(id)) {
                return d;
            }
        }
        return null;
    }

    private EstudioDTO getEstudioPorId(Integer id) {
        if (id == null) {
            return null;
        }
        for (EstudioDTO e : listaEstudios) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    private String nombreCompletoCliente(ClienteDTO c) {
        StringBuilder sb = new StringBuilder(c.getNombre());
        if (c.getApellidoPaterno() != null) {
            sb.append(" ").append(c.getApellidoPaterno());
        }
        if (c.getApellidoMaterno() != null) {
            sb.append(" ").append(c.getApellidoMaterno());
        }
        return sb.toString();
    }

    private String nombreCompletoDoctor(DoctorDTO d) {
        StringBuilder sb = new StringBuilder(d.getNombre());
        if (d.getApellidoPaterno() != null) {
            sb.append(" ").append(d.getApellidoPaterno());
        }
        if (d.getApellidoMaterno() != null) {
            sb.append(" ").append(d.getApellidoMaterno());
        }
        return sb.toString();
    }

    // UI
    private JPanel crearBarraNavegacion() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 0));
        panel.setBackground(COLOR_FONDO);

        JButton btnEstudios = crearBotonOscuro("Estudios");
        btnEstudios.addActionListener(e -> {
            EstudiosFrame estudiosFrame = new EstudiosFrame(usuarioActual);
            estudiosFrame.setVisible(true);
            this.dispose();
        });

        JButton btnSolicitudes = crearBotonOscuro("Solicitudes"); 

        JButton btnResultado = crearBotonOscuro("Resultado");
        btnResultado.addActionListener(e -> {
            ResultadosFrame resultadosFrame = new ResultadosFrame(usuarioActual);
            resultadosFrame.setVisible(true);
            this.dispose();
        });

        JButton btnReportes = crearBotonOscuro("Reportes");
        btnReportes.addActionListener(e -> {
            new ReportesFrame(usuarioActual).setVisible(true);
            dispose();
        });

        panel.add(btnEstudios);
        panel.add(btnSolicitudes);
        panel.add(btnResultado);
        panel.add(btnReportes);

        return panel;
    }

    private JPanel crearSeccionBuscador() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 0));
        panel.setBackground(COLOR_FONDO);

        JLabel lblTitulo = new JLabel("Solicitudes");
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel pnlBusqueda = new JPanel(new BorderLayout(15, 0));
        pnlBusqueda.setBackground(COLOR_FONDO);
        pnlBusqueda.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlBusqueda.setMaximumSize(new Dimension(850, 42));

        txtBuscar = new CampoTexto("Buscar...");
        txtBuscar.setPreferredSize(new Dimension(450, 42));

        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrar();
            }
        });

        pnlBusqueda.add(txtBuscar, BorderLayout.CENTER);

        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(8));
        panel.add(pnlBusqueda);

        return panel;
    }

    private JPanel crearTablaSolicitudes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_FONDO);

        modeloTabla = new DefaultTableModel(
                new Object[]{"Sel", "Folio", "Paciente", "Estudio", "Doctor"}, 0) {
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
                return column == 0;
            }
        };

        modeloTabla.addTableModelListener(e -> {
            if (ajustandoSeleccion) {
                return;
            }

            if (e.getColumn() == 0 && e.getType() == TableModelEvent.UPDATE) {
                int filaModificada = e.getFirstRow();
                Boolean estaMarcado = (Boolean) modeloTabla.getValueAt(filaModificada, 0);

                if (Boolean.TRUE.equals(estaMarcado)) {
                    ajustandoSeleccion = true;
                    for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                        if (i != filaModificada) {
                            modeloTabla.setValueAt(false, i, 0);
                        }
                    }
                    ajustandoSeleccion = false;
                    mostrarDetallesFila(filaModificada);
                } else {
                    modeloDetalles.setRowCount(0);
                }
            }
        });

        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(28);
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

        String[] columnas = {"Paciente", "Doctor", "Solicitud", "Estado"};

        modeloDetalles = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tablaDetalles = new JTable(modeloDetalles);

        tablaDetalles.setRowHeight(160);
        tablaDetalles.setShowGrid(false);
        tablaDetalles.setBackground(COLOR_FONDO);
        tablaDetalles.setOpaque(true);

        tablaDetalles.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaDetalles.getTableHeader().setForeground(Color.GRAY);
        tablaDetalles.getTableHeader().setBackground(COLOR_FONDO);
        tablaDetalles.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        tablaDetalles.setDefaultRenderer(Object.class, new TableCellRenderer() {
            private final JTextArea textArea = new JTextArea();

            {
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setOpaque(true);
                textArea.setBorder(new EmptyBorder(10, 10, 10, 10));
                textArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value != null) {
                    textArea.setText(value.toString());
                } else {
                    textArea.setText("");
                }

                textArea.setBackground(table.getBackground());
                textArea.setForeground(table.getForeground());
                return textArea;
            }
        });

        JScrollPane scrollDetalles = new JScrollPane(tablaDetalles);
        scrollDetalles.setPreferredSize(new Dimension(830, 200));
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

        btnCancelar.addActionListener(e -> cancelarSolicitud());
        

        JButton btnEditar = crearBotonOscuro("Editar solicitud");
        btnEditar.setPreferredSize(new Dimension(160, 40));
        
        btnEditar.addActionListener(e -> editarSeleccionado());

        panel.add(btnCancelar);
        panel.add(btnEditar);

        return panel;
    }
    
    private void editarSeleccionado() {
        SolicitudDTO solicitudSeleccionada = obtenerSolicitudSeleccionada();
        if (solicitudSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un estudio para editar.",
                    "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }
        new EditarSolicitudFrame(usuarioActual, solicitudSeleccionada).setVisible(true);
        dispose();
    }

    private void cancelarSolicitud() {
        SolicitudDTO solicitudSeleccionada = obtenerSolicitudSeleccionada();
        if (solicitudSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una solicitud de la tabla para cancelarla.", "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro que deseas cancelar el Folio " + solicitudSeleccionada.getFolio() + "?", "Confirmar Cancelación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                solicitudNegocio.cancelarSolicitud(solicitudSeleccionada.getId());
                JOptionPane.showMessageDialog(this, "Solicitud cancelada con éxito.");
                cargarDatos(); 
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al cancelar", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private SolicitudDTO obtenerSolicitudSeleccionada() {
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            Boolean estaMarcado = (Boolean) modeloTabla.getValueAt(i, 0);
            if (Boolean.TRUE.equals(estaMarcado)) {
                return (SolicitudDTO) filasVisibles.get(i)[0];
            }
        }
        return null;
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
