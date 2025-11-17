CREATE TABLE reviews (
  id INT AUTO_INCREMENT PRIMARY KEY,
  producto_sku VARCHAR(255) NOT NULL,
  usuario_id VARCHAR(255) NOT NULL,
  rating INT NOT NULL,
  comentario TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. INSERTAR los datos de prueba
INSERT INTO reviews (producto_sku, usuario_id, rating, comentario) 
VALUES ('BK-001', 'user-123', 5, '¡Este producto es excelente!');

INSERT INTO reviews (producto_sku, usuario_id, rating, comentario) 
VALUES ('BK-001', 'user-456', 3, 'No está mal, pero podría mejorar.');

INSERT INTO reviews (producto_sku, usuario_id, rating, comentario) 
VALUES ('PROD-ABC', 'user-789', 4, 'Buena relación precio-calidad.');