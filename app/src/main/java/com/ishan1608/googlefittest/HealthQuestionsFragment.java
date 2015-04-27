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

public class HealthQuestionsFragment extends Fragment {
    //new code
    View rootView;
    int optionValues[] = new int[7];
    private TextView questionContentView1, questionContentView2, questionContentView3, questionContentView4, questionContentView5;
    // private LinearLayout optionView;
    private RadioGroup healthOptionGroup1, healthOptionGroup2, healthOptionGroup3, healthOptionGroup4, healthOptionGroup5, healthOptionGroup6, healthOptionGroup7;
    private LinearLayout optionView, optionContentView2, optionContentView3, optionContentView4, optionContentView5;
    private View questionSetHeadingView, questionHeadingView1, questionHeadingView2, questionHeadingView3, questionHeadingView4, questionHeadingView5,questionHeadingView6,questionHeadingView7;
    private View optionHeadingView, optionHeadingView2, optionHeadingView3, optionHeadingView4, optionHeadingView5,optionHeadingView6,optionHeadingView7;
    private Button radioSubmitOption1, radioSubmitOption2, radioSubmitOption3, radioSubmitOption4, radioSubmitOption5, radioSubmitOption6, radioSubmitOption7;
    private Button registrationButton;
    private int categoryPosition, eventPosition;

