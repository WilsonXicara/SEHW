-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         5.5.33a-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             8.0.0.4396
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Volcando estructura de base de datos para ecegua
DROP DATABASE IF EXISTS `ecegua`;
CREATE DATABASE IF NOT EXISTS `ecegua` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ecegua`;


-- Volcando estructura para tabla ecegua.anio
DROP TABLE IF EXISTS `anio`;
CREATE TABLE IF NOT EXISTS `anio` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Anio` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.anio: ~0 rows (aproximadamente)
DELETE FROM `anio`;
/*!40000 ALTER TABLE `anio` DISABLE KEYS */;
/*!40000 ALTER TABLE `anio` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.cafe
DROP TABLE IF EXISTS `cafe`;
CREATE TABLE IF NOT EXISTS `cafe` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) DEFAULT NULL,
  `Pergamino` tinyint(4) DEFAULT NULL,
  `Oro` tinyint(4) DEFAULT NULL,
  `SaldoBodega` float DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.cafe: ~1 rows (aproximadamente)
DELETE FROM `cafe`;
/*!40000 ALTER TABLE `cafe` DISABLE KEYS */;
INSERT INTO `cafe` (`Id`, `Nombre`, `Pergamino`, `Oro`, `SaldoBodega`) VALUES
	(1, 'De Primera Exportable', 0, 1, 0);
