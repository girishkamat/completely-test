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

import java.util

import com.miguelfonseca.completely.data.Indexable

case class LookupId(id: String) extends AnyVal

case class LookupLabel(label: String) extends AnyVal

case class LookupPriority(id: Int) extends AnyVal

trait Boostable {
  val boost: Int
}

class LookupRecord(val value: String, val boost: Int = 1) extends Indexable with Boostable {
  override val getFields: util.List[String] = util.Arrays.asList(value)

  def toLookupLabel = LookupLabel(value)

  override def toString: String = s"LookupRecord($value, $boost)"
}
