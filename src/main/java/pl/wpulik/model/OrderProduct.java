package pl.wpulik.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="addedproducts")
public class OrderProduct implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_addedproduct")
	private Long id;
	private Long productId;
	@Column(name= "product_index")
	private String index;
	private String name;
	private int addedQuantity;
	private Double price;
	private Double summaryCost;
	private Double discount;	
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;	
	
	public OrderProduct() {}

	public OrderProduct(Long productId, String index, String name, int addedQuantity, Double price,
			Double summaryCost) {
		this.productId = productId;
		this.index = index;
		this.name = name;
		this.addedQuantity = addedQuantity;
		this.price = price;
		this.summaryCost = summaryCost;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	public int getAddedQuantity() {
		return addedQuantity;
	}

	public void setAddedQuantity(int addedQuantity) {
		this.addedQuantity = addedQuantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getSummaryCost() {
		return summaryCost;
	}

	public void setSummaryCost(Double summaryCost) {
		this.summaryCost = summaryCost;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + addedQuantity;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
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
		OrderProduct other = (OrderProduct) obj;
		if (addedQuantity != other.addedQuantity)
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (summaryCost == null) {
			if (other.summaryCost != null)
				return false;
		} else if (!summaryCost.equals(other.summaryCost))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderProduct [id=" + id + ", productId=" + productId + ", index=" + index + ", name=" + name
				+ ", addedQuantity=" + addedQuantity + ", price=" + price + ", summaryCost=" + summaryCost + "]";
	}
	
	
	
	
	

}
