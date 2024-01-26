/*
 * Copyright 2024 HM Revenue & Customs
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

import java.time.{Instant, LocalDate, LocalDateTime}

import uk.gov.hmrc.apiplatform.modules.common.domain.services.DateTimeHelper._
import uk.gov.hmrc.apiplatform.modules.common.utils.HmrcSpec

class DateTimeHelperSpec extends HmrcSpec {

  "DateTimeHelper" should {

    val localDateTime     = LocalDateTime.of(2000, 1, 2, 3, 4, 5, 6 * 1000 * 1000)
    val localDate         = LocalDate.of(2000, 1, 2)
    val instant           = Instant.parse("2000-01-02T03:04:05.006Z")
    val instantAtMidnight = Instant.parse("2000-01-02T00:00:00Z")

    "convert a LocalDateTime to an Instant" in {
      localDateTime.asInstant shouldBe instant
    }

    "convert a LocalDate to an Instant" in {
      localDate.asInstant shouldBe instantAtMidnight
    }

    "convert an Instant to a LocalDateTime" in {
      instant.asLocalDateTime shouldBe localDateTime
    }

    "convert an Instant to a LocalDate (with time truncated)" in {
      instant.asLocalDate shouldBe localDate
    }
  }
}
