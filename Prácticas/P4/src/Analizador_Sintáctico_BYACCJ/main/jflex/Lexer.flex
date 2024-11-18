package main.jflex;

import main.byacc.Parser;
import main.byacc.ParserVal;
import java.io.Reader;


%%

%{

private Parser yyparser;

public Lexer(Reader r, Parser yyparser) {
       this(r);
       this.yyparser = yyparser;
}

public int getLine() { return yyline; }

%}

%public
%class Lexer
%standalone
%unicode
%line

espacio=[ \t]
entero=[0-9]+


%%

{espacio}+ { }
{entero} { yyparser.setYylval(new ParserVal(Double.parseDouble(yytext()))); return Parser.NUM; }
"\n" { return Parser.NL; }

//Aquí el resto de las definiciones
<<EOF>> { return 0; }
. { return -1; }
