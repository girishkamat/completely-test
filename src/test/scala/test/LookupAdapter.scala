package test

import java.util
import java.util.stream.Collectors

import com.miguelfonseca.completely.IndexAdapter
import com.miguelfonseca.completely.data.ScoredObject
import com.miguelfonseca.completely.text.`match`.EditDistanceAutomaton
import com.miguelfonseca.completely.text.index.{FuzzyIndex, PatriciaTrie}
import scala.collection.JavaConverters._

class LookupAdapter[T <: Boostable] extends IndexAdapter[T] {
  private val index: FuzzyIndex[T] = new PatriciaTrie()

  override def get(token: String): util.Collection[ScoredObject[T]] = {
    // Set threshold according to the token length
    val threshold: Double = Math.log(Math.max(token.length() - 1, 1).toDouble)
    index
      .getAny(new EditDistanceAutomaton(token, threshold))
      .asScala
      .map(so => new ScoredObject[T](so.getObject, so.getScore * so.getObject.boost))
      .asJava
  }

  override def put(token: String, value: T): Boolean =
    index.put(token, value)

  override def remove(value: T): Boolean =
    index.remove(value)
}
