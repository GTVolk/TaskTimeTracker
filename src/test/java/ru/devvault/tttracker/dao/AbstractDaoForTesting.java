/**
 * Base class for DAO layer testing.  Assumes the database is loaded
 * with valid data.
 */
package ru.devvault.tttracker.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration("/testingContext.xml")
public abstract class AbstractDaoForTesting extends AbstractTransactionalJUnit4SpringContextTests {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected CompanyDao companyDao;

    @Autowired
    protected ProjectDao projectDao;

    @Autowired
    protected TaskDao taskDao;

    @Autowired
    protected UserDao userDao;

    @Autowired
    protected TaskLogDao taskLogDao;

}