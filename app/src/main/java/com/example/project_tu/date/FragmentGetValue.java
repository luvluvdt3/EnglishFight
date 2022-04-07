package com.example.project_tu.date;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_tu.R;

import java.text.DecimalFormat;

public class FragmentGetValue extends Fragment {
    CalendarView calendar;
    String date;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.date_fragment_up, container, false);
        TextView titre=rootView.findViewById(R.id.txtUp);
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/FredokaOneRegular.ttf");
        titre.setTypeface(typeface);
        calendar=rootView.findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(
                    @NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        String date= getDate(year, month, dayOfMonth).toUpperCase();
                        Fragment fragment;
                        FragmentTransaction fragmentTransaction;
                        fragment=new FragmentDisplayValue();
                        Bundle args = new Bundle();
                        args.putString(getString(R.string.value), date);
                        fragment.setArguments(args);
                        try {
                            fragmentTransaction = getParentFragmentManager().beginTransaction().replace(R.id.frameDown, fragment);
                            fragmentTransaction.commit();
                        }
                        catch (Exception e) {
                            e.printStackTrace();    //erreur fragment non créé
                        }
                    }
        });
        return rootView;
    }//end onCreate
    private final String[] tensNames = {
            "",
            " ten",
            " twenty",
            " thirty",
            " forty",
            " fifty",
            " sixty",
            " seventy",
            " eighty",
            " ninety"
    };

    private final String[] numNames = {
            "",
            " one",
            " two",
            " three",
            " four",
            " five",
            " six",
            " seven",
            " eight",
            " nine",
            " ten",
            " eleven",
            " twelve",
            " thirteen",
            " fourteen",
            " fifteen",
            " sixteen",
            " seventeen",
            " eighteen",
            " nineteen"
    };


    private  String convertLessThanOneThousand(int number) {
        String soFar;
        if (number % 100 < 20){
            soFar = numNames[number % 100];
            number /= 100;
        }
        else {
            soFar = numNames[number % 10];
            number /= 10;
            soFar = tensNames[number % 10] + soFar;
            number /= 10;
        }
        if (number == 0) return soFar;
        return numNames[number] + " hundred" + soFar;
    }
    private String toDay(int number){
        String day=convertLessThanOneThousand(number);
        if(number>20 && number%10 !=0 && number!=30){ //20+
            day=convertLessThanOneThousand(number-number%10);
            if(number%10==1){ //twenty the first, thirty the first
                day+="-first";
            }
            else if(number%10==2){
                day+="-second";
            }
            else if(number%10==3){
                day+="-third";
            }
            else if(number%10==4){
                day+="-fourth";
            }
            else if(number%10==5){
                day+="-fifth";
            }
            else if(number%10==6){
                day+="-sixth";
            }
            else if(number%10==7){
                day+="-seventh";
            }
            else if(number%10==8){
                day+="-eighth";
            }
            else {
                day+="-ninth";
            }
        }
        else {
            day="";
            switch(number){
                case 1:
                    day+="the first";
                    break;
                case 2:
                    day+="the second";
                    break;
                case 3:
                    day+="the third";
                    break;
                case 4:
                    day+="the fourth";
                    break;
                case 5:
                    day+="the fifth";
                    break;
                case 6:
                    day+="the sixth";
                    break;
                case 7:
                    day+="the seventh";
                    break;
                case 8:
                    day+="the eighth";
                    break;
                case 9:
                    day+="the ninth";
                    break;
                case 10:
                    day+="the tenth";
                    break;
                case 11:
                    day+="the eleventh";
                    break;
                case 12:
                    day+="the twelfth";
                    break;
                case 13:
                    day+="the thirteenth";
                    break;
                case 14:
                    day+="the fourteenth";
                    break;
                case 15:
                    day+="the fifth";
                    break;
                case 16:
                    day+="the sixteenth";
                    break;
                case 17:
                    day+="the seventeenth";
                    break;
                case 18:
                    day+="the eighteenth";
                    break;
                case 19:
                    day+="the nineteenth";
                    break;
                case 20:
                    day+="twentyth";
                    break;
                case 30:
                    day+="thirtieth";
                    break;
            }
        }
        return day;
    }
    public String toMonth(int number){
        String[] str = {"January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"};
        if(number<str.length)
            return str[number];
        else
            return "Invalid month";
    }
    public  String convert(int number) {
        // 0 to 999 999 999 999
        if (number == 0) { return "zero"; }
        String snumber = Long.toString(number);
        // pad with "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);
        // XXXnnnnnnnnn
        int billions = Integer.parseInt(snumber.substring(0,3));
        // nnnXXXnnnnnn
        int millions  = Integer.parseInt(snumber.substring(3,6));
        // nnnnnnXXXnnn
        int hundredThousands = Integer.parseInt(snumber.substring(6,9));
        // nnnnnnnnnXXX
        int thousands = Integer.parseInt(snumber.substring(9,12));
        String tradBillions;
        switch (billions) {
            case 0:
                tradBillions = "";
                break;
            case 1 :
                tradBillions = convertLessThanOneThousand(billions) + " billion ";
                break;
            default :
                tradBillions = convertLessThanOneThousand(billions) + " billion ";
        }
        String result =  tradBillions;

        String tradMillions;
        switch (millions) {
            case 0:
                tradMillions = "";
                break;
            case 1 :
                tradMillions = convertLessThanOneThousand(millions) + " million ";
                break;
            default :
                tradMillions = convertLessThanOneThousand(millions) + " million ";
        }
        result =  result + tradMillions;
        String tradHundredThousands;
        switch (hundredThousands) {
            case 0:
                tradHundredThousands = "";
                break;
            case 1 :
                tradHundredThousands = "one thousand ";
                break;
            default :
                tradHundredThousands = convertLessThanOneThousand(hundredThousands)
                        + " thousand ";
        }
        result =  result + tradHundredThousands;
        String tradThousand;
        tradThousand = convertLessThanOneThousand(thousands);
        result =  result + tradThousand;


        // remove extra spaces!
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }

    private String getDate(int year, int month, int dayOfMonth){
        return toDay(dayOfMonth)+" "+toMonth(month)+", "+convert(year);
    }
}