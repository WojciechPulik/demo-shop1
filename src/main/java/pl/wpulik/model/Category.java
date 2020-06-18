package pl.wpulik.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "categories")
public class Category implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_category")
	private Long id;
	private String name;
	private String description;
	//@ManyToOne
	//private List<Category> subcategories; // - to w ogóle ma byc relacja?
	
	@OneToMany(mappedBy = "category")
	private List<Picture> pictures;
	@OneToMany(mappedBy = "category")
	private List<Shipment> shipments;
	
	public Category() {}

	public Category(String name, String description, List<Picture> pictures, List<Shipment> shipments) {
		
		this.name = name;
		this.description = description;
		this.pictures = pictures;
		this.shipments = shipments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
/*
 * 
 * Jszcze nie wiem co z tym zrobić.
 * 
	public List<Category> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(List<Category> subcategories) {
		this.subcategories = subcategories;
	}
*/
	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

	public List<Shipment> getShipments() {
		return shipments;
	}

	public void setShipments(List<Shipment> shipments) {
		this.shipments = shipments;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", description=" + description /* + ", subcategories="
				+ subcategories */ + ", pictures=" + pictures + ", shipments=" + shipments + "]";
	}
	
	
	
}
