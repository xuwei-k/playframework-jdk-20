package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.{Application, Configuration}
import play.api.test._
import play.api.test.Helpers._
import play.server.api.SSLEngineProvider
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLEngine

class HomeControllerSpec extends PlaySpec with GuiceOneServerPerSuite {

  "HomeController GET" should {
    "index" in {
      WsTestClient.withClient { implicit client =>
        val response = await(wsCall(routes.HomeController.index()).get())
        assert(response.status == OK)
      }
    }
  }

  private[this] val factory = new DefaultTestServerFactory {
    override def overrideServerConfiguration(app: Application): Configuration =
      Configuration("play.server.https.engineProvider" -> classOf[MySSLEngineProvider].getName)
  }

  override protected final implicit lazy val runningServer: RunningServer =
    factory.start(app)
}


class MySSLEngineProvider extends SSLEngineProvider {
  override def createSSLEngine(): SSLEngine =
    sslContext().createSSLEngine()

  override def sslContext(): SSLContext =
    SSLContext.getDefault
}
