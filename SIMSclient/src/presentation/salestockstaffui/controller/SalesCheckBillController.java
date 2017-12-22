package presentation.salestockstaffui.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import bussinesslogic.salesbl.SalesController;
import bussinesslogicservice.salesblservice.SalesBLService;
import dataenum.BillState;
import dataenum.BillType;
import dataenum.ResultMessage;
import dataenum.findtype.FindSalesType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import presentation.remindui.RemindPrintUI;
import vo.billvo.salesbillvo.SalesVO;
import vo.commodityvo.CommodityItemVO;
import vo.uservo.UserVO;

public class SalesCheckBillController extends SaleStockStaffController implements Initializable {

	SalesBLService service = new SalesController();
	ObservableList<SalesVO> list = FXCollections.observableArrayList();
	ObservableList<CommodityItemVO> commodityList = FXCollections.observableArrayList();

	@FXML
	ChoiceBox<String> findChoice;
	@FXML
	TextField findingField;

	@FXML
	TableView<SalesVO> table;
	@FXML
	TableColumn<SalesVO,String> tableID;
	@FXML
	TableColumn<SalesVO,String> tableType;
	@FXML
	TableColumn<SalesVO,String> tableMember;
	@FXML
	TableColumn<SalesVO,String> tableSaleMan;
	@FXML
	TableColumn<SalesVO,String> tableWarehouse;
	@FXML
	TableColumn<SalesVO,Double> tableBefore;
	@FXML
	TableColumn<SalesVO,Double> tableVoucher;
	@FXML
	TableColumn<SalesVO,Double> tableAllowance;
	@FXML
	TableColumn<SalesVO,Double> tableAfter;
	@FXML
	TableColumn<SalesVO,String> tableOperator;
	@FXML
	TableColumn<SalesVO,String> tableNote;
	@FXML
	TableColumn<SalesVO,String> tableList;
	@FXML
	TableColumn<SalesVO,String> tableState;
	@FXML
	TableColumn<SalesVO,String> tableSubmit;
	@FXML
	TableColumn<SalesVO,String> tableRedo;
	@FXML
	TableColumn<SalesVO,String> tableDelete;

	@FXML
	TableView<CommodityItemVO> commodity;
	@FXML
	TableColumn<CommodityItemVO,String> commodityID;
	@FXML
	TableColumn<CommodityItemVO,String> commodityName;
	@FXML
	TableColumn<CommodityItemVO,String> commodityModel;
	@FXML
	TableColumn<CommodityItemVO,Integer> commodityNumber;
	@FXML
	TableColumn<CommodityItemVO,Double> commodityPrice;
	@FXML
	TableColumn<CommodityItemVO,Double> commodityMoney;
	@FXML
	TableColumn<CommodityItemVO,String> commodityNote;

	@FXML
	public void find(){

		ArrayList<SalesVO> list = service.find(findingField.getText(),FindSalesType.getType(findChoice.getValue()));
	       if(list==null){
	    	   Platform.runLater(new Runnable() {
		    	    public void run() {
		    	        try {
		    	        	new RemindPrintUI().start(ResultMessage.ILLEAGLINPUTDATA);
						} catch (Exception e) {
							e.printStackTrace();
						}
		    	    }
		    	});
	       }
	       else{
	    	   table.getItems().clear();
	    	   table.getItems().addAll(list);
	       }

	}

	@FXML
	public void makeSalesBill() throws Exception{
         changeStage("SalesMakeBillUI",user,type,null,null);
	}

	public void initData(UserVO user,BillType type) {
		this.user = user;
		this.type = type;
		findChoice.setItems(FXCollections.observableArrayList(FindSalesType.ID.value,FindSalesType.MEMBER.value,
				FindSalesType.OPERATOR.value,FindSalesType.MEMBER.value,FindSalesType.SALEMAN.value,
				FindSalesType.TOTAL.value));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		list.addAll(service.show());
		table.setItems(list);
		manageInit();
		listInit();
	}


