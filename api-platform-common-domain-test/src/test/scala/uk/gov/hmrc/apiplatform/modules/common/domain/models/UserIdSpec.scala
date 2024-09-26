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

import play.api.libs.json._

import uk.gov.hmrc.apiplatform.modules.common.utils.BaseJsonFormattersSpec

class UserIdSpec extends BaseJsonFormattersSpec with UserIdFixture {

  "UserId" should {
    "toString" in {
      userIdOne.toString() shouldBe userIdOne.value.toString()
    }

    "generate a random value" in {
      UserId.random should not be UserId.random
    }

    "apply raw text" in {
      UserId.apply(userIdOne.value.toString()) shouldBe Some(userIdOne)
    }

    "apply raw text fails when not valid" in {
      UserId.apply("not-a-uuid") shouldBe None
    }

    "unsafeApply text" in {
      UserId.unsafeApply(userIdOne.value.toString()) shouldBe userIdOne
    }

    "unsafeApply raw text throws when not valid" in {
      intercept[RuntimeException] {
        UserId.unsafeApply("not-a-uuid")
      }
    }

    "convert to json" in {
      Json.toJson(userIdOne) shouldBe JsString(userIdOne.value.toString())
    }

    "read from json" in {
      testFromJson[UserId](s""""${userIdOne.value.toString}"""")(userIdOne)
    }
  }
}
