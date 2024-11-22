<p align="center">
  <img width="200" src="https://www.fciencias.unam.mx/sites/default/files/logoFC_2.png" alt="">
  <br><strong>Compiladores 2025-1</strong> <br>
  <strong>Práctica 4: Analizadores sintácticos con BYACC/J (YACC)</strong> <br> 
  <strong>Profesora</strong>: Ariel Adara Mercado Martínez <br>
  <strong>Ayudante</strong>: Janeth Pablo Martínez <br>
  <strong>Ayud. Lab.</strong>: Carlos Gerardo Acosta Hernández <br> <br>
  <strong>Alumno</strong>: Kevin Steve Quezada Ordoñez <br>
</p>

## Análisis sintáctico con Yacc
### Objetivo:
1. Aprender a definir una gramática en Yacc.
2. Implementar una gramática con Yacc.
3. Unir un programa generado con Lex/Flex y el programa generado con Yacc.

### Introducción

- YACC es un generador de analizadores sintácticos del tipo ascendente, específicamente LALR(1).
- BYACC/J es una extensión del generador de analizadores sintácticos de Berkeley v1.8 compatible con YACC.
  - Es compatible con YACC.
  - Es capaz de generar código en Java.
- Generalmente Yacc se utiliza en conjunto con Lex/flex 
  - Lex/Flex genera un analizador léxico: `yylex()`
  - YACC genera el analizador sintáctico: `yyparse()`



### Estructura de un archivo YACC
Un definición de BYACC/J consta de tres secciones:
```
Sección de declaraciones
%%
Sección de acciones (Producciones)
%%
Sección de código de usuario (código en lenguaje Java)
```

#### Sección de declaraciones
La primera parte del archivo es el área de DECLARACIONES, donde se definen los tokens, las precedencias, la asociatividad de operadores, etc.

* __Directivas de código.__ Se utilizan para incluir los archivos de biblioteca 
    ```yacc
    %{
    import java.io.*;
    %}
    ```

* __Directivas de YACC.__
    - Los Terminales (```%token[<Tipo>] lista_terminales```).
        ```
        %token<sval> ID 
        %token<ival> ENTERO
        %token IF ELSE
        ```
    - Los No Terminales (```%type<Tipo> lista_no_terminales```). Generalmente los no terminales no se declaran al menos que requieran de un tipo.
        ```
        %type<sval> expresion termino factor
        ``` 
    - la asociatividad y precedencia de los operadores (```%left, %right, %nonassoc```). La precedencia va de menor a mayor.
        ```
        %left MAS
        %left MUL
        %nonassoc LPAR RPAR
        ```


#### Sección de acciones
La segunda parte es el área de ACCIONES, donde se definen las producciones, y se ejecutan las acciones semánticas en Java del usuario.

- Utiliza la notación BNF simplificada:
```Java
/* Una sola regla */
simboloNoTerminal : simb1 sim2 . . . simN [ accion ] ;

/*Varias reglas */
simboloNoTerminal : regla1 [ accion1 ]
                  | regla2 [ accion2 ] 
                  .............
                  ;

/* la regla vacia */
simboloNoTerminal : /* vacio */ [ accion1 ]
                  | regla2 [ accion2 ];
```

- Acciones

    a. Se escriben usando código en Java.

    b. Para hacer referencia a los símbolos gramaticales se utilizan pseudovariables ```$1, $2, ...$N```

    c. ```$$``` representa el encabezado de la producción.

    d. ```$1``` representa el primer símbolo gramatical después de los ```:```

##### Ejemplo
Para una gramática $G$ con símbolo inicial $X$ y
> P = {
>> X → Yc | $\varepsilon$ <br>
>> Y → bZ | $\varepsilon$ <br>
>> Z → $\varepsilon$ <br>
}

Podemos definir lo siguiente haciendo uso de Yacc:

```yacc
/* Sección de declaraciones*/
...

%%

x : y C | A;
y : B z | /* epsilon */;
z : /* epsilon */;

%%

/* Sección de código */
...
```

#### Sección de código
La tercera parte es el área de CÓDIGO, donde se agregan los métodos del usuario.
Hay tres métodos de la clase Parser que es necesario implementar para su funcionamiento:
- ```void yyerror(String msg)```: Se utiliza para proporcionar mensajes de error que se dirigirán a los canales que el usuario desee.
- ```int yylex()```: Este método es el que BYACC/J espera utilizar para obtener sus tokens de entrada. Podemos envolver cualquier código de escaneo de archivos/cadenas que tengas en este método. Este método debe devolver ```< 0``` si hay un error, y ```0``` cuando encuentre el final de la entrada. 
- ```public static void main(String args[])```: Es clara la necesidad de un método principal para la ejecución del análisis sintáctico, pero este método puede definirse en otro archivo o paquete. 

