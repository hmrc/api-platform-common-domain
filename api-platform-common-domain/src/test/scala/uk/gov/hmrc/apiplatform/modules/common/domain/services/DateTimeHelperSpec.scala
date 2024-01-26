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

import java.time.ZoneOffset

import uk.gov.hmrc.apiplatform.modules.common.domain.services.DateTimeHelper._
import uk.gov.hmrc.apiplatform.modules.common.utils.{FixedClock, HmrcSpec}

class DateTimeHelperSpec extends HmrcSpec with FixedClock {

  "DateTimeHelper" should {

    val localDate  = now.toLocalDate
    val startOfDay = localDate.atStartOfDay(ZoneOffset.UTC).toInstant

    "convert a LocalDateTime to an Instant" in {
      preciseNow.asInstant shouldBe precise // TODO APIS-6715 Do we want truncation here? i.e. shouldBe instant
      now.asInstant shouldBe instant
    }

    "convert a LocalDate to an Instant" in {
      localDate.asInstant shouldBe startOfDay
    }

    "convert an Instant to a LocalDateTime" in {
      precise.asLocalDateTime shouldBe preciseNow // TODO APIS-6715 Do we want truncation here? i.e. shouldBe now
      instant.asLocalDateTime shouldBe now
    }

    "convert an Instant to a LocalDate (with time truncated)" in {
      precise.asLocalDate shouldBe localDate
      instant.asLocalDate shouldBe localDate
    }
  }
}
