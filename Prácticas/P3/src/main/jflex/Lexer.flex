package main.jflex;

import main.java.ClaseLexica;
import main.java.Token;

%%

%{
public Token actual;
public int getLine() { return yyline; }
%}

%public
%class Lexer
%standalone
%unicode
%line

// Definición de patrones
espacio=[ \t\n]
letra=[a-zA-Z_]
digito=[0-9]
identificador={letra}({letra}|{digito})*
numero_entero={digito}+
numero_real={digito}+"."+{digito}+

%%

{espacio}+ { /* Ignorar espacios en blanco */ }

// Palabras reservadas
"int"    { return ClaseLexica.INT; }
"float"  { return ClaseLexica.FLOAT; }
"if"     { return ClaseLexica.IF; }
"else"   { return ClaseLexica.ELSE; }
"while"  { return ClaseLexica.WHILE; }

// Identificadores
{identificador} { return ClaseLexica.ID; }

// Números
{numero_entero} { return ClaseLexica.NUMERO_ENTERO; }
{numero_real}   { return ClaseLexica.NUMERO_REAL; }

// Símbolos y operadores
";"   { return ClaseLexica.PYC; }
","   { return ClaseLexica.COMA; }
"("   { return ClaseLexica.LPAR; }
")"   { return ClaseLexica.RPAR; }
"{"   { return ClaseLexica.LLLA; }
"}"   { return ClaseLexica.RLLA; }
"="   { return ClaseLexica.ASIGNACION; }
"=="  { return ClaseLexica.IGUALDAD; }
">"   { return ClaseLexica.MAYORQUE; }
"<"   { return ClaseLexica.MENORQUE; }
"+"   { return ClaseLexica.SUMA; }
"-"   { return ClaseLexica.RESTA; }
"*"   { return ClaseLexica.MULTIPLICACION; }
"/"   { return ClaseLexica.DIVISION; }

// Operadores relacionales y lógicos
">="  { return ClaseLexica.MAYORIGUAL; }
"<="  { return ClaseLexica.MENORIGUAL; }
"!="  { return ClaseLexica.DIFERENTE; }

// Fin de archivo
<<EOF>> { return ClaseLexica.EOF; }

// Caracteres no reconocidos
. { System.err.println("Error: Símbolo no reconocido en línea " + getLine()); return -1; }

