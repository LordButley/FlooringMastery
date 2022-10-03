package dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Order {

	private int orderNumber;
	private String customerName;
	private BigDecimal area;
	
	private Product product;
	private Tax tax;
	
	private BigDecimal materialCost;
	private BigDecimal labourCost;
	private BigDecimal totalTax;
	private BigDecimal total;
	
	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Tax getTax() {
		return tax;
	}

	public void setTax(Tax tax) {
		this.tax = tax;
	}

	public BigDecimal getMaterialCost() {
		return materialCost;
	}

	public void setMaterialCost(BigDecimal materialCost) {
		this.materialCost = materialCost;
	}

	public BigDecimal getLabourCost() {
		return labourCost;
	}

	public void setLabourCost(BigDecimal labourCost) {
		this.labourCost = labourCost;
	}

	public BigDecimal getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(BigDecimal totalTax) {
		this.totalTax = totalTax;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public Order(Product product, Tax tax, BigDecimal area) {
		this.product = product;
		this.tax = tax;
		this.area = area;
		materialCost = (area.multiply(product.getCostPerSquareFoot()));
		labourCost = (area.multiply(product.getLaborCostPerSquareFoot()));
		totalTax = (materialCost.add(labourCost)).multiply(tax.getTaxRate().divide(BigDecimal.valueOf(100)));
		total = materialCost.add(labourCost).add(totalTax);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(area, customerName, labourCost, materialCost, orderNumber, product, tax, total, totalTax);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderNumber != other.orderNumber) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
//        if (!Objects.equals(this.orderDate, other.orderDate)) {
//            return false;
//        }
        if (!Objects.equals(this.tax, other.tax)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        return Objects.equals(this.area, other.area);
	}

	@Override
	public String toString() {
		return "Order [orderNumber=" + orderNumber + ", customerName=" + customerName + ", area=" + area + ", product="
				+ product + ", tax=" + tax + ", materialCost=" + materialCost + ", labourCost=" + labourCost
				+ ", totalTax=" + totalTax + ", total=" + total + "]";
	}
}
