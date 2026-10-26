/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.saludtotalpresentacion;

import DTO.ClienteDTO;
import DTO.DoctorDTO;
import DTO.UsuarioDTO;
import Exception.NegocioException;
import Negocio.FabricaNegocio;
import Negocio.Interfaces.IClienteNegocio;
import Negocio.Interfaces.IDoctorNegocio;
import static com.saludtotalpresentacion.LoginFrame.COLOR_FONDO;
import static com.saludtotalpresentacion.LoginFrame.COLOR_TEXTO;
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
import java.util.ArrayList;
import java.util.List;
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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pirown
 */
public class RegistrarSolicitudFrame extends JFrame {

    private UsuarioDTO usuarioActual;

    private final IClienteNegocio clienteNegocio;
    private final IDoctorNegocio doctorNegocio;

    private List<ClienteDTO> listaClientes = new ArrayList<>();
    private List<DoctorDTO> listaDoctores = new ArrayList<>();

    private final Color COLOR_BOTON = new Color(51, 51, 51);
    private final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 22);

    private DefaultTableModel modeloAnalisis;

    private JComboBox<String> cmbCliente;
    private JComboBox<String> cmbDoctor;
    private JLabel lblInfoCliente;
    private JLabel lblInfoDoctor;
    private JRadioButton rbCuentaPropia;
    private JRadioButton rbDoctorSolicita;

    public RegistrarSolicitudFrame(UsuarioDTO usuario) {
        this.usuarioActual = usuario;
        this.clienteNegocio = FabricaNegocio.crearClienteNegocio();
        this.doctorNegocio = FabricaNegocio.crearDoctorNegocio();

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

        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "No se pudieron cargar los datos", JOptionPane.ERROR_MESSAGE);
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

        panel.add(crearBotonOscuro("Estudios"));
        panel.add(crearBotonOscuro("Solicitudes"));
        panel.add(crearBotonOscuro("Resultado"));
        panel.add(crearBotonOscuro("Reportes"));

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

        // Columna Cliente
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

        cmbCliente.addActionListener(e -> {
            int index = cmbCliente.getSelectedIndex();
            if (index > 0 && index - 1 < listaClientes.size()) {
                ClienteDTO clienteSeleccionado = listaClientes.get(index - 1);
                String sexoVal = "";
                if (clienteSeleccionado.getSexo() != null) {
                    sexoVal = clienteSeleccionado.getSexo();
                }
                String fechaVal = "";
                if (clienteSeleccionado.getFechaNacimiento() != null) {
                    fechaVal = clienteSeleccionado.getFechaNacimiento();
                }
                String sangreVal = "";
                if (clienteSeleccionado.getTipoSangre() != null) {
                    sangreVal = clienteSeleccionado.getTipoSangre();
                }
                
                lblInfoCliente.setText("<html>nombre completo: " + nombreCompletoCliente(clienteSeleccionado)
                        + "<br>sexo: " + sexoVal
                        + "<br>fecha de nacimiento: " + fechaVal
                        + "<br>tipo de sangre: " + sangreVal + "</html>");
            } else {
                lblInfoCliente.setText("<html>nombre completo: <br>sexo: <br>fecha de nacimiento: <br>tipo de sangre: </html>");
            }
        });

        lblInfoCliente = new JLabel("<html>nombre completo: <br>sexo: <br>fecha de nacimiento: <br>tipo de sangre: </html>");
        lblInfoCliente.setForeground(COLOR_TEXTO);
        lblInfoCliente.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnRegistrarCliente = crearBotonOscuro("Registrar cliente");
        btnRegistrarCliente.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        btnRegistrarCliente.addActionListener(e -> {
            RegistrarClienteFrame ventanaCliente = new RegistrarClienteFrame(usuarioActual);
            // Cuando la ventana de cliente se cierre, recargamos los datos para que aparezca el nuevo cliente
            ventanaCliente.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    cargarDatosNegocio();
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

        // Columna Doctor
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

        cmbDoctor.addActionListener(e -> {
            int index = cmbDoctor.getSelectedIndex();
            if (index > 0 && index - 1 < listaDoctores.size()) {
                DoctorDTO doctorSeleccionado = listaDoctores.get(index - 1);
                String sexoVal = "";
                if (doctorSeleccionado.getSexo() != null) {
                    sexoVal = doctorSeleccionado.getSexo();
                }
                
                lblInfoDoctor.setText("<html>nombre completo: DR. " + nombreCompletoDoctor(doctorSeleccionado)
                        + "<br>sexo: " + sexoVal + "</html>");
            } else {
                lblInfoDoctor.setText("<html>nombre completo: <br>sexo: </html>");
            }
        });

        lblInfoDoctor = new JLabel("<html>nombre completo: <br>sexo: </html>");
        lblInfoDoctor.setForeground(COLOR_TEXTO);
        lblInfoDoctor.setAlignmentX(Component.LEFT_ALIGNMENT);

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

    private void actualizarEstadoDoctor() {
        boolean esDoctorSolicita = rbDoctorSolicita.isSelected();
        cmbDoctor.setEnabled(esDoctorSolicita);
        lblInfoDoctor.setEnabled(esDoctorSolicita);
        if (!esDoctorSolicita) {
            cmbDoctor.setSelectedIndex(0);
            lblInfoDoctor.setText("<html>nombre completo: <br>sexo: </html>");
        }
    }

    private JPanel crearTablaAnalisis() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_FONDO);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

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

        panel.add(btnCancelar);
        panel.add(btnRegistrarSolicitud);

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