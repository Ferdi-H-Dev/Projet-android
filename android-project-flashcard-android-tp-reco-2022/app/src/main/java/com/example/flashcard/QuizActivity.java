package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    TextView questionTextView;
    RadioGroup answerRadioGroup;
    RadioButton answerRadioButton1;
    RadioButton answerRadioButton2;
    RadioButton answerRadioButton3;
    RadioButton answerRadioButton4;
    ImageView picture;
    Button submit;


    QuestionList quiz;
    Question current;

//--------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.questionTextView);
        picture = findViewById(R.id.pictureImageView);
        answerRadioGroup = findViewById(R.id.answerRadioGroup);
        answerRadioButton1 = findViewById(R.id.answerRadioButton1);
        answerRadioButton2 = findViewById(R.id.answerRadioButton2);
        answerRadioButton3 = findViewById(R.id.answerRadioButton3);
        answerRadioButton4 = findViewById(R.id.answerRadioButton4);
        submit = findViewById(R.id.summit);

        // récupérer le quiz de l'intent  :
        Intent srcIntent = getIntent();
        quiz = srcIntent.getParcelableExtra("Quiz");

        int size = quiz.size();


            current = (quiz.getCurrentQuestion());
            playQuestion(current);












        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = 0;


                int selected = answerRadioGroup.getCheckedRadioButtonId();
                Log.i("MainActivity", "selected =" + selected);

                if (selected == answerRadioButton1.getId()) {
                    index = 0;

                } else if (selected == answerRadioButton2.getId()) {
                    index = 1;
                } else if (selected == answerRadioButton3.getId()) {
                    index = 2;
                } else if (selected == answerRadioButton4.getId()) {
                    index = 3;
                }


                if (index == current.getCorrectAnswerIndex()) {
                    Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                    quiz.goodAnswersCount++;

                } else {
                    Toast.makeText(QuizActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();



                }

                if (quiz.size() > 1) {

                    if (quiz.nextQuestionIndex < (quiz.size() - 1)) {


                        Intent intent = new Intent(QuizActivity.this, QuizActivity.class);
                        quiz.getNextQuestion();
                        intent.putExtra("Quiz", quiz);
                        startActivity(intent);
                        finish();


                    } else {

                        Intent intent = new Intent(QuizActivity.this, Result.class);
                        intent.putExtra("nbQuestions", size);
                        intent.putExtra("goodAnswers", quiz.goodAnswersCount);
                        startActivity(intent);


                    }
                } else {

                    Intent intent = new Intent(QuizActivity.this,QuestionView.class);
                    startActivity(intent);
                }


            }


                   });



    }

    private void playQuestion(final Question question) {
        questionTextView.setText(question.question);
        answerRadioButton1.setText(question.getAnswersList().get(0));
        answerRadioButton2.setText(question.getAnswersList().get(1));
        answerRadioButton3.setText(question.getAnswersList().get(2));
        answerRadioButton4.setText(question.getAnswersList().get(3));
        picture.setImageResource(question.picture);

    }





}

