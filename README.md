# Tarea 2 lógica III | Polinomio cromático de un grafo

Roger Sepúlveda

## Requerimientos

- Java 8
- [sbt](http://www.scala-sbt.org/0.13/docs/Installing-sbt-on-Linux.html)

Para compilar la aplicación (puede tardar un poco la primera vez)
```
sbt assembly
```

La ejecución del comando anterior, le dará la ruta donde se creó el jar.
Para ejecutarlo, en el siguiente comando, reemplace la ruta dada:

```
java -jar {RUTA_JAR}/tarea.jar --help
```

El comando anterior le mostrará el manual de usuario. 
  
## Desarrollo

El desarrollo de la aplicación se hizo en Scala.

La clase principal es `edu.udea.Main` en donde se interpreta los parámetros del usuario

La clase `Grafo` es la que calcula el polinomio. Todos sus métodos están nombrados
 lo suficientemente claros como para que se entienda qué hacen.

Hay dos versiones de esta función; una con la heurística *vertex order heuristic* descrita en este paper
http://cjtcs.cs.uchicago.edu/articles/CATS2009/3/cats9-3.pdf

Esta heurística, consiste en elegir los vérices que se van a eliminar/combinar
en las iteraciones del algoritmo, basado en los vértices que tengan el número mayor de conexiones
hasta que el nodo esté desconectado completamente.

Una mejora considerable se puede hacer con caché, ya que la heurística aumenta la probabilidad de que los 
grafos converjan a medida que se avanza en el algoritmo.