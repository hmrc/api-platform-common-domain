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

object ApiIdentifierData extends ApiContextFixture with ApiVersionNbrFixture {
  val one   = ApiIdentifier(apiContextOne, apiVersionNbrOne)
  val two   = ApiIdentifier(apiContextOne, apiVersionNbrOnePointOne)
  val three = ApiIdentifier(apiContextTwo, apiVersionNbrOne)
  val four  = ApiIdentifier(apiContextTwo, apiVersionNbrTwo)
}

trait ApiIdentifierFixture extends ApiContextFixture with ApiVersionNbrFixture {
  val apiIdentifierOne   = ApiIdentifierData.one
  val apiIdentifierTwo   = ApiIdentifierData.two
  val apiIdentifierThree = ApiIdentifierData.three
  val apiIdentifierFour  = ApiIdentifierData.four
}
