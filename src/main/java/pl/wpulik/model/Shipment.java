package pl.wpulik.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity(name = "shipments")
public class Shipment implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_shipment")
	private Long id;
	private String name;
	private String description;
	private Double maxWeight;
	private Double shipmentCost;
	
	@ManyToMany(mappedBy = "shipments")
	private List<Product> products = new ArrayList<>();
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	public Shipment() {}

	public Shipment(String name, String description, Double maxWeight, Double shipmentCost) {
		
		this.name = name;
		this.description = description;
		this.maxWeight = maxWeight;
		this.shipmentCost = shipmentCost;
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
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

	public Double getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(Double maxWeight) {
		this.maxWeight = maxWeight;
	}

	public Double getShipmentCost() {
		return shipmentCost;
	}

	public void setShipmentCost(Double shipmentCost) {
		this.shipmentCost = shipmentCost;
	}

	@Override
	public String toString() {
		return "Shipment [id=" + id + ", name=" + name + ", description=" + description + ", maxWeight=" + maxWeight
				+ ", shipmentCost=" + shipmentCost + "]";
	}
	
	
	
	
	

}
