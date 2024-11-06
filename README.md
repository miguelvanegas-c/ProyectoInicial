# Proyecto Inicial

El proyecto es un simulador de un puzzle, cuenta con un solo tipo de movimiento que es el ladeo hacia arriba, abajo, derecha e izquierda.
El puzzle esta compuesto por diferentes tipo de baldosas, y diferentes tipos de pegantes que se le pueden agregar a las baldosas en cuestion.

## Pruebas De Aceptacion

Las pruebas de aceptacion son dos, las dos se encuentran en la clase Main y una se llama acceptanceTestSolveAndSimulateThePuzzleByMiguel() y la otra se llama acceptanceTestGlueByAllan(), las dos pruebas se van a correr consecutivamente. Se puede ejecutar desde consola parandose desde el directorio y con el comando (java -cp bin puzzle.Main) o desde un IDE ejecutando la clase Main.
La prueba acceptanceTestSolveAndSimulateThePuzzleByMiguel() quiere probar el requisito de funcionalidad en el que se exigia que el puzzle debia resolverse solo y simularse, por lo tanto se creo la siguiente historia:
El usuario desea probar que el puzzle en verdad puede llegar a una solucion, fuera de las propuestan en el testeo, por lo tanto decide crear un puzzle con baldosas bastante dispersas, despues de crearlo realiza varios movimiento y guarda la matriz del estado actual del puzzle. El usuario decide crear un puzzle contest para comprobar que va ser capaz de llegar de la matriz inicial a la actual del puzzle. No conforme aun desea probar mas el simulador, por lo tanto ahora el usuario probara algunos de los casos de prueba de la maraton de programacion del 2023. Despues de simularlo el usuario queda conforme con la funcionalidad de la simulacion ya que arrojo los mismos resultados que se exigian en la maraton. 



## Informe de pruebas unitarias
### Como correrlas.
Las pruebas se pueden ejecutar desde un IDE, existen dos clases de test una del puzzle, llamada PuzzleTest y otra del puzzle contest, llamada PuzzleContesTest.
### Como fueron hechas las pruebas unitarias.
Las pruebas unitarias fueron hechas para los metodos que se consideraron mas importantes probar y que se conocia mas podian fallar,
primero se probaban en condiciones perfectas, para probar que el metodo en estos casos tuviera el comportamiento esperado. Despues en algunos de los metodos, 
se probaron varios casos borde, con la intencion de comprobar que el metodo manejara de forma correcta estos casos borde y aparte, lanzara y manejara de manera correctalas excepciones. 
### Estado inicial al correr las pruebas.
Al ejecutar Las pruebas se obtiene el siguiente reporte.
![alt text](image.png)
Como se evidencia hay un gran desvalance en el porcentaje de cada una de las clases ya que clases como GeneralGlue, Glue y GlueOfGlue tienen un porcentaje mucho mas bajo que puzzle y puzzleContest.
### Analisis del reporte.
Al hacer analisis se logra observar que la gran falencia de las pruebas esta en los ladeos, ya que solo se esta teniendo en cuenta los casos de ladeos sin 
la agregacion de un pegante.
![alt text](image-2.png)
### Nuevos testeos.
Se Realizaran 8 nuevos test en los cuales se probaran los ladeos que tienen en cuenta el pegante y los que tienen en cuenta mas de un pegante puesto en el tablero.
### Estado final al correr las pruebas.
![alt text](image-4.png)
En el estado final se logra llegar a un alto porcentaje en la mayoria de los porcentajes, al hacer un promedio entre los porcentajes, se puede concluir que se ha alcanzado un 75,666% 
de cubrimiento del cogido con las pruebas unitarias y con sus respectivos cambios.
### Conclusion.
El codigo tiene un alto porcentaje de cubrimiento en sus pruebas, en el estado inicial se notaba una alata falencia en una de las partes del codigo, la cual fue corregida.
El peor de los porcentajes es el de las ramas, debido a que el codigo resultaba en un gran numero de ramas, la mayoria fueron probadas correctamente, pero de igual forma hicieron falta
mas pruebas en busca de cubrir la gran mayoria de las ramas.
El mejor fue el porcentaje de los metodos, esto es debido a que se tuvieron en cuenta casi en su totalidad todos los metodos del proyecto.
Por ultimo se hizo un buen trabajo de test pero es necesario mejorar el cubriemiento de las ramas del codigo.
## Deployment

Add additional notes about how to deploy this on a live system

## Construido con:

* Para el desarrollo del proyecto se usaron herramientas como bluej y astah. Pero su entrega fue hecha en IntelliJ


## Authors

* **Miguel Angel Vanegas Cardenas y Allan Steef Contreras** - *GitHub link* - [PurpleBooth](https://github.com/miguelvanegas-c/ProyectoInicial)






