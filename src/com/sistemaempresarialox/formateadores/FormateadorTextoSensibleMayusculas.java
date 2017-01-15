package com.sistemaempresarialox.formateadores;

import javax.servlet.http.*;

import org.openxava.formatters.*;

public class FormateadorTextoSensibleMayusculas implements IFormatter {
	
	@Override
	public String format(HttpServletRequest request, Object string) throws Exception {
		return string == null ? "" : string.toString();
	}
	
	@Override
	public Object parse(HttpServletRequest request, String string) throws Exception {
		return string == null ? "" : string;
	}
}
