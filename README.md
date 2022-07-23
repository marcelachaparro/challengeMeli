# Mutantes - Challenge Meli


Esta API REST se encuentra hosteada en Amazon AWS, para hacer uso de ella, por favor ingresar al swagger de la siguiente ruta:
http://44.201.191.8:8080/swagger-ui/

La API dispone de cuatro servicios en el mutant-controller:

* /api/v1/mutant: Recibe una cadena ADN para determinar si esta corresponde a la de un mutante y adicional guarda en base de datos esta informacion.

* /api/v1/stats: Obtiene las estadisticas de las verificaciones de ADN hechos a través del endpoint /mutant

* /api/v1/retrieveHumans: Retorna todos los humanos (dna y su clasificacion como mutante o no)

* /api/v1/removeAllHumans: Remueve todos los humanos que han sido verificados

Nota: Solo para facilidad al operar la API, se creó los dos ultimos servicios (retrieveHumans y removeAllHumans)

Se utilizó Redis como base de datos de almacenamiento en memoria (in memory key-value).
 
Se utilizó Spring Data Redis, (parte de la familia de Spring Data) para la configuracion y el acceso a Redis.