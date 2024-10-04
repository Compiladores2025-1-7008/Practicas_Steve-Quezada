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
public void printLexicalState(String lexema, ClaseLexica claseLexica) {
    String color = claseLexica.getColor();
    Colors.print("\nToken: " + lexema + 
                 " | Clase: " + claseLexica.getNombre() + 
                 " | Línea: " + getLine(), color);
}
%}

%public
%class Lexer
%standalone
%unicode
%line
%type Token

espacio = [ \t\n]
letra = [a-zA-Z_]
digito = [0-9]
identificador = {letra}({letra}|{digito})*
numero_entero = {digito}+
numero_real = {digito}+"."{digito}+

%%

{espacio}+ { /* Ignorar espacios en blanco */ }

"int"    { printLexicalState(yytext(), ClaseLexica.INT); return new Token(ClaseLexica.INT, yytext(), getLine()); }
"float"  { printLexicalState(yytext(), ClaseLexica.FLOAT); return new Token(ClaseLexica.FLOAT, yytext(), getLine()); }
"if"     { printLexicalState(yytext(), ClaseLexica.IF); return new Token(ClaseLexica.IF, yytext(), getLine()); }
"else"   { printLexicalState(yytext(), ClaseLexica.ELSE); return new Token(ClaseLexica.ELSE, yytext(), getLine()); }
"while"  { printLexicalState(yytext(), ClaseLexica.WHILE); return new Token(ClaseLexica.WHILE, yytext(), getLine()); }

// Identificadores
{identificador} { printLexicalState(yytext(), ClaseLexica.ID); return new Token(ClaseLexica.ID, yytext(), getLine()); }

// Números
{numero_entero} { printLexicalState(yytext(), ClaseLexica.NUMERO_ENTERO); return new Token(ClaseLexica.NUMERO_ENTERO, yytext(), getLine()); }
{numero_real}   { printLexicalState(yytext(), ClaseLexica.NUMERO_REAL); return new Token(ClaseLexica.NUMERO_REAL, yytext(), getLine()); }

// Símbolos y operadores
";"   { printLexicalState(yytext(), ClaseLexica.PYC); return new Token(ClaseLexica.PYC, yytext(), getLine()); }
","   { printLexicalState(yytext(), ClaseLexica.COMA); return new Token(ClaseLexica.COMA, yytext(), getLine()); }
"("   { printLexicalState(yytext(), ClaseLexica.LPAR); return new Token(ClaseLexica.LPAR, yytext(), getLine()); }
")"   { printLexicalState(yytext(), ClaseLexica.RPAR); return new Token(ClaseLexica.RPAR, yytext(), getLine()); }
"{"   { printLexicalState(yytext(), ClaseLexica.LLLA); return new Token(ClaseLexica.LLLA, yytext(), getLine()); }
"}"   { printLexicalState(yytext(), ClaseLexica.RLLA); return new Token(ClaseLexica.RLLA, yytext(), getLine()); }
"="   { printLexicalState(yytext(), ClaseLexica.ASIGNACION); return new Token(ClaseLexica.ASIGNACION, yytext(), getLine()); }
"=="  { printLexicalState(yytext(), ClaseLexica.IGUALDAD); return new Token(ClaseLexica.IGUALDAD, yytext(), getLine()); }
">"   { printLexicalState(yytext(), ClaseLexica.MAYORQUE); return new Token(ClaseLexica.MAYORQUE, yytext(), getLine()); }
"<"   { printLexicalState(yytext(), ClaseLexica.MENORQUE); return new Token(ClaseLexica.MENORQUE, yytext(), getLine()); }
"+"   { printLexicalState(yytext(), ClaseLexica.SUMA); return new Token(ClaseLexica.SUMA, yytext(), getLine()); }
"-"   { printLexicalState(yytext(), ClaseLexica.RESTA); return new Token(ClaseLexica.RESTA, yytext(), getLine()); }
"*"   { printLexicalState(yytext(), ClaseLexica.MULTIPLICACION); return new Token(ClaseLexica.MULTIPLICACION, yytext(), getLine()); }
"/"   { printLexicalState(yytext(), ClaseLexica.DIVISION); return new Token(ClaseLexica.DIVISION, yytext(), getLine()); }

// Operadores relacionales y lógicos
">="  { printLexicalState(yytext(), ClaseLexica.MAYORIGUAL); return new Token(ClaseLexica.MAYORIGUAL, yytext(), getLine()); }
"<="  { printLexicalState(yytext(), ClaseLexica.MENORIGUAL); return new Token(ClaseLexica.MENORIGUAL, yytext(), getLine()); }
"!="  { printLexicalState(yytext(), ClaseLexica.DIFERENTE); return new Token(ClaseLexica.DIFERENTE, yytext(), getLine()); }

// Fin de archivo
<<EOF>> { printLexicalState("EOF", ClaseLexica.EOF); return new Token(ClaseLexica.EOF, "EOF", getLine()); }

// Caracteres no reconocidos
. { 
    Colors.println("\nError: Símbolo no reconocido '" + yytext() + 
                   "' en línea " + getLine() + ".", Colors.RED); 
    return new Token(ClaseLexica.UNKNOWN, yytext(), getLine()); 
}