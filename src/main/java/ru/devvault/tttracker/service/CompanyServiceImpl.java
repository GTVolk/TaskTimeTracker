package ru.devvault.tttracker.service;

import ru.devvault.tttracker.dao.CompanyDao;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.devvault.tttracker.entity.Company;
import ru.devvault.tttracker.entity.User;
import ru.devvault.tttracker.util.Result;
import ru.devvault.tttracker.util.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
@Service("companyService")
public class CompanyServiceImpl extends AbstractService implements CompanyService {

    @Autowired
    protected CompanyDao companyDao;

    public CompanyServiceImpl() {
        super();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Result<Company> find(Integer idCompany, String actionUsername) {
        if (isValidUser(actionUsername)) {
            return ResultFactory.getSuccessResult(companyDao.find(idCompany));
        } else {
            return ResultFactory.getFailResult(USER_INVALID);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Result<Company> store(
            Integer idCompany,
            String companyName,
            String actionUsername
    ) {

        User actionUser = userDao.find(actionUsername);

        if (!actionUser.isAdmin()) {
            return ResultFactory.getFailResult(USER_NOT_ADMIN);
        }

        Company company;
        if (idCompany == null) {
            company = new Company();
        } else {

            company = companyDao.find(idCompany);
            if (company == null) {
                return ResultFactory.getFailResult("Unable to find company instance with ID=" + idCompany);
            }
        }

        company.setCompanyName(companyName);

        if (company.getId() == null) {
            companyDao.persist(company);
        } else {
            company = companyDao.merge(company);
        }

        return ResultFactory.getSuccessResult(company);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override    
    public Result<Company> remove(Integer idCompany, String actionUsername) {

        User actionUser = userDao.find(actionUsername);
        
        if (!actionUser.isAdmin()) {
            return ResultFactory.getFailResult(USER_NOT_ADMIN);
        }

        if (idCompany == null) {
            return ResultFactory.getFailResult("Unable to remove Company [null idCompany]");
        } 

        Company company = companyDao.find(idCompany);

        if (company == null) {
            return ResultFactory.getFailResult("Unable to load Company for removal with idCompany=" + idCompany);
        } else {
            if (company.getProjects() == null || company.getProjects().isEmpty()) {

                companyDao.remove(company);

                String msg = "Company " + company.getCompanyName() + " was deleted by " + actionUsername;
                logger.info(msg);

                return ResultFactory.getSuccessResultMsg(msg);
            } else {
                return ResultFactory.getFailResult("Company has projects assigned and could not be deleted");
            }
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Result<List<Company>> findAll(String actionUsername) {
        if (isValidUser(actionUsername)) {
            return ResultFactory.getSuccessResult(companyDao.findAll());
        } else {
            return ResultFactory.getFailResult(USER_INVALID);
        }
    }
}
