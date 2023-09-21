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

import uk.gov.hmrc.apiplatform.modules.common.utils.BaseJsonFormattersSpec
import org.scalatest.prop.TableDrivenPropertyChecks
import play.api.libs.json._

class ActorTypeSpec extends BaseJsonFormattersSpec with TableDrivenPropertyChecks {
  val values =
    Table(
      ("Source", "text", "display text"),
      (ActorType.COLLABORATOR, "collaborator", "Application Collaborator"),
      (ActorType.GATEKEEPER, "gatekeeper", "Gatekeeper User"),
      (ActorType.SCHEDULED_JOB, "scheduled_job", "Scheduled Job"),
      (ActorType.UNKNOWN, "unknown", "Unknown")
    )

  "ActorTypes" when {
    "displayText is good" in {
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
        ActorType.apply(t) shouldBe Some(s)
        ActorType.unsafeApply(t) shouldBe s
      }
    }

    "convert mixed case string to case object" in {
      forAll(values) { (s, t, _) =>
        ActorType.apply(t.toUpperCase()) shouldBe Some(s)
        ActorType.unsafeApply(t.toUpperCase()) shouldBe s
      }
    }

    "convert string value to None when undefined or empty" in {
      ActorType.apply("rubbish") shouldBe None
      ActorType.apply("") shouldBe None
    }

    "throw when string value is invalid" in {
      intercept[RuntimeException] {
        ActorType.unsafeApply("rubbish")
      }.getMessage() should include("Actor Type")
    }

    "read from Json" in {
      forAll(values) { (s, t, _) =>
        testFromJson[ActorType](s""""$t"""")(s)
      }
    }

    "read with text error from Json" in {
      intercept[Exception] {
        testFromJson[ActorType](s""" "123" """)(ActorType.UNKNOWN)
      }.getMessage() should include("123 is not a valid Actor Type")
    }
   
    "read with error from Json" in {
      intercept[Exception] {
        testFromJson[ActorType](s"""123""")(ActorType.UNKNOWN)
      }.getMessage() should include("Cannot parse Actor Type from '123'")
    }

    "write to Json" in {
      forAll(values) { (s, t, _) =>
        Json.toJson[ActorType](s) shouldBe JsString(t.toUpperCase())
      }
    }
  }
}
