## Click2eat

# Configuracion

El proyecto tiene los siguientes archivos de configuracion, los cuales deberemos crear basandonos en los -example o
deberemos obtenerlos del sistema de cifrado de estos ficheros:
````text
src/main/resources/application.properties
src/main/resources/firebase-service-account.json
docker/init/.env
````

Necesitaremos java 21 en nuestro caso amazon correto.

![image](https://github.com/user-attachments/assets/4e1851f5-056c-45c5-97fb-2f5f05488c44)


Una vez creados estos ficheros con sus datos deberemos inicializar el docker y ejecutar la applicacion como una 
spring-boot app


Inicializar docker, deberemos estar en la ruta del proyecto.
```shell
cd docker
docker compose up -d --build
```

Eliminar los contenedores y sus volomunes

`````shell
cd docker
docker compose down -v
`````