/*!40000 ALTER TABLE `cafe` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.cheque
DROP TABLE IF EXISTS `cheque`;
CREATE TABLE IF NOT EXISTS `cheque` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Numero` int(11) DEFAULT NULL,
  `Lugar` varchar(45) DEFAULT NULL,
  `Fecha` date DEFAULT NULL,
  `PagarA` varchar(45) DEFAULT NULL,
  `Cantidad` float DEFAULT NULL,
  `CuentaBancaria_Id` int(11) NOT NULL,
  `CicloContable_Id` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_Cheque_CuentaBancaria1_idx` (`CuentaBancaria_Id`),
  KEY `fk_Cheque_CicloContable1_idx` (`CicloContable_Id`),
  CONSTRAINT `fk_Cheque_CuentaBancaria1` FOREIGN KEY (`CuentaBancaria_Id`) REFERENCES `cuentabancaria` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cheque_CicloContable1` FOREIGN KEY (`CicloContable_Id`) REFERENCES `ciclocontable` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.cheque: ~0 rows (aproximadamente)
DELETE FROM `cheque`;
/*!40000 ALTER TABLE `cheque` DISABLE KEYS */;
/*!40000 ALTER TABLE `cheque` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.ciclocontable
DROP TABLE IF EXISTS `ciclocontable`;
CREATE TABLE IF NOT EXISTS `ciclocontable` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Anio_Id` int(11) NOT NULL,
  `Mes_Id` int(11) NOT NULL,
  `Activo` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`Id`),
  KEY `fk_Anio_has_Mes_Mes1_idx` (`Mes_Id`),
  KEY `fk_Anio_has_Mes_Anio1_idx` (`Anio_Id`),
  CONSTRAINT `fk_Anio_has_Mes_Anio1` FOREIGN KEY (`Anio_Id`) REFERENCES `anio` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Anio_has_Mes_Mes1` FOREIGN KEY (`Mes_Id`) REFERENCES `mes` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.ciclocontable: ~0 rows (aproximadamente)
DELETE FROM `ciclocontable`;
/*!40000 ALTER TABLE `ciclocontable` DISABLE KEYS */;
/*!40000 ALTER TABLE `ciclocontable` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.clientes
DROP TABLE IF EXISTS `clientes`;
CREATE TABLE IF NOT EXISTS `clientes` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `NIT` varchar(45) DEFAULT NULL,
  `Nombre` varchar(45) DEFAULT NULL,
  `Direccion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.clientes: ~0 rows (aproximadamente)
DELETE FROM `clientes`;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.clientes_exportacion
DROP TABLE IF EXISTS `clientes_exportacion`;
CREATE TABLE IF NOT EXISTS `clientes_exportacion` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) DEFAULT NULL,
  `Direccion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.clientes_exportacion: ~0 rows (aproximadamente)
DELETE FROM `clientes_exportacion`;
/*!40000 ALTER TABLE `clientes_exportacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientes_exportacion` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.consumo
DROP TABLE IF EXISTS `consumo`;
CREATE TABLE IF NOT EXISTS `consumo` (
  `Recibo_Codigo` int(11) NOT NULL,
  `OrdenTrilla_Codigo` int(11) NOT NULL,
  `CantidadSacosNylon` int(11) DEFAULT NULL,
  `CantidadSacosYuta` int(11) DEFAULT NULL,
  `PesoUtilizado` float DEFAULT NULL,
  PRIMARY KEY (`Recibo_Codigo`,`OrdenTrilla_Codigo`),
  KEY `fk_Recibo_has_OrdenTrilla_OrdenTrilla1_idx` (`OrdenTrilla_Codigo`),
  KEY `fk_Recibo_has_OrdenTrilla_Recibo1_idx` (`Recibo_Codigo`),
  CONSTRAINT `fk_Recibo_has_OrdenTrilla_Recibo1` FOREIGN KEY (`Recibo_Codigo`) REFERENCES `recibo` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Recibo_has_OrdenTrilla_OrdenTrilla1` FOREIGN KEY (`OrdenTrilla_Codigo`) REFERENCES `ordentrilla` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.consumo: ~0 rows (aproximadamente)
DELETE FROM `consumo`;
/*!40000 ALTER TABLE `consumo` DISABLE KEYS */;
/*!40000 ALTER TABLE `consumo` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.cosecha
DROP TABLE IF EXISTS `cosecha`;
CREATE TABLE IF NOT EXISTS `cosecha` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) DEFAULT NULL,
  `Fecha_Inicio` year(4) DEFAULT NULL,
  `Fecha_fin` year(4) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.cosecha: ~0 rows (aproximadamente)
DELETE FROM `cosecha`;
/*!40000 ALTER TABLE `cosecha` DISABLE KEYS */;
/*!40000 ALTER TABLE `cosecha` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.cuentabancaria
DROP TABLE IF EXISTS `cuentabancaria`;
CREATE TABLE IF NOT EXISTS `cuentabancaria` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `NombreBanco` varchar(45) DEFAULT NULL,
  `NumeroCuenta` varchar(45) DEFAULT NULL,
  `NombrePropietario` varchar(45) DEFAULT NULL,
  `UltimoNumero` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.cuentabancaria: ~0 rows (aproximadamente)
DELETE FROM `cuentabancaria`;
/*!40000 ALTER TABLE `cuentabancaria` DISABLE KEYS */;
/*!40000 ALTER TABLE `cuentabancaria` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.cuentas
DROP TABLE IF EXISTS `cuentas`;
CREATE TABLE IF NOT EXISTS `cuentas` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(60) NOT NULL,
  `Activo` tinyint(1) DEFAULT '0',
  `Pasivo` tinyint(1) DEFAULT '0',
  `Perdida` tinyint(1) DEFAULT '0',
  `Ganancia` tinyint(1) DEFAULT '0',
  `Patrimonio` tinyint(1) DEFAULT '0',
  `Corriente` tinyint(1) DEFAULT '0',
  `Saldo` float(10,2) DEFAULT '0.00',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.cuentas: ~70 rows (aproximadamente)
DELETE FROM `cuentas`;
/*!40000 ALTER TABLE `cuentas` DISABLE KEYS */;
INSERT INTO `cuentas` (`Id`, `Nombre`, `Activo`, `Pasivo`, `Perdida`, `Ganancia`, `Patrimonio`, `Corriente`, `Saldo`) VALUES
	(1, 'Sueldos Administración', 0, 0, 1, 0, 0, 0, 49200.00),
	(2, 'Sueldos Producción', 0, 0, 1, 0, 0, 0, 0.00),
	(3, 'Bonificación Incentivo Administración', 0, 0, 1, 0, 0, 0, 15000.00),
	(4, 'Bonificación Incentivo Producción', 0, 0, 1, 0, 0, 0, 0.00),
	(5, 'Cuota Patronal Administración', 0, 0, 1, 0, 0, 0, 6233.64),
	(6, 'Cuota Patronal Producción', 0, 0, 1, 0, 0, 0, 0.00),
	(7, 'Cuota Patronal por Pagar', 0, 1, 0, 0, 0, 1, 0.00),
	(8, 'Cuota Laboral IGSS', 0, 1, 0, 0, 0, 1, 0.00),
	(9, 'Anticipo sobre Sueldos', 1, 0, 0, 0, 0, 0, 0.00),
	(10, 'Sueldos por Pagar', 0, 1, 0, 0, 0, 1, 0.00),
	(11, 'Sueldos Ventas', 0, 0, 1, 0, 0, 0, 38500.00),
	(12, 'Bonificación Incentivo Ventas', 0, 0, 1, 0, 0, 0, 12000.00),
	(13, 'Cuota Patronal Ventas', 0, 0, 1, 0, 0, 0, 4877.95),
	(14, 'Acreedores', 0, 1, 0, 0, 0, 1, 0.00),
	(15, 'Aguinaldos Administración', 0, 0, 1, 0, 0, 0, 4100.00),
	(16, 'Aguinaldos Producción', 0, 0, 1, 0, 0, 0, 0.00),
	(17, 'Aguinaldos Ventas', 0, 0, 1, 0, 0, 0, 3208.33),
	(18, 'Mobiliario y Equipo', 1, 0, 0, 0, 0, 0, 0.00),
	(19, 'Equipo de Computación', 1, 0, 0, 0, 0, 0, 0.00),
	(20, 'Maquinaria', 1, 0, 0, 0, 0, 0, 0.00),
	(21, 'Herramientas', 1, 0, 0, 0, 0, 0, 0.00),
	(22, 'Caja General', 1, 0, 0, 0, 0, 1, 0.00),
	(23, 'Caja Chica', 1, 0, 0, 0, 0, 1, 0.00),
	(24, 'Bancos', 1, 0, 0, 0, 0, 1, 0.00),
	(25, 'Clientes', 1, 0, 0, 0, 0, 1, 0.00),
	(26, 'Cuentas por Cobrar', 1, 0, 0, 0, 0, 1, 0.00),
	(27, 'Anticipo a Proveedores', 1, 0, 0, 0, 0, 1, 0.00),
	(28, 'IVA por Cobrar', 1, 0, 0, 0, 0, 1, 0.00),
	(29, 'ISR por Pagar', 0, 1, 0, 0, 0, 1, 0.00),
	(30, 'Mercadería', 1, 0, 0, 0, 0, 1, 0.00),
	(31, 'Materia Prima', 1, 0, 0, 0, 0, 1, 0.00),
	(32, 'Reserva Legal', 0, 0, 0, 0, 1, 0, 0.00),
	(33, 'Depreciaciones Mobiliario y Equipo', 0, 0, 1, 0, 0, 0, 2633.76),
	(34, 'Depreciaciones Equipo de Computación', 0, 0, 1, 0, 0, 0, 7104.76),
	(35, 'Depreciaciones Maquinaria', 0, 0, 1, 0, 0, 0, 0.00),
	(36, 'Depreciaciones Herramientas', 0, 0, 1, 0, 0, 0, 0.00),
	(37, 'Depreciaciones Acumulada Mobiliario y Equipo', 0, 1, 0, 0, 0, 0, 0.00),
	(38, 'Depreciaciones Acumulada Equipo de Computación', 0, 1, 0, 0, 0, 0, 0.00),
	(39, 'Depreciaciones Acumulada Maquinaria', 0, 1, 0, 0, 0, 0, 0.00),
	(40, 'Depreciaciones Acumulada Herramientas', 0, 1, 0, 0, 0, 0, 0.00),
	(41, 'Proveedores', 0, 1, 0, 0, 0, 1, 0.00),
	(42, 'IVA por Pagar', 0, 1, 0, 0, 0, 1, 0.00),
	(43, 'Mano de Obra', 0, 0, 1, 0, 0, 0, 0.00),
	(44, 'Compras', 0, 0, 1, 0, 0, 0, 98700.00),
	(45, 'Ventas', 0, 0, 0, 1, 0, 0, 426389.53),
	(46, 'IVA Retenido sobre Facturas Especiales', 0, 1, 0, 0, 0, 1, 0.00),
	(47, 'ISR Retenido sobre Facturas Especiales', 0, 1, 0, 0, 0, 1, 0.00),
	(48, 'Capital', 0, 0, 0, 0, 1, 0, 0.00),
	(49, 'Devoluciones y rebajas sobre ventas', 0, 0, 1, 0, 0, 0, 2140.00),
	(50, 'Inventario inicial de mercadería', 0, 0, 1, 0, 0, 0, 0.00),
	(51, 'Inventario final de merdacería', 0, 0, 0, 1, 0, 0, 0.00),
	(52, 'Gastos sobre compras', 0, 0, 1, 0, 0, 0, 11890.00),
	(53, 'Devoluciones y rebajas sobre compras', 0, 0, 0, 1, 0, 0, 2310.00),
	(54, 'Créditos Recuperados', 0, 0, 0, 1, 0, 0, 1310.00),
	(55, 'Depreciación Edificiios Sala de Ventas', 0, 0, 1, 0, 0, 0, 8835.75),
	(56, 'Depreciación Vehículos Sala de Ventas', 0, 0, 1, 0, 0, 0, 19118.66),
	(57, 'Depreciación Mobiliario y Equipo Sala de Ventas', 0, 0, 1, 0, 0, 0, 3950.64),
	(58, 'IUSI Sala de Ventas', 0, 0, 1, 0, 0, 0, 2754.00),
	(59, 'Indemnización de Vendedores', 0, 0, 1, 0, 0, 0, 3207.05),
	(60, 'Bono 14 de Vendedores', 0, 0, 1, 0, 0, 0, 3208.33),
	(61, 'Depreciaciones Edificios', 0, 0, 1, 0, 0, 0, 7229.25),
	(62, 'Depreciaciones Vehículos', 0, 0, 1, 0, 0, 0, 15642.54),
	(63, 'Amortización Gastos de Organización', 0, 0, 1, 0, 0, 0, 3160.00),
	(64, 'Papelería y útiles consumidos', 0, 0, 1, 0, 0, 0, 3840.00),
	(65, 'Indemnización de Administración', 0, 0, 1, 0, 0, 0, 4098.36),
	(66, 'Bono 14 de Administración', 0, 0, 1, 0, 0, 0, 4100.00),
	(67, 'IUSI de Oficina', 0, 0, 1, 0, 0, 0, 1836.00),
	(68, 'Cuentas Incobrables', 0, 0, 1, 0, 0, 0, 760.00),
	(69, 'Intereses Gasto', 0, 0, 1, 0, 0, 0, 5125.00),
	(70, 'Alquileres percibidos', 0, 0, 0, 1, 0, 0, 15050.00);
