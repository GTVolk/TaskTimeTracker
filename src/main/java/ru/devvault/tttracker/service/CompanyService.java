package ru.devvault.tttracker.service;

import java.util.List;
import ru.devvault.tttracker.domain.Company;
import ru.devvault.tttracker.vo.Result;

public interface CompanyService {

    public Result<Company> store(
        Integer idCompany,
        String companyName,
        String actionUsername);

    public Result<Company> remove(Integer idCompany, String actionUsername);
    public Result<Company> find(Integer idCompany, String actionUsername);
    public Result<List<Company>> findAll(String actionUsername);

}