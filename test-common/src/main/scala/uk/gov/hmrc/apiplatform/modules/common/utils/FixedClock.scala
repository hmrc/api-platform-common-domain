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

package uk.gov.hmrc.apiplatform.modules.common.utils

import java.time.{Clock, Instant, LocalDateTime, ZoneOffset}

import uk.gov.hmrc.apiplatform.modules.common.services.{ClockNow, DateTimeHelper}

trait FixedClock extends ClockNow with DateTimeHelper {

  private val utc: ZoneOffset = ZoneOffset.UTC

  override val now: LocalDateTime = LocalDateTime.of(2020, 1, 2, 3, 4, 5, 6 * 1000 * 1000).truncate()

  override val instant: Instant = now.toInstant(utc)

  override val precise: Instant = instant.plusNanos(7008)

  val nowAsText: String = "2020-01-02T03:04:05.006Z"

  val clock: Clock = Clock.fixed(instant, utc)
}

object FixedClock extends FixedClock
