
-- 1 En este sector se formara el codigo principal de MYSQL para la gestion de reseñas de productos.
CREATE TABLE reviews (
  -- 2. DEFINIR la estructura de la tabla de reseñas
  id INT AUTO_INCREMENT PRIMARY KEY,
  producto_sku VARCHAR(255) NOT NULL,
  usuario_id VARCHAR(255) NOT NULL,
  rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
  comentario TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY unique_review (producto_sku, usuario_id)
);

-- 3. INSERTAR los datos de prueba
INSERT INTO reviews (producto_sku, usuario_id, rating, comentario) 
VALUES ('BK-001', 'user-123', 5, '¡Este producto es excelente!');

INSERT INTO reviews (producto_sku, usuario_id, rating, comentario) 
VALUES ('BK-001', 'user-456', 3, 'No está mal, pero podría mejorar.');

INSERT INTO reviews (producto_sku, usuario_id, rating, comentario) 
VALUES ('PROD-ABC', 'user-789', 4, 'Buena relación precio-calidad.');