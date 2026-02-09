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

import play.api.libs.json.*

/** LaxEmailAddress is a wrapper to string but designed to carry the idea of an email address
  *
  * NO verification takes place ! Should allways contain lower case value
  *
  * We rely on the apply to prevent mixed case as we cannot make it private or it breaks mocking
  */
opaque type LaxEmailAddress = String

object LaxEmailAddress {

  extension (m: LaxEmailAddress) {
    def text: String = m // TODO - deprecate
  }

  def apply(text: String): LaxEmailAddress = text.toLowerCase()

  given Format[LaxEmailAddress] = Format(Reads.StringReads.map(apply), Writes.StringWrites)

  given Ordering[LaxEmailAddress] with
    def compare(x: LaxEmailAddress, y: LaxEmailAddress): Int = x.compare(y)

  object StringSyntax {

    extension (s: String) {
      def toLaxEmail: LaxEmailAddress = LaxEmailAddress(s)
    }
  }
}
