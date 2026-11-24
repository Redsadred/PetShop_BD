
DROP DATABASE IF EXISTS petshop;

CREATE DATABASE petshop;

USE petshop;

CREATE TABLE cliente (
    idCliente INT AUTO_INCREMENT PRIMARY KEY,
    nomeCliente VARCHAR(100) NOT NULL
);

CREATE TABLE telefone_cliente (
    idTelefoneCliente INT AUTO_INCREMENT PRIMARY KEY,
    telefoneCliente VARCHAR(20) NOT NULL,
    idCliente INT NOT NULL,
    FOREIGN KEY (idCliente)
        REFERENCES cliente(idCliente)
        ON DELETE CASCADE
);

CREATE TABLE animal (
    idAnimal INT AUTO_INCREMENT PRIMARY KEY,
    nomeAnimal VARCHAR(100) NOT NULL,
    especieAnimal ENUM('Gato', 'Cachorro') NOT NULL,
    racaAnimal VARCHAR(100) NOT NULL,
    idadeAnimal INT NOT NULL,
    idCliente INT NOT NULL,
    FOREIGN KEY (idCliente)
        REFERENCES cliente(idCliente),
    CHECK (idadeAnimal >= 0)
);

CREATE TABLE veterinario (
    idVeterinario INT AUTO_INCREMENT PRIMARY KEY,
    nomeVeterinario VARCHAR(100) NOT NULL,
    especialidadeVeterinario VARCHAR(100) NOT NULL
);

CREATE TABLE telefone_veterinario (
    idTelefoneVeterinario INT AUTO_INCREMENT PRIMARY KEY,
    telefoneVeterinario VARCHAR(20) NOT NULL,
    idVeterinario INT NOT NULL,
    FOREIGN KEY (idVeterinario)
        REFERENCES veterinario(idVeterinario)
        ON DELETE CASCADE
);

CREATE TABLE procedimento (
    idProcedimento INT AUTO_INCREMENT PRIMARY KEY,
    nomeProcedimento VARCHAR(100) NOT NULL,
    descricaoProcedimento TEXT,
    valorProcedimento DECIMAL(10,2) NOT NULL,
    dataProcedimento DATETIME NOT NULL,
    idAnimal INT NOT NULL,
    idVeterinario INT NOT NULL,
    FOREIGN KEY (idAnimal)
        REFERENCES animal(idAnimal),
    FOREIGN KEY (idVeterinario)
        REFERENCES veterinario(idVeterinario),
    CHECK (valorProcedimento >= 0)
);
