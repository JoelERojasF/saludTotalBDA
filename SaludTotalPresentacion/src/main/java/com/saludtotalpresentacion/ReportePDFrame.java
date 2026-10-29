package com.saludtotalpresentacion;

import DTO.ClienteDTO;
import DTO.DoctorDTO;
import DTO.EmpresaDTO;
import DTO.ParametroDTO;
import DTO.RangoDTO;
import DTO.ResultadoDTO;
import DTO.SolicitudDTO;
import DTO.UsuarioDTO;
import Negocio.EmpresaNegocio;
import Negocio.FabricaNegocio;
import Negocio.ParametroNegocio;
import Negocio.RangoNegocio;
import Negocio.ResultadoNegocio;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.nio.file.Files;

public class ReportePDFrame extends JFrame {

    JLabel lblPaciente = new JLabel();
    JLabel lblEdad = new JLabel();
    JLabel lblSexo = new JLabel();
    JLabel lblTipoSangre = new JLabel();
    JLabel lblFolio = new JLabel();
    JLabel lblFecha = new JLabel();
    JLabel lblDoctor = new JLabel();
    JLabel lblEstudio = new JLabel("", SwingConstants.CENTER);

    JLabel lblDireccion = new JLabel("", SwingConstants.CENTER);
    JLabel lblTelefono = new JLabel("", SwingConstants.CENTER);
    JLabel lblCorreo = new JLabel("", SwingConstants.CENTER);
    JLabel lblRfc = new JLabel("", SwingConstants.CENTER);
    JLabel lblCedula = new JLabel("", SwingConstants.CENTER);

    private UsuarioDTO usuarioActual;

    DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"Determinacion", "Resultados", "Unidad", "Valores normales", "Observaciones"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };

    public ReportePDFrame(SolicitudDTO solicitud, UsuarioDTO usuario) {
        this.usuarioActual = usuario;
        setTitle("Salud Total - Hoja de resultados");
        setSize(800, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 20));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        Font titulo = new Font("Segoe UI", Font.BOLD, 20);
        Font datoEmpresa = new Font("Segoe UI", Font.BOLD, 13);

        JPanel titulos = new JPanel(new GridLayout(5, 1, 0, 4));
        JLabel t1 = new JLabel("Laboratorio Clinico Salud Total", SwingConstants.CENTER);
        JLabel t2 = new JLabel("Hoja de resultados", SwingConstants.CENTER);
        t1.setFont(titulo);
        t2.setFont(titulo);
        lblDireccion.setFont(datoEmpresa);
        lblTelefono.setFont(datoEmpresa);
        lblCorreo.setFont(datoEmpresa);
        titulos.add(t1);
        titulos.add(lblDireccion);
        titulos.add(lblTelefono);
        titulos.add(lblCorreo);
        titulos.add(t2);
        add(titulos, BorderLayout.NORTH);

        JPanel datos = new JPanel(new GridLayout(4, 4, 10, 10));
        datos.add(new JLabel("Paciente"));
        datos.add(lblPaciente);
        datos.add(new JLabel("Folio de la orden"));
        datos.add(lblFolio);
        datos.add(new JLabel("Edad"));
        datos.add(lblEdad);
        datos.add(new JLabel("Fecha de la orden"));
        datos.add(lblFecha);
        datos.add(new JLabel("Sexo"));
        datos.add(lblSexo);
        datos.add(new JLabel("Doctor solicitado"));
        datos.add(lblDoctor);
        datos.add(new JLabel("Tipo de sangre"));
        datos.add(lblTipoSangre);
        datos.add(new JLabel());
        datos.add(new JLabel());

        lblEstudio.setFont(titulo);
        JPanel abajo = new JPanel(new BorderLayout(0, 10));
        abajo.add(lblEstudio, BorderLayout.NORTH);
        abajo.add(new JScrollPane(new JTable(modelo)), BorderLayout.CENTER);

        JPanel centro = new JPanel(new BorderLayout(0, 20));
        centro.add(datos, BorderLayout.NORTH);
        centro.add(abajo, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);
        cargarEmpresa();
        cargarDatos(solicitud);

        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.addActionListener(e -> {
            new ReportesFrame(usuarioActual).setVisible(true);
            dispose();
        });
        JButton btnPdf = new JButton("Generar PDF");
        btnPdf.addActionListener(e -> generarPdf());

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnRegresar);
        panelBoton.add(btnPdf);

        lblRfc.setFont(datoEmpresa);
        lblCedula.setFont(datoEmpresa);
        JPanel pie = new JPanel();
        pie.setLayout(new BoxLayout(pie, BoxLayout.Y_AXIS));
        JPanel filaRfc = new JPanel(new BorderLayout());
        filaRfc.add(lblRfc, BorderLayout.CENTER);
        JPanel filaCedula = new JPanel(new BorderLayout());
        filaCedula.add(lblCedula, BorderLayout.CENTER);
        pie.add(filaRfc);
        pie.add(filaCedula);
        pie.add(panelBoton);
        add(pie, BorderLayout.SOUTH);
    }

    private void cargarEmpresa() {
        try {
            EmpresaDTO emp = new EmpresaNegocio().buscarPorId(1);
            lblDireccion.setText(emp.getDirecion());
            lblTelefono.setText(formatearTelefono(emp.getTelefono()));
            lblCorreo.setText(emp.getCorreo() == null ? "" : emp.getCorreo());
            lblRfc.setText(emp.getRfc());
            lblCedula.setText(emp.getCedula());
        } catch (Exception ex) {
            System.out.println("Error al cargar la empresa: " + ex.getMessage());
        }
    }

    private String formatearTelefono(String tel) {
        if (tel != null && tel.length() == 10) {
            return tel.substring(0, 3) + "-" + tel.substring(3, 6) + "-" + tel.substring(6);
        }
        return tel == null ? "" : tel;
    }

    private void cargarDatos(SolicitudDTO s) {
        try {
            ClienteDTO c = FabricaNegocio.crearClienteNegocio().buscarPorId(s.getIdCliente());

            String paterno = c.getApellidoPaterno() == null ? "" : " " + c.getApellidoPaterno();
            String materno = c.getApellidoMaterno() == null ? "" : " " + c.getApellidoMaterno();
            lblPaciente.setText(c.getNombre() + paterno + materno);
            lblSexo.setText(c.getSexo());
            lblTipoSangre.setText(c.getTipoSangre());

            int edad = Period.between(LocalDate.parse(c.getFechaNacimiento()), LocalDate.now()).getYears();
            lblEdad.setText(edad + " años");

            lblFolio.setText(s.getFolio());
            String f = s.getFechaHoraGeneracion().substring(0, 10);
            lblFecha.setText(f.substring(8, 10) + "/" + f.substring(5, 7) + "/" + f.substring(0, 4));

            if (s.getIdDoctor() == null) {
                lblDoctor.setText("Por cuenta propia");
            } else {
                DoctorDTO d = FabricaNegocio.crearDoctorNegocio().buscarPorId(s.getIdDoctor());
                String dPaterno = d.getApellidoPaterno() == null ? "" : " " + d.getApellidoPaterno();
                lblDoctor.setText(d.getNombre() + dPaterno);
            }

            lblEstudio.setText(FabricaNegocio.crearEstudioNegocio().buscarPorId(s.getIdEstudio()).getNombre());

            List<ResultadoDTO> resultados = new ResultadoNegocio().obtenerTodos();
            List<RangoDTO> rangos = new RangoNegocio().obtenerTodos();

            for (ParametroDTO p : new ParametroNegocio().obtenerTodos()) {
                if (!p.getIdEstudio().equals(s.getIdEstudio())) {
                    continue;
                }

                String valor = "-", obs = "-", rango = "-";
                for (ResultadoDTO r : resultados) {
                    if (r.getIdSolicitud().equals(s.getId()) && r.getIdParametro().equals(p.getId())) {
                        valor = r.getResultado();
                        obs = r.getObservacion().isEmpty() ? "-" : r.getObservacion();
                    }
                }
                for (RangoDTO r : rangos) {
                    if (r.getIdParametro().equals(p.getId()) && edad >= r.getEdadInicio() && edad <= r.getEdadFin()) {
                        rango = r.getInicio() + " - " + r.getFin();
                    }
                }
                modelo.addRow(new Object[]{p.getNombre(), valor, p.getUnidadMedida(), rango, obs});
            }

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void generarPdf() {
        try {
            File carpeta = Files.createTempDirectory("saludtotal").toFile();
            File archivo = new File(carpeta, lblFolio.getText() + ".pdf");

            Document doc = new Document();
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(archivo));

            final String rfc = lblRfc.getText();
            final String cedula = lblCedula.getText();
            writer.setPageEvent(new PdfPageEventHelper() {
                @Override
                public void onEndPage(PdfWriter w, Document d) {
                    PdfContentByte cb = w.getDirectContent();
                    com.lowagie.text.Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
                    float centro = (d.right() + d.left()) / 2;
                    ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                            new Phrase(rfc, fuente), centro, d.bottom() - 10, 0);
                    ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                            new Phrase(cedula, fuente), centro, d.bottom() - 25, 0);
                }
            });

            doc.open();

            Paragraph t1 = new Paragraph("Laboratorio Clinico Salud Total",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            t1.setAlignment(Element.ALIGN_CENTER);
            doc.add(t1);

            com.lowagie.text.Font fEmpresa = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
            for (String linea : new String[]{lblDireccion.getText(), lblTelefono.getText(), lblCorreo.getText()}) {
                Paragraph p = new Paragraph(linea, fEmpresa);
                p.setAlignment(Element.ALIGN_CENTER);
                doc.add(p);
            }

            Paragraph t2 = new Paragraph("Hoja de resultados",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
            t2.setAlignment(Element.ALIGN_CENTER);
            t2.setSpacingBefore(15);
            t2.setSpacingAfter(15);
            doc.add(t2);

            doc.add(new Paragraph("Paciente: " + lblPaciente.getText()));
            doc.add(new Paragraph("Edad: " + lblEdad.getText()));
            doc.add(new Paragraph("Sexo: " + lblSexo.getText()));
            doc.add(new Paragraph("Tipo de sangre: " + lblTipoSangre.getText()));
            doc.add(new Paragraph("Folio de la orden: " + lblFolio.getText()));
            doc.add(new Paragraph("Fecha de la orden: " + lblFecha.getText()));
            doc.add(new Paragraph("Doctor solicitado: " + lblDoctor.getText()));

            Paragraph estudio = new Paragraph(lblEstudio.getText(),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
            estudio.setAlignment(Element.ALIGN_CENTER);
            estudio.setSpacingBefore(15);
            estudio.setSpacingAfter(10);
            doc.add(estudio);

            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);
            for (int c = 0; c < modelo.getColumnCount(); c++) {
                tabla.addCell(modelo.getColumnName(c));
            }
            for (int f = 0; f < modelo.getRowCount(); f++) {
                for (int c = 0; c < modelo.getColumnCount(); c++) {
                    tabla.addCell(String.valueOf(modelo.getValueAt(f, c)));
                }
            }
            doc.add(tabla);
            doc.close();

            Desktop.getDesktop().browse(archivo.toURI());  
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al generar el PDF: " + ex.getMessage());
        }
    }
}