/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package Pantallas;

import DTO.Crear.CrearClienteDTO;
import DTO.UsuarioDTO;
import Exception.NegocioException;
import Negocio.FabricaNegocio;
import Negocio.Interfaces.IClienteNegocio;
import Validador.Validador;
import static Pantallas.LoginFrame.COLOR_TEXTO;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author pirown
 */
public class RegistrarClienteFrame extends JFrame {

    private UsuarioDTO usuarioActual;
    
    private final IClienteNegocio clienteNegocio;

    private final Color COLOR_BOTON = new Color(51, 51, 51);

    private JTextField txtNombre;
    private JTextField txtApellidoPaterno;
    private JTextField txtApellidoMaterno;
    private JSpinner spnFechaNacimiento;
    private JComboBox<String> cmbSexo;
    private JComboBox<String> cmbTipoSangre;

    public RegistrarClienteFrame(UsuarioDTO usuario) {
        this.usuarioActual = usuario;
        
        this.clienteNegocio = FabricaNegocio.crearClienteNegocio();

        setTitle("Registrar Cliente");
        setSize(400, 750); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(380, 700));

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setBorder(new EmptyBorder(25, 30, 25, 30));

        crearComponentesFormulario(panelPrincipal);

        panelPrincipal.add(Box.createVerticalStrut(25));
        panelPrincipal.add(crearBotonesInferiores());

