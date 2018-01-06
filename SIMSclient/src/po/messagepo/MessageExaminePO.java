package po.messagepo;

import po.UserPO;

public class MessageExaminePO extends MessagePO{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id; //单据编号
	private UserPO user; //用户

	public MessageExaminePO(String messageID, String id,UserPO user) {
		super(messageID, getFormatInfo(id,user));
		this.setId(id);
		this.setUser(user);

	}

	public static String getFormatInfo(String id,UserPO user){
	   	 return user.getName()+"向您提交了单据："+id+",请您审批";
	    }

	public UserPO getUser() {
		return user;
	}

	public void setUser(UserPO user) {
		this.user = user;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
