package com.sistemaempresarialox.modulos.comun.localizacion.modelo;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.*;
import org.openxava.annotations.*;

import com.sistemaempresarialox.base.modelo.*;

@Entity
@Table(schema="localizacion",
	   name="provincia",
	   uniqueConstraints={@UniqueConstraint(columnNames={"id_pais", "nombre"}),
						  @UniqueConstraint(columnNames={"id_pais", "codigo_sistema_tributario"})}
)
@Views({
	@View(members=EntidadBase.NOMBRE_COLUMNA_PRIMARY_KEY + ";" +
			  "nombre;" +
			  "codigoSistemaTributario;" +
			  "pais;" +
			  EntidadBaseNoEliminableConEstado.NOMBRE_COLUMNA_ESTADO
	),
	@View(name="IdPaisYNombreDeProvincia",
		  members="id;" +
		  		  "pais;" +
				  "nombre")
})
@Tab(properties=EntidadBase.NOMBRE_COLUMNA_PRIMARY_KEY + 
				", nombre, codigoSistemaTributario, pais.nombre, " +
				EntidadBaseNoEliminableConEstado.NOMBRE_COLUMNA_ESTADO
)
public class Provincia extends EntidadBaseNoEliminableConEstado {
	private Pais pais;
	private String nombre;
	private int codigoSistemaTributario;
	private Collection<Ciudad> ciudades;
	
	@Required
	@ManyToOne
	@JoinColumn(name="id_pais", nullable=false)
	@DescriptionsList(condition=CONDICION_ESTADO_ACTIVO)
	public Pais getPais(){
		return pais;
	}
	
	public void setPais(Pais pais){
		this.pais = pais;
	}
	
	@Required
	@NotBlank
	@Size(min=3, max=64)
	@Column(name="nombre", length=64, nullable=false)
	public String getNombre(){
		return nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	@NotNull
	@Required
	@Column(name="codigo_sistema_tributario", length=2, nullable=false)
	public int getCodigoSistemaTributario(){
		return codigoSistemaTributario;
	}
	
	public void setCodigoSistemaTributario(int codigoSistemaTributario){
		this.codigoSistemaTributario = codigoSistemaTributario;
	}
	
	@OneToMany(mappedBy="provincia")
	public Collection<Ciudad> getCiudades(){
		return ciudades;
	}
	
	public void setCiudades(Collection<Ciudad> ciudades){
		this.ciudades = ciudades;
	}
}
