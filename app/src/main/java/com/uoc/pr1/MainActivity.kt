package com.uoc.pr1

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.ChangeTransform
import android.transition.TransitionSet
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.uoc.pr1.data.DataSource
import com.uoc.pr1.data.ListenerData
import com.uoc.pr1.data.model.Seminary
import com.uoc.pr1.databinding.ActivityMainBinding
import com.uoc.pr1.ui.list.SeminarsFragment
import com.uoc.pr1.ui.login.LoginFragment
import com.uoc.pr1.ui.login.LoginViewModel
import com.uoc.pr1.ui.login.LoginViewModelFactory
import com.uoc.pr1.ui.quiz.QuizFragment

class DetailsTransition : TransitionSet() {
    init {
        setOrdering(ORDERING_TOGETHER)
        addTransition(ChangeBounds())
        addTransition(ChangeTransform())
        addTransition(ChangeImageTransform())
    }
}


class MainActivity : AppCompatActivity(), LoginFragment.OnFragmentLoginInteractionListener {

    lateinit var dataSource: DataSource

    private val sharedViewModel: LoginViewModel by viewModels<LoginViewModel>() {
        LoginViewModelFactory()
    }

    lateinit var f1: LoginFragment

    var seminars_fragment: SeminarsFragment? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataSource = DataSource.getDataSource(DataSource.DataSourceFactory.Default,this)


    //    dataSource.selectItemsSeminary(0)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)





        f1 = LoginFragment()
        f1.loginViewModel = sharedViewModel





        supportFragmentManager.commit {
        //     setReorderingAllowed(true)
            add(R.id.placeholder1, f1)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)


        }




    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 1)
        {
            f1.Reset()


        }


        super.onBackPressed()
    }








    public fun ShowAlertResult(correct:Int,count:Int)
    {
        val builder = AlertDialog.Builder(this)

        with(builder)
        {
            setTitle("Result")
            setMessage("Correct: $correct / Total: $count")
            setPositiveButton("OK", DialogInterface.OnClickListener(
                { dialogInterface: DialogInterface, i: Int ->

                    dialogInterface.dismiss()


                }))

            show()
        }



    }



    fun returnFromQuiz(wow:Boolean,correct:Int,count:Int)
    {
        supportFragmentManager.popBackStack()
        ShowAlertResult(correct,count)
    }

    fun OpenLink(link:String)
    {
        val intent = Intent(this, LinkActivity::class.java).apply {
            putExtra("link",link)

        }
        startActivity(intent)
    }

    fun openQuiz(d: Seminary)
    {
        var f3: Fragment

        f3 = QuizFragment.newInstance("", "")
        f3.item = d






        f3.setSharedElementEnterTransition(DetailsTransition())
        f3.setSharedElementReturnTransition(DetailsTransition())

        listener.onItemsSeminar = {
            supportFragmentManager.commit {
                addSharedElement(d.view!!, "itemImage")
                replace(R.id.placeholder1, f3!!)
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                addToBackStack(null)


            }
        }

        dataSource.selectItemsSeminary(d.id,listener)



        //  ------------------------------------------------



    }


    var listener = ListenerData()
    fun LoginOK(userId:Int)
    {

        listener.onSeminarsUser = {
            LoginOK2()
        }

        dataSource.selectSeminarsUserAsync(userId,listener)

    }


    fun LoginOK2()
    {



        Log.d("TAG","value")

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager


        imm?.hideSoftInputFromWindow(binding.root.windowToken, 0)
        // pop login fragment
        // push list fragment


        if(seminars_fragment==null) {
            seminars_fragment = SeminarsFragment.Companion.newInstance()
        }


       supportFragmentManager.commit {

              replace(R.id.placeholder1, seminars_fragment!!)
              setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
              addToBackStack(null)


        }




    }

    override fun onFragmentLoginInteraction(mensaje: String) {
        Log.d("Debug",mensaje)

    }


}