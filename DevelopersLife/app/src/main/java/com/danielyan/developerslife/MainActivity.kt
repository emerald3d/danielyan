package com.danielyan.developerslife

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.danielyan.developerslife.model.gifData
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var gifView: ImageView
    lateinit var previousButton: Button
    lateinit var description: TextView
    var gifURL: String = ""
    var currentGif: Int = 0
    var currentJson: String = ""
    lateinit var currentJSONObject: JSONObject
    var gifList = arrayListOf<gifData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiListener()
        setContentView(R.layout.activity_main)
        previousButton = findViewById(R.id.previousButton)
        previousButton.setEnabled(false)
        description = findViewById<TextView>(R.id.descView)
        gifView = findViewById(R.id.gifView)
        // Первая гифка
        gifList.add(gifData("https://media.giphy.com/media/xd9HUXswWPY1EEJ80a/giphy.gif", "Илон Маск за работой"))
        gifSet()

    }

    fun loadNextGif(view: View) {
        previousButton.setEnabled(true)
        currentGif++
        gifSet()
        apiListener()
    }

    fun loadPreviousGif(view: View) {
        if (currentGif-1 != -1)
            currentGif--
        if (currentGif == 0)
            previousButton.setEnabled(false)

        gifSet()
        apiListener()
    }

    fun apiListener() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://developerslife.ru/random?json=true"
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                currentJSONObject = JSONObject(response)
                gifList.add(gifData(currentJSONObject.getString("gifURL"), currentJSONObject.getString("description")))
            },
            Response.ErrorListener { currentJson = "That didn't work!" })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    fun gifSet () {
        gifURL = gifList.get(currentGif).gifURL.toString()
        Glide.with(this)
            .load(gifURL)
            .centerCrop()
            .into(gifView)
        description.text = gifList.get(currentGif).description
    }
}