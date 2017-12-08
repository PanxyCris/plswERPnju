package SIMSserver.src.data.userdata;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import SIMSserver.src.data.DBManager;
import SIMSserver.src.dataenum.ResultMessage;
import SIMSserver.src.dataenum.UserRole;
import SIMSserver.src.po.PurchasePO;
import SIMSserver.src.po.UserPO;

/**     
*  
* @author Lijie 
* @date 2017��12��7��    
*/
public class UserData {

	public ResultMessage insert(UserPO po) {
		Connection conn = DBManager.getConnection();// �����õ����ݿ������
		String sql = "" + "insert userrole values (?, ?)";
		try{
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, po.getID());
            ps.setObject(2, po);
            ps.executeUpdate();
            conn.commit();
            ps.close();
            conn.close();
            return ResultMessage.SUCCESS;
        }catch(SQLException e){
            e.printStackTrace();    
        }
		return ResultMessage.FAIL;
	}
	
	public ResultMessage delete(String id)  {
		Connection conn = DBManager.getConnection();
		String sql = "" + "delete from userrole where id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.execute();
			ps.close();
			conn.close();
			return ResultMessage.SUCCESS;
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultMessage.FAIL;
		}
	}
	
	public UserPO find(String id) {
		UserPO po = null;
		Connection conn = DBManager.getConnection();
		String sql = "" + "select * from userrole where id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				// �����Ƕ�ȡ�ķ���һ��Ҫע���ˣ�
				Blob inblob = (Blob) rs.getBlob("object");
				InputStream is = inblob.getBinaryStream();
				BufferedInputStream input = new BufferedInputStream(is);

				byte[] buff = new byte[(int) inblob.length()];// �ŵ�һ��buff �ֽ�����
				while (-1 != (input.read(buff, 0, buff.length)));

				ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buff));
				po = (UserPO) in.readObject();
			}
					
		}catch (SQLException | IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}  
		return po;
	}
	
	public ResultMessage update(UserPO po) {
		Connection conn = DBManager.getConnection();
		String sql = "" + "update userrole set object = ? where id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setObject(1, po);
			ps.setString(2, po.getID());
			ps.executeUpdate();
			ps.close();
			conn.close();
			return ResultMessage.SUCCESS;
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultMessage.FAIL;
		}
	}
	
	public ArrayList<UserPO> show() {
		ArrayList<UserPO> list = new ArrayList<>();
		Connection conn = DBManager.getConnection();
		String sql = "select object from user";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Blob inBlob = (Blob) rs.getBlob("object");   //��ȡblob���� 
				InputStream is = inBlob.getBinaryStream();                //��ȡ������������  
                BufferedInputStream bis = new BufferedInputStream(is);    //����������������  
                byte[] buff = new byte[(int) inBlob.length()];
                
                while(-1!=(bis.read(buff, 0, buff.length))){            //һ����ȫ������buff��  
                    ObjectInputStream in=new ObjectInputStream(new ByteArrayInputStream(buff));  
                    UserPO po = (UserPO)in.readObject();                   //��������  
                      
                    list.add(po);  
                }  
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException | IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}  
		return list;
		
	}

}