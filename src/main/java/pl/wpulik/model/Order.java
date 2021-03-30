package pl.wpulik.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity(name = "orders")
public class Order implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Transient
	private static final String DATE_FORMAT = "dd-MMM-uuuu HH:mm:ss";
	@Transient
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_order")
	private Long id;
	@Transient
	private List<Product> products = new ArrayList<>();
	private LocalDateTime datePurchase;
	private LocalDateTime dateRecived;
	private LocalDateTime dateSent;
	private String orderDetails;
	@OneToMany(mappedBy="order")
	private List<OrderProduct> orderProducts = new ArrayList<>();
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shipment_id")
	private Shipment shipment;
	private boolean isRecieved = false;
	private boolean isCashOnDelivery = false;
	private boolean isPayed = false;
	private boolean isSent = false;
	private boolean isCanceled = false;
	private Double totalPrice;
	@OneToOne
	private Address address;
	
	public Order() {}

	public Order(List<Product> products, LocalDateTime datePurchase, String orderDetails, User user,
			boolean isPayed, boolean isCashOnDelivery, Double totalPrice) {
		this.products = products;
		this.datePurchase = datePurchase;
		this.orderDetails = orderDetails;
		this.user = user;
		this.isPayed = isPayed;
		this.isCashOnDelivery = isCashOnDelivery;
		this.totalPrice = totalPrice;
	}
	
	public void addProducts(Product product) {
		this.products.add(product);
		product.getOrders().add(this);
	}
	
	public void removeProducts(Product product) {
		this.products.removeIf(next -> next.equals(product));
		product.getOrders().removeIf(next -> next.equals(this));
	}
	
	public String getFormattedPurchaseTime() {
		return datePurchase.format(FORMATTER);
	}
	
	public String getFormattedReciveTime() {
		if(dateRecived!=null)
			return dateRecived.format(FORMATTER);
		return "--";
	}
	
	public String getFormattedSentTime() {
		if(dateSent!=null)
			return dateSent.format(FORMATTER);
		return "--";
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

	public LocalDateTime getDatePurchase() {
		return datePurchase;
	}

	public void setDatePurchase(LocalDateTime datePurchase) {
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
	
	public Shipment getShipment() {
		return shipment;
	}

	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
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
	
	public boolean isCanceled() {
		return isCanceled;
	}

	public void setCanceled(boolean isCanceled) {
		this.isCanceled = isCanceled;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	public LocalDateTime getDateRecived() {
		return dateRecived;
	}

	public void setDateRecived(LocalDateTime dateRecived) {
		this.dateRecived = dateRecived;
	}

	public LocalDateTime getDateSent() {
		return dateSent;
	}

	public void setDateSent(LocalDateTime dateSent) {
		this.dateSent = dateSent;
	}

	public boolean isCashOnDelivery() {
		return isCashOnDelivery;
	}

	public void setCashOnDelivery(boolean isCashOnDelivery) {
		this.isCashOnDelivery = isCashOnDelivery;
	}
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<OrderProduct> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<OrderProduct> orderProducts) {
		this.orderProducts = orderProducts;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", products=" + products + ", datePurchase=" + datePurchase + ", dateRecived="
				+ dateRecived + ", dateSent=" + dateSent + ", orderDetails=" + orderDetails + ", user=" + user
				+ ", isRecieved=" + isRecieved + ", isCashOnDelivery=" + isCashOnDelivery + ", isPayed=" + isPayed
				+ ", isSent=" + isSent + ", totalPrice=" + totalPrice + "]";
	}

	
	
	
	
	
	
	
	
}