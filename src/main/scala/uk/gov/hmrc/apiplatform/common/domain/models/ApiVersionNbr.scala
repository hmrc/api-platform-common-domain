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

package uk.gov.hmrc.apiplatform.common.domain.models

import scala.util.Random

import play.api.libs.json.Json

final case class ApiVersionNbr(value: String) extends AnyVal {
  override def toString(): String = value
}

object ApiVersionNbr {
  implicit val formatApiVersionNbr = Json.valueFormat[ApiVersionNbr]

  implicit val ordering: Ordering[ApiVersionNbr] = Ordering.by[ApiVersionNbr, String](_.value)

  /** Produces a version from 0-999 . 0-999
    */
  def random = {
    val major          = Random.nextInt(1000)
    val minor          = Random.nextInt(1000)
    val minorFormatted = f"$minor%3d".stripPrefix(" ").stripPrefix(" ")
    ApiVersionNbr(s"${major.toString}.$minorFormatted")
  }
}
