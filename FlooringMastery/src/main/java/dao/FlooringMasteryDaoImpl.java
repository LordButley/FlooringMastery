package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import dto.Order;
import dto.Product;
import dto.Tax;

public class FlooringMasteryDaoImpl implements FlooringMasteryDao {
	
	public static String ORDER_FOLDER;
	public static String PRODUCT_FILE;
	public static String TAX_FILE;
	public static final String DELIMITER = ",";
	
	Map<String, Product> products = new HashMap<String, Product>();
	Map<String, Tax> taxes = new HashMap<String, Tax>();
	Map<Integer, Order> orders = new HashMap<Integer, Order>();
	
	public FlooringMasteryDaoImpl() {
		ORDER_FOLDER = "C:/Users/giedr/eclipse-workspace/FlooringMastery/Orders";
		PRODUCT_FILE = "C:/Users/giedr/eclipse-workspace/FlooringMastery/Data/Products.txt";
		TAX_FILE = "C:/Users/giedr/eclipse-workspace/FlooringMastery/Data/Taxes.txt";
	}
	public FlooringMasteryDaoImpl(String orderFolder, String productsTextFile, String taxTextFile) {
		ORDER_FOLDER = orderFolder;
		PRODUCT_FILE = productsTextFile;
		TAX_FILE = taxTextFile;
	}
	
	public void addOrder(Order order, String date) throws Exception {
		loadOrders();
		orders.put(order.getOrderNumber(), order);
		writeOrder(date);
	}
	
	public void addProductsAndTaxes(Product product, Tax tax) throws Exception {
		loadProductsAndTaxes();
		products.put(product.getProductType(), product);
		taxes.put(tax.getStateName(), tax);
		writeProduct();
		writeTax();
	}
	
	public void updateOrder(Order order, String date) throws Exception {
		loadOrders();
		orders.replace(order.getOrderNumber(), order);
		writeOrder(date);
	}
	
	public void deleteOrder(Order order, String date) throws Exception {
		loadOrdersFromDate(date);
		orders.remove(order.getOrderNumber());
		writeOrder(date);
	}
	
	public Order getOrderByOrderNumberAndDate(int orderNumber, String date) throws Exception {
		loadOrdersFromDate(date);
		return orders.get(orderNumber);
	}
	
	public List<Order> getOrdersByDate(String date) throws Exception {
		loadOrdersFromDate(date);
		return new ArrayList<Order>(orders.values());
	}

	public List<Order> getAllOrders() throws Exception{
		loadOrders();
		return new ArrayList<Order>(orders.values());
	}
	
	public List<Product> getAllProducts() throws Exception{
		loadProductsAndTaxes();
		return new ArrayList<Product>(products.values());
	}
	
	public List<Tax> getAllTaxes() throws Exception{
		loadProductsAndTaxes();
		return new ArrayList<Tax>(taxes.values());
	}
	
	public Product getProductByProductType(String productType) throws Exception {
		loadProductsAndTaxes();
		return products.get(productType);
	}
	
	public Tax getTaxByStateAbbreviation(String stateAbbreviation) throws Exception {
		loadProductsAndTaxes();
		return taxes.get(stateAbbreviation);
	}
	
	//Translate data from file to an object in memory
	private Order unmarshallOrder(String OrderAsText) throws Exception {
		
		String[] OrderAsElements = OrderAsText.split(DELIMITER);
		int orderNumber = Integer.parseInt(OrderAsElements[0]);
		String customerName = OrderAsElements[1];
		String stateAbbreviation = OrderAsElements[2];
		String productType = OrderAsElements[4];
		BigDecimal area = new BigDecimal(OrderAsElements[5]);
		
		Product product = getProductByProductType(productType);
		Tax tax = getTaxByStateAbbreviation(stateAbbreviation);
		Order orderFromFile = new Order(product, tax, area);
		orderFromFile.setOrderNumber(orderNumber);
		orderFromFile.setCustomerName(customerName);
		orderFromFile.setArea(area);
		
		return orderFromFile;
	}
	
	//Translate data from file to an object in memory
	private Product unmarshallProduct(String ProductAsText) {
				
		String[] ProductAsElements = ProductAsText.split(DELIMITER);
		String productName = ProductAsElements[0];
		Product productFromFile = new Product();
		productFromFile.setProductType(productName);
				
		BigDecimal costPerSquareFoot = new BigDecimal(ProductAsElements[1]);
		BigDecimal laborCostPerSquareFoot = new BigDecimal(ProductAsElements[2]);
				
		productFromFile.setCostPerSquareFoot(costPerSquareFoot.setScale(2, RoundingMode.HALF_UP));
		productFromFile.setLaborCostPerSquareFoot(laborCostPerSquareFoot.setScale(2, RoundingMode.HALF_UP));
				
		return productFromFile;
	}
			
			//Translate data from file to an object in memory
	private Tax unmarshallTax(String TaxAsText) {
				
		String[] TaxAsElements = TaxAsText.split(DELIMITER);
		String stateAbbreviation = TaxAsElements[0];
		String stateName = TaxAsElements[1];
		Tax taxFromFile = new Tax();
		taxFromFile.setStateAbbreviation(stateAbbreviation);
		taxFromFile.setStateName(stateName);
				
		BigDecimal taxRate = new BigDecimal(TaxAsElements[2]);
		taxFromFile.setTaxRate(taxRate.setScale(2, RoundingMode.HALF_UP));
				
		return taxFromFile;
	}
			
