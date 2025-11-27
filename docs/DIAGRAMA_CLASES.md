# Diagrama de Clases - Sistema de Gestión de Laboratorio

## Índice
1. [Modelo de Dominio](#modelo-de-dominio)
2. [Enumeraciones](#enumeraciones)
3. [Data Transfer Objects (DTOs)](#data-transfer-objects-dtos)
4. [Controladores](#controladores)
5. [Capa de Acceso a Datos (DAO)](#capa-de-acceso-a-datos-dao)
6. [Mappers](#mappers)
7. [Utilidades](#utilidades)
8. [Factory](#factory)
9. [Diagrama de Relaciones](#diagrama-de-relaciones)

---

## Modelo de Dominio

### 1. Persona (Abstracta)
**Clase abstracta base para entidades que representan personas**

#### Atributos:
- `- nombre: String`
- `- dni: int`
- `- domicilio: String`
- `- email: String`
- `- apellido: String`

#### Métodos:
- `+ getNombre(): String`
- `+ setNombre(nombre: String): void`
- `+ getDni(): int`
- `+ setDni(dni: int): void`
- `+ getDomicilio(): String`
- `+ setDomicilio(domicilio: String): void`
- `+ getEmail(): String`
- `+ setEmail(email: String): void`
- `+ getApellido(): String`
- `+ setApellido(apellido: String): void`
- `+ toString(): String`

---

### 2. Paciente
**Representa un paciente del laboratorio. Hereda de Persona.**

#### Relaciones:
- **Herencia**: Extiende `Persona`
- **Asociación**: Con `Genero` (enum)

#### Atributos:
- `- id: int`
- `- edad: int`
- `- genero: Genero`

#### Métodos:
- `+ Paciente(id: int, nombre: String, dni: int, domicilio: String, email: String, apellido: String, edad: int, genero: Genero)`
- `+ getId(): int`
- `+ setId(id: int): void`
- `+ getEdad(): int`
- `+ setEdad(edad: int): void`
- `+ getGenero(): Genero`
- `+ setGenero(genero: Genero): void`
- `+ equals(o: Object): boolean`
- `+ hashCode(): int`
- `+ toString(): String`

---

### 3. Usuario
**Representa un usuario del sistema con credenciales de acceso**

#### Relaciones:
- **Asociación**: Con `Roles` (enum)

#### Atributos:
- `- id: int`
- `- nombre: String`
- `- contrasenia: String`
- `- nacimiento: Date`
- `- rol: Roles`

#### Métodos:
- `+ Usuario(id: int, nombre: String, contraseña: String, nacimiento: Date, rol: Roles)`
- `+ getId(): int`
- `+ setId(id: int): void`
- `+ getNombre(): String`
- `+ setNombre(nombre: String): void`
- `+ getContrasenia(): String`
- `+ setContrasenia(contrasenia: String): void`
- `+ getNacimiento(): Date`
- `+ setNacimiento(nacimiento: Date): void`
- `+ getRol(): Roles`
- `+ setRol(rol: Roles): void`
- `+ equals(o: Object): boolean`
- `+ hashCode(): int`

---

### 4. Sucursal
**Representa una sucursal del laboratorio**

#### Relaciones:
- **Asociación**: Con `Usuario` (1:1) - responsableTecnico

#### Atributos:
- `- id: int`
- `- numero: int`
- `- direccion: String`
- `- responsableTecnico: Usuario`

#### Métodos:
- `+ Sucursal(id: int, numero: int, direccion: String, responsableTecnico: Usuario)`
- `+ getId(): int`
- `+ setId(id: int): void`
- `+ getNumero(): int`
- `+ setNumero(numero: int): void`
- `+ getDireccion(): String`
- `+ setDireccion(direccion: String): void`
- `+ getResponsableTecnico(): Usuario`
- `+ setResponsableTecnico(responsableTecnico: Usuario): void`
- `+ equals(o: Object): boolean`
- `+ hashCode(): int`

---

### 5. Peticion
**Representa una petición de análisis clínicos realizada por un paciente en una sucursal**

#### Relaciones:
- **Asociación**: Con `Sucursal` (N:1)
- **Asociación**: Con `Paciente` (N:1)
- **Composición**: Con `Practica` (1:N) - una petición contiene múltiples prácticas

#### Atributos:
- `- id: int`
- `- obraSocial: String`
- `- fechaCarga: Date`
- `- fechaEntrega: Date`
- `- practicas: List<Practica>`
- `- sucursal: Sucursal`
- `- paciente: Paciente`
- `- estado: boolean`

#### Métodos:
- `+ Peticion(id: int, obraSocial: String, fechaCarga: Date, fechaEntrega: Date, sucursal: Sucursal, paciente: Paciente)`
- `+ Peticion(id: int, obraSocial: String, fechaCarga: Date, fechaEntrega: Date, sucursal: Sucursal, paciente: Paciente, practicas: List<Practica>)`
- `+ getId(): int`
- `+ setId(id: int): void`
- `+ getObraSocial(): String`
- `+ setObraSocial(obraSocial: String): void`
- `+ getFechaCarga(): Date`
- `+ setFechaCarga(fechaCarga: Date): void`
- `+ getFechaEntrega(): Date`
- `+ setFechaEntrega(fechaEntrega: Date): void`
- `+ getPracticas(): List<Practica>`
- `+ setPracticas(practicas: List<Practica>): void`
- `+ getSucursal(): Sucursal`
- `+ setSucursal(sucursal: Sucursal): void`
- `+ getPaciente(): Paciente`
- `+ setPaciente(paciente: Paciente): void`
- `+ tieneResultado(): boolean`
- `+ equals(o: Object): boolean`
- `+ hashCode(): int`

---

### 6. Practica
**Representa una práctica o estudio específico dentro de una petición**

#### Relaciones:
- **Composición**: Con `Resultado` (1:0..1) - una práctica puede tener un resultado opcional
- **Asociación**: Con `TipoResultado` (enum) - a través de Resultado

#### Atributos:
- `- id: int`
- `- codigo: int`
- `- nombre: String`
- `- grupo: int`
- `- horasFaltantes: float`
- `- resultado: Resultado`

#### Métodos:
- `+ Practica(id: int, codigo: int, nombre: String, grupo: int, horasFaltantes: float, resultado: Resultado)`
- `+ getId(): int`
- `+ setId(id: int): void`
- `+ getCodigo(): int`
- `+ setCodigo(codigo: int): void`
- `+ getNombre(): String`
- `+ setNombre(nombre: String): void`
- `+ getGrupo(): int`
- `+ setGrupo(grupo: int): void`
- `+ getHorasFaltantes(): float`
- `+ setHorasFaltantes(horasFaltantes: float): void`
- `+ getResultado(): Resultado`
- `+ setResultado(resultado: Resultado): void`
- `+ esCritica(): boolean`
- `+ esReservada(): boolean`
- `+ ocultarResultado(): void`

---

### 7. Resultado
**Representa el resultado de una práctica de laboratorio**

#### Relaciones:
- **Asociación**: Con `TipoResultado` (enum)

#### Atributos:
- `- valor: String`
- `- tipoResultado: TipoResultado`

#### Métodos:
- `+ Resultado(valor: String, tipoResultado: TipoResultado)`
- `+ getValor(): String`
- `+ setValor(valor: String): void`
- `+ getTipoResultado(): TipoResultado`
- `+ setTipoResultado(tipoResultado: TipoResultado): void`

---

## Enumeraciones

### 1. Genero
**Define los géneros posibles para pacientes**

#### Valores:
- `FEMENINO`
- `MASCULINO`
- `OTRO`

---

### 2. Roles
**Define los roles de usuario en el sistema**

#### Valores:
- `RECEPCIONISTA` - Gestión de pacientes y peticiones
- `LABORATORISTA` - Carga de resultados
- `ADMINISTRADOR` - Control total del sistema, puede ser responsable técnico

---

### 3. TipoResultado
**Define el tipo de resultado de una práctica**

#### Valores:
- `NORMAL` - Resultado dentro de parámetros normales
- `CRITICO` - Resultado que requiere atención inmediata
- `RESERVADO` - Resultado que debe retirarse en sucursal (confidencial)

---

## Data Transfer Objects (DTOs)

### 1. PacienteDto
**DTO para transferencia de datos de pacientes**

#### Atributos:
- `- id: int`
- `- edad: int`
- `- genero: Genero`
- `- nombre: String`
- `- dni: int`
- `- domicilio: String`
- `- email: String`
- `- apellido: String`

#### Métodos:
- Getters y Setters para todos los atributos
- `+ toString(): String`

---

### 2. UsuarioDto
**DTO para transferencia de datos de usuarios**

#### Atributos:
- `- id: int`
- `- nombre: String`
- `- contrasenia: String`
- `- nacimiento: Date`
- `- rol: Roles`

#### Métodos:
- Getters y Setters para todos los atributos

---

### 3. SucursalDto
**DTO para transferencia de datos de sucursales**

#### Atributos:
- `- id: int`
- `- numero: int`
- `- direccion: String`
- `- responsableTecnico: UsuarioDto`

#### Métodos:
- Getters y Setters para todos los atributos

---

### 4. PeticionDto
**DTO para transferencia de datos de peticiones**

#### Atributos:
- `- id: int`
- `- obraSocial: String`
- `- fechaCarga: Date`
- `- fechaEntrega: Date`
- `- practicas: List<PracticaDto>`
- `- sucursal: SucursalDto`
- `- paciente: PacienteDto`

#### Métodos:
- Getters y Setters para todos los atributos

---

### 5. PracticaDto
**DTO para transferencia de datos de prácticas**

#### Atributos:
- `- id: int`
- `- codigo: int`
- `- nombre: String`
- `- grupo: int`
- `- horasFaltantes: float`
- `- resultado: ResultadoDto`

#### Métodos:
- Getters y Setters para todos los atributos

---

### 6. ResultadoDto
**DTO para transferencia de datos de resultados**

#### Atributos:
- `- valor: String`
- `- tipoResultado: TipoResultado`

#### Métodos:
- Getters y Setters para todos los atributos

---

## Controladores

### 1. PacienteController
**Controlador para gestionar la lógica de negocio de pacientes. Implementa patrón Singleton.**

#### Relaciones:
- **Dependencia**: Con `IPacienteDao`
- **Dependencia**: Con `IPeticionDao`
- **Dependencia**: Con `PacienteMapper`
- **Usa**: `PacienteDto`, `Paciente`

#### Atributos:
- `- pacienteController: PacienteController` (static)
- `- pacienteDao: IPacienteDao` (final)
- `- peticionDao: IPeticionDao` (final)
- `- pacientes: List<Paciente>`

#### Métodos:
- `+ createInstance(pacienteDao: IPacienteDao, peticionDao: IPeticionDao): PacienteController` (static)
- `+ getAllPacientes(): List<PacienteDto>`
- `+ getPaciente(id: int): PacienteDto`
- `+ getPacientePorDni(dni: int): PacienteDto`
- `+ crearPaciente(pacienteDTO: PacienteDto): void throws PacienteYaExisteException, Exception`
- `+ modificarPaciente(pacienteDto: PacienteDto): void throws Exception`
- `+ borrarPaciente(id: int): void throws Exception`
- `- existePacienteCompleto(dni: int, apellido: String, nombre: String): boolean`
- `- puedeBorrarse(paciente: Paciente): boolean throws Exception`

#### Reglas de Negocio:
- No se puede crear un paciente con DNI, apellido y nombre duplicados
- Solo se puede eliminar un paciente si no tiene peticiones con resultados

---

### 2. PeticionController
**Controlador para gestionar la lógica de negocio de peticiones, prácticas y resultados. Implementa patrón Singleton.**

#### Relaciones:
- **Dependencia**: Con `IPeticionDao`
- **Dependencia**: Con `PeticionMapper`, `PacienteMapper`, `SucursalMapper`
- **Usa**: `PeticionDto`, `PracticaDto`, `ResultadoDto`, `Peticion`, `Practica`, `Resultado`

#### Atributos:
- `- peticionController: PeticionController` (static)
- `- peticionDao: IPeticionDao` (final)
- `- peticiones: List<Peticion>`

#### Métodos:
- `+ createInstance(peticionDao: IPeticionDao): PeticionController` (static)
- `+ getAllPeticiones(): List<PeticionDto>`
- `+ getPeticion(id: int): Optional<Peticion>`
- `+ getAllPracticasDePeticion(idPeticion: int): List<PracticaDto>`
- `+ crearPeticion(peticionDTO: PeticionDto): void throws Exception`
- `+ modificarPeticion(peticionDTO: PeticionDto): void throws Exception`
- `+ borrarPeticion(id: int): void throws Exception`
- `+ getPractica(id: int): Practica`
- `+ crearPractica(idPeticion: int, practicaDTO: PracticaDto): void throws Exception`
- `+ modificarPractica(practicaDTO: PracticaDto): void throws Exception`
- `+ borrarPractica(id: int): void throws Exception`
- `+ crearResultado(idPractica: int, resultadoDTO: ResultadoDto): void throws Exception`
- `+ modificarResultado(idPractica: int, resultadoDTO: ResultadoDto): void throws Exception`
- `+ eliminarResultado(idPractica: int): void throws Exception`
- `+ getPeticionesConResultadosCriticos(): List<PeticionDto>`
- `+ getPracticasConResultadosReservados(idPeticion: int): List<PracticaDto>`

#### Reglas de Negocio:
- Gestión completa de peticiones y sus prácticas asociadas
- Identifica peticiones con resultados críticos
- Oculta automáticamente resultados reservados

---

### 3. SucursalYUsuarioController
**Controlador para gestionar la lógica de negocio de sucursales y usuarios. Implementa patrón Singleton.**

#### Relaciones:
- **Dependencia**: Con `ISucursalDao`, `IUsuarioDao`, `IPeticionDao`
- **Dependencia**: Con `SucursalMapper`, `UsuarioMapper`
- **Dependencia**: Con `PasswordUtil`
- **Usa**: `SucursalDto`, `UsuarioDto`, `Sucursal`, `Usuario`

#### Atributos:
- `- sucursalController: SucursalYUsuarioController` (static)
- `- sucursalDao: ISucursalDao` (final)
- `- peticionDao: IPeticionDao` (final)
- `- usuarioDao: IUsuarioDao` (final)
- `- sucursales: List<Sucursal>`
- `- usuarios: List<Usuario>`

#### Métodos:
- `+ createInstance(sucursalDao: ISucursalDao, usuarioDao: IUsuarioDao, peticionDao: IPeticionDao): SucursalYUsuarioController` (static)
- `+ getAllSucursales(): List<SucursalDto>`
- `+ getSucursalPorId(id: int): SucursalDto`
- `+ crearSucursal(sucursalDTO: SucursalDto): void throws Exception`
- `+ modificarSucursal(sucursalDTO: SucursalDto): void throws Exception`
- `+ borrarSucursal(id: int): void throws Exception`
- `+ getUsuario(id: int): UsuarioDto`
- `+ getAllUsuarios(): List<UsuarioDto>`
- `+ getUsuariosParaResponsableTecnico(): List<UsuarioDto>`
- `+ crearUsuario(usuarioDTO: UsuarioDto): Usuario throws Exception`
- `+ modificarUsuario(usuarioDTO: UsuarioDto): void throws Exception`
- `+ eliminarUsuario(id: int): void throws Exception`
- `+ autenticarUsuario(nombreUsuario: String, contraseniaPlana: String): UsuarioDto`
- `+ getUsuarioPorNombre(nombreUsuario: String): UsuarioDto`
- `- getSucursalRandom(sucursalEliminadaId: int): Sucursal throws Exception`
- `- puedeBorrarse(sucursal: Sucursal): boolean throws Exception`
- `- esRolValidoParaResponsable(rol: Roles): boolean`
- `- esResponsableDeSucursal(usuarioId: int): boolean`

#### Reglas de Negocio:
- Solo usuarios con rol ADMINISTRADOR pueden ser responsables técnicos
- Al eliminar una sucursal sin resultados, las peticiones se derivan automáticamente a otra sucursal
- No se puede cambiar el rol de un usuario si es responsable técnico
- No se puede eliminar un usuario si es responsable técnico de alguna sucursal
- Autenticación con hash de contraseñas

---

## Capa de Acceso a Datos (DAO)

### 1. GenericDAO<T> (Abstracta)
**Clase abstracta genérica que proporciona operaciones CRUD básicas para persistencia en archivos JSON**

#### Relaciones:
- **Usa**: Gson (biblioteca externa)

#### Atributos:
- `# clase: Class<T>` (final)
- `# archivo: File`

#### Métodos:
- `+ GenericDAO(clase: Class<T>, file: String) throws Exception`
- `+ getAll(): List<T> throws Exception`
- `+ getAll(clase: Class<T>): List<T> throws Exception`
- `+ save(obj: T): void throws Exception`
- `+ saveAll(list: List<T>): void throws Exception`
- `+ update(obj: T): boolean throws Exception`
- `+ delete(id: int): boolean throws Exception`
- `+ search(id: int): T throws FileNotFoundException`
- `+ search(id: int, clase: Class<T>): T throws FileNotFoundException`
- `+ getLastInsertId(): int throws Exception`
- `- getObjectId(obj: T): int throws Exception`

---

### 2. IPacienteDao (Interfaz)
**Interfaz para operaciones de acceso a datos de Paciente**

#### Métodos:
- `+ getAll(): List<Paciente> throws Exception`
- `+ save(paciente: Paciente): void throws Exception`
- `+ update(paciente: Paciente): boolean throws Exception`
- `+ delete(id: int): boolean throws Exception`
- `+ search(id: int): Paciente throws Exception`

---

### 3. IPeticionDao (Interfaz)
**Interfaz para operaciones de acceso a datos de Peticion**

#### Métodos:
- `+ getAll(): List<Peticion> throws Exception`
- `+ save(peticion: Peticion): void throws Exception`
- `+ update(peticion: Peticion): boolean throws Exception`
- `+ delete(id: int): boolean throws Exception`
- `+ search(id: int): Peticion throws Exception`

---

### 4. ISucursalDao (Interfaz)
**Interfaz para operaciones de acceso a datos de Sucursal**

#### Métodos:
- `+ getAll(): List<Sucursal> throws Exception`
- `+ save(sucursal: Sucursal): void throws Exception`
- `+ update(sucursal: Sucursal): boolean throws Exception`
- `+ delete(id: int): boolean throws Exception`
- `+ search(id: int): Sucursal throws Exception`

---

### 5. IUsuarioDao (Interfaz)
**Interfaz para operaciones de acceso a datos de Usuario**

#### Métodos:
- `+ getAll(): List<Usuario> throws Exception`
- `+ save(usuario: Usuario): void throws Exception`
- `+ update(usuario: Usuario): boolean throws Exception`
- `+ delete(id: int): boolean throws Exception`
- `+ search(id: int): Usuario throws Exception`

---

### 6. PacienteDao
**Implementación concreta de IPacienteDao. Extiende GenericDAO<Paciente>**

#### Relaciones:
- **Herencia**: Extiende `GenericDAO<Paciente>`
- **Implementación**: Implementa `IPacienteDao`

#### Atributos:
- `- fileName: String = "src/main/resources/pacientes.json"` (static final)

#### Métodos:
- `+ PacienteDao() throws Exception`

---

### 7. PeticionDao
**Implementación concreta de IPeticionDao. Extiende GenericDAO<Peticion>**

#### Relaciones:
- **Herencia**: Extiende `GenericDAO<Peticion>`
- **Implementación**: Implementa `IPeticionDao`

#### Atributos:
- `- fileName: String = "src/main/resources/peticiones.json"` (static final)

#### Métodos:
- `+ PeticionDao() throws Exception`

---

### 8. SucursalDao
**Implementación concreta de ISucursalDao. Extiende GenericDAO<Sucursal>**

#### Relaciones:
- **Herencia**: Extiende `GenericDAO<Sucursal>`
- **Implementación**: Implementa `ISucursalDao`

#### Atributos:
- `- fileName: String = "src/main/resources/sucursales.json"` (static final)

#### Métodos:
- `+ SucursalDao() throws Exception`

---

### 9. UsuarioDao
**Implementación concreta de IUsuarioDao. Extiende GenericDAO<Usuario>**

#### Relaciones:
- **Herencia**: Extiende `GenericDAO<Usuario>`
- **Implementación**: Implementa `IUsuarioDao`

#### Atributos:
- `- fileName: String = "src/main/resources/usuarios.json"` (static final)

#### Métodos:
- `+ UsuarioDao() throws Exception`

---

## Mappers

**Los Mappers convierten entre objetos del Modelo y DTOs. Todos tienen constructor privado y métodos estáticos.**

### 1. PacienteMapper
**Convierte entre Paciente y PacienteDto**

#### Métodos:
- `+ toModel(pacienteDto: PacienteDto): Paciente` (static)
- `+ toDto(paciente: Paciente): PacienteDto` (static)

---

### 2. UsuarioMapper
**Convierte entre Usuario y UsuarioDto**

#### Métodos:
- `+ toModel(usuarioDto: UsuarioDto): Usuario` (static)
- `+ toDto(usuario: Usuario): UsuarioDto` (static)

---

### 3. SucursalMapper
**Convierte entre Sucursal y SucursalDto**

#### Métodos:
- `+ toModel(sucursalDto: SucursalDto): Sucursal` (static)
- `+ toDto(sucursal: Sucursal): SucursalDto` (static)

---

### 4. PeticionMapper
**Convierte entre Peticion/Practica/Resultado y sus DTOs correspondientes**

#### Métodos:
- `+ toModel(peticionDto: PeticionDto): Peticion` (static)
- `+ toDto(peticion: Peticion): PeticionDto` (static)
- `+ toModel(practicaDto: PracticaDto): Practica` (static)
- `+ toDto(practica: Practica): PracticaDto` (static)
- `+ toModel(resultadoDto: ResultadoDto): Resultado` (static)
- `+ toDto(resultado: Resultado): ResultadoDto` (static)

---

## Utilidades

### 1. SessionManager
**Gestiona la sesión del usuario autenticado. Implementa patrón Singleton.**

#### Relaciones:
- **Usa**: `UsuarioDto`

#### Atributos:
- `- instance: SessionManager` (static)
- `- usuarioActual: UsuarioDto`

#### Métodos:
- `+ getInstance(): SessionManager` (static, synchronized)
- `+ setUsuarioActual(usuario: UsuarioDto): void`
- `+ getUsuarioActual(): UsuarioDto`
- `+ haySesionActiva(): boolean`
- `+ cerrarSesion(): void`
- `+ getNombreUsuario(): String`
- `+ getRolUsuario(): Roles`

---

### 2. PasswordUtil
**Utilidad para operaciones con contraseñas (hashing)**

#### Métodos:
- `+ hashPassword(password: String): String` (static)

---

### 3. ValidacionUtil
**Utilidad para validaciones de datos**

---

### 4. DateUtil
**Utilidad para manejo de fechas**

---

### 5. PermissionManager
**Gestiona permisos según roles. Implementa patrón Singleton.**

#### Atributos:
- Mapas de permisos por rol

#### Métodos:
- `+ getInstance(): PermissionManager` (static)
- Métodos de verificación de permisos

---

### 6. ResultadoUtil
**Utilidad para operaciones con resultados**

---

### 7. StyleUtils
**Utilidad para estilos de interfaz gráfica**

---

## Factory

### ControllerFactory
**Factory para crear y gestionar instancias de controladores con inyección de dependencias. Implementa patrón Singleton y Factory.**

#### Relaciones:
- **Crea**: `PacienteController`, `PeticionController`, `SucursalYUsuarioController`
- **Crea**: `PacienteDao`, `PeticionDao`, `SucursalDao`, `UsuarioDao`

#### Atributos:
- `- instance: ControllerFactory` (static)
- `- pacienteDao: IPacienteDao`
- `- peticionDao: IPeticionDao`
- `- sucursalDao: ISucursalDao`
- `- usuarioDao: IUsuarioDao`
- `- pacienteController: PacienteController`
- `- peticionController: PeticionController`
- `- sucursalYUsuarioController: SucursalYUsuarioController`

#### Métodos:
- `+ getInstance(): ControllerFactory` (static, synchronized)
- `+ getPacienteController(): PacienteController throws Exception`
- `+ getPeticionController(): PeticionController throws Exception`
- `+ getSucursalYUsuarioController(): SucursalYUsuarioController throws Exception`
- `+ reset(): void`
- `- initializeDAOs(): void throws Exception`

---

## Diagrama de Relaciones

### Relaciones de Herencia
```
Persona (abstracta)
    ↑
    |
Paciente

GenericDAO<T> (abstracta)
    ↑
    |─── PacienteDao (implementa IPacienteDao)
    |─── PeticionDao (implementa IPeticionDao)
    |─── SucursalDao (implementa ISucursalDao)
    └─── UsuarioDao (implementa IUsuarioDao)
```

### Relaciones de Composición y Agregación

#### Modelo de Dominio
```
Peticion (1) ──◆─(N) Practica
              └──(N:1)── Sucursal
              └──(N:1)── Paciente

Practica (1) ──◆─(0..1) Resultado

Sucursal (1) ───(1) Usuario [responsableTecnico]

Resultado (1) ───(1) TipoResultado [enum]
Paciente (1) ───(1) Genero [enum]
Usuario (1) ───(1) Roles [enum]
```

#### Controladores y DAOs
```
PacienteController ──uses──> IPacienteDao
                   ──uses──> IPeticionDao
                   ──uses──> PacienteMapper

PeticionController ──uses──> IPeticionDao
                   ──uses──> PeticionMapper

SucursalYUsuarioController ──uses──> ISucursalDao
                           ──uses──> IUsuarioDao
                           ──uses──> IPeticionDao
                           ──uses──> SucursalMapper
                           ──uses──> UsuarioMapper
                           ──uses──> PasswordUtil
```

#### Factory y Singleton
```
ControllerFactory ──creates──> PacienteController
                  ──creates──> PeticionController
                  ──creates──> SucursalYUsuarioController
                  ──creates──> PacienteDao
                  ──creates──> PeticionDao
                  ──creates──> SucursalDao
                  ──creates──> UsuarioDao

SessionManager ──uses──> UsuarioDto
```

### Relaciones Modelo ↔ DTO (vía Mappers)
```
Paciente ←──PacienteMapper──→ PacienteDto
Usuario ←──UsuarioMapper──→ UsuarioDto
Sucursal ←──SucursalMapper──→ SucursalDto
Peticion ←──PeticionMapper──→ PeticionDto
Practica ←──PeticionMapper──→ PracticaDto
Resultado ←──PeticionMapper──→ ResultadoDto
```

### Cardinalidades Principales

| Relación | Cardinalidad | Descripción |
|----------|--------------|-------------|
| Persona → Paciente | 1:1 | Herencia |
| Paciente → Genero | N:1 | Un paciente tiene un género |
| Usuario → Roles | N:1 | Un usuario tiene un rol |
| Sucursal → Usuario | N:1 | Una sucursal tiene un responsable técnico |
| Peticion → Sucursal | N:1 | Muchas peticiones pertenecen a una sucursal |
| Peticion → Paciente | N:1 | Muchas peticiones pertenecen a un paciente |
| Peticion → Practica | 1:N | Una petición contiene múltiples prácticas |
| Practica → Resultado | 1:0..1 | Una práctica puede tener 0 o 1 resultado |
| Resultado → TipoResultado | N:1 | Un resultado tiene un tipo |
| Controller → DAO | 1:N | Un controlador usa uno o más DAOs |
| DAO → GenericDAO | N:1 | Herencia de funcionalidad genérica |

---

## Patrones de Diseño Implementados

### 1. **Singleton**
- `PacienteController`
- `PeticionController`
- `SucursalYUsuarioController`
- `SessionManager`
- `ControllerFactory`
- `PermissionManager`

### 2. **Factory**
- `ControllerFactory` - Centraliza la creación de controladores y DAOs

### 3. **DAO (Data Access Object)**
- `GenericDAO<T>` - Encapsula acceso a datos
- `IPacienteDao`, `IPeticionDao`, `ISucursalDao`, `IUsuarioDao` - Interfaces
- Implementaciones concretas para cada entidad

### 4. **DTO (Data Transfer Object)**
- Separación entre objetos de dominio y objetos de transferencia
- Todos los DTOs en paquete dedicado

### 5. **Mapper**
- Conversión entre entidades del modelo y DTOs
- Centraliza la lógica de transformación

### 6. **Template Method**
- `GenericDAO<T>` define operaciones comunes
- Subclases especializan para cada entidad

### 7. **Dependency Injection**
- Controladores reciben DAOs en constructor
- Factory inyecta dependencias

---

## Principios SOLID Aplicados

### Single Responsibility Principle (SRP)
- Cada controlador maneja un dominio específico
- Mappers solo convierten entre tipos
- DAOs solo manejan persistencia

### Open/Closed Principle (OCP)
- `GenericDAO<T>` es extensible sin modificación
- Interfaces permiten diferentes implementaciones

### Liskov Substitution Principle (LSP)
- DAOs concretos pueden sustituir a sus interfaces
- `Paciente` puede sustituir a `Persona`

### Interface Segregation Principle (ISP)
- Interfaces DAO específicas por entidad
- No se fuerza implementación de métodos no usados

### Dependency Inversion Principle (DIP)
- Controladores dependen de interfaces DAO, no implementaciones concretas
- Factory inyecta dependencias

---

## Diagrama UML Textual Completo

```
┌─────────────────────────────────────────────────────────────────┐
│                      CAPA DE MODELO                              │
├─────────────────────────────────────────────────────────────────┤
│  <<abstract>>                                                    │
│  Persona                    Paciente                             │
│  ────────                   ────────                             │
│  - nombre: String           extends Persona                      │
│  - dni: int                 - id: int                            │
│  - domicilio: String        - edad: int                          │
│  - email: String            - genero: Genero                     │
│  - apellido: String         + equals()                           │
│                             + hashCode()                         │
│                                                                   │
│  Usuario                    Sucursal                             │
│  ────────                   ────────                             │
│  - id: int                  - id: int                            │
│  - nombre: String           - numero: int                        │
│  - contrasenia: String      - direccion: String                  │
│  - nacimiento: Date         - responsableTecnico: Usuario        │
│  - rol: Roles               + equals()                           │
│  + equals()                 + hashCode()                         │
│  + hashCode()                                                    │
│                                                                   │
│  Peticion                   Practica                             │
│  ────────                   ────────                             │
│  - id: int                  - id: int                            │
│  - obraSocial: String       - codigo: int                        │
│  - fechaCarga: Date         - nombre: String                     │
│  - fechaEntrega: Date       - grupo: int                         │
│  - estado: boolean          - horasFaltantes: float              │
│  - practicas: List          - resultado: Resultado               │
│  - sucursal: Sucursal       + esCritica(): boolean               │
│  - paciente: Paciente       + esReservada(): boolean             │
│  + tieneResultado()         + ocultarResultado()                 │
│  + equals()                                                      │
│  + hashCode()               Resultado                            │
│                             ────────                             │
│                             - valor: String                      │
│                             - tipoResultado: TipoResultado       │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                      ENUMERACIONES                               │
├─────────────────────────────────────────────────────────────────┤
│  <<enum>> Genero            <<enum>> Roles                       │
│  FEMENINO                   RECEPCIONISTA                        │
│  MASCULINO                  LABORATORISTA                        │
│  OTRO                       ADMINISTRADOR                        │
│                                                                   │
│  <<enum>> TipoResultado                                          │
│  NORMAL                                                          │
│  CRITICO                                                         │
│  RESERVADO                                                       │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                      CAPA DE DTOs                                │
├─────────────────────────────────────────────────────────────────┤
│  PacienteDto               UsuarioDto                            │
│  SucursalDto               PeticionDto                           │
│  PracticaDto               ResultadoDto                          │
│  (Mismos atributos que modelos, con getters/setters)            │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                    CAPA DE CONTROLADORES                         │
├─────────────────────────────────────────────────────────────────┤
│  PacienteController (Singleton)                                  │
│  ──────────────────                                              │
│  - pacienteDao: IPacienteDao                                     │
│  - peticionDao: IPeticionDao                                     │
│  - pacientes: List<Paciente>                                     │
│  + createInstance() [static]                                     │
│  + getAllPacientes()                                             │
│  + crearPaciente()                                               │
│  + modificarPaciente()                                           │
│  + borrarPaciente()                                              │
│                                                                   │
│  PeticionController (Singleton)                                  │
│  ──────────────────                                              │
│  - peticionDao: IPeticionDao                                     │
│  - peticiones: List<Peticion>                                    │
│  + createInstance() [static]                                     │
│  + getAllPeticiones()                                            │
│  + crearPeticion()                                               │
│  + modificarPeticion()                                           │
│  + crearPractica()                                               │
│  + crearResultado()                                              │
│  + getPeticionesConResultadosCriticos()                          │
│                                                                   │
│  SucursalYUsuarioController (Singleton)                          │
│  ──────────────────────────                                      │
│  - sucursalDao: ISucursalDao                                     │
│  - usuarioDao: IUsuarioDao                                       │
│  - peticionDao: IPeticionDao                                     │
│  - sucursales: List<Sucursal>                                    │
│  - usuarios: List<Usuario>                                       │
│  + createInstance() [static]                                     │
│  + getAllSucursales()                                            │
│  + getAllUsuarios()                                              │
│  + crearSucursal()                                               │
│  + crearUsuario()                                                │
│  + autenticarUsuario()                                           │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                      CAPA DE DAOs                                │
├─────────────────────────────────────────────────────────────────┤
│  <<abstract>> GenericDAO<T>                                      │
│  ───────────────────────────                                     │
│  # clase: Class<T>                                               │
│  # archivo: File                                                 │
│  + getAll(): List<T>                                             │
│  + save(obj: T)                                                  │
│  + update(obj: T)                                                │
│  + delete(id: int)                                               │
│  + search(id: int): T                                            │
│          ▲                                                       │
│          │ extends                                               │
│  ┌───────┴────────┬────────┬────────┐                           │
│  │                │        │        │                            │
│  PacienteDao  PeticionDao  SucursalDao  UsuarioDao               │
│  (implements  (implements  (implements  (implements              │
│   IPacienteDao) IPeticionDao) ISucursalDao) IUsuarioDao)         │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                      MAPPERS                                     │
├─────────────────────────────────────────────────────────────────┤
│  PacienteMapper                                                  │
│  + toModel(dto): Paciente [static]                               │
│  + toDto(model): PacienteDto [static]                            │
│                                                                   │
│  UsuarioMapper, SucursalMapper, PeticionMapper                   │
│  (misma estructura)                                              │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                   FACTORY Y UTILIDADES                           │
├─────────────────────────────────────────────────────────────────┤
│  ControllerFactory (Singleton)                                   │
│  ──────────────────                                              │
│  + getInstance() [static]                                        │
│  + getPacienteController()                                       │
│  + getPeticionController()                                       │
│  + getSucursalYUsuarioController()                               │
│                                                                   │
│  SessionManager (Singleton)                                      │
│  ──────────────                                                  │
│  - usuarioActual: UsuarioDto                                     │
│  + getInstance() [static]                                        │
│  + setUsuarioActual()                                            │
│  + getUsuarioActual()                                            │
│  + cerrarSesion()                                                │
│                                                                   │
│  PasswordUtil, ValidacionUtil, DateUtil, etc.                    │
└─────────────────────────────────────────────────────────────────┘
```

---

## Resumen de Arquitectura

El sistema implementa una **arquitectura en capas** con clara separación de responsabilidades:

1. **Capa de Modelo**: Entidades del dominio del negocio
2. **Capa de DTO**: Objetos de transferencia de datos
3. **Capa de Mappers**: Conversión entre modelos y DTOs
4. **Capa de Controladores**: Lógica de negocio
5. **Capa de DAO**: Acceso y persistencia de datos
6. **Capa de Utilidades**: Servicios transversales
7. **Capa de Vista**: Interfaz gráfica (Swing)

Esta arquitectura facilita:
- **Mantenibilidad**: Cambios aislados por capa
- **Testabilidad**: Cada componente es independiente
- **Escalabilidad**: Fácil agregar nuevas funcionalidades
- **Reutilización**: Componentes genéricos y especializados

