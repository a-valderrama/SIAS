CREATE TABLE usuario(
	usuario varchar(200) UNIQUE NOT NULL,
	contraseña varchar(30) NOT NULL,
	tipo varchar(15) NOT NULL,
	CONSTRAINT "PK_usuario" PRIMARY KEY (usuario)
);

COMMENT ON TABLE usuario IS 'Usuarios autorizados en el sistema. La llave primaria es el nombre del usuario, por lo que no podrá haber usuarios iguales';

CREATE TABLE bitacora(
	id_bitacora serial,
	usuario varchar(200) NOT NULL,
	accion varchar(35) NOT NULL,
	acceso varchar(25) NOT NULL,
	CONSTRAINT "PK_bitacora" PRIMARY KEY (id_bitacora),
	CONSTRAINT "FK_bitacora_uduario" FOREIGN KEY (usuario) REFERENCES usuario(usuario)
);

COMMENT ON TABLE bitacora IS 'Tabla en la que se registrará los accesos del usuario.';

CREATE TABLE layout(
	id serial,
	poliza varchar(30) NOT NULL,
	endoso int NOT NULL,
	id_aseguradora int NOT NULL,
	id_credito int NOT NULL,
	id_trabajador int NOT NULL,
	paterno varchar (40) NOT NULL,
	materno varchar (40) NOT NULL,
	nombre varchar (40) NOT NULL,
	segundo_nombre varchar (40),
	fecha_nacimiento varchar (15) NOT NULL,
	sexo char NOT NULL,
	rfc_trabajador varchar (17) NOT NULL,
	importe double precision NOT NULL,
	empresa varchar (100) NOT NULL,
	domicilio_ct varchar (500),
	tel_principal varchar (40) NOT NULL,
	tel_cel varchar (40) NOT NULL,
	tipo_seg_social int NOT NULL,
	num_seguro_social varchar (15) NOT NULL,
	sucursal int NOT NULL,
	producto int NOT NULL,
	cobertura int NOT NULL,
	id_envio int NOT NULL,
	referencia_bancaria double precision NOT NULL,
	fecha_baja varchar NOT NULL,
	suma_reclamospag double precision NOT NULL,
	suma_aseguradoratot double precision NOT NULL,
	fecha_inicio_recl varchar (15) NOT NULL,
	fecha_primervto varchar (15) NOT NULL,
	plazo int NOT NULL,
	tipo_movimiento char NOT NULL,
	fecha_inicio varchar (15) NOT NULL,
	fecha_vto_final varchar (15) NOT NULL,
	max_r_pagadas int,
	actuales int,
	soporte boolean DEFAULT false,
	CONSTRAINT "PK_layout" PRIMARY KEY (id)
);

COMMENT ON TABLE layout IS 'Tabla que representa la información que sube el usario en el layout.';