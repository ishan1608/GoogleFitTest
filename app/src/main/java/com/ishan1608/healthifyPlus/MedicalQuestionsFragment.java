package com.ishan1608.healthifyPlus;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


/**
 * Created by samsung on 14-04-2015.
 */
public class MedicalQuestionsFragment extends Fragment {

    View rootView;
    int optionValues[] = new int[7];
    private TextView questionContentView1, questionContentView2, questionContentView3, questionContentView4, questionContentView5;
    // private LinearLayout optionView;
    private RadioGroup medicalOptionGroup1, medicalOptionGroup2, medicalOptionGroup3, medicalOptionGroup4,medicalOptionGroup5,medicalOptionGroup6, medicalOptionGroup7;
    private LinearLayout optionView, optionContentView2, optionContentView3, optionContentView4, optionContentView5;
    private View medicalQuestionSetHeadingView, medicalQuestionHeadingView1, medicalQuestionHeadingView2, medicalQuestionHeadingView3, medicalQuestionHeadingView4, medicalQuestionHeadingView5,medicalQuestionHeadingView6,medicalQuestionHeadingView7;
    private View medicalOptionHeadingView, medicalOptionHeadingView2, medicalOptionHeadingView3, medicalOptionHeadingView4, medicalOptionHeadingView5,medicalOptionHeadingView6,medicalOptionHeadingView7;
    private Button medicalRadioSubmitOption1, medicalRadioSubmitOption2, medicalRadioSubmitOption3, medicalRadioSubmitOption4, medicalRadioSubmitOption5, medicalRadioSubmitOption6, medicalRadioSubmitOption7;
    private Button registrationButton;
    private int categoryPosition, eventPosition;

