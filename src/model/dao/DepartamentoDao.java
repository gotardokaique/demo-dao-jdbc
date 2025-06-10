package model.dao;

import model.entities.Department;

public interface DepartamentoDao {
	
	void insert (Department obj);
	void update (Department obj);
	void deleteById (Integer id);
	Department findById(Integer id);




}
