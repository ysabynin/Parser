package com.ysabynin;//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";


//#line 2 "parser.y"

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
//#line 21 "Parser.java"


public class Parser {

    boolean yydebug;        //do I want debug output?
    int yynerrs;            //number of errors so far
    int yyerrflag;          //was there an error?
    int yychar;             //the current working character

    //########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
    void debug(String msg) {
        if (yydebug)
            System.out.println(msg);
    }

    //########## STATE STACK ##########
    final static int YYSTACKSIZE = 500;  //maximum stack size
    int statestk[] = new int[YYSTACKSIZE]; //state stack
    int stateptr;
    int stateptrmax;                     //highest index of stackptr
    int statemax;                        //state when highest index reached

    //###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
    final void state_push(int state) {
        try {
            stateptr++;
            statestk[stateptr] = state;
        } catch (ArrayIndexOutOfBoundsException e) {
            int oldsize = statestk.length;
            int newsize = oldsize * 2;
            int[] newstack = new int[newsize];
            System.arraycopy(statestk, 0, newstack, 0, oldsize);
            statestk = newstack;
            statestk[stateptr] = state;
        }
    }

    final int state_pop() {
        return statestk[stateptr--];
    }

    final void state_drop(int cnt) {
        stateptr -= cnt;
    }

    final int state_peek(int relative) {
        return statestk[stateptr - relative];
    }

    //###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
    final boolean init_stacks() {
        stateptr = -1;
        val_init();
        return true;
    }

    //###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
    void dump_stacks(int count) {
        int i;
        System.out.println("=index==state====value=     s:" + stateptr + "  v:" + valptr);
        for (i = 0; i < count; i++)
            System.out.println(" " + i + "    " + statestk[i] + "      " + valstk[i]);
        System.out.println("======================");
    }


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


    String yytext;//user variable to return contextual strings
    ParserVal yyval; //used to return semantic vals from action routines
    ParserVal yylval;//the 'lval' (result) I got from yylex()
    ParserVal valstk[];
    int valptr;

    //###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
    void val_init() {
        valstk = new ParserVal[YYSTACKSIZE];
        yyval = new ParserVal();
        yylval = new ParserVal();
        valptr = -1;
    }

    void val_push(ParserVal val) {
        if (valptr >= YYSTACKSIZE)
            return;
        valstk[++valptr] = val;
    }

    ParserVal val_pop() {
        if (valptr < 0)
            return new ParserVal();
        return valstk[valptr--];
    }

    void val_drop(int cnt) {
        int ptr;
        ptr = valptr - cnt;
        if (ptr < 0)
            return;
        valptr = ptr;
    }

    ParserVal val_peek(int relative) {
        int ptr;
        ptr = valptr - relative;
        if (ptr < 0)
            return new ParserVal();
        return valstk[ptr];
    }

    final ParserVal dup_yyval(ParserVal val) {
        ParserVal dup = new ParserVal();
        dup.ival = val.ival;
        dup.dval = val.dval;
        dup.sval = val.sval;
        dup.obj = val.obj;
        return dup;
    }

