CREATE TABLE topico(
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   titulo VARCHAR(255) NOT NULL,
   mensaje TEXT NOT NULL,
   fecha_creacion DATETIME NOT NULL,
   status TINYINT NOT NULL,
   author_id BIGINT NOT NULL,
   curso VARCHAR(255) NOT NULL,
   UNIQUE KEY unique_mensaje (mensaje(255)),
   UNIQUE KEY unique_titulo (titulo(255)),
   CONSTRAINT fk_topico_usuario_id FOREIGN KEY (author_id) REFERENCES usuarios(id)
);