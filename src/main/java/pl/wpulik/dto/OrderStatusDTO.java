package pl.wpulik.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderStatusDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long orderId;
	private Long orderStatusId;
	private List<StatusDTO> statusList = new ArrayList<>();
	
	public OrderStatusDTO(){}

	public OrderStatusDTO(Long id, Long orderId, List<StatusDTO> statusList) {
		this.id = id;
		this.orderId = orderId;
		this.statusList = statusList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderStatusId() {
		return orderStatusId;
	}

	public void setOrderStatusId(Long orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	public List<StatusDTO> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<StatusDTO> statusList) {
		this.statusList = statusList;
	}

	@Override
	public String toString() {
		return "OrderStatusDTO [orderId=" + orderId + ", statusList=" + statusList + "]";
	}
	
	
	
	
	
	
	

}
