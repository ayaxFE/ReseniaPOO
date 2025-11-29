// Endpoint API
const API_BASE_URL = 'http://localhost:8080/reviews';

// Estado de la aplicación
let currentPage = 0;
const pageSize = 5;

/*

- reviewForm: Formulario para crear nuevas reseñas
- reviewList: Contenedor de la lista de reseñas
- pagination: Controles de paginación
- ratingInput: Campo de calificación (1-5 Estrellas)
- Filtros (productFilter, minRating)
- Componente UI: alertContainer, ratingStats

*/
const reviewForm = document.getElementById('reviewForm');
const reviewsList = document.getElementById('reviewsList');
const pagination = document.getElementById('pagination');
const ratingInput = document.getElementById('rating');
const loadReviewsBtn = document.getElementById('loadReviewsBtn');
const productFilter = document.getElementById('productFilter');
const minRating = document.getElementById('minRating');
const alertContainer = document.getElementById('alertContainer');
const ratingStats = document.getElementById('ratingStats');

// Inicialización
document.addEventListener('DOMContentLoaded', function() {
    loadReviews();
    loadRatingStats();
    
    reviewForm.addEventListener('submit', handleReviewSubmit);
    loadReviewsBtn.addEventListener('click', loadReviews);
});

// Enviar nueva reseña

/*
handleReviewSubmit
1 - Prevenir envío default del formulario
2 - Recopila datos: SKU, Usuario, Rating, Comentario
3 - Envía el POST a la API
4 - Maneja respuestas:
    - 201: Muestras alerta => Resetea formulario => Recarga los datos DB
    - 422: Rating Inválido => Erorr de validación
    - 409: Reseña duplicada => Usuario que ya reseño el Producto
*/

async function handleReviewSubmit(e) {
    e.preventDefault();
    
    const reviewData = {
        productoSku: document.getElementById('productSku').value,
        usuarioId: document.getElementById('userId').value,
        rating: parseInt(ratingInput.value),
        comentario: document.getElementById('comment').value
    };
    
    try {
        const response = await fetch(API_BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(reviewData)
        });
        
        if (response.status === 201) {
            showAlert('Reseña creada exitosamente!', 'success');
            reviewForm.reset();
            loadReviews();
            loadRatingStats();
        } else if (response.status === 422) {
            showAlert('Error: El rating debe estar entre 1 y 5 estrellas', 'error');
        } else if (response.status === 409) {
            showAlert('Error: Ya existe una reseña de este usuario para este producto', 'error');
        } else {
            showAlert('Error al crear la reseña', 'error');
        }
    } catch (error) {
        showAlert('Error de conexión', 'error');
    }
}

// Parámetros de búsqueda de SKUs
async function loadReviews() {
    const sku = productFilter.value || 'BK-001';
    const min = minRating.value;
    
    try {
        let url = `${API_BASE_URL}/${sku}?page=${currentPage}&pageSize=${pageSize}`;
        if (min > 0) {
            url += `&minRating=${min}`;
        }
        
        const response = await fetch(url);
        
        if (response.status === 200) {
            const reviews = await response.json();
            displayReviews(reviews);
            updatePagination();
        } else if (response.status === 404) {
            showAlert('Producto no encontrado', 'error');
            reviewsList.innerHTML = '<div class="empty"><p>Producto no encontrado</p></div>';
        }
    } catch (error) {
        showAlert('Error al cargar reseñas', 'error');
    }
}

// Mostrar reseñas en la lista
function displayReviews(reviews) {
    if (!reviews || reviews.length === 0) {
        reviewsList.innerHTML = '<div class="empty"><p>No hay reseñas para mostrar</p><p>Intenta con otros filtros o crea la primera reseña</p></div>';
        return;
    }
    
    reviewsList.innerHTML = reviews.map(review => `
        <div class="review-item">
            <div class="review-header">
                <div class="review-user">Usuario: ${review.usuarioId}</div>
                <div class="review-date">${formatDate(review.creadaEn)}</div>
            </div>
            <div class="review-rating">Rating: ${review.rating}/5</div>
            <div class="review-comment">${review.comentario}</div>
            <button class="delete-btn" onclick="deleteReview('${review.id}')">
                Eliminar
            </button>
        </div>
    `).join('');
}

/*
Cargar estadísticas de rating
Endpoint: /reviews/{sku}/rating
*/

async function loadRatingStats() {
    const sku = productFilter.value || 'BK-001';
    
    try {
        const response = await fetch(`${API_BASE_URL}/${sku}/rating`);
        
        if (response.status === 200) {
            const stats = await response.json();
            displayRatingStats(stats);
        }
    } catch (error) {
        console.error('Error al cargar estadísticas:', error);
    }
}

// Mostrar estadísticas de rating
function displayRatingStats(stats) {
    ratingStats.innerHTML = `
        <div class="average">${stats.promedio ? stats.promedio.toFixed(1) : '0.0'}</div>
        <div class="count">${stats.cantidad || 0} reseñas</div>
    `;
}

// Eliminar reseña
async function deleteReview(reviewId) {
    if (!confirm('¿Estás seguro de que quieres eliminar esta reseña?')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/${reviewId}`, {
            method: 'DELETE'
        });
        
        if (response.status === 204) {
            showAlert('Reseña eliminada exitosamente', 'success');
            loadReviews();
            loadRatingStats();
        } else if (response.status === 404) {
            showAlert('Reseña no encontrada', 'error');
        }
    } catch (error) {
        showAlert('Error al eliminar reseña', 'error');
    }
}

// Actualizar paginación
function updatePagination(response) {
    // Si la API devuelve un objeto de paginación
    const isLastPage = response.isLast || false;

    pagination.innerHTML = `
        <button class="page-btn" onclick="changePage(${currentPage - 1})" ${currentPage === 0 ? 'disabled' : ''}>Anterior</button>
        <span>Página ${currentPage + 1}</span>
        <button class="page-btn" onclick="changePage(${currentPage + 1})" ${isLastPage ? 'disabled' : ''}>Siguiente</button>
    `;
}

// Cambiar página
function changePage(newPage) {
    if (newPage >= 0) {
        currentPage = newPage;
        loadReviews();
    }
}

// Mostrar alertas
function showAlert(message, type) {
    const alertClass = type === 'success' ? 'alert-success' : 'alert-error';
    
    alertContainer.innerHTML = `
        <div class="alert ${alertClass}">
            ${message}
        </div>
    `;
    
    setTimeout(() => {
        alertContainer.innerHTML = '';
    }, 5000);
}

// Formatear fecha
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('es-ES', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });
}