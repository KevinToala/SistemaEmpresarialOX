package com.sistemaempresarialox.acciones.general;

import org.openxava.actions.*;

import com.sistemaempresarialox.base.modelo.*;

import com.sistemaempresarialox.util.*;
import com.sistemaempresarialox.util.enumeradores.*;

public class AccionBuscarMostrandoAccionBorrado extends SearchByViewKeyAction {
	
	@Override
	public void execute() throws Exception {
		super.execute();
		eliminarAccionesDeBorrado();
		mostrarAcccionCorrespondienteDeBorrado();
	}
	
	@SuppressWarnings("rawtypes")
	private void mostrarAcccionCorrespondienteDeBorrado() throws Exception {
		Class claseEntidadAEliminar = EntidadUtil.obtenerClassDeEntidad(getView());
		
		if(EntidadUtil.esEntidadEliminable(claseEntidadAEliminar)){
			addActions("CRUD.delete");
		}
		else {
			EstadoEntidad estadoEntidad = (EstadoEntidad) getView().getValue(EntidadBaseNoEliminable.NOMBRE_COLUMNA_ESTADO);
			
			if(estadoEntidad == EstadoEntidad.ACTIVO){
				addActions("GeneralSistemaEmpresarialOX.inactivar");
			}
			else {
				addActions("GeneralSistemaEmpresarialOX.activar");
			}
		}
	}

	private void eliminarAccionesDeBorrado(){
		removeActions("CRUD.delete");
		removeActions("GeneralSistemaEmpresarialOX.activar");
		removeActions("GeneralSistemaEmpresarialOX.inactivar");
	}
}
