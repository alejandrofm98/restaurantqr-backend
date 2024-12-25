CREATE DATABASE IF NOT EXISTS restaurante;
CREATE DATABASE IF NOT EXISTS auditory;

GRANT ALL ON restaurante.* TO 'administrador'@'%';
GRANT ALL ON auditory.* TO 'administrador'@'%';