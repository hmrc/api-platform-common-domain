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

package uk.gov.hmrc.apiplatform.modules.common.domain.services

import java.time.{Instant, LocalDateTime, ZoneOffset}

import play.api.libs.json._

import uk.gov.hmrc.apiplatform.modules.common.utils.BaseJsonFormattersSpec

class InstantJsonFormattersSpec extends BaseJsonFormattersSpec {

  "InstantJsonFormatters" should {

    val instant: Instant = LocalDateTime.of(2000, 1, 2, 3, 4, 5, 6 * 1000 * 1000).toInstant(ZoneOffset.UTC)

    "read from Json (WithTimeZone)" in {
      import uk.gov.hmrc.apiplatform.modules.common.domain.services.InstantJsonFormatter.WithTimeZone._
      testFromJson[Instant](""""2000-01-02T03:04:05.006Z"""")(instant)
      testFromJson[Instant](""""2000-01-02T03:04:05.006"""")(instant)
    }

    "write to Json (WithTimeZone)" in {
      import uk.gov.hmrc.apiplatform.modules.common.domain.services.InstantJsonFormatter.WithTimeZone._
      Json.toJson[Instant](instant) shouldBe JsString("2000-01-02T03:04:05.006Z")
    }

    "read from Json (NoTimeZone)" in {
      import uk.gov.hmrc.apiplatform.modules.common.domain.services.InstantJsonFormatter.NoTimeZone._
      testFromJson[Instant](""""2000-01-02T03:04:05.006Z"""")(instant)
      testFromJson[Instant](""""2000-01-02T03:04:05.006"""")(instant)
    }

    "write to Json (NoTimeZone)" in {
      import uk.gov.hmrc.apiplatform.modules.common.domain.services.InstantJsonFormatter.NoTimeZone._
      Json.toJson[Instant](instant) shouldBe JsString("2000-01-02T03:04:05.006")
    }
  }
}
