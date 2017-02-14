package com.sistemaempresarialox.tests.modulos.comun.localizacion;

import java.util.*;

import com.sistemaempresarialox.base.modelo.*;
import com.sistemaempresarialox.modulos.comun.localizacion.modelo.*;
import com.sistemaempresarialox.tests.base.*;
import com.sistemaempresarialox.util.enumeradores.*;

public class PaisTest extends EntidadBaseNoEliminableConEstadoModuleTestBase {
	public PaisTest(String nombrePrueba){
		super(nombrePrueba, "Pais");
	}
	
	@Override
	protected String obtenerNombreCalificadoModulo(){
		return "com.sistemaempresarialox.modulos.comun.localizacion.modelo.Pais";
	}
	
	@Override
	protected List<EntidadBaseNoEliminableConEstado> crearEntidadesBaseNoEliminable(){
		List<EntidadBaseNoEliminableConEstado> paises = new ArrayList<>();
		
		Pais pais1 = new Pais();
		pais1.setNombre("pais1");
		pais1.setCodigoISO("p1");
		pais1.setCodigoSistemaTributario(001);
		pais1.setEstado(EstadoEntidad.ACTIVO);
		
		Pais pais2 = new Pais();
		pais2.setNombre("pais2");
		pais2.setCodigoISO("p2");
		pais2.setCodigoSistemaTributario(002);
		pais2.setEstado(EstadoEntidad.ACTIVO);
		
		Pais pais3 = new Pais();
		pais3.setNombre("pais3");
		pais3.setCodigoISO("p3");
		pais3.setCodigoSistemaTributario(003);
		pais3.setEstado(EstadoEntidad.ACTIVO);
		
		paises.add(pais1);
		paises.add(pais2);
		paises.add(pais3);
		
		return paises;
	}
	
	public void testCrearPais() throws Exception {
		execute("GeneralSistemaEmpresarialOX.new");
		setValue("nombre", "pais de prueba");
		setValue("codigoISO", "pp");
		setValue("codigoSistemaTributario", "999");
		execute("CRUD.save");
		assertNoErrors();
		
		borrarEntidadBaseNoEliminableConEstadoCreadaEnPantalla(
				"nombre", "pais de prueba", Pais.class);
	}
}
