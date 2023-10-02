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

import java.{util => ju}
import scala.util.control.Exception._

case class UserId(value: ju.UUID) extends AnyVal {
  override def toString(): String = value.toString
}

object UserId {
  import play.api.libs.json.{Json, Format}

  implicit val formatUserId: Format[UserId] = Json.valueFormat[UserId]

  def apply(raw: String): Option[UserId] = allCatch.opt(UserId(ju.UUID.fromString(raw)))

  def unsafeApply(raw: String): UserId = apply(raw).getOrElse(throw new RuntimeException(s"$raw is not a valid UserId"))

// $COVERAGE-OFF$
  def random: UserId = UserId(ju.UUID.randomUUID())
// $COVERAGE-ON$
}
