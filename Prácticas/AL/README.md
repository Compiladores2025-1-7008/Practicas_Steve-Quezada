<p  align="center">
  <img  width="200"  src="https://www.fciencias.unam.mx/sites/default/files/logoFC_2.png"  alt="">  <br>Compiladores  2025-1 <br>
  Programa de Análisis Léxico
 <br> Profesora: Ariel Adara Mercado Martínez<br> 
  Alumno: Kevin Steve Quezada Ordoñez 
</p>

## Analizador Léxico
### Objetivo:
Implementar un analizador léxico en Java que sea capaz de reconocer tokens definidos en el lenguaje C_1, generando los tokens correspondientes a partir de un archivo de entrada. Este programa servirá como base para la construcción de un compilador.

### Introducción
El siguiente programa realiza el análisis léxico de un archivo de entrada en lenguaje C_1. El proceso consiste en la lectura del archivo, el reconocimiento de lexemas y la generación de tokens utilizando una tabla de transiciones. Este programa está construido en Java y utiliza la clase `Lexer` para llevar a cabo este análisis.

### Estructura del directorio
```c++
AL
│   build.xml
│   input.txt
│   README.md
│   
└───src
        ClaseLexica.java
        Lexer.java
        Token.java
```

### Flujo General de los Comandos
A continuación, se muestra el flujo de trabajo para compilar y ejecutar el analizador léxico utilizando Ant:

#### Generar y compilar el Lexer:
```bash
$ ant compile
```

#### Ejecutar el analizador léxico:
```bash
$ ant run
```

#### Limpiar el directorio de compilación:
```bash
$ ant clean
```

### Archivo de entrada `input.txt`
```
if (x == 1) {
    float result = 2.5e3;
    x = x + 1;
} else {
    int counter = 100;
}
```

### Ejemplo de salida con `input.txt`
```
--- Análisis Léxico Iniciado ---

Estado actual: 0, Carácter: 'i', Lexema: ''
Estado actual: 1, Carácter: 'f', Lexema: 'i'
                                 Lexema: 'if'

                                 Lexema: '('

Estado actual: 0, Carácter: 'x', Lexema: ''
                                 Lexema: 'x'

                                 Lexema: '='

Estado actual: 0, Carácter: '1', Lexema: ''
                                 Lexema: '1'

                                 Lexema: ')'

                                 Lexema: '{'

                                 Lexema: '↵'

Estado actual: 0, Carácter: 'f', Lexema: ''
Estado actual: 1, Carácter: 'l', Lexema: 'f'
Estado actual: 1, Carácter: 'o', Lexema: 'fl'
Estado actual: 1, Carácter: 'a', Lexema: 'flo'
Estado actual: 1, Carácter: 't', Lexema: 'floa'
                                 Lexema: 'float'

Estado actual: 0, Carácter: 'r', Lexema: ''
Estado actual: 1, Carácter: 'e', Lexema: 'r'
Estado actual: 1, Carácter: 's', Lexema: 're'
Estado actual: 1, Carácter: 'u', Lexema: 'res'
Estado actual: 1, Carácter: 'l', Lexema: 'resu'
Estado actual: 1, Carácter: 't', Lexema: 'resul'
                                 Lexema: 'result'

                                 Lexema: '='

Estado actual: 0, Carácter: '2', Lexema: ''
Estado actual: 2, Carácter: '.', Lexema: '2'
Estado actual: 3, Carácter: '5', Lexema: '2.'
Estado actual: 4, Carácter: 'e', Lexema: '2.5'
Estado actual: 5, Carácter: '3', Lexema: '2.5e'
                                 Lexema: '2.5e3'

                                 Lexema: ';'

                                 Lexema: '↵'

Estado actual: 0, Carácter: 'x', Lexema: ''
                                 Lexema: 'x'

                                 Lexema: '='

Estado actual: 0, Carácter: 'x', Lexema: ''
                                 Lexema: 'x'

                                 Lexema: '+'

Estado actual: 0, Carácter: '1', Lexema: ''
                                 Lexema: '1'

                                 Lexema: ';'

                                 Lexema: '↵'

                                 Lexema: '}'

Estado actual: 0, Carácter: 'e', Lexema: ''
Estado actual: 1, Carácter: 'l', Lexema: 'e'
Estado actual: 1, Carácter: 's', Lexema: 'el'
Estado actual: 1, Carácter: 'e', Lexema: 'els'
                                 Lexema: 'else'

                                 Lexema: '{'

                                 Lexema: '↵'

Estado actual: 0, Carácter: 'i', Lexema: ''
Estado actual: 1, Carácter: 'n', Lexema: 'i'
Estado actual: 1, Carácter: 't', Lexema: 'in'
                                 Lexema: 'int'

Estado actual: 0, Carácter: 'c', Lexema: ''
Estado actual: 1, Carácter: 'o', Lexema: 'c'
Estado actual: 1, Carácter: 'u', Lexema: 'co'
Estado actual: 1, Carácter: 'n', Lexema: 'cou'
Estado actual: 1, Carácter: 't', Lexema: 'coun'
Estado actual: 1, Carácter: 'e', Lexema: 'count'
Estado actual: 1, Carácter: 'r', Lexema: 'counte'
                                 Lexema: 'counter'

                                 Lexema: '='

Estado actual: 0, Carácter: '1', Lexema: ''
Estado actual: 2, Carácter: '0', Lexema: '1'
Estado actual: 2, Carácter: '0', Lexema: '10'
                                 Lexema: '100'

                                 Lexema: ';'

                                 Lexema: '↵'

                                 Lexema: '}'

--- Análisis Léxico Finalizado ---


--- Análisis de Tokens Iniciado ---

<IF, if>
<LPAR, (>
<ID, x>
<IGUALDAD, ==>
<NUMERO, 1>
<RPAR, )>
<LLLA, {>
<FLOAT, float>
<ID, result>
<ASIGNACION, =>
<FLOAT, 2.5e3>
<PYC, ;>
<ID, x>
<ASIGNACION, =>
<ID, x>
<SUMA, +>
<NUMERO, 1>
<PYC, ;>
<RLLA, }>
<ELSE, else>
<LLLA, {>
<INT, int>
<ID, counter>
<ASIGNACION, =>
<NUMERO, 100>
<PYC, ;>
<RLLA, }>

--- Análisis de Tokens Finalizado ---
```

