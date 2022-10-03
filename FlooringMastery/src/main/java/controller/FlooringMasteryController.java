package controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import dto.Order;
import service.FlooringMasteryServiceLayer;
import ui.FlooringMasteryView;

public class FlooringMasteryController {

	private FlooringMasteryView view;
	private FlooringMasteryServiceLayer service;
	
	public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryServiceLayer service) {
        this.view = view;
        this.service = service;
    }
	
	public void run() throws Exception {
	               
	        boolean keepGoing = true;
	        while (keepGoing) {
	            int choice = view.printMenuAndGetSelection();
	            switch (choice) {
	                case 1:
	                	String date = view.getUserDate();
	    				List<Order> orders = service.getOrdersByDate(date);
	    				view.displayOrdersByDate(orders);
	                    break;
	                case 2:
	                	int orderNumber = view.getOrderNumber();
	    				String customerName = view.getCustomerName();
	    				String stateAbbreviation = view.getStateAbbreviation();
	    				String productType = view.getProductType();
	    				String inputArea = view.getArea();
	    				BigDecimal area = new BigDecimal(inputArea);
	    				String userDate = view.getUserDate();
	    				service.addOrder(orderNumber, customerName, stateAbbreviation, productType, area, userDate);
	    				view.addOrderSuccessMessage();
	                    break;
	                case 3:
	                	int orderEditNumber = view.getOrderNumber();
	    				String dateForEdit = view.getUserDate();
	    				int editChoice = view.printEditMenuAndGetSelection();
	    				String userChange;
	    				switch(editChoice) {
	    				
	    				case 1:
	    					userChange = view.getCustomerName();
	    					service.editOrder(orderEditNumber, editChoice, userChange, dateForEdit);
	    					break;
	    				case 2:
	    					userChange = view.getStateAbbreviation();
	    					service.editOrder(orderEditNumber, editChoice, userChange, dateForEdit);
	    					break;
	    				case 3:
	    					userChange = view.getProductType();
	    					service.editOrder(orderEditNumber, editChoice, userChange, dateForEdit);
	    					break;
	    				case 4:
	    					userChange = view.getArea();
	    					service.editOrder(orderEditNumber, editChoice, userChange, dateForEdit);
	    					break;
	    				default:
	    					throw new UnsupportedOperationException("Unexpected result entered");
	    				}
	    				
	    				view.editOrderSuccessMessage();
	                    break;
	                case 4:
	                	int orderDeleteNumber = view.getOrderNumber();
	    				String dateForDelete = view.getUserDate();
	    				service.deleteOrder(orderDeleteNumber, false, dateForDelete);
	    				Order order = service.getOrderByNumberAndDate(orderDeleteNumber, dateForDelete);
	    				view.displayOrder(order);
	    				String confirmation = view.getDeleteConfirmation();
	    				
	    				if(confirmation.equals("YES")) {
	    					service.deleteOrder(orderDeleteNumber, true, dateForDelete);
	    					view.deleteOrderSuccessMessage();
	    				}
	                    break;
	                case 5:
	                    view.displayLine("EXPORT");
	                    break;
	                case 6:
	                	view.displayExitMessage();
	                	keepGoing = false;
	                    break;
	                default:
	                    view.displayLine("Unknown command");
	            }
	        }
	    }
	
	
}
