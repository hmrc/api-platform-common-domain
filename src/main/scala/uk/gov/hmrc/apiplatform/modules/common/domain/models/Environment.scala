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

import uk.gov.hmrc.apiplatform.modules.common.domain.services.SealedTraitJsonFormatting

sealed trait Environment {
  final lazy val isSandbox: Boolean = this == Environment.SANDBOX
  final lazy val isProduction: Boolean = ! isSandbox
  final lazy val displayText: String = Environment.displayText(this)
}

object Environment {
  final case object PRODUCTION extends Environment
  final case object SANDBOX    extends Environment

  val values = Set[Environment](PRODUCTION, SANDBOX)

  import cats.implicits._

  val displayText: Environment => String = _ match {
    case PRODUCTION => "Production"
    case _ => "Sandbox"
  }

  def apply(text: String): Option[Environment] = text.toUpperCase match {
    case "PRODUCTION"    => PRODUCTION.some
    case "SANDBOX"     => SANDBOX.some
    case _         => None
  }

  def unsafeApply(text: String): Environment = {
    apply(text).getOrElse(throw new RuntimeException(s"$text is not a valid Environment"))
  }

  import play.api.libs.json.Format

  implicit val formatEnvironment: Format[Environment] = SealedTraitJsonFormatting.createFormatFor[Environment]("Environment", apply(_))
}
