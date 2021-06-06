%{
    #include<stdio.h>
%}

%%
"//".* {printf("%s==> Comments\n",yytext);}
"System.out.println""(".*")" {printf("%s ==> Print Statement\n", yytext);}
"import "+"java"[^\n]*";" {printf("%s ==> Preprocessor\n",yytext);}
"class"|"static"|"main"|"abstract"|"interface" {printf("%s ==> Keyword\n",yytext);}
"public"|"private"|"protected" {printf("%s ==> Access Specifier\n",yytext);}
"int"|"double"|"float"|"String"|"char"|"short"|"long"|"void" {printf("%s ==> Datatype\n", yytext);}
"{" {printf("%s ==> Block Begin\n",yytext);}
"}" {printf("%s ==> Block End\n",yytext);}
"(" {printf("%s ==> Parenthesis Begin\n",yytext);}
")" {printf("%s ==> Parenthesis Close\n",yytext);}
";" {printf("%s ==> Delimiter\n",yytext);}
[0-9]+ {printf("%s ==> Number\n",yytext);}
[A-Z]+[a-zA-Z0-9]* {printf("%s ==> Class Name\n",yytext);}
[a-zA-z]+[a-zA-z0-9]* {printf("%s ==> Variable\n",yytext);}
"="|"+"|"-"|"*"|"/"|"%" {printf("%s ==> Operator\n",yytext);}
"/*".*\n {printf("%s\n",yytext);}
.*"*/"\n {printf("%s ==> Comments\n",yytext);}
%%

int yywrap(){
    return 1;
}
int main(){
    yyin = fopen("Input.java","r");
    yylex();
    return 0;
}