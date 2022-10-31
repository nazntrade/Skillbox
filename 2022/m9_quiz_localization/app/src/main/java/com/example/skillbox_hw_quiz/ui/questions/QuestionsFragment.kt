package com.example.skillbox_hw_quiz.ui.questions

import android.os.Bundle
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.databinding.FragmentQuestionsBinding
import com.example.skillbox_hw_quiz.databinding.ItemQuestionBinding
import com.example.skillbox_hw_quiz.utils.ViewBindingFragment


class QuestionsFragment :
    ViewBindingFragment<FragmentQuestionsBinding>(FragmentQuestionsBinding::inflate) {

    private val viewModel: QuestionViewModel by viewModels()
    private lateinit var radioGroup1: ItemQuestionBinding
    private lateinit var radioGroup2: ItemQuestionBinding
    private lateinit var radioGroup3: ItemQuestionBinding
    private lateinit var radioGroupList: List<RadioGroup>
    private lateinit var ansverButton: Button
    private val indexList = mutableListOf<Int>()
    private lateinit var rationale: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        radioGroup1 = binding.radioGroup1
        radioGroup2 = binding.radioGroup2
        radioGroup3 = binding.radioGroup3
        ansverButton = binding.answerButton
        radioGroup2.radioGroup.isGone = true
        radioGroup3.radioGroup.isGone = true
        ansverButton.isEnabled = false
        radioGroupList = listOf(
            radioGroup1.radioGroup,
            radioGroup2.radioGroup,
            radioGroup3.radioGroup
        )
        initToolBar()
        initQuestionnaire()
        getResult()
    }

    private fun initToolBar() {
        val toolbar = binding.appBar.toolBar
        toolbar.title = getString(R.string.questions)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    private fun initQuestionnaire() {
        viewModel.loadQuiz()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.quizFlow.collect { quiz ->
                quiz?.let { safeQuiz ->
                    radioGroup1.questionTextView.text = safeQuiz.questions[0].question
                    radioGroup1.radioButton1.text = safeQuiz.questions[0].answers[0]
                    radioGroup1.radioButton2.text = safeQuiz.questions[0].answers[1]
                    radioGroup1.radioButton3.text = safeQuiz.questions[0].answers[2]
                    radioGroup1.radioButton4.text = safeQuiz.questions[0].answers[3]
                    radioGroup2.questionTextView.text = safeQuiz.questions[1].question
                    radioGroup2.radioButton1.text = safeQuiz.questions[1].answers[0]
                    radioGroup2.radioButton2.text = safeQuiz.questions[1].answers[1]
                    radioGroup2.radioButton3.text = safeQuiz.questions[1].answers[2]
                    radioGroup2.radioButton4.text = safeQuiz.questions[1].answers[3]
                    radioGroup3.questionTextView.text = safeQuiz.questions[2].question
                    radioGroup3.radioButton1.text = safeQuiz.questions[2].answers[0]
                    radioGroup3.radioButton2.text = safeQuiz.questions[2].answers[1]
                    radioGroup3.radioButton3.text = safeQuiz.questions[2].answers[2]
                    radioGroup3.radioButton4.text = safeQuiz.questions[2].answers[3]
                }
            }
        }
    }

    private fun toggle(radioGroup: RadioGroup) {
        val transition: Transition = Fade()
        transition.duration = 1000
        transition.addTarget(radioGroup)
        TransitionManager.beginDelayedTransition(binding.root, transition)
        radioGroup.isGone = false
    }

    private fun getResult() {
        radioGroupList.forEach { objectRadioGroup ->
            objectRadioGroup.setOnCheckedChangeListener { group, checkedId ->
                when (group) {
                    radioGroup1.radioGroup -> toggle(radioGroup2.radioGroup)
                    radioGroup2.radioGroup -> toggle(radioGroup3.radioGroup)
                    radioGroup3.radioGroup -> ansverButton.isEnabled = true
                }
                val radioButton: View = group.findViewById(checkedId)
                val idx: Int = group.indexOfChild(radioButton)
                indexList.add(idx)
            }
        }

        binding.answerButton.setOnClickListener {
            rationale = viewModel.getAnswers(indexList)
            val directions =
                QuestionsFragmentDirections.actionQuestionsFragmentToResultFragment(
                    rationale
                )
            findNavController().navigate(directions)
        }
    }

    override fun onResume() {
        super.onResume()
        radioGroupList.forEach { objectRadioGroup ->
            objectRadioGroup.setOnCheckedChangeListener(null)
        }
        radioGroupList.forEach { it.clearCheck() }
        ansverButton.isEnabled = false
        getResult()
        indexList.clear()
        rationale = ""
    }
}