        setContentPane(panelPrincipal);
    }

    private void crearComponentesFormulario(JPanel panel) {
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(COLOR_TEXTO);
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtNombre = new JTextField();
        configurarTextField(txtNombre, "Ingresa tu nombre");

        panel.add(lblNombre);
        panel.add(Box.createVerticalStrut(8));
        panel.add(txtNombre);
        panel.add(Box.createVerticalStrut(20));
        
        JLabel lblPaterno = new JLabel("Apellido Paterno:");
        lblPaterno.setForeground(COLOR_TEXTO);
        lblPaterno.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtApellidoPaterno = new JTextField();
        configurarTextField(txtApellidoPaterno, "Ingresa tu apellido paterno");

        panel.add(lblPaterno);
        panel.add(Box.createVerticalStrut(8));
        panel.add(txtApellidoPaterno);
        panel.add(Box.createVerticalStrut(20));
        
        JLabel lblMaterno = new JLabel("Apellido Materno (Opcional):");
        lblMaterno.setForeground(COLOR_TEXTO);
        lblMaterno.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtApellidoMaterno = new JTextField();
        configurarTextField(txtApellidoMaterno, "Ingresa tu apellido materno");

        panel.add(lblMaterno);
        panel.add(Box.createVerticalStrut(8));
        panel.add(txtApellidoMaterno);
        panel.add(Box.createVerticalStrut(20));

        JLabel lblFecha = new JLabel("Fecha de nacimento:");
        lblFecha.setForeground(COLOR_TEXTO);
        lblFecha.setAlignmentX(Component.LEFT_ALIGNMENT);

        SpinnerDateModel modeloFecha = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        spnFechaNacimiento = new JSpinner(modeloFecha);
        JSpinner.DateEditor editorFecha = new JSpinner.DateEditor(spnFechaNacimiento, "dd/MM/yyyy");
        spnFechaNacimiento.setEditor(editorFecha);
        spnFechaNacimiento.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        spnFechaNacimiento.setPreferredSize(new Dimension(0, 40));
        spnFechaNacimiento.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lblFecha);
        panel.add(Box.createVerticalStrut(8));
        panel.add(spnFechaNacimiento);
        panel.add(Box.createVerticalStrut(20));

        JLabel lblSexo = new JLabel("Sexo:");
        lblSexo.setForeground(COLOR_TEXTO);
        lblSexo.setAlignmentX(Component.LEFT_ALIGNMENT);

        cmbSexo = new JComboBox<>(new String[]{"Seleccionar","Masculino", "Femenino"});
        cmbSexo.setBackground(Color.WHITE);
        cmbSexo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        cmbSexo.setPreferredSize(new Dimension(0, 40));
        cmbSexo.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lblSexo);
        panel.add(Box.createVerticalStrut(8));
        panel.add(cmbSexo);
        panel.add(Box.createVerticalStrut(20));

        JLabel lblSangre = new JLabel("Tipo de sangre:");
        lblSangre.setForeground(COLOR_TEXTO);
        lblSangre.setAlignmentX(Component.LEFT_ALIGNMENT);

        cmbTipoSangre = new JComboBox<>(new String[]{"Seleccionar","A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"});
        cmbTipoSangre.setBackground(Color.WHITE);
        cmbTipoSangre.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        cmbTipoSangre.setPreferredSize(new Dimension(0, 40));
        cmbTipoSangre.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lblSangre);
        panel.add(Box.createVerticalStrut(8));
        panel.add(cmbTipoSangre);
    }

    private void configurarTextField(JTextField campo, String placeholder) {
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        campo.setPreferredSize(new Dimension(0, 40));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setForeground(Color.GRAY);
        campo.setText(placeholder);

        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(COLOR_TEXTO);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setForeground(Color.GRAY);
                    campo.setText(placeholder);
                }
            }
        });
    }

    private JPanel crearBotonesInferiores() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        JButton btnRegistrar = crearBotonOscuro("Registrar");
        btnRegistrar.setPreferredSize(new Dimension(300, 45));

        btnRegistrar.addActionListener(e -> {
            String nombreStr = txtNombre.getText().trim();
            String paternoStr = txtApellidoPaterno.getText().trim();
            String maternoStr = txtApellidoMaterno.getText().trim();
            if(cmbSexo.getSelectedItem().equals("Seleccionar")){
                JOptionPane.showMessageDialog(this, "Seleccione un sexo.", 
                        "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String sexoStr = (String) cmbSexo.getSelectedItem();
            if(cmbSexo.getSelectedItem().equals("Seleccionar")){
                JOptionPane.showMessageDialog(this, "Seleccione un tipo de sangre.", 
                        "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String sangreStr = (String) cmbTipoSangre.getSelectedItem();

            if (nombreStr.equals("Ingresa tu nombre") || paternoStr.equals("Ingresa tu apellido paterno")) {
                JOptionPane.showMessageDialog(this, "Por favor llena los campos obligatorios de nombre.",
                        "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String maternoFinal = maternoStr;
            if (maternoStr.equals("Ingresa tu apellido materno")) {
                maternoFinal = null;
            }

            Validador validador = new Validador();
            
            if (!validador.validarNombrePersona(nombreStr)) {
                JOptionPane.showMessageDialog(this, "El nombre ingresado no es válido.", 
                        "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!validador.validarApellido(paternoStr)) {
                JOptionPane.showMessageDialog(this, "El apellido paterno ingresado no es válido.", 
                        "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
          

            Date fechaNacimiento = (Date) spnFechaNacimiento.getValue();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaParaDTO = sdf.format(fechaNacimiento);

            CrearClienteDTO dto = new CrearClienteDTO();
            dto.setNombre(nombreStr);
            dto.setApellidoPaterno(paternoStr);
            dto.setApellidoMaterno(maternoFinal);
            dto.setFechaNacimiento(fechaParaDTO);
            dto.setSexo(sexoStr);
            dto.setTipoSangre(sangreStr);

            try {
                clienteNegocio.registrar(dto);
                JOptionPane.showMessageDialog(this, "Cliente registrado exitosamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                this.dispose(); 
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Error al registrar", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(btnRegistrar);
        return panel;
    }

    private JButton crearBotonOscuro(String texto) {
        JButton boton = new JButton(texto);
        boton.setForeground(Color.WHITE);
        boton.setBackground(COLOR_BOTON);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }
}