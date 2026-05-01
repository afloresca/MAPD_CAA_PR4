
package com.uoc.pr1.data

import androidx.lifecycle.MutableLiveData
import com.uoc.pr1.data.hardcode.SeminaryHardcode1
import com.uoc.pr1.data.hardcode.SeminaryHardcode2
import com.uoc.pr1.data.model.*
import java.io.IOException


class DataSourceHardcode:DataSource {


    private val seminaryItemList = mutableListOf<Item>()

    private val userSeminaryList = mutableListOf<Seminary>()

    constructor():super(){
        seminarsLiveData = MutableLiveData(userSeminaryList)
        ItemsLiveData = MutableLiveData(seminaryItemList)
    }

    override fun login(username: String, password: String): Result<User>?  {
        try {
            // TODO: handle loggedInUser authentication

            var user_harcoded:User
            //BEGIN-CODE-UOC-1.3
           // user_harcoded = User(1, "Jane Doe")


            if(username=="user1@uoc.com") {
                user_harcoded = User(1, "Jane Doe")
            }
            else{
                user_harcoded = User(2, "John Doe")
            }


            //END-CODE-UOC-1.3
            return Result.Success(user_harcoded)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    override fun logout() {
        // TODO: revoke authentication
    }

    // ****************************************************************
    // Seminars
    override open fun selectSeminarssUser(user_id:Int){
        userSeminaryList.clear()
        if(user_id==1) {
            userSeminaryList.addAll(SeminaryHardcode1())
        }
        else{
            userSeminaryList.addAll(SeminaryHardcode2())
        }
    }





   // ****************************************************************
    // Items
    override fun selectItemsSeminary(seminary_id:Int){
        seminaryItemList.clear()
        if(seminary_id==1) seminaryItemList.addAll(ItemsHardcodeSeminary1())
        else if(seminary_id==2)  seminaryItemList.addAll(ItemsHardcodeSeminary2())
        else if(seminary_id==3)  seminaryItemList.addAll(ItemsHardcodeSeminary3())

    }

    override fun addItem(Item: Item) {
        val currentList = ItemsLiveData.value
        if (currentList == null) {
            ItemsLiveData.postValue(mutableListOf(Item))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, Item)
            ItemsLiveData.postValue(updatedList)
        }
    }

    /* Removes Item from liveData and posts value. */
    override fun removeItem(Item: Item) {
        val currentList = ItemsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(Item)
            ItemsLiveData.postValue(updatedList)
        }
    }


    /* Returns Item given an ID. */
    /*
    override fun getItemForId(id: Long): Item? {
        ItemsLiveData.value?.let { Items ->
            return Items.firstOrNull{ it.id == id}
        }
        return null
    }
*/




}