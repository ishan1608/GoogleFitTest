package com.ishan1608.healthifyPlus;
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

/**
 * Created by samsung on 10-04-2015.
 */
public class LifestyleQuestionsFragment extends Fragment {

    View rootView;
    int optionValues[] = new int[7];
    private TextView questionContentView1, questionContentView2, questionContentView3, questionContentView4, questionContentView5;
    // private LinearLayout optionView;
    private RadioGroup lifestyleOptionGroup1, lifestyleOptionGroup2, lifestyleOptionGroup3, lifestyleOptionGroup4,lifestyleOptionGroup5,lifestyleOptionGroup6, lifestyleOptionGroup7;
    private LinearLayout optionView, optionContentView2, optionContentView3, optionContentView4, optionContentView5;
    private View lifestyleQuestionSetHeadingView, lifestyleQuestionHeadingView1, lifestyleQuestionHeadingView2, lifestyleQuestionHeadingView3, lifestyleQuestionHeadingView4, lifestyleQuestionHeadingView5,lifestyleQuestionHeadingView6,lifestyleQuestionHeadingView7;
    private View lifestyleOptionHeadingView, lifestyleOptionHeadingView2, lifestyleOptionHeadingView3, lifestyleOptionHeadingView4, lifestyleOptionHeadingView5,lifestyleOptionHeadingView6,lifestyleOptionHeadingView7;
    private Button lifestyleRadioSubmitOption1, lifestyleRadioSubmitOption2, lifestyleRadioSubmitOption3, lifestyleRadioSubmitOption4, lifestyleRadioSubmitOption5, lifestyleRadioSubmitOption6, lifestyleRadioSubmitOption7;
    private Button registrationButton;
    private int categoryPosition, eventPosition;

