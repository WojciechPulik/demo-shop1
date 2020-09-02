package pl.wpulik.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import pl.wpulik.model.Product;
import pl.wpulik.model.Shipment;
import pl.wpulik.model.User;

public class OrderDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private List<Product> products = new ArrayList<>();
	private Date datePurchase;
	private Date dateRecived;
	private Date dateSent;
	private String orderDetails;
	private User user;
	private Shipment shipment;
	private boolean isRecieved = false;
	private boolean isCashOnDelivery = false;
	private boolean isPayed = false;
	private boolean isSent = false;
	private Double totalPrice;
	private String status;
	
	public OrderDTO() {}

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

	public boolean isCashOnDelivery() {
		return isCashOnDelivery;
	}

	public void setCashOnDelivery(boolean isCashOnDelivery) {
		this.isCashOnDelivery = isCashOnDelivery;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OrderDTO [id=" + id + ", products=" + products + ", datePurchase=" + datePurchase + ", dateRecived="
				+ dateRecived + ", dateSent=" + dateSent + ", orderDetails=" + orderDetails + ", user=" + user
				+ ", shipment=" + shipment + ", isRecieved=" + isRecieved + ", isCashOnDelivery=" + isCashOnDelivery
				+ ", isPayed=" + isPayed + ", isSent=" + isSent + ", totalPrice=" + totalPrice + ", status=" + status
				+ "]";
	}
	
	

}
