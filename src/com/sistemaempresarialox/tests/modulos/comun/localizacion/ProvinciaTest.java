package com.sistemaempresarialox.tests.modulos.comun.localizacion;

import static org.openxava.jpa.XPersistence.commit;
import static org.openxava.jpa.XPersistence.getManager;

import java.util.*;

import com.sistemaempresarialox.base.modelo.*;
import com.sistemaempresarialox.modulos.comun.localizacion.modelo.*;
import com.sistemaempresarialox.tests.base.*;
import com.sistemaempresarialox.util.*;
import com.sistemaempresarialox.util.enumeradores.*;

public class ProvinciaTest extends EntidadBaseNoEliminableConEstadoModuleTestBase {
	private Pais pais1;
	private Pais pais2;
	
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
		borrarPaisDePrueba();
	}
	
	@Override
	protected String obtenerNombreCalificadoModulo(){
		return "com.sistemaempresarialox.modulos.comun.localizacion.modelo.Provincia";
	}

	@Override
	protected List<EntidadBaseNoEliminableConEstado> crearEntidadesBaseNoEliminable(){
		crearPaisesDePrueba();
		
		List<EntidadBaseNoEliminableConEstado> provincias = new ArrayList<>();
		
		Provincia provincia1 = new Provincia();
		provincia1.setNombre("provincia1");
		provincia1.setCodigoSistemaTributario(001);
		provincia1.setPais(pais1);
		provincia1.setEstado(EstadoEntidad.ACTIVO);
		
		Provincia provincia2 = new Provincia();
		provincia2.setNombre("provincia2");
		provincia2.setCodigoSistemaTributario(002);
		provincia2.setPais(pais2);
		provincia2.setEstado(EstadoEntidad.ACTIVO);
		
		Provincia provincia3 = new Provincia();
		provincia3.setNombre("provincia3");
		provincia3.setCodigoSistemaTributario(003);
		provincia3.setPais(pais1);
		provincia3.setEstado(EstadoEntidad.ACTIVO);
		
		provincias.add(provincia1);
		provincias.add(provincia2);
		provincias.add(provincia3);
		
		return provincias;
	}
	
	private void crearPaisesDePrueba(){
		pais1 = new Pais();
		pais1.setNombre("pais 1");
		pais1.setCodigoISO("p1");
		pais1.setCodigoSistemaTributario(001);
		pais1.setEstado(EstadoEntidad.ACTIVO);
		
		pais2 = new Pais();
		pais2.setNombre("pais 2");
		pais2.setCodigoISO("p2");
		pais2.setCodigoSistemaTributario(002);
		pais2.setEstado(EstadoEntidad.ACTIVO);
		
		getManager().persist(pais1);
		getManager().persist(pais2);
	}
	
	private void borrarPaisDePrueba(){
		EntidadUtil.borrarEntidadesDesasociadas(pais1, pais2);
		commit();
	}
	
	public void testCrearYModificarProvincia() throws Exception {
		crearPais();
		cambiarPaisDeProvincia();
		
		borrarEntidadBaseNoEliminableCreadaEnPantalla(
				"nombre", "provincia prueba", Provincia.class);
	}

	private void crearPais() throws Exception {
		execute("GeneralSistemaEmpresarialOX.new");
		setValue("nombre", "provincia prueba");
		setValue("codigoSistemaTributario", "999");
		setValue("pais.id", String.valueOf(pais1.getId()));
		execute("CRUD.save");
		assertNoErrors();
	}
	
	private void cambiarPaisDeProvincia() throws Exception {
		setValue("nombre", "provincia prueba");
		execute("CRUD.refresh");
		assertNoErrors();
		
		setValue("pais.id", String.valueOf(pais2.getId()));
		execute("CRUD.save");
		assertNoErrors();
		
		verificarCambioPaisDeProvincia();
	}

	private void verificarCambioPaisDeProvincia() throws Exception {
		setValue("nombre", "provincia prueba");
		execute("CRUD.refresh");
		assertNoErrors();
		assertValue("pais.id", String.valueOf(pais2.getId()));
	}
}
