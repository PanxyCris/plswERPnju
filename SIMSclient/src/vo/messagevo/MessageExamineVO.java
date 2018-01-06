package vo.messagevo;

import po.UserPO;

public class MessageExamineVO extends MessageVO{


	private String id; //���ݱ��
	private UserPO user; //�û�

	public MessageExamineVO(String messageID, String id,UserPO user) {
		super(messageID, getFormatInfo(id,user));
		this.setId(id);
		this.setUser(user);

	}

	public static String getFormatInfo(String id,UserPO user){
	   	 return user.getName()+"�����ύ�˵��ݣ�"+id+",��������";
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