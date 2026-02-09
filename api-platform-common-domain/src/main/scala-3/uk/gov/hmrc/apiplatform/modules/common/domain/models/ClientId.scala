/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.apiplatform.modules.common.domain.models

opaque type ClientId = String

object ClientId {
  import play.api.libs.json._
  import scala.util.Random

  extension (m: ClientId) {
    def value: String = m // TODO - deprecate
  }

  def apply(value: String): ClientId = value

  given Format[ClientId] = Format(Reads.StringReads, Writes.StringWrites)

  given Ordering[ClientId] with
    def compare(x: ClientId, y: ClientId): Int = x.compare(y)

// $COVERAGE-OFF$
  def random: ClientId = Random.alphanumeric.take(28).mkString
// $COVERAGE-ON$
}
