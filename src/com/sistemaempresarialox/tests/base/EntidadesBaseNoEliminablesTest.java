package com.sistemaempresarialox.tests.base;

import static org.openxava.jpa.XPersistence.commit;
import static org.openxava.jpa.XPersistence.getManager;

import org.openxava.tests.*;

import com.sistemaempresarialox.base.modelo.*;
import com.sistemaempresarialox.modulos.comun.localizacion.modelo.*;
import com.sistemaempresarialox.util.enumeradores.*;

public class EntidadesBaseNoEliminablesTest extends ModuleTestBase {
	private Pais pais1;
	private Pais pais2;
	private Pais pais3;
	
	private int filaPais1;
	private int filaPais2;
	private int filaPais3;
	
	private boolean columnaEstadoVisible;
	
	private static String MODULO_DE_ENTIDAD_NO_ELIMINABLE = "Pais";
	private static String NOMBRE_CALIFICADO_MODULO_DE_ENTIDAD_NO_ELIMINABLE =
			"com.sistemaempresarialox.modulos.comun.localizacion.modelo.Pais";
	
	public EntidadesBaseNoEliminablesTest(String nombrePrueba){
		super(nombrePrueba, "SistemaEmpresarialOX", MODULO_DE_ENTIDAD_NO_ELIMINABLE);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		crearEntidades();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		borrarEntidades();
	}
	
	private void crearEntidades(){
		pais1 = new Pais();
		pais1.setNombre("pais 1");
		pais1.setCodigoISO("P1");
		pais1.setCodigoSistemaTributario(001);
		pais1.setEstado(EstadoEntidad.ACTIVO);
		
		pais2 = new Pais();
		pais2.setNombre("pais 2");
		pais2.setCodigoISO("P2");
		pais2.setCodigoSistemaTributario(002);
		pais2.setEstado(EstadoEntidad.ACTIVO);
		
		pais3 = new Pais();
		pais3.setNombre("pais 3");
		pais3.setCodigoISO("P3");
		pais3.setCodigoSistemaTributario(003);
		pais3.setEstado(EstadoEntidad.ACTIVO);
		
		getManager().persist(pais1);
		getManager().persist(pais2);
		getManager().persist(pais3);
		
		commit();
	}
	
	private void borrarEntidades(){
		borrarEntidad(pais1, pais2, pais3);
		commit();
	}
	
	private void borrarEntidad(Object... entidades){
		for (Object entidad : entidades){
			Object entidadAsociada = getManager().merge(entidad);
			getManager().remove(entidadAsociada);
		}
	}
	
	public void testCambiarEstadoEntidadesEnModoLista() throws Exception {
		determinarSiColumnaEstadoEstaVisible();
		cambiarEstadoVariasEntidadesDeLista();
		cambiarEstadoDeUnaEntidadDeLista();
	}

	private void determinarSiColumnaEstadoEstaVisible() {
		try {
			assertValueInList(0, "estado", "ACTIVO");
			columnaEstadoVisible = true;
		}
		catch(Exception e){
			columnaEstadoVisible = false;
		}
	}
	
	private void cambiarEstadoVariasEntidadesDeLista() throws Exception {
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
			
			if(idEntidadDeFila == pais1.getId()){
				filaPais1 = fila;
			}
			else if(idEntidadDeFila == pais2.getId()){
				filaPais2 = fila;
			}
			else if(idEntidadDeFila == pais3.getId()){
				filaPais3 = fila;
			}
		}
	}
	
	private void seleccionarEntidadesACambiarEstado() throws Exception {
		checkRow(filaPais1);
		checkRow(filaPais3);
	}
	
	private void cambiarEstadoEntidadesSeleccionadas() throws Exception {
		execute("GeneralSistemaEmpresarialOX.deleteSelected");
		assertNoErrors();
	}
	
	private void verificarEstadoEntidadesSeleccionadas(String estado) throws Exception {
		verificarEstadoEntidad(pais1, filaPais1, estado);
		verificarEstadoEntidad(pais3, filaPais3, estado);
	}
	
	private void cambiarEstadoDeUnaEntidadDeLista() throws Exception {
		execute("List.filter");
		setearFilaDeEntidadesCreadasEnLista();
		
		verificarEstadoEntidad(pais2, filaPais2, "ACTIVO");
		
		cambiarEstadoEntidad("INACTIVO");
		cambiarEstadoEntidad("ACTIVO");
	}
		
	private void cambiarEstadoEntidad(String estado) throws Exception {
		checkRow(filaPais2);
		
		execute("GeneralSistemaEmpresarialOX.deleteRow");
		assertNoErrors();
		
		setearFilaDeEntidadesCreadasEnLista();
		
		verificarEstadoEntidad(pais2, filaPais2, estado);
	}
	
	@SuppressWarnings("rawtypes")
	private void verificarEstadoEntidad(EntidadBaseNoEliminable entidad, int filaEntidad, String estadoEsperado)
	throws Exception{
		if(columnaEstadoVisible){
			assertValueInList(filaEntidad, "estado", estadoEsperado);
		}
		else {
			long idEntidad = entidad.getId();
			Class claseEntidad = Class.forName(NOMBRE_CALIFICADO_MODULO_DE_ENTIDAD_NO_ELIMINABLE);
			EstadoEntidad estadoActualEntidad = EntidadBaseNoEliminable.obtenerEstadoEntidad(claseEntidad, idEntidad);
			
			assertTrue(estadoActualEntidad.name().equalsIgnoreCase(estadoEsperado));
		}
	}
}
