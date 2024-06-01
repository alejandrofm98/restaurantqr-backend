Configuramos el archivo application.properties basandonos en el application-example.properties
Rellenaremos los datos vacios con los valores del entorno en que se encuentre, proximamente estos
datos se encontraran en bitwarden.click2eat.es

````text
#Datos de la conexion
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=

#Envio de correos
spring.mail.host=smtp.ionos.es
spring.mail.port=587
spring.mail.username=
spring.mail.password=
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.servlet.multipart.max-file-size=10MB

#Auditoria de tablas
spring.sql.init.mode=always
spring.jpa.properties.org.hibernate.envers.default_catalog=auditory

#Autogenera tablas
spring.jpa.hibernate.ddl-auto=update

#Documentacion
springdoc.api-docs.path=/docs/api-docs
springdoc.swagger-ui.path=/docs/swagger-ui-custom.html

````

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
