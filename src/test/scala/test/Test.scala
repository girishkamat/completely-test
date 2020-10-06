package test

import com.miguelfonseca.completely.AutocompleteEngine
import com.miguelfonseca.completely.text.analyze.transform.LowerCaseTransformer

import scala.collection.JavaConverters._

object Test extends App {

  val engine: AutocompleteEngine[LookupRecord] = new AutocompleteEngine.Builder[LookupRecord]()
    .setIndex(new LookupAdapter[LookupRecord]())
    .setAnalyzers(new LowerCaseTransformer(), new CustomWordTokenizer())
    .build()

  CSVReader.getCSVData().map {
    case (english: LookupLabel, _: LookupLabel, lookupPriority: LookupPriority, _: LookupId) =>
      engine.add(new LookupRecord(english.label, lookupPriority.id))
  }

  println(engine.search("$").asScala.map(_.value).mkString("\n"))
}
