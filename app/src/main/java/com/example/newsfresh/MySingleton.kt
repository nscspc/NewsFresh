package com.example.newsfresh

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

// singleton class is the class which can has only one instance.
class MySingleton constructor(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: MySingleton? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: MySingleton(context).also {
                    INSTANCE = it
                }
            }
    }
//    val imageLoader: ImageLoader by lazy {
//        ImageLoader(requestQueue,
//            object : ImageLoader.ImageCache {
//                private val cache = LruCache<String, Bitmap>(20)
//                override fun getBitmap(url: String): Bitmap {
//                    return cache.get(url)
//                }
//                override fun putBitmap(url: String, bitmap: Bitmap) {
//                    cache.put(url, bitmap)
//                }
//            })
//    }
    private val requestQueue: RequestQueue by lazy {
        // applicationContext is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }
    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
}


/*
Use a singleton pattern
If your application makes constant use of the network, it’s probably most efficient to set up a single instance of RequestQueue that will last the lifetime of your app. You can achieve this in various ways. The recommended approach is to implement a singleton class that encapsulates RequestQueue and other Volley functionality. Another approach is to subclass Application and set up the RequestQueue in Application.onCreate(). But this approach is discouraged; a static singleton can provide the same functionality in a more modular way.

A key concept is that the RequestQueue must be instantiated with the Application context, not an Activity context. This ensures that the RequestQueue will last for the lifetime of your app, instead of being recreated every time the activity is recreated (for example, when the user rotates the device).
 */