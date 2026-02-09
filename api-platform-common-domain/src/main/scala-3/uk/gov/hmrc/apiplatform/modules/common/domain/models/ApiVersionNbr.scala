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

import scala.util.Random

import play.api.libs.json.*

opaque type ApiVersionNbr = String

object ApiVersionNbr {

  extension (n: ApiVersionNbr) {
    def value: String = n // TODO - deprecate
  }

  def apply(value: String): ApiVersionNbr = value

  given Ordering[ApiVersionNbr] with

    def compare(x: ApiVersionNbr, y: ApiVersionNbr): Int = {

      def asInt(versionNbr: ApiVersionNbr, portion: Int): Int =
        try {
          versionNbr.value.split('.')
            .applyOrElse[Int, String](portion, _ => "")
            .takeWhile(c => Character.isDigit(c))
            .toInt
        } catch {
          case _: NumberFormatException => Integer.MIN_VALUE
        }
      val splitXMajor                                         = asInt(x, 0)
      val splitYMajor                                         = asInt(y, 0)
      val compareMajor                                        = splitXMajor.compare(splitYMajor)

      if (compareMajor == 0) {
        val splitXMinor = asInt(x, 1)
        val splitYMinor = asInt(y, 1)
        splitXMinor.compare(splitYMinor)
      } else {
        compareMajor
      }
    }

  given Format[ApiVersionNbr]    = Format(Reads.StringReads, Writes.StringWrites)
  given KeyReads[ApiVersionNbr]  = key => JsSuccess(key)
  given KeyWrites[ApiVersionNbr] = identity(_)

  /** Produces a version from 0-999 . 0-999
    */
// $COVERAGE-OFF$
  def random: ApiVersionNbr = {
    val major          = Random.nextInt(1000)
    val minor          = Random.nextInt(1000)
    val minorFormatted = f"$minor%3d".stripPrefix(" ").stripPrefix(" ")
    s"${major.toString}.$minorFormatted"
  }
// $COVERAGE-ON$
}
