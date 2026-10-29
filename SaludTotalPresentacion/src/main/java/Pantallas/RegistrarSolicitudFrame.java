/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package Pantallas;

import DTO.ClienteDTO;
import DTO.DoctorDTO;
import DTO.EstudioDTO;
import DTO.UsuarioDTO;
import DTO.Crear.CrearSolicitudDTO;
import Exception.NegocioException;
import Negocio.FabricaNegocio;
import Negocio.Interfaces.IClienteNegocio;
import Negocio.Interfaces.IDoctorNegocio;
import Negocio.Interfaces.IEstudioNegocio;
import Negocio.Interfaces.ISolicitudNegocio;
import Negocio.SolicitudNegocio;
import Pantallas.LoginFrame.CampoTexto;
import static Pantallas.LoginFrame.COLOR_FONDO;
import static Pantallas.LoginFrame.COLOR_TEXTO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pirown
 */
public class RegistrarSolicitudFrame extends JFrame {

    private UsuarioDTO usuarioActual;

    private final IClienteNegocio clienteNegocio;
    private final IDoctorNegocio doctorNegocio;
    private final IEstudioNegocio estudioNegocio;
    private final ISolicitudNegocio solicitudNegocio;

    private List<ClienteDTO> listaClientes = new ArrayList<>();
    private List<DoctorDTO> listaDoctores = new ArrayList<>();

    private List<EstudioDTO> listaEstudios = new ArrayList<>();
    private List<EstudioDTO> estudiosVisibles = new ArrayList<>();

