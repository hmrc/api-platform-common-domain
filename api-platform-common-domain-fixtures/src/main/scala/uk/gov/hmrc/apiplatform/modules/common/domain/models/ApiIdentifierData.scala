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

object ApiIdentifierData {
  val one   = ApiIdentifier(ApiContextData.one, ApiVersionNbrData.one)
  val two   = ApiIdentifier(ApiContextData.one, ApiVersionNbrData.onePointOne)
  val three = ApiIdentifier(ApiContextData.two, ApiVersionNbrData.one)
  val four  = ApiIdentifier(ApiContextData.two, ApiVersionNbrData.two)
}

trait ApiIdentifierFixtures extends ApiContextFixtures with ApiVersionNbrFixtures {
  val apiIdentifierOne   = ApiIdentifierData.one
  val apiIdentifierTwo   = ApiIdentifierData.two
  val apiIdentifierThree = ApiIdentifierData.three
  val apiIdentifierFour  = ApiIdentifierData.four
}
