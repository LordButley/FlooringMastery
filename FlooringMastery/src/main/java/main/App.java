package main;

import controller.FlooringMasteryController;
import dao.FlooringMasteryDao;
import dao.FlooringMasteryDaoImpl;
import service.FlooringMasteryServiceLayer;
import service.FlooringMasteryServiceLayerImpl;
import ui.FlooringMasteryView;
import ui.UserIO;
import ui.UserIOImpl;

public class App {
	
	public static void main(String[] args) throws Exception {

		UserIO io = new UserIOImpl();
		FlooringMasteryView view = new FlooringMasteryView(io);
		FlooringMasteryDao dao = new FlooringMasteryDaoImpl();
		FlooringMasteryServiceLayer service = new FlooringMasteryServiceLayerImpl(dao);
		FlooringMasteryController controller = new FlooringMasteryController(view, service);
		controller.run();
	}
}
