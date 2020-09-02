package pl.wpulik.dto;

import java.io.Serializable;

public class StatusDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String status;
	private String name;
	
	public StatusDTO () {}

	public StatusDTO(Long id, String status, String name) {
		this.id = id;
		this.status = status;
		this.name = name;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "StatusDTO [status=" + status + ", name=" + name + "]";
	}
	
	

}
