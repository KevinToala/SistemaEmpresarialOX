package com.sistemaempresarialox.tests.modulos.comun.localizacion;

import static org.openxava.jpa.XPersistence.commit;
import static org.openxava.jpa.XPersistence.getManager;

import java.util.*;

import com.sistemaempresarialox.base.modelo.*;
import com.sistemaempresarialox.modulos.comun.localizacion.modelo.*;
import com.sistemaempresarialox.tests.base.*;
import com.sistemaempresarialox.util.*;
import com.sistemaempresarialox.util.enumeradores.*;

public class CiudadTest extends EntidadBaseNoEliminableConEstadoModuleTestBase {
	private List<Pais> paises = new ArrayList<>();
	private List<Provincia> provincias = new ArrayList<>();
	
	
	public CiudadTest(String nombrePrueba){
		super(nombrePrueba, "Ciudad");
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		borrarProvinciasDePrueba();
		borrarPaisDePrueba();
	}
	
	@Override
	protected String obtenerNombreCalificadoModulo(){
		return "com.sistemaempresarialox.modulos.comun.localizacion.modelo.Ciudad";
	}
	
	@Override
	protected List<EntidadBaseNoEliminableConEstado> crearEntidadesBaseNoEliminable(){
		crearPaisesDePrueba();
		crearProvinciasDePrueba();
		
		List<EntidadBaseNoEliminableConEstado> ciudades = new ArrayList<>();
		
		Ciudad ciudad1 = new Ciudad();
		ciudad1.setNombre("ciudad 1");
		ciudad1.setCodigoSistemaTributario(01);
		ciudad1.setEstado(EstadoEntidad.ACTIVO);
		ciudad1.setProvincia(provincias.get(0));
		
		Ciudad ciudad2 = new Ciudad();
		ciudad2.setNombre("ciudad 2");
		ciudad2.setCodigoSistemaTributario(02);
		ciudad2.setEstado(EstadoEntidad.ACTIVO);
		ciudad2.setProvincia(provincias.get(1));
		
		Ciudad ciudad3 = new Ciudad();
		ciudad3.setNombre("ciudad 3");
		ciudad3.setCodigoSistemaTributario(03);
		ciudad3.setEstado(EstadoEntidad.ACTIVO);
		ciudad3.setProvincia(provincias.get(0));
		
		ciudades.add(ciudad1);
		ciudades.add(ciudad2);
		ciudades.add(ciudad3);
		
		return ciudades;
	}
	
	private void crearPaisesDePrueba(){
		Pais pais1 = new Pais();
		pais1.setNombre("pais 1");
		pais1.setCodigoISO("p1");
		pais1.setCodigoSistemaTributario(001);
		pais1.setEstado(EstadoEntidad.ACTIVO);
		
		Pais pais2 = new Pais();
		pais2.setNombre("pais 2");
		pais2.setCodigoISO("p2");
		pais2.setCodigoSistemaTributario(002);
		pais2.setEstado(EstadoEntidad.ACTIVO);
		
		getManager().persist(pais1);
		getManager().persist(pais2);
		
		paises.add(pais1);
		paises.add(pais2);
	}
	
	private void borrarPaisDePrueba(){
		EntidadUtil.borrarEntidadesDesasociadas(paises.get(0),
												paises.get(1));
		commit();
	}
	
	private void crearProvinciasDePrueba(){
		Provincia provincia1 = new Provincia();
		provincia1.setNombre("provincia 1");
		provincia1.setCodigoSistemaTributario(001);
		provincia1.setPais(paises.get(0));
		provincia1.setEstado(EstadoEntidad.ACTIVO);
		
		Provincia provincia2 = new Provincia();
		provincia2.setNombre("provincia 2");
		provincia2.setCodigoSistemaTributario(002);
		provincia2.setPais(paises.get(1));
		provincia2.setEstado(EstadoEntidad.ACTIVO);
		
		getManager().persist(provincia1);
		getManager().persist(provincia2);
		
		provincias.add(provincia1);
		provincias.add(provincia2);
	}
	
	private void borrarProvinciasDePrueba(){
		EntidadUtil.borrarEntidadesDesasociadas(provincias.get(0),
												provincias.get(1));
		commit();
	}
	
	public void testCrearCiudad() throws Exception {
		execute("GeneralSistemaEmpresarialOX.new");
		setValue("nombre", "ciudad prueba");
		setValue("codigoSistemaTributario", "99");
		setValue("provincia.id", String.valueOf(provincias.get(0).getId()));
		execute("CRUD.save");
		assertNoErrors();
		
		borrarEntidadBaseNoEliminableConEstadoCreadaEnPantalla(
				"nombre", "ciudad prueba", Ciudad.class);
	}
	
	// TODO crear test cambiar provincia de ciudad
}
