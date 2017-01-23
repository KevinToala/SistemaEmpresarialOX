package com.sistemaempresarialox.base.modelo;

import javax.persistence.*;
import javax.persistence.criteria.*;

import org.openxava.annotations.*;
import org.openxava.jpa.*;

import com.sistemaempresarialox.calculadores.*;
import com.sistemaempresarialox.util.enumeradores.*;

@MappedSuperclass
public abstract class EntidadBaseNoEliminableConEstado extends EntidadBase {
	public final static String NOMBRE_COLUMNA_ESTADO = "estado";
	public final static String CONDICION_ESTADO_ACTIVO =
			"${" + NOMBRE_COLUMNA_ESTADO + "} = '" + EstadoEntidad.STRING_ACTIVO + "'";
	
	private EstadoEntidad estado;
	
	@Required
	@Enumerated(EnumType.STRING)
	@Column(name=NOMBRE_COLUMNA_ESTADO, nullable=false)
	@DefaultValueCalculator(CalculadorValorPorDefectoEstadoEntidad.class)
	public EstadoEntidad getEstado(){
		return estado;
	}
	
	public void setEstado(EstadoEntidad estado){
		this.estado = estado;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static EstadoEntidad obtenerEstadoEntidad(
			Class claseEntidadBaseNoEliminable, long id)
	{
		CriteriaBuilder criteriaBuilder = XPersistence.getManager().getCriteriaBuilder();
		CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(claseEntidadBaseNoEliminable);
		
		Root root = criteriaQuery.from(claseEntidadBaseNoEliminable);
		Predicate whereIdEntidad = criteriaBuilder.equal(root.get(NOMBRE_COLUMNA_PRIMARY_KEY), id);
		criteriaQuery.select(root.get(NOMBRE_COLUMNA_ESTADO))
					 .where(whereIdEntidad);
		
		TypedQuery typedQuery = XPersistence.getManager().createQuery(criteriaQuery);
		EstadoEntidad estadoEntidad = (EstadoEntidad) typedQuery.getSingleResult();
		return estadoEntidad;
	}
}
