package com.saludtotalpresentacion;

import DTO.ClienteDTO;
import DTO.DoctorDTO;
import DTO.SolicitudDTO;
import Negocio.FabricaNegocio;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.Period;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;


public class ReportePDFrame extends JFrame{
    
    // Valores que despues se llenan con setText(...)
    JLabel lblPaciente = new JLabel();
    JLabel lblEdad = new JLabel();
    JLabel lblSexo = new JLabel();
    JLabel lblTipoSangre = new JLabel();
    JLabel lblFolio = new JLabel();
    JLabel lblFecha = new JLabel();
    JLabel lblDoctor = new JLabel();
    JLabel lblEstudio = new JLabel("", SwingConstants.CENTER);
 
    // Tabla vacia, solo con los encabezados
    DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"Determinacion", "Resultados", "Unidad", "Valores normales", "Observaciones"}, 0);
 
    public ReportePDFrame(SolicitudDTO solicitud) {
        setTitle("Salud Total - Hoja de resultados");
        setSize(800, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 20));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
 
        Font titulo = new Font("Segoe UI", Font.BOLD, 20);
 
        // Titulos de arriba
        JPanel titulos = new JPanel(new GridLayout(2, 1, 0, 20));
        JLabel t1 = new JLabel("Laboratorio Clinico Salud Total", SwingConstants.CENTER);
        JLabel t2 = new JLabel("Hoja de resultados", SwingConstants.CENTER);
        t1.setFont(titulo);
        t2.setFont(titulo);
        titulos.add(t1);
        titulos.add(t2);
        add(titulos, BorderLayout.NORTH);
 
        // Datos del paciente
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
 
        // Nombre del estudio + tabla
        lblEstudio.setFont(titulo);
        JPanel abajo = new JPanel(new BorderLayout(0, 10));
        abajo.add(lblEstudio, BorderLayout.NORTH);
        abajo.add(new JScrollPane(new JTable(modelo)), BorderLayout.CENTER);
 
        JPanel centro = new JPanel(new BorderLayout(0, 20));
        centro.add(datos, BorderLayout.NORTH);
        centro.add(abajo, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);
        cargarDatos(solicitud);
    }
    
    private void cargarDatos(SolicitudDTO s) {
    try {
        ClienteDTO c = FabricaNegocio.crearClienteNegocio().buscarPorId(s.getIdCliente());

        lblPaciente.setText(c.getNombre() + " " + c.getApellidoPaterno() + " " + c.getApellidoMaterno());
        lblSexo.setText(c.getSexo());
        lblTipoSangre.setText(c.getTipoSangre());

        // Edad: de la fecha de nacimiento a hoy
        int edad = Period.between(LocalDate.parse(c.getFechaNacimiento()), LocalDate.now()).getYears();
        lblEdad.setText(edad + " años");

        // Datos de la orden
        lblFolio.setText(s.getFolio());
        String f = s.getFechaHoraGeneracion().substring(0, 10);
        lblFecha.setText(f.substring(8, 10) + "/" + f.substring(5, 7) + "/" + f.substring(0, 4));

        // Doctor (puede no haber)
        if (s.getIdDoctor() == null) {
            lblDoctor.setText("Por cuenta propia");
        } else {
            DoctorDTO d = FabricaNegocio.crearDoctorNegocio().buscarPorId(s.getIdDoctor());
            lblDoctor.setText(d.getNombre() + " " + d.getApellidoPaterno());
        }
    } catch (Exception ex) {
        System.out.println("Error: " + ex.getMessage());
    }
}
}
