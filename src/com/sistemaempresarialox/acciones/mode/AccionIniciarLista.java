package com.sistemaempresarialox.acciones.mode;

import org.openxava.actions.*;

import com.sistemaempresarialox.util.*;

public class AccionIniciarLista extends InitListAction {
	@Override
	public void execute() throws Exception {
		super.execute();
		eliminarAccionesDeBorrado();
		
		Class<?> claseEntidadAEliminar = EntidadUtil.obtenerClassDeEntidad(getView());
		
		if(EntidadUtil.esEntidadEliminable(claseEntidadAEliminar)){
			addActions("CRUD.deleteRow");
			addActions("CRUD.deleteSelected");
		}
		else {
			addActions("GeneralSistemaEmpresarialOX.actualizarEstadoFila");
			addActions("GeneralSistemaEmpresarialOX.actualizarEstadoSeleccionados");
		}
	}
	
	private void eliminarAccionesDeBorrado(){
		removeActions("CRUD.deleteRow");
		removeActions("CRUD.deleteSelected");
		removeActions("GeneralSistemaEmpresarialOX.actualizarEstadoFila");
		removeActions("GeneralSistemaEmpresarialOX.actualizarEstadoSeleccionados");
	}
}
