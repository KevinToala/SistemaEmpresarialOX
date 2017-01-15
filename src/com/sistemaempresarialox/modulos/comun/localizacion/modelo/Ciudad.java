package com.sistemaempresarialox.modulos.comun.localizacion.modelo;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.*;
import org.openxava.annotations.*;

import com.sistemaempresarialox.acciones.ciudad.*;
import com.sistemaempresarialox.base.modelo.*;

@Entity
@Table(schema="localizacion",
	   name="ciudad",
	   uniqueConstraints={@UniqueConstraint(columnNames={"id_provincia", "nombre"}),
						  @UniqueConstraint(columnNames={"id_provincia", "codigo_sistema_tributario"})}
)
@View(members=EntidadBase.NOMBRE_COLUMNA_PRIMARY_KEY + ";" +
			  "nombre;" +
			  "codigoSistemaTributario;" +
			  "pais;" +
			  "provincia;" +
			  EntidadBaseNoEliminable.NOMBRE_COLUMNA_ESTADO
)
@Tab(properties=EntidadBase.NOMBRE_COLUMNA_PRIMARY_KEY + 
				", nombre, codigoSistemaTributario, provincia.nombre, " +
				EntidadBaseNoEliminable.NOMBRE_COLUMNA_ESTADO
)
public class Ciudad extends EntidadBaseNoEliminable {
	private Pais pais;
	private Provincia provincia;
	private String nombre;
	private int codigoSistemaTributario;
	
	@Transient
	@ManyToOne
	@DescriptionsList(condition=EntidadBaseNoEliminable.CONDICION_ESTADO_ACTIVO)
	@OnChange(AccionAlCambiarPaisEnEntidadCiudad.class)
	public Pais getPais(){
		if(pais == null){
			pais = getProvincia().getPais();
		}
		
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	@Required
	@ManyToOne
	@JoinColumn(name="id_provincia", nullable=false)
	@DescriptionsList(depends="this.pais",
					  condition="${pais.id} = ? and " + EntidadBaseNoEliminable.CONDICION_ESTADO_ACTIVO)
	public Provincia getProvincia(){
		return provincia;
	}
	
	public void setProvincia(Provincia provincia){
		this.provincia = provincia;
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
}
