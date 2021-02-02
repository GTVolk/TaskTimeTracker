package ru.devvault.tttracker.dao;

import ru.devvault.tttracker.entity.Company;
import java.util.List;

public interface CompanyDao extends GenericDao<Company, Integer>{

    public List<Company> findAll();
    
}