	public void manageInit(){
		tableID.setCellValueFactory(
                new PropertyValueFactory<SalesVO,String>("id"));
		tableType.setCellValueFactory(
                new PropertyValueFactory<SalesVO,String>("typeString"));
		tableMember.setCellValueFactory(
                new PropertyValueFactory<SalesVO,String>("retailer"));
		tableWarehouse.setCellValueFactory(
                new PropertyValueFactory<SalesVO,String>("warehouseString"));
		tableBefore.setCellValueFactory(
                new PropertyValueFactory<SalesVO,Double>("beforePrice"));
		tableAllowance.setCellValueFactory(
                new PropertyValueFactory<SalesVO,Double>("allowance"));
		tableVoucher.setCellValueFactory(
                new PropertyValueFactory<SalesVO,Double>("voucher"));
		tableAfter.setCellValueFactory(
                new PropertyValueFactory<SalesVO,Double>("afterPrice"));
		tableOperator.setCellValueFactory(
                new PropertyValueFactory<SalesVO,String>("operator"));
		tableNote.setCellValueFactory(
                new PropertyValueFactory<SalesVO,String>("note"));
		tableState.setCellValueFactory(
                new PropertyValueFactory<SalesVO,String>("stateString"));
		checkInit();
		submitInit();
		redoInit();
        deleteInit();
	}

	public void checkInit(){

		tableList.setCellFactory((col) -> {
            TableCell<SalesVO, String> cell = new TableCell<SalesVO, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);

                    if (!empty) {
                        Button delBtn = new Button("�鿴��Ʒ�б�");
                        this.setGraphic(delBtn);
                        delBtn.setOnMouseClicked((me) -> {
                        	SalesVO clickedItem = this.getTableView().getItems().get(this.getIndex());
                            commodityList.clear();
                            commodityList.addAll(clickedItem.getCommodity());
                            commodity.setItems(commodityList);

                        });
                    }
                }

            };
            return cell;
        });

	}

	public void submitInit(){

		tableSubmit.setCellFactory((col) -> {
            TableCell<SalesVO, String> cell = new TableCell<SalesVO, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
              for(int i=0;i< this.getTableView().getItems().size();i++){
                   BillState clickedState = this.getTableView().getItems().get(i).getState();
                    if (!empty&&clickedState == BillState.DRAFT) {
                        Button delBtn = new Button("�ύ");
                        this.setGraphic(delBtn);
                        delBtn.setOnMouseClicked((me) -> {
                        	SalesVO clickedItem = this.getTableView().getItems().get(this.getIndex());
                            service.submit(clickedItem);
                        });
                    }
                  }
                }

            };
            return cell;
        });

	}

	public void redoInit(){

		tableRedo.setCellFactory((col) -> {
            TableCell<SalesVO, String> cell = new TableCell<SalesVO, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    for(int i=0;i< this.getTableView().getItems().size();i++){
                   BillState clickedState = this.getTableView().getItems().get(i).getState();
                    if (!empty&&(clickedState == BillState.DRAFT||clickedState == BillState.FAIL)) {
                        Button delBtn = new Button("����");
                        this.setGraphic(delBtn);
                        delBtn.setOnMouseClicked((me) -> {
                        	SalesVO clickedItem = this.getTableView().getItems().get(this.getIndex());
                            try {
								changeStage("SalesMakeBillUI",user,clickedItem.getType(),null,clickedItem);
							} catch (Exception e) {
								e.printStackTrace();
							}
                        });
                    }
                    }
                }

            };
            return cell;
        });


	}


	public void deleteInit(){
		tableDelete.setCellFactory((col) -> {
            TableCell<SalesVO, String> cell = new TableCell<SalesVO, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    for(int i=0;i< this.getTableView().getItems().size();i++){
                    BillState clickedState = this.getTableView().getItems().get(i).getState();
                    if (!empty&&(clickedState == BillState.DRAFT||clickedState == BillState.FAIL)) {
                        Button delBtn = new Button("ɾ��");
                        this.setGraphic(delBtn);
                        delBtn.setOnMouseClicked((me) -> {
                        	SalesVO clickedItem = this.getTableView().getItems().get(this.getIndex());
                        	service.delete(clickedItem);
                            list.remove(clickedItem);
                            table.setItems(list);

                        });
                    }
                }
              }
            };
            return cell;
        });
	}

	public void listInit(){
		commodityID.setCellValueFactory(
                new PropertyValueFactory<CommodityItemVO,String>("id"));
		commodityName.setCellValueFactory(
                new PropertyValueFactory<CommodityItemVO,String>("name"));
		commodityModel.setCellValueFactory(
                new PropertyValueFactory<CommodityItemVO,String>("model"));
		commodityNumber.setCellValueFactory(
                new PropertyValueFactory<CommodityItemVO,Integer>("number"));
		commodityPrice.setCellValueFactory(
                new PropertyValueFactory<CommodityItemVO,Double>("price"));
		commodityMoney.setCellValueFactory(
                new PropertyValueFactory<CommodityItemVO,Double>("total"));
		commodityNote.setCellValueFactory(
                new PropertyValueFactory<CommodityItemVO,String>("remark"));
	}


}