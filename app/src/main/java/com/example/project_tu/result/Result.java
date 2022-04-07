package com.example.project_tu.result;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.project_tu.R;

public class Result implements Parcelable {
    private float score;
    private int image;
    private String text;
    private int scoreTxt;
    public Result(float correct,float wrong) {
        this.score = correct/(correct+wrong)*100;
        this.scoreTxt=Math.round(score);
        if (score<20) {
            this.text = "You have to study harder";
            this.image = R.drawable.star0;
        }
        else if (20<=score&&score<40) {
            this.text = "You have to study a little bit harder ";
            this.image = R.drawable.star1;
        }
        else if (40<=score&&score<60) {
            this.text = "Not bad";
            this.image = R.drawable.star2;
        }
        else if (60<=score&&score<80) {
            this.text = "Not bad at all";
            this.image = R.drawable.star3;
        }
        else if (80<=score&&score<90) {
            this.text = "Well done";
            this.image = R.drawable.star4;
        }
        else if (90<=score) {
            this.text = "Congratulations! You're a REAL winner";
            this.image = R.drawable.star5;
        }
        else {
            this.text = "ERROR";
        }
    }

    protected Result(Parcel in) {
        this.text=in.readString();
        this.image=in.readInt();
        this.score=in.readInt();
        this.scoreTxt=in.readInt();
    }

    public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(text);
        parcel.writeInt(image);
        parcel.writeFloat(score);
        parcel.writeInt(scoreTxt);
    }

    public int getScore() {
        return this.scoreTxt;
    }

    public int getImage() {
        return image;
    }

    public String getText() {
        return text;
    }
}
