package com.example.ReseniaPOO.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; // Importamos Autowired
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ReseniaPOO.dto.RatingResponseDTO;
import com.example.ReseniaPOO.dto.ReviewInputDTO;
import com.example.ReseniaPOO.model.Review;
import com.example.ReseniaPOO.service.ReviewService;

import jakarta.validation.Valid;

// aca esta el controlador que maneja las peticiones HTTP
@RestController
// el RequestMapping define la ruta base para todas las peticiones HTTP
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // el postmapping mapea las peticiones HTTP POST para crear una nueva reseña en
    // el controlador controller
    // en resumen define el endpoint para crear una nueva reseña cliente servidor
    @PostMapping
    public ResponseEntity<Review> createReview(@Valid @RequestBody ReviewInputDTO reviewInput) {
        Review newReview = reviewService.createReview(reviewInput);
        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }

    // este getmapping maneja las peticiones HTTP GET para obtener las reseñas de un
    // producto por su SKU
    // en resumen define los endpoint para obtener las reseñas de un producto
    // especifico
    @GetMapping("/{sku}")
    public ResponseEntity<List<Review>> getReviewsByProduct(
            @PathVariable String sku,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        List<Review> reviews = reviewService.getReviews(sku, minRating, page, pageSize);
        return ResponseEntity.ok(reviews);
    }

    // este getmapping maneja las peticiones HTTP GET para obtener el rating
    // promedio de un producto por su SKU
    // en resumen define los endpoint para obtener el rating promedio de un producto
    // especifico
    @GetMapping("/{sku}/rating")
    public ResponseEntity<RatingResponseDTO> getAverageRating(@PathVariable String sku) {
        RatingResponseDTO ratingData = reviewService.getAverageRating(sku);
        return ResponseEntity.ok(ratingData);
    }

    // con el DeleteMapping genero peticiones HTTP DELETE para eliminar las reseñas
    // por id
    // se aplicaria un borrado logico antes de eliminar fisicamente la reseña desde
    // la base de datos
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
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
 * 
 * @PostMapping es una anotación específica de Spring utilizada para mapear
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