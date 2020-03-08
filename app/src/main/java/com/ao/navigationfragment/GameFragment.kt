package com.ao.navigationfragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.ao.navigationfragment.databinding.FragmentGameBinding

/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment() {

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 3)

     @SuppressLint("ResourceType")
     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val bindingRout  = DataBindingUtil.inflate<FragmentGameBinding>(inflater,
            R.layout.fragment_game, container, false)

         // Shuffles the questions and sets the question index to the first question.
         randomizeQuestions()
         // Bind this fragment class to the layout
            bindingRout.game   = this

         bindingRout.submitButton.setOnClickListener {v:View ->
             val checkedId = bindingRout.questionRadioGroup.checkedRadioButtonId
                if (-1 != checkedId ){
                    var answerIndex = 0
                    when (checkedId) {
                        R.id.secondAnswerRadioButton -> answerIndex = 1
                        R.id.thirdAnswerRadioButton -> answerIndex = 2
                        R.id.fourthAnswerRadioButton -> answerIndex = 3
                    }
                    // The first answer in the original question is always the correct one, so if our
                    // answer matches, we have the correct answer.
                     if (answers[answerIndex] == currentQuestion.answers[0] ){
                         questionIndex ++
                         // this  Advance to the next question
                         if (questionIndex < numQuestions) {
                             currentQuestion = questions[questionIndex]
                             setQuestion()
                             bindingRout.invalidateAll()
                         } else {
                             // We've won!  Navigate to the gameWonFragment.
                             v.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(numQuestions,questionIndex))
                         }
                     }else{
                         v.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment())


                     }
                }

        }

        return bindingRout.root


    }
    // randomize the questions and set the first question
    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }
    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        // randomize the answers into a copy of the array
        answers = currentQuestion.answers.toMutableList()
        // and shuffle them
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions)
    }



}