/*!40000 ALTER TABLE `cuentas` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;



-- Volcando estructura para tabla ecegua.detalle_factura
DROP TABLE IF EXISTS `detalle_factura`;
CREATE TABLE IF NOT EXISTS `detalle_factura` (
  `Cafe_Id` int(11) NOT NULL,
  `Factura_Id` int(11) NOT NULL,
  `Cantidad` float DEFAULT NULL,
  `Precio` float DEFAULT NULL,
  KEY `fk_Cafe_has_Factura_Factura1_idx` (`Factura_Id`),
  KEY `fk_Cafe_has_Factura_Cafe1_idx` (`Cafe_Id`),
  CONSTRAINT `fk_Cafe_has_Factura_Cafe1` FOREIGN KEY (`Cafe_Id`) REFERENCES `cafe` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cafe_has_Factura_Factura1` FOREIGN KEY (`Factura_Id`) REFERENCES `factura` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.detalle_factura: ~0 rows (aproximadamente)
DELETE FROM `detalle_factura`;
/*!40000 ALTER TABLE `detalle_factura` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalle_factura` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.detalle_partida
DROP TABLE IF EXISTS `detalle_partida`;
CREATE TABLE IF NOT EXISTS `detalle_partida` (
  `Partida_Id` int(11) NOT NULL,
  `Cuentas_Id` int(11) NOT NULL,
  `Valor` float DEFAULT NULL,
  `Debe` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`Partida_Id`,`Cuentas_Id`),
  KEY `fk_Partida_has_Cuentas_Cuentas1_idx` (`Cuentas_Id`),
  KEY `fk_Partida_has_Cuentas_Partida1_idx` (`Partida_Id`),
  CONSTRAINT `fk_Partida_has_Cuentas_Partida1` FOREIGN KEY (`Partida_Id`) REFERENCES `partida` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Partida_has_Cuentas_Cuentas1` FOREIGN KEY (`Cuentas_Id`) REFERENCES `cuentas` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.detalle_partida: ~0 rows (aproximadamente)
DELETE FROM `detalle_partida`;
/*!40000 ALTER TABLE `detalle_partida` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalle_partida` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.empleo
DROP TABLE IF EXISTS `empleo`;
CREATE TABLE IF NOT EXISTS `empleo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Personal_Id` int(11) NOT NULL,
  `Puesto_Id` int(11) NOT NULL,
  `FechaInicio` date DEFAULT NULL,
  `FechaFin` date DEFAULT NULL,
  `SueldoBase` float DEFAULT NULL,
  `Vigente` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`Id`),
  KEY `fk_Personal_has_Puesto_Puesto1_idx` (`Puesto_Id`),
  KEY `fk_Personal_has_Puesto_Personal_idx` (`Personal_Id`),
  CONSTRAINT `fk_Personal_has_Puesto_Personal` FOREIGN KEY (`Personal_Id`) REFERENCES `personal` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Personal_has_Puesto_Puesto1` FOREIGN KEY (`Puesto_Id`) REFERENCES `puesto` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.empleo: ~0 rows (aproximadamente)
DELETE FROM `empleo`;
/*!40000 ALTER TABLE `empleo` DISABLE KEYS */;
/*!40000 ALTER TABLE `empleo` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.factura
DROP TABLE IF EXISTS `factura`;
CREATE TABLE IF NOT EXISTS `factura` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Clientes_Id` int(11) NOT NULL,
  `Fecha` date DEFAULT NULL,
  `Serie` varchar(45) DEFAULT NULL,
  `Numero` varchar(45) DEFAULT NULL,
  `Total` float DEFAULT NULL,
  `CicloContable_Id` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_Factura_Clientes1_idx` (`Clientes_Id`),
  KEY `fk_Factura_CicloContable1_idx` (`CicloContable_Id`),
  CONSTRAINT `fk_Factura_Clientes1` FOREIGN KEY (`Clientes_Id`) REFERENCES `clientes` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Factura_CicloContable1` FOREIGN KEY (`CicloContable_Id`) REFERENCES `ciclocontable` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.factura: ~0 rows (aproximadamente)
DELETE FROM `factura`;
/*!40000 ALTER TABLE `factura` DISABLE KEYS */;
/*!40000 ALTER TABLE `factura` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.factura_especial
DROP TABLE IF EXISTS `factura_especial`;
CREATE TABLE IF NOT EXISTS `factura_especial` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Proveedor_Id` int(11) DEFAULT NULL,
  `Precio_por_quintal` float DEFAULT NULL,
  `Serie` varchar(45) DEFAULT NULL,
  `Numero` int(11) DEFAULT NULL,
  `Fecha` date DEFAULT NULL,
  `CicloContable_Id` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_Factura Especial_Cliente1_idx` (`Proveedor_Id`),
  KEY `fk_Factura_Especial_CicloContable1_idx` (`CicloContable_Id`),
  CONSTRAINT `fk_Factura Especial_Cliente1` FOREIGN KEY (`Proveedor_Id`) REFERENCES `proveedores` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Factura_Especial_CicloContable1` FOREIGN KEY (`CicloContable_Id`) REFERENCES `ciclocontable` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.factura_especial: ~0 rows (aproximadamente)
DELETE FROM `factura_especial`;
/*!40000 ALTER TABLE `factura_especial` DISABLE KEYS */;
/*!40000 ALTER TABLE `factura_especial` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.factura_exportacion
DROP TABLE IF EXISTS `factura_exportacion`;
CREATE TABLE IF NOT EXISTS `factura_exportacion` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Serie` varchar(45) DEFAULT NULL,
  `Numero` varchar(45) DEFAULT NULL,
  `Precio` float DEFAULT NULL,
  `Clientes_exportacion_Id` int(11) NOT NULL,
  `Cafe_Id` int(11) NOT NULL,
  `Fecha` date DEFAULT NULL,
  `Cantidad` float DEFAULT NULL,
  `CicloContable_Id` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_Factura_exportación_Clientes_exportacion1_idx` (`Clientes_exportacion_Id`),
  KEY `fk_Factura_exportación_Cafe1_idx` (`Cafe_Id`),
  KEY `fk_Factura_exportacion_CicloContable1_idx` (`CicloContable_Id`),
  CONSTRAINT `fk_Factura_exportación_Clientes_exportacion1` FOREIGN KEY (`Clientes_exportacion_Id`) REFERENCES `clientes_exportacion` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Factura_exportación_Cafe1` FOREIGN KEY (`Cafe_Id`) REFERENCES `cafe` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Factura_exportacion_CicloContable1` FOREIGN KEY (`CicloContable_Id`) REFERENCES `ciclocontable` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.factura_exportacion: ~0 rows (aproximadamente)
DELETE FROM `factura_exportacion`;
/*!40000 ALTER TABLE `factura_exportacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `factura_exportacion` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.horasextras
DROP TABLE IF EXISTS `horasextras`;
CREATE TABLE IF NOT EXISTS `horasextras` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `CicloContable_Id` int(11) NOT NULL,
  `Empleo_Id` int(11) NOT NULL,
  `Fecha` date DEFAULT NULL,
  `HoraInicio` time DEFAULT NULL,
  `HoraFin` time DEFAULT NULL,
  `HorasExtra` float DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_Horario_Trabajo1_idx` (`Empleo_Id`),
  KEY `fk_HorasExtras_CicloContable1_idx` (`CicloContable_Id`),
  CONSTRAINT `fk_Horario_Trabajo1` FOREIGN KEY (`Empleo_Id`) REFERENCES `empleo` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_HorasExtras_CicloContable1` FOREIGN KEY (`CicloContable_Id`) REFERENCES `ciclocontable` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.horasextras: ~0 rows (aproximadamente)
DELETE FROM `horasextras`;
/*!40000 ALTER TABLE `horasextras` DISABLE KEYS */;
/*!40000 ALTER TABLE `horasextras` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.mes
DROP TABLE IF EXISTS `mes`;
CREATE TABLE IF NOT EXISTS `mes` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Mes` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.mes: ~12 rows (aproximadamente)
DELETE FROM `mes`;
/*!40000 ALTER TABLE `mes` DISABLE KEYS */;
INSERT INTO `mes` (`Id`, `Mes`) VALUES
	(1, 'Enero'),
	(2, 'Febrero'),
	(3, 'Marzo'),
	(4, 'Abril'),
	(5, 'Mayo'),
	(6, 'Junio'),
	(7, 'Julio'),
	(8, 'Agosto'),
	(9, 'Septiembre'),
	(10, 'Octubre'),
	(11, 'Noviembre'),
	(12, 'Diciembre');
