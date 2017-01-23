package com.sistemaempresarialox.acciones.general;

import org.openxava.actions.*;

import com.sistemaempresarialox.util.*;

public class AccionCRUDNew extends NewAction {
	@Override
	public void execute() throws Exception {
		super.execute();
		eliminarAccionesDeBorrado();
		mostrarAcccionCorrespondienteDeBorrado();
	}
	
	private void mostrarAcccionCorrespondienteDeBorrado() throws Exception {
		Class<?> claseEntidadAEliminar = EntidadUtil.obtenerClassDeEntidad(getView());
		
		if(EntidadUtil.esEntidadEliminable(claseEntidadAEliminar)){
			addActions("CRUD.delete");
		}
		else {
			addActions("GeneralSistemaEmpresarialOX.activar");
		}
	}
	
	private void eliminarAccionesDeBorrado(){
		removeActions("CRUD.delete");
		removeActions("GeneralSistemaEmpresarialOX.activar");
		removeActions("GeneralSistemaEmpresarialOX.inactivar");
	}
}
