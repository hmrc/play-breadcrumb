package uk.gov.hmrc.play.breadcrumb.factory

import play.api.mvc.Request
import uk.gov.hmrc.play.breadcrumb.model.Breadcrumb

/**
 * Created by nic on 06/02/2015.
 */
trait BreadcrumbFactory {
  def buildBreadcrumb(implicit request: Request[_]): Breadcrumb
}
