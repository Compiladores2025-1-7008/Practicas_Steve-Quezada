/**
 * Escáner que detecta el lenguaje C_1
*/

package main.jflex;

import main.java.ClaseLexica;
import main.java.Token;

%%

%{

/**
 * Método que imprime en consola el tipo de token y el lexema asociado. 
 * Se usa para generar la salida esperada del analizador léxico.
*/
private void reportarToken(ClaseLexica tipoToken, String lexema) {
    System.out.println("<" + tipoToken + ", " + lexema + ">");
}

%}

%public
%class Lexer
%standalone
%unicode

// Patrones para el análisis léxico
espacio = [ \t\n]+
digito = [0-9]
letra = [a-zA-Z]
inicioIdent = {letra}|_
parteIdent = {letra}|{digito}|_
identificador = {inicioIdent}({parteIdent})*
numeroFloat = {digito}+(\.{digito}+)?([eE][-+]?{digito}+)?
numeroInt = {digito}+

%%

<YYINITIAL> {

  // Palabras clave
  "int"       { reportarToken(ClaseLexica.INT, yytext()); }
  "float"     { reportarToken(ClaseLexica.FLOAT, yytext()); }
  "if"        { reportarToken(ClaseLexica.IF, yytext()); }
  "else"      { reportarToken(ClaseLexica.ELSE, yytext()); }
  "while"     { reportarToken(ClaseLexica.WHILE, yytext()); }
  
  // Símbolos
  ";"         { reportarToken(ClaseLexica.PYC, yytext()); }
  ","         { reportarToken(ClaseLexica.COMA, yytext()); }
  "("         { reportarToken(ClaseLexica.LPAR, yytext()); }
  ")"         { reportarToken(ClaseLexica.RPAR, yytext()); }

  // Números
  {numeroFloat} { reportarToken(ClaseLexica.NUMERO, yytext()); }
  {numeroInt}   { reportarToken(ClaseLexica.NUMERO, yytext()); }

  // Identificadores
  {identificador} { reportarToken(ClaseLexica.ID, yytext()); }

  // Espacios
  {espacio} { /* Ignorar espacios en blanco, tabulaciones y saltos de línea */ }

  // No reconocidos
  . { System.err.println("Carácter no reconocido: " + yytext()); }
}
