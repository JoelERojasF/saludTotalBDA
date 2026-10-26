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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
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

    private List<ClienteDTO> listaClientes = new ArrayList<>();
    private List<DoctorDTO> listaDoctores = new ArrayList<>();

    private final Color COLOR_BOTON = new Color(51, 51, 51);
    private final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 22);

    private DefaultTableModel modeloTabla;
    private DefaultTableModel modeloDetalles;

    private boolean ajustandoSeleccion = false;

    public SolicitudesFrame(UsuarioDTO usuario) {
        this.usuarioActual = usuario;
        this.clienteNegocio = FabricaNegocio.crearClienteNegocio();
        this.doctorNegocio = FabricaNegocio.crearDoctorNegocio();

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
        modeloTabla.setRowCount(0);
        modeloDetalles.setRowCount(0);
        try {
            listaClientes = clienteNegocio.obtenerTodos();
            listaDoctores = doctorNegocio.obtenerTodos();

            int limite = Math.max(listaClientes.size(), listaDoctores.size());

            for (int i = 0; i < limite; i++) {
                String nombrePaciente = "";
                if (i < listaClientes.size()) {
                    nombrePaciente = nombreCompleto(listaClientes.get(i));
                }

                String nombreDoctor = "";
                if (i < listaDoctores.size()) {
                    nombreDoctor = "DR. " + nombreCompletoDoctor(listaDoctores.get(i));
                }

                String vacio = "";

                modeloTabla.addRow(new Object[]{
                    false,
                    nombrePaciente,
                    vacio,
                    nombreDoctor
                });
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "No se pudieron cargar los registros", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarDetallesFila(int fila) {
        modeloDetalles.setRowCount(0);

        if (fila < 0 || fila >= modeloTabla.getRowCount()) {
            return;
        }

        ClienteDTO cliente = null;
        if (fila < listaClientes.size()) {
            cliente = listaClientes.get(fila);
        }

        DoctorDTO doctor = null;
        if (fila < listaDoctores.size()) {
            doctor = listaDoctores.get(fila);
        }

        String textoPaciente = "";
        if (cliente != null) {
            String sexo = "";
            if (cliente.getSexo() != null) {
                sexo = cliente.getSexo();
            }

            String fechaNacimiento = "";
            if (cliente.getFechaNacimiento() != null) {
                fechaNacimiento = cliente.getFechaNacimiento();
            }

            String tipoSangre = "";
            if (cliente.getTipoSangre() != null) {
                tipoSangre = cliente.getTipoSangre();
            }

            textoPaciente = nombreCompleto(cliente) + "\n\n"
                    + "Sexo: " + sexo + "\n\n"
                    + "Fecha nacimiento:\n" + fechaNacimiento + "\n\n"
                    + "Tipo Sangre: " + tipoSangre;
        }

        String textoDoctor = "";
        if (doctor != null) {
            String sexo = "";
            if (doctor.getSexo() != null) {
                sexo = doctor.getSexo();
            }

            textoDoctor = "DR. " + nombreCompletoDoctor(doctor) + "\n\n"
                    + "Sexo: " + sexo;
        }

        String solicitud = (String) modeloTabla.getValueAt(fila, 2);
        String resultado = "";

        modeloDetalles.addRow(new Object[]{
            textoPaciente,
            textoDoctor,
            solicitud,
            resultado
        });
    }

    private String nombreCompleto(ClienteDTO c) {
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

        modeloTabla = new DefaultTableModel(
                new Object[]{"Sel", "Paciente", "Solicitud", "Doctor"}, 0) {
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
                    boolean algunaSeleccionada = false;
                    for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                        if (Boolean.TRUE.equals(modeloTabla.getValueAt(i, 0))) {
                            algunaSeleccionada = true;
                            mostrarDetallesFila(i);
                            break;
                        }
                    }
                    if (!algunaSeleccionada) {
                        modeloDetalles.setRowCount(0);
                    }
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

        String[] columnas = {"Paciente", "Doctor", "Solicitud", "Resultado"};

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