import org.antlr.v4.runtime.*
import org.antlr.v4.runtime.tree.*

import java.io.FileInputStream
import java.io.InputStream

@main def main(args: String*): Unit = {
  val is: InputStream = args.length match
    case 0 => System.in
    case 1 => new FileInputStream(args.head)
    case _ => throw new Exception
  val input: ANTLRInputStream = new ANTLRInputStream(is)
  val lexer: ExprLexer = new ExprLexer(input)
  val tokens: CommonTokenStream = new CommonTokenStream(lexer)
  val parser: ExprParser = new ExprParser(tokens)
  val tree: ParseTree = parser.prog()

  val eval: EvalVisitor = new EvalVisitor()
  eval.visit(tree)
}

class EvalVisitor extends ExprBaseVisitor[Int] {
  val memory = scala.collection.mutable.Map[String, Int]()

  override def visitAssign(ctx: ExprParser.AssignContext): Int = {
    val id: String = ctx.ID().getText()
    val value: Int = visit(ctx.expr())
    memory += (id -> value)
    value
  }

  override def visitPrintExpr(ctx: ExprParser.PrintExprContext): Int = {
    val value: Int = visit(ctx.expr())
    println(s"$value")
    0
  }

  override def visitInt(ctx: ExprParser.IntContext): Int = {
    ctx.INT().getText().toInt
  }

  override def visitId(ctx: ExprParser.IdContext): Int = {
    val id: String = ctx.ID().getText()
    if memory.contains(id) then memory(id) else 0
  }

  override def visitMulDiv(ctx: ExprParser.MulDivContext): Int = {
    val left: Int = visit(ctx.expr(0))
    val right: Int = visit(ctx.expr(1))
    if ctx.op.getType() == ExprParser.MUL then (left * right) else (left / right)
  }

  override def visitAddSub(ctx: ExprParser.AddSubContext): Int = {
    val left: Int = visit(ctx.expr(0))
    val right: Int = visit(ctx.expr(1))
    if ctx.op.getType == ExprParser.ADD then (left + right) else (left - right)
  }

  override def visitParens(ctx: ExprParser.ParensContext): Int = {
    visit(ctx.expr())
  }
}
