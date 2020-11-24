package pl.wpulik.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;

import pl.wpulik.model.OrderProduct;
import pl.wpulik.model.Shipment;
import pl.wpulik.model.User;

public class OrderDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private List<OrderProduct> products = new ArrayList<>();
	private LocalDateTime datePurchase;
	private LocalDateTime dateRecived;
	private LocalDateTime dateSent;
	private String formattedPurchaseTime;
	private String formattedReciveTime;
	private String formattedSentTime;
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
	
	public List<OrderProduct> getProducts() {
		return products;
	}

	public void setProducts(List<OrderProduct> products) {
		this.products = products;
	}

	public String getFormattedPurchaseTime() {
		return formattedPurchaseTime;
	}

	public void setFormattedPurchaseTime(String formattedPurchaseTime) {
		this.formattedPurchaseTime = formattedPurchaseTime;
	}

	public String getFormattedReciveTime() {
		return formattedReciveTime;
	}

	public void setFormattedReciveTime(String formattedReciveTime) {
		this.formattedReciveTime = formattedReciveTime;
	}

	public String getFormattedSentTime() {
		return formattedSentTime;
	}

	public void setFormattedSentTime(String formattedSentTime) {
		this.formattedSentTime = formattedSentTime;
	}

	public List<OrderProduct> getOrderProducts() {
		return products;
	}

	public void setOrderProducts(List<OrderProduct> products) {
		this.products = products;
	}

	public LocalDateTime getDatePurchase() {
		return datePurchase;
	}

	public void setDatePurchase(LocalDateTime datePurchase) {
		this.datePurchase = datePurchase;
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
