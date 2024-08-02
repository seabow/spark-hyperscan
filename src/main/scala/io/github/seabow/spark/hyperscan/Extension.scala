package io.github.seabow.spark.hyperscan

import org.apache.spark.sql.SparkSessionExtensions
import org.apache.spark.sql.catalyst.FunctionIdentifier
import org.apache.spark.sql.catalyst.expressions.{Expression, ExpressionInfo}

class Extension extends (SparkSessionExtensions=>Unit){
  override def apply(extensions: SparkSessionExtensions): Unit = {
    extensions.injectFunction(
      FunctionIdentifier("hlike"),
      new ExpressionInfo(
        classOf[HyperscanLike].getName, "hlike"),
      (expressions: Seq[Expression]) => {
        HyperscanLike(expressions(0),expressions(1))
      }
    )
  }
}
