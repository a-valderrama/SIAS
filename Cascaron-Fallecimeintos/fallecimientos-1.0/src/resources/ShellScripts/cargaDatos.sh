#!/bin/bash
dbname="SIAS"
psql $dbname << EOF
\COPY layout(poliza,endoso,id_aseguradora,id_credito,id_trabajador,paterno,materno,nombre,segundo_nombre,fecha_nacimiento,sexo,rfc_trabajador,importe,empresa,domicilio_ct,tel_principal,tel_cel,tipo_seg_social,num_seguro_social,sucursal,producto,cobertura,id_envio,referencia_bancaria,fecha_baja) FROM /home/dielsale/Documentos/SIAS/Cascaron-Fallecimeintos/fallecimientos-1.0/src/resources/UploadedFiles/Fii/layout_02-07-2019.csv DELIMITER ',' CSV HEADER;
EOF