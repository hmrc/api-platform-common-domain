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

import uk.gov.hmrc.apiplatform.modules.common.domain.services.SimpleEnumJsonFormatting

enum Environment {

  def isSandbox: Boolean = this match {
    case Production => false
    case _          => true
  }

  def isProduction: Boolean = !isSandbox

  def displayText: String = this match {
    case Production => "Production"
    case _          => "Sandbox"
  }

  case Production, Sandbox
}

object Environment {
  import cats.implicits.*

  def apply(text: String): Option[Environment] = text.toUpperCase match {
    case "PRODUCTION" => Production.some
    case "SANDBOX"    => Sandbox.some
    case _            => None
  }

  def unsafeApply(text: String): Environment = {
    apply(text).getOrElse(throw new RuntimeException(s"$text is not a valid Environment"))
  }

  import play.api.libs.json.Format

  given Format[Environment] = SimpleEnumJsonFormatting.screamingSnakeCaseFormatFor[Environment]("Environment", apply)
}
