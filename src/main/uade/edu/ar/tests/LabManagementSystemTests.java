package main.uade.edu.ar.tests;

import main.uade.edu.ar.Main;
import main.uade.edu.ar.controller.*;
import main.uade.edu.ar.dto.*;
import main.uade.edu.ar.enums.Genero;
import main.uade.edu.ar.enums.Roles;
import main.uade.edu.ar.enums.TipoResultado;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LabManagementSystemTests {
    
    private PeticionController peticionesController;
    private SucursalYUsuarioController sucursalYUsuarioController;
    private PacienteController pacienteController;
    
    // Helper method to create dates for tests
    private Date createDate(String dateString) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            return format.parse(dateString);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing date: " + dateString, e);
        }
    }

    public LabManagementSystemTests() throws Exception {
        // Verificar que el sistema esté inicializado
        if (!Main.isSystemInitialized()) {
            throw new IllegalStateException("El sistema no ha sido inicializado. Llame a Main.initializeSystem() primero.");
        }
        
        // Obtener los controladores desde la clase Main
        this.peticionesController = Main.getPeticionController();
        this.sucursalYUsuarioController = Main.getSucursalYUsuarioController();
        this.pacienteController = Main.getPacienteController();
    }

    public void runAllTests() throws Exception {
        System.out.println("🧪 Iniciando tests del sistema de gestión de laboratorio...");
        
        testPacientes();
        testPeticiones();
        testUsuarios();
        testPeticionesConValoresReservados();
        
        System.out.println("✅ Todos los tests completados exitosamente");
    }

    public void testPacientes() throws Exception {
        System.out.println("📋 Ejecutando tests de pacientes...");
        
        UsuarioDto responsable = new UsuarioDto(1, "Test", "1dh68lpxz*", createDate("1990-06-04"), Roles.ADMINISTRADOR);
        SucursalDto sucursal = new SucursalDto(1, 100, "Av Santa Fe", responsable);
        PacienteDto paciente = new PacienteDto(1, 22, Genero.MASCULINO, "Test", 1234, "dom", "garciatest@gmail.com", "Garcia");

        List<PracticaDto> practicas = List.of(
                new PracticaDto(1, 999, "Análisis 2", 3, 3, new ResultadoDto("60", TipoResultado.CRITICO)),
                new PracticaDto(1, 999, "Análisis 3", 3, 3, new ResultadoDto("60", TipoResultado.CRITICO))
        );

        peticionesController.borrarPeticion(1);
        peticionesController.crearPeticion(new PeticionDto(1, "Swiss Medical", createDate("2023-06-01"), createDate("2023-06-02"), sucursal, paciente, practicas));

        pacienteController.crearPaciente(paciente);

        paciente.setApellido("Paez");
        pacienteController.modificarPaciente(paciente);
        pacienteController.borrarPaciente(1);
        
        System.out.println("✅ Tests de pacientes completados");
    }

    public void testPeticiones() throws Exception {
        System.out.println("📋 Ejecutando tests de peticiones...");
        
        UsuarioDto responsable = new UsuarioDto(1, "Hugo", "", createDate("1990-06-04"), Roles.ADMINISTRADOR);
        SucursalDto sucursal = new SucursalDto(1, 100, "Av Santa Fe", responsable);
        PracticaDto practica = new PracticaDto(1, 999, "Análisis", 3, 3);
        PacienteDto paciente = new PacienteDto(1, 22, Genero.MASCULINO, "Test", 1234, "dom", "test@gmail.com", "Testing");

        peticionesController.crearPeticion(new PeticionDto(1, "Swiss Medical", createDate("2023-06-01"), createDate("2023-06-02"), sucursal, paciente));
        peticionesController.crearPeticion(new PeticionDto(2, "Swiss Medical", createDate("2023-06-01"), createDate("2023-06-02"), sucursal, paciente, List.of(practica)));
        peticionesController.modificarPeticion(new PeticionDto(2, "OSDE", createDate("2023-05-01"), createDate("2023-05-02"), sucursal, paciente, List.of(practica)));
        peticionesController.borrarPeticion(2);

        peticionesController.crearPractica(1, practica);
        peticionesController.modificarPractica(new PracticaDto(1, 111, "Practica 1", 60, 10));
        peticionesController.borrarPractica(1);

        peticionesController.crearResultado(1, new ResultadoDto("40", TipoResultado.CRITICO));
        peticionesController.modificarResultado(1, new ResultadoDto("50", TipoResultado.CRITICO));
        peticionesController.eliminarResultado(1);
        
        System.out.println("✅ Tests de peticiones completados");
    }

    public void testUsuarios() throws Exception {
        System.out.println("👥 Ejecutando tests de usuarios...");
        
        UsuarioDto responsable = new UsuarioDto(1, "Hugo", "", createDate("1990-06-04"), Roles.ADMINISTRADOR);
        SucursalDto sucursal = new SucursalDto(1, 100, "Av Santa Fe", responsable);
        SucursalDto sucursalA = new SucursalDto(2, 100, "Av Santa Fe", responsable);
        PacienteDto paciente = new PacienteDto(1, 22, Genero.MASCULINO, "Paciente test", 12349977, "dom", "pereztest@gmail.com", "Perez");

        List<PracticaDto> practicas = List.of(
                new PracticaDto(1, 999, "Análisis de sangre", 3, 3),
                new PracticaDto(2, 999, "Análisis de orina", 3, 3)
        );

        peticionesController.borrarPeticion(1);
        peticionesController.crearPeticion(new PeticionDto(1, "Swiss Medical", createDate("2023-06-01"), createDate("2023-06-02"), sucursal, paciente, practicas));

        sucursalYUsuarioController.crearUsuario(new UsuarioDto(1, "Didy", "", createDate("1990-06-04"), Roles.LABORTISTA));
        sucursalYUsuarioController.modificarUsuario(new UsuarioDto(1, "Didy", "", createDate("1990-06-04"), Roles.ADMINISTRADOR));
        sucursalYUsuarioController.eliminarUsuario(1);

        sucursalYUsuarioController.crearSucursal(sucursal);
        sucursalYUsuarioController.crearSucursal(sucursalA);
        sucursalYUsuarioController.modificarSucursal(sucursal);
        sucursalYUsuarioController.modificarSucursal(sucursalA);
        sucursalYUsuarioController.borrarSucursal(1);
        
        System.out.println("✅ Tests de usuarios completados");
    }

    public void testPeticionesConValoresReservados() throws Exception {
        System.out.println("🔬 Ejecutando tests de peticiones con valores reservados...");
        
        UsuarioDto responsable = new UsuarioDto(1, "Hugo", "13jfso*jd37", createDate("1990-06-04"), Roles.ADMINISTRADOR);
        SucursalDto sucursal = new SucursalDto(1, 100, "Av Santa Fe", responsable);
        PracticaDto practica1 = new PracticaDto(1, 999, "Análisis de sangre", 3, 3);
        PracticaDto practica2 = new PracticaDto(2, 999, "Análisis de sangre", 3, 3);
        PracticaDto practica3 = new PracticaDto(3, 999, "Análisis de sangre", 3, 3, new ResultadoDto("No debe mostrarse", TipoResultado.RESERVADO));
        PacienteDto paciente = new PacienteDto(1, 22, Genero.MASCULINO, "Test", 12345678, "dom", "test@gmail.com", "Gomez");
        ResultadoDto resultado1 = new ResultadoDto("40", TipoResultado.CRITICO);
        ResultadoDto resultado2 = new ResultadoDto("40", TipoResultado.RESERVADO);

        peticionesController.crearPeticion(new PeticionDto(1, "Swiss Medical", createDate("2023-06-01"), createDate("2023-06-02"), sucursal, paciente));
        peticionesController.crearPeticion(new PeticionDto(2, "Swiss Medical", createDate("2023-06-01"), createDate("2023-06-02"), sucursal, paciente, List.of(practica2)));

        peticionesController.crearPractica(1, practica1);
        peticionesController.crearPractica(1, practica3);

        peticionesController.crearResultado(1, resultado1);
        peticionesController.crearResultado(2, resultado2);

        peticionesController.getPracticasConResultadosReservados(1).forEach(p -> System.out.println("Resultado práctica " + p.getId() + " = " + p.getResultado().getValor()));
        
        System.out.println("✅ Tests de peticiones con valores reservados completados");
    }
}
