# 📊 Diagramas de Flujo del Sistema de Gestión de Laboratorio

Este documento contiene los diagramas de flujo completos del sistema, desde la inicialización hasta las operaciones CRUD.

---

## 📋 Índice

1. [Flujo General del Sistema](#flujo-general-del-sistema)
2. [Flujo de Inicialización](#flujo-de-inicialización)
3. [Flujo de Autenticación](#flujo-de-autenticación)
4. [Flujo de Operación CRUD (Crear)](#flujo-de-operación-crud-crear)
5. [Flujo de Operación CRUD (Leer)](#flujo-de-operación-crud-leer)
6. [Flujo de Operación CRUD (Actualizar)](#flujo-de-operación-crud-actualizar)
7. [Flujo de Operación CRUD (Eliminar)](#flujo-de-operación-crud-eliminar)
8. [Arquitectura de Capas](#arquitectura-de-capas)

---

## 🔄 Flujo General del Sistema

```mermaid
flowchart TD
    Start([Inicio de la Aplicación]) --> Init{¿Desde Main.java<br/>o Menu.java?}
    
    Init -->|Main.java| TestFlow[Inicializar Sistema<br/>Ejecutar Tests]
    Init -->|Menu.java| GUIFlow[Inicializar GUI]
    
    TestFlow --> End1([Fin - Tests Completados])
    
    GUIFlow --> Factory[ControllerFactory.getInstance<br/>Singleton Pattern]
    Factory --> CreateDAOs[Crear DAOs:<br/>PacienteDao, PeticionDao, etc.]
    CreateDAOs --> CreateControllers[Crear Controladores:<br/>PacienteController, etc.]
    CreateControllers --> Login[Mostrar LoginWindow]
    
    Login --> Auth{Autenticación<br/>Exitosa?}
    Auth -->|No| Login
    Auth -->|Sí| Session[SessionManager.setUsuarioActual]
    Session --> Menu[Mostrar Menu Principal]
    
    Menu --> NavBar[BarraNavegacion]
    NavBar --> Views{Seleccionar Vista}
    
    Views -->|Pacientes| PacientesView[PacientesTodas]
    Views -->|Peticiones| PeticionesView[PeticionesTodas]
    Views -->|Sucursales| SucursalesView[SucursalTodas]
    Views -->|Usuarios| UsuariosView[UsuariosTodos]
    Views -->|Críticas| CriticasView[PeticionConResultadosCriticos]
    
    PacientesView --> CRUD[Operaciones CRUD]
    PeticionesView --> CRUD
    SucursalesView --> CRUD
    UsuariosView --> CRUD
    CriticasView --> CRUD
    
    CRUD --> Menu
    
    style Start fill:#e1f5ff
    style End1 fill:#ffe1f5
    style Factory fill:#fff4e1
    style Auth fill:#ffe1f5
    style CRUD fill:#e1ffe1
```

---

## 🚀 Flujo de Inicialización

```mermaid
flowchart TD
    Start([Inicio]) --> Main[Main.main o Menu.main]
    
    Main --> Factory[ControllerFactory.getInstance]
    Factory --> CheckInstance{¿Instance<br/>existe?}
    
    CheckInstance -->|Sí| ReturnInstance[Retornar Instance]
    CheckInstance -->|No| CreateFactory[Crear ControllerFactory]
    
    CreateFactory --> InitDAOs[initializeDAOs]
    InitDAOs --> CreatePacienteDao[new PacienteDao]
    InitDAOs --> CreatePeticionDao[new PeticionDao]
    InitDAOs --> CreateSucursalDao[new SucursalDao]
    InitDAOs --> CreateUsuarioDao[new UsuarioDao]
    
    CreatePacienteDao --> GetControllers[Obtener Controladores]
    CreatePeticionDao --> GetControllers
    CreateSucursalDao --> GetControllers
    CreateUsuarioDao --> GetControllers
    
    GetControllers --> GetPacienteCtrl[getPacienteController]
    GetControllers --> GetPeticionCtrl[getPeticionController]
    GetControllers --> GetSucursalCtrl[getSucursalYUsuarioController]
    
    GetPacienteCtrl --> CheckPacienteCtrl{¿Controller<br/>existe?}
    CheckPacienteCtrl -->|No| CreatePacienteCtrl[PacienteController.createInstance<br/>con DAOs inyectados]
    CheckPacienteCtrl -->|Sí| ReturnPacienteCtrl[Retornar Controller]
    
    CreatePacienteCtrl --> LoadPacientes[DAO.getAll<br/>Cargar desde JSON]
    LoadPacientes --> ReturnPacienteCtrl
    
    GetPeticionCtrl --> CheckPeticionCtrl{¿Controller<br/>existe?}
    CheckPeticionCtrl -->|No| CreatePeticionCtrl[PeticionController.createInstance<br/>con DAO inyectado]
    CheckPeticionCtrl -->|Sí| ReturnPeticionCtrl[Retornar Controller]
    
    CreatePeticionCtrl --> LoadPeticiones[DAO.getAll<br/>Cargar desde JSON]
    LoadPeticiones --> ReturnPeticionCtrl
    
    GetSucursalCtrl --> CheckSucursalCtrl{¿Controller<br/>existe?}
    CheckSucursalCtrl -->|No| CreateSucursalCtrl[SucursalYUsuarioController.createInstance<br/>con DAOs inyectados]
    CheckSucursalCtrl -->|Sí| ReturnSucursalCtrl[Retornar Controller]
    
    CreateSucursalCtrl --> LoadSucursales[DAO.getAll<br/>Cargar desde JSON]
    LoadSucursales --> ReturnSucursalCtrl
    
    ReturnInstance --> End([Sistema Inicializado])
    ReturnPacienteCtrl --> End
    ReturnPeticionCtrl --> End
    ReturnSucursalCtrl --> End
    
    style Start fill:#e1f5ff
    style End fill:#e1ffe1
    style Factory fill:#fff4e1
    style CreateFactory fill:#fff4e1
    style InitDAOs fill:#ffe1f5
```

---

## 🔐 Flujo de Autenticación

```mermaid
flowchart TD
    Start([Usuario Abre LoginWindow]) --> Input[Usuario Ingresa<br/>Nombre y Contraseña]
    
    Input --> ValidateFields{Campos<br/>Válidos?}
    ValidateFields -->|No| ShowError1[Mostrar Error:<br/>Campos Requeridos]
    ShowError1 --> Input
    
    ValidateFields -->|Sí| DisableButtons[Deshabilitar Botones]
    DisableButtons --> GetController[Obtener SucursalYUsuarioController<br/>desde Factory]
    
    GetController --> Authenticate[controller.autenticarUsuario<br/>nombreUsuario, contraseña]
    
    Authenticate --> GetUsers[DAO.getAll Usuarios<br/>desde JSON]
    GetUsers --> FindUser{¿Usuario<br/>existe?}
    
    FindUser -->|No| AuthFailed[Retornar null]
    FindUser -->|Sí| HashPassword[PasswordUtil.hashPassword<br/>contraseña ingresada]
    
    HashPassword --> CompareHash{¿Hash coincide<br/>con hash almacenado?}
    CompareHash -->|No| AuthFailed
    CompareHash -->|Sí| AuthSuccess[Retornar UsuarioDto]
    
    AuthFailed --> CheckResult{¿Resultado<br/>de autenticación?}
    AuthSuccess --> CheckResult
    
    CheckResult -->|null| ShowError2[Mostrar Error:<br/>Credenciales Incorrectas]
    ShowResult --> EnableButtons[Rehabilitar Botones]
    ShowError2 --> ClearPassword[Limpiar Campo Contraseña]
    ClearPassword --> Input
    
    CheckResult -->|UsuarioDto| SetSession[SessionManager.setUsuarioActual<br/>usuario]
    SetSession --> ShowWelcome[Mostrar Mensaje:<br/>Bienvenido + Rol]
    ShowWelcome --> OpenMenu[Menu.createAndShowMenu]
    OpenMenu --> CloseLogin[Cerrar LoginWindow]
    CloseLogin --> End([Menú Principal])
    
    style Start fill:#e1f5ff
    style End fill:#e1ffe1
    style Authenticate fill:#fff4e1
    style AuthSuccess fill:#e1ffe1
    style AuthFailed fill:#ffe1f5
    style SetSession fill:#e1ffe1
```

---

## ➕ Flujo de Operación CRUD (Crear)

```mermaid
flowchart TD
    Start([Usuario hace clic en<br/>Agregar/Guardar]) --> GetData[Vista: Obtener datos<br/>de campos de formulario]
    
    GetData --> ValidateVista{Validaciones<br/>en Vista}
    ValidateVista -->|Campos vacíos| ShowError1[Mostrar Error:<br/>Campos Requeridos]
    ShowError1 --> GetData
    
    ValidateVista -->|DNI inválido| ShowError2[ValidacionUtil.esDniValido<br/>Mostrar Error DNI]
    ShowError2 --> GetData
    
    ValidateVista -->|Edad inválida| ShowError3[ValidacionUtil.esEdadValida<br/>Mostrar Error Edad]
    ShowError3 --> GetData
    
    ValidateVista -->|Válido| CreateDTO[Crear DTO<br/>PacienteDto, PeticionDto, etc.]
    
    CreateDTO --> CallController[Vista: controller.crearPaciente<br/>o crearPeticion, etc.]
    
    CallController --> ControllerValidate{Validaciones<br/>en Controller}
    ControllerValidate -->|Paciente existe| CheckExists[existePacienteCompleto<br/>DNI + Apellido + Nombre]
    
    CheckExists -->|Sí| ThrowException[Lanzar<br/>PacienteYaExisteException]
    ThrowException --> ShowError4[Mostrar Error:<br/>Paciente ya existe]
    ShowError4 --> GetData
    
    CheckExists -->|No| ConvertToModel[Mapper.toModel<br/>DTO → Model]
    ControllerValidate -->|Otra validación| ConvertToModel
    
    ConvertToModel --> SaveDAO[DAO.save<br/>model]
    
    SaveDAO --> WriteJSON[GenericDAO: Escribir<br/>a archivo JSON]
    WriteJSON --> UpdateList[Controller: Agregar<br/>a lista en memoria]
    
    UpdateList --> ReturnSuccess[Retornar éxito]
    ReturnSuccess --> UpdateView[Vista: Actualizar tabla<br/>actualizarTablaPacientes]
    
    UpdateView --> ShowSuccess[Mostrar Mensaje:<br/>✅ Creado exitosamente]
    ShowSuccess --> CloseDialog[Cerrar Diálogo]
    CloseDialog --> End([Operación Completada])
    
    style Start fill:#e1f5ff
    style End fill:#e1ffe1
    style ValidateVista fill:#ffe1f5
    style ControllerValidate fill:#ffe1f5
    style ConvertToModel fill:#fff4e1
    style SaveDAO fill:#e1ffe1
    style WriteJSON fill:#e1ffe1
```

---

## 📖 Flujo de Operación CRUD (Leer)

```mermaid
flowchart TD
    Start([Usuario abre vista<br/>PacientesTodas, etc.]) --> GetController[Obtener Controller<br/>desde Factory o Main]
    
    GetController --> CallGetAll[Vista: controller.getAllPacientes<br/>o getAllPeticiones, etc.]
    
    CallGetAll --> ControllerGetAll[Controller: getAllPacientes]
    
    ControllerGetAll --> GetFromMemory{¿Datos en<br/>memoria?}
    
    GetFromMemory -->|Sí| ConvertToDTO[Stream: Model → DTO<br/>Mapper.toDto]
    GetFromMemory -->|No| LoadFromDAO[DAO.getAll<br/>desde JSON]
    
    LoadFromDAO --> ReadJSON[GenericDAO: Leer<br/>archivo JSON línea por línea]
    ReadJSON --> ParseJSON[Gson: fromJson<br/>JSON → Model]
    ParseJSON --> AddToList[Agregar a lista<br/>en memoria]
    AddToList --> ConvertToDTO
    
    ConvertToDTO --> ReturnList[Retornar List<DTO>]
    
    ReturnList --> PopulateTable[Vista: Poblar JTable<br/>con datos]
    
    PopulateTable --> AddListeners[Agregar Listeners:<br/>Editar, Borrar, etc.]
    
    AddListeners --> End([Vista Mostrada])
    
    style Start fill:#e1f5ff
    style End fill:#e1ffe1
    style LoadFromDAO fill:#fff4e1
    style ReadJSON fill:#fff4e1
    style ConvertToDTO fill:#e1ffe1
    style PopulateTable fill:#e1ffe1
```

---

## ✏️ Flujo de Operación CRUD (Actualizar)

```mermaid
flowchart TD
    Start([Usuario hace clic en<br/>Editar/Guardar]) --> GetData[Vista: Obtener datos<br/>del formulario]
    
    GetData --> ValidateVista{Validaciones<br/>en Vista}
    ValidateVista -->|Inválido| ShowError[Mostrar Error]
    ShowError --> GetData
    
    ValidateVista -->|Válido| CreateDTO[Crear DTO<br/>con datos actualizados]
    
    CreateDTO --> CallController[Vista: controller.modificarPaciente<br/>o modificarPeticion, etc.]
    
    CallController --> FindInMemory[Controller: Buscar en lista<br/>por ID]
    
    FindInMemory --> Found{¿Encontrado?}
    Found -->|No| ShowError2[Mostrar Error:<br/>No encontrado]
    ShowError2 --> GetData
    
    Found -->|Sí| UpdateModel[Actualizar atributos<br/>del Model en memoria]
    
    UpdateModel --> UpdateDAO[DAO.update<br/>model]
    
    UpdateDAO --> ReadAllJSON[GenericDAO: Leer<br/>todo el archivo JSON]
    ReadAllJSON --> FindInJSON{¿Encontrar<br/>objeto por ID?}
    
    FindInJSON -->|No| ShowError3[Error: No encontrado]
    ShowError3 --> GetData
    
    FindInJSON -->|Sí| UpdateInJSON[Actualizar objeto<br/>en lista JSON]
    UpdateInJSON --> WriteAllJSON[GenericDAO: Escribir<br/>todo de vuelta a JSON]
    
    WriteAllJSON --> ReturnSuccess[Retornar éxito]
    
    ReturnSuccess --> UpdateView[Vista: Actualizar tabla<br/>actualizarTablaPacientes]
    
    UpdateView --> ShowSuccess[Mostrar Mensaje:<br/>✅ Actualizado exitosamente]
    ShowSuccess --> CloseDialog[Cerrar Diálogo]
    CloseDialog --> End([Operación Completada])
    
    style Start fill:#e1f5ff
    style End fill:#e1ffe1
    style ValidateVista fill:#ffe1f5
    style UpdateModel fill:#fff4e1
    style UpdateDAO fill:#e1ffe1
    style WriteAllJSON fill:#e1ffe1
```

---

## 🗑️ Flujo de Operación CRUD (Eliminar)

```mermaid
flowchart TD
    Start([Usuario hace clic en<br/>Borrar/Eliminar]) --> ConfirmDialog[Mostrar Diálogo<br/>de Confirmación]
    
    ConfirmDialog --> UserConfirm{¿Usuario<br/>confirma?}
    UserConfirm -->|No| Cancel[Cancelar Operación]
    Cancel --> End1([Operación Cancelada])
    
    UserConfirm -->|Sí| GetID[Obtener ID<br/>del objeto a borrar]
    
    GetID --> CallController[Vista: controller.borrarPaciente<br/>o borrarPeticion, etc.]
    
    CallController --> FindInMemory[Controller: Buscar en lista<br/>por ID]
    
    FindInMemory --> Found{¿Encontrado?}
    Found -->|No| ShowError1[Mostrar Error:<br/>No encontrado]
    ShowError1 --> End1
    
    Found -->|Sí| CheckRules{¿Puede<br/>borrarse?}
    
    CheckRules -->|Paciente| CheckPeticiones[Verificar si tiene<br/>peticiones asociadas]
    CheckPeticiones --> HasPeticiones{¿Tiene<br/>peticiones?}
    HasPeticiones -->|Sí| ShowError2[Error: No puede borrarse<br/>tiene peticiones]
    ShowError2 --> End1
    
    HasPeticiones -->|No| CanDelete[Puede borrarse]
    
    CheckRules -->|Otra entidad| CanDelete
    
    CanDelete --> RemoveFromMemory[Remover de lista<br/>en memoria]
    
    RemoveFromMemory --> DeleteDAO[DAO.delete<br/>id]
    
    DeleteDAO --> ReadAllJSON[GenericDAO: Leer<br/>todo el archivo JSON]
    ReadAllJSON --> FindInJSON[Buscar objeto<br/>por ID en JSON]
    FindInJSON --> RemoveFromJSON[Remover objeto<br/>de lista JSON]
    RemoveFromJSON --> WriteAllJSON[GenericDAO: Escribir<br/>todo de vuelta a JSON]
    
    WriteAllJSON --> ReturnSuccess[Retornar éxito]
    
    ReturnSuccess --> UpdateView[Vista: Actualizar tabla<br/>actualizarTablaPacientes]
    
    UpdateView --> ShowSuccess[Mostrar Mensaje:<br/>✅ Eliminado exitosamente]
    ShowSuccess --> End2([Operación Completada])
    
    style Start fill:#e1f5ff
    style End1 fill:#ffe1f5
    style End2 fill:#e1ffe1
    style CheckRules fill:#ffe1f5
    style CanDelete fill:#e1ffe1
    style DeleteDAO fill:#e1ffe1
    style WriteAllJSON fill:#e1ffe1
```

---

## 🏗️ Arquitectura de Capas

```mermaid
flowchart TB
    subgraph "Capa de Presentación (Vista)"
        V1[LoginWindow]
        V2[Menu]
        V3[PacientesTodas]
        V4[AgregarPaciente]
        V5[EditarPaciente]
        V6[PeticionesTodas]
        V7[BarraNavegacion]
    end
    
    subgraph "Capa de Control (Controller)"
        C1[PacienteController]
        C2[PeticionController]
        C3[SucursalYUsuarioController]
    end
    
    subgraph "Capa de Mapeo (Mapper)"
        M1[PacienteMapper]
        M2[PeticionMapper]
    end
    
    subgraph "Capa de Acceso a Datos (DAO)"
        D1[PacienteDao]
        D2[PeticionDao]
        D3[SucursalDao]
        D4[UsuarioDao]
        D5[GenericDAO]
    end
    
    subgraph "Capa de Modelo (Domain)"
        MD1[Paciente]
        MD2[Peticion]
        MD3[Sucursal]
        MD4[Usuario]
    end
    
    subgraph "Capa de Utilidades"
        U1[ControllerFactory]
        U2[SessionManager]
        U3[ValidacionUtil]
        U4[PasswordUtil]
        U5[StyleUtils]
    end
    
    subgraph "Persistencia"
        JSON[(Archivos JSON)]
    end
    
    V1 --> C3
    V2 --> C1
    V2 --> C2
    V2 --> C3
    V3 --> C1
    V4 --> C1
    V5 --> C1
    V6 --> C2
    
    C1 --> M1
    C1 --> D1
    C2 --> M2
    C2 --> D2
    C3 --> D3
    C3 --> D4
    
    M1 --> MD1
    M2 --> MD2
    
    D1 --> D5
    D2 --> D5
    D3 --> D5
    D4 --> D5
    
    D5 --> JSON
    
    U1 --> C1
    U1 --> C2
    U1 --> C3
    U1 --> D1
    U1 --> D2
    U1 --> D3
    U1 --> D4
    
    V1 --> U2
    V2 --> U2
    V1 --> U3
    V4 --> U3
    C3 --> U4
    
    style V1 fill:#e1f5ff
    style C1 fill:#fff4e1
    style M1 fill:#ffe1f5
    style D1 fill:#e1ffe1
    style MD1 fill:#f0e1ff
    style U1 fill:#ffffe1
    style JSON fill:#ffe1e1
```

---

## 📝 Notas Importantes

### Patrones Aplicados

1. **Singleton**: `ControllerFactory`, `SessionManager`, Controladores
2. **Factory**: `ControllerFactory` para crear controladores
3. **Dependency Injection**: Controladores reciben DAOs por constructor
4. **DAO Pattern**: Abstracción de acceso a datos
5. **DTO Pattern**: Separación entre Model y DTO
6. **Mapper Pattern**: Conversión entre Model y DTO

### Flujos de Datos

- **Vista → Controller**: DTOs
- **Controller → DAO**: Models
- **DAO → JSON**: Serialización con Gson
- **JSON → DAO**: Deserialización con Gson
- **DAO → Controller**: Models
- **Controller → Vista**: DTOs

### Manejo de Errores

- Excepciones personalizadas: `PacienteYaExisteException`
- Mensajes de error amigables al usuario
- Logging de errores en consola

