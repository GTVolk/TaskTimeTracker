drop database if exists task_time_tracker;
create database task_time_tracker;
use task_time_tracker;
create table ttt_company(
    id_company      int unsigned not null auto_increment,
    company_name    varchar(200) not null,
    primary key(id_company)
);

insert into ttt_company(company_name) values ('DevVault');
insert into ttt_company(company_name) values ('IBS');

create table ttt_project(
    id_project      int unsigned not null auto_increment,
    project_name    varchar(200) not null,
    id_company      int unsigned not null,
    primary key(id_project),
    foreign key(id_company) references ttt_company(id_company)
);

insert into ttt_project(project_name, id_company) values ('DevVault site', 1);
insert into ttt_project(project_name, id_company) values ('DevVault blog', 1);
insert into ttt_project(project_name, id_company) values ('Book reading ', 1);
insert into ttt_project(project_name, id_company) values ('Pet project', 2);
insert into ttt_project(project_name, id_company) values ('Revizor tasks', 2);
insert into ttt_project(project_name, id_company) values ('Conf reading', 2);

create table ttt_task(
    id_task         int unsigned not null auto_increment,
    id_project      int unsigned not null,      
    task_name       varchar(200) not null,
    primary key(id_task),
    foreign key(id_project) references ttt_project(id_project)
);

insert into ttt_task(id_project, task_name)values (1, 'Fix errors');
insert into ttt_task(id_project, task_name)values (1, 'Make new links');
insert into ttt_task(id_project, task_name)values (1, 'Migrate to Gatsby 2.0');

insert into ttt_task(id_project, task_name)values (2, 'New content about projects');
insert into ttt_task(id_project, task_name)values (2, 'New content about IT skills');
insert into ttt_task(id_project, task_name)values (2, 'New content about me');

insert into ttt_task(id_project, task_name)values (3, 'Read spring books');

insert into ttt_task(id_project, task_name)values (4, 'Idea');
insert into ttt_task(id_project, task_name)values (4, 'Realization');
insert into ttt_task(id_project, task_name)values (4, 'Tests');

insert into ttt_task(id_project, task_name)values (5, 'Task 32315');
insert into ttt_task(id_project, task_name)values (5, 'Java dev with tests');

insert into ttt_task(id_project, task_name)values (6, 'Read about migrated checks');
insert into ttt_task(id_project, task_name)values (6, 'Read about TDD');

create table ttt_user(
    username        varchar(10) not null,
    first_name      varchar(100) not null,
    last_name       varchar(100) not null,
    email           varchar(100) not null unique,
    password        varchar(100) not null,
    admin_role      char(1) default 'N',
    primary key(username)
);

insert into ttt_user(username, first_name, last_name, email, password, admin_role) 
    values ('admin', 'DevVault', 'Admin', 'admin@devvault.ru', 'admin', 'Y');
insert into ttt_user(username, first_name, last_name, email, password, admin_role) 
    values ('max', 'Max', 'Vorobyev', 'max@devvault.ru', 'Vfrcbv', 'N');

create table ttt_task_log(
    id_task_log         int unsigned not null auto_increment,
    id_task             int unsigned not null,
    username            varchar(10) not null,
    task_description    varchar(2000) not null,
    task_log_date       date not null,
    task_minutes        int unsigned not null,
    primary key(id_task_log),
    foreign key(id_task) references ttt_task(id_task),
    foreign key(username) references ttt_user(username)
);

insert into ttt_task_log (id_task, username, task_description, task_log_date,task_minutes)
values(1,'admin','Fixed most errors',now(), 120);
insert into ttt_task_log (id_task, username, task_description, task_log_date,task_minutes)
values(2,'admin','Created some new links',now(), 240);
insert into ttt_task_log (id_task, username, task_description, task_log_date,task_minutes)
values(3,'admin','Prepare environment for migration',now(), 90);
insert into ttt_task_log (id_task, username, task_description, task_log_date,task_minutes)
values(3,'admin','Updated NPM dependencies',now(), 180);

insert into ttt_task_log (id_task, username, task_description, task_log_date,task_minutes)
values(1,'max','Fix error with Google Analytics',now(), 340);
insert into ttt_task_log (id_task, username, task_description, task_log_date,task_minutes)
values(7,'max','Finished reading book',now(), 140);
insert into ttt_task_log (id_task, username, task_description, task_log_date,task_minutes)
values(8,'max','Idea about Time Tracker',now(), 450);
insert into ttt_task_log (id_task, username, task_description, task_log_date,task_minutes)
values(9,'max','Created Pet project',now(), 600);
