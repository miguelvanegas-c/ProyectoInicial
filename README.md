# Proyecto: Test POOB - Simulador de Rompecabezas

## Descripción
Este proyecto implementa un simulador de rompecabezas utilizando baldosas y pegamento. Permite la manipulación de piezas y su unión mediante diferentes funciones.

## Clases

### 1. Clase Tile

#### Atributos
- `color`: Color de la pieza.
- `position`: Posición de la pieza en el tablero.
- `isFixed`: Indica si la pieza está fijada.

#### Métodos
- `__init__(self, color, position)`: Inicializa la pieza con un color y una posición.
- `move(self, new_position)`: Mueve la pieza a una nueva posición.
- `fix(self)`: Fija la pieza en su posición actual.

### 2. Clase Puzzle

#### Atributos
- `startingMatrix`: Matriz inicial del rompecabezas.
- `endingMatrix`: Matriz objetivo del rompecabezas.
- `tiles`: Lista de piezas del rompecabezas.

#### Métodos
- `__init__(self, startingMatrix, endingMatrix)`: Inicializa el rompecabezas con matrices inicial y final.
- `isGoal(self)`: Verifica si el estado actual del rompecabezas coincide con el objetivo.
- `actualArrangement(self)`: Muestra la disposición actual de las piezas.
- `addTile(self, tile)`: Agrega una nueva pieza al rompecabezas.

### 3. Clase Glue

#### Atributos
- `gluedTiles`: Lista de piezas unidas por el pegamento.

#### Métodos
- `__init__(self)`: Inicializa la clase de pegamento.
- `glueTiles(self, tile1, tile2)`: Une dos piezas usando pegamento.
- `releaseTiles(self, tile1, tile2)`: Libera la unión entre dos piezas.

### 4. Clase GlueOfGlue

#### Atributos
- `gluedPairs`: Lista de pares de piezas que están unidas por pegamento.

#### Métodos
- `__init__(self)`: Inicializa la clase GlueOfGlue.
- `addGluePair(self, pair)`: Agrega un par de piezas unidas.
- `removeGluePair(self, pair)`: Elimina un par de piezas unidas.

