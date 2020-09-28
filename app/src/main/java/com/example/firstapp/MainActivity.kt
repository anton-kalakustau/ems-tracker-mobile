package com.example.firstapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import java.time.Instant
import java.time.format.DateTimeFormatter

const val SEARCH_EMS_ID = "com.example.firstApp.EMS_ID"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DbHandler.initialize(applicationContext)
    }

    fun search(view: View) {
        val emsInputComponent = findViewById<EditText>(R.id.emsIdTxt);
        val emsId = "EG688165502JP";// emsInputComponent.text.toString();
        val apiCallResult =  DataFetcher().execute(emsId).get();

        if (DbHandler.db == null) {
            AlertDialog.Builder(applicationContext)
                .setTitle("An error has occurred.")
                .setMessage("A DB connection could not be opened")
                .show();
        }

        val dbItem = DbHandler.GetByIdExecutor().execute(emsId).get(); // DbHandler.db?.emsDao()?.findByEmsId(emsId);
        if (dbItem == null) {
            DbHandler.InsertExecutor().execute(SearchHistoryItem(emsId, DateTimeFormatter.ISO_INSTANT.format(
               Instant.now()), apiCallResult.size.toString()));
        }

        val intent = Intent(this, SearchResultActivity::class.java).apply {
            putExtra(SEARCH_EMS_ID, apiCallResult)
        }
        startActivity(intent)
    }

    fun history(view: View) {
        val intent = Intent(this, SearchHistoryActivity::class.java)
        startActivity(intent)
    }

    /*
    Final plan
    - Simple search
    - Search history
    - Background search
    - Notifications
     */

    /*
    * Planned features
    * 1. Simple search + input validation
    * 2. Copy results to buffer, optimisation (caching etc), search history
    * 3. Setup a watcher to periodically query a defined set of searches and trigger notification about changes
    * **4. Attempt to add integration to google maps to show current approximate location of the item AND/OR "share" integration
    * */
}
