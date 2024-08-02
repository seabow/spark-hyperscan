package io.github.seabow.spark.hyperscan

import org.apache.commons.lang.StringEscapeUtils
import org.apache.spark.sql.catalyst.expressions.codegen.CodegenFallback
import org.apache.spark.sql.catalyst.expressions._
import org.apache.spark.sql.catalyst.util.GenericArrayData
import org.apache.spark.sql.types._
import org.apache.spark.unsafe.types.UTF8String

import scala.collection.immutable

@ExpressionDescription(
  usage = "_FUNC_(str, patterns) - Returns true if str matches one of pattern, " +
    "null if any arguments are null, false otherwise.",
  arguments ="""
  * str - a string expression
  * patterns - a array of strings expression. Should always be foldable (eg array('pattern1', 'pattern2')).
  """,
  group = "json_funcs")
case class HyperscanLike(left: Expression, right: Expression) extends BinaryExpression with ImplicitCastInputTypes with CodegenFallback{
  override def dataType: DataType = BooleanType

  override def inputTypes: Seq[DataType] = Seq(StringType, DataTypes.createArrayType(StringType,false))

  @transient private lazy val hlikeInstance = {
    val patterns= right.eval().asInstanceOf[GenericArrayData]
    val regexes= ( 0 until patterns.numElements()).map(i=>patterns.getUTF8String(i).toString)
    HyperscanLikeInstance(regexes)}

  override protected def withNewChildrenInternal(newLeft: Expression, newRight: Expression): HyperscanLike =  copy(left = newLeft,right = newRight)

  override def nullSafeEval(value1: Any,value2:Any): Any = {
    val str=value1.asInstanceOf[UTF8String].toString
    hlikeInstance.hlikeInternal(str)
  }


}
