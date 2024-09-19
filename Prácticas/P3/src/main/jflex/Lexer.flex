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

espacio=[ \t\n]

%%

{espacio}+ { }
"int" { System.out.println("Encontramos una palabra reservada"); return ClaseLexica.INT; }
//Aquí el resto de las definiciones
<<EOF>> { return 0; }
. { return -1; }
