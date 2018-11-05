import sys
from antlr4 import *
from moomaLexer import moomaLexer
from moomaParser import moomaParser
from moomaListener import moomaListener


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
    input = FileStream("Lenguaje/test2.moo")
    lexer = moomaLexer(input)
    stream = CommonTokenStream(lexer)
    parser = moomaParser(stream)
    tree = parser.program()
    printer = KeyPrinter()
    walker = ParseTreeWalker()
    walker.walk(printer, tree)


if __name__ == "__main__":
    main(sys.argv)

