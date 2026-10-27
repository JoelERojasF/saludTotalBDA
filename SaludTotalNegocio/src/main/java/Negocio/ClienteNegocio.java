package Negocio;

import Conexion.ConexionBD;
import Exception.NegocioException;
import Negocio.Interfaces.IClienteNegocio;
import DTO.ClienteDTO;
import DTO.Crear.CrearClienteDTO;
import Daos.ClienteDAO;
import Entidades.ClienteEntidad;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import Daos.Interfaces.IClienteDAO;
import Validador.Validador;
import exception.PersistenciaException;

public class ClienteNegocio implements IClienteNegocio {

    private final IClienteDAO clienteDAO;
    Validador val = new Validador();

    public ClienteNegocio() {
        this.clienteDAO = new ClienteDAO(new ConexionBD());
    }

    @Override
    public ClienteDTO registrar(CrearClienteDTO dto) throws NegocioException {
        if (dto == null) {
            throw new NegocioException("No se recibieron los datos del cliente.");
        }
        validar(dto.getNombre(), dto.getFechaNacimiento(), dto.getSexo(), dto.getTipoSangre());
        if (!val.validarNombrePersona(dto.getApellidoPaterno())) {
            dto.setApellidoPaterno(null);
        }
        if (!val.validarNombrePersona(dto.getApellidoMaterno())) {
            dto.setApellidoMaterno(null);
        }

        ClienteEntidad entidad = DTOAEntidad(dto);
        try {
            return EntidadADTO(clienteDAO.guardar(entidad));
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo registrar el cliente. Revisa la conexion con la base de datos.", e);
        }
    }

    @Override
    public ClienteDTO actualizar(ClienteDTO dto) throws NegocioException {
        if (dto == null || dto.getId() == null) {
            throw new NegocioException("Falta el id del cliente a actualizar.");
        }
        validar(dto.getNombre(), dto.getFechaNacimiento(), dto.getSexo(), dto.getTipoSangre());
        if (!val.validarNombrePersona(dto.getApellidoPaterno())) {
            dto.setApellidoPaterno(null);
        }
        if (!val.validarNombrePersona(dto.getApellidoMaterno())) {
            dto.setApellidoMaterno(null);
        }

        try {
            if (clienteDAO.buscarPorId(dto.getId()) == null) {
                throw new NegocioException("El cliente que intentas actualizar no existe.");
            }
            ClienteEntidad entidad = DTOAEntidad(dto);
            entidad.setId(dto.getId());
            return EntidadADTO(clienteDAO.actualizar(entidad));
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo actualizar el cliente.", e);
        }
    }

    @Override
    public void eliminar(Integer id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("Falta el id del cliente a eliminar.");
        }
        if (id <= 0) {
            throw new NegocioException("El id es invalido.");
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
        if (id <= 0) {
            throw new NegocioException("El id es invalido.");
        }
        try {
            ClienteEntidad entidad = clienteDAO.buscarPorId(id);
            if (entidad == null) {
                throw new NegocioException("No se encontro el cliente.");
            }
            return EntidadADTO(entidad);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo consultar el cliente.", e);
        }
    }

    @Override
    public List<ClienteDTO> obtenerTodos() throws NegocioException {
        try {
            List<ClienteDTO> lista = new ArrayList<>();
            for (ClienteEntidad e : clienteDAO.obtenerTodos()) {
                lista.add(EntidadADTO(e));
            }
            return lista;
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo obtener la lista de clientes.", e);
        }
    }

    private void validar(String nombre, String fechaNacimiento, String sexo, String tipoSangre) throws NegocioException {
        if (!val.validarNombrePersona(nombre)) {
            throw new NegocioException("El nombre es invalido.");
        }
        if (!val.validarFormatoFecha(fechaNacimiento)) {
            throw new NegocioException("La fecha de nacimiento es invalida.");
        }
        if (Date.valueOf(fechaNacimiento).after(new java.util.Date())) {
            throw new NegocioException("La fecha de nacimiento no puede ser futura.");
        }
        if (!val.validarSexo(sexo)) {
            throw new NegocioException("El sexo es invalido.");
        }
        if (!val.validarTipoSangre(tipoSangre)) {
            throw new NegocioException("El tipo de sangre es invalido.");
        }
    }

    private ClienteDTO EntidadADTO(ClienteEntidad e) {
        return new ClienteDTO(
                e.getId(),
                e.getNombre(),
                e.getApellidoPaterno(),
                e.getApellidoMaterno(),
                e.getFechaNacimiento().toString(),
                e.getSexo(),
                e.getTipoSangre());
    }

    private ClienteEntidad DTOAEntidad(ClienteDTO dto) {
        ClienteEntidad creado = new ClienteEntidad(
                dto.getNombre().trim(),
                val.vacioANulo(dto.getApellidoPaterno()),
                val.vacioANulo(dto.getApellidoMaterno()),
                Date.valueOf(dto.getFechaNacimiento()),
                dto.getSexo(),
                dto.getTipoSangre());
        creado.setId(dto.getId());
        return creado;
    }

    private ClienteEntidad DTOAEntidad(CrearClienteDTO dto) {
        return new ClienteEntidad(
                dto.getNombre().trim(),
                val.vacioANulo(dto.getApellidoPaterno()),
                val.vacioANulo(dto.getApellidoMaterno()),
                Date.valueOf(dto.getFechaNacimiento()),
                dto.getSexo(),
                dto.getTipoSangre());
    }

}
