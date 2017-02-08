package udacity.android.simplejavaquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static udacity.android.simplejavaquiz.R.id.abstract_class_answer;
import static udacity.android.simplejavaquiz.R.id.data_structure_answer;
import static udacity.android.simplejavaquiz.R.id.efficiency_answer;
import static udacity.android.simplejavaquiz.R.id.keyword_answer;
import static udacity.android.simplejavaquiz.R.id.platform_answer;

public class MainActivity extends AppCompatActivity {

    // A list of answer layouts
    private List<ViewGroup> answerLayouts;

    // The number of correct answers for the quiz
    private int numberOfCorrectAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize and populate the answer layouts
        answerLayouts = populateAnswerLayouts();
    }

    /**
     * This method initializes and populates the answerLayouts list.
     *
     * @return The populated List.
     */
    private List<ViewGroup> populateAnswerLayouts() {
        List<ViewGroup> answers = new ArrayList<>();

        // Add all of the answerLayouts
        answers.add((ViewGroup)findViewById(R.id.keyword_answer));
        answers.add((ViewGroup)findViewById(R.id.platform_answer));
        answers.add((ViewGroup)findViewById(R.id.efficiency_answer));
        answers.add((ViewGroup)findViewById(R.id.data_structure_answer));
        answers.add((ViewGroup)findViewById(R.id.abstract_class_answer));

        return answers;
    }

    /**
     * This method traverses the answer layouts and sends each
     * one checkAnswers method for evaluation. It then displays
     * a toast to notify the user of their score and resents the
     * answers.
     *
     * @param view Not used.
     */
    public void gradeQuiz(View view) {
        // Iterate over answerLayouts to check each answer
        for(ViewGroup answers: answerLayouts) {
            checkAnswers(answers);
        }

        // Display results to quiz taker
        String resultsDisplay = "You got " + numberOfCorrectAnswers + " out of " + answerLayouts.size()
                + " questions correct!";

        Toast toast = Toast.makeText(this, resultsDisplay, Toast.LENGTH_LONG);
        toast.show();

        // Reset the answers and number of correct answers
        resetDisplay();
    }

    /**
     * This method deter;mines if the questions have been answered correctly. It
     * receives the ViewGroup containing all of the possible answers for a question.
     *
     * @param answerLayout The ViewGroup holding the possible answers to a question.
     */
    private void checkAnswers(ViewGroup answerLayout) {
        // The id for the layout
        int id = answerLayout.getId();

        // ViewGroup holding possible answers to question
        ViewGroup answers;

        // Determine which question is being answered and whether or not it is correct
        if(id == R.id.keyword_answer) {
            // Parent ViewGroup holding the possible answers
            answers = (ViewGroup)findViewById(R.id.keyword_answer);

            // Possible answers
            CheckBox jumpCheckBox = (CheckBox)answers.getChildAt(0);
            CheckBox finallyCheckBox = (CheckBox)answers.getChildAt(1);
            CheckBox continueCheckBox = (CheckBox)answers.getChildAt(2);
            CheckBox instanceofCheckBox = (CheckBox)answers.getChildAt(3);

            // The conditions for a correct answer
            if(!jumpCheckBox.isChecked() && finallyCheckBox.isChecked() &&
                    continueCheckBox.isChecked() && instanceofCheckBox.isChecked()) {
                numberOfCorrectAnswers++;
            }
        } else if(id == R.id.platform_answer) {
            // Parent ViewGroup holding the possible answers
            answers = (ViewGroup)findViewById(R.id.platform_answer);

            // Possible answers, in case both were somehow selected
            RadioButton trueButton = (RadioButton)answers.getChildAt(0);
            RadioButton falseButton = (RadioButton)answers.getChildAt(1);

            // Conditions for correct answer
            if(trueButton.isChecked() && !falseButton.isChecked()) {
                numberOfCorrectAnswers++;
            }
        } else if(id == R.id.efficiency_answer) {
            // Parent ViewGroup holding the possible answers
            answers = (ViewGroup)findViewById(R.id.efficiency_answer);

            // Possible answers
            RadioButton linearButton = (RadioButton)answers.getChildAt(0);
            RadioButton logarithmicButton = (RadioButton)answers.getChildAt(1);
            RadioButton quadraticButton = (RadioButton)answers.getChildAt(2);
            RadioButton factorialButton = (RadioButton)answers.getChildAt(3);

            // Conditions for correct answer
            if(logarithmicButton.isChecked() && !linearButton.isChecked() &&
                    !quadraticButton.isChecked() && !factorialButton.isChecked()) {
                numberOfCorrectAnswers++;
            }
        } else if(id == R.id.data_structure_answer) {
            // Parent ViewGroup holding the possible answers
            answers = (ViewGroup)findViewById(R.id.data_structure_answer);

            // Answer
            EditText answerText = (EditText)answers.getChildAt(0);

            // Get the entered text and trim white space
            String answer = answerText.getText().toString().trim();

            if(answer.equalsIgnoreCase("stack")) {
                numberOfCorrectAnswers++;
            }
        } else if(id == R.id.abstract_class_answer) {
            // Parent ViewGroup holding the possible answers
            answers = (ViewGroup)findViewById(R.id.abstract_class_answer);

            // Possible answers, in case both were somehow able to be selected
            RadioButton trueButton = (RadioButton)answers.getChildAt(0);
            RadioButton falseButton = (RadioButton)answers.getChildAt(1);

            // Conditions for correct answer
            if(!trueButton.isChecked() && falseButton.isChecked()) {
                numberOfCorrectAnswers++;
            }
        } else {
            // In case an incorrect ViewGroup is passed in
            throw new IllegalArgumentException();
        }
    }

    /**
     * This method deselects all of the answers and resets the
     * number of correct answers to 0 so the quiz can be retaken.
     */
    private void resetDisplay() {
        // Reset the number of correct answers
        numberOfCorrectAnswers = 0;

        // For each answer layout
        for(ViewGroup viewGroup: answerLayouts) {
            // For each possible answer
            for(int i = 0; i < viewGroup.getChildCount(); i++) {
                View view = viewGroup.getChildAt(i);
                if(view instanceof RadioButton) {
                    // Cast to RadioButton
                    RadioButton radioButton = ((RadioButton) view);
                    // Clear the group
                    ((RadioGroup)radioButton.getParent()).clearCheck();
                    break; // Not necessary to clear group multiple times
                } else if(view instanceof CheckBox) {
                    ((CheckBox) view).setChecked(false);
                } else if(view instanceof EditText) {
                    ((EditText) view).setText("");
                }
            }
        }
    }
}