/*!40000 ALTER TABLE `mes` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.mezcla
DROP TABLE IF EXISTS `mezcla`;
CREATE TABLE IF NOT EXISTS `mezcla` (
  `NotaRendimientoActual` int(11) NOT NULL,
  `NotaRendimientoAnterior` int(11) NOT NULL,
  `Cosecha_Id` int(11) NOT NULL,
  `PesoAnterior` float DEFAULT NULL,
  PRIMARY KEY (`NotaRendimientoActual`,`NotaRendimientoAnterior`),
  KEY `fk_NotaRendimiento_has_NotaRendimiento_NotaRendimiento2_idx` (`NotaRendimientoAnterior`),
  KEY `fk_NotaRendimiento_has_NotaRendimiento_NotaRendimiento1_idx` (`NotaRendimientoActual`),
  KEY `fk_Mezcla_Cosecha1_idx` (`Cosecha_Id`),
  CONSTRAINT `fk_NotaRendimiento_has_NotaRendimiento_NotaRendimiento1` FOREIGN KEY (`NotaRendimientoActual`) REFERENCES `notarendimiento` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_NotaRendimiento_has_NotaRendimiento_NotaRendimiento2` FOREIGN KEY (`NotaRendimientoAnterior`) REFERENCES `notarendimiento` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Mezcla_Cosecha1` FOREIGN KEY (`Cosecha_Id`) REFERENCES `cosecha` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.mezcla: ~0 rows (aproximadamente)
DELETE FROM `mezcla`;
/*!40000 ALTER TABLE `mezcla` DISABLE KEYS */;
/*!40000 ALTER TABLE `mezcla` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.notarendimiento
DROP TABLE IF EXISTS `notarendimiento`;
CREATE TABLE IF NOT EXISTS `notarendimiento` (
  `Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `Cosecha_Id` int(11) NOT NULL,
  `FechaNota` date DEFAULT NULL,
  `Marca` varchar(45) DEFAULT NULL,
  `FechaEmbarque` date DEFAULT NULL,
  `NumEmbarque` varchar(45) DEFAULT NULL,
  `NumSacos` int(11) DEFAULT NULL,
  `PesoAExportar` float DEFAULT NULL,
  `Imperfecciones` int(11) DEFAULT NULL,
  `PuertoEmbarque` varchar(45) DEFAULT NULL,
  `Saldo` float DEFAULT NULL,
  PRIMARY KEY (`Codigo`),
  KEY `fk_NotaRendimiento_Cosecha1_idx` (`Cosecha_Id`),
  CONSTRAINT `fk_NotaRendimiento_Cosecha1` FOREIGN KEY (`Cosecha_Id`) REFERENCES `cosecha` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.notarendimiento: ~0 rows (aproximadamente)
DELETE FROM `notarendimiento`;
/*!40000 ALTER TABLE `notarendimiento` DISABLE KEYS */;
/*!40000 ALTER TABLE `notarendimiento` ENABLE KEYS */;


