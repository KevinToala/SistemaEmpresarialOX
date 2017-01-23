package com.sistemaempresarialox.modulos.comun.localizacion.modelo;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.*;
import org.openxava.annotations.*;

import com.sistemaempresarialox.base.modelo.*;

@Entity
@Table(schema="localizacion",
	   name="pais",
	   uniqueConstraints={@UniqueConstraint(columnNames="nombre"),
						  @UniqueConstraint(columnNames="codigo_iso"),
						  @UniqueConstraint(columnNames="codigo_sistema_tributario")
						 }
)
@View(members=EntidadBase.NOMBRE_COLUMNA_PRIMARY_KEY + ";" +
			  "nombre;" +
			  "codigoISO, codigoSistemaTributario;" +
			  EntidadBaseNoEliminableConEstado.NOMBRE_COLUMNA_ESTADO
)
@Tab(properties=EntidadBase.NOMBRE_COLUMNA_PRIMARY_KEY + 
				", nombre, codigoISO, codigoSistemaTributario, " +
				EntidadBaseNoEliminableConEstado.NOMBRE_COLUMNA_ESTADO
)
public class Pais extends EntidadBaseNoEliminableConEstado {
	private String nombre;
	private String codigoISO;
	private int codigoSistemaTributario;
	private Collection<Provincia> provincias;
	
	@NotBlank
	@Required
	@Size(min=3, max=64)
	@Column(name="nombre", length=64, nullable=false)
	public String getNombre(){
		return nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	@NotBlank
	@Required
	@Size(min=2, max=2)
	@Column(name="codigo_iso", length=2, nullable=false)
	public String getCodigoISO(){
		return codigoISO;
	}
	
	public void setCodigoISO(String codigoISO){
		this.codigoISO = codigoISO;
	}
	
	@NotNull
	@Required
	@Column(name="codigo_sistema_tributario", length=3, nullable=false)
	public int getCodigoSistemaTributario(){
		return codigoSistemaTributario;
	}
	
	public void setCodigoSistemaTributario(int codigoSistemaTributario){
		this.codigoSistemaTributario = codigoSistemaTributario;
	}
	
	@OneToMany(mappedBy="pais")
	public Collection<Provincia> getProvincias(){
		return provincias;
	}
	
	public void setProvincias(Collection<Provincia> provincias){
		this.provincias = provincias;
	}
}
