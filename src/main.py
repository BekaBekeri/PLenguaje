import sys
from antlr4 import *
from moomaLexer import moomaLexer
from moomaParser import moomaParser
from moomaListener import moomaListener
from myMoomaListener import MyMoomaListener
from moomaErrorListener import moomaErrorListener


class KeyPrinter(moomaListener):
    def enterProgram(self, ctx:moomaParser.ProgramContext):
        print("Empezamos")

    def exitProgram(self, ctx:moomaParser.ProgramContext):
        print("Acabamos")

    def enterOutput(self, ctx:moomaParser.OutputContext):
        cosa = ctx.getText().split(":=")
        ident = ctx.Ident()
        code = str(ctx.Codigo())[3:-4]

        print("Encontrado output {} : {}".format(ident, code))




def main(argv):
    input = FileStream("../Lenguaje/test3.moo")

    errorListener = moomaErrorListener()

    lexer = moomaLexer(input)
    stream = CommonTokenStream(lexer)
    parser = moomaParser(stream)
    parser.removeErrorListeners()
    parser.addErrorListener(errorListener)
    tree = parser.program()
    listener = MyMoomaListener("out.java")
    walker = ParseTreeWalker()
    walker.walk(listener, tree)


if __name__ == "__main__":
    main(sys.argv)

