package bootstrap.liftweb

import _root_.net.liftweb.util._
import _root_.net.liftweb.common._
import _root_.net.liftweb.http._
import _root_.net.liftweb.http.provider._
import _root_.net.liftweb.sitemap._
import _root_.net.liftweb.sitemap.Loc._
import Helpers._
import _root_.net.liftweb.mapper.{DB, ConnectionManager, Schemifier, DefaultConnectionIdentifier, StandardDBVendor}
import _root_.java.sql.{Connection, DriverManager}
import _root_.code.model._
import _root_.code.db._
import net.liftweb.mongodb.DefaultMongoIdentifier
import java.util.Locale

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    SlimstackDemoDB.setup

    // where to search snippet
    LiftRules.addToPackages("code")

    // Build SiteMap
    val entries = Menu(Loc("Home", List("index"), "Home")) ::
      Menu(Loc("Static", Link(List("static"), true, "/static/index"), "Static Content")) ::
      Menu(Loc("Pet Form", Link(List("petform"), true, "/petform"), "Pet Form")) :: User.sitemap

    LiftRules.setSiteMap(SiteMap(entries:_*))

    /*
    * Show the spinny image when an Ajax call starts
    */
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    /*
    * Make the spinny image go away when it ends
    */
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    LiftRules.early.append(makeUtf8)

    LiftRules.loggedInTest = Full(() => User.loggedIn_?)

    LiftRules.localeCalculator = localeCalculator _
  }

  /**
   * Force the request to be UTF-8
   */
  private def makeUtf8(req: HTTPRequest) {
    req.setCharacterEncoding("UTF-8")
  }

  private def localeCalculator(request : Box[HTTPRequest]): Locale =
    User.currentUser.map(u => new Locale(u.locale.value)) openOr Locale.getDefault
}
