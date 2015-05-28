/*
 * Copyright 2015 HM Revenue & Customs
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

package uk.gov.hmrc.play.breadcrumb.factory

import org.scalatest.{ShouldMatchers, WordSpec}
import play.api.mvc.Request
import play.api.test.FakeRequest
import uk.gov.hmrc.play.breadcrumb.model._

class BreadcrumbFactorySpec extends WordSpec with ShouldMatchers {
  "A BreadcrumbFactory" should {
    "produce a proper breadcrumb when passed to breadcrumbTag" in {
      val f = new BreadcrumbFactory {
        override def buildBreadcrumb(implicit request: Request[_]) = Breadcrumb(Vector(BreadcrumbItem("Home", "/home"),
          BreadcrumbItem("Account", "/account")))
      }

      val b: String = views.html.breadcrumbTag(f.buildBreadcrumb(FakeRequest())).toString
      b.contains( """<li><a href="/home">Home</a></li>""") shouldBe true
      b.contains( """<li>Account</li>""") shouldBe true
    }

    "be iterable" in {
      val f = new BreadcrumbFactory {
        override def buildBreadcrumb(implicit request: Request[_]) = Breadcrumb(Vector(
          BreadcrumbItem("Home", "/home"),
          BreadcrumbItem("Account", "/account")
        ))
      }

      val breadCrumb = f.buildBreadcrumb(FakeRequest())

      breadCrumb.iterator.toList shouldBe List(BreadcrumbItem("Home", "/home"))
      breadCrumb.lastItem shouldBe Some(BreadcrumbItem("Account", "/account"))
    }
  }

  "A Breadcrumb with only one item" should {
    "return an empty iterator and a last item" in {
      val f = new BreadcrumbFactory {
        override def buildBreadcrumb(implicit request: Request[_]) = Breadcrumb(Vector(
          BreadcrumbItem("Home","/home")
        ))
      }

      val breadCrumb = f.buildBreadcrumb(FakeRequest())

      breadCrumb.iterator.toList shouldBe List.empty
      breadCrumb.lastItem shouldBe Some(BreadcrumbItem("Home","/home"))
    }
  }

  "A Breadcrumb with two items" should {
    "return an iterator with one item and a last item" in {
      val f = new BreadcrumbFactory {
        override def buildBreadcrumb(implicit request: Request[_]) = Breadcrumb(Vector(
          BreadcrumbItem("Home","/home"), BreadcrumbItem("Account","/account")
        ))
      }

      val breadCrumb = f.buildBreadcrumb(FakeRequest())

      breadCrumb.iterator.toList shouldBe List(BreadcrumbItem("Home","/home"))
      breadCrumb.lastItem shouldBe Some(BreadcrumbItem("Account","/account"))
    }
  }
  
  "A Breadcrumb with with zero items" should {
    "return an empty iterator and no last item" in {
      val f = new BreadcrumbFactory {
        override def buildBreadcrumb(implicit request: Request[_]) = Breadcrumb(Vector.empty)
      }

      val breadCrumb = f.buildBreadcrumb(FakeRequest())

      breadCrumb.iterator.toList shouldBe List.empty
      breadCrumb.lastItem shouldBe None
    }
  }
}