    //#### end semantic value section ####
    public final static short NUM = 257;
    public final static short OPERATOR = 258;
    public final static short LBRACKET = 259;
    public final static short RBRACKET = 260;
    public final static short SPACE = 261;
    public final static short VAR = 262;
    public final static short SKIP = 263;
    public final static short WRITE = 264;
    public final static short READ = 265;
    public final static short WHILE = 266;
    public final static short DO = 267;
    public final static short IF = 268;
    public final static short THEN = 269;
    public final static short ELSE = 270;
    public final static short SEMICOLON = 271;
    public final static short COLONEQUAL = 272;
    public final static short COLON = 273;
    public final static short COMMENT = 274;
    public final static short YYERRCODE = 256;
    final static short yylhs[] = {-1,
            0, 1, 1, 1, 1, 1, 1, 1, 2, 2,
            2, 2,
    };
    final static short yylen[] = {2,
            1, 1, 3, 3, 2, 2, 4, 6, 1, 1,
            5, 3,
    };
    final static short yydefred[] = {0,
            0, 2, 0, 0, 0, 0, 0, 0, 0, 9,
            0, 10, 0, 6, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 12, 0, 0, 0, 0, 11,
            0,
    };
    final static short yydgoto[] = {7,
            8, 13,
    };
    final static short yysindex[] = {-237,
            -260, 0, -227, -246, -227, -227, 0, -255, -227, 0,
            -227, 0, -236, 0, -243, -258, -237, -236, -225, -227,
            -237, -237, -255, -227, 0, -255, -253, -226, -237, 0,
            -255,
    };
    final static short yyrindex[] = {0,
            0, 0, 0, 0, 0, 0, 0, 36, 0, 0,
            0, 0, 1, 0, 0, 0, 0, 2, 0, 0,
            0, 0, 3, 0, 0, 6, 0, -221, 0, 0,
            7,
    };
    final static short yygindex[] = {0,
            -8, -1,
    };
    final static int YYTABLESIZE = 277;
    static short yytable[];

    static {
        yytable();
    }

    static void yytable() {
        yytable = new short[]{20,
                5, 3, 4, 15, 16, 7, 8, 18, 23, 19,
                22, 9, 26, 27, 20, 14, 29, 17, 25, 17,
                31, 20, 28, 21, 1, 2, 3, 4, 5, 10,
                6, 11, 24, 30, 12, 1, 12, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                5, 3, 4, 5, 3, 7, 8,
        };
    }

    static short yycheck[];

    static {
        yycheck();
    }

    static void yycheck() {
        yycheck = new short[]{258,
                0, 0, 0, 5, 6, 0, 0, 9, 17, 11,
                269, 272, 21, 22, 258, 262, 270, 273, 20, 273,
                29, 258, 24, 267, 262, 263, 264, 265, 266, 257,
                268, 259, 258, 260, 262, 0, 258, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                270, 270, 270, 273, 273, 270, 270,
        };
    }

    final static short YYFINAL = 7;
    final static short YYMAXTOKEN = 274;
    final static String yyname[] = {
            "end-of-file", null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, "NUM", "OPERATOR", "LBRACKET", "RBRACKET", "SPACE", "VAR", "SKIP",
            "WRITE", "READ", "WHILE", "DO", "IF", "THEN", "ELSE", "SEMICOLON", "COLONEQUAL", "COLON",
            "COMMENT",
    };
    final static String yyrule[] = {
            "$accept : s0",
            "s0 : s",
            "s : SKIP",
            "s : VAR COLONEQUAL expr",
            "s : s COLON s",
            "s : WRITE expr",
            "s : READ VAR",
            "s : WHILE expr DO s",
            "s : IF expr THEN s ELSE s",
            "expr : NUM",
            "expr : VAR",
            "expr : LBRACKET expr OPERATOR expr RBRACKET",
            "expr : expr OPERATOR expr",
    };

//#line 89 "parser.y"

    private Lexer lexer;
    private static boolean escape = false;

    private int yylex() {
        int yyl_return = -1;
        try {

            yyl_return = lexer.yylex();
            int token = yyl_return;
            String outputText = "";
            if (token == Parser.VAR) {
                outputText = String.format("Variable(\"%s\", %d, %d, %d); ", lexer.yytext(), lexer.yyline(), lexer.yycolumn(), lexer.yycolumn() + lexer.yytext().length() - 1);
            } else if (token == Parser.NUM) {
                outputText = String.format("Number(%s, %d, %d, %d); ", lexer.yytext(), lexer.yyline(), lexer.yycolumn(), lexer.yycolumn() + lexer.yytext().length() - 1);
            } else if (token == Parser.OPERATOR) {
                outputText = String.format("Operator(%s, %d, %d, %d); ", lexer.yytext(), lexer.yyline(), lexer.yycolumn(), lexer.yycolumn() + lexer.yytext().length() - 1);
            } else if (token == Parser.COMMENT) {
                outputText = String.format("Comment(\"%s\", %d, %d, %d); ", lexer.yytext().replaceAll("\\p{Cntrl}", ""), lexer.yyline(), lexer.yycolumn(), lexer.yycolumn() + lexer.yytext().length() - 1);
            } else {
                String tokenName = null;
                switch (token) {
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
                        token = -1;
                        break;
                }
                outputText = String.format("%s(%d, %d, %d); ", tokenName, lexer.yyline(), lexer.yycolumn(), lexer.yycolumn() + lexer.yytext().length() - 1);
            }

            yylval = new ParserVal(outputText);
        } catch (IOException e) {
            System.err.println("Reading error :" + e);
        }
        return yyl_return;
    }

