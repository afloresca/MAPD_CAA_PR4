
package com.uoc.pr1.ui.list


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uoc.pr1.data.DataSource

class SeminarsViewModel(val dataSource: DataSource) : ViewModel() {

    val itemsLiveData = dataSource.getSeminars()


}

class SeminarsViewModelViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SeminarsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SeminarsViewModel(
                dataSource = DataSource.getDataSource(DataSource.DataSourceFactory.Default)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}