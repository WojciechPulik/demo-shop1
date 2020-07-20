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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

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
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name = "category_shipments",
			joinColumns = {@JoinColumn(name = "shipment_id", referencedColumnName="id_shipment")},
			inverseJoinColumns = {@JoinColumn(name = "category_id", referencedColumnName="id_category")})
	private List<Category> categories = new ArrayList<>();
	@OneToMany(mappedBy = "shipment")
	private List<Order> orders = new ArrayList<>();
	
	public Shipment() {}

	public Shipment(String name, String description, Double maxWeight, Double shipmentCost) {
		
		this.name = name;
		this.description = description;
		this.maxWeight = maxWeight;
		this.shipmentCost = shipmentCost;
	}
	
	public void addCategories(Category category) {
		this.categories.add(category);
		category.getShipments().add(this);
	}
	
	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
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
	
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "Shipment [id=" + id + ", name=" + name + ", description=" + description + ", maxWeight=" + maxWeight
				+ ", shipmentCost=" + shipmentCost + "]";
	}
	
	
	
	
	

}
