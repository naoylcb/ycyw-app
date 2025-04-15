## YCYW POC

### Prerequisites

-   NodeJS 16+
-   Java JDK 17
-   Maven
-   MySQL

Git clone:

> git clone https://github.com/naoylcb/ycyw-app.git

Go inside front-end folder:

> cd ycyw-front

Install front-end dependencies:

> npm install

Launch front-end:

> npm start

Go inside back-end folder:

> cd ycyw-api

Install back-end dependencies:

> mvn clean install

Launch back-end:

> mvn spring-boot:run

### Database

Create a MySQL database and use `script.sql` to initialize the database.

Update the datasource in `application.properties` (back-end) and set these environnement variables on your system :
- YCYW_API_SERVER_PORT
- YCYW_API_DB_NAME
- YCYW_API_DB_PORT
- YCYW_API_DB_USER
- YCYW_API_DB_PASSWORDn
- YCYW_API_JWT_SECRET_KEY

By default the 2 accounts are :

-   login: john@test.fr
-   password: test

-   login: support@test.fr
-   password: test