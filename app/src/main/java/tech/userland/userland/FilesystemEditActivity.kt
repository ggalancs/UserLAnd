package tech.userland.userland

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_filesystem_edit.*
import tech.userland.userland.database.models.Filesystem
import tech.userland.userland.database.repositories.FilesystemRepository
import java.util.*

class FilesystemEditActivity: AppCompatActivity() {
    var newFilesystemName: String = ""
    var newFilesystemType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filesystem_edit)
        setSupportActionBar(toolbar)

        val nameInput: TextInputEditText = findViewById(R.id.input_filesystem_name)
        nameInput.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                newFilesystemName = p0.toString()
                Toast.makeText(this@FilesystemEditActivity, newFilesystemName, Toast.LENGTH_LONG).show()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        // Session type dropdown
        val filesystemTypeList = ArrayList<String>()
        filesystemTypeList.add("debian")

        val filesystemTypeDropdown: Spinner = findViewById(R.id.spinner_filesystem_type)
        val filesystemTypeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, filesystemTypeList)
        filesystemTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filesystemTypeDropdown.adapter = filesystemTypeAdapter
        filesystemTypeDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val data = parent.getItemAtPosition(position)
                Toast.makeText(this@FilesystemEditActivity, "Type " + data.toString(), Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_item_add -> {
                insertFilesystem()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun insertFilesystem() {
        val date = Date()
        val newFilesystem = Filesystem(newFilesystemName, false, "/", newFilesystemType, date.toString())
        FilesystemRepository(this@FilesystemEditActivity).insertFilesystem(newFilesystem)
    }
}