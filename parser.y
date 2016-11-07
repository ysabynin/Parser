%{
  import java.io.*;
  import java.nio.file.Files;
  import java.nio.file.Paths;
%}
      
%token NUM 
%token OPERATOR 
%token LBRACKET
%token RBRACKET
%token SPACE
%token VAR 
%token SKIP 
%token WRITE 
%token READ 
%token WHILE 
%token DO 
%token IF 
%token THEN 
%token ELSE 
%token SEMICOLON 
%token COLONEQUAL 
%token COLON
%token COMMENT

%left OPERATOR

%nonassoc    SPACE 

%%
      
      
s0: s {$1.nodeval.print(0);};
s:   SKIP {$$.nodeval = new NodeElement($1.sval);}
	|VAR COLONEQUAL expr {
							$$.nodeval = new NodeElement("S -> var := E"); 
							$$.nodeval.addChildren(new NodeElement($1.sval)); 
							$$.nodeval.addChildren(new NodeElement($2.sval)); 
							$$.nodeval.addChildren($3.nodeval);}
	|s COLON s 			{
							$$.nodeval = new NodeElement("S -> S;S"); 
							$$.nodeval.addChildren($1.nodeval); 
							$$.nodeval.addChildren(new NodeElement($2.sval));  
							$$.nodeval.addChildren($3.nodeval); }
	|WRITE expr 		{
							$$.nodeval = new NodeElement("S -> write E");
							$$.nodeval.addChildren(new NodeElement($1.sval)); 
							$$.nodeval.addChildren($2.nodeval); }
	|READ VAR 			{
							$$.nodeval = new NodeElement("S -> read var");
							$$.nodeval.addChildren(new NodeElement($1.sval)); 
							$$.nodeval.addChildren(new NodeElement($2.sval)); }
	|WHILE expr DO s 	{
							$$.nodeval = new NodeElement("S -> while E do S"); 
							$$.nodeval.addChildren(new NodeElement($1.sval)); 
							$$.nodeval.addChildren($2.nodeval); 
							$$.nodeval.addChildren(new NodeElement($3.sval));
							$$.nodeval.addChildren($4.nodeval);}

	|IF expr THEN s ELSE s {
							$$.nodeval = new NodeElement("S -> if E then S else S"); 
							$$.nodeval.addChildren(new NodeElement($1.sval)); 
							$$.nodeval.addChildren($2.nodeval); 
							$$.nodeval.addChildren(new NodeElement($3.sval));
							$$.nodeval.addChildren($4.nodeval);
							$$.nodeval.addChildren(new NodeElement($5.sval));
							$$.nodeval.addChildren($6.nodeval);}
;

expr: 
	NUM						{$$.nodeval = new NodeElement($1.sval);}
	|VAR 					{$$.nodeval = new NodeElement($1.sval);}
	|LBRACKET expr OPERATOR expr RBRACKET { 
											$$.nodeval = new NodeElement("E -> (E operator E)"); 
											$$.nodeval.addChildren(new NodeElement($1.sval)); 
											$$.nodeval.addChildren($2.nodeval); 
											$$.nodeval.addChildren(new NodeElement($3.sval));
											$$.nodeval.addChildren($4.nodeval);
											$$.nodeval.addChildren(new NodeElement($5.sval));}
	|expr OPERATOR expr					  { 
											$$.nodeval = new NodeElement("E -> E operator E"); 
											$$.nodeval.addChildren($1.nodeval); 
											$$.nodeval.addChildren(new NodeElement($2.sval)); 
											$$.nodeval.addChildren($3.nodeval);} 
	
;   

%%
	
  private Lexer lexer;
  private static boolean escape = false;

  private int yylex () {
    int yyl_return = -1;
    try {

      yyl_return = lexer.yylex();
	  int token = yyl_return;
	  String outputText = "";
		if(token==Parser.VAR){
			outputText = String.format("Variable(\"%s\", %d, %d, %d); ",lexer.yytext(),lexer.yyline(),lexer.yycolumn(),lexer.yycolumn() + lexer.yytext().length()-1);
		}
		else if(token==Parser.NUM){
			outputText = String.format("Number(%s, %d, %d, %d); ",lexer.yytext(),lexer.yyline(),lexer.yycolumn(),lexer.yycolumn() + lexer.yytext().length()-1);
		}
		else if(token==Parser.OPERATOR){
			outputText = String.format("Operator(%s, %d, %d, %d); ",lexer.yytext(),lexer.yyline(),lexer.yycolumn(),lexer.yycolumn() + lexer.yytext().length()-1);
		}
		else if(token==Parser.COMMENT){
			outputText = String.format("Comment(\"%s\", %d, %d, %d); ",lexer.yytext().replaceAll("\\p{Cntrl}", ""),lexer.yyline(),lexer.yycolumn(),lexer.yycolumn() + lexer.yytext().length()-1);
		}
		else
		{
			String tokenName = null;
			switch (token){
				case Parser.RBRACKET:               
					tokenName = "R_bracket"; 
					break;
				case Parser.LBRACKET:               
					tokenName = "L_bracket"; 
					break;
				case Parser.SKIP:               
					tokenName = "Skip"; 
					break;
				case Parser.WRITE:               
					tokenName = "Write"; 
					break;
				case Parser.READ:               
					tokenName = "Read"; 
					break;
				case Parser.WHILE:               
					tokenName = "While"; 
					break;
				case Parser.DO:               
					tokenName = "Do"; 
					break;
				case Parser.IF:               
					tokenName = "If"; 
					break;
				case Parser.THEN:               
					tokenName = "Then"; 
					break;
				case Parser.ELSE:               
					tokenName = "Else"; 
					break;
				case Parser.SEMICOLON:               
					tokenName = "Semicolon"; 
					break;
				case Parser.COLONEQUAL:               
					tokenName = "Colonequal"; 
					break;
				case Parser.COLON:               
					tokenName = "Colon"; 
					break;
				default: 
					token =-1;
					break;
			}
			outputText = String.format("%s(%d, %d, %d); ",tokenName,lexer.yyline(),lexer.yycolumn(),lexer.yycolumn() + lexer.yytext().length()-1);
		}
			
	  yylval = new ParserVal(outputText);
    }
    catch (IOException e) {
      System.err.println("Reading error :"+e);
    }
    return yyl_return;
  }

  public void yyerror (String error) {
    System.out.println ("Cannot parse current statement!");
  }

  public Parser(Reader r) {
    lexer = new Lexer(r);
  }
