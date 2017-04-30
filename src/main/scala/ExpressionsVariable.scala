package csp.ch01

/**
  * Created by Chezka on 1/12/2017.
  */

object ExpressionsVariables {

  sealed trait Expr
  case class CstI (n:Int)                       extends Expr
  case class Var (s:String)                     extends Expr
  case class Prim (op:String, e1:Expr, e2:Expr) extends Expr

  type Env = List[(String, Int)]

  val emptyenv : Env = Nil

  def lookup (env:Env, x:String) : Int = {
    env match {
      case Nil           => throw new RuntimeException (x + " not found")
      case ((y, v) :: r) => if (x == y) v else lookup (r, x)
    }
  }

  def eval (env:Env, e:Expr) : Int = {
    e match {
      case CstI (n)           => n
      case Var (x)            => lookup (env, x)
      case Prim ("+", e1, e2) => eval (env, e1) + eval (env, e2)
      case Prim ("*", e1, e2) => eval (env, e1) * eval (env, e2)
      case Prim ("-", e1, e2) => eval (env, e1) - eval (env, e2)
      case Prim (  _,  _,  _) => throw new RuntimeException ("unknown primitive")
    }
  }

}