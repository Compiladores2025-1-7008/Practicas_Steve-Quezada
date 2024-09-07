/**
 * Escáner que detecta el lenguaje C_1
*/

package main.jflex;

import main.java.ClaseLexica;
import main.java.Token;

%%

%{

public Token actual;

%}

%public
%class Lexer
%standalone
%unicode

espacio=[ \t\n]

%%

{espacio} {/* La acción léxica puede ir vacía si queremos que el escáner ignore la regla */}