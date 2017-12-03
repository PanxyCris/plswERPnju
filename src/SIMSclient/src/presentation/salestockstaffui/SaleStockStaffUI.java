package SIMSclient.src.presentation.salesui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SalesUI extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		    Parent root = FXMLLoader.load(getClass().getResource("SalesUI.fxml"));
	        Scene scene = new Scene(root, 900, 600);
	        primaryStage.setScene(scene);
	        primaryStage.setTitle("����������Ա����");
	        primaryStage.show();
	}

	public static void main(String[] args){
		launch(args);
	}

}