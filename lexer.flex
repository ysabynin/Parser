import java.lang.*;
%%
%class Lexer
%unicode
%byaccj
%line
%column
%public


%{
    StringBuffer string = new StringBuffer();
	public int yyline(){
	return yyline;}
	public int yycolumn(){
	return  yycolumn;}
%}


not_num = [0-9]+[a-zA-Z]+
num = ("-")?[0-9]+
comment = ("(*"([^"*"]|[\r\n]|("*"+([^"*"\"]|[\r\n])))*"*"+")")|("//"[^.\r\n]*)
var = [A-Za-z_][A-Za-z0-9_]*
operator = "+" | "-" | "**"| "*" | "/" | "%" | "==" | "!=" | ">" | ">=" | "<" | "<=" | "&&" | "||"
lbracket = "("
rbracket = ")"
%%

{not_num}	{
		System.out.println(String.format("ERROR: unexpected lexem (%s, %d, %d, %d); ",lexer.yytext(),lexer.yyline(),lexer.yycolumn(),lexer.yycolumn() + lexer.yytext().length()-1));
		System.exit(-1);
}

{comment} {

 	return Parser.COMMENT;
 	}

{num} {
 	return Parser.NUM;
 	}

{rbracket} {
 	return Parser.RBRACKET;
 	}
{lbracket} {
 	return Parser.LBRACKET;
 	}

"(//.*)" {
 	return Parser.COMMENT;
 	}

{operator} {
 	return Parser.OPERATOR;
 	}

 " " { }

 "skip" {
 	return Parser.SKIP;
 	}

 "write" {
 	return Parser.WRITE;
 	}

 "read" {
 	return Parser.READ;
 	}

 "while" {
 	return Parser.WHILE;
 	}

  "do" {
 	return Parser.DO;
 	}

   "if" {
 	return Parser.IF;
 	}

   "then" {
 	return Parser.THEN;
 	}

    "else" {
 	return Parser.ELSE;
 	}

    ";" {
 	return Parser.COLON;
 	}

 	":=" {
 	return Parser.COLONEQUAL;
 	}

 	":" {
 	return Parser.SEMICOLON;
 	}

"\n" | "\r" | "\r\n" | "\t" {}

{var} {
 	return Parser.VAR;
 	}


[^]    {
		System.out.println(String.format("ERROR: unexpected lexem (%s, %d, %d, %d); ",lexer.yytext(),lexer.yyline(),lexer.yycolumn(),lexer.yycolumn() + lexer.yytext().length()-1));
		System.exit(-1);
}
