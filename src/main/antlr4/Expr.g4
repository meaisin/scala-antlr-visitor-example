grammar Expr;
import CommonLexerRules;

prog: stat+;

stat: expr NL               # printExpr
    | ID '=' expr NL        # assign
    | NL                    # blank
    ;

expr: expr op=('*'|'/') expr   # MulDiv
    | expr op=('+'|'-') expr   # AddSub
    | INT                   # int
    | ID                    # id
    | '(' expr ')'          # parens
    ;

MUL: '*';
DIV: '/';
ADD: '+';
SUB: '-';
