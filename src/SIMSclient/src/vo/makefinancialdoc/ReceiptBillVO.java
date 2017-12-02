package SIMSclient.src.vo.makefinancialdoc;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class ReceiptBillVO extends FinancialDocVO {
	private String docID;//单据编号
	private String userID;//当前登录用户的ID
	private ArrayList<String> accountName;//银行账户名，付款单，list里只会存在一个银行账户，但是为了减少代码，这里仍然用list存储
	private ArrayList<String> money;//转账总金额(transferAmount数值的总和)，使用list理由同accountName
	private String customerID;//客户ID

	// 条目清单
	private ArrayList<String> entryName;// 条目名
	private ArrayList<String> transferAmount;// 金额
	private String note;// 备注

	public ReceiptBillVO(String docID, String userID, ArrayList<String> accountName, ArrayList<String> money, String customerID,
			String note,ArrayList<String> entryName,ArrayList<String> transferAmount) {
		super(docID,userID,accountName,money,customerID,note);
		this.entryName=entryName;
		this.transferAmount=transferAmount;
	}

	public String getDocID() {
		return docID;
	}

	public void setDocID(String docID) {
		this.docID = docID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public ArrayList<String> getAccountName() {
		return accountName;
	}

	public void setAccountName(ArrayList<String> accountName) {
		this.accountName = accountName;
	}

	public ArrayList<String> getMoney() {
		return money;
	}

	public void setMoney(ArrayList<String> money) {
		this.money = money;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public ArrayList<String> getEntryName() {
		return entryName;
	}

	public void setEntryName(ArrayList<String> entryName) {
		this.entryName = entryName;
	}

	public ArrayList<String> getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(ArrayList<String> transferAmount) {
		this.transferAmount = transferAmount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}


}
