package bussinesslogic.classificationbl;

import java.rmi.RemoteException;
import java.util.ArrayList;

import bussinesslogicservice.commodityblservice.ClassificationBLService;
import dataenum.ResultMessage;
import dataservice.classificationdataservice.ClassificationDataService;
import po.ClassificationVPO;
import rmi.RemoteHelper;

public class ClassificationBL implements ClassificationBLService {

	private ClassificationDataService classificationDataService;

	public ClassificationBL() {
		classificationDataService = RemoteHelper.getInstance().getClassificationDataService();
	}

	/**
	 * 用于生成新创建的商品分类的id
	 */

	@Override
	public String getID() {
		try {
			return classificationDataService.getId();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<String> showName() {
		ArrayList<String> names = new ArrayList<>();
		try {
			ArrayList<ClassificationVPO> all = classificationDataService.show();
			for (ClassificationVPO vpo : all) {
				if (vpo.getB()) {
					if (vpo.getChildrenPointer() == null)
						names.add(vpo.getName());
					else if (vpo.getChildrenPointer().isEmpty())
						names.add(vpo.getName());
					else if (getClass(vpo.getChildrenPointer().get(0)).getB())
						names.add(vpo.getName());
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return names;
	}

	@Override
	public ResultMessage insert(ClassificationVPO vpo) {
		try {
			// 先更新vpo父节点的子节点指针
			ClassificationVPO father = getClass(vpo.getFather());
			ArrayList<String> children = new ArrayList<>();
			if (father.getChildrenPointer() != null && father.getChildrenPointer().size() != 0)
				children = father.getChildrenPointer();
			children.add(vpo.getName());
			father.setChildrenPointer(children);
			update(father);
			return classificationDataService.insertClassification(vpo);
		} catch (RemoteException e) {
			e.printStackTrace();

		}
		return ResultMessage.FAIL;
	}

	@Override
	public void delete(ClassificationVPO vpo) {
		String id = vpo.getName();
		// 先更新vpo父节点的子节点指针
		ClassificationVPO father = getClass(vpo.getFather());
		ArrayList<String> children = father.getChildrenPointer();
		children.remove(vpo.getName());
		father.setChildrenPointer(children);
		update(father);
		try {
			classificationDataService.deleteClassification(id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ResultMessage update(ClassificationVPO vpo) {
		try {
			ClassificationVPO father = getClass(vpo.getFather());
			for(int i = 0;i<father.getChildrenPointer().size();i++){
				String id = classificationDataService.findClassification(father.getChildrenPointer().get(i)).getId();
				if(id.equals(vpo.getId())){
					ArrayList<String> children = father.getChildrenPointer();
					children.set(i, vpo.getName());
					father.setChildrenPointer(children);
					classificationDataService.updateClassification(father);
					break;
				}
			}
			return classificationDataService.updateClassification(vpo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return ResultMessage.FAIL;
	}

	@Override
	public ClassificationVPO getClass(String name) {
		try {
			return classificationDataService.findClassification(name);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ClassificationVPO getRoot() {
		try {
			ClassificationVPO root = classificationDataService.getRoot();
			addChildren(root);
			return root;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 运行时生成树形结构
	 *
	 * @param vpo
	 *            节点
	 * @throws RemoteException
	 */

	public void addChildren(ClassificationVPO vpo) throws RemoteException {
		ArrayList<ClassificationVPO> children = new ArrayList<>();
		ArrayList<ClassificationVPO> vpos = classificationDataService.show();
		for (ClassificationVPO po : vpos) {
			if (po.getFather() != null && po.getFather().equals(vpo.getName()))
				children.add(po);
		}
		if (children != null) {
			vpo.setChildren(children);
			for (ClassificationVPO child : vpo.getChildren())
				addChildren(child);
		}
	}

}
