#!/bin/bash
git checkout config
git reset --hard HEAD
git pull origin config
awk 'BEGIN{FS=OFS="="}
     NR==FNR {key[$1]=$2 ; next }
     $1 in key {$2=key[$1]}
     1' ../../configuraciones/.env configuracion/desarrollo/application.concatenar.properties > temp

cat configuracion/produccion/application.properties temp > application.properties
cat application.properties
git checkout main
git pull origin main
mv application.properties  src/main/resources/