package com.ipn.mx.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

	@Column(name="nombreProducto")
	private String nombreProducto;
	
	@Column(name="descripcionProducto")
	private String descripcionProducto;
	
	@Column(name="precioProducto")
	private double precioProducto;
	
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
