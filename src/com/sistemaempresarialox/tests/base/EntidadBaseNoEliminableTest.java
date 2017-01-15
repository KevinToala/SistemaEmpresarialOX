package com.sistemaempresarialox.tests.base;

import org.openxava.tests.*;
import static org.openxava.jpa.XPersistence.*;

import com.sistemaempresarialox.base.modelo.*;
import com.sistemaempresarialox.modulos.comun.localizacion.modelo.*;
import com.sistemaempresarialox.util.enumeradores.*;

public class EntidadBaseNoEliminableTest extends ModuleTestBase {
	private Pais pais;
	
	private static String MODULO_DE_ENTIDAD_NO_ELIMINABLE = "Pais";
	private static String NOMBRE_CALIFICADO_MODULO_DE_ENTIDAD_NO_ELIMINABLE =
			"com.sistemaempresarialox.modulos.comun.localizacion.modelo.Pais";
	
	public EntidadBaseNoEliminableTest(String nombrePrueba){
		super(nombrePrueba, "SistemaEmpresarialOX", MODULO_DE_ENTIDAD_NO_ELIMINABLE);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		crearEntidad();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		borrarEntidad();
	}
	
	private void crearEntidad(){
		pais = new Pais();
		pais.setNombre("pais prueba");
		pais.setCodigoISO("PP");
		pais.setCodigoSistemaTributario(999);
		pais.setEstado(EstadoEntidad.ACTIVO);
		getManager().persist(pais);
		commit();
	}
	
	private void borrarEntidad(){
		pais = getManager().merge(pais);
		getManager().remove(pais);
		commit();
	}
	
	public void testCambiarEstadoEntidad() throws Exception {
		execute("CRUD.new");
		verificarValoresPorDefecto();
		inactivarEntidad();
		activarEntidad();
	}
	
	private void verificarValoresPorDefecto() throws Exception {
		assertValue("estado", obtenerEstadoEntidadActivo());
	}
	
	private void inactivarEntidad() throws Exception {
		cargarEntidadPrueba();
		execute("GeneralSistemaEmpresarialOX.inactivar");
		assertNoErrors();
		
		verificarEntidadInactivada();
	}
	
	private void verificarEntidadInactivada() throws Exception {
		cargarEntidadPrueba();
		assertValue("estado", obtenerEstadoEntidadInactivo());
	}
	
	private void activarEntidad() throws Exception {
		cargarEntidadPrueba();
		execute("GeneralSistemaEmpresarialOX.activar");
		assertNoErrors();
		
		verificarEntidadActivada();
	}
	
	private void verificarEntidadActivada() throws Exception {
		cargarEntidadPrueba();
		assertValue("estado", obtenerEstadoEntidadActivo());
	}
	
	private void cargarEntidadPrueba() throws Exception {
		setValue("id", String.valueOf(pais.getId()));
		execute("CRUD.refresh");
		assertNoErrors();
	}
	
	private String obtenerEstadoEntidadActivo(){
		return String.valueOf(EstadoEntidad.ACTIVO.ordinal());
	}
	
	private String obtenerEstadoEntidadInactivo(){
		return String.valueOf(EstadoEntidad.INACTIVO.ordinal());
	}
	
	@SuppressWarnings("rawtypes")
	public void testObtenerEstadoEntidad() throws Exception {
		long idEntidad = pais.getId();
		Class claseEntidad = Class.forName(NOMBRE_CALIFICADO_MODULO_DE_ENTIDAD_NO_ELIMINABLE);
		EstadoEntidad estadoActualEntidad = EntidadBaseNoEliminable.obtenerEstadoEntidad(claseEntidad, idEntidad);
		
		assertTrue(estadoActualEntidad.equals(EstadoEntidad.ACTIVO));
	}
}
