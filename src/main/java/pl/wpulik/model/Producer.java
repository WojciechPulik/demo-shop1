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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

@Entity
public class Producer implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_producer")
	private Long id;
	@NotEmpty
	private String name;
	private String description;
	private String url;
	@OneToMany(mappedBy = "producer")
	private List<Product> products = new ArrayList<>();
	@OneToMany(mappedBy = "producer")
	private List<Picture> pictures = new ArrayList<>();
	
	public Producer() {}

	public Producer(@NotEmpty String name, String description, String url, List<Picture> pictures) {
		
		this.name = name;
		this.description = description;
		this.url = url;
		this.pictures = pictures;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}
	

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Producer [id=" + id + ", name=" + name + ", description=" + description + ", url=" + url + "]";
	}
	
	
	

}
