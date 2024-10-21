
# ToDo Application

Este proyecto es una aplicación de lista de tareas (ToDo) creada utilizando **Spring Boot** para el backend y **React** para el frontend. La aplicación permite a los usuarios crear, editar, marcar como hecho/no hecho, eliminar tareas y aplicar filtros de búsqueda y ordenamiento.

## Tabla de Contenidos

1. [Requisitos](#requisitos)
2. [Instalación](#instalación)
3. [Ejecución](#ejecución)
4. [Uso de la API](#uso-de-la-api)
5. [Pruebas](#pruebas)
6. [Tecnologías Utilizadas](#tecnologías-utilizadas)

## Requisitos

Antes de ejecutar la aplicación, asegúrate de tener instalados los siguientes programas:

- **Java 17** o superior
- **Maven 3.6+**
- **Node.js** (versión 14+)
- **npm** o **yarn**

## Instalación

Sigue estos pasos para instalar el proyecto en tu máquina local.

### Backend (Spring Boot)

1. Clona el repositorio en tu máquina local:

   ```bash
   git clone https://github.com/usuario/repositorio.git
   ```

2. Navega al directorio del backend:

   ```bash
   cd repositorio/todoapp
   ```

3. Instala las dependencias del backend con **Maven**:

   ```bash
   mvn clean install
   ```

### Frontend (React)

1. Navega al directorio del frontend:

   ```bash
   cd repositorio/todoapp/frontend
   ```

2. Instala las dependencias del frontend con **npm** o **yarn**:

   ```bash
   npm install
   ```

## Ejecución

### Backend (Spring Boot)

Para ejecutar el backend, navega al directorio raíz del proyecto backend y ejecuta el siguiente comando:

```bash
mvn spring-boot:run
```

Esto levantará el servidor backend en **http://localhost:9090**.

### Frontend (React)

Navega al directorio del frontend y ejecuta el siguiente comando para levantar la aplicación:

```bash
npm run start
```

Esto abrirá la aplicación en el navegador en **http://localhost:8080**.

## Uso de la API

### Endpoints principales

1. **Obtener todas las tareas (paginadas y filtradas)**:
    - **GET** `/todos/filter`
    - Parámetros opcionales: `page`, `name`, `done`, `priority`, `sortByPriority`, `sortByDueDate`

2. **Crear una nueva tarea**:
    - **POST** `/todos`
    - Datos del cuerpo: `{ "text": "Nombre de la tarea", "priority": "HIGH|MEDIUM|LOW", "dueDate": "YYYY-MM-DD" }`

3. **Actualizar una tarea existente**:
    - **PUT** `/todos/{id}`
    - Datos del cuerpo: `{ "text": "Nuevo nombre", "priority": "HIGH|MEDIUM|LOW", "dueDate": "YYYY-MM-DD" }`

4. **Marcar tarea como hecha/no hecha**:
    - **POST** `/todos/{id}/done`
    - **PUT** `/todos/{id}/undone`

5. **Eliminar una tarea**:
    - **DELETE** `/todos/{id}`

## Pruebas

### Backend

Para ejecutar las pruebas del backend, navega al directorio del backend y ejecuta:

```bash
mvn test
```

Esto ejecutará todas las pruebas unitarias escritas para la aplicación.

### Frontend

Para ejecutar las pruebas del frontend, navega al directorio del frontend y ejecuta:

```bash
npm run test
```

Esto ejecutará las pruebas del frontend que estén configuradas.

## Tecnologías Utilizadas

- **Backend**: Java, Spring Boot, Maven
- **Frontend**: React, TypeScript
- **Pruebas**: JUnit, Mockito, React Testing Library
