package uk.gov.hmrc.play.breadcrumb.factory

import org.scalatest.{ShouldMatchers, WordSpec}
import play.api.mvc.Request
import uk.gov.hmrc.play.breadcrumb.model._

class BreadcrumbFactorySpec extends WordSpec with ShouldMatchers {

  "A BreadcrumbFactory" should {

    "produce a proper breadcrumb when passed to breadcrumbTag" in {

      val f = new BreadcrumbFactory {
        override def buildBreadcrumb(implicit request: Request[_]): Breadcrumb =  Breadcrumb( List ( BreadcrumbItem("Home","/home"), BreadcrumbItem("Account","/account")))
      }

      val b: String = views.html.breadcrumbTag(f.buildBreadcrumb(null)).toString

      b.contains("""<li><a href="/home">Home</a></li>""") shouldBe true
      b.contains("""<li>Account</li>""") shouldBe true

    }
  }
}
