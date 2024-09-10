/**
 * Escáner que detecta tokens del lenguaje C_1 utilizando JFlex.
 * Este analizador léxico reconoce palabras clave, identificadores, 
 * números y símbolos en el lenguaje C_1.
 *
 * Los tokens únicos no almacenan su lexema, mientras que aquellos que 
 * varían guardan el lexema correspondiente.
 */

package main.jflex;

import main.java.ClaseLexica;
import main.java.Token;

%%

%{

/**
 * Método que imprime en consola el tipo de token, sin almacenar el lexema.
 * Se utiliza para tokens que tienen un único miembro en su clase léxica.
 * @param tipoToken Clase léxica del token.
 */
private void reportarTokenSinLexema(ClaseLexica tipoToken) {
    System.out.println("<" + tipoToken + ">");
}

/**
 * Método que imprime en consola el tipo de token junto con su lexema.
 * Se utiliza para tokens que pueden variar en su valor.
 * @param tipoToken Clase léxica del token.
 * @param lexema El texto o valor del token identificado.
 */
private void reportarTokenConLexema(ClaseLexica tipoToken, String lexema) {
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

  // Palabras clave (no guardan lexema porque son únicas)
  "int"       { reportarTokenSinLexema(ClaseLexica.INT); }
  "float"     { reportarTokenSinLexema(ClaseLexica.FLOAT); }
  "if"        { reportarTokenSinLexema(ClaseLexica.IF); }
  "else"      { reportarTokenSinLexema(ClaseLexica.ELSE); }
  "while"     { reportarTokenSinLexema(ClaseLexica.WHILE); }
  
  // Símbolos (no guardan lexema porque son únicos)
  ";"         { reportarTokenSinLexema(ClaseLexica.PYC); }
  ","         { reportarTokenSinLexema(ClaseLexica.COMA); }
  "("         { reportarTokenSinLexema(ClaseLexica.LPAR); } 
  ")"         { reportarTokenSinLexema(ClaseLexica.RPAR); }
  "{"         { reportarTokenSinLexema(ClaseLexica.LLLA); }
  "}"         { reportarTokenSinLexema(ClaseLexica.RLLA); }
  "="         { reportarTokenSinLexema(ClaseLexica.ASIGNACION); }
  "=="        { reportarTokenSinLexema(ClaseLexica.IGUALDAD); }
  ">"         { reportarTokenSinLexema(ClaseLexica.MAYORQUE); }
  "<"         { reportarTokenSinLexema(ClaseLexica.MENORQUE); }
  "+"         { reportarTokenSinLexema(ClaseLexica.SUMA); }
  "-"         { reportarTokenSinLexema(ClaseLexica.RESTA); }

  // Números (sí guardan lexema porque varían)
  {numeroFloat} { reportarTokenConLexema(ClaseLexica.NUMERO, yytext()); }
  {numeroInt}   { reportarTokenConLexema(ClaseLexica.NUMERO, yytext()); }

  // Identificadores (sí guardan lexema porque varían)
  {identificador} { reportarTokenConLexema(ClaseLexica.ID, yytext()); }

  // Espacios
  {espacio} { /* Ignorar espacios */ }

  // No reconocidos
  . { System.err.println("Carácter no reconocido: " + yytext()); }
}
