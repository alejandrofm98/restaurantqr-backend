Configuramos el archivo application.properties basandonos en el application-example.properties
Rellenaremos los datos vacios con los valores del entorno en que se encuentre, proximamente estos
datos se encontraran en bitwarden.click2eat.es

Copiamos el archivo `.env.example` y creamos archivo `.env`. Nos aseguramos de que las variables tengan su valor
correspondiente

````text
MYSQL_USER=administrador
MYSQL_PASSWORD=administrador
MYSQL_PORT=3306
MYSQL_ROOT_PASSWORD=rootpassword
MYSQL_DATABASE=restaurante
````


Inicializar docker
```shell
docker-compose up -d
```

Eliminar los contenedores

`````shell
docker-compose down -v
`````
