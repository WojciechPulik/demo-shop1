package pl.wpulik.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity(name = "categories")
public class Category implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_category")
	private Long id;
	private String name;
	private String description;
	private Long overridingCategoryId;		
	@OneToMany(mappedBy = "category")
	private List<Picture> pictures = new ArrayList<>();
	@ManyToMany(mappedBy = "categories",fetch = FetchType.EAGER)//, cascade = CascadeType.PERSIST)
	@Fetch(FetchMode.SUBSELECT)
	private List<Shipment> shipments  = new ArrayList<>();
	
	public Category() {}

	public Category(String name, String description, Long overridingCategoryId) {		
		this.name = name;
		this.description = description;
		this.overridingCategoryId = overridingCategoryId;
	}
	
	public void addShipments(Shipment shipment) {
		this.shipments.add(shipment);
		shipment.getCategories().add(this);
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

	public Long getOverridingCategoryId() {
		return overridingCategoryId;
	}

	public void setOverridingCategoryId(Long overridingCategoryId) {
		this.overridingCategoryId = overridingCategoryId;
	}

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
