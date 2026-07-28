/*
 * Copyright 2026 HM Revenue & Customs
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

import play.api.libs.json.*

import uk.gov.hmrc.apiplatform.modules.common.utils.BaseJsonFormattersSpec

class SimpleEnumJsonFormattingSpec extends BaseJsonFormattersSpec {

  "snakeCaseFormatFor" should {
    enum TestEnum {
      case SomethingHere, SomethingThere
    }

    object TestEnum {
      def apply(in: String): Option[TestEnum] = TestEnum.values.find(_.toString == in)
    }

    given Format[TestEnum] = SimpleEnumJsonFormatting.snakeCaseFormatFor[TestEnum]("Test Enum", TestEnum.apply)

    "write json" in {
      Json.toJson(TestEnum.SomethingHere) shouldBe JsString("something_here")
    }

    "read json" in {
      testFromJson[TestEnum](""" "something_there" """)(TestEnum.SomethingThere)
    }
  }
}
