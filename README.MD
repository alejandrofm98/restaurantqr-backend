## Click2eat

# Configuracion

El proyecto tiene los siguientes archivos de configuracion, los cuales deberemos crear basandonos en los -example o
deberemos obtenerlos del sistema de cifrado de estos ficheros:
````text
src/main/resources/application.properties
src/main/resources/firebase-service-account.json
docker/.env
````
Tenemos 2 opciones de ejecución, como una spring boot app o como docker y remote jvm debug. 
<br>
Ambas opciones están configuradas para intellij en la carpeta .run del repositorio.
<br>
## Spring boot app
Para este caso solo ejecutaremos la bd del docker compose.
```shell
cd docker
docker compose up -d --build db
```
Necesitaremos java 21 en nuestro caso amazon correto.
<br>
Settings -> Project Structure
![image](https://github.com/user-attachments/assets/26a8b484-365f-4e7a-b90c-47878ed8381f)

Run -> Edit configurations
![image](https://github.com/user-attachments/assets/10d0d97c-be23-49f6-9a64-2d6b4d491a9f)


## App dockerizada con Remote Debug
Inicializar el docker y ejecutar.


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
Y por último configuramos el remote debug y ya funcionará correctamente.
![image](https://github.com/user-attachments/assets/5c179fc1-a92c-4450-bce1-271f64732833)
Para ver los logs iremos a Services -> seleccionamos el contenedor en este caso app y vemos la pestaña Log
![image](https://github.com/user-attachments/assets/1350dfe1-eb69-43b5-9bd7-41d59c78d6a5)


# Apuntar a Servidores
Si queremos utilizar la bd de pro o dev deberemos añadir spring.profiles.active=entorno en el 
application.properties (esto es lo que se hace para desplegar en los servidores).
<br>
![image](https://github.com/user-attachments/assets/6e75d4f6-23f3-4a58-a65a-1ea9f480bdb8)
<br>
Aparte de tener creado el fichero application-entorno.properties y el private_key correspondiente. 
Ambos ficheros estan en bitwarden.
<br>
![image](https://github.com/user-attachments/assets/2e8d2806-d5c0-4afe-a03d-ff1ff39559e2)
<br>
Por último si estamos usando docker también deberemos descomentar la línea del fichero docker-compose.yaml
,poner la ip del servidor correcta y comentar la línea de arriba.
<br>
![image](https://github.com/user-attachments/assets/a893c4d6-4fae-41bf-81f6-cc8993b5105f)
