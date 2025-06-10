package model.dao;

import db.DB;
import model.dao.impl.VendedorDaoJDBC;

public class DaoFabrica {
	
	public static VendedorDao createVendedorDao() {
		return new VendedorDaoJDBC(DB.getConnection());
		
	}

}
