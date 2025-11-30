
CREATE TABLE IF NOT EXISTS reviews (
  id INT AUTO_INCREMENT PRIMARY KEY,
  producto_sku VARCHAR(255) NOT NULL,
  usuario_id VARCHAR(255) NOT NULL,
  rating INT NOT NULL,
  comentario TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

TRUNCATE TABLE reviews;

-- 3. Insertamos los datos de prueba
INSERT INTO reviews (producto_sku, usuario_id, rating, comentario) 
VALUES ('BK-001', 'Facundo Miranda', 5, '¡Este producto es excelente!');

INSERT INTO reviews (producto_sku, usuario_id, rating, comentario) 
VALUES ('LK-002', 'Facundo de Renzo', 3, 'No está mal, pero podría mejorar.');

INSERT INTO reviews (producto_sku, usuario_id, rating, comentario) 
VALUES ('LK-002', 'Facundo Aguilar', 4, 'Buena relación precio-calidad.');

INSERT INTO reviews (producto_sku, usuario_id, rating, comentario) 
VALUES ('CK-002', 'Demian Farias', 2, '¡Excelente Producto!');