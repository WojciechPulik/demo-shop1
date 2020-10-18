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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity(name = "shipments")
public class Shipment implements Serializable{
	private static final long serialVersionUID = 1L;
	public static Double CASH_ON_DELIVERY_COST = 5.0;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_shipment")
	private Long id;
	private String name;
	private String supplier;
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

	public Shipment(String name, String supplier,String description, Double maxWeight, Double shipmentCost) {
		
		this.name = name;
		this.supplier = supplier;
		this.description = description;
		this.maxWeight = maxWeight;
		this.shipmentCost = shipmentCost;
	}
	
	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((maxWeight == null) ? 0 : maxWeight.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((shipmentCost == null) ? 0 : shipmentCost.hashCode());
		result = prime * result + ((supplier == null) ? 0 : supplier.hashCode());
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
		Shipment other = (Shipment) obj;
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
		if (maxWeight == null) {
			if (other.maxWeight != null)
				return false;
		} else if (!maxWeight.equals(other.maxWeight))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (shipmentCost == null) {
			if (other.shipmentCost != null)
				return false;
		} else if (!shipmentCost.equals(other.shipmentCost))
			return false;
		if (supplier == null) {
			if (other.supplier != null)
				return false;
		} else if (!supplier.equals(other.supplier))
			return false;
		return true;
	}

	
	
	
	
	
	

}
