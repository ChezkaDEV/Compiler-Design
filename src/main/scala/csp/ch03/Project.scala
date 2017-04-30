package csp.ch03
// From SBT: ~run-main csp.ch03.Project

// white space sensitive
import fastparse.all._

object Project {

  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // Abstract Syntax
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  sealed trait Expr
  case class CstI (n : Int)                                               extends Expr
  case class Var (nm : String)                                            extends Expr
  case class Prim (nm : String, e1 : Expr, e2 : Expr)                     extends Expr
  case class Call(nm : String, el: List[Expr])                            extends Expr

  sealed trait StatementStuff
  case class Assign (nm : String, e : Expr)                               extends StatementStuff
  case class If (e : Expr, s1 : StatementStuff, s2 : StatementStuff)      extends StatementStuff
  case class Block (ss : List[StatementStuff])                            extends StatementStuff
  case class For (nm : String, e1 : Expr, e2 : Expr, s : StatementStuff)  extends StatementStuff
  case class While (e : Expr, s : StatementStuff)                         extends StatementStuff
  case class Print (e : Expr)                                             extends StatementStuff
  case class PrintString (arr : Expr)                                     extends StatementStuff
  case class IfAnd (e1: Expr, e2: Expr)                                   extends StatementStuff
  case class Return (e : Expr)                                            extends StatementStuff

  case class FuncDef (nm : String, params : List[String], body : StatementStuff)
  case class Program (funs: List[FuncDef], main : StatementStuff)
  case class prog (s:Program)
  case class listofStatements (funs : List[StatementStuff])

  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // Parsing
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  // cannot see white spaces. Parse will fail for: "x = 0"
  object MyParserNoWhiteSpace {
    import fastparse.all._

    def foldAssocLeft (p : (Expr, List[(String, Expr)])) : Expr = {
      p match {
        case (e1, Nil)              => e1
        case (e1, (op, e2) :: rest) => foldAssocLeft (Prim (op, e1, e2), rest)
        }
      }

    //NAIVESTORE//
    type NaiveStore = Map[String,Int]

    val emptyStore : NaiveStore = Map.empty

    def getSto (store : NaiveStore, x : String) : Int = {
      store.get (x).get
    }

    def setSto (store : NaiveStore, k : String, v : Int) : NaiveStore = {
      store + ( (k, v) )
    }

    def b2i (b : Boolean) : Int = if (b) 1 else 0

    def eval (e : Expr, store : NaiveStore) : Int = {
      e match {
        case CstI (i)           => i
        case Var (x)            => getSto (store, x)
        case Prim (op, e1, e2) => {
          val i1 = eval (e1, store)
          val i2 = eval (e2, store)
          op match {
            case  "+" => i1 + i2
            case  "-" => i1 - i2
            case  "*" => i1 * i2
            case  "%" => i1 % i2
            case "==" => b2i (i1 == i2)
            case "<>" => b2i (i1 != i2)
            case  "<" => b2i (i1 < i2)
            case  ">" => b2i (i1 > i2)
            case "<=" => b2i (i1 <= i2)
            case ">=" => b2i (i1 >= i2)
            case   _ => throw new RuntimeException ("unknown primitive " + op)
          }
        }
      }
    }


      // constructor argument: indent (level of indentation)
    class Dummy (indent: Int) {
        import fastparse.all._

