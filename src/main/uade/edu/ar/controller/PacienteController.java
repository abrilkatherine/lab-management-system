package main.uade.edu.ar.controller;

import main.uade.edu.ar.dao.IPacienteDao;
import main.uade.edu.ar.dao.IPeticionDao;
import main.uade.edu.ar.dto.PacienteDto;
import main.uade.edu.ar.exceptions.PacienteYaExisteException;
import main.uade.edu.ar.mappers.PacienteMapper;
import main.uade.edu.ar.model.Paciente;
import main.uade.edu.ar.model.Peticion;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para gestionar la lógica de negocio de pacientes.
 * Maneja operaciones CRUD y validaciones de reglas de negocio.
 */
public class PacienteController {

    private static PacienteController pacienteController;
    private final IPacienteDao pacienteDao;
    private final IPeticionDao peticionDao;
    private List<Paciente> pacientes;

    private PacienteController(IPacienteDao pacienteDao, IPeticionDao peticionDao) throws Exception {
        if (pacienteDao == null || peticionDao == null) {
            throw new IllegalArgumentException("Los DAOs no pueden ser null");
        }
        this.pacienteDao = pacienteDao;
        this.peticionDao = peticionDao;
        this.pacientes = pacienteDao.getAll();
    }

    public static PacienteController createInstance(IPacienteDao pacienteDao, IPeticionDao peticionDao) throws Exception {
        if (pacienteController == null) {
            pacienteController = new PacienteController(pacienteDao, peticionDao);
        }
        return pacienteController;
    }

    /**
     * Obtiene todos los pacientes del sistema.
     * 
     * @return lista de pacientes en formato DTO
     */
    public List<PacienteDto> getAllPacientes() {
        return pacientes.stream()
                .map(PacienteMapper::toDto)
                .collect(Collectors.toList());
    }

    public PacienteDto getPaciente(int id) {
        return pacientes.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .map(PacienteMapper::toDto)
                .orElse(null);
    }

    public PacienteDto getPacientePorDni(int dni) {
        return pacientes.stream()
                .filter(p -> p.getDni() == dni)
                .findFirst()
                .map(PacienteMapper::toDto)
                .orElse(null);
    }

    private boolean existePacienteCompleto(int dni, String apellido, String nombre) {
        return pacientes.stream()
                .anyMatch(p -> p.getDni() == dni && 
                             p.getApellido().equalsIgnoreCase(apellido) && 
                             p.getNombre().equalsIgnoreCase(nombre));
    }

    /**
     * Crea un nuevo paciente en el sistema.
     * 
     * @param pacienteDTO datos del paciente a crear
     * @throws PacienteYaExisteException si ya existe un paciente con el mismo DNI, apellido y nombre
     * @throws Exception si hay error al persistir
     */
    public void crearPaciente(PacienteDto pacienteDTO) throws PacienteYaExisteException, Exception {
        if (existePacienteCompleto(pacienteDTO.getDni(), pacienteDTO.getApellido(), pacienteDTO.getNombre())) {
            throw new PacienteYaExisteException(pacienteDTO.getDni(), 
                "Ya existe un paciente con DNI: " + pacienteDTO.getDni() + 
                ", Apellido: " + pacienteDTO.getApellido() + 
                " y Nombre: " + pacienteDTO.getNombre());
        }
        
        Paciente paciente = PacienteMapper.toModel(pacienteDTO);
        pacienteDao.save(paciente);
        pacientes.add(paciente);
    }

    /**
     * Modifica los datos de un paciente existente.
     * 
     * @param pacienteDto datos actualizados del paciente
     * @throws Exception si hay error al actualizar o el paciente no existe
     */
    public void modificarPaciente(PacienteDto pacienteDto) throws Exception {
        Paciente paciente = pacientes.stream()
                .filter(p -> p.getId() == pacienteDto.getId())
                .findFirst()
                .orElse(null);

        if (paciente != null) {
            paciente.setNombre(pacienteDto.getNombre());
            paciente.setDni(pacienteDto.getDni());
            paciente.setDomicilio(pacienteDto.getDomicilio());
            paciente.setEmail(pacienteDto.getEmail());
            paciente.setApellido(pacienteDto.getApellido());
            paciente.setEdad(pacienteDto.getEdad());
            paciente.setGenero(pacienteDto.getGenero());

            pacienteDao.update(paciente);
        }
    }

    /**
     * Elimina un paciente del sistema.
     * Un paciente solo puede ser eliminado si no tiene peticiones con resultados.
     * 
     * @param id identificador del paciente a eliminar
     * @throws Exception si el paciente no puede ser eliminado o hay error al borrar
     */
    public void borrarPaciente(int id) throws Exception {
        Paciente paciente = pacientes.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        if (puedeBorrarse(paciente)) {
            pacienteDao.delete(id);
            pacientes.remove(paciente);
        } else {
            throw  new Exception("El paciente no cumple las condiciones para ser borrado");
        }
    }

    private boolean puedeBorrarse(Paciente paciente) throws Exception {
        if (paciente == null) {
            return false;
        }

        List<Peticion> peticiones = peticionDao.getAll()
                .stream()
                .filter(peticion -> peticion.getPaciente().getId() == paciente.getId())
                .toList();

        for (Peticion peticion : peticiones) {
            if (peticion.tieneResultado()) {
                return false;
            }
        }

        return true;
    }
}
