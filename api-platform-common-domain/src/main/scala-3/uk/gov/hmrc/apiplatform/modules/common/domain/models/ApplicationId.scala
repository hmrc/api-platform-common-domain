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

import java.util as ju
import scala.util.control.Exception.*

opaque type ApplicationId <: ju.UUID = ju.UUID

object ApplicationId {
  import play.api.libs.json.*

  extension (n: ApplicationId) {
    def value: ju.UUID = n
  }

  def apply(raw: String): Option[ApplicationId] = allCatch.opt(ApplicationId(ju.UUID.fromString(raw)))
  def apply(uuid: ju.UUID): ApplicationId       = uuid

  def unsafeApply(raw: String): ApplicationId = ApplicationId(raw).getOrElse(throw new RuntimeException(s"Cannot parse ApplicationId from '$raw'"))

  given Format[ApplicationId] = Format(Reads.UUIDReader(true), Writes.UuidWrites)

// $COVERAGE-OFF$
  def random: ApplicationId = ApplicationId(ju.UUID.randomUUID)
// $COVERAGE-ON$
}