        val atExpr : Parser[Expr] = P (
          integer |
            (ident ~ (expr.rep (sep = ",").map (s => s.toList)).?).map {
              case (nm, None)      => Var (nm)
              case (nm, Some (es)) => Call (nm, es)
            }
//            |
//            ("(" ~/ expr ~ ")")
        )
        val multDiv : Parser[Expr] = P (
          (atExpr ~ (("*" | "/").! ~ atExpr).rep.map (s => s.toList)).map (foldAssocLeft)
        )
        val addSub : Parser[Expr] = P (
          (multDiv ~ (("+" | "-").! ~ multDiv).rep.map (s => s.toList)).map (foldAssocLeft)
        )
        val gtLtGeLeExpr : Parser[Expr] = P (
          (addSub ~ ((">" | "<" | ">=" | "<=" | "==").! ~ addSub).rep.map (s => s.toList)).map (foldAssocLeft)
        )
        val eqNeExpr : Parser[Expr] = P (
          (gtLtGeLeExpr ~ (( "%" | "=" | "<>").! ~ gtLtGeLeExpr).rep.map (s => s.toList)).map (foldAssocLeft)
        )
        val expr : Parser[Expr] = P (eqNeExpr)

        val ident : Parser[String] = P ((alpha ~ (alpha | CharIn ('0' to '9')).rep (0)).!).filter (s => !keywords.contains (s))
        val alpha : Parser[String] = P ((CharIn ('A' to 'Z') | CharIn ('a' to 'z')).!)
        val keywords : List[String] = List ("print", "while", "return", "if", "else", "for", "def", "End", "range", "in")
        val number: Parser[Int] = P ( CharIn('0'to'9').rep(1).!.map(_.toInt) )
        val digits : Parser[Int] = P (CharIn ('0' to '9').rep (1).!).map (s => s.toInt)
        val integer : Parser[Expr] = digits.map (n => CstI (n))
        val variable : Parser[Expr] = P (ident.map (s => Var(s)))

        val blocks : Parser[StatementStuff] = P (stmt.rep).map { case ss => Block (ss.toList) }
        //val assignStatement : Parser[StatementStuff] = P ( ident ~ "=" ~ expr).map { case (nm, e) => Assign(nm, e)}
        val deeper : Parser[Int] = P( " ".rep(indent + 1).!.map(_.length) )

        // fix
        val blockBody: Parser[StatementStuff] = "\n" ~ deeper.flatMap(i =>
          new Dummy(indent = i).stmt.rep(1, sep = ("\n" + " " * i).~/).map (s => Block (s.toList)) )

        val stmt: Parser[StatementStuff] = P ("\n".rep.? ~ "\t".rep.? ~
          (
            ("return " ~ expr.map( d => Return(d)) ) |
              //statement |
              ( ident ~ "=" ~ expr).map { case (nm, e) => Assign(nm, e)} |
              ("for " ~ ident ~ " in range(" ~ expr ~ "," ~ expr ~ "):" ~ stmt).map { case (nm, e1, e2, s) => For (nm, e1, e2, s)} |
              blockBody |
              ("print " ~ expr ~ (" ".?)).map( e => Print(e) ) |
              ("print " ~ "\"" ~ (expr) ~ "\"" ).map { case (e) => PrintString (e) } |
              ("if " ~ expr ~ ":" ~ ("\n".rep.? ~ "\t".rep.?) ~ stmt ~ ("\n".rep.? ~ "\t".rep.?) ~ stmt).map {
                case (e, st1, st2) => If (e, st1, st2) } |
              ("while " ~ "(" ~ expr ~ "):" ~ ("\n".rep.? ~ "\t".rep.?) ~ stmt).map { case (e, s) => While (e, s) }
              // BLOCK NOT WORKING -------- | (stmt.rep).map { case ss => Block (ss.toList) }
          )
//          | blocks
          // | ifandstatement | blockssss
          )

        val funcdef : P[FuncDef] = P (
          ("def " ~ ident ~ "(" ~ ident.rep (sep=",").map(s => s.toList) ~ "):" ~ stmt).map {
            case (nm, params, body) => FuncDef (nm, params, body) }
        )

