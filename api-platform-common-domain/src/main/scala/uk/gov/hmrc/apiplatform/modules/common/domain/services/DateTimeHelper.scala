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

import java.time.{Instant, LocalDate, LocalDateTime, ZoneOffset}

object DateTimeHelper {

  implicit class LocalDateTimeConversionSyntax(localDateTime: LocalDateTime) {

    /** Converts a LocalDateTime to an Instant, assuming the LDT was created in the UTC time zone. */
    def asInstant: Instant = localDateTime.toInstant(ZoneOffset.UTC)
  }

  implicit class LocalDateConversionSyntax(localDate: LocalDate) {

    /** Converts a LocalDate to an Instant, assuming the LD was created in the UTC time zone. */
    def asInstant: Instant = localDate.atTime(0, 0).toInstant(ZoneOffset.UTC)
  }

  implicit class InstantConversionSyntax(instant: Instant) {

    /** Converts an Instant to a LocalDateTime, assuming "local" is in the UTC time zone. */
    def asLocalDateTime: LocalDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)

    /** Converts an Instant to a LocalDate, assuming "local" is in the UTC time zone. Note: truncates the time */
    def asLocalDate: LocalDate = LocalDate.ofInstant(instant, ZoneOffset.UTC)
  }
}
