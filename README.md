demo for using testcontainers on tests and also for local development in order to get rid of docker-compose file.
in order to run the app in development mode you need to run the main function inside test/java/.../TestApplication 
in this case you will use the testcontainers db and all stuff you want redis, kafka ... 
you will only need to add them to the LocalContainerConfiguration class.
And when you stop the aaplication all the running docker containers are going to automaticaly stop 
(the magic ryuk container will do house keeping run docker ps to check )
and if you want to persiste data in you're data base you can use the 
```container.withClasspathResourceMapping("application.properties","/tmp/application.properties", BindMode.READ_ONLY)```
```container.withFileSystemBind("c:/files path/application.properties","/tmp/application.properties", BindMode.READ_ONLY)```
to bind local data directory for our db instance as we do with docker-compose binding.
```
And finnaly to run the app on prod mode you run the normal main from TestcontainersDemoApplication.
```
