package com.ipn.mx.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="categoria")
public class Categoria implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idCategoria", nullable = false)
	private Long idCategoria;
	
	@NotEmpty(message="No puede estar vacio")
	@Size(min=4, max=50, message="Esta chiquito o muy grandote")
	@Column(name="nombreCategoria", length=50, nullable=false)	
	private String nombreCategoria;
	
	@NotEmpty(message="No puede estar vacio")
	@Size(min=4, max=50, message="Esta chiquito o muy grandote")
	@NotBlank(message="Esta en blanco, aunque es lo mismo que vacio")
	@Column(name="descripcionCategoria", length=100, nullable=false)		
	private String descripcionCategoria;

	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "idCategoria", cascade = CascadeType.REFRESH)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private List<Producto> productos;

	public Categoria() {
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public String getDescripcionCategoria() {
		return descripcionCategoria;
	}

	public void setDescripcionCategoria(String descripcionCategoria) {
		this.descripcionCategoria = descripcionCategoria;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
}
