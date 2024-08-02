package io.github.seabow.spark.hyperscan
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite

class HyperscanLikeTest  extends  AnyFunSuite with BeforeAndAfterAll with SparkSessionTestWrapper {

  test("hyperscan like"){
    spark.sql("select hlike('abcde', array('c.*', '[a-Z]+other'))").show
  }

}
