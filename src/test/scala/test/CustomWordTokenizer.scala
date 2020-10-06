package test

import java.text.BreakIterator
import java.util

import scala.collection.JavaConverters._
import com.miguelfonseca.completely.common.Precondition.checkPointer
import com.miguelfonseca.completely.text.analyze.Analyzer

class CustomWordTokenizer extends Analyzer {

  private val boundary = BreakIterator.getWordInstance

  override def apply(input: util.Collection[String]): util.Collection[String] = {
    checkPointer(input != null)
    val result = new util.LinkedList[String]
    for (text <- input.asScala) {
      checkPointer(text != null)
      boundary.setText(text)
      var start = boundary.first
      var end = boundary.next
      while ( {
        end != BreakIterator.DONE
      }) {
        val word = text.substring(start, end)
        if (Character.isLetterOrDigit(word.charAt(0)) || Character.getType(word.charAt(0)) == Character.CURRENCY_SYMBOL)
          result.add(word)

        start = end
        end = boundary.next
      }
    }
    result
  }
}
