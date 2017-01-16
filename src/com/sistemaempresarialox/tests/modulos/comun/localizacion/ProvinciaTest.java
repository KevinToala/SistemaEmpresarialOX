package com.sistemaempresarialox.tests.modulos.comun.localizacion;

import static org.openxava.jpa.XPersistence.commit;
import static org.openxava.jpa.XPersistence.getManager;

import java.util.*;

import com.sistemaempresarialox.base.modelo.*;
import com.sistemaempresarialox.modulos.comun.localizacion.modelo.*;
import com.sistemaempresarialox.tests.base.*;
import com.sistemaempresarialox.util.*;
import com.sistemaempresarialox.util.enumeradores.*;

public class ProvinciaTest extends EntidadBaseNoEliminableModuleTestBase {
	private Pais pais;
	
	public ProvinciaTest(String nombrePrueba){
		super(nombrePrueba, "Provincia");
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		borrarPais();
	}
	
	@Override
	protected String obtenerNombreCalificadoModulo(){
		return "com.sistemaempresarialox.modulos.comun.localizacion.modelo.Provincia";
	}

	@Override
	protected List<EntidadBaseNoEliminable> crearEntidadesBaseNoEliminable(){
		crearPais();
		
		List<EntidadBaseNoEliminable> provincias = new ArrayList<>();
		
		Provincia provincia1 = new Provincia();
		provincia1.setNombre("provincia1");
		provincia1.setCodigoSistemaTributario(001);
		provincia1.setPais(pais);
		provincia1.setEstado(EstadoEntidad.ACTIVO);
		
		Provincia provincia2 = new Provincia();
		provincia2.setNombre("provincia2");
		provincia2.setCodigoSistemaTributario(002);
		provincia2.setPais(pais);
		provincia2.setEstado(EstadoEntidad.ACTIVO);
		
		Provincia provincia3 = new Provincia();
		provincia3.setNombre("provincia3");
		provincia3.setCodigoSistemaTributario(003);
		provincia3.setPais(pais);
		provincia3.setEstado(EstadoEntidad.ACTIVO);
		
		provincias.add(provincia1);
		provincias.add(provincia2);
		provincias.add(provincia3);
		
		return provincias;
	}
	
	private void crearPais(){
		pais = new Pais();
		pais.setNombre("pais prueba");
		pais.setCodigoISO("pp");
		pais.setCodigoSistemaTributario(001);
		pais.setEstado(EstadoEntidad.ACTIVO);
		
		getManager().persist(pais);
	}
	
	private void borrarPais(){
		EntidadUtil.borrarEntidadesDesasociadas(pais);
		commit();
	}
	
	public void testCrearProvincia() throws Exception {
		execute("CRUD.new");
		setValue("nombre", "provincia prueba");
		setValue("codigoSistemaTributario", "999");
		setValue("pais.id", String.valueOf(pais.getId()));
		execute("CRUD.save");
		assertNoErrors();
		
		borrarEntidadBaseNoEliminableCreadaEnPantalla(
				"nombre", "provincia prueba", Provincia.class);
	}
}
