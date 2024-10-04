package main.jflex;

import main.java.ClaseLexica;
import main.java.Token;
import main.java.Colors;

%%

%{
/**
 * Clase Lexer que implementa un analizador léxico utilizando JFlex.
 * 
 * Esta clase se encarga de leer el código fuente y dividirlo en tokens
 * que serán utilizados por el analizador sintáctico.
 * 
 * @author steve-quezada
 */
public Token actual;

/**
 * Obtiene la línea actual del análisis léxico.
 * 
 * @return El número de línea actual.
 */
public int getLine() { return yyline + 1; }

/**
 * Imprime el estado léxico actual.
 * 
 * @param lexema El lexema del token.
 * @param claseLexica La clase léxica del token.
 */
public void printLexicalState(String lexema, int claseLexica) {
    String color = ClaseLexica.getColorClase(claseLexica);
    Colors.print("\nToken: " + lexema + 
                 " | Clase: " + ClaseLexica.getNombreClase(claseLexica) + 
                 " | Línea: " + getLine(), color);
}
%}

%public
%class Lexer
%standalone
%unicode
%line

espacio = [ \t\n]
letra = [a-zA-Z_]
digito = [0-9]
identificador = {letra}({letra}|{digito})*
numero_entero = {digito}+
numero_real = {digito}+"."{digito}+

%%

{espacio}+ { /* Ignorar espacios en blanco */ }

"int"    { printLexicalState(yytext(), ClaseLexica.INT); return ClaseLexica.INT; }
"float"  { printLexicalState(yytext(), ClaseLexica.FLOAT); return ClaseLexica.FLOAT; }
"if"     { printLexicalState(yytext(), ClaseLexica.IF); return ClaseLexica.IF; }
"else"   { printLexicalState(yytext(), ClaseLexica.ELSE); return ClaseLexica.ELSE; }
"while"  { printLexicalState(yytext(), ClaseLexica.WHILE); return ClaseLexica.WHILE; }

// Identificadores
{identificador} { printLexicalState(yytext(), ClaseLexica.ID); return ClaseLexica.ID; }

// Números
{numero_entero} { printLexicalState(yytext(), ClaseLexica.NUMERO_ENTERO); return ClaseLexica.NUMERO_ENTERO; }
{numero_real}   { printLexicalState(yytext(), ClaseLexica.NUMERO_REAL); return ClaseLexica.NUMERO_REAL; }

// Símbolos y operadores
";"   { printLexicalState(yytext(), ClaseLexica.PYC); return ClaseLexica.PYC; }
","   { printLexicalState(yytext(), ClaseLexica.COMA); return ClaseLexica.COMA; }
"("   { printLexicalState(yytext(), ClaseLexica.LPAR); return ClaseLexica.LPAR; }
")"   { printLexicalState(yytext(), ClaseLexica.RPAR); return ClaseLexica.RPAR; }
"{"   { printLexicalState(yytext(), ClaseLexica.LLLA); return ClaseLexica.LLLA; }
"}"   { printLexicalState(yytext(), ClaseLexica.RLLA); return ClaseLexica.RLLA; }
"="   { printLexicalState(yytext(), ClaseLexica.ASIGNACION); return ClaseLexica.ASIGNACION; }
"=="  { printLexicalState(yytext(), ClaseLexica.IGUALDAD); return ClaseLexica.IGUALDAD; }
">"   { printLexicalState(yytext(), ClaseLexica.MAYORQUE); return ClaseLexica.MAYORQUE; }
"<"   { printLexicalState(yytext(), ClaseLexica.MENORQUE); return ClaseLexica.MENORQUE; }
"+"   { printLexicalState(yytext(), ClaseLexica.SUMA); return ClaseLexica.SUMA; }
"-"   { printLexicalState(yytext(), ClaseLexica.RESTA); return ClaseLexica.RESTA; }
"*"   { printLexicalState(yytext(), ClaseLexica.MULTIPLICACION); return ClaseLexica.MULTIPLICACION; }
"/"   { printLexicalState(yytext(), ClaseLexica.DIVISION); return ClaseLexica.DIVISION; }

// Operadores relacionales y lógicos
">="  { printLexicalState(yytext(), ClaseLexica.MAYORIGUAL); return ClaseLexica.MAYORIGUAL; }
"<="  { printLexicalState(yytext(), ClaseLexica.MENORIGUAL); return ClaseLexica.MENORIGUAL; }
"!="  { printLexicalState(yytext(), ClaseLexica.DIFERENTE); return ClaseLexica.DIFERENTE; }

// Fin de archivo
<<EOF>> { printLexicalState("EOF", ClaseLexica.EOF); return ClaseLexica.EOF; }

// Caracteres no reconocidos
. { 
    Colors.println("\nError: Símbolo no reconocido '" + yytext() + 
                   "' en línea " + getLine() + ".", Colors.RED); 
    return -1; 
}