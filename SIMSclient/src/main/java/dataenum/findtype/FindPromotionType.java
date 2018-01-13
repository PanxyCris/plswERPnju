package dataenum.findtype;
/**
*
* @author Lijie
* @date 2017��12��9��
*/
public enum FindPromotionType {

	ID("�������"),
	TIMEINTERVAL("ʱ������"),
	TYPE("��������");

	public final String value;
	private FindPromotionType(String value) {
		this.value = value;
	}

	public static FindPromotionType getType(String value){
		switch(value){
		case "�������":return ID;
		case "ʱ������":return TIMEINTERVAL;
		default:return TYPE;
		}
	}

}