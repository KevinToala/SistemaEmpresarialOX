package com.sistemaempresarialox.acciones.entidadnoeliminable;

import org.openxava.actions.*;

import com.sistemaempresarialox.base.modelo.*;
import com.sistemaempresarialox.util.*;

public class AccionBusquedadDeReferenciasDeEntidadesSoloActivas extends ReferenceSearchAction {
	@Override
	@SuppressWarnings("rawtypes")
	public void execute() throws Exception {
		super.execute();
		
		Class claseEntidad = EntidadUtil.obtenerClassDeEntidad(getTab());
		
		if(!EntidadUtil.esEntidadEliminable(claseEntidad)){
			getTab().setBaseCondition(EntidadBaseNoEliminable.CONDICION_ESTADO_ACTIVO);
		}
	}
}
