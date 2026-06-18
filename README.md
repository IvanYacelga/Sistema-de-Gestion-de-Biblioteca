# 📚 BiblioSmart

Sistema de Gestión Bibliotecaria desarrollado para automatizar y optimizar los procesos de administración de recursos bibliográficos dentro de una biblioteca.

## 📖 Descripción

BiblioSmart permite gestionar usuarios, administrar el catálogo de libros, registrar préstamos y devoluciones, controlar multas por retrasos y garantizar el acceso seguro mediante mecanismos de autenticación.

El proyecto fue desarrollado siguiendo un modelo de proceso **Iterativo-Incremental** y utilizando la metodología **Scrum** para la gestión de actividades y seguimiento de avances.

## 🎯 Objetivos

- Automatizar los procesos bibliotecarios.
- Mejorar el control de préstamos y devoluciones.
- Gestionar eficientemente el catálogo de libros.
- Controlar multas generadas por retrasos.
- Garantizar un acceso seguro mediante autenticación de usuarios.

## 🚀 Funcionalidades

### 🔐 Autenticación de Usuarios
- Inicio de sesión.
- Validación de credenciales.
- Recuperación de contraseña.

### 👥 Gestión de Usuarios
- Registro de usuarios.
- Consulta de usuarios.
- Actualización de información.
- Eliminación de usuarios.

### 📚 Gestión de Libros
- Registro de libros.
- Consulta de libros.
- Modificación de información bibliográfica.
- Eliminación de libros.

### 📖 Gestión de Préstamos y Devoluciones
- Registro de préstamos.
- Registro de devoluciones.
- Validación de disponibilidad de libros.
- Control de fechas de préstamo.

### 💰 Gestión de Multas
- Generación automática de multas.
- Consulta de multas pendientes.
- Actualización del estado de multas.

---

## 🏗️ Arquitectura del Proyecto

El sistema fue desarrollado utilizando el patrón de arquitectura **MVC (Modelo - Vista - Controlador)**.

```text
BiblioSmart
│
├── Modelo
│   ├── Usuario
│   ├── Libro
│   ├── Prestamo
│   ├── Multa
│   └── Categoria
│
├── Vista
│   ├── Login
│   ├── Gestión de Usuarios
│   ├── Gestión de Libros
│   ├── Gestión de Préstamos
│   └── Gestión de Multas
│
└── Controlador
    ├── LoginControlador
    ├── UsuarioControlador
    └── Conexión PostgreSQL
```

---

## 🛠️ Tecnologías Utilizadas

### Lenguaje de Programación
- Java

### Interfaz Gráfica
- Java Swing

### Base de Datos
- PostgreSQL

### Conectividad
- JDBC

### Control de Versiones
- Git
- GitHub

### Gestión del Proyecto
- Jira

### Modelado y Diseño
- UML
- Draw.io
- PlantUML

---

## 🗄️ Base de Datos

El sistema utiliza PostgreSQL para almacenar la información relacionada con:

- Usuarios
- Libros
- Categorías
- Préstamos
- Devoluciones
- Multas

---

## 📂 Estructura del Proyecto

```text
src/
│
├── Controlador/
│   ├── ConexionPostgresql.java
│   ├── LoginControlador.java
│   └── UsuarioControlador.java
│
├── Modelo/
│   ├── Categoria.java
│   ├── Cliente.java
│   ├── Libro.java
│   ├── Login.java
│   ├── Multa.java
│   ├── Prestamo.java
│   └── UsuarioAdministrador.java
│
├── Vista/
│   ├── FrmLogin.java
│   ├── FrmMenu.java
│   ├── FrmRecuperacion.java
│   └── FrmRegistrarse.java
│
├── Vista.Cliente/
│   ├── FrmGestionClientes.java
│   ├── consultarCliente.java
│   ├── editarCliente.java
│   ├── eliminarCliente.java
│   └── registrarCliente.java
│
├── Vista.Libro/
│   ├── FrmGestionLibros.java
│   ├── buscarLibros.java
│   ├── eliminarLibro.java
│   └── registrarLibro.java
│
└── Vista.Prestamo/
    ├── FrmGestionPrestamos.java
    ├── prestarLibros.java
    ├── devolverLibros.java
    └── gestionMultas.java
```

---

## ⚙️ Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/usuario/BiblioSmart.git
```

### 2. Abrir el proyecto

Importar el proyecto en:

- NetBeans IDE
- Apache NetBeans

### 3. Configurar PostgreSQL

Crear la base de datos:

```sql
CREATE DATABASE bibliosmart;
```

Importar el script SQL correspondiente.

### 4. Configurar la conexión

Modificar los parámetros de conexión en:

```java
ConexionPostgresql.java
```

```java
String url = "jdbc:postgresql://localhost:5432/bibliosmart";
String usuario = "postgres";
String password = "tu_contraseña";
```

### 5. Ejecutar el proyecto

Ejecutar:

```text
main.java
```

---

## 📈 Metodología de Desarrollo

### Modelo de Proceso

- Iterativo-Incremental

### Metodología Ágil

- Scrum

### Iteraciones

| Iteración | Funcionalidad |
|------------|--------------|
| Iteración 1 | Autenticación de usuarios |
| Iteración 2 | Gestión de usuarios |
| Iteración 3 | Gestión de libros |
| Iteración 4 | Gestión de préstamos y devoluciones |
| Iteración 5 | Gestión de multas e integración final |

---

## 📋 Artefactos Generados

- Product Backlog
- Sprint Backlogs
- Casos de Uso
- Diagrama de Clases
- Diagrama Entidad-Relación
- Diagramas de Actividades
- Diagramas de Componentes
- Arquitectura en Capas
- Mockups de Interfaces
- Base de Datos PostgreSQL
- Código Fuente
- Documentación Técnica

---

## 👨‍💻 Equipo de Desarrollo

Proyecto desarrollado como parte de la asignatura de Ingeniería de Software.

### Integrantes

- Freddy Joel Fuentes Escobar
- Lennon Arias 
- Erick Sabando 
- Iván Yacelga 

---

## 📄 Licencia

Este proyecto fue desarrollado con fines académicos y educativos.

---


> Agregar captura del módulo de multas.
