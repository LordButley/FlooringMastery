package ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import dto.Order;

public class FlooringMasteryView {

    private UserIO io;

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }
    
    public void displayLine(String prompt) {
    	io.print(prompt);
    }
    
    public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1. Display orders");
        io.print("2. Create order");
        io.print("3. Edit order");
        io.print("4. Remove order");
        io.print("5. Export Data");
        io.print("6. Exit");

        return io.readInt("Please select from the above choices.", 6);
    }
    
    public int printEditMenuAndGetSelection() {
        io.print("Edit Menu");
        io.print("1. Customer Name");
        io.print("2. State");
        io.print("3. Product Type");
        io.print("4. Area");

        return io.readInt("Please select from the above choices.", 4);
    }
    
    public void displayOrder(Order order) {
		
		io.print("Order Number: " + order.getOrderNumber());
		io.print("Customer name: " + order.getCustomerName());
		io.print("State: " + order.getTax().getStateName());
		io.print("TaxRate: " + order.getTax().getTaxRate());
		io.print("Product type: " + order.getProduct().getProductType());
		io.print("Area: " + order.getArea());
		io.print("Cost per square foot: " + order.getProduct().getCostPerSquareFoot());
		io.print("Labor per square foot: " + order.getProduct().getLaborCostPerSquareFoot());
		io.print("Material cost: " + order.getMaterialCost());
		io.print("Labor cost: " + order.getLabourCost());
		io.print("Tax: " + order.getTotalTax());
		io.print("Total: " + order.getTotal());
		io.print("");
	}
    
    public void displayOrdersByDate(List<Order> orders) {
		
		for(Order order : orders) {
			io.print("Order Number: " + order.getOrderNumber());
			io.print("Customer name: " + order.getCustomerName());
			io.print("State: " + order.getTax().getStateName());
			io.print("TaxRate: " + order.getTax().getTaxRate());
			io.print("Product type: " + order.getProduct().getProductType());
			io.print("Area: " + order.getArea());
			io.print("Cost per square foot: " + order.getProduct().getCostPerSquareFoot());
			io.print("Labor per square foot: " + order.getProduct().getLaborCostPerSquareFoot());
			io.print("Material cost: " + order.getMaterialCost());
			io.print("Labor cost: " + order.getLabourCost());
			io.print("Tax: " + order.getTotalTax());
			io.print("Total: " + order.getTotal());
			io.print("");
		}
	}
    
    public String getUserDate() {

		String userInput = io.readString("Please input the date for the order in the format MM-dd-yyyy");
		return userInput;
	}

	public int getOrderNumber() {
		
		int orderNumber = io.readInt("Please input the new product's order number");
		return orderNumber;
	}
	
	public String getCustomerName() {
		
		String customerName = io.readString("Please enter a new customer name");
		return customerName;
	}
	public String getStateAbbreviation() {
		
		String stateAbbreviation = io.readString("Please enter the state abbreviation");
		return stateAbbreviation;
	}
	public String getProductType() {
		
		String productType = io.readString("Please enter an existing product you want to order");
		return productType;
	}
	public String getArea() {
	
		String area = io.readString("Please enter your flooring area");
		return area;
	}
	
	public String getDeleteConfirmation() {
		io.print("Are you sure that you would like to delete this order?");
		io.print("Write 'YES' to delete the order permanently or write 'NO' to go back.");
		String deleteConfirmation = io.readString("");
		return deleteConfirmation;
	}
	
	public void addOrderSuccessMessage() {
		io.print("Order has been added successfully!!");
	}
	public void editOrderSuccessMessage() {
		io.print("Order has been edited successfully");
	}
	public void deleteOrderSuccessMessage() {
		io.print("Order has been successfully deleted!");
	}
	public void displayExitMessage() {
		io.print("Goodbye!");
	}
	
    
}
    
    