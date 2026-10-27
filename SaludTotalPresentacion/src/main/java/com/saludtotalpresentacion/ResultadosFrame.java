package com.saludtotalpresentacion;

import DTO.UsuarioDTO;
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
import javax.swing.table.DefaultTableModel;


public class ResultadosFrame extends JFrame{
    
    private final Font FUENTE_TITULO = new Font(FUENTE, Font.BOLD, 22);
    private final Font FUENTE_NORMAL = new Font(FUENTE, Font.PLAIN, 14);
    private final Font FUENTE_CHICA = new Font(FUENTE, Font.PLAIN, 12);
 
    private final UsuarioDTO usuarioActual;
 
    // Cada solicitud: {folio, paciente, edad, doctor, estudio, parametro, unidad, rango}
    private final List<String[]> todas = new ArrayList<>();
    private final List<String[]> visibles = new ArrayList<>();
    private String[] seleccionada;      // solicitud marcada en la lista
    private boolean cargando = false;
 
    private final CampoTexto txtFolio = new CampoTexto("ej.LAB-000248");
 
    private final JLabel lblCliente = new JLabel("", SwingConstants.CENTER);
    private final JLabel lblEdad = new JLabel("", SwingConstants.CENTER);
    private final JLabel lblDoctor = new JLabel("", SwingConstants.CENTER);
    private final JLabel lblEstudio = new JLabel("", SwingConstants.CENTER);
 
    // Lista de solicitudes con checkbox
    private final DefaultTableModel modeloLista = new DefaultTableModel(new Object[]{"Sel", "Solicitud"}, 0) {
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
 
    // Tabla de parametros: solo se pueden editar Resultado (1) y Observacion (4)
    private final DefaultTableModel modeloParametros = new DefaultTableModel(
            new Object[]{"Parametro", "Resultado", "Unidad", "Rango Normal", "Observacion"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            if (column == 1 || column == 4) {
                return true;
            } else {
                return false;
            }
        }
    };
 
    private JTable tablaParametros;
 
    public ResultadosFrame(UsuarioDTO usuario) {
        this.usuarioActual = usuario;
 
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
 
        cargarDatosDemo(); // TODO: reemplazar por setSolicitudes(...) con datos reales
        setContentPane(panelPrincipal);
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
        // ya estamos en Resultado
        // TODO: btnReportes cuando exista ese frame
 
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
 
        JButton btnBuscar = crearBoton("Buscar folio");
        btnBuscar.setPreferredSize(new Dimension(130, 42));
        btnBuscar.addActionListener(e -> buscar());
        txtFolio.addActionListener(e -> buscar()); // Enter tambien busca
 
        panel.add(txtFolio, BorderLayout.CENTER);
        panel.add(btnBuscar, BorderLayout.EAST);
        return panel;
    }
 
    private JScrollPane crearListaSolicitudes() {
        JTable tabla = new JTable(modeloLista);
        tabla.setTableHeader(null);
        tabla.setRowHeight(24);
        tabla.setShowGrid(false);
        tabla.setFont(FUENTE_NORMAL);
        tabla.setForeground(COLOR_TEXTO);
        tabla.getColumnModel().getColumn(0).setMaxWidth(40);
 
        // cuando se marca o desmarca un checkbox
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
        return scroll;
    }
 
    /** Fila con Cliente, Edad, Doctor y Estudio (etiqueta gris arriba, dato abajo). */
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
    // Logica de pantalla
    // ------------------------------------------------------------------
    /** Carga las solicitudes: {folio, paciente, edad, doctor, estudio, parametro, unidad, rango}. */
    public void setSolicitudes(List<String[]> solicitudes) {
        todas.clear();
        todas.addAll(solicitudes);
        buscar();
    }
 
    /** Muestra solo las solicitudes cuyo folio contiene el texto escrito. */
    private void buscar() {
        String texto = txtFolio.getText().trim().toLowerCase();
 
        cargando = true;
        modeloLista.setRowCount(0);
        visibles.clear();
        for (String[] s : todas) {
            if (s[0].toLowerCase().contains(texto)) {
                visibles.add(s);
                modeloLista.addRow(new Object[]{false, s[1] + " - " + s[4] + " - " + s[3]});
            }
        }
        cargando = false;
 
        limpiarDetalle();
    }
 
    /** Solo una solicitud marcada a la vez; muestra sus datos abajo. */
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
            seleccionada = visibles.get(fila);
            lblCliente.setText(seleccionada[1]);
            lblEdad.setText(seleccionada[2]);
            lblDoctor.setText(seleccionada[3]);
            lblEstudio.setText(seleccionada[4]);
            // TODO: si el estudio tiene varios parametros, agregar una fila por cada uno
            modeloParametros.addRow(new Object[]{
                "1 " + seleccionada[5], "", seleccionada[6], seleccionada[7], ""});
        }
    }
 
    private void limpiarDetalle() {
        if (tablaParametros != null && tablaParametros.isEditing()) {
            tablaParametros.getCellEditor().cancelCellEditing();
        }
        seleccionada = null;
        lblCliente.setText("");
        lblEdad.setText("");
        lblDoctor.setText("");
        lblEstudio.setText("");
        modeloParametros.setRowCount(0);
    }
 
    private void limpiar() {
        txtFolio.setText("");
        buscar();
    }
 
    private void guardar() {
        if (seleccionada == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una solicitud.",
                    "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        // si el usuario estaba escribiendo en una celda, se confirma lo escrito
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
 
        // TODO: construir el DTO (folio, resultados, observaciones) y llamar a la capa de negocio
        JOptionPane.showMessageDialog(this, "Resultado guardado correctamente.");
        limpiar();
    }
 
    private void cargarDatosDemo() {
        List<String[]> d = new ArrayList<>();
        d.add(new String[]{"LAB-000245", "Daniela López Valenzuela", "31 años", "a quien corresponda", "Biometría hemática", "Hemoglobina", "g/dL", "12.0-15.5"});
        d.add(new String[]{"LAB-000248", "Juan Lopez Perez", "26 años", "DR. Ricardo Salas", "Química sanguínea", "Hemoglobina", "g/dL", "12.0-15.5"});
        d.add(new String[]{"LAB-000250", "Carlos Alberto Méndez Ibarra", "45 años", "DR. Arturo Zamora", "Perfil lipídico", "Colesterol total", "mg/dL", "0-200"});
        d.add(new String[]{"LAB-000251", "Fernanda Ruiz Cota", "29 años", "DRA. Alejandra Paredes", "Examen general de orina", "pH", "-", "4.6-8.0"});
        d.add(new String[]{"LAB-000252", "José Manuel Navarro Acosta", "52 años", "DR. Roberto Aguilar", "Coprocultivo", "Bacterias", "-", "Negativo"});
        d.add(new String[]{"LAB-000253", "Andrea Torres Camacho", "38 años", "DRA. Mariana Ochoa", "Biopsia", "Celulas", "-", "Normal"});
        d.add(new String[]{"LAB-000254", "Miguel Ángel Salazar Duarte", "34 años", "DR. Sergio Castro", "hisopado nasal", "Antigeno", "-", "Negativo"});
        d.add(new String[]{"LAB-000255", "Valeria Espinoza Romero", "41 años", "DRA. Karla Mendoza", "Análisis de líquido cefalorraquídeo", "Proteinas", "mg/dL", "15-45"});
        setSolicitudes(d);
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
