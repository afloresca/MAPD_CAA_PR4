package com.uoc.pr1.ui.quiz

import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.uoc.pr1.MainActivity
import com.uoc.pr1.R
import com.uoc.pr1.data.DataSource
import com.uoc.pr1.data.model.Item
import com.uoc.pr1.data.model.Seminary
import com.uoc.pr1.ui.list.SeminarsAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizFragment : Fragment() {

    val dataSource: DataSource = DataSource.getDataSource(DataSource.DataSourceFactory.Default)
    private var answers: Array<Long>? = null
    private var current_question: Int = 0
    private var fragmentView:View? = null

    private var question: TextView? = null
    private var answer1: TextView? = null
    private var answer2: TextView? = null
    private var answer3: TextView? = null
    private var answer4: TextView? = null
    private var link: TextView? = null
    private var nextButton: Button? = null
    private var prevButton: Button? = null
    private var finishButton: Button? = null



    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    public var item: Seminary? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }



    }

    public fun LoadValuesCurrentQuestion()
    {

        val item: Item = dataSource.getItemPos(current_question)


        question!!.text = item.question

        answer1!!.text = item.answer1
        answer1!!.setBackgroundColor(Color.parseColor("#EEEEEE"))


        answer2!!.text = item.answer2
        answer2!!.setBackgroundColor(Color.parseColor("#EEEEEE"))


        answer3!!.text = item.answer3
        answer3!!.setBackgroundColor(Color.parseColor("#EEEEEE"))


        answer4!!.text = item.answer4
        answer4!!.setBackgroundColor(Color.parseColor("#EEEEEE"))

        // Set background color


        if(answers!![current_question]!=-1L){
            if(answers!![current_question]==1L){
                answer1!!.setBackgroundColor(Color.GREEN)
            }
            else if(answers!![current_question]==2L){
                answer2!!.setBackgroundColor(Color.GREEN)
            }
            else if(answers!![current_question]==3L){
                answer3!!.setBackgroundColor(Color.GREEN)
            }
            else if(answers!![current_question]==4L){
                answer4!!.setBackgroundColor(Color.GREEN)
            }
        }

    }

    fun ClickAnswerLogic(pos:Long)
    {
        if(pos!=answers!![current_question])
        {
            if(answers!![current_question]==1L){
                answer1!!.setBackgroundColor(Color.parseColor("#EEEEEE"))
            }
            else if(answers!![current_question]==2L){
                answer2!!.setBackgroundColor(Color.parseColor("#EEEEEE"))
            }
            else if(answers!![current_question]==3L){
                answer3!!.setBackgroundColor(Color.parseColor("#EEEEEE"))
            }
            else if(answers!![current_question]==4L){
                answer4!!.setBackgroundColor(Color.parseColor("#EEEEEE"))
            }

            answers!![current_question] = pos


            if(answers!![current_question]==1L){
                answer1!!.setBackgroundColor(Color.GREEN)
            }
            else if(answers!![current_question]==2L){
                answer2!!.setBackgroundColor(Color.GREEN)
            }
            else if(answers!![current_question]==3L){
                answer3!!.setBackgroundColor(Color.GREEN)
            }
            else if(answers!![current_question]==4L){
                answer4!!.setBackgroundColor(Color.GREEN)
            }


        }

    }

    public fun ButtonsViewLogic()
    {


        prevButton?.visibility = View.INVISIBLE
        nextButton?.visibility = View.INVISIBLE
        finishButton?.visibility = View.INVISIBLE
        link?.visibility = View.INVISIBLE

        if(current_question!=0){
            prevButton?.visibility = View.VISIBLE
        }

        if(current_question==answers!!.size-1){

            finishButton?.visibility = View.VISIBLE
        }
        else{
            nextButton?.visibility = View.VISIBLE

        }

        val item: Item = dataSource.getItemPos(current_question)

        if((item.link!=null)&&(item.link!="")){
            link?.visibility = View.VISIBLE
        }
    }

    public fun Finish()
    {
        val correct:Int = dataSource.getCorrects(answers!!)

        (activity as MainActivity?)!!.returnFromQuiz(false,correct,answers!!.size)

    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v:View = inflater.inflate(R.layout.fragment_quiz, container, false)
        fragmentView = v


        var v2: ImageView = v.findViewById(R.id.item_image_detail)

       // val bm: Bitmap =  BitmapFactory.decodeFile(item!!.image_path);
        // v2.setImageBitmap(bm)

        //BEGIN-CODE-UOC-3.4
        SeminarsAdapter.ItemViewHolder(v2,{}).bind(item!!)



        //BEGIN-CODE-UOC-3.4



        // CODE UOC
        answers = Array(dataSource.getCountItems()) { -1 }

        question = fragmentView!!.findViewById(R.id.item_text_title)
        answer1 = fragmentView!!.findViewById(R.id.answer1)
        answer2= fragmentView!!.findViewById(R.id.answer2)
        answer3= fragmentView!!.findViewById(R.id.answer3)
        answer4= fragmentView!!.findViewById(R.id.answer4)
        link= fragmentView!!.findViewById(R.id.link)
        nextButton= fragmentView!!.findViewById(R.id.next)
        prevButton= fragmentView!!.findViewById(R.id.prev)
        finishButton= fragmentView!!.findViewById(R.id.finish)



        // Set onClickListeners for the views (excluding item_image_detail and item_text_title)
        answer1!!.setOnClickListener {
            // Handle click for answer1
            ClickAnswerLogic(1)
        }

        answer2!!.setOnClickListener {
            // Handle click for answer2
            ClickAnswerLogic(2)
        }

        answer3!!.setOnClickListener {
            // Handle click for answer3
            ClickAnswerLogic(3)
        }

        answer4!!.setOnClickListener {
            // Handle click for answer4
            ClickAnswerLogic(4)
        }

        link!!.setOnClickListener {
            // Handle click for link
            (activity as MainActivity?)!!.OpenLink(dataSource.getItemPos(current_question).link!!)

        }

        nextButton!!.setOnClickListener {
            // Handle click for next button
            if(current_question<answers!!.size-1){
                current_question++
                ButtonsViewLogic()
                LoadValuesCurrentQuestion()
            }
        }

        prevButton!!.setOnClickListener {
            // Handle click for prev button
            if(current_question>0){
                current_question--
                ButtonsViewLogic()
                LoadValuesCurrentQuestion()
            }

        }

        finishButton!!.setOnClickListener {
            // Handle click for finish button
            Finish()


        }



        //END-CODE-UOC

        ButtonsViewLogic()
        LoadValuesCurrentQuestion()





        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuizFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}