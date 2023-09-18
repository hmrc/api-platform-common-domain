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

package uk.gov.hmrc.apiplatform.common.domain.services

import java.time.temporal.ChronoUnit
import java.time.{Clock, Instant, LocalDateTime}

trait ClockNow {

  implicit class LocalDateTimeTruncateSyntax(me: LocalDateTime) {
    def truncate() = me.truncatedTo(ChronoUnit.MILLIS)
  }

  implicit class InstantTruncateSyntax(me: Instant) {
    def truncate() = me.truncatedTo(ChronoUnit.MILLIS)
  }

  def precise(): Instant = Instant.now(clock)

  def now(): LocalDateTime = LocalDateTime.now(clock).truncate()

  def instant(): Instant = Instant.now(clock).truncate()

  def clock: Clock
}
