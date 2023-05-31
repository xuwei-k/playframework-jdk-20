package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

class HomeControllerSpec extends PlaySpec with GuiceOneServerPerSuite {

  "HomeController GET" should {
    "index" in {
      WsTestClient.withClient { implicit client =>
        val response = await(wsCall(routes.HomeController.index()).get())
        assert(response.status == OK)
      }
    }
  }
}
