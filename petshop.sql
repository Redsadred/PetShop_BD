
CREATE DATABASE IF NOT EXISTS petshop;

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

CREATE TABLE funcionario (
    idFuncionario INT AUTO_INCREMENT PRIMARY KEY,
    nomeFuncionario VARCHAR(100) NOT NULL,
    cargoFuncionario VARCHAR(100) NOT NULL
);

CREATE TABLE telefone_funcionario (
    idTelefoneFuncionario INT AUTO_INCREMENT PRIMARY KEY,
    telefoneFuncionario VARCHAR(20) NOT NULL,
    idFuncionario INT NOT NULL,
    FOREIGN KEY (idFuncionario)
        REFERENCES funcionario(idFuncionario)
        ON DELETE CASCADE
);

CREATE TABLE procedimento (
    idProcedimento INT AUTO_INCREMENT PRIMARY KEY,
    nomeProcedimento VARCHAR(100) NOT NULL,
    descricaoProcedimento TEXT,
    valorProcedimento DECIMAL(10,2) NOT NULL,
    CHECK (valorProcedimento >= 0)
);

CREATE TABLE atendimento (
    idAtendimento INT AUTO_INCREMENT PRIMARY KEY,
    dataAtendimento DATETIME NOT NULL,
    idAnimal INT NOT NULL,
    idProcedimento INT NOT NULL,
    idFuncionario INT NOT NULL,
    FOREIGN KEY (idAnimal)
        REFERENCES animal(idAnimal),
    FOREIGN KEY (idProcedimento)
        REFERENCES procedimento(idProcedimento),
    FOREIGN KEY (idFuncionario)
        REFERENCES funcionario(idFuncionario)
);
