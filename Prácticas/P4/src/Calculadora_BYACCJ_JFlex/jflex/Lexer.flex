package src.Calculadora_BYACCJ_JFlex;

/* 
 * Analizador Léxico para Calculadora
 * 
 * Este archivo define los tokens y patrones para una calculadora que soporta:
 * - Números enteros y decimales
 * - Operadores: +, -, *, /, ^
 * - Paréntesis
 * - Espacios en blanco
 *
 * @author steve-quezada
 */

%%

%class Lexer
%byaccj
%unicode

%{
  /* Constructor y variables de instancia */
  private Parser yyparser;

  /**
   * Constructor del analizador léxico
   * @param r Reader para la entrada
   * @param yyparser Referencia al parser
   */
  public Lexer(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }
%}

/* Definición de patrones */
NUM = [0-9]+ ("." [0-9]*)?    /* Números enteros o decimales */
WHITE = [ \t\r]               /* Espacios, tabs y retornos */

%%
/* Reglas léxicas */

{NUM}    { yyparser.yylval = new ParserVal(Double.parseDouble(yytext())); return Parser.NUM; }
"+"      { return Parser.PLUS; }
"-"      { return Parser.MINUS; }
"*"      { return Parser.TIMES; }
"/"      { return Parser.DIVIDE; }
"^"      { return Parser.POWER; }
"("      { return Parser.LPAREN; }
")"      { return Parser.RPAREN; }
\n       { return Parser.EOL; }
{WHITE}  { /* ignorar espacios en blanco */ }
.        { System.err.println("Caracter ilegal: " + yytext()); }
