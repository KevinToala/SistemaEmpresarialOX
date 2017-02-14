package com.sistemaempresarialox.tests.base;

import org.openxava.tests.*;
import static org.openxava.jpa.XPersistence.*;

import java.util.*;

import org.openxava.jpa.*;

import com.sistemaempresarialox.base.modelo.*;
import com.sistemaempresarialox.util.*;
import com.sistemaempresarialox.util.enumeradores.*;

public abstract class EntidadBaseNoEliminableConEstadoModuleTestBase extends ModuleTestBase {
	private EntidadBaseNoEliminableConEstado entidad1;
	private EntidadBaseNoEliminableConEstado entidad2;
	private EntidadBaseNoEliminableConEstado entidad3;
	
	private int filaEntidad1;
	private int filaEntidad2;
	private int filaEntidad3;
	
	private boolean columnaEstadoVisible;
	
	public EntidadBaseNoEliminableConEstadoModuleTestBase(String nombrePrueba, String modulo){
		super(nombrePrueba, "SistemaEmpresarialOX", modulo);
	}
	
	protected abstract String obtenerNombreCalificadoModulo();
	
	protected abstract List<EntidadBaseNoEliminableConEstado> crearEntidadesBaseNoEliminable();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		List<EntidadBaseNoEliminableConEstado> entidades = crearEntidadesBaseNoEliminable();
		
		if(entidades.isEmpty() || entidades.size() < 3){
			throw new Exception("Lista de entidades incompletas, para poder realizar la prueba");
		}
		
		entidad1 = entidades.get(0);
		entidad2 = entidades.get(1);
		entidad3 = entidades.get(2);
		
		getManager().persist(entidad1);
		getManager().persist(entidad2);
		getManager().persist(entidad3);
		
