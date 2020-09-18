package pl.wpulik.dto;

import java.io.Serializable;

public class SearchParamDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String phrase;

	public SearchParamDTO() {
	}

	public SearchParamDTO(String phrase) {
		this.phrase = phrase;
	}

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	@Override
	public String toString() {
		return "SearchParamDTO [phrase=" + phrase + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((phrase == null) ? 0 : phrase.hashCode());
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
		SearchParamDTO other = (SearchParamDTO) obj;
		if (phrase == null) {
			if (other.phrase != null)
				return false;
		} else if (!phrase.equals(other.phrase))
			return false;
		return true;
	}
	
	
	
	

}
