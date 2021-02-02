package ru.devvault.tttracker.service;

import java.util.List;
import ru.devvault.tttracker.entity.Company;
import ru.devvault.tttracker.util.Result;

public interface CompanyService {

    Result<Company> store(
            Integer idCompany,
            String companyName,
            String actionUsername);

    Result<Company> remove(Integer idCompany, String actionUsername);
    Result<Company> find(Integer idCompany, String actionUsername);
    Result<List<Company>> findAll(String actionUsername);
}