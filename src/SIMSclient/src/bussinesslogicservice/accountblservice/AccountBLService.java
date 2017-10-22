package SIMSclient.src.bussinesslogicservice.accountblservice;

import SIMSclient.src.po.AccountPO;

public interface AccountBLService {
	
	public AccountPO check(String message);//message是用户查找ID时输入的信息，经过逻辑处理后返回一个AccountPO对象
	
	public AccountPO newBuild();//新建一个账户,返回一个AccountPO对象
	
	public boolean delete(AccountPO accountPO);//删除
	
	public boolean modify(String name);//修改
	
}
