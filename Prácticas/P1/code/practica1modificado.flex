/**
 * Escáner que detecta números, palabras, hexadecimales, palabras reservadas e identificadores en Java
 */

%%

%public
%class Lexer
%standalone

// Expresiones regulares básicas
digito=[0-9]
letra=[a-zA-Z]
letraDigito=[a-zA-Z0-9]
espacio=[ \t\n]

// Expresiones regulares adicionales
hexadecimal=0[xX][0-9a-fA-F]+
reservadas=(if|else|while|return|class)
identificador={letra}({letraDigito}|_){0,31}
identificadorlargo={letra}({letraDigito}|_){32,64}

%%

{espacio} { System.out.print("Se encontró un espacio en blanco: "+yytext()+"\n"); }
{hexadecimal} { System.out.print("Se encontró un hexadecimal: "+yytext()+"\n"); }
{reservadas { System.out.print("Se encontró una palabra reservada: "+yytext()+"\n"); }
{identificador} { System.out.print("Se encontró un identificador: "+yytext()+"\n"); }
{identificadorlargo} { System.out.print("Se encontró  un identificador no valido: "+yytext()+"\n"); }
{digito}+ { System.out.print("Se encontró un número: "+yytext()+"\n"); }