### Descripción de los componentes principales:

#### Clase `Lexer`
El programa incluye una clase `Lexer` que implementa un analizador léxico. La clase utiliza un **PushbackReader** para leer el archivo de entrada y devolver caracteres cuando sea necesario. Contiene una **tabla de transiciones** que define los estados y los caracteres permitidos para cada uno de ellos. Además, reconoce caracteres especiales como `+`, `-`, `=` y otros operadores del lenguaje.

#### Clase `ClaseLexica`
La clase `ClaseLexica` es un `enum` que define los diferentes tipos de tokens que el analizador léxico puede reconocer en el lenguaje C_1. Cada tipo de token representa una categoría de lexemas del código fuente. 

- **Palabras clave:**  
  `INT`, `FLOAT`, `IF`, `ELSE`, `WHILE`

- **Identificadores:**  
  `ID`

- **Números:**  
  `NUMERO`

- **Símbolos:**  
  `PYC (;)`, `COMA (,)`, `LPAR ( ( )`, `RPAR ( ) )`, `LLLA ({)`, `RLLA (})`,  
  `ASIGNACION (=)`, `IGUALDAD (==)`, `SUMA (+)`, `RESTA (-)`, `SALTOLINEA (↵)`

#### Clase `Token`
La clase `Token` representa una unidad léxica que será reconocida por el analizador léxico. Un token contiene dos elementos importantes: el **tipo** (de la clase `ClaseLexica`) y el **lexema** (el valor del token en el código fuente).  
 
La clase tiene dos atributos privados:  
1. **`tipo`**: Un objeto de la clase `ClaseLexica`, que define el tipo del token.  
2. **`lexema`**: Una cadena de texto que representa el valor real que ha sido reconocido.

El token también incluye métodos para obtener estos valores:
- **`getTipo()`**: Devuelve el tipo del token.
- **`getLexema()`**: Devuelve el lexema.

Además, la clase sobrescribe el método `toString()` para proporcionar una representación legible del token en el formato `<Tipo, Lexema>`.

#### Tokens y Lexemas
Los tokens son definidos por la clase `Token` y reconocidos por el método `analizarLexema()` en la clase `Lexer`. Este método procesa el archivo de entrada carácter por carácter, acumulando lexemas y devolviendo los tokens correspondientes al final de cada secuencia válida.

#### Estados y Tabla de Transiciones
El **autómata finito** que representa el analizador léxico es implementado mediante una **tabla de transiciones** y un conjunto de **estados de aceptación**. Cada estado tiene asignados los caracteres que permiten avanzar a un estado siguiente o quedarse en el mismo estado.