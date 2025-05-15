DROP
DATABASE IF EXISTS Library_of_ohara;

CREATE
DATABASE Library_of_ohara;

USE
Library_of_ohara;

-- -----------------------------------------------------
-- Tabla `Library_of_ohara`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE Usuario
(
    id          INT NOT NULL AUTO_INCREMENT,
    nombre      VARCHAR(30)  DEFAULT NULL,
    apellidos   VARCHAR(50)  DEFAULT NULL,
    gmail       VARCHAR(50)  DEFAULT NULL,
    contrasenna VARCHAR(500) DEFAULT NULL,
    rol         VARCHAR(10)  DEFAULT FALSE,
    PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1;

-- -----------------------------------------------------
-- Tabla `Library_of_ohara`.`Autor`
-- -----------------------------------------------------

CREATE TABLE Autor
(
    id        INT NOT NULL AUTO_INCREMENT,
    nombre    VARCHAR(30) DEFAULT NULL,
    apellidos VARCHAR(50) DEFAULT NULL,
    edad      INT(3) DEFAULT 0,
    PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1;

-- -----------------------------------------------------
-- Tabla `Library_of_ohara`.`Genero`
-- -----------------------------------------------------

CREATE TABLE Genero
(
    id     INT NOT NULL AUTO_INCREMENT,
    genero VARCHAR(50) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1;

-- -----------------------------------------------------
-- Tabla `Library_of_ohara`.`Libro`
-- -----------------------------------------------------

CREATE TABLE Libro
(
    id                INT NOT NULL AUTO_INCREMENT,
    titulo            VARCHAR(50)   DEFAULT NULL,
    sinopsis          VARCHAR(1000) DEFAULT NULL,
    fecha_publicacion DATE          DEFAULT NULL,
    portada           VARCHAR(500)  DEFAULT NULL,
    autor             INT NOT NULL,
    genero            INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (autor) REFERENCES Autor (id),
    FOREIGN KEY (genero) REFERENCES Genero (id)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1;

-- -----------------------------------------------------
-- Tabla `Library_of_ohara`.`Libros_Usuarios`
-- -----------------------------------------------------

CREATE TABLE Libros_Usuarios
(
    id           INT NOT NULL AUTO_INCREMENT,
    id_usuario   INT NOT NULL,
    id_libro     INT NOT NULL,
    fecha_inicio DATE        DEFAULT NULL,
    estado       VARCHAR(20) DEFAULT "sin empezar",
    PRIMARY KEY (id),
    FOREIGN KEY (id_usuario) REFERENCES Usuario (id),
    FOREIGN KEY (id_libro) REFERENCES Libro (id)

)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1;