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

object ApiVersionNbrData {
  val one         = ApiVersionNbr("1.0")
  val onePointOne = ApiVersionNbr("1.1")
  val two         = ApiVersionNbr("2.0")
  val three       = ApiVersionNbr("3.0")
}

trait ApiVersionNbrFixtures {
  val apiVersionNbrOne         = ApiVersionNbrData.one
  val apiVersionNbrOnePointOne = ApiVersionNbrData.onePointOne
  val apiVersionNbrTwo         = ApiVersionNbrData.two
  val apiVersionNbrThree       = ApiVersionNbrData.three
}
