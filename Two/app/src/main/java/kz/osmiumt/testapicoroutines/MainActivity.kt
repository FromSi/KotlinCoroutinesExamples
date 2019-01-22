package kz.osmiumt.testapicoroutines

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val recyclerAdapterPost = RecyclerAdapterPost()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        makeRequest()
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recyclerView.adapter = recyclerAdapterPost
    }

    private fun makeRequest(){
        GlobalScope.launch(Dispatchers.Main){
            try {
                val response = RetrofitFactory.makeRetrofitService().getPost()
                recyclerAdapterPost.list = response.await()
            }  catch (e: Throwable) {
                Log.e("Error", "Ooops: Something else went wrong")
            }
        }
    }
}