    //new code ends
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_question, container, false);
        //new code
        questionSetHeadingView = rootView.findViewById(R.id.question_set1);
        questionHeadingView1 = rootView.findViewById(R.id.question1_heading);
        optionHeadingView = rootView.findViewById(R.id.options1_heading);
        questionHeadingView2 = rootView.findViewById(R.id.question2_heading);
        optionHeadingView2 = rootView.findViewById(R.id.option2_heading);
        questionHeadingView3 = rootView.findViewById(R.id.question3_heading);
        optionHeadingView3 = rootView.findViewById(R.id.option3_heading);
        questionHeadingView4 = rootView.findViewById(R.id.question4_heading);
        optionHeadingView4 = rootView.findViewById(R.id.option4_heading);
        questionHeadingView5 = rootView.findViewById(R.id.question5_heading);
        optionHeadingView5 = rootView.findViewById(R.id.option5_heading);
        questionHeadingView6 = rootView.findViewById(R.id.question6_heading);
        optionHeadingView6 = rootView.findViewById(R.id.option6_heading);
        questionHeadingView7 = rootView.findViewById(R.id.question7_heading);
        optionHeadingView7 = rootView.findViewById(R.id.option7_heading);

        questionContentView1 = (TextView) rootView.findViewById(R.id.question1_content);
        optionView = (LinearLayout) rootView.findViewById(R.id.option1_content);
        questionContentView2 = (TextView) rootView.findViewById(R.id.question2_content);
        optionContentView2 = (LinearLayout) rootView.findViewById(R.id.option2_content);
        questionContentView3 = (TextView) rootView.findViewById(R.id.question3_content);
        optionContentView3 = (LinearLayout) rootView.findViewById(R.id.option3_content);
        questionContentView4 = (TextView) rootView.findViewById(R.id.question4_content);
        optionContentView4 = (LinearLayout) rootView.findViewById(R.id.option4_content);
        questionContentView4 = (TextView) rootView.findViewById(R.id.question4_content);
        optionContentView4 = (LinearLayout) rootView.findViewById(R.id.option4_content);


        // Setting the hide and display
        questionSetHeadingView.setOnClickListener(new contentToggler());
        questionHeadingView1.setOnClickListener(new contentToggler());
        optionHeadingView.setOnClickListener(new contentToggler());
        questionHeadingView2.setOnClickListener(new contentToggler());
        optionHeadingView2.setOnClickListener(new contentToggler());
        questionHeadingView3.setOnClickListener(new contentToggler());
        optionHeadingView3.setOnClickListener(new contentToggler());
        questionHeadingView4.setOnClickListener(new contentToggler());
        optionHeadingView4.setOnClickListener(new contentToggler());
        questionHeadingView5.setOnClickListener(new contentToggler());
        optionHeadingView5.setOnClickListener(new contentToggler());
        questionHeadingView6.setOnClickListener(new contentToggler());
        optionHeadingView6.setOnClickListener(new contentToggler());
        questionHeadingView7.setOnClickListener(new contentToggler());
        optionHeadingView7.setOnClickListener(new contentToggler());
        addListenerHealthRadioGroup1();
        addListenerHealthRadioGroup2();
        addListenerHealthRadioGroup3();
        addListenerHealthRadioGroup4();
        addListenerHealthRadioGroup5();
        addListenerHealthRadioGroup6();
        addListenerHealthRadioGroup7();

        return rootView;
    }

    private void addListenerHealthRadioGroup1() {
        healthOptionGroup1 = (RadioGroup) rootView.findViewById
                (R.id.health_radio_group1);
        radioSubmitOption1 = (Button) rootView.findViewById(R.id.radio_submit_group1);
        radioSubmitOption1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String dialogText = null;
                Bundle bundle = new Bundle();
                // get selected radio button from radioGroupTutorials
                int selected =
                        healthOptionGroup1.getCheckedRadioButtonId();
                boolean checked = ((RadioButton) rootView.findViewById(R.id.health_option1_radio1)).isChecked();

                // Check which radio button was clicked
                if (checked) {
                    optionValues[0] = 1;
                    dialogText ="High blood glucose levels can contribute to the formation of fatty deposits in blood vessel walls. Over time, that can restrict blood flow and increase the risk of hardening of the blood vessels.";
                    bundle.putString("key",dialogText);
                } else {
                    optionValues[0] = 2;
                    dialogText ="That's good..Stay healthy!!";
                    bundle.putString("key",dialogText);
                }

                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);
            }
        });
    }

    private void addListenerHealthRadioGroup2() {
        healthOptionGroup2 = (RadioGroup) rootView.findViewById
                (R.id.health_radio_group2);
        radioSubmitOption2 = (Button) rootView.findViewById(R.id.radio_submit_group2);
        radioSubmitOption2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String dialogText = null;
                Bundle bundle = new Bundle();
                // get selected radio button from radioGroupTutorials
                int selected =
                        healthOptionGroup2.getCheckedRadioButtonId();
                boolean checked = ((RadioButton) rootView.findViewById(R.id.health_option2_radio1)).isChecked();

                // Check which radio button was clicked
                if (checked) {
                    optionValues[0] = 1;
                    dialogText ="If you have too much cholesterol, it starts to build up in your arteries. It is the starting point for some heart and blood flow problems";
                    bundle.putString("key" ,dialogText);
                } else {
                    optionValues[0] = 2;
                    dialogText ="Decreases your chance of plaque formation in your arteries. Plaque in your arteries can lead to strokes, heart attacks, and peripheral artery disease";
                    bundle.putString("key",dialogText);
                }

                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);
            }
        });
    }

    private void addListenerHealthRadioGroup3() {
        healthOptionGroup3 = (RadioGroup) rootView.findViewById
                (R.id.health_radio_group3);
        radioSubmitOption3 = (Button) rootView.findViewById(R.id.radio_submit_group3);
        radioSubmitOption3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String dialogText = null;
                Bundle bundle = new Bundle();
                // get selected radio button from radioGroupTutorials
                int selected =
                        healthOptionGroup3.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.health_option3_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.health_option3_radio2)).isChecked();
                if (checked1) {
                    optionValues[2] = 1;
                    dialogText ="Alcohol affects the entire body, including the brain, nervous system, liver, heart, and the individual’s emotional well-being";
                    bundle.putString("key" ,dialogText);
                }
                if(checked2) {
                    optionValues[2] = 2;
                    dialogText ="That's good...Stay healthy!!";
                    bundle.putString("key" ,dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);
            }
        });
    }

    private void addListenerHealthRadioGroup4() {
        healthOptionGroup4 = (RadioGroup) rootView.findViewById
                (R.id.health_radio_group4);
        radioSubmitOption4 = (Button) rootView.findViewById(R.id.radio_submit_group4);
        radioSubmitOption4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroupTutorials
                String dialogText = null;
                Bundle bundle = new Bundle();
                int selected =
                        healthOptionGroup4.getCheckedRadioButtonId();

                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.health_option4_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.health_option4_radio2)).isChecked();
                boolean checked3 = ((RadioButton) rootView.findViewById(R.id.health_option4_radio3)).isChecked();


                // Check which radio button was clicked
                if (checked1) {
                    optionValues[3] = 1;
                    dialogText ="Smoking harms nearly every organ of the body";
                    bundle.putString("key" ,dialogText);
                }
                if (checked2) {
                    optionValues[3] = 2;
                    dialogText ="That's good .keeping yourself away from all these will keep you healthy";
                    bundle.putString("key" ,dialogText);
                }
                if (checked3) {
                    optionValues[3] = 3;
                    dialogText ="Its good that finally you took a step and quit smoking but make sure that you have regular check-up to avoid any health related problem";
                    bundle.putString("key" ,dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);
            }
        });
    }

    private void addListenerHealthRadioGroup5() {
        healthOptionGroup5 = (RadioGroup) rootView.findViewById
                (R.id.health_radio_group5);
        radioSubmitOption5 = (Button) rootView.findViewById(R.id.radio_submit_group5);
        radioSubmitOption5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroupTutorials
                String dialogText = null;
                Bundle bundle = new Bundle();
                int selected =
                        healthOptionGroup5.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.health_option5_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.health_option5_radio2)).isChecked();

                if (checked1) {
                    optionValues[4] = 1;
                    dialogText ="Walking helps you to shed those extra kilos, it also tones your body and shapes you well";
                    bundle.putString("key" ,dialogText);
                }
                if (checked2) {
                    optionValues[4] = 2;
                    dialogText ="Walking energizes you, awakens you and stills your mind to fully relax.So its beneficial if u indulge yourself in walking activities on daily basis";
                    bundle.putString("key" ,dialogText);
                }
                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);

                // Check which radio button was clicked

            }
        });
    }
    private void addListenerHealthRadioGroup6() {
        healthOptionGroup6 = (RadioGroup) rootView.findViewById
                (R.id.health_radio_group6);
        radioSubmitOption6 = (Button) rootView.findViewById(R.id.radio_submit_group6);
        radioSubmitOption6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroupTutorials
                String dialogText = null;
                Bundle bundle = new Bundle();

                int selected =
                        healthOptionGroup6.getCheckedRadioButtonId();
                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.health_option6_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.health_option6_radio2)).isChecked();
                boolean checked3 = ((RadioButton) rootView.findViewById(R.id.health_option6_radio3)).isChecked();



                // Check which radio button was clicked

                if (checked1) {
                    optionValues[5] = 1;
                    dialogText = "When you’re deprived of sleep, your brain can’t function properly, affecting your cognitive abilities and emotional state";
                    bundle.putString("key", dialogText);
                }
                if (checked2) {
                    optionValues[5] = 2;
                    dialogText = "Excess sleep can slow down your brain and can make you more tired";
                    bundle.putString("key", dialogText);
                }
                if (checked3) {
                    optionValues[5] = 3;
                    dialogText = "Adequate sleep is a key part of a healthy lifestyle, and can benefit your heart, weight, mind, and more";
                    bundle.putString("key", dialogText);
                }

                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);


            }
        });
    }
    private void addListenerHealthRadioGroup7() {
        healthOptionGroup7 = (RadioGroup) rootView.findViewById
                (R.id.health_radio_group7);
        radioSubmitOption7 = (Button) rootView.findViewById(R.id.health_radio_submit_group7);
        radioSubmitOption7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroupTutorials
                String dialogText = null;
                Bundle bundle = new Bundle();
                int selected =
                        healthOptionGroup7.getCheckedRadioButtonId();

                boolean checked1 = ((RadioButton) rootView.findViewById(R.id.health_option7_radio1)).isChecked();
                boolean checked2 = ((RadioButton) rootView.findViewById(R.id.health_option7_radio2)).isChecked();
                boolean checked3 = ((RadioButton) rootView.findViewById(R.id.health_option7_radio3)).isChecked();


                // Check which radio button was clicked
                if (checked1) {
                    optionValues[6] = 1;
                    dialogText = "abc";
                    bundle.putString("key", dialogText);
                }
                if (checked2) {
                    optionValues[6] = 2;
                    dialogText = "abc";
                    bundle.putString("key", dialogText);
                }
                if (checked3) {
                    optionValues[6] = 3;
                    dialogText = "abc";
                    bundle.putString("key", dialogText);
                }

                Intent developerIntent = new Intent(getActivity(), DialogActivity.class);
                developerIntent.putExtras(bundle);
                startActivity(developerIntent);

            }


        });
    }
}

class contentToggler implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        ViewGroup parent = (ViewGroup) v.getParent();
        View childView = parent.getChildAt(parent.indexOfChild(v) + 1);
        this.viewToggler(childView);
    }

    private void viewToggler(View view) {
        if(view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}