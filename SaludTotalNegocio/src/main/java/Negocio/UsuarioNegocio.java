package Negocio;

import Conexion.ConexionBD;
import DTO.CrearUsuarioDTO;
import DTO.UsuarioDTO;
import Entidades.UsuarioEntidad;
import org.mindrot.jbcrypt.BCrypt;
import persistencia.datos.IUsuarioDAO;
import persistencia.datos.PersistenciaException;
import persistencia.datos.UsuarioDAO;

public class UsuarioNegocio implements IUsuarioNegocio {

    private static final String CREDENCIALES_INVALIDAS = "Nombre o password incorrectos.";

    private final IUsuarioDAO usuarioDAO;

    public UsuarioNegocio() {
        this.usuarioDAO = new UsuarioDAO(new ConexionBD());
    }

    @Override
    public UsuarioDTO registrarUsuario(CrearUsuarioDTO dto) throws NegocioException {
        if (dto == null) {
            throw new NegocioException("No se recibieron los datos del usuario.");
        }
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new NegocioException("El nombre es obligatorio.");
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new NegocioException("El password es obligatorio.");
        }

        String nombre = dto.getNombre().trim();
        try {
            if (usuarioDAO.buscarPorNombre(nombre) != null) {
                throw new NegocioException("Ya existe un usuario con ese nombre.");
            }
            String hash = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
            UsuarioEntidad guardado = usuarioDAO.guardar(new UsuarioEntidad(nombre, hash));
            return aDTO(guardado);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo registrar el usuario. Revisa la conexion con la base de datos.", e);
        }
    }

    @Override
    public UsuarioDTO iniciarSesion(String nombre, String password) throws NegocioException {
        if (nombre == null || nombre.isBlank() || password == null || password.isEmpty()) {
            throw new NegocioException("Ingresa tu nombre y tu password.");
        }
        try {
            UsuarioEntidad usuario = usuarioDAO.buscarPorNombre(nombre.trim());
            if (usuario == null || !passwordCorrecto(password, usuario.getContraseña())) {
                throw new NegocioException(CREDENCIALES_INVALIDAS);
            }
            return aDTO(usuario);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo iniciar sesion. Revisa la conexion con la base de datos.", e);
        }
    }

    private boolean passwordCorrecto(String password, String hashGuardado) {
        try {
            return hashGuardado != null && BCrypt.checkpw(password, hashGuardado);
        } catch (IllegalArgumentException e) {
            // El valor guardado no es un hash BCrypt (usuario viejo en texto plano).
            return false;
        }
    }

    private UsuarioDTO aDTO(UsuarioEntidad u) {
        return new UsuarioDTO(u.getId(), u.getNombre());
    }

}
