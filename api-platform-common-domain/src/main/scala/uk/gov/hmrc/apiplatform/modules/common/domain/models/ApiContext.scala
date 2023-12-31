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

import play.api.libs.json._

final case class ApiContext(value: String) extends AnyVal {

  def segments(): Array[String] = value.split("/")

  def topLevelContext(): ApiContext = ApiContext(segments().head)

  override def toString(): String = value
}

object ApiContext {
  implicit val format: Format[ApiContext]       = Json.valueFormat[ApiContext]
  implicit val keyReads: KeyReads[ApiContext]   = key => JsSuccess(ApiContext(key))
  implicit val keyWrites: KeyWrites[ApiContext] = _.value

  implicit val ordering: Ordering[ApiContext] = Ordering.by[ApiContext, String](_.value)

// $COVERAGE-OFF$
  def random: ApiContext = ApiContext(Random.alphanumeric.take(10).mkString)
// $COVERAGE-ON$
}