    public void yyerror(String error) {
        System.out.println("Unexpected lexem: "+
                String.format("%s(%d, %d, %d)", lexer.yytext(), lexer.yyline(), lexer.yycolumn(), lexer.yycolumn() + lexer.yytext().length() - 1)
        );
    }

    public Parser(Reader r) {
        lexer = new Lexer(r);
    }

    //#line 338 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
    void yylexdebug(int state, int ch) {
        String s = null;
        if (ch < 0) ch = 0;
        if (ch <= YYMAXTOKEN) //check index bounds
            s = yyname[ch];    //now get it
        if (s == null)
            s = "illegal-symbol";
        debug("state " + state + ", reading " + ch + " (" + s + ")");
    }


    //The following are now global, to aid in error reporting
    int yyn;       //next next thing to do
    int yym;       //
    int yystate;   //current parsing state from state table
    String yys;    //current token string


    //###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
    int yyparse() {
        boolean doaction;
        init_stacks();
        yynerrs = 0;
        yyerrflag = 0;
        yychar = -1;          //impossible char forces a read
        yystate = 0;            //initial state
        state_push(yystate);  //save it
        val_push(yylval);     //save empty value
        while (true) //until parsing is done, either correctly, or w/error
        {
            doaction = true;
            if (yydebug) debug("loop");
            //#### NEXT ACTION (from reduction table)
            for (yyn = yydefred[yystate]; yyn == 0; yyn = yydefred[yystate]) {
                if (yydebug) debug("yyn:" + yyn + "  state:" + yystate + "  yychar:" + yychar);
                if (yychar < 0)      //we want a char?
                {
                    yychar = yylex();  //get next token
                    if (yydebug) debug(" next yychar:" + yychar);
                    //#### ERROR CHECK ####
                    if (yychar < 0)    //it it didn't work/error
                    {
                        yychar = 0;      //change it to default string (no -1!)
                        if (yydebug)
                            yylexdebug(yystate, yychar);
                    }
                }//yychar<0
                yyn = yysindex[yystate];  //get amount to shift by (shift index)
                if ((yyn != 0) && (yyn += yychar) >= 0 &&
                        yyn <= YYTABLESIZE && yycheck[yyn] == yychar) {
                    if (yydebug)
                        debug("state " + yystate + ", shifting to state " + yytable[yyn]);
                    //#### NEXT STATE ####
                    yystate = yytable[yyn];//we are in a new state
                    state_push(yystate);   //save it
                    val_push(yylval);      //push our lval as the input for next rule
                    yychar = -1;           //since we have 'eaten' a token, say we need another
                    if (yyerrflag > 0)     //have we recovered an error?
                        --yyerrflag;        //give ourselves credit
                    doaction = false;        //but don't process yet
                    break;   //quit the yyn=0 loop
                }

                yyn = yyrindex[yystate];  //reduce
                if ((yyn != 0) && (yyn += yychar) >= 0 &&
                        yyn <= YYTABLESIZE && yycheck[yyn] == yychar) {   //we reduced!
                    if (yydebug) debug("reduce");
                    yyn = yytable[yyn];
                    doaction = true; //get ready to execute
                    break;         //drop down to actions
                } else //ERROR RECOVERY
                {
                    if (yyerrflag == 0) {
                        yyerror("syntax error");
                        yynerrs++;
                    }
                    if (yyerrflag < 3) //low error count?
                    {
                        yyerrflag = 3;
                        while (true)   //do until break
                        {
                            if (stateptr < 0)   //check for under & overflow here
                            {
                                yyerror("stack underflow. aborting...");  //note lower case 's'
                                return 1;
                            }
                            yyn = yysindex[state_peek(0)];
                            if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE) {
                                if (yydebug)
                                    debug("state " + state_peek(0) + ", error recovery shifting to state " + yytable[yyn] + " ");
                                yystate = yytable[yyn];
                                state_push(yystate);
                                val_push(yylval);
                                doaction = false;
                                break;
                            } else {
                                if (yydebug)
                                    debug("error recovery discarding state " + state_peek(0) + " ");
                                if (stateptr < 0)   //check for under & overflow here
                                {
                                    yyerror("Stack underflow. aborting...");  //capital 'S'
                                    return 1;
                                }
                                state_pop();
                                val_pop();
                            }
                        }
                    } else            //discard this token
                    {
                        if (yychar == 0)
                            return 1; //yyabort
                        if (yydebug) {
                            yys = null;
                            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
                            if (yys == null) yys = "illegal-symbol";
                            debug("state " + yystate + ", error recovery discards token " + yychar + " (" + yys + ")");
                        }
                        yychar = -1;  //read another
                    }
                }//end error recovery
            }//yyn=0 loop
            if (!doaction)   //any reason not to proceed?
                continue;      //skip action
            yym = yylen[yyn];          //get count of terminals on rhs
            if (yydebug)
                debug("state " + yystate + ", reducing " + yym + " by rule " + yyn + " (" + yyrule[yyn] + ")");
            if (yym > 0)                 //if count of rhs not 'nil'
                yyval = val_peek(yym - 1); //get current semantic value
            yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
            switch (yyn) {
//########## USER-SUPPLIED ACTIONS ##########
                case 1:
//#line 33 "parser.y"
                {
                    val_peek(0).nodeval.print(0);
                }
                break;
                case 2:
//#line 34 "parser.y"
                {
                    yyval.nodeval = new NodeElement(val_peek(0).sval);
                }
                break;
                case 3:
//#line 35 "parser.y"
                {
                    yyval.nodeval = new NodeElement("S -> var := E");
                    yyval.nodeval.addChildren(new NodeElement(val_peek(2).sval));
                    yyval.nodeval.addChildren(new NodeElement(val_peek(1).sval));
                    yyval.nodeval.addChildren(val_peek(0).nodeval);
                }
                break;
                case 4:
//#line 40 "parser.y"
                {
                    yyval.nodeval = new NodeElement("S -> S;S");
                    yyval.nodeval.addChildren(val_peek(2).nodeval);
                    yyval.nodeval.addChildren(new NodeElement(val_peek(1).sval));
                    yyval.nodeval.addChildren(val_peek(0).nodeval);
                }
                break;
                case 5:
//#line 45 "parser.y"
                {
                    yyval.nodeval = new NodeElement("S -> write E");
                    yyval.nodeval.addChildren(new NodeElement(val_peek(1).sval));
                    yyval.nodeval.addChildren(val_peek(0).nodeval);
                }
                break;
                case 6:
//#line 49 "parser.y"
                {
                    yyval.nodeval = new NodeElement("S -> read var");
                    yyval.nodeval.addChildren(new NodeElement(val_peek(1).sval));
                    yyval.nodeval.addChildren(new NodeElement(val_peek(0).sval));
                }
                break;
                case 7:
//#line 53 "parser.y"
                {
                    yyval.nodeval = new NodeElement("S -> while E do S");
                    yyval.nodeval.addChildren(new NodeElement(val_peek(3).sval));
                    yyval.nodeval.addChildren(val_peek(2).nodeval);
                    yyval.nodeval.addChildren(new NodeElement(val_peek(1).sval));
                    yyval.nodeval.addChildren(val_peek(0).nodeval);
                }
                break;
                case 8:
//#line 60 "parser.y"
                {
                    yyval.nodeval = new NodeElement("S -> if E then S else S");
                    yyval.nodeval.addChildren(new NodeElement(val_peek(5).sval));
                    yyval.nodeval.addChildren(val_peek(4).nodeval);
                    yyval.nodeval.addChildren(new NodeElement(val_peek(3).sval));
                    yyval.nodeval.addChildren(val_peek(2).nodeval);
                    yyval.nodeval.addChildren(new NodeElement(val_peek(1).sval));
                    yyval.nodeval.addChildren(val_peek(0).nodeval);
                }
                break;
                case 9:
//#line 71 "parser.y"
                {
                    yyval.nodeval = new NodeElement(val_peek(0).sval);
                }
                break;
                case 10:
//#line 72 "parser.y"
                {
                    yyval.nodeval = new NodeElement(val_peek(0).sval);
                }
                break;
                case 11:
//#line 73 "parser.y"
                {
                    yyval.nodeval = new NodeElement("E -> (E operator E)");
                    yyval.nodeval.addChildren(new NodeElement(val_peek(4).sval));
                    yyval.nodeval.addChildren(val_peek(3).nodeval);
                    yyval.nodeval.addChildren(new NodeElement(val_peek(2).sval));
                    yyval.nodeval.addChildren(val_peek(1).nodeval);
                    yyval.nodeval.addChildren(new NodeElement(val_peek(0).sval));
                }
                break;
                case 12:
//#line 80 "parser.y"
                {
                    yyval.nodeval = new NodeElement("E -> E operator E");
                    yyval.nodeval.addChildren(val_peek(2).nodeval);
                    yyval.nodeval.addChildren(new NodeElement(val_peek(1).sval));
                    yyval.nodeval.addChildren(val_peek(0).nodeval);
                }
                break;
//#line 571 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
            }//switch
            //#### Now let's reduce... ####
            if (yydebug) debug("reduce");
            state_drop(yym);             //we just reduced yylen states
            yystate = state_peek(0);     //get new state
            val_drop(yym);               //corresponding value drop
            yym = yylhs[yyn];            //select next TERMINAL(on lhs)
            if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
            {
                if (yydebug) debug("After reduction, shifting from state 0 to state " + YYFINAL + "");
                yystate = YYFINAL;         //explicitly say we're done
                state_push(YYFINAL);       //and save it
                val_push(yyval);           //also save the semantic value of parsing
                if (yychar < 0)            //we want another character?
                {
                    yychar = yylex();        //get next character
                    if (yychar < 0) yychar = 0;  //clean, if necessary
                    if (yydebug)
                        yylexdebug(yystate, yychar);
                }
                if (yychar == 0)          //Good exit (if lex returns 0 ;-)
                    break;                 //quit the loop--all DONE
            }//if yystate
            else                        //else not done yet
            {                         //get next state and push, for next yydefred[]
                yyn = yygindex[yym];      //find out where to go
                if ((yyn != 0) && (yyn += yystate) >= 0 &&
                        yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
                    yystate = yytable[yyn]; //get new state
                else
                    yystate = yydgoto[yym]; //else go to new defred
                if (yydebug)
                    debug("after reduction, shifting from state " + state_peek(0) + " to state " + yystate + "");
                state_push(yystate);     //going again, so push state & val...
                val_push(yyval);         //for next action
            }
        }//main loop
        return 0;//yyaccept!!
    }
//## end of method parse() ######################################


//## run() --- for Thread #######################################

    /**
     * A default run method, used for operating this parser
     * object in the background.  It is intended for extending Thread
     * or implementing Runnable.  Turn off with -Jnorun .
     */
    public void run() {
        yyparse();
    }
//## end of method run() ########################################


//## Constructors ###############################################

    /**
     * Default constructor.  Turn off with -Jnoconstruct .
     */
    public Parser() {
        //nothing to do
    }


    /**
     * Create a parser, setting the debug to true or false.
     *
     * @param debugMe true for debugging, false for no debug.
     */
    public Parser(boolean debugMe) {
        yydebug = debugMe;
    }
//###############################################################


}
//################### END OF CLASS ##############################
