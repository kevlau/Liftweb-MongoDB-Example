package code.snippet

import _root_.net.liftweb.common._
import _root_.net.liftweb.util._
import _root_.net.liftweb.http._
import _root_.net.liftweb.mapper._
import _root_.net.liftweb.util.Helpers._
import _root_.net.liftweb.sitemap._
import _root_.scala.xml._
import _root_.net.liftweb.http.S._
import _root_.net.liftweb.http.RequestVar
import _root_.net.liftweb.util.Helpers._
import _root_.net.liftweb.common.Full
import code.model.Pet
import net.liftweb.mongodb.{Skip, Limit}
import _root_.net.liftweb.http.S._
import _root_.net.liftweb.mapper.view._
import com.mongodb._

class PetSnippet extends StatefulSnippet with PaginatorSnippet[Pet] {
  // For pagination
  val PAGE_SIZE = 5

  // function show here matches the petform.html lift:PetSnippet.show
  var dispatch: DispatchIt = {
    case "showAll" => showAll _
    case "editForm" => editForm _
    case "paginate" => paginate _
  }

  // Hold state about if we are editing or creating a new pet
  var isEditing = false
  var editingPet = Pet.createRecord

  def showAll(xhtml: NodeSeq): NodeSeq = {
    page.flatMap(pet => {
      ( ".petEdit *" #> link("pet/edit", () => edit(pet), Text("Edit")) &
        ".petDelete *" #> link("", () => delete(pet), Text("Delete")) &
        ".petName *" #> pet.name &
        ".petAge *" #> pet.age).apply(xhtml)
    })
  }

  def editForm(xhtml: NodeSeq): NodeSeq = {
        ("#editName" #> editingPet.name.toForm &
         "#editAge" #> editingPet.age.toForm &
         "#editDescription" #> editingPet.description.toForm &
          "type=submit" #> SHtml.submit(?("Save"), () => save )).apply(xhtml)
  }

  // This is related to PaginatorSnippet
  override def count = Pet.findAll(QueryBuilder.start().get()).size
  override def itemsPerPage = PAGE_SIZE
	override def page = Pet.findAll(QueryBuilder.start().get(), Limit(itemsPerPage), Skip(curPage*itemsPerPage))

  // Place this into a edit mode and store the pet which is being edited
  def edit(pet : Pet ) = {
    isEditing = true
    editingPet = pet
  }

  // Delete a pet
  def delete(pet : Pet ) = {
    pet.delete_!
    redirectToHome
  }

  // Save a pet
  def save = {
    editingPet.save
    redirectToHome
  }

  def redirectToHome = {
    isEditing = false
    editingPet = Pet.createRecord
    redirectTo("/pet")
  }

}