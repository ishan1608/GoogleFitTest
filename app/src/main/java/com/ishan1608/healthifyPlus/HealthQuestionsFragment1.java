package com.ishan1608.healthifyPlus;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class HealthQuestionsFragment1 extends Fragment {
    View rootView;
    int optionValues[] = new int[7];
    private TextView questionContentView1, questionContentView2, questionContentView3, questionContentView4, questionContentView5;
    // private LinearLayout optionView;
    private RadioGroup hygieneOptionGroup1, hygieneOptionGroup2, hygieneOptionGroup3, hygieneOptionGroup4, hygieneOptionGroup5,hygieneOptionGroup6, hygieneOptionGroup7;
    private LinearLayout optionView, optionContentView2, optionContentView3, optionContentView4, optionContentView5;
    private View hygieneQuestionSetHeadingView, hygieneQuestionHeadingView1, hygieneQuestionHeadingView2, hygieneQuestionHeadingView3, hygieneQuestionHeadingView4, hygieneQuestionHeadingView5,hygieneQuestionHeadingView6,hygieneQuestionHeadingView7;
    private View hygieneOptionHeadingView, hygieneOptionHeadingView2, hygieneOptionHeadingView3, hygieneOptionHeadingView4, hygieneOptionHeadingView5,hygieneOptionHeadingView6,hygieneOptionHeadingView7;
    private Button hygieneRadioSubmitOption1, hygieneRadioSubmitOption2, hygieneRadioSubmitOption3, hygieneRadioSubmitOption4, hygieneRadioSubmitOption5, hygieneRadioSubmitOption6, hygieneRadioSubmitOption7;
    private Button registrationButton;
    private int categoryPosition, eventPosition;

    //new code ends
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_question1, container, false);
        //new code
        hygieneQuestionSetHeadingView = rootView.findViewById(R.id.hygiene_question_set2);
        hygieneQuestionHeadingView1 = rootView.findViewById(R.id.hygiene_question1_heading);
        hygieneOptionHeadingView = rootView.findViewById(R.id.hygiene_options1_heading);
        hygieneQuestionHeadingView2 = rootView.findViewById(R.id.hygiene_question2_heading);
        hygieneOptionHeadingView2 = rootView.findViewById(R.id.hygiene_option2_heading);
        hygieneQuestionHeadingView3 = rootView.findViewById(R.id.hygiene_question3_heading);
        hygieneOptionHeadingView3 = rootView.findViewById(R.id.hygiene_option3_heading);
        hygieneQuestionHeadingView4 = rootView.findViewById(R.id.hygiene_question4_heading);
        hygieneOptionHeadingView4 = rootView.findViewById(R.id.hygiene_option4_heading);
        hygieneQuestionHeadingView5 = rootView.findViewById(R.id.hygiene_question5_heading);
        hygieneOptionHeadingView5 = rootView.findViewById(R.id.hygiene_option5_heading);
        hygieneQuestionHeadingView6 = rootView.findViewById(R.id.hygiene_question6_heading);
        hygieneOptionHeadingView6 = rootView.findViewById(R.id.hygiene_option6_heading);
        hygieneQuestionHeadingView7 = rootView.findViewById(R.id.hygiene_question7_heading);
        hygieneOptionHeadingView7 = rootView.findViewById(R.id.hygiene_option7_heading);

        questionContentView1 = (TextView) rootView.findViewById(R.id.hygiene_question1_content);
        optionView = (LinearLayout) rootView.findViewById(R.id.hygiene_option1_content);
        questionContentView2 = (TextView) rootView.findViewById(R.id.hygiene_question2_content);
        optionContentView2 = (LinearLayout) rootView.findViewById(R.id.hygiene_option2_content);
        questionContentView3 = (TextView) rootView.findViewById(R.id.hygiene_question3_content);
        optionContentView3 = (LinearLayout) rootView.findViewById(R.id.hygiene_option3_content);
        questionContentView4 = (TextView) rootView.findViewById(R.id.hygiene_question4_content);
        optionContentView4 = (LinearLayout) rootView.findViewById(R.id.hygiene_option4_content);
        questionContentView4 = (TextView) rootView.findViewById(R.id.hygiene_question4_content);
        optionContentView4 = (LinearLayout) rootView.findViewById(R.id.hygiene_option4_content);


        // Setting the hide and display
        hygieneQuestionSetHeadingView.setOnClickListener(new contentToggler());
        hygieneQuestionHeadingView1.setOnClickListener(new contentToggler());
        hygieneOptionHeadingView.setOnClickListener(new contentToggler());
        hygieneQuestionHeadingView2.setOnClickListener(new contentToggler());
        hygieneOptionHeadingView2.setOnClickListener(new contentToggler());
        hygieneQuestionHeadingView3.setOnClickListener(new contentToggler());
        hygieneOptionHeadingView3.setOnClickListener(new contentToggler());
        hygieneQuestionHeadingView4.setOnClickListener(new contentToggler());
        hygieneOptionHeadingView4.setOnClickListener(new contentToggler());
        hygieneQuestionHeadingView5.setOnClickListener(new contentToggler());
        hygieneOptionHeadingView5.setOnClickListener(new contentToggler());
        hygieneQuestionHeadingView6.setOnClickListener(new contentToggler());
        hygieneOptionHeadingView6.setOnClickListener(new contentToggler());
        hygieneQuestionHeadingView7.setOnClickListener(new contentToggler());
        hygieneOptionHeadingView7.setOnClickListener(new contentToggler());
        addListenerHygieneRadioGroup1();
        addListenerHygieneRadioGroup2();
        addListenerHygieneRadioGroup3();
        addListenerHygieneRadioGroup4();
        addListenerHygieneRadioGroup5();
        addListenerHygieneRadioGroup6();
        addListenerHygieneRadioGroup7();

        return rootView;
    }

    private void addListenerHygieneRadioGroup1() {
        hygieneOptionGroup1 = (RadioGroup) rootView.findViewById
                (R.id.hygiene_radio_group1);
        hygieneRadioSubmitOption1 = (Button) rootView.findViewById(R.id.hygiene_radio_submit_group1);
        hygieneRadioSubmitOption1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroupTutorials
                int selected =
                        hygieneOptionGroup1.getCheckedRadioButtonId();
                boolean checked = ((RadioButton) v).isChecked();

                // Check which radio button was clicked
                switch (v.getId()) {
                    case R.id.hygiene_option1_radio1:
                        if (checked)
                            optionValues[0] = 1;
                        break;
                    case R.id.hygiene_option1_radio2:
                        if (checked)
                            optionValues[0] = 2;
                        break;

                    case R.id.hygiene_option1_radio3:
                        if (checked)
                            optionValues[0] = 3;
                        break;
                    case R.id.hygiene_option1_radio4:
                        if (checked)
                            optionValues[0] = 4;
                        break;
                }
            }
        });
    }

    private void addListenerHygieneRadioGroup2() {
        hygieneOptionGroup2 = (RadioGroup) rootView.findViewById
                (R.id.hygiene_radio_group2);
        hygieneRadioSubmitOption2 = (Button) rootView.findViewById(R.id.hygiene_radio_submit_group2);
        hygieneRadioSubmitOption2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroupTutorials
                int selected =
                        hygieneOptionGroup2.getCheckedRadioButtonId();
                boolean checked = ((RadioButton) v).isChecked();

                // Check which radio button was clicked
                switch (v.getId()) {
                    case R.id.hygiene_option2_radio1:
                        if (checked)
                            optionValues[1] = 1;
                        break;
                    case R.id.hygiene_option2_radio2:
                        if (checked)
                            optionValues[1] = 2;
                        break;
                    case R.id.hygiene_option2_radio3:
                        if (checked)
                            optionValues[1]=3;
                        break;
                    case R.id.hygiene_option2_radio4:
                        if (checked)
                            optionValues[1]=4;
                        break;
                }
            }
        });
    }

    private void addListenerHygieneRadioGroup3() {
        hygieneOptionGroup3 = (RadioGroup) rootView.findViewById
                (R.id.hygiene_radio_group3);
        hygieneRadioSubmitOption3 = (Button) rootView.findViewById(R.id.hygiene_radio_submit_group3);
        hygieneRadioSubmitOption3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroupTutorials
                int selected =
                        hygieneOptionGroup3.getCheckedRadioButtonId();
                boolean checked = ((RadioButton) v).isChecked();

                // Check which radio button was clicked
                switch (v.getId()) {
                    case R.id.hygiene_option3_radio1:
                        if (checked)
                            optionValues[2] = 1;
                        break;
                    case R.id.hygiene_option3_radio2:
                        if (checked)
                            optionValues[2] = 2;
                        break;
                    case R.id.hygiene_option3_radio3:
                        if (checked)
                            optionValues[2] = 3;
                        break;
                    case R.id.hygiene_option3_radio4:
                        if (checked)
                            optionValues[2] = 4;
                        break;


                }
            }
        });
    }

    private void addListenerHygieneRadioGroup4() {
        hygieneOptionGroup4 = (RadioGroup) rootView.findViewById
                (R.id.hygiene_radio_group4);
        hygieneRadioSubmitOption4 = (Button) rootView.findViewById(R.id.hygiene_radio_submit_group4);
        hygieneRadioSubmitOption4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroupTutorials
                int selected =
                        hygieneOptionGroup4.getCheckedRadioButtonId();
                boolean checked = ((RadioButton) v).isChecked();

                // Check which radio button was clicked
                switch (v.getId()) {
                    case R.id.hygiene_option4_radio1:
                        if (checked)
                            optionValues[3] = 1;
                        break;
                    case R.id.hygiene_option4_radio2:
                        if (checked)
                            optionValues[3] = 2;
                        break;
                    case R.id.hygiene_option4_radio3:
                        if (checked)
                            optionValues[3] = 3;
                        break;
                    case R.id.hygiene_option4_radio4:
                        if (checked)
                            optionValues[3] = 4;
                        break;
                }
            }
        });
    }

    private void addListenerHygieneRadioGroup5() {
        hygieneOptionGroup5 = (RadioGroup) rootView.findViewById
                (R.id.hygiene_radio_group5);
        hygieneRadioSubmitOption5 = (Button) rootView.findViewById(R.id.hygiene_radio_submit_group5);
        hygieneRadioSubmitOption5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroupTutorials
                int selected =
                        hygieneOptionGroup5.getCheckedRadioButtonId();
                boolean checked = ((RadioButton) v).isChecked();

                // Check which radio button was clicked
                switch (v.getId()) {
                    case R.id.hygiene_option5_radio1:
                        if (checked)
                            optionValues[4] = 1;
                        break;
                    case R.id.hygiene_option5_radio2:
                        if (checked)
                            optionValues[4] = 2;
                        break;
                    case R.id.hygiene_option5_radio3:
                        if (checked)
                            optionValues[4] = 3;
                        break;
                    case R.id.hygiene_option5_radio4:
                        if (checked)
                            optionValues[4]=4;
                        break;

                }
            }
        });
    }
    private void addListenerHygieneRadioGroup6() {
        hygieneOptionGroup6 = (RadioGroup) rootView.findViewById
                (R.id.hygiene_radio_group6);
        hygieneRadioSubmitOption6 = (Button) rootView.findViewById(R.id.hygiene_radio_submit_group6);
        hygieneRadioSubmitOption6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroupTutorials
                int selected =
                        hygieneOptionGroup6.getCheckedRadioButtonId();
                boolean checked = ((RadioButton) v).isChecked();

                // Check which radio button was clicked
                switch (v.getId()) {
                    case R.id.hygiene_option6_radio1:
                        if (checked)
                            optionValues[5] = 1;
                        break;
                    case R.id.hygiene_option6_radio2:
                        if (checked)
                            optionValues[5] = 2;
                        break;
                    case R.id.hygiene_option6_radio3:
                        if (checked)
                            optionValues[5]=3;
                        break;
                    case R.id.hygiene_option6_radio4:
                        if (checked)
                            optionValues[5]=4;
                        break;



                }
            }
        });
    }
    private void addListenerHygieneRadioGroup7() {
        hygieneOptionGroup7 = (RadioGroup) rootView.findViewById
                (R.id.hygiene_radio_group7);
        hygieneRadioSubmitOption7 = (Button) rootView.findViewById(R.id.hygiene_radio_submit_group7);
        hygieneRadioSubmitOption7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroupTutorials
                int selected =
                        hygieneOptionGroup7.getCheckedRadioButtonId();
                boolean checked = ((RadioButton) v).isChecked();

                // Check which radio button was clicked
                switch (v.getId()) {
                    case R.id.hygiene_option7_radio1:
                        if (checked)
                            optionValues[6] = 1;
                        break;
                    case R.id.hygiene_option7_radio2:
                        if (checked)
                            optionValues[6] = 2;
                        break;
                    case R.id.hygiene_option7_radio3:
                        if (checked)
                            optionValues[6] = 3;
                        break;
                    case R.id.hygiene_option7_radio4:
                        if (checked)
                            optionValues[6] = 4;
                        break;

                }
            }
        });
    }
}