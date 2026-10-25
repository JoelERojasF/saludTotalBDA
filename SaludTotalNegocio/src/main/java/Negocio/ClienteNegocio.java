package Negocio;

import DTO.ClienteDTO;
import DTO.CrearClienteDTO;
import Entidades.ClienteEntidad;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import persistencia.datos.IClienteDAO;
import persistencia.datos.PersistenciaException;





public class ClienteNegocio implements IClienteNegocio{
    
    private final IClienteDAO clienteDAO;

    public ClienteNegocio(IClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    @Override
    public ClienteDTO registrarCliente(CrearClienteDTO dto) throws NegocioException {
        if (dto == null) {
            throw new NegocioException("No se recibieron los datos del cliente.");
        }
        validar(dto.getNombre(), dto.getFechaNacimiento(), dto.getSexo(), dto.getTipoSangre());

        ClienteEntidad entidad = new ClienteEntidad(
                dto.getNombre().trim(),
                vacioANulo(dto.getApellidoPaterno()),
                vacioANulo(dto.getApellidoMaterno()),
                new Date(dto.getFechaNacimiento().getTime()),
                dto.getSexo(),
                dto.getTipoSangre());
        try {
            return aDTO(clienteDAO.guardar(entidad));
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo registrar el cliente. Revisa la conexion con la base de datos.", e);
        }
    }

    @Override
    public ClienteDTO actualizarCliente(ClienteDTO dto) throws NegocioException {
        if (dto == null || dto.getId() == null) {
            throw new NegocioException("Falta el id del cliente a actualizar.");
        }
        validar(dto.getNombre(), dto.getFechaNacimiento(), dto.getSexo(), dto.getTipoSangre());
        try {
            if (clienteDAO.buscarPorId(dto.getId()) == null) {
                throw new NegocioException("El cliente que intentas actualizar no existe.");
            }
            ClienteEntidad entidad = new ClienteEntidad(
                    dto.getNombre().trim(),
                    vacioANulo(dto.getApellidoPaterno()),
                    vacioANulo(dto.getApellidoMaterno()),
                    new Date(dto.getFechaNacimiento().getTime()),
                    dto.getSexo(),
                    dto.getTipoSangre());
            entidad.setId(dto.getId());
            return aDTO(clienteDAO.actualizar(entidad));
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo actualizar el cliente.", e);
        }
    }

    @Override
    public void eliminarCliente(Integer id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("Falta el id del cliente a eliminar.");
        }
        try {
            if (clienteDAO.buscarPorId(id) == null) {
                throw new NegocioException("El cliente que intentas eliminar no existe.");
            }
            clienteDAO.eliminar(id);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo eliminar el cliente. Puede tener solicitudes registradas.", e);
        }
    }

    @Override
    public ClienteDTO buscarPorId(Integer id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("Falta el id del cliente.");
        }
        try {
            ClienteEntidad cliente = clienteDAO.buscarPorId(id);
            if (cliente == null) {
                throw new NegocioException("No se encontro el cliente.");
            }
            return aDTO(cliente);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo consultar el cliente.", e);
        }
    }

    @Override
    public List<ClienteDTO> obtenerTodos() throws NegocioException {
        try {
            List<ClienteDTO> lista = new ArrayList<>();
            for (ClienteEntidad e : clienteDAO.obtenerTodos()) {
                lista.add(aDTO(e));
            }
            return lista;
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo obtener la lista de clientes.", e);
        }
    }

    // ---------------- Validaciones ----------------

    private void validar(String nombre, java.util.Date fechaNacimiento,
            String sexo, String tipoSangre) throws NegocioException {
        if (nombre == null || nombre.isBlank()) {
            throw new NegocioException("El nombre es obligatorio.");
        }
        if (fechaNacimiento == null) {
            throw new NegocioException("La fecha de nacimiento es obligatoria.");
        }
        if (fechaNacimiento.after(new java.util.Date())) {
            throw new NegocioException("La fecha de nacimiento no puede ser futura.");
        }
        if (sexo == null || sexo.isBlank()) {
            throw new NegocioException("El sexo es obligatorio.");
        }
        if (tipoSangre == null || tipoSangre.isBlank()) {
            throw new NegocioException("El tipo de sangre es obligatorio.");
        }
    }

    // ---------------- Conversion Entidad -> DTO ----------------

    private ClienteDTO aDTO(ClienteEntidad e) {
        return new ClienteDTO(
                e.getId(),
                e.getNombre(),
                e.getApellidoPaterno(),
                e.getApellidoMaterno(),
                e.getFechaNacimiento(),
                e.getSexo(),
                e.getTipoSangre());
    }

    private String vacioANulo(String texto) {
        return (texto == null || texto.isBlank()) ? null : texto.trim();
    }
    
}
