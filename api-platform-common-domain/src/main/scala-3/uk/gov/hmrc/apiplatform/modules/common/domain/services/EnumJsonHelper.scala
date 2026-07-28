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

  // These are both rather tolerant of case
  def fromSnakeCase(in: String): String = in.split("_").map(_.toLowerCase.capitalize).mkString("")

  @deprecated("Replaced with tolerant fromSnakeCase", "1.2.0")
  def fromScreamingSnakeCase(in: String): String = fromSnakeCase(in)

  /** Convert a CamelCase (leading caps) named type to Screaming Snake Case and (more tolerantly) visa versa
    *
    * MyBigClass becomes MY_BIG_CLASS Bobby becomes BOBBY etc
    */
  def toScreamingSnakeCase[T](in: T): String = regex.findAllIn(in.toString()).filter(_ != "").mkString("_").toUpperCase()

  /** Convert a CamelCase (leading caps) named type to Snake Case and (more tolerantly) visa versa
    *
    * MyBigClass becomes my_big_class, Bobby becomes bobby etc
    */
  def toSnakeCase[T](in: T): String = regex.findAllIn(in.toString()).filter(_ != "").mkString("_").toLowerCase()

  extension [T](t: T) {
    def asScreamingSnakeCase: String = EnumJsonHelper.toScreamingSnakeCase(t)

    def asSnakeCase: String = EnumJsonHelper.toSnakeCase(t)
  }
}