    //new code ends
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_medical_question, container, false);
        //new code
        medicalQuestionSetHeadingView = rootView.findViewById(R.id.medical_question_set4);
        medicalQuestionHeadingView1 = rootView.findViewById(R.id.medical_question1_heading);
        medicalOptionHeadingView = rootView.findViewById(R.id.medical_options1_heading);
        medicalQuestionHeadingView2 = rootView.findViewById(R.id.medical_question2_heading);
        medicalOptionHeadingView2 = rootView.findViewById(R.id.medical_option2_heading);
        medicalQuestionHeadingView3 = rootView.findViewById(R.id.medical_question3_heading);
        medicalOptionHeadingView3 = rootView.findViewById(R.id.medical_option3_heading);
        medicalQuestionHeadingView4 = rootView.findViewById(R.id.medical_question4_heading);
        medicalOptionHeadingView4 = rootView.findViewById(R.id.medical_option4_heading);
        medicalQuestionHeadingView5 = rootView.findViewById(R.id.medical_question5_heading);
        medicalOptionHeadingView5 = rootView.findViewById(R.id.medical_option5_heading);
        medicalQuestionHeadingView6 = rootView.findViewById(R.id.medical_question6_heading);
        medicalOptionHeadingView6 = rootView.findViewById(R.id.medical_option6_heading);
        medicalQuestionHeadingView7 = rootView.findViewById(R.id.medical_question7_heading);
        medicalOptionHeadingView7 = rootView.findViewById(R.id.medical_option7_heading);

        questionContentView1 = (TextView) rootView.findViewById(R.id.medical_question1_content);
        optionView = (LinearLayout) rootView.findViewById(R.id.medical_option1_content);
        questionContentView2 = (TextView) rootView.findViewById(R.id.medical_question2_content);
        optionContentView2 = (LinearLayout) rootView.findViewById(R.id.medical_option2_content);
        questionContentView3 = (TextView) rootView.findViewById(R.id.medical_question3_content);
        optionContentView3 = (LinearLayout) rootView.findViewById(R.id.medical_option3_content);
        questionContentView4 = (TextView) rootView.findViewById(R.id.medical_question4_content);
        optionContentView4 = (LinearLayout) rootView.findViewById(R.id.medical_option4_content);
        questionContentView4 = (TextView) rootView.findViewById(R.id.medical_question4_content);
        optionContentView4 = (LinearLayout) rootView.findViewById(R.id.medical_option4_content);


        // Setting the hide and display
        medicalQuestionSetHeadingView.setOnClickListener(new contentToggler());
        medicalQuestionHeadingView1.setOnClickListener(new contentToggler());
        medicalOptionHeadingView.setOnClickListener(new contentToggler());
        medicalQuestionHeadingView2.setOnClickListener(new contentToggler());
        medicalOptionHeadingView2.setOnClickListener(new contentToggler());
        medicalQuestionHeadingView3.setOnClickListener(new contentToggler());
        medicalOptionHeadingView3.setOnClickListener(new contentToggler());
        medicalQuestionHeadingView4.setOnClickListener(new contentToggler());
        medicalOptionHeadingView4.setOnClickListener(new contentToggler());
        medicalQuestionHeadingView5.setOnClickListener(new contentToggler());
        medicalOptionHeadingView5.setOnClickListener(new contentToggler());
        medicalQuestionHeadingView6.setOnClickListener(new contentToggler());
        medicalOptionHeadingView6.setOnClickListener(new contentToggler());
        medicalQuestionHeadingView7.setOnClickListener(new contentToggler());
        medicalOptionHeadingView7.setOnClickListener(new contentToggler());
        addListenerMedicalRadioGroup1();
        addListenerMedicalRadioGroup2();
        addListenerMedicalRadioGroup3();
        addListenerMedicalRadioGroup4();
        addListenerMedicalRadioGroup5();
        addListenerMedicalRadioGroup6();
        addListenerMedicalRadioGroup7();

        // Moving on to next tab when clicked upon the next tab button
        Button nextTabButton = (Button) rootView.findViewById(R.id.next_tab_button);
        nextTabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPager reportViewPager = (ViewPager) getActivity().findViewById(R.id.report_view_pager);
                reportViewPager.setCurrentItem((reportViewPager.getCurrentItem() + 1) % reportViewPager.getAdapter().getCount(), true);
            }
        });

        return rootView;
    }

    private void addListenerMedicalRadioGroup1() {
        medicalOptionGroup1 = (RadioGroup) rootView.findViewById(R.id.medical_radio_group1);
        medicalRadioSubmitOption1 = (Button) rootView.findViewById(R.id.medical_radio_submit_group1);
        medicalRadioSubmitOption1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroupTutorials
                String dialogText = null;
                Bundle bundle = new Bundle();

                int selected =
                        medicalOptionGroup1.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.medical_option1_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.medical_option1_radio2)).isChecked();

                // Check which radio button was clicked
                if (checked1) {
                    optionValues[0] = 1;
                    dialogText ="Avoid eating foods that have too much saturated fat and trans fat.Speak with your doctor to see if you should be taking a cholesterol medicine along with making these lifestyle changes.";
                    bundle.putString("key",dialogText);
                }
                if(checked2) {
                    optionValues[0] = 2;
                    dialogText ="Keep your diet balanced and prefer regular checkups.";
                    bundle.putString("key",dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);

            }
        });
    }

    private void addListenerMedicalRadioGroup2() {
        medicalOptionGroup2 = (RadioGroup) rootView.findViewById
                (R.id.medical_radio_group2);
        medicalRadioSubmitOption2 = (Button) rootView.findViewById(R.id. medical_radio_submit_group2);
        medicalRadioSubmitOption2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String dialogText = null;
                Bundle bundle = new Bundle();

                // get selected radio button from radioGroupTutorials
                int selected =
                        medicalOptionGroup2.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.medical_option2_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.medical_option2_radio2)).isChecked();

                // Check which radio button was clicked
                if (checked1) {
                    optionValues[1] = 1;
                    dialogText ="To take care of your kidney stay away from pre-packaged food,eat fresh fruit and vegetables in addition to low-sodium foods.";
                    bundle.putString("key",dialogText);
                }
                if (checked2) {
                    optionValues[1] = 2;
                    dialogText ="Kidneys are the organs that help filter waste products from the blood and thus  play vital role in our life.So take care of your kidney  and stay healthy.";
                    bundle.putString("key",dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);

            }
        });
    }

    private void addListenerMedicalRadioGroup3() {
        medicalOptionGroup3 = (RadioGroup) rootView.findViewById
                (R.id.medical_radio_group3);
        medicalRadioSubmitOption3 = (Button) rootView.findViewById(R.id.medical_radio_submit_group3);
        medicalRadioSubmitOption3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String dialogText = null;
                Bundle bundle = new Bundle();

                // get selected radio button from radioGroupTutorials
                int selected =
                        medicalOptionGroup3.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.medical_option3_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.medical_option3_radio2)).isChecked();
                // Check which radio button was clicked
                if (checked1) {
                    optionValues[2] = 1;
                    dialogText ="Survivors who have had one stroke are at high risk of having another one if the treatment recommendations are not followed. Make sure you eat a healthy diet,takes medications as prescribed.";
                    bundle.putString("key",dialogText);
                }
                if (checked2) {
                    optionValues[2] = 2;
                    dialogText ="To avoid chances of stroke exercise regularly,control your weight,take a healthy diet and keep your blood pressure under control.";
                    bundle.putString("key",dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);

            }
        });
    }

    private void addListenerMedicalRadioGroup4() {
        medicalOptionGroup4 = (RadioGroup) rootView.findViewById
                (R.id.medical_radio_group4);
        medicalRadioSubmitOption4 = (Button) rootView.findViewById(R.id.medical_radio_submit_group4);
        medicalRadioSubmitOption4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String dialogText = null;
                Bundle bundle = new Bundle();

                // get selected radio button from radioGroupTutorials
                int selected =
                        medicalOptionGroup4.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.medical_option4_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.medical_option4_radio2)).isChecked();

                // Check which radio button was clicked
                if (checked1) {
                    optionValues[3] = 1;
                    dialogText ="Prefer healthy eating,keep check on your blood glucose,take medication and be active because being active can help control blood glucose levels.";
                    bundle.putString("key",dialogText);
                }
                if (checked2) {
                    optionValues[3] = 2;
                    dialogText = "Keep your blood pressure and cholestrol under control,schedule regular exercise in order to avoid diabetes in future.";
                    bundle.putString("key", dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);

            }
        });
    }

    private void addListenerMedicalRadioGroup5() {
        medicalOptionGroup5 = (RadioGroup) rootView.findViewById
                (R.id.medical_radio_group5);
        medicalRadioSubmitOption5 = (Button) rootView.findViewById(R.id.medical_radio_submit_group5);
        medicalRadioSubmitOption5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String dialogText = null;
                Bundle bundle = new Bundle();

                // get selected radio button from radioGroupTutorials
                int selected =
                        medicalOptionGroup5.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.medical_option5_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.medical_option5_radio2)).isChecked();

                // Check which radio button was clicked
                if (checked1) {
                    optionValues[4] = 1;
                    dialogText ="Untreated depression can cause trouble with mental tasks such as remembering, concentrating, or making decisions.";
                    bundle.putString("key",dialogText);
                }
                if (checked2) {
                    optionValues[4] = 2;
                    dialogText ="Always opt for a healthy diet,regular exercise,fun and relaxation to keep yourself away from depression.";
                    bundle.putString("key",dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);

            }

        });
    }
    private void addListenerMedicalRadioGroup6() {
        medicalOptionGroup6 = (RadioGroup) rootView.findViewById
                (R.id.medical_radio_group6);
        medicalRadioSubmitOption6 = (Button) rootView.findViewById(R.id.medical_radio_submit_group6);
        medicalRadioSubmitOption6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String dialogText = null;
                Bundle bundle = new Bundle();

                // get selected radio button from radioGroupTutorials
                int selected =
                        medicalOptionGroup6.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.medical_option6_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.medical_option6_radio2)).isChecked();

                // Check which radio button was clicked
                if (checked1) {
                    optionValues[5] = 1;
                    dialogText ="keep your diet healthy,eat plenty of fruits and vegetables and prefer regular health checkups.";
                    bundle.putString("key",dialogText);
                }
                if (checked2) {
                    optionValues[5] = 2;
                    dialogText ="Eat plenty of fruits and vegetables,limit fat,maintain a healthy weight and be physically active to lower the risk of various types of cancer.";
                    bundle.putString("key",dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);

            }
        });
    }
    private void addListenerMedicalRadioGroup7() {
        medicalOptionGroup7 = (RadioGroup) rootView.findViewById
                (R.id.medical_radio_group7);
        medicalRadioSubmitOption7 = (Button) rootView.findViewById(R.id.medical_radio_submit_group7);
        medicalRadioSubmitOption7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String dialogText = null;
                Bundle bundle = new Bundle();

                // get selected radio button from radioGroupTutorials
                int selected =
                        medicalOptionGroup7.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.medical_option7_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.medical_option7_radio2)).isChecked();
                // Check which radio button was clicked
                if (checked1) {
                    optionValues[6] = 1;
                    dialogText ="Tobacco is the single greatest cause of preventable death globally.Tobacco use leads most commonly to diseases affecting the heart, liver and lungs.";
                    bundle.putString("key",dialogText);
                }
                if (checked2) {
                    optionValues[6] = 2;
                    dialogText ="Great!!Health is the groundwork of all happiness.";
                    bundle.putString("key",dialogText);
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

