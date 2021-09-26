package com.example.orderapp;

public class Model {


    String mPhoneName;
    String mPhoneDetail;
    int mPhonePhoto;

    public Model(String mPhoneName, String mPhoneDetail, int mPhonePhoto) {
        this.mPhoneName = mPhoneName;
        this.mPhoneDetail = mPhoneDetail;
        this.mPhonePhoto = mPhonePhoto;
    }




    public String getmPhoneName() {

        return mPhoneName;
    }

    public String getmPhoneDetail() {

        return mPhoneDetail;
    }

    public int getmPhonePhoto() {

        return mPhonePhoto;
    }
}