    //new code ends
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.lifestyle_fragment_question, container, false);
        //new code
        lifestyleQuestionSetHeadingView = rootView.findViewById(R.id.lifestyle_question_set3);
        lifestyleQuestionHeadingView1 = rootView.findViewById(R.id.lifestyle_question1_heading);
        lifestyleOptionHeadingView = rootView.findViewById(R.id.lifestyle_options1_heading);
        lifestyleQuestionHeadingView2 = rootView.findViewById(R.id.lifestyle_question2_heading);
        lifestyleOptionHeadingView2 = rootView.findViewById(R.id.lifestyle_option2_heading);
        lifestyleQuestionHeadingView3 = rootView.findViewById(R.id.lifestyle_question3_heading);
        lifestyleOptionHeadingView3 = rootView.findViewById(R.id.lifestyle_option3_heading);
        lifestyleQuestionHeadingView4 = rootView.findViewById(R.id.lifestyle_question4_heading);
        lifestyleOptionHeadingView4 = rootView.findViewById(R.id.lifestyle_option4_heading);
        lifestyleQuestionHeadingView5 = rootView.findViewById(R.id.lifestyle_question5_heading);
        lifestyleOptionHeadingView5 = rootView.findViewById(R.id.lifestyle_option5_heading);
        lifestyleQuestionHeadingView6 = rootView.findViewById(R.id.lifestyle_question6_heading);
        lifestyleOptionHeadingView6 = rootView.findViewById(R.id.lifestyle_option6_heading);
        lifestyleQuestionHeadingView7 = rootView.findViewById(R.id.lifestyle_question7_heading);
        lifestyleOptionHeadingView7 = rootView.findViewById(R.id.lifestyle_option7_heading);

        questionContentView1 = (TextView) rootView.findViewById(R.id.lifestyle_question1_content);
        optionView = (LinearLayout) rootView.findViewById(R.id.lifestyle_option1_content);
        questionContentView2 = (TextView) rootView.findViewById(R.id.lifestyle_question2_content);
        optionContentView2 = (LinearLayout) rootView.findViewById(R.id.lifestyle_option2_content);
        questionContentView3 = (TextView) rootView.findViewById(R.id.lifestyle_question3_content);
        optionContentView3 = (LinearLayout) rootView.findViewById(R.id.lifestyle_option3_content);
        questionContentView4 = (TextView) rootView.findViewById(R.id.lifestyle_question4_content);
        optionContentView4 = (LinearLayout) rootView.findViewById(R.id.lifestyle_option4_content);
        questionContentView4 = (TextView) rootView.findViewById(R.id.lifestyle_question4_content);
        optionContentView4 = (LinearLayout) rootView.findViewById(R.id.lifestyle_option4_content);


        // Setting the hide and display
        lifestyleQuestionSetHeadingView.setOnClickListener(new contentToggler());
        lifestyleQuestionHeadingView1.setOnClickListener(new contentToggler());
        lifestyleOptionHeadingView.setOnClickListener(new contentToggler());
        lifestyleQuestionHeadingView2.setOnClickListener(new contentToggler());
        lifestyleOptionHeadingView2.setOnClickListener(new contentToggler());
        lifestyleQuestionHeadingView3.setOnClickListener(new contentToggler());
        lifestyleOptionHeadingView3.setOnClickListener(new contentToggler());
        lifestyleQuestionHeadingView4.setOnClickListener(new contentToggler());
        lifestyleOptionHeadingView4.setOnClickListener(new contentToggler());
        lifestyleQuestionHeadingView5.setOnClickListener(new contentToggler());
        lifestyleOptionHeadingView5.setOnClickListener(new contentToggler());
        lifestyleQuestionHeadingView6.setOnClickListener(new contentToggler());
        lifestyleOptionHeadingView6.setOnClickListener(new contentToggler());
        lifestyleQuestionHeadingView7.setOnClickListener(new contentToggler());
        lifestyleOptionHeadingView7.setOnClickListener(new contentToggler());
        addListenerLifestyleRadioGroup1();
        addListenerLifestyleRadioGroup2();
        addListenerLifestyleRadioGroup3();
        addListenerLifestyleRadioGroup4();
        addListenerLifestyleRadioGroup5();
        addListenerLifestyleRadioGroup6();
        addListenerLifestyleRadioGroup7();

        return rootView;
    }

    private void addListenerLifestyleRadioGroup1() {
        lifestyleOptionGroup1 = (RadioGroup) rootView.findViewById
                (R.id.lifestyle_radio_group1);
        lifestyleRadioSubmitOption1 = (Button) rootView.findViewById(R.id.lifestyle_radio_submit_group1);
        lifestyleRadioSubmitOption1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroupTutorials
                String dialogText = null;
                Bundle bundle = new Bundle();
                int selected =
                        lifestyleOptionGroup1.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.lifestyle_option1_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.lifestyle_option1_radio2)).isChecked();
                boolean checked3 = ((RadioButton) rootView.findViewById(R.id.lifestyle_option1_radio3)).isChecked();

                // Check which radio button was clicked
                if(checked1) {
                    optionValues[0] = 1;
                    dialogText ="Research shows that being connected and going out with friends really can help you live longer, lower your Alzheimer’s risk and experience less anxiety and depression.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked2) {
                    optionValues[0] = 2;
                    dialogText ="Being social is good but make sure that you plan your schedule properly and manage everthing.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked3) {
                    optionValues[0] = 3;
                    dialogText ="That's good.You take time for yourself and enjoy as it is one of your best tools for handling stress.";
                    bundle.putString("key" ,dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);
            }
        });
    }

    private void addListenerLifestyleRadioGroup2() {
        lifestyleOptionGroup2 = (RadioGroup) rootView.findViewById
                (R.id.lifestyle_radio_group2);
        lifestyleRadioSubmitOption2 = (Button) rootView.findViewById(R.id.lifestyle_radio_submit_group2);
        lifestyleRadioSubmitOption2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroupTutorials
                String dialogText = null;
                Bundle bundle = new Bundle();
                int selected =
                        lifestyleOptionGroup2.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.lifestyle_option2_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.lifestyle_option2_radio2)).isChecked();

                // Check which radio button was clicked
                if(checked1) {
                    optionValues[1] = 1;
                    dialogText ="People who are in good spirits tend to eat better, exercise more frequently and get better sleep than those who are not.Being happy can strengthen your immune system and boost positive emotions.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked2) {
                    optionValues[1] = 2;
                    dialogText ="Health and happiness are related and can affect each other. Good feelings can come from simple pleasures. Anything you enjoy doing, when done, make you happy.";
                    bundle.putString("key" ,dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);
            }
        });
    }

    private void addListenerLifestyleRadioGroup3() {
        lifestyleOptionGroup3 = (RadioGroup) rootView.findViewById
                (R.id.lifestyle_radio_group3);
        lifestyleRadioSubmitOption3 = (Button) rootView.findViewById(R.id.lifestyle_radio_submit_group3);
        lifestyleRadioSubmitOption3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroupTutorials
                String dialogText = null;
                Bundle bundle = new Bundle();
                int selected =
                        lifestyleOptionGroup3.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.lifestyle_option3_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.lifestyle_option3_radio2)).isChecked();

                // Check which radio button was clicked
                if(checked1) {
                    optionValues[2] = 1;
                    dialogText ="Exercise control weight,combats health conditions and diseases and improves mood and results in a healthier level of physical fitness and both physical and mental health.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked2) {
                    optionValues[2] = 2;
                    dialogText ="Lack of exercise can lead to a plethora of physical and mental health problems and is the leading cause of obesity among adolescents.";
                    bundle.putString("key" ,dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);
            }
        });
    }

    private void addListenerLifestyleRadioGroup4() {
        lifestyleOptionGroup4 = (RadioGroup) rootView.findViewById
                (R.id.lifestyle_radio_group4);
        lifestyleRadioSubmitOption4 = (Button) rootView.findViewById(R.id.lifestyle_radio_submit_group4);
        lifestyleRadioSubmitOption4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroupTutorials
                String dialogText = null;
                Bundle bundle = new Bundle();
                int selected =
                        lifestyleOptionGroup4.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.lifestyle_option4_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.lifestyle_option4_radio2)).isChecked();

                // Check which radio button was clicked
                if(checked1) {
                    optionValues[3] = 1;
                    dialogText ="A little bit of stress is thought to be good for memory and motivation.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked2) {
                    optionValues[3] = 2;
                    dialogText ="Too much stress can make the person unhealthy.Stress becomes negative when a person faces continuous challenges without relief or relaxation between challenges.";
                    bundle.putString("key" ,dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);
            }
        });
    }

    private void addListenerLifestyleRadioGroup5() {
        lifestyleOptionGroup5 = (RadioGroup) rootView.findViewById
                (R.id.lifestyle_radio_group5);
        lifestyleRadioSubmitOption5 = (Button) rootView.findViewById(R.id.lifestyle_radio_submit_group5);
        lifestyleRadioSubmitOption5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroupTutorials
                String dialogText = null;
                Bundle bundle = new Bundle();
                int selected =
                        lifestyleOptionGroup5.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.lifestyle_option5_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.lifestyle_option5_radio2)).isChecked();


                // Check which radio button was clicked
                if(checked1) {
                    optionValues[4] = 1;
                    dialogText ="Watching too much television can significantly increase the risk of developing obesity and type 2 diabetes and decreases viewer sense of self-efficacy in maintaining personal health.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked2) {
                    optionValues[4] = 2;
                    dialogText ="Looking at still images of nature can lower blood pressure and muscle tension.So it is healthy to watch T.V sometimes.";
                    bundle.putString("key" ,dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);
            }
        });
    }
    private void addListenerLifestyleRadioGroup6() {
        lifestyleOptionGroup6 = (RadioGroup) rootView.findViewById
                (R.id.lifestyle_radio_group6);
        lifestyleRadioSubmitOption6 = (Button) rootView.findViewById(R.id.lifestyle_radio_submit_group6);
        lifestyleRadioSubmitOption6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroupTutorials
                String dialogText = null;
                Bundle bundle = new Bundle();
                int selected =
                        lifestyleOptionGroup6.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.lifestyle_option6_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.lifestyle_option6_radio2)).isChecked();

                // Check which radio button was clicked
                if(checked1) {
                    optionValues[5] = 1;
                    dialogText ="Eating fast food regularly may decrease your sensory-specific satiety, resulting in overeating can trigger digestive problem.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked2) {
                    optionValues[5] = 2;
                    dialogText ="These food items contain many vitamins and minerals that are essential parts of a healthy and balanced diet.";
                    bundle.putString("key" ,dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);
            }
        });
    }
    private void addListenerLifestyleRadioGroup7() {
        lifestyleOptionGroup7 = (RadioGroup) rootView.findViewById
                (R.id.lifestyle_radio_group7);
        lifestyleRadioSubmitOption7 = (Button) rootView.findViewById(R.id.lifestyle_radio_submit_group7);
        lifestyleRadioSubmitOption7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroupTutorials
                String dialogText = null;
                Bundle bundle = new Bundle();
                int selected =
                        lifestyleOptionGroup7.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.lifestyle_option7_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.lifestyle_option7_radio2)).isChecked();


                // Check which radio button was clicked
                if(checked1) {
                    optionValues[6] = 1;
                    dialogText ="That's really good because fresh air is essential to make your immune system strong.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked2) {
                    optionValues[6] = 2;
                    dialogText ="Fresh air cleans the lungs and helps to bring more oxygen to the cells and improves the cleansing action of your lungs and there are many benefits of inhaling fresh air.";
                    bundle.putString("key" ,dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);
            }
        });
    }
}

//class contentToggler2 implements View.OnClickListener {
//    @Override
//    public void onClick(View v) {
//        ViewGroup parent = (ViewGroup) v.getParent();
//        View childView = parent.getChildAt(parent.indexOfChild(v) + 1);
//        this.viewToggler(childView);
//    }
//
//    private void viewToggler(View view) {
//        if(view.getVisibility() == View.VISIBLE) {
//            view.setVisibility(View.GONE);
//        } else {
//            view.setVisibility(View.VISIBLE);
//        }
//    }
//}



