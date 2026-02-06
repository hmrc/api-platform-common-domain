/*
 * Copyright 2025 HM Revenue & Customs
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

opaque type OrganisationId = ju.UUID

object OrganisationId {
  import play.api.libs.json._

  extension (n: OrganisationId) {
    def value: String = n.toString()       // TODO - deprecate
  }

  def apply(raw: String): Option[OrganisationId] = allCatch.opt(OrganisationId(ju.UUID.fromString(raw)))
  def apply(uuid: ju.UUID): OrganisationId = uuid

  def unsafeApply(raw: String): OrganisationId = OrganisationId(raw).getOrElse(throw new RuntimeException(s"Cannot parse OrganisationId from '$raw'"))

  given Format[OrganisationId]    = Format(Reads.UUIDReader(true), Writes.UuidWrites)
  
// $COVERAGE-OFF$
  def random: OrganisationId = OrganisationId(ju.UUID.randomUUID)
// $COVERAGE-ON$
}