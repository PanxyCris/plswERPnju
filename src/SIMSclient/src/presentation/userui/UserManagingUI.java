package SIMSclient.src.presentation.userui;

import java.net.URL;
import java.util.ResourceBundle;
import SIMSclient.src.vo.UserVO;
import SIMSclient.src.vo.UserVO.UserRole;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class UserManagingUI extends UserUI implements Initializable{
	    ObservableList<UserVO> list = FXCollections.observableArrayList();
	    @FXML
	    protected TextField idField;
	    @FXML
        protected TextField nameField;
	    @FXML
        protected TextField passwordField;
	    @FXML
        protected ChoiceBox<UserRole> roleChoice;
    	@FXML
    	protected Label idLabel;


		@FXML
		protected TableView<UserVO> table;
		@FXML
		private TableColumn<UserVO,String> tableID;
		@FXML
		private TableColumn<UserVO,String> tablePassword;
		@FXML
		private TableColumn<UserVO,String> tableName;
		@FXML
		private TableColumn<UserVO,String> tableRole;

		@FXML
		public void insert() throws Exception{
			Stage stage = new Stage();
			new UserInsertUI().start(stage);
		}

		@FXML
		public void delete() throws Exception{
			Stage stage = new Stage();
			new UserDeleteUI().start(stage);
		}

		@FXML
		public void update(){
		/*	 UserVO user = new UserVO(idField.getText(), nameField.getText(), passwordField.getText(),roleChoice.getValue());
	         UserBLService service = UserBL.getInstance().getUserService();

	            if(!service.judgeLegal(user)){
	            	Platform.runLater(new Runnable() {
			    	    public void run() {
			    	        try {
								new RemindPrintUI().start(new Stage());
							} catch (Exception e) {
								e.printStackTrace();
							}
			    	    }
			    	});
	            }
	            else if(!service.judgeExist(idField.getText())){
					  Platform.runLater(new Runnable() {
				    	    public void run() {
				    	        try {
									new RemindnotExistUI().start(new Stage());
								} catch (Exception e) {
									e.printStackTrace();
								}
				    	    }
				    	});
				  }
	            else if(service.find(user)){
	            	Platform.runLater(new Runnable() {
			    	    public void run() {
			    	        try {
								new RemindRepeatUI().start(new Stage());
							} catch (Exception e) {
								e.printStackTrace();
							}
			    	    }
			    	});
	            }else{
	            list.remove(service.find(idField.getText(),nameField.getText()));
	            service.update(user);
	            list.add(user);
	            table.setItems(list);
	            }*/
		}

		@FXML
		public void find(){

		}

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			UserVO user1 = new UserVO("161250058","���","161250058",UserRole.SALESPERSON);
			UserVO user2 =new UserVO("161250136","���Ӳ�","161250136",UserRole.FINANCIALSTAFF);
			tableID.setCellValueFactory(
	                new PropertyValueFactory<UserVO,String>("ID"));
	        tableName.setCellValueFactory(
	                new PropertyValueFactory<UserVO,String>("name"));
	        tablePassword.setCellValueFactory(
	                new PropertyValueFactory<UserVO,String>("password"));
	        tableRole.setCellValueFactory(
	                new PropertyValueFactory<UserVO,String>("role"));
	        list.addAll(user1,user2);
	        table.setItems(list);

		}

		@Override
		public void start(Stage primaryStage) throws Exception {
			   changeStage("UserManageUI","UserManagingUI.fxml");
		}


		/**
		 * �Զ�������id
		 * @param id
		 * @return ��id
		 */
		public String addOne(String id){
			int tmp = Integer.parseInt(id);
			tmp++;
			String newID = String.valueOf(tmp);
			return newID;
		}

}