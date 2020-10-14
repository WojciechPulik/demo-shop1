package pl.wpulik.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	@OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
	private List<Picture> pictures = new ArrayList<>();
	@ManyToMany(mappedBy = "categories",fetch = FetchType.EAGER)//, cascade = CascadeType.PERSIST)
	@Fetch(FetchMode.SUBSELECT)
	private List<Shipment> shipments  = new ArrayList<>();
	@ManyToMany(mappedBy = "categories")
	private List<Product> products = new ArrayList<>();
	
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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", description=" + description /* + ", subcategories="
				+ subcategories */ + ", pictures=" + pictures + ", shipments=" + shipments + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
}
