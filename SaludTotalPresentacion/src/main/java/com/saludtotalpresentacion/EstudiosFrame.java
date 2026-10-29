package com.saludtotalpresentacion;

import DTO.EstudioDTO;
import DTO.UsuarioDTO;
import Negocio.FabricaNegocio;
import Negocio.Interfaces.IEstudioNegocio;
import com.saludtotalpresentacion.LoginFrame.BotonRedondeado;
import static com.saludtotalpresentacion.LoginFrame.COLOR_FONDO;
import static com.saludtotalpresentacion.LoginFrame.COLOR_TEXTO;
import com.saludtotalpresentacion.LoginFrame.CampoTexto;
import static com.saludtotalpresentacion.LoginFrame.FUENTE;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import java.util.ArrayList;
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

public class EstudiosFrame extends JFrame {

    private final Font FUENTE_TITULO = new Font(FUENTE, Font.BOLD, 22);
    private final Font FUENTE_NORMAL = new Font(FUENTE, Font.PLAIN, 14);

    private final UsuarioDTO usuarioActual;

    private final List<String[]> todos = new ArrayList<>();
    private final List<String[]> visibles = new ArrayList<>();
    private boolean cargando = false;
    private String[] seleccionado; 

    private final CampoTexto txtBuscar = new CampoTexto("buscar estudio");

    private final DefaultTableModel modeloLista = new DefaultTableModel(new Object[]{"Sel", "Estudio"}, 0) {
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
            if (column == 0) {
                return true;
            } else {
                return false;
            }
        }
    };

    private final DefaultTableModel modeloDetalle = new DefaultTableModel(
            new Object[]{"nombre", "descripcion", "muestra", "parametro"}, 0) {
        @Override
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };

    public EstudiosFrame(UsuarioDTO usuario) {
        this.usuarioActual = usuario;

        setTitle("Salud Total - Estudio");
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

        JLabel lblTitulo = new JLabel("Estudio");
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(Box.createVerticalStrut(15));

        txtBuscar.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtBuscar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        panelPrincipal.add(txtBuscar);
        panelPrincipal.add(Box.createVerticalStrut(20));

        panelPrincipal.add(crearListaEstudios());
        panelPrincipal.add(Box.createVerticalStrut(10));

        JButton btnAgregar = crearBoton("Agregar Estudio");
        panelPrincipal.add(crearFilaDerecha(btnAgregar));
        panelPrincipal.add(Box.createVerticalStrut(15));

        panelPrincipal.add(crearDetalle());
        panelPrincipal.add(Box.createVerticalGlue());

        JButton btnEditar = crearBoton("Editar Estudio");
        panelPrincipal.add(crearFilaDerecha(btnEditar));

        btnAgregar.addActionListener(e -> {
            new RegistrarEstudioFrame(usuarioActual).setVisible(true);
            dispose();
        });
        btnEditar.addActionListener(e -> editarSeleccionado());

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

        cargarEstudios();
        setContentPane(panelPrincipal);
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

    private JScrollPane crearListaEstudios() {
        JTable tabla = new JTable(modeloLista);
        tabla.setTableHeader(null);
        tabla.setRowHeight(24);
        tabla.setShowGrid(false);
        tabla.setFont(FUENTE_NORMAL);
        tabla.setForeground(COLOR_TEXTO);
        tabla.getColumnModel().getColumn(0).setMaxWidth(40);

        modeloLista.addTableModelListener(e -> {
            if (cargando || e.getType() != TableModelEvent.UPDATE || e.getColumn() != 0) {
                return;
            }
            alSeleccionar(e.getFirstRow());
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        scroll.setPreferredSize(new Dimension(830, 190));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 190));
        scroll.getViewport().setBackground(Color.WHITE);
        return scroll;
    }

    private JScrollPane crearDetalle() {
        JTable tabla = new JTable(modeloDetalle);
        tabla.setRowHeight(75);
        tabla.setShowGrid(false);
        tabla.setBackground(COLOR_FONDO);
        tabla.setFont(FUENTE_NORMAL);
        tabla.setForeground(COLOR_TEXTO);

        tabla.getColumnModel().getColumn(1).setCellRenderer(new AreaRenderer());
        int[] anchos = {150, 300, 90, 140};
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
        scroll.setPreferredSize(new Dimension(830, 125));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 125));
        scroll.getViewport().setBackground(COLOR_FONDO);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        return scroll;
    }

    private JPanel crearFilaDerecha(JButton boton) {
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
        boton.setPreferredSize(new Dimension(140, 40));
        return boton;
    }

    private void cargarEstudios() {
        try {
            IEstudioNegocio estudioNegocio = FabricaNegocio.crearEstudioNegocio();
            List<EstudioDTO> estudiosBD = estudioNegocio.obtenerTodos();
            List<String[]> datosParaTabla = new ArrayList<>();

            for (EstudioDTO est : estudiosBD) {
                String nombre = est.getNombre();
                String descripcion = "\n" + est.getDescripcion();
                String muestra = est.getNombreTipoMuestra();
                String parametro = String.join(", ", est.getNombresParametros());

                datosParaTabla.add(new String[]{nombre, descripcion, muestra, parametro, String.valueOf(est.getId())});
            }
            setEstudios(datosParaTabla);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los estudios: " + ex.getMessage(),
                    "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        }
    }

  
    public void setEstudios(List<String[]> estudios) {
        todos.clear();
        todos.addAll(estudios);
        filtrar();
    }

    private void filtrar() {
        String t = txtBuscar.getText().trim().toLowerCase();
        cargando = true;
        modeloLista.setRowCount(0);
        modeloDetalle.setRowCount(0);
        visibles.clear();
        seleccionado = null;
        for (String[] s : todos) {
            if (s[0].toLowerCase().contains(t)) {
                visibles.add(s);
                modeloLista.addRow(new Object[]{false, s[0] + " - Muestra: " + s[2]});
            }
        }
        cargando = false;
    }


    private void alSeleccionar(int fila) {
        boolean marcado = (Boolean) modeloLista.getValueAt(fila, 0);
        cargando = true;
        for (int i = 0; i < modeloLista.getRowCount(); i++) {
            if (i != fila) {
                modeloLista.setValueAt(false, i, 0);
            }
        }
        cargando = false;

        modeloDetalle.setRowCount(0);
        seleccionado = marcado ? visibles.get(fila) : null;
        if (marcado) {
           modeloDetalle.addRow(java.util.Arrays.copyOf(visibles.get(fila), 4));
        }
    }

    private void editarSeleccionado() {
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un estudio para editar.",
                    "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }
        new EditarEstudioFrame(usuarioActual, Integer.valueOf(seleccionado[4])).setVisible(true);
        dispose();
    }

   
    private static class AreaRenderer extends JTextArea implements TableCellRenderer {

        AreaRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
            setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
        }

        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean sel,
                boolean foco, int row, int col) {
            setText(v == null ? "" : v.toString());
            setFont(t.getFont());
            setForeground(t.getForeground());
            setBackground(t.getBackground());
            return this;
        }
    }

}
