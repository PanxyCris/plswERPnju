package vo.billvo.purchasebillvo;

import java.util.ArrayList;
import dataenum.BillState;
import dataenum.BillType;
import dataenum.Warehouse;
import vo.billvo.BillVO;
import vo.commodityvo.CommodityItemVO;

public class PurchaseVO extends BillVO{

	private String supplier;  //��Ӧ��
	private Warehouse warehouse;
	private String warehouseString;
	private String operator;
	private ArrayList<CommodityItemVO> commodities;  //�����Ʒ�б�
	private Double sum;

	public PurchaseVO(String id, String supplier, Warehouse warehouse, String operator,
			ArrayList<CommodityItemVO> commodities, String note, Double sum, BillType type, BillState state) {
		super(id,type,state,note);
		this.supplier = supplier;
		this.warehouse = warehouse;
		this.operator = operator;
		this.commodities = commodities;
		this.sum = sum;
		warehouseString = warehouse.value;
	}

	public String getSupplier(){
		return supplier;
	}

	public Warehouse getWarehouse(){
		return warehouse;
	}

	public String getWarehouseString(){
		return warehouse.value;
	}

	public String getOperator(){
		return operator;
	}

	public ArrayList<CommodityItemVO> getCommodities(){
		return commodities;
	}

	public Double getSum(){
		return sum;
	}


}