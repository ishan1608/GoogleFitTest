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
 * Created by samsung on 06-04-2015.
 */
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
                String dialogText = null;
                Bundle bundle = new Bundle();

                int selected =
                        hygieneOptionGroup1.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.hygiene_option1_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.hygiene_option1_radio2)).isChecked();
                boolean checked3 = ((RadioButton) rootView.findViewById(R.id.hygiene_option1_radio3)).isChecked();
                boolean checked4 = ((RadioButton) rootView.findViewById(R.id.hygiene_option1_radio4)).isChecked();
                // Check which radio button was clicked
                if(checked1) {
                    optionValues[0] = 1;
                    dialogText ="Walking reduces body fat,is good for your brain,reduces stress and boosts immune function.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked2) {
                    optionValues[0] = 2;
                    dialogText ="Going to gym definately improves one's fitness and overall health.Doing same work day and night would rather bore you.So have a break and spend some time in stretching to recharge yourself.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked3) {
                    optionValues[0] = 3;
                    dialogText ="Playing sports is fun and are a good way to relieve stress and reduce depression.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked4) {
                    optionValues[0] = 4;
                    dialogText ="Allows you to be more active, socialise within local communities and develop creative skills.";
                    bundle.putString("key" ,dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);
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
                String dialogText = null;
                Bundle bundle = new Bundle();

                int selected =
                        hygieneOptionGroup2.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.hygiene_option2_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.hygiene_option2_radio2)).isChecked();
                boolean checked3 = ((RadioButton) rootView.findViewById(R.id.hygiene_option2_radio3)).isChecked();
                boolean checked4 = ((RadioButton) rootView.findViewById(R.id.hygiene_option2_radio4)).isChecked();

                // Check which radio button was clicked
                if(checked1) {
                    optionValues[1] = 1;
                    dialogText ="Weight loss, a strong and flexible body, glowing beautiful skin,peaceful mind, good health all these are benefits of yoga.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked2) {
                    optionValues[1] = 2;
                    dialogText ="You may enjoy the work you do or some parts of it, at least but taking time to relax is important to regain energy.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked3) {
                    optionValues[1] = 3;
                    dialogText ="Massage reduces pain,improves joint mobility,improves circulation,improves lymphatic drainage and reduces muscular tension.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked4) {
                    optionValues[1] = 4;
                    dialogText ="Relaxing through watching movies or reading books is a good choice but doing physical exercise is a better choice.";
                    bundle.putString("key" ,dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);
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
                String dialogText = null;
                Bundle bundle = new Bundle();

                int selected =
                        hygieneOptionGroup3.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.hygiene_option3_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.hygiene_option3_radio2)).isChecked();

                // Check which radio button was clicked
                if(checked1) {
                    optionValues[2] = 1;
                    dialogText ="Eating plenty of vegetables and fruits can prevent you from heart disease and stroke, control blood pressure, prevent some types of cancer,and guard against cataract.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked2) {
                    optionValues[2] = 2;
                    dialogText ="That's good!!! Eating fast food regularly may decrease your sensory-specific satiety, resulting in overeating can trigger digestive problem.";
                    bundle.putString("key" ,dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);
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
                String dialogText = null;
                Bundle bundle = new Bundle();

                int selected =
                        hygieneOptionGroup4.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.hygiene_option4_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.hygiene_option4_radio2)).isChecked();

                // Check which radio button was clicked
                if(checked1) {
                    optionValues[3] = 1;
                    dialogText ="That's really nice because breakfast foods are good sources of important nutrients such as calcium, iron and B vitamins as well as protein and fibre.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked2) {
                    optionValues[3] = 2;
                    dialogText ="The body needs essential nutrients such as calcium,iron,vitamins and if these are missed at breakfast, they are less likely to be compensated for later in the day.";
                    bundle.putString("key" ,dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);
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
                String dialogText = null;
                Bundle bundle = new Bundle();

                int selected =
                        hygieneOptionGroup5.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.hygiene_option5_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.hygiene_option5_radio2)).isChecked();
                boolean checked3 = ((RadioButton) rootView.findViewById(R.id.hygiene_option5_radio3)).isChecked();
                boolean checked4 = ((RadioButton) rootView.findViewById(R.id.hygiene_option5_radio4)).isChecked();

                // Check which radio button was clicked
                if(checked1) {
                    optionValues[4] = 1;
                    dialogText ="Music can help you communicate difficult feelings and memories that you find too difficult to talk about in words and creating art is a very effective way to stimulate the brain.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked2) {
                    optionValues[4] = 2;
                    dialogText ="Playing sports affects your mental and emotional health due to the physiological effects that exercise has on the body.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked3) {
                    optionValues[4] = 3;
                    dialogText ="Going out with friends help you relax,celebrate good times and enjoy life.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked4) {
                    optionValues[4] = 4;
                    dialogText ="You should spend sometime doing things that are beneficial for your health and mind.";
                    bundle.putString("key" ,dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);
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
                String dialogText = null;
                Bundle bundle = new Bundle();

                int selected =
                        hygieneOptionGroup6.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.hygiene_option6_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.hygiene_option6_radio2)).isChecked();
                boolean checked3 = ((RadioButton) rootView.findViewById(R.id.hygiene_option6_radio3)).isChecked();
                boolean checked4 = ((RadioButton) rootView.findViewById(R.id.hygiene_option6_radio4)).isChecked();

                // Check which radio button was clicked
                if(checked1) {
                    optionValues[5] = 1;
                    dialogText ="That's great!!! Because sleeping in your makeup can result in unnecessary exposure to the free radicals in the environment, which the makeup holds.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked2) {
                    optionValues[5] = 2;
                    dialogText ="Taking shower before going to bed relaxes the body,reduces allergy symptoms and reduces stress.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked3) {
                    optionValues[5] = 3;
                    dialogText ="You should relax yourself before going to bed!!";
                    bundle.putString("key" ,dialogText);
                }
                if(checked4) {
                    optionValues[5] = 4;
                    dialogText ="Night brushing is one of the best ways to have a cavity-free mouth.";
                    bundle.putString("key" ,dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);
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
                String dialogText = null;
                Bundle bundle = new Bundle();

                int selected =
                        hygieneOptionGroup7.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.hygiene_option7_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.hygiene_option7_radio2)).isChecked();
                boolean checked3 = ((RadioButton) rootView.findViewById(R.id.hygiene_option7_radio3)).isChecked();

                // Check which radio button was clicked
                if(checked1) {
                    optionValues[6] = 1;
                    dialogText ="Its a good practice to brush your teeth in the morning and at night.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked2) {
                    optionValues[6] = 2;
                    dialogText ="Brushing your teeth immediately after consuming acidic foods and drinks increases the chance of enamel erosion.";
                    bundle.putString("key" ,dialogText);
                }
                if(checked3) {
                    optionValues[6] = 3;
                    dialogText ="Not brushing teeth results in gum disease,tooth loss and bad breathe.";
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
