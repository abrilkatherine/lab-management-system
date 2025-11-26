# Patrones GRASP y SOLID aplicados en el Sistema de Gestión de Laboratorio

Este documento detalla todos los patrones GRASP (General Responsibility Assignment Software Patterns) y SOLID que se aplican en el proyecto, con ejemplos concretos del código.

---

## 📋 Índice

1. [Principios SOLID](#principios-solid)
2. [Patrones GRASP](#patrones-grasp)
3. [Patrones de Diseño Adicionales](#patrones-de-diseño-adicionales)
4. [Resumen Ejecutivo](#resumen-ejecutivo)

---

## 🎯 Principios SOLID

### 1. **S - Single Responsibility Principle (SRP)**
**Principio de Responsabilidad Única**: Cada clase debe tener una sola razón para cambiar.

#### ✅ Ejemplos en el Proyecto:

**`PacienteController`** - Responsabilidad única: gestionar la lógica de negocio de pacientes
```java
public class PacienteController {
    // Solo se encarga de operaciones de negocio sobre pacientes
    public void crearPaciente(PacienteDto pacienteDTO) throws Exception { ... }
    public void modificarPaciente(PacienteDto pacienteDto) throws Exception { ... }
    public void borrarPaciente(int id) throws Exception { ... }
}
```

**`PacienteDao`** - Responsabilidad única: acceso a datos de pacientes
```java
public class PacienteDao extends GenericDAO<Paciente> implements IPacienteDao {
    // Solo se encarga de persistir y recuperar pacientes
}
```

**`ValidacionUtil`** - Responsabilidad única: validaciones de datos
```java
public class ValidacionUtil {
    // Solo se encarga de validar DNI, edad, etc.
    public static boolean esDniValido(int dni) { ... }
    public static boolean esEdadValida(int edad) { ... }
}
```

**`SessionManager`** - Responsabilidad única: gestión de sesiones
```java
public class SessionManager {
    // Solo se encarga de manejar la sesión del usuario
    public void setUsuarioActual(UsuarioDto usuario) { ... }
    public UsuarioDto getUsuarioActual() { ... }
}
```

**`PacienteMapper`** - Responsabilidad única: conversión entre DTO y Model
```java
public class PacienteMapper {
    // Solo se encarga de convertir entre PacienteDto y Paciente
    public static Paciente toModel(PacienteDto pacienteDto) { ... }
    public static PacienteDto toDto(Paciente paciente) { ... }
}
```

---

### 2. **O - Open/Closed Principle (OCP)**
**Principio Abierto/Cerrado**: Las entidades deben estar abiertas para extensión pero cerradas para modificación.

#### ✅ Ejemplos en el Proyecto:

**`GenericDAO<T>`** - Clase abstracta abierta a extensión
```java
public abstract class GenericDAO<T> {
    // Implementación genérica de operaciones CRUD
    // Las clases hijas extienden sin modificar la base
}

// Extensión sin modificar GenericDAO
public class PacienteDao extends GenericDAO<Paciente> implements IPacienteDao {
    // Agrega funcionalidad específica sin cambiar GenericDAO
}

public class PeticionDao extends GenericDAO<Peticion> implements IPeticionDao {
    // Mismo patrón, extensión sin modificación
}
```

**`Persona` (abstracta)** - Abierta a extensión
```java
public abstract class Persona {
    // Clase base que puede extenderse
    // No se modifica al agregar nuevas subclases
}

public class Paciente extends Persona {
    // Extiende Persona sin modificarla
}
```

**Interfaces DAO** - Permiten agregar nuevas implementaciones
```java
public interface IPacienteDao {
    // Contrato abierto para implementaciones
    // Se pueden crear nuevas implementaciones sin cambiar la interfaz
}

public class PacienteDao implements IPacienteDao {
    // Implementación actual (JSON)
    // Futuras implementaciones (BD, API) sin cambiar la interfaz
}
```

---

### 3. **L - Liskov Substitution Principle (LSP)**
**Principio de Sustitución de Liskov**: Los objetos de una superclase deben poder ser reemplazados por objetos de sus subclases sin alterar el funcionamiento del programa.

#### ✅ Ejemplos en el Proyecto:

**`GenericDAO<T>` y sus implementaciones**
```java
// Cualquier implementación de GenericDAO puede usarse donde se espera GenericDAO
GenericDAO<Paciente> dao1 = new PacienteDao();
GenericDAO<Peticion> dao2 = new PeticionDao();
GenericDAO<Sucursal> dao3 = new SucursalDao();

// Todas se comportan de la misma manera (getAll, save, update, delete)
```

**Interfaces DAO**
```java
// Cualquier implementación de IPacienteDao puede usarse donde se espera IPacienteDao
IPacienteDao dao = new PacienteDao();
// O futuramente: IPacienteDao dao = new PacienteDaoBD();
// El código que usa IPacienteDao no necesita cambiar
```

**`Paciente extends Persona`**
```java
// Paciente puede usarse donde se espera Persona
Persona persona = new Paciente(...);
// Mantiene el comportamiento esperado de Persona
```

---

### 4. **I - Interface Segregation Principle (ISP)**
**Principio de Segregación de Interfaces**: Los clientes no deben depender de interfaces que no usan.

#### ✅ Ejemplos en el Proyecto:

**Interfaces DAO específicas** - Cada interfaz es pequeña y enfocada
```java
// IPacienteDao solo define métodos para pacientes
public interface IPacienteDao {
    List<Paciente> getAll() throws Exception;
    void save(Paciente paciente) throws Exception;
    boolean update(Paciente paciente) throws Exception;
    boolean delete(int id) throws Exception;
    Paciente search(int id) throws Exception;
}

// IPeticionDao solo define métodos para peticiones
public interface IPeticionDao { ... }

// No hay una interfaz gigante con todos los métodos
```

**Enums específicos** - Cada enum tiene un propósito único
```java
public enum Genero { ... }      // Solo para género
public enum Roles { ... }       // Solo para roles
public enum TipoResultado { ... } // Solo para tipos de resultado
```

---

### 5. **D - Dependency Inversion Principle (DIP)**
**Principio de Inversión de Dependencias**: Los módulos de alto nivel no deben depender de módulos de bajo nivel. Ambos deben depender de abstracciones.

#### ✅ Ejemplos en el Proyecto:

**Controladores dependen de interfaces, no de implementaciones**
```java
public class PacienteController {
    // Depende de la interfaz IPacienteDao, no de PacienteDao
    private final IPacienteDao pacienteDao;
    private final IPeticionDao peticionDao;
    
    // Inyección de dependencias por constructor
    private PacienteController(IPacienteDao pacienteDao, IPeticionDao peticionDao) {
        this.pacienteDao = pacienteDao;
        this.peticionDao = peticionDao;
    }
}
```

**ControllerFactory aplica DIP**
```java
public class ControllerFactory {
    // Usa interfaces
    private IPacienteDao pacienteDao;
    private IPeticionDao peticionDao;
    
    // Crea controladores con dependencias inyectadas
    public PacienteController getPacienteController() {
        // El controlador recibe interfaces, no implementaciones concretas
        return PacienteController.createInstance(pacienteDao, peticionDao);
    }
}
```

**GenericDAO es abstracto** - Las clases concretas dependen de la abstracción
```java
public abstract class GenericDAO<T> {
    // Abstracción que define el comportamiento
}

// Implementación concreta depende de la abstracción
public class PacienteDao extends GenericDAO<Paciente> {
    // Usa la abstracción GenericDAO
}
```

---

## 🎨 Patrones GRASP

### 1. **Creator (Creador)**
**Asignar la responsabilidad de crear una instancia de clase A a la clase B si:**
- B contiene o agrega instancias de A
- B registra instancias de A
- B usa directamente instancias de A

#### ✅ Ejemplos en el Proyecto:

**`ControllerFactory` crea controladores y DAOs**
```java
public class ControllerFactory {
    // ControllerFactory es el Creator de los controladores
    private void initializeDAOs() {
        pacienteDao = new PacienteDao();      // Crea DAOs
        peticionDao = new PeticionDao();
    }
    
    public PacienteController getPacienteController() {
        if (pacienteController == null) {
            // Crea el controlador con dependencias
            pacienteController = PacienteController.createInstance(pacienteDao, peticionDao);
        }
        return pacienteController;
    }
}
```

**`PacienteMapper` crea instancias de Paciente y PacienteDto**
```java
public class PacienteMapper {
    // PacienteMapper es el Creator de las conversiones
    public static Paciente toModel(PacienteDto pacienteDto) {
        return new Paciente(...); // Crea instancia de Paciente
    }
    
    public static PacienteDto toDto(Paciente paciente) {
        return new PacienteDto(...); // Crea instancia de PacienteDto
    }
}
```

**`PacienteDao` crea instancias de Paciente (mediante GenericDAO)**
```java
// GenericDAO lee y crea instancias desde JSON
public abstract class GenericDAO<T> {
    public List<T> getAll(Class<T> clase) {
        // Crea instancias de T desde JSON
        list.add(g.fromJson(jsonObject, clase));
    }
}
```

---

### 2. **Information Expert (Experto en Información)**
**Asignar una responsabilidad a la clase que tiene la información necesaria para cumplirla.**

#### ✅ Ejemplos en el Proyecto:

**`PacienteController` es experto en lógica de pacientes**
```java
public class PacienteController {
    private List<Paciente> pacientes;
    
    // Es experto en validar si un paciente existe (tiene la información)
    private boolean existePacienteCompleto(int dni, String apellido, String nombre) {
        return pacientes.stream()
            .anyMatch(p -> p.getDni() == dni && ...);
    }
    
    // Es experto en determinar si puede borrarse (tiene acceso a pacientes y peticiones)
    private boolean puedeBorrarse(Paciente paciente) {
        List<Peticion> peticiones = peticionDao.getAll()
            .stream()
            .filter(peticion -> peticion.getPaciente().getId() == paciente.getId())
            .toList();
        // Lógica de negocio basada en la información que tiene
    }
}
```

**`Practica` es experta en determinar si es crítica o reservada**
```java
public class Practica {
    private Resultado resultado;
    
    // Es experta en determinar su estado (tiene el resultado)
    public boolean esCritica() {
        if (resultado == null) return false;
        return TipoResultado.CRITICO.equals(resultado.getTipoResultado());
    }
    
    public boolean esReservada() {
        if (resultado == null) return false;
        return TipoResultado.RESERVADO.equals(resultado.getTipoResultado());
    }
}
```

**`ValidacionUtil` es experto en validaciones**
```java
public class ValidacionUtil {
    // Es experto en validar DNI (tiene las reglas de validación)
    public static boolean esDniValido(int dni) {
        return dni >= DNI_MINIMO && dni <= DNI_MAXIMO;
    }
}
```

---

### 3. **Low Coupling (Bajo Acoplamiento)**
**Reducir las dependencias entre clases.**

#### ✅ Ejemplos en el Proyecto:

**Controladores dependen de interfaces, no de implementaciones**
```java
public class PacienteController {
    // Bajo acoplamiento: depende de IPacienteDao (interfaz)
    // No depende de PacienteDao (implementación concreta)
    private final IPacienteDao pacienteDao;
}
```

**DTOs desacoplan Vista de Model**
```java
// Vista usa DTOs, no Models directamente
public class AgregarPaciente {
    // Usa PacienteDto, no Paciente
    // Esto desacopla la vista del modelo de dominio
    private PacienteController pacienteController;
}
```

**Mappers separan capas**
```java
// Los mappers evitan que Controller conozca directamente los detalles de Model/DTO
public class PacienteMapper {
    // Conversión que desacopla Controller de Model/DTO
    public static Paciente toModel(PacienteDto dto) { ... }
}
```

**ControllerFactory centraliza creación**
```java
// Centraliza la creación, reduciendo acoplamiento en Main
public class Main {
    // Main solo conoce ControllerFactory, no los detalles de creación
    ControllerFactory factory = ControllerFactory.getInstance();
    pacienteController = factory.getPacienteController();
}
```

---

### 4. **High Cohesion (Alta Cohesión)**
**Asignar responsabilidades para mantener clases cohesivas (todas sus partes están relacionadas).**

#### ✅ Ejemplos en el Proyecto:

**`PacienteController` - Alta cohesión**
```java
public class PacienteController {
    // Todos los métodos están relacionados con la gestión de pacientes
    public void crearPaciente(...) { ... }
    public void modificarPaciente(...) { ... }
    public void borrarPaciente(...) { ... }
    public List<PacienteDto> getAllPacientes() { ... }
    // Todos trabajan con la misma responsabilidad
}
```

**`ValidacionUtil` - Alta cohesión**
```java
public class ValidacionUtil {
    // Todos los métodos están relacionados con validaciones
    public static boolean esDniValido(...) { ... }
    public static boolean esEdadValida(...) { ... }
    public static int parsearDni(...) { ... }
    // Todos tienen propósito relacionado: validar datos
}
```

**`PacienteMapper` - Alta cohesión**
```java
public class PacienteMapper {
    // Solo métodos relacionados con conversión de Paciente
    public static Paciente toModel(PacienteDto dto) { ... }
    public static PacienteDto toDto(Paciente paciente) { ... }
    // Alta cohesión: solo conversión
}
```

**`SessionManager` - Alta cohesión**
```java
public class SessionManager {
    // Todos los métodos están relacionados con gestión de sesión
    public void setUsuarioActual(...) { ... }
    public UsuarioDto getUsuarioActual() { ... }
    public boolean haySesionActiva() { ... }
    public void cerrarSesion() { ... }
    // Alta cohesión: solo gestión de sesión
}
```

---

### 5. **Controller (Controlador)**
**Asignar responsabilidades de manejar eventos del sistema a una clase que representa el caso de uso.**

#### ✅ Ejemplos en el Proyecto:

**`PacienteController` actúa como controlador de casos de uso**
```java
public class PacienteController {
    // Maneja los casos de uso relacionados con pacientes
    public void crearPaciente(...) { ... }      // Caso de uso: crear paciente
    public void modificarPaciente(...) { ... }  // Caso de uso: modificar paciente
    public void borrarPaciente(...) { ... }     // Caso de uso: borrar paciente
}
```

**Vistas actúan como controladores de interfaz**
```java
public class AgregarPaciente extends JDialog {
    // Maneja eventos de la interfaz para agregar pacientes
    private void setListeners() {
        guardarButton.addActionListener(e -> {
            // Delega al Controller la lógica de negocio
            pacienteController.crearPaciente(...);
        });
    }
}
```

---

### 6. **Polymorphism (Polimorfismo)**
**Asignar responsabilidades usando operaciones polimórficas cuando el comportamiento varía según el tipo.**

#### ✅ Ejemplos en el Proyecto:

**`GenericDAO<T>` usa polimorfismo genérico**
```java
public abstract class GenericDAO<T> {
    // Métodos genéricos que funcionan con cualquier tipo T
    public List<T> getAll() { ... }
    public void save(T obj) { ... }
    public boolean update(T obj) { ... }
}

// Cada DAO concreto usa el mismo comportamiento genérico
PacienteDao extends GenericDAO<Paciente>  // T = Paciente
PeticionDao extends GenericDAO<Peticion>  // T = Peticion
```

**Interfaces DAO permiten polimorfismo**
```java
// Se puede usar cualquier implementación de IPacienteDao
IPacienteDao dao = new PacienteDao();
// O futuramente:
IPacienteDao dao = new PacienteDaoBD();

// El código que usa dao no necesita cambiar
List<Paciente> pacientes = dao.getAll();
```

**Herencia: `Paciente extends Persona`**
```java
public abstract class Persona {
    // Métodos comunes a todas las personas
}

public class Paciente extends Persona {
    // Extiende Persona con funcionalidad específica
}
```

---

### 7. **Pure Fabrication (Fabricación Pura)**
**Asignar responsabilidades a una clase artificial cuando no hay clase del dominio natural.**

#### ✅ Ejemplos en el Proyecto:

**`ControllerFactory` - Fabricación pura**
```java
// No representa una entidad del dominio, es una clase artificial
// creada para centralizar la creación de objetos
public class ControllerFactory {
    // Facilita la gestión de dependencias y creación de objetos
}
```

**`PacienteMapper` - Fabricación pura**
```java
// No representa una entidad del dominio
// Existe solo para convertir entre DTO y Model
public class PacienteMapper {
    // Facilita la separación de capas
}
```

**`ValidacionUtil` - Fabricación pura**
```java
// No representa una entidad del dominio
// Existe para centralizar validaciones
public class ValidacionUtil {
    // Facilita reutilización de código de validación
}
```

**`GenericDAO<T>` - Fabricación pura**
```java
// Clase genérica que no representa una entidad del dominio
// Existe para evitar duplicación de código de persistencia
public abstract class GenericDAO<T> {
    // Implementa lógica de persistencia genérica
}
```

---

### 8. **Indirection (Indirección)**
**Asignar responsabilidades a un objeto intermediario para desacoplar componentes.**

#### ✅ Ejemplos en el Proyecto:

**ControllerFactory como intermediario**
```java
// Main no crea directamente los controladores
// ControllerFactory actúa como intermediario
public class Main {
    ControllerFactory factory = ControllerFactory.getInstance();
    // Indirección: Main → Factory → Controller
}
```

**Mappers como intermediarios**
```java
// Controller no convierte directamente
// Mapper actúa como intermediario
public class PacienteController {
    // Indirección: Controller → Mapper → Model/DTO
    Paciente paciente = PacienteMapper.toModel(pacienteDTO);
}
```

**Interfaces DAO como intermediarias**
```java
// Controller no usa directamente PacienteDao
// Interfaz IPacienteDao actúa como intermediaria
public class PacienteController {
    // Indirección: Controller → IPacienteDao → PacienteDao
    private final IPacienteDao pacienteDao;
}
```

---

### 9. **Protected Variations (Variaciones Protegidas)**
**Identificar puntos de variación y crear una interfaz estable que los proteja.**

#### ✅ Ejemplos en el Proyecto:

**Interfaces DAO protegen contra cambios de implementación**
```java
// Si cambia la implementación (JSON → BD), el código que usa la interfaz no cambia
public interface IPacienteDao {
    // Interfaz estable que protege contra cambios
}

public class PacienteController {
    // Estable contra variaciones de implementación
    private final IPacienteDao pacienteDao; // Puede ser JSON, BD, API, etc.
}
```

**GenericDAO protege contra duplicación**
```java
// Cambios en la lógica de persistencia se hacen en GenericDAO
// Todos los DAOs se benefician automáticamente
public abstract class GenericDAO<T> {
    // Lógica centralizada que protege contra duplicación
}
```

**DTOs protegen contra cambios en el Model**
```java
// Cambios en Model no afectan a Vista (usa DTOs)
// Cambios en Vista no afectan a Model
public class AgregarPaciente {
    // Usa PacienteDto, no Paciente directamente
    // Protegido contra cambios en Paciente
}
```

---

## 🔧 Patrones de Diseño Adicionales

### **Singleton Pattern**
Patrón creacional que garantiza una única instancia de una clase.

#### ✅ Ejemplos:

**`ControllerFactory`**
```java
public class ControllerFactory {
    private static ControllerFactory instance;
    
    public static synchronized ControllerFactory getInstance() {
        if (instance == null) {
            instance = new ControllerFactory();
        }
        return instance;
    }
}
```

**`SessionManager`**
```java
public class SessionManager {
    private static SessionManager instance;
    
    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
}
```

**Controladores (mediante Factory)**
```java
public class PacienteController {
    private static PacienteController pacienteController;
    
    public static PacienteController createInstance(...) {
        if (pacienteController == null) {
            pacienteController = new PacienteController(...);
        }
        return pacienteController;
    }
}
```

---

### **Factory Pattern**
Patrón creacional para crear objetos sin especificar la clase exacta.

#### ✅ Ejemplos:

**`ControllerFactory`**
```java
public class ControllerFactory {
    // Factory para crear controladores con dependencias
    public PacienteController getPacienteController() {
        if (pacienteController == null) {
            pacienteController = PacienteController.createInstance(pacienteDao, peticionDao);
        }
        return pacienteController;
    }
}
```

---

### **DAO Pattern (Data Access Object)**
Patrón estructural para abstraer el acceso a datos.

#### ✅ Ejemplos:

**Estructura DAO completa**
```java
// Interfaz
public interface IPacienteDao {
    List<Paciente> getAll() throws Exception;
    void save(Paciente paciente) throws Exception;
    // ...
}

// Implementación
public class PacienteDao extends GenericDAO<Paciente> implements IPacienteDao {
    // Implementación específica
}
```

---

### **DTO Pattern (Data Transfer Object)**
Patrón estructural para transferir datos entre capas.

#### ✅ Ejemplos:

**DTOs separados del Model**
```java
// Model
public class Paciente extends Persona { ... }

// DTO
public class PacienteDto {
    // Objeto plano para transferencia
    // Sin lógica de negocio
}
```

---

### **Mapper Pattern**
Patrón estructural para convertir entre objetos.

#### ✅ Ejemplos:

**Mappers para conversión**
```java
public class PacienteMapper {
    public static Paciente toModel(PacienteDto dto) { ... }
    public static PacienteDto toDto(Paciente paciente) { ... }
}
```

---

### **Template Method Pattern**
Patrón de comportamiento que define el esqueleto de un algoritmo.

#### ✅ Ejemplos:

**`GenericDAO<T>` define template**
```java
public abstract class GenericDAO<T> {
    // Métodos template que definen el algoritmo
    public List<T> getAll() { ... }
    public void save(T obj) { ... }
    // Las clases hijas usan estos métodos sin modificarlos
}
```

---

### **Strategy Pattern (implícito)**
Interfaces permiten diferentes estrategias de implementación.

#### ✅ Ejemplos:

**Diferentes estrategias de persistencia**
```java
// Estrategia actual: JSON
public class PacienteDao implements IPacienteDao { ... }

```

---

