package com.uoc.pr1.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import com.uoc.pr1.R
import com.uoc.pr1.data.model.Seminary
import com.uoc.pr1.data.model.Item
import com.uoc.pr1.data.model.ItemType
import com.uoc.pr1.data.model.User
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class DataSourceFirebase :  DataSource {

    private val seminarItemList = mutableListOf<Item>()
    private val userSeminaryList = mutableListOf<Seminary>()


    private lateinit  var context: Context
    private var _user_id = 0

    constructor(context: Context?) : super() {
        seminarsLiveData = MutableLiveData(userSeminaryList)
        ItemsLiveData = MutableLiveData(seminarItemList)
        this.context =  context!!




    }



    override fun loginAsync(username: String, password: String,listener:ListenerData){

        lateinit var result:Result<User>



        val db = FirebaseFirestore.getInstance()




        db.collection("user")
            .whereEqualTo("user_username", username)
            .whereEqualTo("user_pwd", password)
            .get(Source.SERVER)
            .addOnSuccessListener { querySnapshot ->
                if(!querySnapshot.isEmpty()){
                    val doc_user = querySnapshot.documents.get(0)
                    val user_id = doc_user.data?.get("user_id") as Long
                    var user_display_name = doc_user.data?.get("user_display_name") as String
                    var user:User = User(user_id.toInt(),user_display_name)
                    result =  Result.Success(user)
                    _user_id = user_id.toInt()
                    listener.onLogin(result);
                }
                else{

                    _user_id = 0;
                    listener.onLogin(Result.Error(DataSourceException("User not exists")));
                }
            }
            .addOnFailureListener { exception ->
                _user_id = 0;
                listener.onLogin(Result.Error(DataSourceException("User not exists")));
                Log.w("Firestore", "Error getting documents $exception")
            }





    }

    override fun logout() {
        _user_id = 0
    }


    fun readSeminarsUserIdsAsync(listener:ListenerData){

        val result = mutableListOf<Long>()
        //BEGIN-CODE-UOC-3.1
        val db = FirebaseFirestore.getInstance()
        db.collection("user_seminary")
            .whereEqualTo("user_id", _user_id)
            .get(Source.SERVER)
            .addOnSuccessListener { querySnapshot ->
                result.clear()
                if(!querySnapshot.isEmpty()){
                    for(doc in querySnapshot.documents){
                        val seminar_id = doc.data?.get("sem_id") as Long
                        result.add(seminar_id)
                    }
                }
                listener.onSeminarsUserIds(result)
            }
            .addOnFailureListener { exception ->
                result.clear()
                listener.onSeminarsUserIds(result)
                Log.w("Firestore", "Error getting documents $exception")
            }
        //END-CODE-UOC-3.1
    }

    //**********************************************************************
    override fun selectSeminarsUserAsync(user_id:Int, listener:ListenerData) {
        userSeminaryList.clear()


        listener.onSeminarsUserIds = { list_ids ->
            if (!list_ids.isEmpty()){
                //BEGIN-CODE-UOC-3.2
                val db = FirebaseFirestore.getInstance()
                db.collection("seminary")
                    .whereIn("sem_id", list_ids)
                    .orderBy("sem_id")
                    .get(Source.SERVER)
                    .addOnSuccessListener { querySnapshot ->
                        userSeminaryList.clear()
                        if (!querySnapshot.isEmpty()) {
                            for (doc in querySnapshot.documents) {
                                val sem_id = doc.data?.get("sem_id") as Int
                                val sem_name = doc.data?.get("sem_name") as String
                                val sem_duration = doc.data?.get("sem_duration") as Int
                                val sem_level = doc.data?.get("sem_level") as String
                                val sem_image_url = doc.data?.get("sem_image_url") as String
                                var seminar = Seminary(
                                    sem_id,
                                    sem_name,
                                    sem_duration,
                                    sem_level,
                                    sem_image_url
                                )
                                userSeminaryList.add(seminar)
                            }
                        }
                        listener.onSeminarsUser()
                    }
                    .addOnFailureListener { exception ->
                        userSeminaryList.clear()
                        listener.onSeminarsUser()
                        Log.w("Firestore", "Error getting documents $exception")
                    }
                //END-CODE-UOC-3.2
            }
        }

        readSeminarsUserIdsAsync(listener)


    }

    // ************************************************************


    override fun selectItemsSeminary(id:Int,listener:ListenerData){
        seminarItemList.clear()


        //BEGIN-CODE-UOC-4.3




        //END-CODE-UOC-4.3

    }

    // ****************************************************

    fun ReloadViewModelSeminar(item:Seminary){
        val currentList = seminarsLiveData.value
        if (currentList == null) {
            seminarsLiveData.postValue(mutableListOf(item))
        } else {
            val updatedList = currentList.toMutableList()
           // updatedList.add(0, item)
            updatedList.add(item)

            seminarsLiveData.postValue(updatedList)
        }
    }

    fun ReloadViewModel(item:Item)
    {
        val currentList = ItemsLiveData.value
        if (currentList == null) {
            ItemsLiveData.postValue(mutableListOf(item))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, item)
            ItemsLiveData.postValue(updatedList)
        }

    }

    fun getNewRequestId(listener: ListenerData)
    {






    }

    fun getNewSeminarId(listener: ListenerData)
    {

        //BEGIN-CODE-UOC_5.1



        //END-CODE-UOC_5.1




    }

    override fun addSeminarAsync(title:String, url:String, sem_duration: Int, sem_level:String, listener:ListenerData) {

        listener.onNewSeminarId = { new_id ->

            val hashMap = hashMapOf<String, Any>(
                "sem_name" to title,
                "sem_id" to new_id,
                "sem_duration" to sem_duration,
                "sem_level" to sem_level,
                "sem_image_url" to url
            )

        //BEGIN-CODE-UOC-5.2




        //BEGIN-CODE-UOC-5.2

        }

        getNewSeminarId(listener)

    }


    override fun addItemAsync(title:String, description:String, uri: Uri?,listener:ListenerData) {

        listener.onNewRequestId = { new_id ->

            lateinit var inputStream:InputStream

            try {
                inputStream = this.context.getContentResolver().openInputStream(uri!!)!!

            }
            catch(e: Exception)
            {
                Log.d("error",e.message!!)

            }

            val bitmap = BitmapFactory.decodeStream(inputStream)



        }

        getNewRequestId(listener)



    }


}