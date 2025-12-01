// Endpoint API
// --- CAMBIO CRÍTICO: Usamos ruta relativa para que funcione en el servidor ---
const API_BASE_URL = '/reviews'; 
const API_KEY = 'clave_grupo8_keyPi';

// Estado de la aplicación
let currentPage = 0;
const pageSize = 5;

const reviewForm = document.getElementById('reviewForm');
const reviewList = document.getElementById('reviewList');
const pagination = document.getElementById('pagination');
const ratingInput = document.getElementById('rating');
const loadReviewsBtn = document.getElementById('loadReviewsBtn');
const productFilter = document.getElementById('productFilter');
const minRating = document.getElementById('minRating');
const alertContainer = document.getElementById('alertContainer');
const ratingStats = document.getElementById('ratingStats');

// Inicialización
document.addEventListener('DOMContentLoaded', function () {
    loadReviews();
    loadRatingStats();

    reviewForm.addEventListener('submit', handleReviewSubmit);
    loadReviewsBtn.addEventListener('click', () => {
        currentPage = 0; // Resetear a pagina 0 al filtrar
        loadReviews();
        loadRatingStats();
    });

    // Enviar nueva reseña
    async function handleReviewSubmit(e) {
        e.preventDefault();

        const reviewData = {
            productoSku: document.getElementById('productSku').value,
            usuarioId: document.getElementById('userId').value,
            rating: parseInt(ratingInput.value),
            comentario: document.getElementById('comment').value
        };

        try {
            // Nota: Al usar ruta relativa, el navegador usa el dominio actual automáticamente
            const response = await fetch(API_BASE_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-API-Key': API_KEY // Clave de seguridad
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
            } else if (response.status === 401) {
                showAlert('Error: No autorizado (API Key incorrecta)', 'error');
            } else {
                showAlert('Error al crear la reseña', 'error');
            }
        } catch (error) {
            console.error(error);
            showAlert('Error de conexión con el servidor', 'error');
        }
    }

    // Parámetros de búsqueda de SKUs
    async function loadReviews() {
        const sku = productFilter.value || 'BK-001';
        const min = minRating.value;

        reviewList.innerHTML = '<div class="empty"><p>Cargando Reseñas...</p></div>';
        try {

            let url = `${API_BASE_URL}/${sku}?page=${currentPage}&pageSize=${pageSize}`;
            if (min > 0) {
                url += `&minRating=${min}`;
            }

            const response = await fetch(url);

            if (response.status === 200) {
                const reviews = await response.json();
                displayReviews(reviews);
                updatePagination(reviews);
            } else if (response.status === 404) {
                showAlert('Producto no encontrado', 'error');
                reviewList.innerHTML = '<div class="empty"><p>Producto no encontrado</p></div>';
                pagination.innerHTML = ''; 
            } else {
                reviewList.innerHTML = '<div class="empty"><p>No se encontraron resultados.</p></div>';
            }
        } catch (error) {
            console.error(error);
            showAlert('Error al cargar reseñas', 'error');
            reviewList.innerHTML = '<div class="empty"><p>No se pudieron cargar las reseñas.</p></div>';
        }
    }

    // Mostrar reseñas en la lista
    function displayReviews(reviews) {
        if (!reviews || reviews.length === 0) {
            reviewList.innerHTML = '<div class="empty"><p>No hay reseñas para mostrar</p><p>Intenta con otros filtros o crea la primera reseña</p></div>';
            return;
        }

        reviewList.innerHTML = reviews.map(review => `
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

    // Cargar estadísticas de rating
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
    window.deleteReview = async function (reviewId) {
        if (!confirm('¿Estás seguro de que quieres eliminar esta reseña?')) {
            return;
        }

        try {
            const response = await fetch(`${API_BASE_URL}/${reviewId}`, {
                method: 'DELETE',
                headers: {
                    'X-API-Key': API_KEY
                }
            });

            if (response.status === 204) {
                showAlert('Reseña eliminada exitosamente', 'success');
                loadReviews();
                loadRatingStats();
            } else if (response.status === 404) {
                showAlert('Reseña no encontrada', 'error');
            } else if (response.status === 401) {
                showAlert('No autorizado para eliminar', 'error');
            }
        } catch (error) {
            showAlert('Error al eliminar reseña', 'error');
        }
    }

    // Actualizar paginación
    function updatePagination(currentReviews) {
        const isLastPage = (currentReviews.length < pageSize);

        pagination.innerHTML = `
        <button class="page-btn" onclick="changePage(${currentPage - 1})" ${currentPage === 0 ? 'disabled' : ''}>Anterior</button>
        <span>Página ${currentPage + 1}</span>
        <button class="page-btn" onclick="changePage(${currentPage + 1})" ${isLastPage ? 'disabled' : ''}>Siguiente</button>
    `;
    }

    // Cambiar página
    window.changePage = function (newPage) {
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
});