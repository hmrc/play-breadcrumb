#Play Breadcrumb Plugin

Plugin to provide commom breadcrumb model and template tag


##Setup

Add the jar to the projects dependencies:

```
    "uk.gov.hmrc" %% "play-breadcrumb" % "[INSERT VERSION]"
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

Of course your implementation may be as complex as you like.

The next step is to have your controller extend your BreadcrumbFactory, and make sure your actions use an
implicit Request:

```
    class MyController extends MyBreadcrumbFactory {

      def index = Action { implicit request =>
        Ok(views.html.index())
      }
    }
```


Then pass an implicit breadcrumb to your template(s) and render the breadcrumbTag where you want
your breadcrumb to appear. The controller will

```
    @()(implicit breadcrumb: uk.gov.hmrc.play.breadcrumb.model.Breadcrumb)

    @headerContent

    @breadcrumbTag(breadcrumb)

    Hello, this is the index page!

    @footerContent
```

## License ##
 
This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
