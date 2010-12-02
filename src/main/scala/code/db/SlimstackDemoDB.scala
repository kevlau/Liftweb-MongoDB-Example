/*
 * Copyright 2010 WorldWide Conferencing, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package code {
package db {

	import _root_.net.liftweb.mongodb._
	import _root_.net.liftweb.util._
	import _root_.net.liftweb.common._
	import net.liftweb.json.DefaultFormats
	import net.liftweb.json.JsonAST._
	import net.liftweb.json.JsonParser._
	import net.liftweb.json.JsonDSL._

	import com.mongodb.{BasicDBObject, BasicDBObjectBuilder, DBObject}


object SlimstackDemoDB {
  	def setup {
	    MongoDB.defineDb(DefaultMongoIdentifier, MongoAddress(MongoHost(), "slimstack_demo"))
	}

	def isMongoRunning: Boolean = {
	    try {
	      MongoDB.use(DefaultMongoIdentifier) ( db => { db.getLastError } )
	      true
	    }
	    catch {
	      case e => false
	    }
	  }
}

}
}