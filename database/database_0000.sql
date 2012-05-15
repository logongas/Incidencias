DROP DATABASE IF EXISTS `incidencias`;
CREATE DATABASE IF NOT EXISTS `incidencias`;
USE `incidencias`;


DROP TABLE IF EXISTS `aula`;
CREATE TABLE IF NOT EXISTS `aula` (
  `CodAula` varchar(50) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`CodAula`),
  KEY `CodAula` (`CodAula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `bl_lock`;
CREATE TABLE IF NOT EXISTS `bl_lock` (
  `Category` varchar(255) DEFAULT NULL,
  `Type` varchar(255) DEFAULT NULL,
  `Instance` varchar(50) NOT NULL DEFAULT '',
  `SystemUID` varchar(50) DEFAULT NULL,
  `SessionUID` varchar(50) DEFAULT NULL,
  `StartLock` datetime DEFAULT NULL,
  `HostIP` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Instance`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `bl_sequencevalues`;
CREATE TABLE IF NOT EXISTS `bl_sequencevalues` (
  `CodSequence` varchar(50) NOT NULL DEFAULT '',
  `Partitioner1` varchar(50) NOT NULL DEFAULT '',
  `Partitioner2` varchar(50) NOT NULL DEFAULT '',
  `Partitioner3` varchar(50) NOT NULL DEFAULT '',
  `CurrentValue` varchar(255) NOT NULL DEFAULT '0',
  PRIMARY KEY (`CodSequence`,`Partitioner1`,`Partitioner2`,`Partitioner3`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `incidencia`;
CREATE TABLE IF NOT EXISTS `incidencia` (
  `IdIncidencia` int(11) NOT NULL DEFAULT '0',
  `Login` varchar(50) DEFAULT NULL,
  `IdProyecto` int(11) DEFAULT '0',
  `Descripcion` varchar(255) DEFAULT NULL,
  `CodAula` varchar(50) DEFAULT NULL,
  `CodEstado` varchar(50) DEFAULT NULL,
  `Explicacion` longtext,
  `FechaApertura` datetime DEFAULT NULL,
  `FechaCierre` datetime DEFAULT NULL,
  PRIMARY KEY (`IdIncidencia`),
  KEY `IdIncidencia` (`IdIncidencia`),
  KEY `IdProyecto` (`IdProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `incidenciatarea`;
CREATE TABLE IF NOT EXISTS `incidenciatarea` (
  `IdIncidencia` int(11) NOT NULL DEFAULT '0',
  `IdTarea` int(11) NOT NULL DEFAULT '0',
  `Login` varchar(50) DEFAULT NULL,
  `Fecha` datetime DEFAULT NULL,
  `Duracion` int(11) DEFAULT '0',
  `Descripcion` longtext,
  PRIMARY KEY (`IdIncidencia`,`IdTarea`),
  KEY `IdIncidencia` (`IdIncidencia`),
  KEY `IdTarea` (`IdTarea`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `proyecto`;
CREATE TABLE IF NOT EXISTS `proyecto` (
  `IdProyecto` int(11) NOT NULL DEFAULT '0',
  `Descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IdProyecto`),
  KEY `IdProyecto` (`IdProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `proyectoaula`;
CREATE TABLE IF NOT EXISTS `proyectoaula` (
  `IdProyecto` int(11) NOT NULL DEFAULT '0',
  `CodAula` varchar(50) NOT NULL,
  PRIMARY KEY (`IdProyecto`,`CodAula`),
  KEY `IdProyecto` (`IdProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `proyectouser`;
CREATE TABLE IF NOT EXISTS `proyectouser` (
  `IdProyecto` int(11) NOT NULL DEFAULT '0',
  `Login` varchar(50) NOT NULL,
  PRIMARY KEY (`IdProyecto`,`Login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `estado`;
CREATE TABLE IF NOT EXISTS `estado` (
  `CodEstado` varchar(50) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`CodEstado`),
  KEY `CodEstado` (`CodEstado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `estado` (`CodEstado`, `Descripcion`) VALUES
	('A', 'Abierta'),
	('C', 'Cerrada');


DROP TABLE IF EXISTS `habilitadousuario`;
CREATE TABLE IF NOT EXISTS `habilitadousuario` (
  `CodHabilitadoUsuario` varchar(4) NOT NULL,
  `Descripcion` varchar(255) NOT NULL,
  PRIMARY KEY (`CodHabilitadoUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `habilitadousuario` (`CodHabilitadoUsuario`, `Descripcion`) VALUES
	('D', 'Deshabilitado'),
	('H', 'Habilitado');


DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `IdRole` varchar(50) NOT NULL DEFAULT '',
  `Description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`IdRole`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `role` (`IdRole`, `Description`) VALUES
	('ADMIN', 'Administrador'),
	('USER', 'Usuarios'),
	('TECNICO', 'Técnico');

DROP TABLE IF EXISTS `dl_config`;
CREATE TABLE IF NOT EXISTS `dl_config` (
  `Name` varchar(255) NOT NULL DEFAULT '',
  `DataValue` varchar(255) DEFAULT NULL,
  `DataType` int(11) DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `dl_config` (`Name`, `DataValue`, `DataType`, `Description`) VALUES
	('app.nombreCentro', 'CIPFP Mislata', 0, 'Nombre del centro'),
	('login.authenticationServer', 'pop.gmail.com', 0, 'Servidor Pop3 para autenticar a los usuarios'),
	('login.emailDomain', 'gmail.com', 0, 'Dominio de correo de los usuarios dela aplicación'),
	('notify.sendMail', '1', 7, 'Mandar E-Mails si se añade una incidencia');

DROP TABLE IF EXISTS `usuariorole`;
CREATE TABLE IF NOT EXISTS `usuariorole` (
  `Login` varchar(50) NOT NULL DEFAULT '',
  `IdRole` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`IdRole`,`Login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;	
	
DROP TABLE IF EXISTS `usuario`;
CREATE TABLE IF NOT EXISTS `usuario` (
  `Login` varchar(50) NOT NULL DEFAULT '',
  `Nombre` varchar(50) DEFAULT NULL,
  `Ape1` varchar(50) DEFAULT NULL,
  `Ape2` varchar(50) DEFAULT NULL,
  `CodHabilitadoUsuario` varchar(4) DEFAULT NULL,
  PRIMARY KEY (`Login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `usuario` (`Login`, `Nombre`, `Ape1`, `Ape2`, `CodHabilitadoUsuario`) VALUES
	('', 'Administrador', 'Administrador', NULL, 'H');

