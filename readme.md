# Install backend

## Install Database

Thi part will guide you how to install Postgres DB on you local computer.
If you already installed it, check that you know it's `username, password, port, DB name`. and skip to IntelliJ installation

### **Using docker**

1. Download docker from [docker website](https://www.docker.com/)
2. After that initialize Postgres official image

```bash
docker run -d \
	--name some-postgres \
	-e POSTGRES_PASSWORD=mysecretpassword \
    -p 5432:5432
```

3. Now your done and the server is running in the background connection creds are

```
    username: postgres
    password: mysecretpassword
    jdbc-url: jdbc:postgresql://localhost:5432/

```

### **Using local server**

[video guide](https://www.youtube.com/watch?v=0n41UTkOBb0)

1. Download Postgres binaries for your OS from [postgres download](https://www.postgresql.org/download/)
2. run the installer with the default setting (keep the installation directory)
3. you will be prompt to set port and password please remember what you chosen
4. when finish it might ask to install the stack builder, it is recommend but not a must
5. run `pgAdmin4` that you have installed
6. You'll be prompt to put the password from the installation
7. if you follow the guide you will create ned db named NewDB
8. you r creds will be this

```
if you changed some thing during insolation
    username: postgres
    password: ${password from the installation}
    jdbc-url: jdbc:postgresql://localhost:${port from the installation}/${DB you created}
else
    username: postgres
    password: ${password from the installation}
    jdbc-url: jdbc:postgresql://localhost:5432/
```

## Install IntelliJ

### [Download link](https://www.jetbrains.com/idea/download/)

### [Video link](TODO:somelinkhere)

We recommend using the ultimate version that is free for `30-day trial` and for students and educator are free using [`github pro for students`](https://education.github.com/pack).

### **Using zipped project**

1. extract the zip to a comfortable place
2. open the the extracted folder with IntelliJ
3. It might prompt you to download JDK17.
4. Sync all of maven packages
5. In the project explorer (usually on the left side) in navigate to
   `src->main->resources` and open `application.properties` here we change our database creds

```
spring.datasource.username=${Your Postgres username}
spring.datasource.password=${Your Postgres password}
spring.datasource.url=${Your Postgres jdbc link}
```

6. two ways to run:

    1. run the server navigate to `src->main->java` open `Demo1Application` and run the main function.

    2. Run in the root folder of the project

       _Linux/Mac_

       ```
       ./mvnw spring-boot:run
       ```

       _Windows_

       ```
       mvnw.exe spring-boot:run
       ```

### **Adding data to the database**

After running the backend server once all the tables will be created.
Under the `tableFiller` folder you will have multiple sql scripts that populate the db with data. see video to how to run them inside the IntelliJ
