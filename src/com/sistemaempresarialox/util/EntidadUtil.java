package com.sistemaempresarialox.util;

import static org.openxava.jpa.XPersistence.getManager;

import org.openxava.tab.*;
import org.openxava.view.*;

import com.sistemaempresarialox.base.modelo.*;

public class EntidadUtil {
	
	public static boolean esEntidadEliminable(Class<?> claseDeEntidadAEliminar)
	throws Exception {
		Object instanciaDeEntidadAEliminar = claseDeEntidadAEliminar.newInstance();
		return !(instanciaDeEntidadAEliminar instanceof EntidadBaseNoEliminableConEstado);
	}
	
	public static Class<?> obtenerClassDeEntidad(View view){
		return view.getMetaModel().getPOJOClass();
	}
	
	public static Class<?> obtenerClassDeEntidad(Tab tab){
		return tab.getMetaTab().getMetaComponent().getMetaEntity().getPOJOClass();
	}
	
	public static void borrarEntidadesDesasociadas(Object... entidades){
		for (Object entidad : entidades){
			Object entidadAsociada = getManager().merge(entidad);
			getManager().remove(entidadAsociada);
		}
	}
}
