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
 * Controlador para gestionar pacientes.
 * Aplica inyección de dependencias: recibe los DAOs por constructor.
 * Esto permite:
 * - Bajo acoplamiento (GRASP): depende de interfaces, no de implementaciones
 * - Inversión de dependencias (SOLID): depende de abstracciones
 * - Testabilidad: permite inyectar mocks para testing
 */
public class PacienteController {

    private static PacienteController pacienteController;
    private final IPacienteDao pacienteDao;
    private final IPeticionDao peticionDao;
    private List<Paciente> pacientes;

    /**
     * Constructor privado que recibe las dependencias (Dependency Injection)
     * Aplica el principio de Inversión de Dependencias (DIP - SOLID)
     */
    private PacienteController(IPacienteDao pacienteDao, IPeticionDao peticionDao) throws Exception {
        if (pacienteDao == null || peticionDao == null) {
            throw new IllegalArgumentException("Los DAOs no pueden ser null");
        }
        this.pacienteDao = pacienteDao;
        this.peticionDao = peticionDao;
        this.pacientes = pacienteDao.getAll();
    }

    /**
     * Método estático para obtener la instancia (Singleton)
     * NOTA: Este método está aquí por compatibilidad, pero se recomienda
     * usar ControllerFactory para obtener instancias con dependencias inyectadas
     * 
     * @deprecated Usar ControllerFactory.getPacienteController() en su lugar
     */
    @Deprecated
    public static synchronized PacienteController getInstance() throws Exception {
        if (pacienteController == null) {
            // Por compatibilidad, creamos los DAOs aquí
            // En producción, usar ControllerFactory
            main.uade.edu.ar.dao.PacienteDao dao = new main.uade.edu.ar.dao.PacienteDao();
            main.uade.edu.ar.dao.PeticionDao peticionDao = new main.uade.edu.ar.dao.PeticionDao();
            pacienteController = new PacienteController(dao, peticionDao);
        }
        return pacienteController;
    }
    
    /**
     * Método público para crear instancia con dependencias inyectadas
     * Usado por ControllerFactory
     */
    public static PacienteController createInstance(IPacienteDao pacienteDao, IPeticionDao peticionDao) throws Exception {
        if (pacienteController == null) {
            pacienteController = new PacienteController(pacienteDao, peticionDao);
        }
        return pacienteController;
    }

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

    /**
     * Verifica si existe un paciente con los mismos DNI, apellido y nombre
     * @param dni El DNI a verificar
     * @param apellido El apellido a verificar
     * @param nombre El nombre a verificar
     * @return true si existe un paciente con los 3 campos iguales, false en caso contrario
     */
    private boolean existePacienteCompleto(int dni, String apellido, String nombre) {
        return pacientes.stream()
                .anyMatch(p -> p.getDni() == dni && 
                             p.getApellido().equalsIgnoreCase(apellido) && 
                             p.getNombre().equalsIgnoreCase(nombre));
    }

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

        List<Peticion> peticiones = peticionDao.getAll() //Nos traemos las peticiones guardas en el JSON
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
