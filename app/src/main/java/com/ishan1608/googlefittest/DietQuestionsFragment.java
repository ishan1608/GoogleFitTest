package com.ishan1608.googlefittest;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class DietQuestionsFragment extends Fragment {
    View rootView;
    int optionValues[] = new int[7];
    private TextView questionContentView1, questionContentView2, questionContentView3, questionContentView4, questionContentView5;
    // private LinearLayout optionView;
    private RadioGroup dietOptionGroup1, dietOptionGroup2, dietOptionGroup3, dietOptionGroup4,dietOptionGroup5,dietOptionGroup6, dietOptionGroup7;
    private LinearLayout optionView, optionContentView2, optionContentView3, optionContentView4, optionContentView5;
    private View dietQuestionSetHeadingView, dietQuestionHeadingView1, dietQuestionHeadingView2, dietQuestionHeadingView3, dietQuestionHeadingView4, dietQuestionHeadingView5,dietQuestionHeadingView6,dietQuestionHeadingView7;
    private View dietOptionHeadingView, dietOptionHeadingView2, dietOptionHeadingView3, dietOptionHeadingView4, dietOptionHeadingView5,dietOptionHeadingView6,dietOptionHeadingView7;
    private Button dietRadioSubmitOption1, dietRadioSubmitOption2, dietRadioSubmitOption3, dietRadioSubmitOption4, dietRadioSubmitOption5, dietRadioSubmitOption6, dietRadioSubmitOption7;
    private Button registrationButton;
    private int categoryPosition, eventPosition;

    //new code ends
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_diet_questions, container, false);
        //new code
        dietQuestionSetHeadingView = rootView.findViewById(R.id.diet_question_set5);
        dietQuestionHeadingView1 = rootView.findViewById(R.id.diet_question1_heading);
        dietOptionHeadingView = rootView.findViewById(R.id.diet_options1_heading);
        dietQuestionHeadingView2 = rootView.findViewById(R.id.diet_question2_heading);
        dietOptionHeadingView2 = rootView.findViewById(R.id.diet_option2_heading);
        dietQuestionHeadingView3 = rootView.findViewById(R.id.diet_question3_heading);
        dietOptionHeadingView3 = rootView.findViewById(R.id.diet_option3_heading);
        dietQuestionHeadingView4 = rootView.findViewById(R.id.diet_question4_heading);
        dietOptionHeadingView4 = rootView.findViewById(R.id.diet_option4_heading);
        dietQuestionHeadingView5 = rootView.findViewById(R.id.diet_question5_heading);
        dietOptionHeadingView5 = rootView.findViewById(R.id.diet_option5_heading);
        dietQuestionHeadingView6 = rootView.findViewById(R.id.diet_question6_heading);
        dietOptionHeadingView6 = rootView.findViewById(R.id.diet_option6_heading);
        dietQuestionHeadingView7 = rootView.findViewById(R.id.diet_question7_heading);
        dietOptionHeadingView7 = rootView.findViewById(R.id.diet_option7_heading);

        questionContentView1 = (TextView) rootView.findViewById(R.id.diet_question1_content);
        optionView = (LinearLayout) rootView.findViewById(R.id.lifestyle_option1_content);
        questionContentView2 = (TextView) rootView.findViewById(R.id.diet_question2_content);
        optionContentView2 = (LinearLayout) rootView.findViewById(R.id.diet_option2_content);
        questionContentView3 = (TextView) rootView.findViewById(R.id.diet_question3_content);
        optionContentView3 = (LinearLayout) rootView.findViewById(R.id.diet_option3_content);
        questionContentView4 = (TextView) rootView.findViewById(R.id.diet_question4_content);
        optionContentView4 = (LinearLayout) rootView.findViewById(R.id.diet_option4_content);
        questionContentView4 = (TextView) rootView.findViewById(R.id.diet_question4_content);
        optionContentView4 = (LinearLayout) rootView.findViewById(R.id.diet_option4_content);


        // Setting the hide and display
        dietQuestionSetHeadingView.setOnClickListener(new contentToggler());
        dietQuestionHeadingView1.setOnClickListener(new contentToggler());
        dietOptionHeadingView.setOnClickListener(new contentToggler());
        dietQuestionHeadingView2.setOnClickListener(new contentToggler());
        dietOptionHeadingView2.setOnClickListener(new contentToggler());
        dietQuestionHeadingView3.setOnClickListener(new contentToggler());
        dietOptionHeadingView3.setOnClickListener(new contentToggler());
        dietQuestionHeadingView4.setOnClickListener(new contentToggler());
        dietOptionHeadingView4.setOnClickListener(new contentToggler());
        dietQuestionHeadingView5.setOnClickListener(new contentToggler());
        dietOptionHeadingView5.setOnClickListener(new contentToggler());
        dietQuestionHeadingView6.setOnClickListener(new contentToggler());
        dietOptionHeadingView6.setOnClickListener(new contentToggler());
        dietQuestionHeadingView7.setOnClickListener(new contentToggler());
        dietOptionHeadingView7.setOnClickListener(new contentToggler());
        addListenerDietRadioGroup1();
        addListenerDietRadioGroup2();
        addListenerDietRadioGroup3();
        addListenerDietRadioGroup4();
        addListenerDietRadioGroup5();
        addListenerDietRadioGroup6();
        addListenerDietRadioGroup7();

        return rootView;
    }

    private void addListenerDietRadioGroup1() {
        dietOptionGroup1 = (RadioGroup) rootView.findViewById
                (R.id.diet_radio_group1);
        dietRadioSubmitOption1 = (Button) rootView.findViewById(R.id.diet_radio_submit_group1);
        dietRadioSubmitOption1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String dialogText = null;
                Bundle bundle = new Bundle();
                // get selected radio button from radioGroupTutorials

                int selected =
                        dietOptionGroup1.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.diet_option1_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.diet_option1_radio2)).isChecked();

                // Check which radio button was clicked
                if (checked1) {
                    optionValues[0] = 1;
                    dialogText = "Good.If you reduce the amount of sugar you eat, you may have more energy, lose weight or stay at a healthy weight more easily";
                    bundle.putString("key", dialogText);
                }
                if (checked2) {
                    optionValues[0] = 2;
                    dialogText = "High sugar intake can be a cause depression, headaches, weight gain, fluid retention, hormonal imbalances and hypertension";
                    bundle.putString("key", dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);



            }
        });
    }

    private void addListenerDietRadioGroup2() {
        dietOptionGroup2 = (RadioGroup) rootView.findViewById
                (R.id.diet_radio_group2);
        dietRadioSubmitOption2 = (Button) rootView.findViewById(R.id. diet_radio_submit_group2);
        dietRadioSubmitOption2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String dialogText = null;
                Bundle bundle = new Bundle();
                // get selected radio button from radioGroupTutorials
                int selected =
                        dietOptionGroup2.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.diet_option2_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.diet_option2_radio2)).isChecked();


                // Check which radio button was clicked
                if (checked1) {
                    optionValues[1] = 1;
                    dialogText = "Fibre can help in lowering cholesterol and blood glucose levels,Controlling your weight and can make you healthy";
                    bundle.putString("key", dialogText);
                }
                if (checked2) {
                    optionValues[1] = 2;
                    dialogText = "Lack of fibre in your diet can lead to many diseases like high blood pressure,diabetes,cardiovascular disease and obesity";
                    bundle.putString("key", dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);

            }
        });
    }

    private void addListenerDietRadioGroup3() {
        dietOptionGroup3 = (RadioGroup) rootView.findViewById
                (R.id.diet_radio_group3);
        dietRadioSubmitOption3 = (Button) rootView.findViewById(R.id.diet_radio_submit_group3);
        dietRadioSubmitOption3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String dialogText = null;
                Bundle bundle = new Bundle();
                // get selected radio button from radioGroupTutorials
                int selected =
                        dietOptionGroup3.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.diet_option3_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.diet_option3_radio2)).isChecked();


                // Check which radio button was clicked
                if (checked1) {
                    optionValues[2] = 1;
                    dialogText = "Good.Limited intake of saturated fat make it easier to lose weight,reduce the risk of  heart disease and keeps you fit";
                    bundle.putString("key", dialogText);
                }
                if (checked2) {
                    optionValues[2] = 2;
                    dialogText = "Eating foods that contain saturated fats raises the level of cholestrol in your blood and can be high in calories too";
                    bundle.putString("key", dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);

            }
        });
    }

    private void addListenerDietRadioGroup4() {
        dietOptionGroup4 = (RadioGroup) rootView.findViewById
                (R.id.diet_radio_group4);
        dietRadioSubmitOption4 = (Button) rootView.findViewById(R.id.diet_radio_submit_group4);
        dietRadioSubmitOption4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String dialogText = null;
                Bundle bundle = new Bundle();
                // get selected radio button from radioGroupTutorials
                int selected =
                        dietOptionGroup4.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.diet_option4_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.diet_option4_radio2)).isChecked();


                // Check which radio button was clicked
                if (checked1) {
                    optionValues[3] = 1;
                    dialogText = "People who eat fruit and vegetables as part of their daily diet have a reduced risk of many chronic diseases and the nutrients in vegetables are vital for health and maintenance of your body";
                    bundle.putString("key", dialogText);
                }
                if (checked2) {
                    optionValues[3] = 2;
                    dialogText = "Having a low intake of fruit and vegetables is estimated to cause about 19% of cancers of the digestive system, 31% of heart disease and 11% of stroke";
                    bundle.putString("key", dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);

            }
        });
    }

    private void addListenerDietRadioGroup5() {
        dietOptionGroup5 = (RadioGroup) rootView.findViewById
                (R.id.diet_radio_group5);
        dietRadioSubmitOption5 = (Button) rootView.findViewById(R.id.diet_radio_submit_group5);
        dietRadioSubmitOption5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String dialogText = null;
                Bundle bundle = new Bundle();
                // get selected radio button from radioGroupTutorials
                int selected =
                        dietOptionGroup5.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.diet_option5_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.diet_option5_radio2)).isChecked();


                // Check which radio button was clicked
                if (checked1) {
                    optionValues[4] = 1;
                    dialogText = "Drinking water helps maintain the balance of body fluids,treats headaches,helps in digestion and aids weight loss";
                    bundle.putString("key", dialogText);
                }
                if (checked2) {
                    optionValues[4] = 2;
                    dialogText = "Lack of water can lead to dehydration, a condition that occurs when you don't have enough water in your body to carry out normal functions";
                    bundle.putString("key", dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);

            }
        });
    }
    private void addListenerDietRadioGroup6() {
        dietOptionGroup6 = (RadioGroup) rootView.findViewById
                (R.id.diet_radio_group6);
        dietRadioSubmitOption6 = (Button) rootView.findViewById(R.id.diet_radio_submit_group6);
        dietRadioSubmitOption6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String dialogText = null;
                Bundle bundle = new Bundle();
                // get selected radio button from radioGroupTutorials
                int selected =
                        dietOptionGroup6.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.diet_option6_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.diet_option6_radio2)).isChecked();


                // Check which radio button was clicked
                if (checked1) {
                    optionValues[5] = 1;
                    dialogText = "Moderate amount of caffeine is not harmful";
                    bundle.putString("key", dialogText);
                }
                if (checked2) {
                    optionValues[5] = 2;
                    dialogText = "Excess caffeine consumption may raise blood pressure,may cause insomnia, indigestion and make you dependent on it";
                    bundle.putString("key", dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);


            }
        });
    }
    private void addListenerDietRadioGroup7() {
        dietOptionGroup7 = (RadioGroup) rootView.findViewById
                (R.id.diet_radio_group7);
        dietRadioSubmitOption7 = (Button) rootView.findViewById(R.id.diet_radio_submit_group7);
        dietRadioSubmitOption7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String dialogText = null;
                Bundle bundle = new Bundle();
                // get selected radio button from radioGroupTutorials
                int selected =
                        dietOptionGroup7.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.diet_option7_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.diet_option7_radio2)).isChecked();


                // Check which radio button was clicked
                if (checked1) {
                    optionValues[6] = 1;
                    dialogText = "";
                    bundle.putString("key", dialogText);
                }
                if (checked2) {
                    optionValues[6] = 2;
                    dialogText = "";
                    bundle.putString("key", dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);


            }
        });
    }
}
