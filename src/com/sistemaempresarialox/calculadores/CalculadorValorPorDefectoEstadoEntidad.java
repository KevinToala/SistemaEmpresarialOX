package com.sistemaempresarialox.calculadores;

import org.openxava.calculators.*;

import com.sistemaempresarialox.util.enumeradores.*;

@SuppressWarnings("serial")
public class CalculadorValorPorDefectoEstadoEntidad implements ICalculator {
	@Override
	public Object calculate() throws Exception {
		return EstadoEntidad.ACTIVO;
	}
}
