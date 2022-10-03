package dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Tax {
	
	private String stateAbbreviation;
	private String stateName;
	private BigDecimal taxRate;
	
	public String getStateAbbreviation() {
		return stateAbbreviation;
	}

	public void setStateAbbreviation(String stateAbbreviation) {
		this.stateAbbreviation = stateAbbreviation;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}
	
	public Tax(String stateAbbreviation, String stateName, BigDecimal taxRate) {
		this.stateAbbreviation = stateAbbreviation;
		this.stateName = stateName;
		this.taxRate = taxRate;
	}

	public Tax() {
	}

	@Override
	public int hashCode() {
		return Objects.hash(stateAbbreviation, stateName, taxRate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tax other = (Tax) obj;
		return Objects.equals(stateAbbreviation, other.stateAbbreviation) && Objects.equals(stateName, other.stateName)
				&& Objects.equals(taxRate, other.taxRate);
	}

	@Override
	public String toString() {
		return "Tax [stateAbbreviation=" + stateAbbreviation + ", stateName=" + stateName + ", taxRate=" + taxRate
				+ "]";
	}
}
