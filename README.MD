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

# Apuntar a Servidores
Si queremos utilizar la bd de pro o dev deberemos añadir spring.profiles.active=entorno en el 
application.properties (esto es lo que se hace para desplegar en los servidores).
![image](https://github.com/user-attachments/assets/6e75d4f6-23f3-4a58-a65a-1ea9f480bdb8)
Aparte de tener creado el fichero application-entorno.properties y el private_key correspondiente. 
Ambos ficheros estan en bitwarden.
![image](https://github.com/user-attachments/assets/2e8d2806-d5c0-4afe-a03d-ff1ff39559e2)