		commit();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		EntidadUtil.borrarEntidadesDesasociadas(entidad1, entidad2, entidad3);
		commit();
	}
	
	public void testInactivarActivarEntidadEnModoDetalle() throws Exception {
		execute("GeneralSistemaEmpresarialOX.new");
		verificarValoresPorDefecto();
		inactivarEntidad();
		activarEntidad();
	}
	
	private void verificarValoresPorDefecto() throws Exception {
		assertValue("estado", obtenerEstadoEntidadActivo());
	}
	
	private void inactivarEntidad() throws Exception {
		cargarEntidadModoDetalle();
		execute("GeneralSistemaEmpresarialOX.inactivar");
		assertNoErrors();
		
		verificarEntidadInactivada();
	}
	
	private void verificarEntidadInactivada() throws Exception {
		cargarEntidadModoDetalle();
		assertValue("estado", obtenerEstadoEntidadInactivo());
	}
	
	private void activarEntidad() throws Exception {
		cargarEntidadModoDetalle();
		execute("GeneralSistemaEmpresarialOX.activar");
		assertNoErrors();
		
		verificarEntidadActivada();
	}
	
	private void verificarEntidadActivada() throws Exception {
		cargarEntidadModoDetalle();
		assertValue("estado", obtenerEstadoEntidadActivo());
	}
	
	private void cargarEntidadModoDetalle() throws Exception {
		setValue("id", String.valueOf(entidad1.getId()));
		execute("CRUD.refresh");
		assertNoErrors();
	}
	
	private String obtenerEstadoEntidadActivo(){
		return String.valueOf(EstadoEntidad.ACTIVO.ordinal());
	}
	
	private String obtenerEstadoEntidadInactivo(){
		return String.valueOf(EstadoEntidad.INACTIVO.ordinal());
	}
	
	public void testObtenerEstadoEntidad() throws Exception {
		long idEntidad = entidad1.getId();
		Class<?> claseEntidad = Class.forName(obtenerNombreCalificadoModulo());
		EstadoEntidad estadoActualEntidad = EntidadBaseNoEliminableConEstado.obtenerEstadoEntidad(claseEntidad, idEntidad);
		
		assertTrue(estadoActualEntidad.equals(EstadoEntidad.ACTIVO));
	}
	
	public void testInactivarActivarEntidadesEnModoLista() throws Exception {
		execute("GeneralSistemaEmpresarialOX.new");
		execute("Mode.list");
		
		determinarSiColumnaEstadoEstaVisibleEnModoLista();
		
		cambiarEstadoVariasEntidadesEnModoLista();
		cambiarEstadoDeUnaEntidadEnModoLista();
	}

	private void determinarSiColumnaEstadoEstaVisibleEnModoLista() throws Exception {
		setearFilaDeEntidadesCreadasEnLista();
		
		try {
			assertValueInList(filaEntidad1, "estado", "ACTIVO");
			columnaEstadoVisible = true;
		}
		catch(Exception e){
			columnaEstadoVisible = false;
		}
	}
	
	private void cambiarEstadoVariasEntidadesEnModoLista() throws Exception {
		execute("List.filter");
		setearFilaDeEntidadesCreadasEnLista();
		
		cambiarEstadoVariasEntidades("INACTIVO");
		cambiarEstadoVariasEntidades("ACTIVO");
	}
	
	private void cambiarEstadoVariasEntidades(String estado) throws Exception {
		seleccionarEntidadesACambiarEstado();
		cambiarEstadoEntidadesSeleccionadas();
		setearFilaDeEntidadesCreadasEnLista();
		verificarEstadoEntidadesSeleccionadas(estado);
	}
	
	private void setearFilaDeEntidadesCreadasEnLista() throws Exception {
		int filas = getListRowCount();
		
		for(int fila = 0 ; fila < filas ; fila++){
			long idEntidadDeFila = Long.parseLong(getValueInList(fila, "id"));
			
			if(idEntidadDeFila == entidad1.getId()){
				filaEntidad1 = fila;
			}
			else if(idEntidadDeFila == entidad2.getId()){
				filaEntidad2 = fila;
			}
			else if(idEntidadDeFila == entidad3.getId()){
				filaEntidad3 = fila;
			}
		}
	}
	
	private void seleccionarEntidadesACambiarEstado() throws Exception {
		checkRow(filaEntidad1);
		checkRow(filaEntidad3);
	}
	
	private void cambiarEstadoEntidadesSeleccionadas() throws Exception {
		execute("GeneralSistemaEmpresarialOX.actualizarEstadoSeleccionados");
		assertNoErrors();
	}
	
	private void verificarEstadoEntidadesSeleccionadas(String estado) throws Exception {
		verificarEstadoEntidad(entidad1, filaEntidad1, estado);
		verificarEstadoEntidad(entidad3, filaEntidad3, estado);
	}
	
	private void cambiarEstadoDeUnaEntidadEnModoLista() throws Exception {
		execute("List.filter");
		setearFilaDeEntidadesCreadasEnLista();
		
		verificarEstadoEntidad(entidad2, filaEntidad2, "ACTIVO");
		
		cambiarEstadoEntidad("INACTIVO");
		cambiarEstadoEntidad("ACTIVO");
	}
		
	private void cambiarEstadoEntidad(String estado) throws Exception {
		checkRow(filaEntidad2);
		
		execute("GeneralSistemaEmpresarialOX.actualizarEstadoFila");
		assertNoErrors();
		
		setearFilaDeEntidadesCreadasEnLista();
		
		verificarEstadoEntidad(entidad2, filaEntidad2, estado);
	}
	
	@SuppressWarnings("rawtypes")
	private void verificarEstadoEntidad(EntidadBaseNoEliminableConEstado entidad, int filaEntidad, String estadoEsperado)
	throws Exception{
		if(columnaEstadoVisible){
			assertValueInList(filaEntidad, "estado", estadoEsperado);
		}
		else {
			long idEntidad = entidad.getId();
			Class claseEntidad = Class.forName(obtenerNombreCalificadoModulo());
			EstadoEntidad estadoActualEntidad = EntidadBaseNoEliminableConEstado.obtenerEstadoEntidad(claseEntidad, idEntidad);
			
			assertTrue(estadoActualEntidad.name().equalsIgnoreCase(estadoEsperado));
		}
	}
	
	protected void borrarEntidadBaseNoEliminableConEstadoCreadaEnPantalla(
			String propiedad, String valorPropiedad, 
			Class<? extends EntidadBaseNoEliminableConEstado> claseEntidad)
	throws Exception {
		setValue(propiedad, valorPropiedad);
		execute("CRUD.refresh");
		
		assertNoErrors();
		
		long idEntidad = Long.parseLong(getValue("id"));
		
		Object entidad = XPersistence.getManager().find(claseEntidad, idEntidad);
		XPersistence.getManager().remove(entidad);
	}
}
