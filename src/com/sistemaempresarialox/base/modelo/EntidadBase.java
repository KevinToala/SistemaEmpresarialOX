package com.sistemaempresarialox.base.modelo;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.openxava.annotations.*;

@MappedSuperclass
public abstract class EntidadBase {
	public final static String NOMBRE_COLUMNA_PRIMARY_KEY = "id";
	
	private long id;
	
	@Id
	@ReadOnly
	@NotNull
	@Column(name=NOMBRE_COLUMNA_PRIMARY_KEY, nullable=false)
	@TableGenerator(name="generadorTablasSistemaEmpresarialOX", 
					schema="general", table="secuencias_id_tablas",
					pkColumnName="tabla", valueColumnName="secuencia_id",
					uniqueConstraints=@UniqueConstraint(columnNames={"tabla"}),
					initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generadorTablasSistemaEmpresarialOX")
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
}
