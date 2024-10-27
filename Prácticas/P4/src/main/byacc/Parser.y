%{
  import java.lang.Math;
  import java.io.Reader;
  import java.io.IOException;
  import main.jflex.Lexer;
%}

/* YACC Declarations */
%token NUM NL

 /* Grammar follows */
%%
input: /* empty string */
| input line
;

line: NL
| NUM { System.out.println(" " + $1.dval + " "); }
;

%%


Lexer scanner;


public Parser(Reader r) {
  this.scanner = new Lexer(r, this);
}

public void setYylval(ParserVal yylval) {
  this.yylval = yylval;
}

public void parse() {
  this.yyparse();
}

void yyerror(String s)
{
  System.out.println("Error sintactico:"+s);
}


int yylex()
{
  int yyl_return = -1;
  try {
    yyl_return = scanner.yylex();
  }
  catch (IOException e) {
    System.err.println("IO error :"+e);
  }
  return yyl_return;
}




