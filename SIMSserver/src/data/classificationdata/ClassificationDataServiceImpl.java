package data.classificationdata;

import java.rmi.RemoteException;
import java.util.ArrayList;

import dataenum.ResultMessage;
import dataservice.classificationdataservice.ClassificationDataService;
import po.ClassificationVPO;

/**
*
* @author Lijie
* @date 2017��12��14��
*/
public class ClassificationDataServiceImpl implements ClassificationDataService{

	private ClassificationData classfication;

	public ClassificationDataServiceImpl() {
		classfication = new ClassificationData();
	}

	@Override
	public ResultMessage insertClassification(ClassificationVPO po) throws RemoteException {
		return classfication.insert(po);
	}

	@Override
	public ResultMessage updateClassification(ClassificationVPO po) throws RemoteException {
		return classfication.update(po);
	}

	@Override
	public ResultMessage deleteClassification(String id) throws RemoteException {
		return classfication.delete(id);
	}


	@Override
	public ClassificationVPO getRoot() throws RemoteException {
		return classfication.getRoot();
	}

	@Override
	public ClassificationVPO findClassification(String keyword) throws RemoteException {
		return classfication.findClassification(keyword);
	}

	@Override
	public String getId() throws RemoteException {
		if(classfication.show()!=null){
		String oldId = classfication.show().get(classfication.show().size()-1).getId();
		int count = Integer.parseInt(oldId);
		count++;
		String newId = String.valueOf(count);
		while(newId.length()<oldId.length())
			newId = "0"+newId;
		return newId;
		}
		else
		return "0001";
	}

}