-- Volcando estructura para procedimiento ecegua.nuevoAnio
DROP PROCEDURE IF EXISTS `nuevoAnio`;
DELIMITER //
CREATE DEFINER=`eceguaxela`@`%` PROCEDURE `nuevoAnio`()
BEGIN
	DECLARE vYaExiste INT;
	DECLARE vIdNuevoAnio INT;
	SELECT COUNT(Anio) INTO vYaExiste FROM Anio WHERE Anio = YEAR(NOW());
	IF (vYaExiste = 0) THEN	-- El Año aún no ha sido creado. Se creará, junto a 12 Ciclos Contables
		INSERT INTO Anio(Anio) VALUES(YEAR(NOW()));	-- Creación del Registro del Año
		-- Creación de los 12 registros de los Ciclos Contables (uno por cada Mes-NuevoAño)
		SET vIdNuevoAnio = LAST_INSERT_ID();	-- Función que devuelve el ID del último registro insertado
		INSERT INTO CicloContable(Anio_Id, Mes_Id) VALUES
			(vIdNuevoAnio, 1),
			(vIdNuevoAnio, 2),
			(vIdNuevoAnio, 3),
			(vIdNuevoAnio, 4),
			(vIdNuevoAnio, 5),
			(vIdNuevoAnio, 6),
			(vIdNuevoAnio, 7),
			(vIdNuevoAnio, 8),
			(vIdNuevoAnio, 9),
			(vIdNuevoAnio, 10),
			(vIdNuevoAnio, 11),
			(vIdNuevoAnio, 12);	-- Por defecto, todos los Ciclos están no Activos
	END IF;
	-- Hasta aquí se garantiza la creación de un Nuevo Año, en caso de aún no existir en la Base de Datos