    private final Color COLOR_BOTON = new Color(51, 51, 51);
    private final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 22);

    private DefaultTableModel modeloAnalisis;

    private CampoTexto txtBuscarEstudio;
    private JComboBox<String> cmbCliente;
    private JComboBox<String> cmbDoctor;
    private JTextArea lblInfoCliente;
    private JTextArea lblInfoDoctor;
    private JRadioButton rbCuentaPropia;
    private JRadioButton rbDoctorSolicita;

    public RegistrarSolicitudFrame(UsuarioDTO usuario) {
        this.usuarioActual = usuario;
        this.clienteNegocio = FabricaNegocio.crearClienteNegocio();
        this.doctorNegocio = FabricaNegocio.crearDoctorNegocio();
        this.estudioNegocio = FabricaNegocio.crearEstudioNegocio();
        this.solicitudNegocio = new SolicitudNegocio();

        setTitle("Salud Total - Registrar Solicitud");
        setSize(900, 820);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(500, 650));

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(COLOR_FONDO);
        panelPrincipal.setBorder(new EmptyBorder(20, 30, 20, 30));

        panelPrincipal.add(crearBarraNavegacion());
        panelPrincipal.add(Box.createVerticalStrut(15));

        panelPrincipal.add(crearSeccionFormulario());
        panelPrincipal.add(Box.createVerticalStrut(15));

        panelPrincipal.add(crearBotonesInferiores());

        setContentPane(panelPrincipal);

        cargarDatosNegocio();
        actualizarEstadoDoctor();
    }

    private void cargarDatosNegocio() {
        try {
            listaClientes = clienteNegocio.obtenerTodos();
            listaDoctores = doctorNegocio.obtenerTodos();
            listaEstudios = estudioNegocio.obtenerTodos();

            cmbCliente.removeAllItems();
            cmbCliente.addItem("Seleccionar cliente...");
            for (ClienteDTO cliente : listaClientes) {
                cmbCliente.addItem(nombreCompletoCliente(cliente));
            }

            cmbDoctor.removeAllItems();
            cmbDoctor.addItem("Seleccionar doctor...");
            for (DoctorDTO doctor : listaDoctores) {
                cmbDoctor.addItem("DR. " + nombreCompletoDoctor(doctor));
            }

            filtrarEstudios();

        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "No se pudieron cargar los datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtrarEstudios() {
        String textoBuscado = txtBuscarEstudio.getText().trim().toLowerCase();
        if (textoBuscado.equals("buscar estudio...")) {
            textoBuscado = "";
        }

        estudiosVisibles.clear();
        modeloAnalisis.setRowCount(0);

        for (EstudioDTO estudio : listaEstudios) {
            if (estudio.getNombre().toLowerCase().contains(textoBuscado)) {
                estudiosVisibles.add(estudio);
                modeloAnalisis.addRow(new Object[]{false, estudio.getNombre() + " - " + estudio.getNombreTipoMuestra()});
            }
        }
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

    private JPanel crearBarraNavegacion() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 0));
        panel.setBackground(COLOR_FONDO);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        JButton btnEstudios = crearBotonOscuro("Estudios");
        btnEstudios.addActionListener(e -> {
            new EstudiosFrame(usuarioActual).setVisible(true);
            this.dispose();
        });

        JButton btnSolicitudes = crearBotonOscuro("Solicitudes");
        btnSolicitudes.addActionListener(e -> {
            new SolicitudesFrame(usuarioActual).setVisible(true);
            this.dispose();
        });

        JButton btnResultado = crearBotonOscuro("Resultado");
        btnResultado.addActionListener(e -> {
            new ResultadosFrame(usuarioActual).setVisible(true);
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

    private JPanel crearSeccionFormulario() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_FONDO);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel pnlCabecera = new JPanel(new BorderLayout());
        pnlCabecera.setBackground(COLOR_FONDO);
        pnlCabecera.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlCabecera.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        JLabel lblTitulo = new JLabel("Nueva Solicitud");
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_TEXTO);

        pnlCabecera.add(lblTitulo, BorderLayout.WEST);

        panel.add(pnlCabecera);
        panel.add(Box.createVerticalStrut(15));

        JPanel pnlBusquedas = new JPanel(new GridLayout(1, 2, 30, 0));
        pnlBusquedas.setBackground(COLOR_FONDO);
        pnlBusquedas.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlBusquedas.setPreferredSize(new Dimension(0, 200));
        pnlBusquedas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        JPanel pnlCliente = new JPanel();
        pnlCliente.setLayout(new BoxLayout(pnlCliente, BoxLayout.Y_AXIS));
        pnlCliente.setBackground(COLOR_FONDO);

        JLabel lblCliente = new JLabel("Cliente");
        lblCliente.setForeground(COLOR_TEXTO);
        lblCliente.setAlignmentX(Component.LEFT_ALIGNMENT);

        cmbCliente = new JComboBox<>(new String[]{"Seleccionar cliente..."});
        cmbCliente.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        cmbCliente.setPreferredSize(new Dimension(0, 35));
        cmbCliente.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblInfoCliente = crearAreaInfoTexto();

        cmbCliente.addActionListener(e -> {
            int index = cmbCliente.getSelectedIndex();
            if (index > 0 && index - 1 < listaClientes.size()) {
                ClienteDTO clienteSeleccionado = listaClientes.get(index - 1);
                String sexoVal = clienteSeleccionado.getSexo() != null ? clienteSeleccionado.getSexo() : "";
                String fechaVal = clienteSeleccionado.getFechaNacimiento() != null ? clienteSeleccionado.getFechaNacimiento() : "";
                String sangreVal = clienteSeleccionado.getTipoSangre() != null ? clienteSeleccionado.getTipoSangre() : "";

                lblInfoCliente.setText("nombre completo: " + nombreCompletoCliente(clienteSeleccionado) + "\n"
                        + "sexo: " + sexoVal + "\n"
                        + "fecha de nacimiento: " + fechaVal + "\n"
                        + "tipo de sangre: " + sangreVal);
            } else {
                lblInfoCliente.setText("nombre completo: \nsexo: \nfecha de nacimiento: \ntipo de sangre: ");
            }
        });

        lblInfoCliente.setText("nombre completo: \nsexo: \nfecha de nacimiento: \ntipo de sangre: ");

        JButton btnRegistrarCliente = crearBotonOscuro("Registrar cliente");
        btnRegistrarCliente.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnRegistrarCliente.addActionListener(e -> {
            RegistrarClienteFrame ventanaCliente = new RegistrarClienteFrame(usuarioActual);
            this.setEnabled(false);

            ventanaCliente.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    cargarDatosNegocio();
                    RegistrarSolicitudFrame.this.setEnabled(true);
                    RegistrarSolicitudFrame.this.toFront();
                }
            });

            ventanaCliente.setVisible(true);
        });

        pnlCliente.add(lblCliente);
        pnlCliente.add(Box.createVerticalStrut(5));
        pnlCliente.add(cmbCliente);
        pnlCliente.add(Box.createVerticalStrut(8));
        pnlCliente.add(lblInfoCliente);
        pnlCliente.add(Box.createVerticalStrut(8));
        pnlCliente.add(btnRegistrarCliente);

        JPanel pnlDoctor = new JPanel();
        pnlDoctor.setLayout(new BoxLayout(pnlDoctor, BoxLayout.Y_AXIS));
        pnlDoctor.setBackground(COLOR_FONDO);

        JPanel pnlRadios = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlRadios.setBackground(COLOR_FONDO);
        pnlRadios.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlRadios.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        rbCuentaPropia = new JRadioButton("Por cuenta propia");
        rbCuentaPropia.setBackground(COLOR_FONDO);
        rbCuentaPropia.setForeground(COLOR_TEXTO);

        rbDoctorSolicita = new JRadioButton("Doctor que solicita");
        rbDoctorSolicita.setBackground(COLOR_FONDO);
        rbDoctorSolicita.setForeground(COLOR_TEXTO);
        rbDoctorSolicita.setSelected(true);

        ButtonGroup grupoDoctor = new ButtonGroup();
        grupoDoctor.add(rbCuentaPropia);
        grupoDoctor.add(rbDoctorSolicita);

        pnlRadios.add(rbCuentaPropia);
        pnlRadios.add(Box.createHorizontalStrut(15));
        pnlRadios.add(rbDoctorSolicita);

        rbCuentaPropia.addActionListener(e -> actualizarEstadoDoctor());
        rbDoctorSolicita.addActionListener(e -> actualizarEstadoDoctor());

        cmbDoctor = new JComboBox<>(new String[]{"Seleccionar doctor..."});
        cmbDoctor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        cmbDoctor.setPreferredSize(new Dimension(0, 35));
        cmbDoctor.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblInfoDoctor = crearAreaInfoTexto();

        cmbDoctor.addActionListener(e -> {
            int index = cmbDoctor.getSelectedIndex();
            if (index > 0 && index - 1 < listaDoctores.size()) {
                DoctorDTO doctorSeleccionado = listaDoctores.get(index - 1);
                String sexoVal = doctorSeleccionado.getSexo() != null ? doctorSeleccionado.getSexo() : "";
                lblInfoDoctor.setText("nombre completo: DR. " + nombreCompletoDoctor(doctorSeleccionado) + "\n"
                        + "sexo: " + sexoVal);
            } else {
                lblInfoDoctor.setText("nombre completo: \nsexo: ");
            }
        });

        lblInfoDoctor.setText("nombre completo: \nsexo: ");

        pnlDoctor.add(pnlRadios);
        pnlDoctor.add(Box.createVerticalStrut(5));
        pnlDoctor.add(cmbDoctor);
        pnlDoctor.add(Box.createVerticalStrut(8));
        pnlDoctor.add(lblInfoDoctor);

        pnlBusquedas.add(pnlCliente);
        pnlBusquedas.add(pnlDoctor);

        panel.add(pnlBusquedas);
        panel.add(Box.createVerticalStrut(15));

        JLabel lblAnalisis = new JLabel("Analisis solicitados");
        lblAnalisis.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblAnalisis.setForeground(COLOR_TEXTO);
        lblAnalisis.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lblAnalisis);
        panel.add(Box.createVerticalStrut(8));

        panel.add(crearTablaAnalisis());

        return panel;
    }

    private JTextArea crearAreaInfoTexto() {
        JTextArea area = new JTextArea();
        area.setBackground(COLOR_FONDO);
        area.setForeground(COLOR_TEXTO);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        area.setEditable(false);
        area.setFocusable(false);
        area.setOpaque(true);
        area.setBorder(null);
        area.setAlignmentX(Component.LEFT_ALIGNMENT);
        return area;
    }

    private void actualizarEstadoDoctor() {
        boolean esDoctorSolicita = rbDoctorSolicita.isSelected();
        cmbDoctor.setEnabled(esDoctorSolicita);
        lblInfoDoctor.setEnabled(esDoctorSolicita);
        if (!esDoctorSolicita) {
            cmbDoctor.setSelectedIndex(0);
            lblInfoDoctor.setText("nombre completo: \nsexo: ");
        }
    }

    private JPanel crearTablaAnalisis() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(COLOR_FONDO);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtBuscarEstudio = new CampoTexto("Buscar estudio...");
        txtBuscarEstudio.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarEstudios();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarEstudios();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarEstudios();
            }
        });
        panel.add(txtBuscarEstudio, BorderLayout.NORTH);

        modeloAnalisis = new DefaultTableModel(new Object[]{"Sel", "Análisis solicitados"}, 0) {
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

        JTable tabla = new JTable(modeloAnalisis);
        tabla.setRowHeight(25);
        tabla.setShowGrid(false);
        tabla.setTableHeader(null);
        tabla.getColumnModel().getColumn(0).setMaxWidth(40);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(Color.WHITE);

        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearBotonesInferiores() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panel.setBackground(COLOR_FONDO);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(160, 40));

        btnCancelar.addActionListener(e -> {
            SolicitudesFrame solicitudesFrame = new SolicitudesFrame(usuarioActual);
            solicitudesFrame.setVisible(true);
            this.dispose();
        });

        JButton btnRegistrarSolicitud = crearBotonOscuro("Registrar solicitud");
        btnRegistrarSolicitud.setPreferredSize(new Dimension(160, 40));

        btnRegistrarSolicitud.addActionListener(e -> guardarSolicitud());

        panel.add(btnCancelar);
        panel.add(btnRegistrarSolicitud);

        return panel;
    }

    private void guardarSolicitud() {
        int clienteIdx = cmbCliente.getSelectedIndex();
        if (clienteIdx <= 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer idDoctor = null;
        if (rbDoctorSolicita.isSelected()) {
            int doctorIdx = cmbDoctor.getSelectedIndex();
            if (doctorIdx <= 0) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un doctor o marcar 'Por cuenta propia'.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            idDoctor = listaDoctores.get(doctorIdx - 1).getId();
        }

        List<EstudioDTO> estudiosSeleccionados = new ArrayList<>();
        for (int i = 0; i < modeloAnalisis.getRowCount(); i++) {
            Boolean seleccionado = (Boolean) modeloAnalisis.getValueAt(i, 0);
            if (seleccionado != null && seleccionado) {
                estudiosSeleccionados.add(estudiosVisibles.get(i));
            }
        }

        if (estudiosSeleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar al menos un análisis de la lista.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ClienteDTO clienteSeleccionado = listaClientes.get(clienteIdx - 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaHoraActual = sdf.format(new Date());

        try {
            for (EstudioDTO estudio : estudiosSeleccionados) {
                String folioGenerado = "LAB-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

                CrearSolicitudDTO dto = new CrearSolicitudDTO(
                        folioGenerado,
                        fechaHoraActual,
                        clienteSeleccionado.getId(),
                        idDoctor,
                        estudio.getId()
                );

                solicitudNegocio.registrar(dto);
            }

            JOptionPane.showMessageDialog(this, "Solicitud(es) registrada(s) con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            new SolicitudesFrame(usuarioActual).setVisible(true);
            this.dispose();

        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error: " + ex.getMessage(), "Error al Registrar", JOptionPane.ERROR_MESSAGE);
        }
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