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
import com.mongodb._

class PetSnippet extends StatefulSnippet {

  // function show here matches the petform.html lift:PetSnippet.show
  var dispatch: DispatchIt = {
    case "show" => show _
  }

  // Hold state about if we are editing or creating a new pet
  var isEditing = false
  var editingPet = Pet.createRecord

  // Called when the snippet is shown on the page
  def show(xhtml: NodeSeq): NodeSeq = {
    if (isEditing)
      editPage(xhtml)
    else
      showPage(xhtml)
  }

  // Binds some page elements to show the edit form
  def editPage(xhtml : NodeSeq) : NodeSeq =
  (
    "#show" #> <div></div> & // Hide the show DIV
    "#editForm" #> editForm ) // Replace the #edit with the edit form
    .apply(xhtml)

  def showPage(xhtml : NodeSeq) : NodeSeq =
            ( "#edit" #> <div></div> & // Hide the edit DIV
              "#petTable" #> petTable & // Replace the table with a new table from the function petTable
              "#addForm" #> addForm // Replace the #addForm with a form
      ).apply(xhtml)

   // This binds the edit form to the edit controls
  def editForm = ("#editName" #> editingPet.name.toForm &
                "#editAge" #> editingPet.age.toForm &
                "#editDescription" #> editingPet.description.toForm &
                "type=submit" #> SHtml.submit(?("Save Pet"), () => save ))

  // This binds the add form to the add controls
  def addForm = ("#addName" #> editingPet.name.toForm &
                             "#addAge" #> editingPet.age.toForm &
                             "#addDescription" #> editingPet.description.toForm &
                             "type=submit" #> SHtml.submit(?("Add Pet"), () => save ))

  // A table of all pets, pagination could be implemented through the use of Limit and Skip
  def petTable = <table> {
      Pet.findAll(QueryBuilder.start().get(), Limit(100), Skip(0)).map(pet =>
        {
        <tr>
          <td>{pet.name}</td>
          <td>{
            // This link is a <a href with no destination "" which calls back the server function edit(pet)
            // when a user clicks on the link
            link("", () => edit(pet), Text("Edit"))
            }</td>
          <td>{link("", () => delete(pet), Text("Delete"))}</td>
        </tr>
        } )
      } </table>

  // Place this into a edit mode and store the pet which is being edited
  def edit(pet : Pet ) = {
    isEditing = true
    editingPet = pet
  }

  // Delete a pet
  def delete(pet : Pet ) = {
    pet.delete_!
  }

  // Save a pet
  def save = {
    editingPet.save
    isEditing = false
    editingPet = Pet.createRecord
  }

}