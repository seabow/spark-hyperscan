package io.github.seabow.spark.hyperscan
import com.gliwka.hyperscan.wrapper._

import scala.collection.JavaConverters._
object HyperscanLikeInstance{
 @transient lazy val threadLocalScanner= new ThreadLocal[Scanner](){
    override def  initialValue():Scanner={
      val scanner=new Scanner
      scanner
    }
  }
}
case class HyperscanLikeInstance(regexes: Seq[String]) {
  @transient lazy val exprHLike = regexes.map(new Expression(_)).asJava
  @transient lazy val dbHLike = try {
    val db=Database.compile(exprHLike)
    scannerHLike.allocScratch(db)
    db
  } catch {
    case e: CompileErrorException => throw new RuntimeException("can't compile pattern", e)
  }
  @transient lazy val scannerHLike =HyperscanLikeInstance.threadLocalScanner.get()

  def hlikeInternal(str: String) = {
    !scannerHLike.scan(dbHLike, str).isEmpty
  }
}
