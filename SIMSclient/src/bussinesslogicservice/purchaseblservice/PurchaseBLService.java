package bussinesslogicservice.purchaseblservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import dataenum.ResultMessage;
import vo.billvo.purchasebillvo.PurchaseVO;
import vo.commodityvo.CommodityItemVO;
/**
* 类说明   进货
*
* @author lijie
* @date 2017年10月22日
*/
public interface PurchaseBLService {

	public String getPurchaseID();
	public String getPurChaseBackID();

    public void delete(PurchaseVO info);

	public ResultMessage submit(PurchaseVO Info);

	public ResultMessage save(PurchaseVO Info);

	public ResultMessage isLegal(CommodityItemVO vo);

	/**
	 * 显示单据
	 */
	public ArrayList<PurchaseVO> show();
	
}
