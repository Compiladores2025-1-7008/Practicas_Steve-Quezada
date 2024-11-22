/**
 * Analizador Sintáctico para Calculadora
 *
 * Este archivo define la gramática y las acciones semánticas para una calculadora
 * que soporta operaciones aritméticas básicas, potencias y expresiones con paréntesis.
 *
 * Gramática implementada:
 * input -> ε | input line
 * line  -> EOL | exp EOL
 * exp   -> NUM | exp op exp | -exp | (exp)
 * op    -> + | - | * | / | ^
 *
 * @author steve-quezada
 */

%{
/* Importaciones necesarias para la calculadora */
  import java.io.*;
  import java.lang.Math;
%}

/* Declaración de tokens y sus tipos */
%token <dval> NUM                     /* Token para números (valores decimales) */
%token PLUS MINUS TIMES DIVIDE POWER  /* Operadores */
%token LPAREN RPAREN                  /* Paréntesis */
%token EOL                            /* Fin de archivo */

%type <dval> exp                      /* Las expresiones devuelven valores decimales */

/* Precedencia y asociatividad de operadores (de menor a mayor precedencia) */
%left PLUS MINUS     /* + y - son asociativos por la izquierda */
%left TIMES DIVIDE   /* * y / son asociativos por la izquierda */
%right POWER         /* ^ es asociativo por la derecha */
%nonassoc NEG        /* - unario tiene la mayor precedencia */

%%
/* Reglas de producción de la gramática con acciones semánticas */

input:
| input line
;

line: EOL
    | exp EOL { System.out.print(expression + "\nresultado: " + $1); }
;

exp: NUM { $$ = $1; }
| exp PLUS exp { $$ = $1 + $3; }
| exp MINUS exp { $$ = $1 - $3; }
| exp TIMES exp { $$ = $1 * $3; }
| exp DIVIDE exp { $$ = $1 / $3; }
| MINUS exp %prec NEG { $$ = -$2; }
| exp POWER exp { $$ = Math.pow($1, $3); }
| LPAREN exp RPAREN { $$ = $2; }
;

%%

/* Código de soporte */

private Lexer lexer;           // Analizador léxico
private String expression;     // Expresión actual siendo evaluada

/**
 * Constructor del analizador sintáctico
 * @param r Reader para la entrada
 */
public Parser(Reader r) {
    lexer = new Lexer(r, this);
}

/**
 * Establece la expresión actual para mostrarla en los resultados
 * @param expression Expresión a evaluar
 */
public void setExpression(String expression) {
    this.expression = expression;
}

/**
 * Obtiene el siguiente token del analizador léxico
 * @return código del token o -1 en caso de error
 */
public int yylex() {
    try {
        return lexer.yylex();
    } catch (IOException e) {
        System.err.println("Error de entrada/salida: " + e);
        return -1;
    }
}

/**
 * Maneja los errores sintácticos
 * @param s Mensaje de error
 */
void yyerror(String s) {
    System.err.println("Error de sintaxis: " + s);
}