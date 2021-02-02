package ru.devvault.tttracker.service;

import ru.devvault.tttracker.dao.CompanyDao;
import ru.devvault.tttracker.dao.ProjectDao;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.devvault.tttracker.entity.Company;
import ru.devvault.tttracker.entity.Project;
import ru.devvault.tttracker.entity.User;
import ru.devvault.tttracker.util.Result;
import ru.devvault.tttracker.util.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
@Service("projectService")
public class ProjectServiceImpl extends AbstractService implements ProjectService {

    @Autowired
    protected CompanyDao companyDao;    
    @Autowired
    protected ProjectDao projectDao;

    public ProjectServiceImpl() {
        super();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Result<Project> find(Integer idProject, String actionUsername) {

        if(isValidUser(actionUsername)) {

            Project project = projectDao.find(idProject);
            return ResultFactory.getSuccessResult(project);

        } else {
            return ResultFactory.getFailResult(USER_INVALID);
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Result<Project> store(
        Integer idProject,
        Integer idCompany,
        String projectName,
        String actionUsername) {

        User actionUser = userDao.find(actionUsername);
        
        if (!actionUser.isAdmin()) {

            return ResultFactory.getFailResult(USER_NOT_ADMIN);

        }

        Project project;
        Company company = companyDao.find(idCompany);

        if (idProject == null) {
            project = new Project();

            if(company == null){

                return ResultFactory.getFailResult("Unable to add new project without a valid company [idCompany=" + idCompany + "]");

            } else {
                project.setCompany(company);
                company.getProjects().add(project);
            }

        } else {
            project = projectDao.find(idProject);

            if(project == null) {

                return ResultFactory.getFailResult("Unable to find project instance with ID=" + idProject);

            } else {

                if(company != null){
                    if( ! project.getCompany().equals(company)){

                        Company currentCompany = project.getCompany();
                        project.setCompany(company);
                        company.getProjects().add(project);

                        currentCompany.getProjects().remove(project);
                    }
                }
            }
        }

        project.setProjectName(projectName);

        if(project.getIdProject() == null) {

            projectDao.persist(project);

        } else {

            project = projectDao.merge(project);

        }

        return ResultFactory.getSuccessResult(project);

    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Result<Project> remove(Integer idProject, String actionUsername){

        User actionUser = userDao.find(actionUsername);
        
        if (!actionUser.isAdmin()) {

            return ResultFactory.getFailResult(USER_NOT_ADMIN);

        }

        if(idProject == null){

            return ResultFactory.getFailResult("Unable to remove Project with a null idProject");

        } 

        Project project = projectDao.find(idProject);

        if(project == null) {

            return ResultFactory.getFailResult("Unable to load Project for removal with idProject=" + idProject);

        } else if (project.getTasks() != null && ! project.getTasks().isEmpty() ) {
            return ResultFactory.getFailResult("Unable to remove Project with idProject=" + idProject + " as valid tasks are assigned");

        } else {
            Company company = project.getCompany();

            projectDao.remove(project);
            company.getProjects().remove(project);

            String msg = "Project " + project.getProjectName() + " was deleted by " + actionUsername;
            logger.info(msg);
            return ResultFactory.getSuccessResultMsg(msg);

        }

    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Result<List<Project>> findAll(String actionUsername){

        if(isValidUser(actionUsername)){

            return ResultFactory.getSuccessResult(projectDao.findAll());

        } else {

            return ResultFactory.getFailResult(USER_INVALID);

        }

    }
}
