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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@NoArgsConstructor
@Entity
@Table(name="categoria")
public class Categoria implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idCategoria", nullable = false)
	private long idCategoria;
	
	@NotEmpty(message="No puede estar vacio")
	@Size(min=4, max=50, message="Esta chiquito o muy grandote amigo, vamos por unas quekas")
	@Column(name="nombreCategoria", length=50, nullable=false)	
	private String nombreCategoria;
	
	@NotEmpty(message="No puede estar vacio")
	@Size(min=4, max=50, message="Esta chiquito o muy grandote amigo, vamos por unas quekas")
	@NotBlank(message="Esta en blanco, aunque es lo mismo que vacio")
	@Column(name="descripcionCategoria", length=100, nullable=false)		
	private String descripcionCategoria;

	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "idCategoria", cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private List<Producto> productos;
}
