# Introducción
- El programa consiste en la calculadora que ya habíamos hecho previamente, pero conectándola con una base de datos h2.
- En mi caso, no he usado dao, ya que tenía parte diseñada cuando aún no lo habíamos dado, y no disponía del tiempo suficiente como para rediseñarlo, aunque trataré de hacerlo si el tiempo me lo permite.
- La función del programa es que guarde cada una de las operaciones en una base de datos, como ya habiamos mencionado, ahora explicaré como lo he hecho.
## Conexión con H2
- Primero que todo debemos de establecer una conexión con la base de datos, junto con un PATH que nos lo permita.
https://github.com/juanma-ge/CalculadoraH2/blob/65f88a4ca472a5f815edaa9b88681be4e74b9ea9/src/main/kotlin/data/Base_datos.kt#L7-L9
- También debemos de conectar e iniciar la base de datos, lo cual lo haremos también dentro de nuestra clase 'Base_datos'.
https://github.com/juanma-ge/CalculadoraH2/blob/65f88a4ca472a5f815edaa9b88681be4e74b9ea9/src/main/kotlin/data/Base_datos.kt#L11-L12
https://github.com/juanma-ge/CalculadoraH2/blob/65f88a4ca472a5f815edaa9b88681be4e74b9ea9/src/main/kotlin/data/Base_datos.kt#L14-L30
## Data class
- He creado la data class 'Operacion', con sus respectivos parámetros, para facilitaru su guardado en la base de datos.
https://github.com/juanma-ge/CalculadoraH2/blob/65f88a4ca472a5f815edaa9b88681be4e74b9ea9/src/main/kotlin/model/Operacion.kt#L5-L12
- Además en caso de que se quieras usar, he creado una clase llamada 'DatabaseError'.
https://github.com/juanma-ge/CalculadoraH2/blob/65f88a4ca472a5f815edaa9b88681be4e74b9ea9/src/main/kotlin/model/DatabaseError.kt#L3
## Funcionamiento
- El programa tiene varios try catch para asegurarse de las capturas de las excepciones y su uso correcto.
- La función del programa como hemos dicho anteriormente, es la de guardar operaciones utilizando H2, además de sus datos como el operador, la fecha o el resultado.
