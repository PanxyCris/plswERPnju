package presentation.inventorymanagerui.controller;

import java.util.ArrayList;
import bussinesslogic.billbl.inventory.InventoryBillController;
import bussinesslogic.commoditybl.CommodityBL;
import bussinesslogicservice.billblservice.inventory.InventoryBillBLService;
import bussinesslogicservice.commodityblservice.CommodityBLService;
import dataenum.BillState;
import dataenum.BillType;
import dataenum.Remind;
import dataenum.ResultMessage;
import dataenum.findtype.FindCommodityType;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import presentation.common.EditingCellInteger;
import vo.billvo.inventorybillvo.InventoryBillVO;
import vo.commodityvo.GiftVO;
import vo.uservo.UserVO;

public class MakeReceiptController extends InventoryManagerController{

    InventoryBillBLService service = new InventoryBillController();
    ObservableList<GiftVO> list = FXCollections.observableArrayList();
	public static final Remind remind = Remind.BILL;
	@FXML
	Label idLabel;
	@FXML
	Label operatorLabel;
    @FXML
    ComboBox<String> receiptChoice;
	@FXML
	TextArea noteArea;


	@FXML
	TableView<GiftVO> table;
	@FXML
	TableColumn<GiftVO,String> tableName;
	TableColumn<GiftVO,Integer> tableNumber;
	@FXML
	TableColumn<GiftVO,String> tableDelete;

	@FXML
	ChoiceBox<String> nameChoice;
	@FXML
	TextField numberField;

	@FXML
	public void save(){
		ArrayList<GiftVO> gifts = new ArrayList<>();
		gifts.addAll(list);
       InventoryBillVO vo = new InventoryBillVO(idLabel.getText(),gifts,operatorLabel.getText(),
    		   BillType.getType(receiptChoice.getValue()),BillState.DRAFT,noteArea.getText());
       ResultMessage message = service.save(vo);
       if(message == ResultMessage.SUCCESS){
           print(ResultMessage.SAVED);
           fresh();
           }
       else
    	   print(message);
	}

	@FXML
	public void submit(){
		ArrayList<GiftVO> gifts = new ArrayList<>();
		gifts.addAll(list);
       InventoryBillVO vo = new InventoryBillVO(idLabel.getText(),gifts,operatorLabel.getText(),
    		   BillType.getType(receiptChoice.getValue()),BillState.COMMITED,noteArea.getText());
       ResultMessage message = service.submit(vo);
       if(message == ResultMessage.SUCCESS){
           print(ResultMessage.COMMITED);
           fresh();
       }
       else
    	   print(message);
	}


	@FXML
	public void insert(){
		GiftVO vo = new GiftVO(nameChoice.getValue(),Integer.parseInt(numberField.getText()));
        list.add(vo);
        table.setItems(list);
	}

	@FXML
	public void checkBefore() throws Exception{
		changeStage("CheckReceiptUI",user,type,null);
	}

	@FXML
	public void fresh(){
	   idLabel.setText(null);
	   nameChoice.setValue(null);
       numberField.setText(null);
   	   receiptChoice.setValue(null);
	   noteArea.setText(null);
	   list.clear();
	   table.setItems(list);
	}

	public void initData(UserVO user,BillType type,InventoryBillVO inv) throws Exception{
		this.user = user;
		this.inv = inv;
		operatorLabel.setText(readUser().getName());
		if(type!=null)
			receiptChoice.setValue(type.value);
		fresh();
		if(inv!=null){
			idLabel.setText(inv.getId());
			receiptChoice.setValue(inv.getTypeString());
			noteArea.setText(inv.getNote());
			list.addAll(inv.getGifts());
			table.setItems(list);
		}
		choiceInit();
		edit();
		manageInit();

	}

	public void choiceInit() throws Exception{
		receiptChoice.setItems(FXCollections.observableArrayList(BillType.INVENTORYGIFTBILL.value,BillType.INVENTORYLOSSBILL.value,
				BillType.INVENTORYREVENUEBILL.value,BillType.INVENTORYWARNINGBILL.value));
		CommodityBLService commodityService = new CommodityBL();
		nameChoice.setItems(FXCollections.observableArrayList(commodityService.getIDandName()));
		receiptChoice.getSelectionModel().selectedItemProperty().addListener(
	        		(ObservableValue<? extends String> cl,String oldValue,String newValue)->{
	        			idLabel.setText(service.getId(BillType.getType(newValue)));
	        		});
	}


	public void manageInit(){
		tableName.setCellValueFactory(
                new PropertyValueFactory<GiftVO,String>("name"));

		tableNumber.setCellValueFactory(
                new PropertyValueFactory<GiftVO,Integer>("number"));
        deleteInit();
	}

	public void edit(){
	    Callback<TableColumn<GiftVO, Integer>,
		    TableCell<GiftVO, Integer>> cellFactoryInteger
		        = (TableColumn<GiftVO, Integer> p) -> new EditingCellInteger<GiftVO>();

	    tableNumber.setCellFactory(cellFactoryInteger);
	    tableNumber.setOnEditCommit(
	            (CellEditEvent<GiftVO, Integer> t) -> {
	                ((GiftVO) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).setNumber(t.getNewValue());

	        });

	}



	public void deleteInit(){
		tableDelete.setCellFactory((col) -> {
            TableCell<GiftVO, String> cell = new TableCell<GiftVO, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        Button delBtn = new Button("ɾ��");
                        this.setGraphic(delBtn);
                        delBtn.setOnMouseClicked((me) -> {
                        	GiftVO clickedItem = this.getTableView().getItems().get(this.getIndex());
                            list.remove(clickedItem);
                            table.setItems(list);

                        });
                    }

              }
            };
            return cell;
        });
	}

}