        val program : Parser[Program] = P (
          (funcdef.rep.map (s => s.toList) ~ "main".? ~ stmt.rep.map { case ss => Block(ss.toList)}).map { case (funcdefs, body) => Program (funcdefs, body) }
        )
        val start : Parser[Program] = P (program ~ ("\r" | "\n").rep ~ End.?)
      }
    val poo = new Dummy(indent = 0).start
  }


  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // Pretty printing
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  def ppExpr (e : Expr) : String = {
    e match {
      case CstI (i)                     => i.toString
      case Var (x)                      => x
      case Prim (op, e1, e2)            => "%s %s %s".format (ppExpr (e1), op, ppExpr (e2))
      case Call (nm, es)                => "%s %s".format (nm, es.map (ppExpr).mkString (", "))
    }
  }

  def ppBlock (indent : String, s : StatementStuff) : String = {
    val newIndent = indent + "  "
    s match {
      case Block (ss) => {
        val sb = new StringBuilder
        for (s <- ss) {
          sb.append (ppStmt (newIndent, s))
        }
        sb.toString
      }
      case _ => {
        "%s".format (ppStmt (newIndent, s))
      }
    }
  }

  def ppStmt (indent : String, s : StatementStuff) : String = {
    val newIndent = indent + "  "
    s match {
      case Assign (nm, e)           =>
        "%s%s=%s\n".format (indent, nm, ppExpr (e))
      case If (e, s1, s2)         =>
        "%sif %s:\n/t%s%s else \n%s%s\n".format (indent, ppExpr (e), ppBlock (indent, s1), indent, ppBlock (indent, s2), indent)
      case Block (ss) => {
        "%s\n%s%s\n".format (indent, ppBlock (indent, s), indent)
      }
      case For (nm, low, high, s) => {
        "%sfor %s in range(%s,%s):\n%s%s\n".format (indent, nm, ppExpr (low), ppExpr (high), ppBlock (indent, s), indent)
      }
      case While (e, s)           =>
        "%swhile %s \n%s%s:\n".format (indent, ppExpr (e), ppBlock (indent, s), indent)
      case Print (e)              =>
        "%sprint %s\n".format (indent, ppExpr (e))
      case PrintString (e)              =>
        "%sprintstring %s\n".format (indent, ppExpr (e))
      case Return (e)             =>
        "%sreturn %s\n".format (indent, ppExpr (e))
    }
  }

  def ppFuncDef (f : FuncDef) : String = {
    "def %s(%s):\n%s".format (f.nm, f.params.mkString (", "), ppStmt ("", f.body))
  }

  def ppProgram (p : Program) : String = {
    p.funs.map (f => ppFuncDef (f)).mkString ("\n") + ppStmt ("", p.main)
  }

  import fastparse.all.{Parsed,Parser}

  def readFile (filename : String) : String = {
    val source : scala.io.BufferedSource = io.Source.fromFile (filename)
    try source.getLines.mkString ("\n") finally source.close ()
  }

