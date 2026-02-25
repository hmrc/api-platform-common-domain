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

object EnumJsonHelper {
  private val regex = """[\p{Lu}]*[^\p{Lu}]*""".r

  def fromScreamingSnakeCase(in: String): String = in.split("_").map(_.toLowerCase.capitalize).mkString("")

  /** Convert a CamelCase (leading caps) named type to Screaming Snake Case and (more tolerantly) visa versa
    *
    * MyBigClass becomes MY_BIG_CLASS Bobby becomes BOBBY etc
    */
  def toScreamingSnakeCase[T](in: T): String = regex.findAllIn(in.toString()).filter(_ != "").mkString("_").toUpperCase()

  extension [T](t: T) {
    def asScreamingSnakeCase: String = EnumJsonHelper.toScreamingSnakeCase(t)
  }
}
