package uk.gov.hmrc.play.breadcrumb.model

/**
 * Created by nic on 06/02/2015.
 */


case class BreadcrumbItem(text: String, url: String)

case class Breadcrumb(items: List[BreadcrumbItem])