/*
 * Copyright 2020 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test

import kantan.csv.{CellDecoder, HeaderDecoder, rfc}
import kantan.csv.ops._
import kantan.csv.{CellDecoder, HeaderDecoder, rfc}

object CSVReader {

  implicit private val lookupIdCellDecoder: CellDecoder[LookupId] = implicitly[CellDecoder[String]].map(LookupId)
  implicit private val lookupPriorityCellDecoder: CellDecoder[LookupPriority] = implicitly[CellDecoder[String]].map(v => if(v.trim.isEmpty) LookupPriority(1) else LookupPriority(v.trim.toInt))
  implicit private val lookupLabelCellDecoder: CellDecoder[LookupLabel] =
    implicitly[CellDecoder[String]].map(s => LookupLabel(s.trim))

  def getCSVData() = {
    val lookup = getClass.getClassLoader.getResourceAsStream("BCD-Currency.csv")

    val headerDecoder: HeaderDecoder[(LookupLabel, LookupLabel, LookupPriority, LookupId)] =
      HeaderDecoder.decoder("Name", "cy", "Priority", "CurrencyCode")((_: LookupLabel, _: LookupLabel, _: LookupPriority, _: LookupId))
    lookup
      .unsafeReadCsv[List, (LookupLabel, LookupLabel, LookupPriority, LookupId)](rfc.withHeader)(
        headerDecoder,
        implicitly,
        implicitly)
  }
}
