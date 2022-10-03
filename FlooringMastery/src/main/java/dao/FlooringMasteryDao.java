package dao;

import java.util.List;

import dto.Order;
import dto.Product;
import dto.Tax;

public interface FlooringMasteryDao {

		void addOrder(Order order, String date) throws Exception;
		void addProductsAndTaxes(Product product, Tax tax) throws Exception;
		void updateOrder(Order order, String date) throws Exception;
		void deleteOrder(Order order, String date) throws Exception;
		Order getOrderByOrderNumberAndDate(int orderNumber, String date) throws Exception;
		List<Order> getOrdersByDate(String date) throws Exception;
		
		List<Order> getAllOrders() throws Exception;
		List<Product> getAllProducts() throws Exception;
		List<Tax> getAllTaxes() throws Exception;
		
		Product getProductByProductType(String productType) throws Exception;
		Tax getTaxByStateAbbreviation(String stateName) throws Exception;
	
}
