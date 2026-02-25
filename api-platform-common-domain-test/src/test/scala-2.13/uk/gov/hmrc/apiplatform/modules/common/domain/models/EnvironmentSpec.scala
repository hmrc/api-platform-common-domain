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

import org.scalatest.prop.TableDrivenPropertyChecks

import play.api.libs.json.{JsString, Json}

import uk.gov.hmrc.apiplatform.modules.common.utils._

class EnvironmentSpec extends BaseJsonFormattersSpec with TableDrivenPropertyChecks {

  "Environment" should {
    val values =
      Table(
        ("Source", "text", "display text"),
        (Environment.PRODUCTION, "production", "Production"),
        (Environment.SANDBOX, "sandbox", "Sandbox")
      )

    "display text correctly" in {
      forAll(values) { (s, _, d) =>
        s.displayText shouldBe d
      }
    }

    "convert to string correctly" in {
      forAll(values) { (s, t, _) =>
        s.toString() shouldBe t.toUpperCase()
      }
    }

    "convert lower case string to case object" in {
      forAll(values) { (s, t, _) =>
        Environment.apply(t) shouldBe Some(s)
        Environment.unsafeApply(t) shouldBe s
      }
    }

    "convert mixed case string to case object" in {
      forAll(values) { (s, t, _) =>
        Environment.apply(t.toUpperCase()) shouldBe Some(s)
        Environment.unsafeApply(t.toUpperCase()) shouldBe s
      }
    }

    "convert string value to None when undefined or empty" in {
      Environment.apply("rubbish") shouldBe None
      Environment.apply("") shouldBe None
    }

    "throw when string value is invalid" in {
      intercept[RuntimeException] {
        Environment.unsafeApply("rubbish")
      }.getMessage() should include("Environment")
    }

    "read from Json" in {
      forAll(values) { (s, t, _) =>
        testFromJson[Environment](s""""$t"""")(s)
      }
    }

    "read with text error from Json" in {
      intercept[Exception] {
        testFromJson[Environment](s""" "123" """)(Environment.PRODUCTION)
      }.getMessage() should include("123 is not a valid Environment")
    }

    "read with error from Json" in {
      intercept[Exception] {
        testFromJson[Environment](s"""123""")(Environment.PRODUCTION)
      }.getMessage() should include("Cannot parse Environment from '123'")
    }

    "write to Json" in {
      forAll(values) { (s, t, _) =>
        Json.toJson[Environment](s) shouldBe JsString(t.toUpperCase())
      }
    }
  }
}