## Primer programa en YACC

### Instalación de BYACC/J
* Para Linux:

    - Obtenemos el archivo de instalación desde [https://byaccj.sourceforge.net/#download](https://byaccj.sourceforge.net/#download)

    - Descomprimimos en algún directorio pertinente:
        ```bash
        $ tar -C /some/user/directory -xvf byaccj1.15_linux.tar.gz
        ```

    - Creamos un enlace simbólico a los ejecutables del usuario para poder ejecutar desde cualquier directorio
        ```
        # ln -s /some/user/directory/yacc.linux /usr/bin/byaccj
        ```

### Para comprobar que se ha instalado correctamente:
```$ byaccj```

### Primer programa en YACC
 
```java
%{
  import java.lang.Math;
  import java.io.*;
  import java.util.StringTokenizer;
%}

/* YACC Declarations */
%token NUM
%left '-' '+'
%left '*' '/'
%left NEG /* negation--unary minus */
%right '^' /* exponentiation */

 /* Grammar follows */
%%
input: /* empty string */
| input line
;

line: '\n'
| exp '\n' { System.out.println(" " + $1.dval + " "); }
;

exp: NUM { $$ = $1; }
| exp '+' exp { $$ = new ParserVal($1.dval + $3.dval); }
| exp '-' exp { $$ = new ParserVal($1.dval - $3.dval); }
| exp '*' exp { $$ = new ParserVal($1.dval   $3.dval); }
| exp '/' exp { $$ = new ParserVal($1.dval / $3.dval); }
| '-' exp %prec NEG { $$ = new ParserVal(-$2.dval); }
| exp '^' exp { $$ = new ParserVal(Math.pow($1.dval, $3.dval)); }
| '(' exp ')' { $$ = $2; }
;
%%

String ins;
StringTokenizer st;

void yyerror(String s)
{
  System.out.println("par:"+s);
}

boolean newline;
int yylex()
{
  String s;
  int tok;
  Double d;
  //System.out.print("yylex ");
  if (!st.hasMoreTokens())
    if (!newline)
      {
	newline=true;
	return '\n'; //So we look like classic YACC example
      }
    else
      return 0;
  s = st.nextToken();
  //System.out.println("tok:"+s);
  try
    {
      d = Double.valueOf(s);/*this may fail*/
      yylval = new ParserVal(d.doubleValue()); //SEE BELOW
      tok = NUM;
    }
  catch (Exception e)
    {
      tok = s.charAt(0);/*if not float, return char*/
    }
  return tok;
}

void dotest()
{
  BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
  System.out.println("BYACC/J Calculator Demo");
  System.out.println("Note: Since this example uses the StringTokenizer");
  System.out.println("for simplicity, you will need to separate the items");
  System.out.println("with spaces, i.e.: '( 3 + 5 )   2'");
  while (true)
    {
      System.out.print("expression:");
      try
	{
	  ins = in.readLine();
	}
      catch (Exception e)
	{
	}
      st = new StringTokenizer(ins);
      newline=false;
      yyparse();
    }
}

public static void main(String args[])
{
  Parser par = new Parser(false);
  par.dotest();
}
```

#### Pasos para ejecutar la calculadora

#### Usando BYACCJ (Calculadora básica)

Nota: Aunque se muestran los pasos individuales para mayor claridad, se puede ejecutar directamente usando `ant calc-byaccj:run`, ya que las dependencias están configuradas para ejecutar los pasos previos automáticamente.

a. Generar el parser:
   ```bash
   $ ant calc-byaccj:generate
   ```

b. Verificar la generación de archivos:
   - Comprobar que se generaron correctamente `Parser.java` y `ParserVal.java`

   <br>

c. Compilar los archivos:
   ```bash
   $ ant calc-byaccj:compile
   ```

d. Ejecutar la calculadora:
   ```bash
   $ ant calc-byaccj:run
   ```

e. Limpiar archivos generados anteriormente:
   ```bash
   $ ant calc-byaccj:clean
   ```

### Ejercicios
1. Reemplazar el _StringTokenizer_ utilizado en el método original ```int yylex()``` con un analizador léxico generado mediante JFLex combinando ambas tecnologías (_BYACCJ+JFlex_).
2. Definir tokens para todos los operadores de la calculadora en ```archivo.y``` y devolverlos en los patrones que corresponda mediante acciones léxicas del archivo de _JFlex_. 
3. La calculadora debe ser capaz de mantener su funcionamiento interactivo mediante la consola y/o ser capaz de leer un archivo de entrada.

#### Usando BYACCJ+JFlex (Calculadora avanzada)


a. Generar el analizador léxico y sintáctico:
   ```bash
   $ ant calc-jflex+byaccj:generate
   ```

b. Verificar la generación de archivos:
   - Comprobar que se generaron correctamente `Parser.java`, `ParserVal.java` y `Lexer.java`

  <br>

c. Compilar los archivos:
   ```bash
   $ ant calc-jflex+byaccj:compile
   ```

d. Ejecutar la calculadora:
   ```bash
   $ ant calc-jflex+byaccj:run
   ```

e. Limpiar archivos generados anteriormente:
   ```bash
   $ ant calc-jflex+byaccj:clean
   ```

<br>

### Ejercicios para la definción de un Analizador Sintáctico en BYACC/J
Para la gramática: 
````
   programa → lista declaraciones cuerpo programa EOF
    
   cuerpo programa → bloque principal | lista sentencias
   bloque principal → {lista sentencias}
   lista declaraciones → lista declaraciones declaracion | declaracion
   declaracion → tipo lista var ;
   tipo → int | float
   lista var → lista var , id | id
   
   lista sentencias → lista sentencias sentencia | sentencia
   sentencias → {lista sentencias} | sentencia
   
   sentencia → asignacion
   | if stmt
   | while stmt
   
   asignacion → id = expresion ;
   
   if stmt → if (expresion) sentencias
   | if (expresion) sentencias else sentencias
   
   while stmt → while (expresion) sentencias
   
   expresion → expresion + termino
   | expresion − termino
   | expresion < termino
   | expresion > termino
   | expresion <= termino
   | expresion >= termino
   | expresion == termino
   | expresion ! = termino
   | termino
   
   termino → termino ∗ factor
   | termino / factor
   | factor
   
   factor → (expresion)
   | id | numero entero | numero real
````

4. Determinar en un archivo Readme, en formato Markdown (.md) o LaTeX (.tex) -- con su respectivo PDF, para este último -- , los conjuntos _N_, _Σ_ y el símbolo inicial _S_.  (0.5 pts.)
5. Mostrar en el archivo el proceso de eliminación de ambigüedad o justificar, en caso de no ser necesario. (1 pts.).
6. Mostrar en el archivo el proceso de eliminación de la recursividad izquierda o justificar, en caso de no ser necesario. (1 pts.)
7. Mostrar en el archivo el proceso de factorización izquierda o justificar, en caso de no ser necesario. (1 pts.)
8. Mostrar en el archivo los nuevos conjuntos _N_ y _P_. (0.5 pts.)
9. Realizar cualquier otro tratamiento necesario para evitar conflictos de _shift/reduce_ mostrando el proceso.
10. Crear una definición con _BYACC/J_ para la gramática resultante. 
11. Documentar el código. (0.25pts)
12. Proponer 4 archivos de prueba nuevos, 2 válidos y 2 inválidos. (0.25pts)
13. Crear un archivo build.xml para ANT que permita la automatización de la generación de los analizadores léxico y sintáctico y la compilación del resultado. 


### Estructura del directorio
```c++
P4:
│   build.xml
│   Practica_04.pdf
│   README.md
│
└───src
    ├───Analizador_Sintactico_BYACCJ
    │   │   ClaseLexica.java
    │   │   Colors.java
    │   │   Main.java
    │   │   Token.java
    │   │
    │   ├───byacc
    │   │       Parser.y
    │   │
    │   ├───jflex
    │   │       Lexer.flex
    │   │
    │   └───tst
    │           prueba.txt
    │           prueba1_Valida.txt
    │           prueba2_Valida.txt
    │           prueba3_Valida.txt
    │           prueba4_Inválida.txt
    │           prueba5_Inválida.txt
    │           prueba6_Inválida.txt
    │
    ├───Calculadora_BYACCJ
    │       archivo.y
    │
    └───Calculadora_BYACCJ_JFlex
        │   Main.java
        │
        ├───byacc
        │       Parser.y
        │
        └───jflex
                Lexer.flex
```

#### Usando el Analizador Sintáctico

Nota: Se pueden ejecutar las pruebas de diferentes maneras:

1. Ejecutar todas las pruebas de una vez:
   ```bash
   $ ant analizador:test-all
   ```

2. Ejecutar pruebas individuales:
   ```bash
   # Prueba base
   $ ant analizador:test-base

   # Pruebas válidas
   $ ant analizador:test-valid1
   $ ant analizador:test-valid2
   $ ant analizador:test-valid3

   # Pruebas inválidas
   $ ant analizador:test-invalid1
   $ ant analizador:test-invalid2
   $ ant analizador:test-invalid3
   ```

3. También se pueden ejecutar los pasos individuales si se desea:

   a. Generar el analizador léxico y sintáctico:
      ```bash
      $ ant analizador:generate
      ```

   b. Compilar los archivos:
      ```bash
      $ ant analizador:compile
      ```
      
   c. Limpiar archivos generados:
      ```bash
      $ ant analizador:clean
      ```
````