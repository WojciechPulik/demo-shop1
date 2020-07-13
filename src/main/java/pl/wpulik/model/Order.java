package pl.wpulik.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity(name = "orders")
public class Order implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_order")
	private Long id;
	@ManyToMany(mappedBy = "orders",
			fetch = FetchType.EAGER,
			cascade = CascadeType.MERGE)
	@Fetch(FetchMode.JOIN)
	private List<Product> products = new ArrayList<>();
	private Date datePurchase;
	private Date dateRecived;
	private Date dateSent;
	private String orderDetails;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	private User user;
	private boolean isRecieved = false;
	private boolean isCashOnDelivery = false;
	private boolean isPayed = false;
	private boolean isSent = false;
	private Double totalPrice;
	
	public void addProducts(Product product) {
		this.products.add(product);
		product.getOrders().add(this);
	}
	
	public Order() {}

	public Order(List<Product> products, Date datePurchase, String orderDetails, User user,
			boolean isPayed, boolean isCashOnDelivery, Double totalPrice) {
		this.products = products;
		this.datePurchase = datePurchase;
		this.orderDetails = orderDetails;
		this.user = user;
		this.isPayed = isPayed;
		this.isCashOnDelivery = isCashOnDelivery;
		this.totalPrice = totalPrice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Date getDatePurchase() {
		return datePurchase;
	}

	public void setDatePurchase(Date datePurchase) {
		this.datePurchase = datePurchase;
	}

	public String getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(String orderDetails) {
		this.orderDetails = orderDetails;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isRecieved() {
		return isRecieved;
	}

	public void setRecieved(boolean isRecieved) {
		this.isRecieved = isRecieved;
	}

	public boolean isPayed() {
		return isPayed;
	}

	public void setPayed(boolean isPayed) {
		this.isPayed = isPayed;
	}

	public boolean isSent() {
		return isSent;
	}

	public void setSent(boolean isSent) {
		this.isSent = isSent;
	}
	
	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	public Date getDateRecived() {
		return dateRecived;
	}

	public void setDateRecived(Date dateRecived) {
		this.dateRecived = dateRecived;
	}

	public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

	public boolean isCashOnDelivery() {
		return isCashOnDelivery;
	}

	public void setCashOnDelivery(boolean isCashOnDelivery) {
		this.isCashOnDelivery = isCashOnDelivery;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", products=" + products + ", datePurchase=" + datePurchase + ", dateRecived="
				+ dateRecived + ", dateSent=" + dateSent + ", orderDetails=" + orderDetails + ", user=" + user
				+ ", isRecieved=" + isRecieved + ", isCashOnDelivery=" + isCashOnDelivery + ", isPayed=" + isPayed
				+ ", isSent=" + isSent + ", totalPrice=" + totalPrice + "]";
	}

	
	
	
	
	
	
	
	
}
