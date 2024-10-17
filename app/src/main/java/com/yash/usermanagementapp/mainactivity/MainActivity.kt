package com.yash.usermanagementapp.mainactivity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.MotionEvent
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yash.usermanagementapp.R
import com.yash.usermanagementapp.adapter.UserAdapter
import com.yash.usermanagementapp.database.DatabaseHelper
import com.yash.usermanagementapp.user.User

class MainActivity :AppCompatActivity()
{

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var nameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var mobileEditText:EditText
    private lateinit var addButton: Button
    private lateinit var displayButton: Button
    private lateinit var userRecyclerView: RecyclerView

  //  private var editingUserIndex: Int? = null
    //private var editingUserId: Int? = null
    private lateinit var userAdapter: UserAdapter

    private var userList = mutableListOf<User>()


    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
//       getSupportActionBar()?.hide(); //hide the title bar
//        this.getWindow().setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
    setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)
        nameEditText = findViewById(R.id.nameEditText)
        ageEditText = findViewById(R.id.ageEditText)
        mobileEditText=findViewById(R.id.mobileEditText)
        addButton = findViewById(R.id.addButton)
        displayButton = findViewById(R.id.displayButton)
        userRecyclerView = findViewById(R.id.userRecyclerView)


        addButton.setOnClickListener {
            addUser()
        }


        displayButton.setOnClickListener {
            loadUsers()

        }

        userAdapter = UserAdapter(userList)
        { user, action ->
            when (action) {
                "edit" -> {
                    // Edit user action

                    showEditDialog(user.id)

//                    editingUserIndex = userList.indexOf(user)
//                    editingUserId = user.id
//
//                    nameEditText.setText(user.name)
//                    ageEditText.setText(user.age.toString())
//                    Toast.makeText(this, "Editing ${user.name}", Toast.LENGTH_SHORT).show()
                }

                "delete" -> {
                    // Delete user action
                    showDeleteConfirmation(user.id, user.name)

                    //  userList.remove(user)
//
//                    dbHelper.deleteUser(user.id)
//                    userAdapter.notifyDataSetChanged()
//                    Toast.makeText(this, "${user.name} deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }

        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = userAdapter


//        userAdapter=UserAdapter(dbHelper.getAllUsers(),this)
//        userListView.adapter = userAdapter


        //   loadUsers()

// Add user button click
//                addButton.setOnClickListener {
//                    val name = nameEditText.text.toString()
//                    val age = ageEditText.text.toString().toIntOrNull()
//
//
//                    if (name.isNotBlank() && age != null) {
//                        if (editingUserIndex != null) {
//                            // Update existing user
//                            userList[editingUserIndex!!] = User(id,name, age)
//                            editingUserIndex = null // Reset editing user index
//                            Toast.makeText(this, "User updated", Toast.LENGTH_SHORT).show()
//                        } else {
//                            // Add new user
//                            userList.add(User(name, age))
//                            Toast.makeText(this, "User added", Toast.LENGTH_SHORT).show()
//                        }
//
//                        userAdapter.notifyDataSetChanged()
//                        nameEditText.text.clear()
//                        ageEditText.text.clear()
//                    } else {
//                        Toast.makeText(this, "Please enter valid name and age", Toast.LENGTH_SHORT).show()
//                    }}


//        userListView.setOnItemLongClickListener { parent, view, position, id ->
//            deleteUser(position)
//            true
//        }


//        deleteImageView.setOnClickListener() {
//            val position = 0
//            val userId = users[position].id  // Get the ID of the user to delete
//            showDeleteConfirmation(userId)    // Show confirmation dialog
//            // showEditDialog(userId)
//        }
//        editImageView.setOnClickListener() {
//            var position = 0
//            val userId = users[position].id
//            showEditDialog(userId)
//        }
        // }
//    private fun deleteUser(position: Int) {
//
//        AlertDialog.Builder(this)
//            .setTitle("Delete User")
//            .setMessage("Are you sure you want to delete this user?")
//            .setPositiveButton("Yes") { _, _ ->
//                val deletedRows = dbHelper.deleteUser(position)
//                if (deletedRows > 0) {
//                    loadUsers()  // Reload the list to reflect the deletion
//                    Toast.makeText(this, "$ deleted", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this, "Error deleting user", Toast.LENGTH_SHORT).show()
//                }
//            }
//            .setNegativeButton("No", null)
//            .show()
//    }
        //for hide editor

        val mainLayout = findViewById<RelativeLayout>(R.id.mainLayout)

        // Set a touch listener on the main layout
        mainLayout.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                hideKeyboardAndClearFocus()
            }
            true
        }
    }
    private fun hideKeyboardAndClearFocus() {
        // Hide the soft keyboard
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let { view ->
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            view.clearFocus()
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun loadUsers()
    {

//                        userList = dbHelper.getAllUsers().toMutableList()
//                        userList.clear()
//
//                       userAdapter.notifyDataSetChanged()

        val usersDb = dbHelper.getAllUsers()
        userList.clear()
        userList.addAll(usersDb)
        userAdapter.notifyDataSetChanged()
    }

    //
    private fun addUser()
    {
        val name = nameEditText.text.toString()
        val ageString = ageEditText.text.toString()
        val numberString= mobileEditText.text.toString()

        if (name.isEmpty() && ageString.isEmpty() && numberString.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (name.isEmpty() && ageString.isNotEmpty() && numberString.isNotEmpty()) {
                Toast.makeText(this, "Please fill Correct Name", Toast.LENGTH_SHORT)
                    .show()
                return
            }

        if (name.isNotEmpty() && ageString.isEmpty() && numberString.isNotEmpty()) {
            Toast.makeText(this, "Please fill Correct Age", Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (name.isNotEmpty() && ageString.isNotEmpty() && numberString.isEmpty()) {
            Toast.makeText(this, "Please fill Correct Number", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val age = ageString.toIntOrNull()
        val number=numberString.toIntOrNull()
        if (name.isNotEmpty() && age != null && number !=null) {


           val isAdded= dbHelper.addUser(name, age, number)
            if(isAdded) {
                //loadUsers()
                Toast.makeText(this, "User Saved", Toast.LENGTH_SHORT).show()
                //  userAdapter.notifyDataSetChanged()
                nameEditText.text.clear()
                ageEditText.text.clear()
                mobileEditText.text.clear()
            }
            else {
                Toast.makeText(this, "Error,  saving ${name}, \nnumber might be duplicate", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please fill Correct age fields", Toast.LENGTH_SHORT).show()

        }
    }
    private fun showEditDialog(userId: Int)
    {
        val user = userList.find { it.id == userId } ?: return
        val builder = AlertDialog.Builder(this)

        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_user, null)
        val editNameEditText = dialogView.findViewById<EditText>(R.id.editNameEditText)
        val editAgeEditText = dialogView.findViewById<EditText>(R.id.editAgeEditText)
        val editNumberEditText=dialogView.findViewById<EditText>(R.id.editMobileEditText)

        editNameEditText.setText(user.name)
        editAgeEditText.setText(user.age.toString())
        editNumberEditText.setText((user.number.toString()))

        builder.setView(dialogView)
            .setTitle("Edit User")
            .setPositiveButton("Save")
            { _, _ ->
                val newName = editNameEditText.text.toString()
                val newAge = editAgeEditText.text.toString().toIntOrNull()
                val newNumber=editNumberEditText.text.toString().toIntOrNull()

                if (newName.isNotBlank() && newAge != null && newNumber!= null) {
                    // Update the user in the list and notify the adapter
                    val isUpdated = dbHelper.updateUser(userId, newName, newAge,newNumber)

            if(isUpdated){
                    val index = userList.indexOfFirst { it.id == userId }
                    if (index >= 0) {
                        userList[index].name = newName
                        userList[index].age = newAge
                        userList[index].number= newNumber

                        userAdapter.notifyItemChanged(index)

                        Toast.makeText(this, " User: ${user.name} updated", Toast.LENGTH_SHORT).show()
                    }
                } else {
                Toast.makeText(this, "Failed to updateUser:  ${user.name} in database", Toast.LENGTH_SHORT).show()
            }
}
            else {
                    Toast.makeText(this, "Please enter valid data", Toast.LENGTH_SHORT).show()
                }
                }

            .setNegativeButton("Cancel", null).show()
    }
    private fun showDeleteConfirmation(userId: Int, name: String) {
        AlertDialog.Builder(this)
            .setTitle("Delete User")
            .setMessage("Are you sure you want to delete this user?")
            .setPositiveButton("Yes")
            { _, _ ->
                val deletedRows = dbHelper.deleteUser(userId)
                if (deletedRows > 0) {
                    loadUsers()  // Reload the list to reflect the deletion
                    Toast.makeText(this, "User: ${name} deleted", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this, "Error deleting  ${name}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .setNegativeButton("No", null).show()
    }
}