END//
DELIMITER ;


-- Volcando estructura para tabla ecegua.ordentrilla
DROP TABLE IF EXISTS `ordentrilla`;
CREATE TABLE IF NOT EXISTS `ordentrilla` (
  `Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `Fecha` date DEFAULT NULL,
  `Cliente` varchar(45) DEFAULT NULL,
  `Preparacion` varchar(45) DEFAULT NULL,
  `Marca` varchar(45) DEFAULT NULL,
  `NotaRendimiento_Codigo` int(11) DEFAULT NULL,
  `Disponible` tinyint(4) DEFAULT NULL,
  `CafeUsado` float DEFAULT NULL,
  `Cosecha_Id` int(11) NOT NULL,
  PRIMARY KEY (`Codigo`),
  KEY `fk_OrdenTrilla_NotaRendimiento1_idx` (`NotaRendimiento_Codigo`),
  KEY `fk_OrdenTrilla_Cosecha1_idx` (`Cosecha_Id`),
  CONSTRAINT `fk_OrdenTrilla_NotaRendimiento1` FOREIGN KEY (`NotaRendimiento_Codigo`) REFERENCES `notarendimiento` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_OrdenTrilla_Cosecha1` FOREIGN KEY (`Cosecha_Id`) REFERENCES `cosecha` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.ordentrilla: ~0 rows (aproximadamente)
DELETE FROM `ordentrilla`;
/*!40000 ALTER TABLE `ordentrilla` DISABLE KEYS */;
/*!40000 ALTER TABLE `ordentrilla` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.organizacion
DROP TABLE IF EXISTS `organizacion`;
CREATE TABLE IF NOT EXISTS `organizacion` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) DEFAULT NULL,
  `Direccion` varchar(45) DEFAULT NULL,
  `Telefono` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.organizacion: ~1 rows (aproximadamente)
DELETE FROM `organizacion`;
/*!40000 ALTER TABLE `organizacion` DISABLE KEYS */;
INSERT INTO `organizacion` (`Id`, `Nombre`, `Direccion`, `Telefono`) VALUES
	(0, 'Independiente', NULL, NULL);
/*!40000 ALTER TABLE `organizacion` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.otros
DROP TABLE IF EXISTS `otros`;
CREATE TABLE IF NOT EXISTS `otros` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) DEFAULT NULL,
  `Saldo` float DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.otros: ~9 rows (aproximadamente)
DELETE FROM `otros`;
/*!40000 ALTER TABLE `otros` DISABLE KEYS */;
INSERT INTO `otros` (`Id`, `Nombre`, `Saldo`) VALUES
	(1, 'Ventas netas', 0),
	(2, 'Compras brutas', 0),
	(3, 'Compras netas', 0),
	(4, 'Mercadería disponible', 0),
	(5, 'Costo de ventas', 0),
	(6, 'Margen bruto', 0),
	(7, 'Resultado de Operacion', 0),
	(8, 'Ganancia antes del ISR', 0),
	(9, 'Ganancia antes de la Reserva Legal', 0),
	(10, 'Ganancia neta', 0),
	(11, 'Inventario inicial de Mercaderías', 0),
	(12, 'Inventario final de Mercaderías', 0);
/*!40000 ALTER TABLE `otros` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.pago
DROP TABLE IF EXISTS `pago`;
CREATE TABLE IF NOT EXISTS `pago` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Empleo_Id` int(11) NOT NULL,
  `CicloContable_Id` int(11) NOT NULL,
  `Fecha` date DEFAULT NULL,
  `CantidadHorasExtras` float DEFAULT NULL,
  `IGSS` float DEFAULT NULL,
  `Prestamo` float DEFAULT NULL,
  `PrecioHoraExtra` float DEFAULT NULL,
  `Partida_Id` int(11) DEFAULT '0',
  PRIMARY KEY (`Id`),
  KEY `fk_CicioContable_has_Empleo_Empleo1_idx` (`Empleo_Id`),
  KEY `fk_Pago_CicloContable1_idx` (`CicloContable_Id`),
  KEY `fk_Pago_Partida1_idx` (`Partida_Id`),
  CONSTRAINT `fk_CicioContable_has_Empleo_Empleo1` FOREIGN KEY (`Empleo_Id`) REFERENCES `empleo` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Pago_CicloContable1` FOREIGN KEY (`CicloContable_Id`) REFERENCES `ciclocontable` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Pago_Partida1` FOREIGN KEY (`Partida_Id`) REFERENCES `partida` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.pago: ~0 rows (aproximadamente)
DELETE FROM `pago`;
/*!40000 ALTER TABLE `pago` DISABLE KEYS */;
/*!40000 ALTER TABLE `pago` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.partida
DROP TABLE IF EXISTS `partida`;
CREATE TABLE IF NOT EXISTS `partida` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `CicloContable_Id` int(11) NOT NULL,
  `Numero` int(11) DEFAULT NULL,
  `Fecha` date DEFAULT NULL,
  `Descripcion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_Partida_CicloContable1_idx` (`CicloContable_Id`),
  CONSTRAINT `fk_Partida_CicloContable1` FOREIGN KEY (`CicloContable_Id`) REFERENCES `ciclocontable` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.partida: ~0 rows (aproximadamente)
DELETE FROM `partida`;
/*!40000 ALTER TABLE `partida` DISABLE KEYS */;
/*!40000 ALTER TABLE `partida` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.personal
DROP TABLE IF EXISTS `personal`;
CREATE TABLE IF NOT EXISTS `personal` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `NIT` varchar(45) DEFAULT NULL,
  `DPI` varchar(13) DEFAULT NULL,
  `Nombre` varchar(45) DEFAULT NULL,
  `FechaNacimiento` date DEFAULT NULL,
  `Sexo` varchar(1) DEFAULT NULL,
  `EstadoCivil` varchar(45) DEFAULT NULL,
  `Profesion` varchar(45) DEFAULT NULL,
  `Idioma` varchar(45) DEFAULT NULL,
  `Direccion` varchar(45) DEFAULT NULL,
  `Telefono` varchar(8) DEFAULT NULL,
  `eMail` varchar(45) DEFAULT NULL,
  `Trabajando` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.personal: ~0 rows (aproximadamente)
DELETE FROM `personal`;
/*!40000 ALTER TABLE `personal` DISABLE KEYS */;
/*!40000 ALTER TABLE `personal` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.producto
DROP TABLE IF EXISTS `producto`;
CREATE TABLE IF NOT EXISTS `producto` (
  `NotaRendimiento_Codigo` int(11) NOT NULL,
  `Cafe_Id` int(11) NOT NULL,
  `PesoFinal` float DEFAULT NULL,
  `CantidadSacos` int(11) DEFAULT NULL,
  PRIMARY KEY (`NotaRendimiento_Codigo`,`Cafe_Id`),
  KEY `fk_NotaRendimiento_has_CafeOro_NotaRendimiento1_idx` (`NotaRendimiento_Codigo`),
  KEY `fk_Producto_Cafe1_idx` (`Cafe_Id`),
  CONSTRAINT `fk_NotaRendimiento_has_CafeOro_NotaRendimiento1` FOREIGN KEY (`NotaRendimiento_Codigo`) REFERENCES `notarendimiento` (`Codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Producto_Cafe1` FOREIGN KEY (`Cafe_Id`) REFERENCES `cafe` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.producto: ~0 rows (aproximadamente)
DELETE FROM `producto`;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.productor
DROP TABLE IF EXISTS `productor`;
CREATE TABLE IF NOT EXISTS `productor` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) DEFAULT NULL,
  `Direccion` varchar(45) DEFAULT NULL,
  `TipoCertificado` varchar(45) DEFAULT NULL,
  `Telefono` varchar(45) DEFAULT NULL,
  `Organizacion_Id` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_Productor_Organizacion_idx` (`Organizacion_Id`),
  CONSTRAINT `fk_Productor_Organizacion` FOREIGN KEY (`Organizacion_Id`) REFERENCES `organizacion` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.productor: ~0 rows (aproximadamente)
DELETE FROM `productor`;
/*!40000 ALTER TABLE `productor` DISABLE KEYS */;
/*!40000 ALTER TABLE `productor` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.proveedores
DROP TABLE IF EXISTS `proveedores`;
CREATE TABLE IF NOT EXISTS `proveedores` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `CUI` varchar(13) DEFAULT NULL,
  `NIT` varchar(9) DEFAULT NULL,
  `Nombre` varchar(45) DEFAULT NULL,
  `Direccion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.proveedores: ~0 rows (aproximadamente)
DELETE FROM `proveedores`;
/*!40000 ALTER TABLE `proveedores` DISABLE KEYS */;
/*!40000 ALTER TABLE `proveedores` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.puesto
DROP TABLE IF EXISTS `puesto`;
CREATE TABLE IF NOT EXISTS `puesto` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre_Puesto` varchar(25) DEFAULT NULL,
  `Descripcion` varchar(45) DEFAULT NULL,
  `esAdministracion` tinyint(1) DEFAULT '0',
  `esProduccion` tinyint(1) DEFAULT '0',
  `esVentas` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.puesto: ~0 rows (aproximadamente)
DELETE FROM `puesto`;
/*!40000 ALTER TABLE `puesto` DISABLE KEYS */;
/*!40000 ALTER TABLE `puesto` ENABLE KEYS */;


-- Volcando estructura para tabla ecegua.recibo
DROP TABLE IF EXISTS `recibo`;
CREATE TABLE IF NOT EXISTS `recibo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Cosecha_Id` int(11) NOT NULL,
  `Productor_Id` int(11) NOT NULL,
  `Cafe_Id` int(11) NOT NULL,
  `Numero` int(11) DEFAULT NULL,
  `Fecha` date DEFAULT NULL,
  `NumEnvio` int(11) DEFAULT NULL,
  `Peso` float DEFAULT NULL,
  `SacosNylon` int(11) DEFAULT NULL,
  `SacosYuta` int(11) DEFAULT NULL,
  `Saldo` float DEFAULT NULL,
  `Facturado` tinyint(1) DEFAULT '0',
  `Factura_Especial_Id` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_CafePergamino_has_Productor_Productor1_idx` (`Productor_Id`),
  KEY `fk_Recibo_Cosecha1_idx` (`Cosecha_Id`),
  KEY `fk_Recibo_Factura Especial1_idx` (`Factura_Especial_Id`),
  KEY `fk_Recibo_Cafe1_idx` (`Cafe_Id`),
  CONSTRAINT `fk_CafePergamino_has_Productor_Productor1` FOREIGN KEY (`Productor_Id`) REFERENCES `productor` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Recibo_Cosecha1` FOREIGN KEY (`Cosecha_Id`) REFERENCES `cosecha` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Recibo_Factura Especial1` FOREIGN KEY (`Factura_Especial_Id`) REFERENCES `factura_especial` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Recibo_Cafe1` FOREIGN KEY (`Cafe_Id`) REFERENCES `cafe` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla ecegua.recibo: ~0 rows (aproximadamente)
DELETE FROM `recibo`;
/*!40000 ALTER TABLE `recibo` DISABLE KEYS */;
/*!40000 ALTER TABLE `recibo` ENABLE KEYS */;


-- Volcando estructura para disparador ecegua.agregarHorasExtra
DROP TRIGGER IF EXISTS `agregarHorasExtra`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `agregarHorasExtra` AFTER INSERT ON `horasextras` FOR EACH ROW BEGIN
	DECLARE vCantidadPago INT;
	DECLARE vCantidadHorasExtra FLOAT;
	DECLARE vPrecioHoraExtra FLOAT;
	DECLARE vIGSS FLOAT;
	DECLARE vSueldoBase FLOAT;
	SELECT COUNT(CantidadHorasExtras), PrecioHoraExtra, CantidadHorasExtras, IGSS INTO vCantidadPago, vPrecioHoraExtra, vCantidadHorasExtra, vIGSS FROM Pago
		WHERE Empleo_Id = NEW.Empleo_Id AND CicloContable_Id = NEW.CicloContable_Id;
	-- Si vCantidadPago = 0 es porque aún no se ha creado el pago, y vCantidadHorasExtra y vIGSS son NULL
	IF (vCantidadPago != 0) THEN
		-- Obtengo el Sueldo Base del Empleo
		SELECT SueldoBase INTO vSueldoBase FROM Empleo WHERE Id = NEW.Empleo_Id;
		SET vIGSS = (vSueldoBase + vPrecioHoraExtra*(vCantidadHorasExtra + NEW.HorasExtra))*0.0483;
		UPDATE Pago SET CantidadHorasExtras = vCantidadHorasExtra + NEW.HorasExtra, IGSS = vIGSS
			WHERE Empleo_Id = NEW.Empleo_Id AND CicloContable_Id = NEW.CicloContable_Id;
	END IF;
	-- Hasta aquí se garantiza la actualización del Total de Horas Extra y el valor de IGSS, al crear nuevas horas extra
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;


-- Volcando estructura para disparador ecegua.nuevoDetallePartida
DROP TRIGGER IF EXISTS `nuevoDetallePartida`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `nuevoDetallePartida` AFTER INSERT ON `detalle_partida` FOR EACH ROW BEGIN
	DECLARE vSaldoDetalle FLOAT;
	DECLARE vEsActivo INT;
	DECLARE vEsPasivo INT;
	DECLARE vEsPerdida INT;
	DECLARE vEsGanancia INT;
	-- Verifico si la Cuenta es Activo, Pasivo, Pérdida o Ganancia
	SELECT Activo, Pasivo, Perdida, Ganancia INTO vEsActivo, vEsPasivo, vEsPerdida, vEsGanancia FROM Cuentas WHERE Id = NEW.Cuentas_Id;
	-- Si es Activo o Pérdida, su saldo: Aumenta con un Detalle con Debe; Disminuye con un Detalle con Haber
	-- Si es Pasivo o Ganancia, su saldo: Aumenta con un Detalle con Haber; Disminuye con un Detalle con Debe
	SET vSaldoDetalle = NEW.Valor;
	IF (NEW.Debe = 1) THEN	-- Si el Saldo del Detalle está en el lado del Debe
		IF (vEsPasivo = 1 OR vEsGanancia = 1) THEN	-- El Saldo del Detalle se restará al Saldo de la Cuenta
			SET vSaldoDetalle = vSaldoDetalle*-1;
		END IF;
		-- Si el Saldo del Detalle se sumara al Saldo de la Cuenta, no es necesario indicarlo (ya es positivo)
	ELSE	-- Si el Saldo del Detalle está en el lado del Haber
		IF (vEsActivo = 1 OR vEsPerdida = 1) THEN	-- El Saldo del Detalle se restará al Saldo de la Cuenta
			SET vSaldoDetalle = vSaldoDetalle*-1;
		END IF;
		-- Si el Saldo del Detalle se sumara al Saldo de la Cuenta, no es necesario indicarlo (ya es positivo)
	END IF;
	-- Hasta aquí, vSaldoDetalle se sumará o restará a la Cuenta asociada en el Detalle de la Partida
	
	UPDATE Cuentas SET Saldo = Saldo + vSaldoDetalle WHERE Id = NEW.Cuentas_Id;
	-- Hasta aquí se garantiza la actualización del Saldo en la Cuenta asociada al Detalle de la Partida creada
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
