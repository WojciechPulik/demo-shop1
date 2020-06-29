package pl.wpulik.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Fetch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Entity(name = "products")
public class Product implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_product")
	private Long id;
	@Column(name= "product_index")
	private String index;
	private String name;
	private String description;
	private int quantity;
	private Double price;
	private Double discount;
	private boolean isActive = false;
	private boolean isPromoted = false;
	private boolean isDiscounted = false;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "producer_id")
	private Producer producer;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(
			name = "products_orders",
			joinColumns = {@JoinColumn(name = "product_id", referencedColumnName="id_product")},
			inverseJoinColumns = {@JoinColumn(name = "order_id", referencedColumnName="id_order")})
	@Fetch(FetchMode.JOIN)
	private List<Order> orders = new ArrayList<>();
	@ManyToMany
	@JoinTable(
			name = "products_shipments",
			joinColumns = {@JoinColumn(name = "product_id", referencedColumnName="id_product")},
			inverseJoinColumns = {@JoinColumn(name = "shipment_id", referencedColumnName="id_shipment")})
	private List<Shipment> shipments = new ArrayList<>();
	@ManyToMany
	@JoinTable(
			name = "products_categories",
			joinColumns = {@JoinColumn(name = "product_id", referencedColumnName="id_product")},
			inverseJoinColumns = {@JoinColumn(name = "category_id", referencedColumnName="id_category")})
	private List<Category> categories = new ArrayList<>();
	@OneToMany
	@JoinColumn(name = "product_id", referencedColumnName = "id_product")
	private List<Picture> pictures;
	
	public void addOrder(Order order) {
		order.setProducts(Arrays.asList(this));
		getOrders().add(order);
	}
	
	public Product() {}
	
	public Product(List<Order> orders, String index, String name, Producer producer, String description, int quantity, Double price) {
		this.orders = orders;
		this.index = index;
		this.name = name;
		this.producer = producer;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isPromoted() {
		return isPromoted;
	}

	public void setPromoted(boolean isPromoted) {
		this.isPromoted = isPromoted;
	}

	public boolean isDiscounted() {
		return isDiscounted;
	}

	public void setDiscounted(boolean isDiscounted) {
		this.isDiscounted = isDiscounted;
	}

	public List<Shipment> getShipments() {
		return shipments;
	}

	public void setShipments(List<Shipment> shipments) {
		this.shipments = shipments;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", index=" + index + ", name=" + name + ", description=" + description
				+ ", quantity=" + quantity + ", price=" + price + ", discount=" + discount + ", isActive=" + isActive
				+ ", isPromoted=" + isPromoted + ", isDiscounted=" + isDiscounted + 
				", producer=" + producer + 
				"]";
	}
	
	
	
	
	
	
	

}
