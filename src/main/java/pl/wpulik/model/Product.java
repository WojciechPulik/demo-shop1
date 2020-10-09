package pl.wpulik.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

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
	@Column(length = 512)
	private String description;
	private int quantity;
	@Transient
	private int addedQuantity;
	private Double price;
	@Transient
	private Double summaryCost;
	@Transient
	private String mainPicture;
	private Double discount;
	private boolean isActive = false;
	private boolean isPromoted = false;
	private boolean isDiscounted = false;	
	@ManyToOne
	@JoinColumn(name = "producer_id")
	private Producer producer;
	@ManyToMany
	@JoinTable(
			name = "products_orders",
			joinColumns = {@JoinColumn(name = "product_id", referencedColumnName="id_product")},
			inverseJoinColumns = {@JoinColumn(name = "order_id", referencedColumnName="id_order")})
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
	private List<Picture> pictures = new ArrayList<>();
	
	public Product() {}
	
	public Product(String index, String name, Producer producer, String description, int quantity, Double price, int addedQuantity) {
		
		this.index = index;
		this.name = name;
		this.producer = producer;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.addedQuantity = addedQuantity;
	}
	
	public void addOrder(Order order) {
		order.setProducts(Arrays.asList(this));
		getOrders().add(order);
	}
	
	public Double valueOfAll() {
		Double result = Math.round(addedQuantity * price * 100)/100.0;
		return result;
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

	public String getMainPicture() {
		return mainPicture;
	}

	public void setMainPicture(String mainPicture) {
		this.mainPicture = mainPicture;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
	public Double getSummaryCost() {
		return summaryCost;
	}

	public void setSummaryCost(Double summaryCost) {
		this.summaryCost = summaryCost;
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
	
	public int getAddedQuantity() {
		return addedQuantity;
	}

	public void setAddedQuantity(int addedQuantity) {
		this.addedQuantity = addedQuantity;
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
				", producer=" + producer.getName() + ", Pictures=" + pictures + 
				"]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + addedQuantity;
		result = prime * result + ((categories == null) ? 0 : categories.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((discount == null) ? 0 : discount.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result + (isDiscounted ? 1231 : 1237);
		result = prime * result + (isPromoted ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((orders == null) ? 0 : orders.hashCode());
		result = prime * result + ((pictures == null) ? 0 : pictures.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((producer == null) ? 0 : producer.hashCode());
		result = prime * result + quantity;
		result = prime * result + ((shipments == null) ? 0 : shipments.hashCode());
		result = prime * result + ((summaryCost == null) ? 0 : summaryCost.hashCode());
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
		Product other = (Product) obj;
		if (addedQuantity != other.addedQuantity)
			return false;
		if (categories == null) {
			if (other.categories != null)
				return false;
		} else if (!categories.equals(other.categories))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (discount == null) {
			if (other.discount != null)
				return false;
		} else if (!discount.equals(other.discount))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		if (isActive != other.isActive)
			return false;
		if (isDiscounted != other.isDiscounted)
			return false;
		if (isPromoted != other.isPromoted)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orders == null) {
			if (other.orders != null)
				return false;
		} else if (!orders.equals(other.orders))
			return false;
		if (pictures == null) {
			if (other.pictures != null)
				return false;
		} else if (!pictures.equals(other.pictures))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (producer == null) {
			if (other.producer != null)
				return false;
		} else if (!producer.equals(other.producer))
			return false;
		if (quantity != other.quantity)
			return false;
		if (shipments == null) {
			if (other.shipments != null)
				return false;
		} else if (!shipments.equals(other.shipments))
			return false;
		if (summaryCost == null) {
			if (other.summaryCost != null)
				return false;
		} else if (!summaryCost.equals(other.summaryCost))
			return false;
		return true;
	}
	
	
	
	
	
	
	

}
