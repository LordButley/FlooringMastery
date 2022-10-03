package service;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;

import dao.FlooringMasteryDao;
import dto.Order;
import dto.Product;
import dto.Tax;

public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer {
	
	FlooringMasteryDao dao;
	
	public FlooringMasteryServiceLayerImpl(FlooringMasteryDao dao) {
		this.dao = dao;
	}
	
	public void addOrder(int orderNumber, String customerName, String stateAbbr,
			String productType, BigDecimal area, String date) throws Exception {
		
		dateFormatValidation(date);
		LocalDate currentDate = LocalDate.now();
		LocalDate formattedDate;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		
		try {
			formattedDate = LocalDate.parse(date, formatter);
			
		}catch(DateTimeParseException e) {
			throw new Exception("Sorry, but your date was inserted in the wrong format, please enter the date in the format MM-dd-yyyy");
		}
		
		if(ChronoUnit.DAYS.between(currentDate, formattedDate) < 0) {
			throw new Exception("Sorry, but your date is invalid. Please insert a date in the future");
		}
		
		if(customerName == null || customerName.trim().length() == 0) {
			throw new Exception("Sorry but you need to enter a name");
		}
		
		if(dao.getTaxByStateAbbreviation(stateAbbr) == null ||
				stateAbbr.length() == 0) {
			throw new Exception("Sorry, but you need to enter a valid state name");
		}
		
		if(dao.getProductByProductType(productType) == null ||
			productType.length() == 0) {
			throw new Exception("Sorry, but the product type you entered does not exist");
		}
		
		Product product = dao.getProductByProductType(productType);
		Tax tax = dao.getTaxByStateAbbreviation(stateAbbr);
		Order order = new Order(product, tax, area);
		order.setOrderNumber(orderNumber);
		order.setCustomerName(customerName);
		
		dao.addOrder(order, date);
	}
	
	public List<Order> getAllOrders() throws Exception{
		return dao.getAllOrders();
	}
	
	public Order getOrderByNumberAndDate(int orderNumber, String date) throws Exception{
		
		dateFormatValidation(date);
		Order order = dao.getOrderByOrderNumberAndDate(orderNumber, date);
		
		if(order == null) {
			throw new Exception("Sorry, but the order was not found");
		}
		
		return order;
	}
	
	public List<Order> getOrdersByDate(String date) throws Exception{
		
		dateFormatValidation(date);
		
		try {
		List<Order> orders = dao.getOrdersByDate(date);
		if(orders == null || orders.isEmpty()) {
			throw new Exception("Sorry, but no orders were found for this date");
		}
		return orders;
		}
		catch(FileNotFoundException e) {
			throw new Exception("Sorry, but no orders exist at this date");
		}
	}
	
	
	public void editOrder(int orderNumber, int editChoice, String change, String date) throws Exception {
		
		dateFormatValidation(date);
		Order order = dao.getOrderByOrderNumberAndDate(orderNumber, date);
		
		if(order == null) {
			throw new Exception("Sorry, but the order was not found");
		}
		
		switch(editChoice) {
		
		case 1:
			order.setCustomerName(change);
			dao.updateOrder(order, date);
			return;
		case 2:
			Tax tax = dao.getTaxByStateAbbreviation(change);
			if(tax == null) {
				throw new Exception("Sorry, but you need to enter an existing state name");
			}
			order.setTax(tax);
			dao.updateOrder(order, date);
			return;
		case 3:
			Product product = dao.getProductByProductType(change);
			if(product == null) {
				throw new Exception("Sorry, but the product type you entered does not exist");
			}
			order.setProduct(product);
			dao.updateOrder(order, date);
			return;
		case 4:
			BigDecimal newArea = new BigDecimal(change);
			order.setArea(newArea);
			dao.updateOrder(order, date);
			return;
		default:
			throw new UnsupportedOperationException("Unexpected result");
		}
		
	}
	
	
	public void deleteOrder(int orderNumber, Boolean confirmation, String date) throws Exception {
		
		dateFormatValidation(date);
		Order order = dao.getOrderByOrderNumberAndDate(orderNumber, date);
		
		if(order == null) {
			throw new Exception("Sorry, but the order was not found");
		}
		
		if(!confirmation) {
			return;
		}
		else {
			dao.deleteOrder(order, date);
			return;
		}
	}
	
	
	public void dateFormatValidation(String date) throws Exception {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		
		try {
			LocalDate formattedDate = LocalDate.parse(date, formatter);
			return;
			
		}catch(DateTimeParseException e) {
			throw new Exception("Sorry, but your date was inserted in the wrong format, please enter the date in the format MM-dd-yyyy");
		}
	}
}
