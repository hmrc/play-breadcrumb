#Play Breadcrumb Plugin

Plugin to provide commom breadcrumb model and template tag


##Setup

Add the jar to the projects dependencies:

```
    "uk.gov.hmrc" %% "breadcrumb" % "0.0.0"
```

Add plugin to play.plugins:

```
    {priority}:uk.gov.hmrc.play.breadcrumb.BreadcrumbPlugin
```

##Code Example

The model consists of:

    +------------+           +----------------+
    | Breadcrumb |<>---------| BreadcrumbItem |
    +------------+           +----------------+
    | +items     |           | +text          |
    +------------+           | +url           |
                             +----------------+

You must first implement BreadcrumbFactory's buildBreadcrumb method. A naive example which assumes
a 1:1 mapping between request path and breadcrumb items could be:

```
    trait MyBreadcrumbFactory extends BreadcrumbFactory {

      implicit override def buildBreadcrumb(implicit request: Request[_]): Breadcrumb = {
        val links = Map(
          "app"     -> BreadcrumbItem("Home",    routes.ApplicationController.index().url),
          "account" -> BreadcrumbItem("Account", routes.ApplicationController.account().url)
        )
        try {
          val items = request.path.split("/").filter(!_.isEmpty).map(key => links(key)).toList
          Breadcrumb(items)
        } catch {
          case e: NoSuchElementException => Breadcrumb(Nil)
        }
      }

    }
```


The next step is to have your controller extend your BreadcrumbFactory:

```
    class MyController extends MyBreadcrumbFactory
```


Then pass an implicit breadcrumb to your teplate(s) and render the breadcrumbTag where you want
your breadcrumb to appear:

```
    @()(mainContent: Html)(implicit breadcrumb: uk.gov.hmrc.play.breadcrumb.model.Breadcrumb)

    @headerContent

    @breadcrumbTag(breadcrumb)

    @mainContent

    @footerContent
```
