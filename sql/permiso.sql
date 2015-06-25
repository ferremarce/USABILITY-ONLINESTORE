INSERT INTO PERMISO (DESCRIPCION_PERMISO, NIVEL, ORDEN, TAG_MENU, URL_IMAGEN) 
	VALUES ('Archivo', '9', 0, '', 'ui-icon ui-icon-plusthick');
INSERT INTO PERMISO (DESCRIPCION_PERMISO, NIVEL, ORDEN, TAG_MENU, URL_IMAGEN) 
	VALUES ('Usuarios', '9.1', 0, '#{UsuarioController.doListarForm}', 'ui-icon-person');
INSERT INTO PERMISO (DESCRIPCION_PERMISO, NIVEL, ORDEN, TAG_MENU, URL_IMAGEN) 
	VALUES ('Roles', '9.2', 0, '#{RolController.doListarForm}', 'ui-icon ui-icon-gear');
INSERT INTO PERMISO (DESCRIPCION_PERMISO, NIVEL, ORDEN, TAG_MENU, URL_IMAGEN) 
	VALUES ('Opciones', '1', 0, '', '');
INSERT INTO PERMISO (DESCRIPCION_PERMISO, NIVEL, ORDEN, TAG_MENU, URL_IMAGEN) 
	VALUES ('lblCatalogoProducto', '1.1', 0, '#{ArticuloController.doListarForm}', 'ui-icon-contact');
INSERT INTO PERMISO (DESCRIPCION_PERMISO, NIVEL, ORDEN, TAG_MENU, URL_IMAGEN) 
	VALUES ('Tipificaciones', '9.3', 0, '#{SubTipoController.doListarForm}', 'ui-icon-grip-diagonal-se');
INSERT INTO PERMISO (DESCRIPCION_PERMISO, NIVEL, ORDEN, TAG_MENU, URL_IMAGEN) 
	VALUES ('Experimento', '2', 0, '', NULL);
INSERT INTO PERMISO (DESCRIPCION_PERMISO, NIVEL, ORDEN, TAG_MENU, URL_IMAGEN) 
	VALUES ('Sujetos Experimentales', '2.1', 0, '#{ArticuloController.doListarForm}', 'ui-icon ui-icon-person');

