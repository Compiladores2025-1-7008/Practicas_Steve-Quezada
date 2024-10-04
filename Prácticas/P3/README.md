<p  align="center">
  <img  width="200"  src="https://www.fciencias.unam.mx/sites/default/files/logoFC_2.png"  alt="">  <br>Compiladores  2025-1 <br>
  Práctica 3: Implementación de un Analizador Sintáctico de descenso recursivo <br> 
  Profesora: Ariel Adara Mercado Martínez <br>
  Ayudante: Janeth Pablo Martínez <br>
  Ayud. Lab.: Carlos Gerardo Acosta Hernández <br>
  Alumno: Kevin Steve Quezada Ordoñez <br>
</p>

#### Ejercicios
Para la gramática G = ( N, Σ, P, S), descrita por las siguientes producciones: 
> P = {
>> programa → declaraciones sentencias <br>
>> declaraciones → declaraciones declaracion | declaracion <br>
>> declaracion → tipo lista-var **;** <br>
>> tipo → **int** | **float** <br>
>> lista_var → lista_var **,** _**identificador**_ | _**identificador**_ <br>
>> sentencias → sentencias sentencia | sentencia <br>
>> sentencia → _**identificador**_ **=** expresion **;** | **if** **(** expresion **)** sentencias **else** sentencias | **while** **(** expresión **)** sentencias <br>
>> expresion → expresion **+** expresion | expresion **-** expresion | expresion __\*__ expresion | expresion **/** expresión | _**identificador**_ | **_numero_** <br>
>> expresion → **(** expresion **)** <br>
}


1. Determinar en un archivo Readme, en formato Markdown (.md) o LaTeX (.tex) -- con su respectivo PDF, para este último -- , los conjuntos _N_, _Σ_ y el símbolo inicial _S_.  (0.5 pts.)
2. Mostrar en el archivo el proceso de eliminación de ambigüedad o justificar, en caso de no ser necesario. (1 pts.).
3. Mostrar en el archivo el proceso de eliminación de la recursividad izquierda o justificar, en caso de no ser necesario. (1 pts.)
4. Mostrar en el archivo el proceso de factorización izquierda o justificar, en caso de no ser necesario. (1 pts.)
5. Mostrar en el archivo los nuevos conjuntos _N_ y _P_. (0.5 pts.)
6. Sustituir el enum de la práctica 2 con una definición de clases léxicas utilizando enteros (ClaseLexica.java). (0.5 pts.)
7. Implementar el Analizador Sintáctico (Parser.java) de descenso recursivo, documentando las funciones de cada No-Terminal, de forma que el programa descrito en el archivo _prueba.txt_ sea reconocido y aceptado por el analizador resultante. (4 pts.)
8. Proveer una segunda versión del programa en una implementación de la interfaz ParserInterface.java, en la que los analizadores léxico y sintáctico hagan uso de la clase Token (Token.java). (2 pts.)

---
#### Extras

9. Documentar el código. (0.25pts)
10. Proponer 4 archivos de prueba nuevos, 2 válidos y 2 inválidos. (0.25pts)

### Estructura del directorio
```c++
P3
├─── Practica_3.pdf
├─── README.md
│   
├───V1
│   ├─── build.xml
│   │   
│   ├───src
│   │   └───main
│   │       ├───java
│   │       │       ClaseLexica.java
│   │       │       Colors.java
│   │       │       Main.java
│   │       │       Parser.java
│   │       │       ParserInterface.java
│   │       │       Token.java
│   │       │       
│   │       └───jflex
│   │               Lexer.flex
│   │
│   └───tst
│           prueba.txt
│
└───V2
    ├─── build.xml
    │   
    ├───src
    │   └───main
    │       ├───java
    │       │       ClaseLexica.java
    │       │       Colors.java
    │       │       Main.java
    │       │       Parser.java
    │       │       ParserInterface.java
    │       │       Token.java
    │       │
    │       └───jflex
    │               Lexer.flex
    │
    └───tst
            prueba.txt
```

## Implementaciones

### Versión 1

En la carpeta `V1` se encuentra la implementación del ejercicio 7, que consiste en:

- Implementar el Analizador Sintáctico (`Parser.java`) de descenso recursivo, documentando las funciones de cada No-Terminal, de forma que el programa descrito en el archivo `prueba.txt` sea reconocido y aceptado por el analizador resultante. (4 pts.)

#### Versión 2

En la carpeta `V2` se encuentra la implementación del ejercicio 8, que consiste en:

- Proveer una segunda versión del programa en una implementación de la interfaz `ParserInterface.java`, en la que los analizadores léxico y sintáctico hagan uso de la clase `Token` (`Token.java`). (2 pts.)

## Flujo General de los Comandos

### Ejecutar el analizador léxico:
Este comando compilará el proyecto y luego ejecutará el analizador léxico:

```bash
$ ant run
```

### Limpiar el directorio de compilación:
Este comando eliminará todos los archivos generados en el proceso de compilación:

```bash
$ ant clean
```

### Generar el analizador léxico:
Si necesitas regenerar el archivo `Lexer.java`:

```bash
$ ant generate
```

### Compilar el proyecto:
Si deseas compilar el proyecto sin ejecutarlo:

```bash
$ ant compile
```

### Archivo de entrada por defecto `prueba.txt`
```
int a, _b;
float c, d;

a = 1 + 3;
_b = a + 23;
```

### Archivos de prueba Validos.

#### Ejemplo 1:
```
int a, b, c;
float x, y, z;

a = 5;
b = 10;
c = a + b;

if (a < b) {
    x = 1.5;
    y = x * 2;
} else {
    z = 3.5;
}

while (c > 0) {
    c = c - 1;
}
```

#### Ejemplo 2:
```
int a, b;
float c;
{
    a = 5;
    b = 10;
    if (a < b) {
        c = a + b * 2;
        while (c >= 10.5) {
            c = c / 2;
        }
    } else {
        a = b - 3;
    }
}
```

#### Ejemplo 3:
```
int x; 
float y; 
{
    x = 10; 
    y = (x - 3.5); 
    while (y < x) { 
        y = y + 1.5; 
    } 
}
```

### Archivos de prueba Inválidos.

#### Ejemplo 1:
```
int p, q;
float r;
{
    p = 8;
    q = (p + 2;
    if (q > p) {
        r = q * 2;
        while r >= 10 {
            r = r / 2;
        }
    }
}
```

#### Ejemplo 2:
```
int a, b;
{
    a = 10;
    b = a * 2;
    if (b >= 20) {
        a = a + 1;
        b = b - a;
    } else {
        a = b * 3;
    }
    b = a / 2;
```

#### Ejemplo 3:
```
int x; 
float y; 
@
{
    x = 10; 
    y = (x - 3.5); 
    while (y < x) { 
        y = y + 1.5; 
    } 
}
```