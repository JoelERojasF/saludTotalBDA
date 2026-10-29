package com.saludtotalpresentacion;

import DTO.ClienteDTO;
import DTO.EstudioDTO;
import DTO.ResultadoDTO;
import DTO.SolicitudDTO;
import DTO.UsuarioDTO;
import Negocio.FabricaNegocio;
import Negocio.ResultadoNegocio;
import Negocio.SolicitudNegocio;
import static com.saludtotalpresentacion.LoginFrame.COLOR_BOTON;
import static com.saludtotalpresentacion.LoginFrame.COLOR_FONDO;
import static com.saludtotalpresentacion.LoginFrame.COLOR_TEXTO;
import com.saludtotalpresentacion.LoginFrame.CampoTexto;
import static com.saludtotalpresentacion.LoginFrame.FUENTE;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ReportesFrame extends JFrame {

    private final Font FUENTE_TITULO = new Font(FUENTE, Font.BOLD, 22);
    private final Font FUENTE_NORMAL = new Font(FUENTE, Font.PLAIN, 14);

    private final UsuarioDTO usuarioActual;

    private final CampoTexto txtBuscarFolio = new CampoTexto("ej.LAB-000248");

    private final JPanel panelLista = new JPanel();

    private final List<Object[]> todasLasFilas = new ArrayList<>();

    public ReportesFrame(UsuarioDTO usuario) {
        this.usuarioActual = usuario;

        setTitle("Salud Total - Reportes");
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

        JLabel lblTitulo = new JLabel("Emision de reportes");
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(Box.createVerticalStrut(20));

        panelPrincipal.add(crearFilaBusqueda());
        panelPrincipal.add(Box.createVerticalStrut(20));

        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
        panelLista.setBackground(COLOR_FONDO);
        JScrollPane scroll = new JScrollPane(panelLista);
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        scroll.setBorder(null);
        panelPrincipal.add(scroll);

        setContentPane(panelPrincipal);
        cargarSolicitudes();
    }

    private void cargarSolicitudes() {
        try {
            List<ResultadoDTO> resultados = new ResultadoNegocio().obtenerTodos();

            todasLasFilas.clear();
            for (SolicitudDTO s : new SolicitudNegocio().obtenerTodosActivos()) {
                boolean tieneResultado = false;
                for (ResultadoDTO r : resultados) {
                    if (r.getIdSolicitud().equals(s.getId())) {
                        tieneResultado = true;
                    }
                }

                if (!tieneResultado) {
                    continue; 
                }
                ClienteDTO c = FabricaNegocio.crearClienteNegocio().buscarPorId(s.getIdCliente());
                EstudioDTO e = FabricaNegocio.crearEstudioNegocio().buscarPorId(s.getIdEstudio());

                String apellido = c.getApellidoPaterno() == null ? "" : c.getApellidoPaterno();
                String cliente = c.getNombre() + " " + apellido;

                String f = s.getFechaHoraGeneracion().substring(0, 10);
                String fecha = f.substring(8, 10) + "/" + f.substring(5, 7) + "/" + f.substring(0, 4);

                todasLasFilas.add(new Object[]{s, cliente, fecha, e.getNombreTipoMuestra()});
            }
            filtrar();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void filtrar() {
        String texto = txtBuscarFolio.getText().trim().toLowerCase();

        panelLista.removeAll();
        for (Object[] fila : todasLasFilas) {
            SolicitudDTO s = (SolicitudDTO) fila[0];
            String cliente = (String) fila[1];
            String folio = s.getFolio() == null ? "" : s.getFolio().toLowerCase();

            if (texto.isEmpty() || folio.contains(texto) || cliente.toLowerCase().contains(texto)) {
                panelLista.add(crearFila(s, cliente, (String) fila[2], (String) fila[3]));
            }
        }
        panelLista.revalidate();
        panelLista.repaint();
    }

    private JPanel crearFila(SolicitudDTO s, String cliente, String fecha, String tipoMuestra) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        fila.setBackground(COLOR_FONDO);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel lblCliente = new JLabel(cliente);
        lblCliente.setFont(FUENTE_NORMAL);
        lblCliente.setPreferredSize(new Dimension(280, 30));

        JLabel lblFecha = new JLabel(fecha);
        lblFecha.setFont(FUENTE_NORMAL);
        lblFecha.setPreferredSize(new Dimension(110, 30));

        JLabel lblMuestra = new JLabel(tipoMuestra);
        lblMuestra.setFont(FUENTE_NORMAL);
        lblMuestra.setPreferredSize(new Dimension(180, 30));

        JButton btnGenerar = new JButton("Generar Reporte PDF");
        btnGenerar.addActionListener(e -> {
            new ReportePDFrame(s, usuarioActual).setVisible(true);
            this.dispose();
        });
        btnGenerar.setFont(FUENTE_NORMAL);
        btnGenerar.setForeground(Color.WHITE);
        btnGenerar.setBackground(new Color(0xE53935));
        btnGenerar.setOpaque(true);
        btnGenerar.setBorderPainted(false);
        btnGenerar.setFocusPainted(false);

        fila.add(lblCliente);
        fila.add(lblFecha);
        fila.add(lblMuestra);
        fila.add(btnGenerar);
        return fila;
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

    private JPanel crearFilaBusqueda() {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        fila.setBackground(COLOR_FONDO);
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        txtBuscarFolio.setPreferredSize(new Dimension(300, 42));
        txtBuscarFolio.getDocument().addDocumentListener(new DocumentListener() {
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
        txtBuscarFolio.addActionListener(e -> filtrar());
        fila.add(txtBuscarFolio);

       
        return fila;
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