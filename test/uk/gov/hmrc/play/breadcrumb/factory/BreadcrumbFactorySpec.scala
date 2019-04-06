/*
 * Copyright 2016 HM Revenue & Customs
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

    "produce a breadcrumb with a last item when passed to breadcrumbTag with showLastItem == true" in {
      val f = new BreadcrumbFactory {
        override def buildBreadcrumb(implicit request: Request[_]) = Breadcrumb(Vector(BreadcrumbItem("Home", "/home",Some("myFunction")),
          BreadcrumbItem("Account", "/account")))
      }

      val b: String = views.html.breadcrumbTag(f.buildBreadcrumb(FakeRequest()), true).toString
      b.contains( """<li><a href="/home">Home</a></li>""") shouldBe true
      b.contains( """<li>Account</li>""") shouldBe true
    }

    "produce a breadcrumb without a last item when passed to breadcrumbTag with showLastItem == false" in {
      val f = new BreadcrumbFactory {
        override def buildBreadcrumb(implicit request: Request[_]) = Breadcrumb(Vector(BreadcrumbItem("Home", "/home",Some("myFunction")),
          BreadcrumbItem("Account", "/account",Some("myFunction"))))
      }

      val b: String = views.html.breadcrumbTag(f.buildBreadcrumb(FakeRequest()), false).toString
      b.contains( """<li><a href="/home" onclick="myFunction">Home</a></li>""") shouldBe true
      b.contains( """<li>Account</li>""") shouldBe false
    }

    "be iterable" in {
      val f = new BreadcrumbFactory {
        override def buildBreadcrumb(implicit request: Request[_]) = Breadcrumb(Vector(
          BreadcrumbItem("Home", "/home",Some("myFunction")),
          BreadcrumbItem("Account", "/account",Some("myFunction"))
        ))
      }

      val breadCrumb = f.buildBreadcrumb(FakeRequest())

      breadCrumb.iterator.toList shouldBe List(BreadcrumbItem("Home", "/home",Some("myFunction")))
      breadCrumb.lastItem shouldBe Some(BreadcrumbItem("Account", "/account",Some("myFunction")))
    }
  }

  "A Breadcrumb with only one item" should {
    "return an empty iterator and a last item" in {
      val f = new BreadcrumbFactory {
        override def buildBreadcrumb(implicit request: Request[_]) = Breadcrumb(Vector(
          BreadcrumbItem("Home","/home",Some("myFunction"))
        ))
      }

      val breadCrumb = f.buildBreadcrumb(FakeRequest())

      breadCrumb.iterator.toList shouldBe List.empty
      breadCrumb.lastItem shouldBe Some(BreadcrumbItem("Home","/home",Some("myFunction")))
    }
  }

  "A Breadcrumb with two items" should {
    "return an iterator with one item and a last item" in {
      val f = new BreadcrumbFactory {
        override def buildBreadcrumb(implicit request: Request[_]) = Breadcrumb(Vector(
          BreadcrumbItem("Home","/home",Some("myFunction")), BreadcrumbItem("Account","/account",Some("myFunction"))
        ))
      }

      val breadCrumb = f.buildBreadcrumb(FakeRequest())

      breadCrumb.iterator.toList shouldBe List(BreadcrumbItem("Home","/home",Some("myFunction")))
      breadCrumb.lastItem shouldBe Some(BreadcrumbItem("Account","/account",Some("myFunction")))
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
