package presentation.financialstaffui.controller;

import java.time.LocalDate;
import java.util.ArrayList;

import bussinesslogic.tablebl.BussinessSituationBL;
import bussinesslogic.tablebl.SaleScheduleBL;
import bussinesslogicservice.checktableblservice.BussinessSituationBLService;
import bussinesslogicservice.checktableblservice.SaleScheduleBLService;
import dataenum.ResultMessage;
import dataenum.findtype.FindSaleScheduleType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import presentation.remindui.RemindPrintUI;
import vo.tablevo.PaymentTableVO;
import vo.tablevo.ReceiveTableVO;
import vo.tablevo.SaleScheduleVO;
import vo.uservo.UserVO;

public class BussinessSituationTableController extends ViewTableController{

	BussinessSituationBLService service = new BussinessSituationBL();
	ObservableList<PaymentTableVO> payList = FXCollections.observableArrayList();
	ObservableList<ReceiveTableVO> receiveList = FXCollections.observableArrayList();

	@FXML
	DatePicker startPicker;
	@FXML
	DatePicker endPicker;

	@FXML
	Label revenue;
	@FXML
	Label allowance;
	@FXML
	Label expenditure;
	@FXML
	Label profit;

	@FXML
	TableView<ReceiveTableVO> receive;
	@FXML
	TableColumn<ReceiveTableVO,LocalDate> receiveTime;
	@FXML
	TableColumn<ReceiveTableVO,String> receiveType;
	@FXML
	TableColumn<ReceiveTableVO,Double> receiveAllowance;
	@FXML
	TableColumn<ReceiveTableVO,Double> receiveSum;


	@FXML
	TableView<PaymentTableVO> pay;
	@FXML
	TableColumn<PaymentTableVO,LocalDate> payTime;
	@FXML
	TableColumn<PaymentTableVO,String> payType;
	@FXML
	TableColumn<PaymentTableVO,Double> paySum;


	@FXML
	public void printout() throws Exception{
		ArrayList<PaymentTableVO> resultPay = new ArrayList<>();
		ArrayList<ReceiveTableVO> resultReceive = new ArrayList<>();
		resultPay.addAll(payList);
		resultReceive.addAll(receiveList);
         service.exportReport(resultPay,resultReceive);
	}

	@FXML
	public void siftTime(){

		ArrayList<PaymentTableVO> paylist = service.siftPay(startPicker.getValue(),endPicker.getValue());
		ArrayList<ReceiveTableVO> receivelist = service.siftReceive(startPicker.getValue(),endPicker.getValue());
	       if(paylist==null||receivelist==null){
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
	    	payList.clear();
	    	receiveList.clear();
	    	payList.addAll(paylist);
	   		receiveList.addAll(receivelist);
	   		pay.setItems(payList);
	   		receive.setItems(receiveList);
	   		updateSum();
	       }

	}



	public void initData(UserVO user) {
		this.user = user;
		payList.addAll(service.showPay());
		receiveList.addAll(service.showReceive());
		pay.setItems(payList);
		receive.setItems(receiveList);
        updateSum();
		manageInit();
	}

	public void manageInit(){
		payTime.setCellValueFactory(
                new PropertyValueFactory<PaymentTableVO,LocalDate>("date"));
		payType.setCellValueFactory(
                new PropertyValueFactory<PaymentTableVO,String>("typeString"));
		paySum.setCellValueFactory(
                new PropertyValueFactory<PaymentTableVO,Double>("sum"));

		receiveTime.setCellValueFactory(
                new PropertyValueFactory<ReceiveTableVO,LocalDate>("date"));
		receiveType.setCellValueFactory(
                new PropertyValueFactory<ReceiveTableVO,String>("typeString"));
		receiveAllowance.setCellValueFactory(
                new PropertyValueFactory<ReceiveTableVO,Double>("allowance"));
		receiveSum.setCellValueFactory(
                new PropertyValueFactory<ReceiveTableVO,Double>("sum"));
	}

	public void updateSum(){
		double reve = 0,allo = 0,pro = 0,exp = 0;
		for(int i=0;i<payList.size();i++)
			exp+=payList.get(i).getSum();
		for(int i=0;i<receiveList.size();i++){
			reve+=receiveList.get(i).getSum();
			allo+=receiveList.get(i).getAllowance();
		}
		pro = reve - exp;
		revenue.setText(String.valueOf(reve));
		allowance.setText(String.valueOf(allo));
		profit.setText(String.valueOf(pro));
		expenditure.setText(String.valueOf(exp));
	}


}