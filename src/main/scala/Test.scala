/**
  *
  * @author wang.baozhi 
  * @since 2019/8/22 下午3:37 
  */
object Test {
  def main(args: Array[String]) {
    val speeds = Array.fill[Integer](2)(50)
    System.out.println(speeds.length)
    System.out.println(speeds.apply(0))
    System.out.println(speeds.apply(1))

  }
}
