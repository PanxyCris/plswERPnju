package presentation.inventorymanagerui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presentation.inventorymanagerui.controller.MakeReceiptController;
import vo.uservo.UserVO;

public class MakeReceiptUI {

	public void start(UserVO user) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/MakeReceiptUI.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setScene( new Scene((Pane) loader.load()));
        MakeReceiptController controller = loader.<MakeReceiptController>getController();
        controller.initData(user);
        primaryStage.show();

	}

}
