import java.math.BigInteger

/**
  *
  * @author wang.baozhi 
  * @since 2019/8/22 下午3:37 
  */
object Test {
  def main(args: Array[String]) {
    val a = BigInteger.valueOf(2147483647)
    println(a)

    val s=String.format("player_sdk_startplay aa=\"%s\"", "aa")
    System.out.println(s)
  }
}
