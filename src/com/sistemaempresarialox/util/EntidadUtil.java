package com.sistemaempresarialox.util;

import org.openxava.tab.*;
import org.openxava.view.*;

import com.sistemaempresarialox.base.modelo.*;

@SuppressWarnings("rawtypes")
public class EntidadUtil {
	
	public static boolean esEntidadEliminable(Class claseDeEntidadAEliminar)
	throws Exception {
		Object instanciaDeEntidadAEliminar = claseDeEntidadAEliminar.newInstance();
		return !(instanciaDeEntidadAEliminar instanceof EntidadBaseNoEliminable);
	}
	
	public static Class obtenerClassDeEntidad(View view){
		return view.getMetaModel().getPOJOClass();
	}
	
	public static Class obtenerClassDeEntidad(Tab tab){
		return tab.getMetaTab().getMetaComponent().getMetaEntity().getPOJOClass();
	}
}