	private void loadOrders() throws Exception {
		
		File dir = new File(ORDER_FOLDER);
		
		for(File file : dir.listFiles()) {
			
			Scanner scanner;
			
			try {
				scanner = new Scanner(new BufferedReader(new FileReader(file.getAbsolutePath())));
			}catch(FileNotFoundException e) {
				throw new Exception("Could not locate the file");
			}
				
			String currentLine;
			Order currentOrder;
			
			while(scanner.hasNextLine()) {
				currentLine = scanner.nextLine();
				currentOrder = unmarshallOrder(currentLine);
				orders.put(currentOrder.getOrderNumber(), currentOrder);
			}
			scanner.close();
		}
	}
	
	private void loadOrdersFromDate(String date) throws Exception {
		
		String dir = ORDER_FOLDER + "/Orders_" + date.replaceAll("-", "") + ".txt";
			
			Scanner scanner;
			
			try {
				scanner = new Scanner(new BufferedReader(new FileReader(dir)));
			}catch(FileNotFoundException e) {
				return;
			}
				
			String currentLine;
			Order currentOrder;
			
			while(scanner.hasNextLine()) {
				currentLine = scanner.nextLine();
				currentOrder = unmarshallOrder(currentLine);
				orders.put(currentOrder.getOrderNumber(), currentOrder);
			}
			scanner.close();
	}
			
	//Put all unmarshalled data into products and taxes
	private void loadProductsAndTaxes() throws Exception{
				
		Scanner scanner;
				
		try {
			scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
		}
		catch(FileNotFoundException e) {
			throw new Exception("Could not locate the file", e);
		}
				
		String currentLine;
		Product currentProduct;
		Tax currentTax;
				
		while(scanner.hasNextLine()) {
			currentLine = scanner.nextLine();
			currentProduct = unmarshallProduct(currentLine);
			products.put(currentProduct.getProductType(), currentProduct);
		}
				
		try {
			scanner = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
		}
		catch(FileNotFoundException e) {
			throw new Exception("Could not locate the file", e);
		}
				
		while(scanner.hasNextLine()) {
			currentLine = scanner.nextLine();
			currentTax = unmarshallTax(currentLine);
			taxes.put(currentTax.getStateAbbreviation(), currentTax);
		}
				
		scanner.close();
	}
	
	//Translate data from order object into a text file
	private String marshallOrder(Order order) {
		
		String OrderAsText = order.getOrderNumber() + DELIMITER;
		OrderAsText += order.getCustomerName() + DELIMITER;
		OrderAsText += order.getTax().getStateAbbreviation() + DELIMITER;
		OrderAsText += order.getTax().getTaxRate() + DELIMITER;
		OrderAsText += order.getProduct().getProductType() + DELIMITER;
		OrderAsText += order.getArea() + DELIMITER;
		OrderAsText += order.getProduct().getCostPerSquareFoot() + DELIMITER;
		OrderAsText += order.getProduct().getLaborCostPerSquareFoot() + DELIMITER;
		OrderAsText += order.getMaterialCost() + DELIMITER;
		OrderAsText += order.getLabourCost() + DELIMITER;
		OrderAsText += order.getTotalTax() + DELIMITER;
		OrderAsText += order.getTotal();
		
		return OrderAsText;
	}
			
	//Translate data from object in memory into a text file.
	private String marshallProduct(Product product) {
				
		String ProductAsText = product.getProductType() + DELIMITER;
		ProductAsText += product.getCostPerSquareFoot() + DELIMITER;
		ProductAsText += product.getLaborCostPerSquareFoot();
				
		return ProductAsText;
	}
			
	//Translate data from object in memory into a text file.
	private String marshallTax(Tax tax) {
				
		String TaxAsText = tax.getStateAbbreviation() + DELIMITER;
		TaxAsText += tax.getStateName() + DELIMITER;
		TaxAsText += tax.getTaxRate();
				
		return TaxAsText;
	}
	
	//Write marshalled data into the text file.
	private void writeOrder(String orderDate) throws Exception{
		
		String orderDateAsText = orderDate.replaceAll("-", "");
		String dir = ORDER_FOLDER + "/Orders_" + orderDateAsText + ".txt";
		PrintWriter out;
					
		try {
			out = new PrintWriter(new FileWriter(dir));
		}
		catch(Exception e) {
			throw new Exception("Could not save Order data", e);
		}
		
		String OrderAsText;
		Collection<Order> orderList = orders.values();
		for(Order currentOrder : orderList) {
			OrderAsText = marshallOrder(currentOrder);
			out.println(OrderAsText);
			out.flush();
		}
		out.close();
	}
			
	//Write marshalled data into the text file.
	private void writeProduct() throws Exception{
				
		PrintWriter out;
				
		try {
			out = new PrintWriter(new FileWriter(PRODUCT_FILE));
		}
		catch(Exception e) {
			throw new Exception("Could not save Product data", e);
		}
				
		String ProductAsText;
		List<Product> productList = this.getAllProducts();
		for(Product currentProduct : productList) {
			ProductAsText = marshallProduct(currentProduct);
			out.println(ProductAsText);
			out.flush();
		}
		out.close();
	}
	
	//Write marshalled data into the text file.
		private void writeTax() throws Exception{
					
			PrintWriter out;
					
			try {
				out = new PrintWriter(new FileWriter(TAX_FILE));
			}
			catch(Exception e) {
				throw new Exception("Could not save Tax data", e);
			}
					
			String TaxAsText;
			List<Tax> taxList = this.getAllTaxes();
			for(Tax currentTax : taxList) {
				TaxAsText = marshallTax(currentTax);
				out.println(TaxAsText);
				out.flush();
			}
			out.close();
		}
}
