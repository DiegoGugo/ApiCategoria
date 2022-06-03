package com.ipn.mx.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="producto")
public class Producto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idProducto")
	private Long idProducto;

	@NotEmpty(message="No puede estar vacio")
	@Size(min=4, max=50, message="Esta chiquito o muy grandote")
	@Column(name="nombreProducto")
	private String nombreProducto;

	@NotEmpty(message="No puede estar vacio")
	@Column(name="descripcionProducto")
	private String descripcionProducto;

	@NotNull(message="No puede estar vacio")
	@Column(name="precioProducto")
	private double precioProducto;

	@NotNull(message="No puede estar vacio")
	@Column(name="existencia")
	private int existencia;
	
	@Temporal(TemporalType.DATE)
	private Date fechaCreacion;
	
	@PrePersist
	public void prePersist() {
		this.fechaCreacion = new Date();
	}

	@JsonIgnore
	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="idCategoria")
	private Categoria idCategoria;
}
