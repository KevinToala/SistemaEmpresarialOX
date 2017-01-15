package com.sistemaempresarialox.acciones.general;

import java.util.*;

import org.openxava.actions.*;
import org.openxava.model.*;

import com.sistemaempresarialox.base.modelo.*;
import com.sistemaempresarialox.util.*;
import com.sistemaempresarialox.util.enumeradores.*;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class AccionActualizarEstadoEntidadNoEliminable extends ViewBaseAction {
	@Override
	public void execute() throws Exception {
		if (getView().getKeyValuesWithValue().isEmpty()){
			addError("no_delete_not_exists");
			return;
		}
		
		Class claseEntidadAEliminar = EntidadUtil.obtenerClassDeEntidad(getView());
		
		actualizarEstadoEntidad(claseEntidadAEliminar);
		restearCacheYLimpiarPantalla();
	}
	
	private void actualizarEstadoEntidad(Class claseEntidadAEliminar) throws Exception {
		long idEntidad = (long) getView().getValue(EntidadBase.NOMBRE_COLUMNA_PRIMARY_KEY);
		EstadoEntidad estadoEntidadActual = 
				EntidadBaseNoEliminable.obtenerEstadoEntidad(claseEntidadAEliminar, idEntidad);

		EstadoEntidad nuevoEstado = null;
		String idMensajeCambioEstado = "";
		
		if(estadoEntidadActual == EstadoEntidad.ACTIVO){
			nuevoEstado = EstadoEntidad.INACTIVO;
			idMensajeCambioEstado = "objetoInactivado";
		}
		else {
			nuevoEstado = EstadoEntidad.ACTIVO;
			idMensajeCambioEstado = "objetoActivado";
		}
		
		setearEstadoEntidad(nuevoEstado, idMensajeCambioEstado);
	}

	private void setearEstadoEntidad(EstadoEntidad nuevoEstado, String idMensajeCambioEstado)
	throws Exception {
		Map valores = new HashMap();
		valores.put(EntidadBaseNoEliminable.NOMBRE_COLUMNA_ESTADO, nuevoEstado);
		
		MapFacade.setValues(getModelName(), getView().getKeyValues(), valores);
		
		addMessage(idMensajeCambioEstado, getModelName());
	}

	private void restearCacheYLimpiarPantalla(){
		resetDescriptionsCache();
		getView().clear();
		getView().setEditable(false);
	}
}
