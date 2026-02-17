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

opaque type ApiContext <: String = String

object ApiContext {

  extension (m: ApiContext) {
    def value: String     = m
    def segments()        = m.split("/")
    def topLevelContext() = ApiContext(segments().head)
  }

  def apply(value: String): ApiContext = value

  given Format[ApiContext]    = Format(Reads.StringReads, Writes.StringWrites)
  given KeyReads[ApiContext]  = key => JsSuccess(key)
  given KeyWrites[ApiContext] = identity(_)

// $COVERAGE-OFF$
  def random: ApiContext = Random.alphanumeric.take(10).mkString
// $COVERAGE-ON$
}
