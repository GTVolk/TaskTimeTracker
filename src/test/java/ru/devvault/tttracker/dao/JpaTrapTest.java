package ru.devvault.tttracker.dao;

import ru.devvault.tttracker.entity.Company;
import ru.devvault.tttracker.entity.Project;
import ru.devvault.tttracker.entity.User;

import org.junit.Test;

import static org.junit.Assert.*;

public class JpaTrapTest extends AbstractDaoForTesting {

    @Test
    public void testManyToOne() throws Exception {

        logger.debug("\nSTARTED testManyToOne()\n");

        Company c = companyDao.findAll().get(0);
        Company c2 = companyDao.findAll().get(1);

        Project p = c.getProjects().get(0);

        p.setCompany(c2);
        p = projectDao.merge(p);

        c.getProjects().remove(p);
        c2.getProjects().add(p);

        assertFalse("Original company still has project in its collection!", c.getProjects().contains(p));
        assertTrue("Newly assigned company does not have project in its collection",
                c2.getProjects().contains(p));

        logger.debug("\nFINISHED testManyToOne()\n");

    }

    @Test
    public void testFindByUsernamePassword() throws Exception {

        logger.debug("\nSTARTED testFindByUsernamePassword()\n");

        String Password = "admin";
        String UserName = "admin";
        User user = userDao.findByUsernamePassword(UserName, Password);

        assertNotNull("Unable to find valid user with correct username/password combination", user);

        user = userDao.findByUsernamePassword(UserName, "ThisIsInvalidPassword!");

        assertNull("User found with invalid password", user);
        
        logger.debug("\nFINISHED testFindByUsernamePassword()\n");
    }

}
