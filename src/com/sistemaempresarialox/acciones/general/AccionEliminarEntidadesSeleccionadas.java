package com.sistemaempresarialox.acciones.general;

import java.util.*;

import org.openxava.actions.*;
import org.openxava.model.*;
import org.openxava.model.meta.*;
import org.openxava.tab.*;
import org.openxava.validators.*;

import com.sistemaempresarialox.base.modelo.*;
import com.sistemaempresarialox.util.*;
import com.sistemaempresarialox.util.enumeradores.*;

@SuppressWarnings("rawtypes")
public class AccionEliminarEntidadesSeleccionadas extends TabBaseAction implements IChainAction {
	private static final int INDICE_COLUMNA_ESTADO_NO_ENCONTRADA = -1;
	
	private String siguienteAccion = null;
	private Class claseEntidadAEliminar;
	private Tab tab;
	
	@Override
	public void execute() throws Exception {
		tab = getTab();
		claseEntidadAEliminar = getView().getMetaModel().getPOJOClass();
		
		if (EntidadUtil.esEntidadEliminable(claseEntidadAEliminar)){
			siguienteAccion = "CRUD.deleteSelected";
			/* TODO Al dar clic en la accion de borrar un registro este no se borra
			 * 		si el registro no esta marcado con el check, esto solo sucede cuando se utiliza IChainAction
			 */
		}
		else {
			actualizarEstadoDeEntidadesSeleccionadas();
		}
	}
	
	@Override
	public String getNextAction() throws Exception {
		return siguienteAccion;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	private void actualizarEstadoDeEntidadesSeleccionadas() throws Exception {
		int indiceColumnaEstado = obtenerIndiceColumnaEstado();
		
		for(int fila : getSelected()){
			Map valores = new HashMap();
			Map idEntidadDeFila =  (Map) tab.getTableModel().getObjectAt(fila);
			EstadoEntidad actualEstadoEntidad = obtenerActualEstadoEntidad(fila, indiceColumnaEstado, idEntidadDeFila);
			EstadoEntidad nuevoEstadoEntidad = obtenerNuevoEstadoEntidad(actualEstadoEntidad);
			
			valores.put(EntidadBaseNoEliminable.NOMBRE_COLUMNA_ESTADO, nuevoEstadoEntidad);
			
			try {
				MapFacade.setValues(tab.getModelName(), idEntidadDeFila, valores);
				mostrarMensajeActualizacionEstadoExitosa(tab.getModelName(), idEntidadDeFila, nuevoEstadoEntidad);
			}
			catch (ValidationException validationException) {
				addError("no_delete_row", fila + 1, idEntidadDeFila);
				addErrors(validationException.getErrors());
			}
			catch (Exception exception) {
				addError("no_delete_row", fila + 1, idEntidadDeFila);
			}
		}
		
		tab.deselectAll();
		resetDescriptionsCache();
	}
	
	private int obtenerIndiceColumnaEstado() {
		int indiceColumnaEstado = INDICE_COLUMNA_ESTADO_NO_ENCONTRADA;
		List<MetaProperty> metaPropiedades = tab.getMetaProperties();
		
		for(int indice = 0 ; indice < metaPropiedades.size() ; indice++){
			if(metaPropiedades.get(indice).getName().equals(EntidadBaseNoEliminable.NOMBRE_COLUMNA_ESTADO)){
				indiceColumnaEstado = indice;
			}
		}
		
		return indiceColumnaEstado;
	}
	
	private EstadoEntidad obtenerActualEstadoEntidad(int fila, int indiceColumnaEstado, Map mapIdEntidad)
	throws Exception {
		EstadoEntidad actualEstadoEntidad = null;
		
		if(indiceColumnaEstado != INDICE_COLUMNA_ESTADO_NO_ENCONTRADA){
			actualEstadoEntidad = (EstadoEntidad) tab.getTableModel().getValueAt(fila, indiceColumnaEstado);
		}
		else {
			long idEntidad = obtenerIdEntidad(mapIdEntidad);
			actualEstadoEntidad = EntidadBaseNoEliminable.obtenerEstadoEntidad(claseEntidadAEliminar, idEntidad);
		}
		
		return actualEstadoEntidad;
	}
	
	private EstadoEntidad obtenerNuevoEstadoEntidad(EstadoEntidad actualEstadoEntidad){
		EstadoEntidad nuevoEstadoEntidad = EstadoEntidad.ACTIVO;
		
		if(actualEstadoEntidad == EstadoEntidad.ACTIVO){
			nuevoEstadoEntidad = EstadoEntidad.INACTIVO;
		}

		return nuevoEstadoEntidad;
	}
	
	private void mostrarMensajeActualizacionEstadoExitosa(
			String modelName, Map idEntidadDeFila, EstadoEntidad nuevoEstadoEntidad)
	{
		String idMensaje = "objetoConIdInactivado";
		long idEntidad = obtenerIdEntidad(idEntidadDeFila);
		
		if(nuevoEstadoEntidad == EstadoEntidad.ACTIVO){
			idMensaje = "objetoConIdActivado";
		}
		
		addMessage(idMensaje, modelName, idEntidad);
	}
	
	private long obtenerIdEntidad(Map mapIdEntidad){
		return (Long) mapIdEntidad.values().toArray()[0];
	}
}
