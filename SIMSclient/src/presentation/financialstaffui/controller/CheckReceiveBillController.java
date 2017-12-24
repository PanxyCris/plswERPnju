package presentation.financialstaffui.controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import bussinesslogic.accountbillbl.ReceiptBillController;
import bussinesslogic.examinebl.ExamineReceiptBL;
import bussinesslogic.memberbl.MemberController;
import bussinesslogic.tablebl.BussinessHistoryScheduleReceiveBL;
import bussinesslogicservice.accountbillblservice.ReceiptBillBLService;
import bussinesslogicservice.checktableblservice.BusinessHistoryScheduleBLService;
import bussinesslogicservice.examineblservice.ExamineBLService;
import bussinesslogicservice.memberblservice.MemberBLService;
import dataenum.BillType;
import dataenum.ResultMessage;
import dataenum.findtype.FindBillType;
import dataenum.findtype.FindReceiptBillType;
import dataenum.findtype.FindSaleScheduleType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import presentation.common.EditingCell;
import presentation.common.EditingCellChoice;
import presentation.common.EditingCellDouble;
import presentation.generalmanagerui.controller.BussinessProcessTableController;
import presentation.remindui.RemindPrintUI;
import vo.billvo.financialbillvo.AccountListVO;
import vo.billvo.financialbillvo.ReceiptBillVO;
import vo.billvo.inventorybillvo.InventoryBillVO;
import vo.uservo.UserVO;

public class CheckReceiveBillController extends BussinessProcessTableController{

	BusinessHistoryScheduleBLService<ReceiptBillVO> service = new BussinessHistoryScheduleReceiveBL();
	ObservableList<ReceiptBillVO> list = FXCollections.observableArrayList();
	ObservableList<AccountListVO> accountVOList = FXCollections.observableArrayList();

	@FXML
	ChoiceBox<String> findChoice;
	@FXML
	TextField findingField;

	@FXML
	DatePicker startPicker;
	@FXML
	DatePicker endPicker;


	@FXML
	TableView<ReceiptBillVO> table;
	@FXML
	TableColumn<ReceiptBillVO,String> tableID;
	@FXML
	TableColumn<ReceiptBillVO,String> tableMember;
	@FXML
	TableColumn<ReceiptBillVO,Double> tableSum;
	@FXML
	TableColumn<ReceiptBillVO,String> tableOperator;
	@FXML
	TableColumn<ReceiptBillVO,String> tableRemark;
	@FXML
	TableColumn<ReceiptBillVO,String> tableCheck;
	@FXML
	TableColumn<ReceiptBillVO,CheckBox> tableRed;


	@FXML
	TableView<AccountListVO> accountList;
	@FXML
	TableColumn<AccountListVO,String> tableAccount;
	@FXML
	TableColumn<AccountListVO,String> tableMoney;
	@FXML
	TableColumn<AccountListVO,String> tableDescription;

	@FXML
	public void red(){
		ArrayList<ReceiptBillVO> result = new ArrayList<>();
		for(int i=0;i<list.size();i++)
			if(list.get(i).getRed().isSelected())
				result.add(list.get(i));
		service.writeOff(result);
		list.removeAll(result);
	}

	@FXML
	public void redCopy(){
		ArrayList<ReceiptBillVO> result = new ArrayList<>();
		for(int i=0;i<list.size();i++)
			if(list.get(i).getRed().isSelected())
				result.add(list.get(i));
		service.writeOffAndCopy(result);
		list.removeAll(result);
	}


	@FXML
	public void printout(){
		ArrayList<ReceiptBillVO> result = new ArrayList<>();
		result.addAll(list);
		service.exportReport(result);
	}

	@FXML
	public void siftTime(){
		list.clear();
		ArrayList<ReceiptBillVO> siftList = service.siftTime(startPicker.getValue(), endPicker.getValue());
		list.addAll(siftList);
		table.setItems(list);
	}

	@FXML
	public void find(){

		ArrayList<ReceiptBillVO> list = service.sift(findingField.getText(),FindSaleScheduleType.getType(findChoice.getValue()));
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

	public void initData(UserVO user) throws RemoteException {
		this.user = user;
		list.addAll(service.show());
		table.setItems(list);
		manageInit();
		listInit();
		findChoice.setItems(FXCollections.observableArrayList(FindSaleScheduleType.MEMBER.value,
				FindSaleScheduleType.OPERATOR.value));
	}



	public void manageInit() throws RemoteException{
		tableID.setCellValueFactory(
                new PropertyValueFactory<ReceiptBillVO,String>("id"));
		tableMember.setCellValueFactory(
                new PropertyValueFactory<ReceiptBillVO,String>("customerID"));
		tableSum.setCellValueFactory(
                new PropertyValueFactory<ReceiptBillVO,Double>("total"));
		tableOperator.setCellValueFactory(
                new PropertyValueFactory<ReceiptBillVO,String>("userID"));
		tableRemark.setCellValueFactory(
                new PropertyValueFactory<ReceiptBillVO,String>("note"));
		tableRed.setCellValueFactory(
                new PropertyValueFactory<ReceiptBillVO,CheckBox>("red"));
		checkInit();
	}

	public void checkInit(){

		tableCheck.setCellFactory((col) -> {
            TableCell<ReceiptBillVO, String> cell = new TableCell<ReceiptBillVO, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);

                    if (!empty) {
                        Button delBtn = new Button("查看转账列表");
                        this.setGraphic(delBtn);
                        delBtn.setOnMouseClicked((me) -> {
                        	ReceiptBillVO clickedItem = this.getTableView().getItems().get(this.getIndex());
                            accountVOList.clear();
                            accountVOList.addAll(clickedItem.getAccountListVOs());
                            accountList.setItems(accountVOList);

                        });
                    }
                }

            };
            return cell;
        });

	}

	public void listInit(){
		tableAccount.setCellValueFactory(
                new PropertyValueFactory<AccountListVO,String>("accountID"));
        tableMoney.setCellValueFactory(
                new PropertyValueFactory<AccountListVO,String>("money"));
        tableDescription.setCellValueFactory(
                new PropertyValueFactory<AccountListVO,String>("note"));
	}


}
