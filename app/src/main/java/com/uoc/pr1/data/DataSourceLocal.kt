package com.uoc.pr1.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.uoc.data.localstorage.DbHelper
import com.uoc.pr1.R
import com.uoc.pr1.data.model.*
import java.io.FileOutputStream
import java.io.InputStream


class DataSourceLocal:  DataSource {

    private lateinit var dbHelper: DbHelper
    private lateinit  var db: SQLiteDatabase
    private lateinit  var db_read: SQLiteDatabase

    private val seminarItemList = mutableListOf<Item>()

    private val userSeminarList = mutableListOf<Seminary>()

    private lateinit  var context: Context

    private var _user_id = 0

    constructor(context: Context?) : super() {
        seminarsLiveData = MutableLiveData(userSeminarList)
        ItemsLiveData = MutableLiveData(seminarItemList)

        dbHelper = DbHelper(context!!)
        db = dbHelper.writableDatabase
        db_read = dbHelper.readableDatabase

        this.context =  context
    }

    override fun login(username: String, password: String): Result<User>? {

        lateinit var result:Result<User>




        // var user_harcoded:User  = User(1, "Jane Doe")
        // result =  Result.Success(user_harcoded)

        val str_sql = "SELECT * FROM user WHERE user_username = '$username' and  user_pwd='$password'"

        val cursor2 = db_read.rawQuery(
            str_sql
            , null)

        with(cursor2) {
            if (moveToNext()) {

                val user_id = getInt(getColumnIndexOrThrow("user_id"))
                val user_display_name = getString(getColumnIndexOrThrow("user_display_name"))

                _user_id = user_id;

                var user:User = User(user_id,user_display_name)
                result =  Result.Success(user)
            }
            else{
                _user_id = 0;
                return Result.Error(DataSourceException("User not exists"))
            }
        }
        cursor2.close()





        return result

    }

    override fun logout() {
        _user_id = 0
    }

    // ****************************************************************
    // Seminars
    override open fun selectSeminarssUser(user_id: Int) {
        userSeminarList.clear()
        // Load user's seminars

        val str_sql = "SELECT * FROM seminar,user_seminar WHERE  usersem_user_id= $user_id and usersem_seminar_id = sem_id"

        val cursor2 = db_read.rawQuery(
            str_sql
            , null)

        with(cursor2) {
            while (moveToNext()) {

                val sem_id = getInt(getColumnIndexOrThrow("sem_id"))
                val sem_name = getString(getColumnIndexOrThrow("sem_name"))
                val sem_dur = getInt(getColumnIndexOrThrow("sem_duration"))
                val sem_level = getString(getColumnIndexOrThrow("sem_level"))
                var image_path:String = getString(getColumnIndexOrThrow("sem_image_url"))

                userSeminarList.add(Seminary(sem_id,sem_name,sem_dur,sem_level,image_path,null,null))

            }

        }
        cursor2.close()

    }

    // ****************************************************************
    // Items
    override fun selectItemsSeminary(id:Int){

        seminarItemList.clear()




        val str_sql = "SELECT * FROM items WHERE item_sem_id= $id"

        val cursor2 = db_read.rawQuery(
            str_sql
            , null)

        with(cursor2) {
            while (moveToNext()) {

                val item_type = getInt(getColumnIndexOrThrow("item_type"))
                val item_id = getInt(getColumnIndexOrThrow("item_id"))
                val item_question = getString(getColumnIndexOrThrow("item_question"))
                val item_link = getString(getColumnIndexOrThrow("item_link"))


                val item_correct_answer = getInt(getColumnIndexOrThrow("item_correct_answer"))
                val item_answer1 = getString(getColumnIndexOrThrow("item_answer1"))
                val item_answer2 = getString(getColumnIndexOrThrow("item_answer2"))
                val item_answer3 = getString(getColumnIndexOrThrow("item_answer3"))
                val item_answer4 = getString(getColumnIndexOrThrow("item_answer4"))


                val map = ItemType.values().associateBy(ItemType::v1)
                var type: ItemType? = map[item_type]

                seminarItemList.add(Item(type!!,item_id,item_question,item_link,item_correct_answer as Long,item_answer1,item_answer2,item_answer3,item_answer4))


            }

        }
        cursor2.close()


        // END-CODE-UOC-3.3



        ItemsLiveData.postValue(seminarItemList)
    }


    fun getNewId():Int
    {

        val str_sql = "SELECT max(sem_id)+1 as 'new_sem_id' FROM seminar"

        val cursor2 = db_read.rawQuery(
            str_sql
            , null)

        var new_sem_id = -1

        with(cursor2) {
            while (moveToNext()) {

                new_sem_id = getInt(getColumnIndexOrThrow("new_sem_id"))

            }

        }
        cursor2.close()

        return new_sem_id
    }

    // The method must force the seminar duration to be 60 and the sem level to be a 'beginner.'
    override fun addSeminary(name:String, uri:Uri?) {

        val new_id:Int = getNewId()

        var name_sql = name.replace("'","''");

        var str_sql = "INSERT INTO 'main'.'seminar' ('sem_id', 'sem_name', 'sem_duration', 'sem_level') VALUES ($new_id, '$name_sql', 60, '')"




        db.execSQL(str_sql)

        str_sql = "INSERT INTO 'main'.'user_seminar' ('usersem_user_id', 'usersem_seminar_id') VALUES ($_user_id, $new_id)"

        db.execSQL(str_sql)


        var image_path:String = ""


        if(uri!=null) {
            val inputStream: InputStream? = this.context.getContentResolver().openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            val directory_path = this.context.filesDir.path + "/media/seminar/"

            image_path = directory_path + new_id + ".jpg"

            val out = FileOutputStream(image_path)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        }



        val seminary:Seminary = Seminary(new_id,name_sql,60,"beginner" ,image_path, null, null)



        val currentList = seminarsLiveData.value
        if (currentList == null) {
            seminarsLiveData.postValue(mutableListOf(seminary))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, seminary)
            seminarsLiveData.postValue(updatedList)
        }


    }





}