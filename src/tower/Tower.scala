package tower

abstract class Tower() {
  val price: Int
  val healthPoints: Int
  val range: Int
  val icon: String

  def attack()
}