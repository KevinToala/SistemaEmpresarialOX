package com.sistemaempresarialox.tests.util;

import static com.sistemaempresarialox.util.EntidadUtil.*;

import com.sistemaempresarialox.modulos.comun.localizacion.modelo.*;

import junit.framework.*;

public class EntidadUtilTest extends TestCase {
	public void testNoEsEntidadEliminable() throws Exception {
		assertFalse(esEntidadEliminable(Pais.class));
	}
	
	public void testEsEntidadEliminable() throws Exception {
		// TODO Cuando exista una entidad Eliminable implementar el test
		//assertTrue(esEntidadEliminable(Pais.class));
	}
}
