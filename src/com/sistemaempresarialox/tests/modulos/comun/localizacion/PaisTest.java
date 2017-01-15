package com.sistemaempresarialox.tests.modulos.comun.localizacion;

import org.openxava.jpa.*;
import org.openxava.tests.*;

import com.sistemaempresarialox.modulos.comun.localizacion.modelo.*;

public class PaisTest extends ModuleTestBase {
	public PaisTest(String nombrePrueba){
		super(nombrePrueba, "SistemaEmpresarialOX", "Pais");
	}
	
	public void testCrearPais() throws Exception {
		execute("CRUD.new");
		setValue("nombre", "pais de prueba");
		setValue("codigoISO", "pp");
		setValue("codigoSistemaTributario", "999");
		execute("CRUD.save");
		assertNoErrors();
		
		borrarEntidad();
	}
	
	private void borrarEntidad() throws Exception{
		setValue("nombre", "pais de prueba");
		execute("CRUD.refresh");
		
		long idPais = Long.parseLong(getValue("id"));
		
		Pais pais = XPersistence.getManager().find(Pais.class, idPais);
		XPersistence.getManager().remove(pais);
	}
}
