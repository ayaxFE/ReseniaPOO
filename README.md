# Sistema de Reseñas y Reputación - Grupo N°3

## Descripción
Este módulo es parte del Trabajo Práctico Integrador de POO 2025. Se encarga de gestionar la creación de reseñas de productos, el listado de las mismas, el cálculo de promedios de calificación y la moderación (eliminación) de reseñas.

## Integrantes
* Facundo Aguilar
* Demian Farias
* Facundo de Renzo

## Tecnologías Utilizadas
* *Lenguaje:* Java 17
* *Framework:* Spring Boot 3.x
* *Base de Datos:* MySQL / H2
* *Gestor de Dependencias:* Maven
* *Frontend:* HTML5, CSS3, JavaScript

## Requisitos de Ejecución
Para correr este proyecto necesitas tener instalado:
1. Java JDK 17 o superior.
2. Maven (o usar el wrapper incluido mvnw).
3. MySQL (si no usas H2 en memoria).
4. PuTTY en Ejecución

## Configuración
Antes de iniciar, verificar el archivo src/main/resources/application.properties:
```properties
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/reviews_db
spring.datasource.username=root
spring.datasource.password=tupassword

----
Nota de Seguridad: La API está protegida mediante una API Key.
Clave Maestra: `clave_grupo8_keyPi`


Instalación y Ejecución

1.  **Clonar el repositorio:**

    bash
    git clone https://github.com/ayaxFE/ReseniaPOO.git
    

2.  **Compilar el proyecto:**
    Abra una terminal en la carpeta del proyecto y ejecute:

    bash
    ./mvnw clean install
    

3.  **Ejecutar la aplicación en PuTTY:**

    Desplegue en servidor SSH

-----

Acceso al Frontend

Hemos implementado una interfaz gráfica para probar todos los endpoints sin necesidad de herramientas externas.

1.  Inicie el servidor.
2.  Abra su navegador web.
3.  Ingrese a: **`http://localhost:8080/`**

Se podrá crear reseñas, ver el cálculo de promedios en tiempo real y eliminar reseñas.

-----

Documentación de la API (Endpoints)

1. Crear Reseña (7.1)

Crea una nueva opinión sobre un producto. Valida que el rating sea 1-5 y que el usuario no repita reseña.

  * **Método:** `POST`
  * **URL:** `/reviews`
  * **Header:** `X-API-Key: clave_grupo8_keyPi`
  * **Body (JSON):**

<!-- end list -->

json
{
  "productoSku": "BK-001",
  "usuarioId": "usuario_prueba",
  "rating": 5,
  "comentario": "Excelente producto, muy recomendado."
}


  * **Respuesta Exitosa (201 Created):**

<!-- end list -->

json
{
  "id": "rev12",
  "productoSku": "BK-001",
  "rating": 5,
  "comentario": "Excelente producto, muy recomendado.",
  "creadaEn": "2025-12-01T10:00:00"
}


2. Listar Reseñas de un Producto (7.2)

Obtiene el listado histórico de opiniones para un SKU específico.

  * **Método:** `GET`
  * **URL:** `/reviews/{sku}`
  * **Ejemplo:** `/reviews/BK-001`
  * **Respuesta Exitosa (200 OK):**

<!-- end list -->

json
[
  {
    "id": 12,
    "productoSku": "BK-001",
    "usuarioId": "usuario_prueba",
    "rating": 5,
    "comentario": "...",
    "creadaEn": "..."
  }
]


3. Obtener Rating Promedio (7.3)

Calcula matemáticamente el promedio y cuenta la cantidad de reseñas.

  * **Método:** `GET`
  * **URL:** `/reviews/{sku}/rating`
  * **Ejemplo:** `/reviews/BK-001/rating`
  * **Respuesta Exitosa (200 OK):**

<!-- end list -->

json
{
  "sku": "BK-001",
  "promedio": 4.8,
  "cantidad": 15
}


4. Eliminar Reseña / Moderación (7.4)

Elimina físicamente una reseña de la base de datos dado su ID numérico.

  * **Método:** `DELETE`
  * **URL:** `/reviews/{id}`
  * **Header:** `X-API-Key: clave_grupo8_keyPi`
  * **Ejemplo de comando cURL:**

<!-- end list -->

bash
curl -X DELETE "http://localhost:8080/reviews/12" -H "X-API-Key: clave_grupo8_keyPi"


  * **Respuesta Exitosa:** `204 No Content`

-----

5. Datos de Prueba Sugeridos

Para verificar el funcionamiento correcto del cálculo de promedios, sugerimos cargar reseñas para los siguientes SKUs:

1.  **`BK-001`** (Libro POO) -\> Cargar reseñas de 5 estrellas.
2.  **`NK-050`** (Notebook) -\> Cargar reseñas variadas (3 y 4 estrellas).
3.  **`MS-200`** (Mouse) -\> Cargar reseñas de 1 estrella (para probar promedios bajos).

-----

*Trabajo Práctico Integrador - UNSAdA 2025 Grupo N°3*


```