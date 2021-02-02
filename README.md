# Task Time Tracker

Pet project for Enterprise Application Development with Ext JS and Spring with modern technologies

### Screenshot
![Screenshot](screenshot.PNG?raw=true "Screenshot")

## Requirements
 - Java Development Kit 1.8
 - Apache Maven 3.6.3
 - MySQL 8
 - Sencha CMD 7.3.0.59

## Running project
- Configure MySQL Database instance:
```mysql
CREATE USER 'tttadmin'@'localhost' IDENTIFIED BY 'tttpasswd';
CREATE DATABASE 'tttdb' CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci';
GRANT ALL PRIVILEGES ON 'tttdb'.* TO 'tttadmin'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;
```
- Change your database credentials in files ``jdbc.properties``
- Execute maven goal for package WAR file ```mvn clean package``` OR run it just in embedded Jetty ```mvn jetty:run```! All other dependencies automatically will be downloaded for you!
- You are breathtaking!