  def invokeAssemblerLinker (asmFilename : String) : Unit = {
    import scala.sys.process.{Process}
    val pb = Process (List ("gcc", "-o", asmFilename.replace (".s", ""), asmFilename))
    import scala.language.postfixOps
    val result : String = (pb !!)
    println ("Running assembler: %s".format (result))
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // Code Generation
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  type Env = Map[String,String]
  type FuncEnv = Map[String,FuncDef]

  val emptyEnv : Env = Map.empty

  var labelCounter : Int = 0
  def newLabel () : String = {
    labelCounter = labelCounter + 1
    "lab%03d".format (labelCounter)
  }

  def compileExpr (e : Expr, env : Env, fenv : FuncEnv) : String = {
    e match {
      case CstI (i)           =>
        "\tpushq\t$%d\n".format (i)
      case Var (x)            =>
        env.get (x) match {
          case None => throw new RuntimeException ("unable to find variable %s in environment".format (x))
          case Some (lab) =>
            "\tpushq\t%s\n".format (lab)
        }
      case Prim (op, e1, e2) => {
        val insts1 = compileExpr (e1, env, fenv)
        val insts2 = compileExpr (e2, env, fenv)
        val push = "\tpushq\t%rax\n"
        def pop (reg : String) = "\tpopq\t%%%s\n".format (reg)
        val instsOp : String = op match {
          case  "+" => "\taddq\t%rbx, %rax\n"
          case  "-" => "\tsubq\t%rbx, %rax\n"
          case  "*" => "\timulq\t%rbx, %rax\n"
          case  "=" => {
            "\tcmpq\t%rbx, %rax\n" +    // sets ZF if ((rax-rbx) = 0) as signed, i.e., (rax = rbx)
              "\tsete\t%al\n" +           // sets low-order byte (%al) of %rax to 1 if ZF is set, otherwise to 0
              "\tmovzbl\t%al, %eax\n"     // extends %al to %rax (recall that assignment to a 32-bit register clears the upper 32-bits of the corresponding 64-bit register)
          }
          // case "<>" => b2i (i1 != i2)
          case  "<" => {
            "\tcmpq\t%rbx, %rax\n" +    // sets SF if ((rax-rbx) < 0) as signed, i.e., (rax < rbx)
              "\tsets\t%al\n" +           // sets low-order byte (%al) of %rax to 1 if SF is set, otherwise to 0
              "\tmovzbl\t%al, %eax\n"     // extends %al to %rax (recall that assignment to a 32-bit register clears the upper 32-bits of the corresponding 64-bit register)
          }
          // case  ">" => b2i (i1 > i2)
          // case "<=" => b2i (i1 <= i2)
          // case ">=" => b2i (i1 >= i2)
          case   _ => throw new RuntimeException ("unknown primitive " + op)
        }
        insts1 +
          insts2 +
          pop ("rbx") +
          pop ("rax") +
          instsOp +
          push
      }
      case Call (nm, es) => {
        es.reverse.map (e => compileExpr (e, env, fenv)).mkString +
          "\tcall\t%s\n".format (nm) +
          "\taddq\t$%d, %%rsp\n".format (es.length * 8) +
          "\tpushq\t%rax\n"
      }
    }
  }

  def compileAll (prog : Program, env : Env, fenv : FuncEnv) : String = {
    header () +
      compileFunc (FuncDef ("main", Nil, prog.main), env, fenv) +
      "\n" +
      prog.funs.map (fd => compileFunc (fd, env, fenv)).mkString ("\n") +
      footer (env)
  }

  def header () : String = {
    ""
  }

  def print_string () : String = {
    "\t.text\n" +
      "\t.globl\tprint_string\n" +
      "\t.type\tprint_string, @function\n" +
      "print_string:\n" +
      ".LFB0:\n" +
      "\tpushq\t%rbp\n" +
      "\tmovq\t%rsp, %rbp\n" +
      "\tsubq\t$16, %rsp\n" +
      "\tmovq\t%rdi, -8(%rbp)\n" +
      "\tjmp\t.L2\n" +
      ".L3:\n" +
      "\tmovq\t-8(%rbp), %rax\n" +
      "\tmovq\t(%rax), %rax\n" +
      "\tmovsbl\t%al, %eax\n" +
      "\tmovl\t%eax, %edi\n" +
      "\tcall\tputchar\n" +
      "\taddq\t$8, -8(%rbp)\n" +
      ".L2:\n" +
      "\tmovq\t-8(%rbp), %rax\n" +
      "\tmovq\t(%rax), %rax\n" +
      "\ttestq\t%rax, %rax\n" +
      "\tjne\t.L3\n" +
      "\tleave\n" +
      "\tret\n"
  }

  def footer (env : Env) : String = {
    "\n" +
      print_string () +
      "\n" +
      "\t.section .rodata\n" +
      ".output:\n" +
      "\t.string \"%d\\n\"\n" +
      "\n" +
      (for ((nm1, _) <- env) yield {
        "\t.globl\t%s\n".format (nm1) +
          "\t.data\n".format (nm1) +
          "\t.align\t8\n" +
          "\t.size\t%s, 8\n".format (nm1) +
          "%s:\n".format (nm1) +
          "\t.quad\t0\n" +
          "\n"
      }).mkString
  }

// series of statements
  def compileFunc (func : FuncDef, env : Env, fenv : FuncEnv) : String = {
    val header = {
      "\t.text\n" +
        "\t.globl\t%s\n".format (func.nm) +
        "\t.type\t%s, @function\n".format (func.nm) +
        "%s:\n".format (func.nm) +
        "\tpushq\t%rbp\n" +
        "\tmovq\t%rsp, %rbp\n"
    }
    val footer = {
      "\tpopq\t%rbp\n" +
        "\tret\n"
    }
    var env2 : Env = env
    for ((param, i) <- func.params.zipWithIndex) {
      env2 = env2 + ( (param, "%d%%rbp".format ((i + 2) * 8)) )
    }
    header +
      compileStmt (func.body, env2, fenv) +
      footer
  }

  // compiling statementsss
  def compileStmt (s : StatementStuff, env : Env, fenv : FuncEnv) : String = {
    s match {
      case Assign (nm, e)            => {
        env.get (nm) match {
          case None => throw new RuntimeException ("unable to find variable %s in environment".format (nm))
          case Some (lab) =>
            ppStmt ("// ", s) +
              compileExpr (e, env, fenv) +
              "\tpopq\t%rax\n" +
              "\tmovq\t%%rax, %s\n".format (lab)
        }
      }
      case If (e, s1, s2)          =>
        val label1 = newLabel ()
        val label2 = newLabel ()
        val label3 = newLabel ()
        "// %s\n".format () +
          compileExpr (e, env, fenv) +
          "\tpopq\t%rax\n" +
          "\ttestq\t%rax, %rax\n" +
          "\tjne\t%s\n".format (label1) +
          "\tjmp\t%s\n".format (label2) +
          "%s:\n".format (label1) +
          compileStmt (s1, env, fenv) +
          "\tjmp\t%s\n".format (label3) +
          "%s:\n".format (label2) +
          compileStmt (s2, env, fenv) +
          "%s:\n".format (label3)
      case Block (ss)              => {
        def loop (ss2 : List[StatementStuff]) : String = {
          ss2 match {
            case Nil       => ""
            case s2 :: ss3 => compileStmt (s2, env, fenv) + loop (ss3)
          }
        }
        loop (ss)
      }
      case For (nm, low, high, s)  => {
        val label1 = newLabel ()
        val label2 = newLabel ()
        "// for %s in range (%s,%s):\n".format (nm, ppExpr (low), ppExpr (high)) +
          compileExpr (low, env, fenv) +
          "\tpopq\t%rax\n" +
          "\tmovq\t%%rax, %s\n".format (nm) +
          "\tjmp\t%s\n".format (label2) +
          "%s:\n".format (label1) +
          compileStmt (s, env, fenv) +
          "\tmovq\t%s, %%rax\n".format (nm) +
          "\taddq\t$1, %rax\n" +
          "\tmovq\t%%rax, %s\n".format (nm) +
          "%s:\n".format (label2) +
          compileExpr (high, env, fenv) +
          "\tpopq\t%rbx\n" +
          "\tmovq\t%s, %%rax\n".format (nm) +
          "\tcmpq\t%rbx, %rax\n" +
          "\tjle\t%s\n".format (label1)
      }
      case While (e, s)            => {
        val label1 = newLabel ()
        val label2 = newLabel ()
        "// while %s:\n".format (ppExpr (e)) +
          "\tjmp\t%s\n".format (label2) +
          "%s:\n".format (label1) +
          compileStmt (s, env, fenv) +
          "%s:\n".format (label2) +
          compileExpr (e, env, fenv) +
          "\tpopq\t%rax\n" +
          "\ttestq\t%rax, %rax\n" +
          "\tjne\t%s\n".format (label1)
      }
      case Print (e)               => {
        ppStmt ("// ", s) +
          compileExpr (e, env, fenv) +
          "\tpopq\t%rsi\n" +
          "\tmovl\t$.output, %edi\n" +
          "\tmovl\t$0, %eax\n" +
          "\tcall\tprintf\n"
      }
      case PrintString (e)               => {
        ppStmt ("// ", s) +
          compileExpr (e, env, fenv) +
          "\tpopq\t%rdi\n" +
          "\tcall\tprint_string\n"
      }
      case Return (e)               => {
        ppStmt ("// ", s) +
          compileExpr (e, env, fenv) +
          "\tpopq\t%rax\n" +
          "\tpopq\t%rbp\n" +
          "\tret\n"
      }
    }
  }

  def findVarsExpr (e : Expr) : List[String] = {
    e match {
      case CstI (i)           => Nil
      case Var (x)            => List (x)
      case Prim (op, e1, e2)  => findVarsExpr (e1) ::: findVarsExpr (e2)
      case Call (nm, es)      => es.flatMap (findVarsExpr)
    }
  }

  def findVarsStmt (s : StatementStuff) : List[String] = {
    s match {
      case Assign (nm, e)            => nm :: findVarsExpr (e)
      case If (e, s1, s2)          => findVarsExpr (e) ::: findVarsStmt (s1) ::: findVarsStmt (s2)
      case Block (ss)              => {
        def loop (ss2 : List[StatementStuff]) : List[String] = {
          ss2 match {
            case Nil       => Nil
            case s2 :: ss3 => findVarsStmt (s2) ::: loop (ss3)
          }
        }
        loop (ss)
      }
      case For (nm, low, high, s)  => {
        nm :: findVarsExpr (low) ::: findVarsExpr (high) ::: findVarsStmt (s)
      }
      case While (e, s)            => {
        findVarsExpr (e) ::: findVarsStmt (s)
      }
      case Print (e)               => {
        findVarsExpr (e)
      }
      case PrintString (e)         => {
        findVarsExpr (e)
      }
      case Return (e)              => {
        findVarsExpr (e)
      }
    }
  }

  def findVars (s : StatementStuff) : List[String] = {
    findVarsStmt (s).toSet.toList.sortWith ((s1,s2) => s1 < s2)
  }

  def compile (prog : Program, filename: String) : Unit = {
    val fenv : FuncEnv = (for (fd <- prog.funs) yield (fd.nm, fd)).toMap
    val vars : List[String] = for (stmt <- (prog.main :: prog.funs.map (f => f.body)); v <- findVars (stmt)) yield v
    val env : Env = (for (v <- vars) yield (v, "%s".format (v))).toMap

    println ("Variables: %s".format (env.mkString (", ")))
    println ("Compiling:")
    val asm : String = compileAll (prog, env, fenv)
    val asmFilename = filename.replace (".py", ".s")
    val fw = new java.io.FileWriter (asmFilename)
    fw.write (asm)
    fw.close
    println ("Wrote to: %s".format (asmFilename))
//    invokeAssemblerLinker (asmFilename)
    println (asm)
  }

  def test [X] (p : Parser[X], s : String) : Unit = {
    val result : fastparse.core.Parsed[X, Char, String] = p.parse (s)
    result match {
      case Parsed.Success (value, successIndex) => {
        println ("Successfully parsed:\n \"%s\".  Result is:\n\n %s  Index is %d.".format (s, value, successIndex))
      }
      case Parsed.Failure (lastParser, index, extra) => {
        println ("Failed to parse:\n \"%s\".  Last parser is:\n\n %s  Index is %d.  Extra is %s".format (s, lastParser, index, extra))
      }
    }
  }


  def test2 (p : Parser[Program], filename : String) : Unit = {
    val input : String = readFile (filename)
    val result : fastparse.core.Parsed[Program, Char, String] = p.parse (input)
    result match {
      case Parsed.Success (value, successIndex) => {
        println ("Successfully parsed file:\n \"%s\".\nResult is:\n\n %s\nIndex is %d.".format (filename, value, successIndex))
      }
      case Parsed.Failure (lastParser, index, extra) => {
        println ("Failed to parse file:\n \"%s\".  Last parser is:\n %s  Index is %d.  Extra is %s".format (filename, lastParser, index, extra))
      }
    }
  }

  def test3 (p : Parser[Program], filename : String) : Unit = {
    val input : String = readFile (filename)
    val result : fastparse.core.Parsed[Program, Char, String] = p.parse (input)
    result match {
      case Parsed.Success (prog, successIndex) => {
        println ("Successfully parsed file: \"%s\".\nResult is %s.\nIndex is %d.".format (filename, prog, successIndex))
        //println ("Pretty printing:")
        //print (ppStmt ("  ", stmt))
        compile (prog, filename)
      }
      case Parsed.Failure (lastParser, index, extra) => {
        println ("Failed to parse file \"%s\".  Last parser is %s.  Index is %d.  Extra is %s".format (filename, lastParser, index, extra))
      }
    }
  }

  def main (args : Array[String]) {
    println ("=" * 80)

    import java.io.File
    for (f <- new File ("./input").listFiles.toList.sortWith ((f1, f2) => f1.getName < f2.getName);
         if (f.getName.endsWith (".py"))) {

      // you can also uncomment this file to read files from ./input
      test3 (MyParserNoWhiteSpace.poo, f.getPath)
      println ("=" * 80)
    }

    //////////////////////////////////////////////////////////
    // Parsing Testing
    //////////////////////////////////////////////////////////

//    // success: hello.py
//    test (MyParserNoWhiteSpace.poo, "def main():\n\tprint Hello World!")
//    println ("=" * 80)
//
//    // success: count.py
      //test (MyParserNoWhiteSpace.poo, "def main():\n\tfor i in range(1,101):\n\t\tprint i")
//    println ("=" * 80)
//
//    // success: fact.py
//    test (MyParserNoWhiteSpace.poo, "def fact(n):\n\tif n==0:\n\treturn 1\nelse:\n\treturn n*fact n-1")
//    println ("=" * 80)
//    test (MyParserNoWhiteSpace.poo, "def main():\n\tcount=0\n\tsum=0")
//    println ("=" * 80)
//    test (MyParserNoWhiteSpace.poo, "def main():\n\tfor i in range(1,101):\n\t\tn=i%5\n\t\tm=i%3\n\t\tif n==0 and m==0:\n\t\t\tprint \"FizzBuzz\"")
//    println ("=" * 80)
//    test (MyParserNoWhiteSpace.poo,
//    "def main():\n\tn=i-5\n\tm=i-3\n\tfor i in range(1, 101):\n\t\tif n==0:\n\t\t\tif m==0:\n\t\t\t\tprint i")

    ////////////////////////////////////////////////////////
    // AST Testing
    ////////////////////////////////////////////////////////

    val ex1 : StatementStuff = {
      Block (
        List (
          Assign (
            "sum",
            CstI (0)),
          For (
            "i",
            CstI (0),
            CstI (100),
            Assign (
              "sum",
              Prim ("+", Var ("sum"), Var ("i"))
            )),
          Print (Var ("sum"))
        )
      )
    }

    val ex2 : StatementStuff = {
      Block (
        List (
          Assign (
            "i",
            CstI (1)
          ),
          Assign (
            "sum",
            CstI (0)
          ),
          While (
            Prim ("<", Var ("sum"), CstI (10000)),
            Block (
              List (
                Print (Var ("sum")),
                Assign (
                  "sum",
                  Prim ("+", Var ("sum"), Var ("i"))
                ),
                Assign (
                  "i",
                  Prim ("+", CstI (1), Var ("i"))
                )
              )
            )
          ),
          Print (Var ("i")),
          Print (Var ("sum"))
        )
      )
    }

//    println(ex1)
//    println("=" * 80)
//
//    println(ex2)
//    println("=" * 80)

  }
}