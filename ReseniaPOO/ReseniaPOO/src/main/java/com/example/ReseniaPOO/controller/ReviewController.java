package com.example.ReseniaPOO.controller;

//importamos librerias como HashMap, Map, List
import java.util.HashMap;
import java.util.LinkedHashMap; // Agregado para el orden del JSON (id, sku, etc)
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.ReseniaPOO.dto.RatingResponseDTO;
import com.example.ReseniaPOO.dto.ReviewInputDTO;
import com.example.ReseniaPOO.model.Review;
import com.example.ReseniaPOO.repository.ReviewRepository; // Necesario para el listado general
import com.example.ReseniaPOO.service.ReviewService;

// aca esta el controlador que maneja las peticiones HTTP
@RestController

// el RequestMapping define la ruta base para todas las peticiones HTTP
@RequestMapping("/reviews")

// el CrossOrigin define la ruta base para todas las peticiones HTTP por asi
// decirlo la conexion entre el cliente y el servidor
@CrossOrigin(origins = "*")

public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository; // Agregado para poder listar todo en /reviews

    // esta es la clave que se usara para validar las peticiones HTTP para X-API-Key
    private static final String MI_CLAVE_SECRETA = "clave_grupo8_keyPi";

    /*
     * EN ESTA PARTE SE CREA LA NUEVA RESEÑA POR METODO POST COMO SE PIDIO
     * el postmapping mapea las peticiones HTTP POST para crear una nueva reseña en
     * el controlador controller
     * en resumen define el endpoint para crear una nueva reseña cliente servidor
     */
    @PostMapping
    public ResponseEntity<?> createReview(
            @RequestHeader(value = "X-API-Key", required = false) String apiKey,
            @RequestBody ReviewInputDTO reviewInput) {

        // aca se valida que la api key sea correcta sino se avisa que la api key
        // inválida o ausente
        if (apiKey == null || !apiKey.equals(MI_CLAVE_SECRETA)) {
            return generarError(HttpStatus.UNAUTHORIZED, "API Key inválida o ausente");
        }

        // aca se valida que el rating sea entre 1 y 5 sino se avisa que el rating debe
        // estar entre 1 y 5
        if (reviewInput.getRating() < 1 || reviewInput.getRating() > 5) {
            return generarError(HttpStatus.UNPROCESSABLE_ENTITY, "El rating debe estar entre 1 y 5");
        }

        try {
            // aca se crea la reseña en la base de datos
            Review newReview = reviewService.createReview(reviewInput);

            // --- MODIFICACIÓN PARA CUMPLIR EL FORMATO "rev12" ---
            // Construimos un mapa manual para que el JSON salga con "rev" y sin usuarioId
            Map<String, Object> respuesta = new LinkedHashMap<>();
            respuesta.put("id", "rev" + newReview.getId());
            respuesta.put("productoSku", newReview.getProductoSku());
            respuesta.put("rating", newReview.getRating());
            respuesta.put("comentario", newReview.getComentario());
            respuesta.put("creadaEn", newReview.getCreatedAt().toString());

            // se retorna la reseña creada con el codigo 201 que significa que fue creado
            // correctamente (devuelve el mapa personalizado)
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);

        } catch (ResponseStatusException e) {
            // se captura las reseñas duplicadas (error 409)
            return generarError((HttpStatus) e.getStatusCode(), e.getReason());
        }
    }

    /*
     * Endpoint EXTRA (Agregado para ver todas las reseñas al entrar a /reviews)
     * Esto soluciona el error 405 y permite ver el listado general
     */
    @GetMapping
    public ResponseEntity<List<Review>> listarTodas() {
        return ResponseEntity.ok(reviewRepository.findAll());
    }

    /*
     * con el DeleteMapping genero peticiones HTTP DELETE para eliminar las reseñas
     * por id se aplicaria un borrado logico antes de eliminar fisicamente la reseña
     * desde
     * la base de datos
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(
            // aca se valida que la api key sea correcta sino se avisa que la api key
            // inválida o ausente
            @RequestHeader(value = "X-API-Key", required = false) String apiKey,
            @PathVariable Integer id) {

        // se evalua que la api key sea correcta sino se avisa que la api key inválida o
        // ausente
        if (apiKey == null || !apiKey.equals(MI_CLAVE_SECRETA)) {
            return generarError(HttpStatus.UNAUTHORIZED, "API Key inválida o ausente");
        }

        try {
            reviewService.deleteReview(id);
            // aca se presenta el codigo 204 que significa que la reseña fue eliminada
            // correctamente
            return ResponseEntity.noContent().build();

        } catch (ResponseStatusException e) {
            // se captura el error 404 que significa que la reseña no fue encontrada
            return generarError((HttpStatus) e.getStatusCode(), e.getReason());
        }
    }

    // metodo para generar errores personalizados
    private ResponseEntity<Map<String, Object>> generarError(HttpStatus estado, String mensaje) {

        // creamos un map para almacenar el error
        Map<String, Object> errorBody = new HashMap<>();

        // agregamos el error y el mensaje al map que se va a devolver
        errorBody.put("error", estado.toString());
        errorBody.put("message", mensaje);

        // devolvemos el error con el estado que se le pasa como parametro
        return new ResponseEntity<>(errorBody, estado);
    }

    /*
     * este getmapping maneja las peticiones HTTP GET para obtener las reseñas de un
     * producto por su SKU
     * en resumen define los endpoint para obtener las reseñas de un producto
     * especifico
     */

    @GetMapping("/{sku}")
    public ResponseEntity<List<Review>> getReviewsByProduct(

            // PathVariable define que el sku es un parametro de la URL
            @PathVariable String sku,

            // RequestParam define que el minRating, page y pageSize son parametros de la
            // URL
            @RequestParam(required = false) Integer minRating,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {

        // llamamos al servicio reviewService para obtener las reseñas
        List<Review> reviews = reviewService.getReviews(sku, minRating, page, pageSize);

        // retornamos la lista de reseñas
        return ResponseEntity.ok(reviews);
    }

    /*
     * este getmapping maneja las peticiones HTTP GET para obtener el rating
     * promedio de un producto por su SKU
     * en resumen define los endpoint para obtener el rating promedio de un producto
     * especifico
     */
    @GetMapping("/{sku}/rating")
    public ResponseEntity<RatingResponseDTO> getAverageRating(@PathVariable String sku) {
        RatingResponseDTO ratingData = reviewService.getAverageRating(sku);
        return ResponseEntity.ok(ratingData);
    }

}

/*
 * info : @GetMapping es una anotación específica de Spring que se usa para
 * manejar
 * solicitudes HTTP GET. Normalmente, se emplea para definir endpoints que
 * obtienen datos, como
 * la consulta de información en bases de datos o la generación de respuestas en
 * formato JSON o XML.
 * Al igual que otras anotaciones de mapeo (@PostMapping, @PutMapping), permite
 * establecer la ruta y los
 * parámetros que el endpoint debe recibir. citado:
 * https://localhorse.net/article/como-manejar-solicitudes-get-con-getmapping-en
 * -spring-boot
 * * @PostMapping es una anotación específica de Spring utilizada para mapear
 * solicitudes HTTP POST en métodos
 * de un controlador. Esta anotación simplifica la configuración de rutas y es
 * útil para operaciones que
 * implican la creación o el envío de datos desde el cliente al servidor. A
 * diferencia de @GetMapping,
 * que se usa para recuperar información, @PostMapping suele utilizarse cuando
 * se envían datos para ser
 * procesados, como por ejemplo el envío de formularios, la creación de
 * registros en bases de datos, o para
 * recibir datos en formato JSON. citado :
 * https://localhorse.net/article/como-manejar-solicitudes-post-con-postmapping-
 * en-spring-boot
 */