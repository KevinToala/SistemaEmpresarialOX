package com.sistemaempresarialox.tests;

import org.openxava.tests.*;

public class PruebaTest extends ModuleTestBase {
	public PruebaTest(String nombrePrueba){
		super(nombrePrueba, "SistemaEmpresarialOX", "Pais");
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testCrearPais() throws Exception {
		execute("GeneralSistemaEmpresarialOX.new");
		setValue("nombre", "pais de prueba");
		setValue("codigoISO", "pp");
		setValue("codigoSistemaTributario", "999");
		execute("CRUD.save");
		assertNoErrors();
		
		/*borrarEntidadBaseNoEliminableConEstadoCreadaEnPantalla(
				"nombre", "pais de prueba", Pais.class);*/
	}
}
