package com.example.friendverse.COR;

import android.os.Parcel;
import android.os.Parcelable;

public class RegistrationContext implements Parcelable {
    private String email;
    private int otp;
    private boolean isEmailVerified;
    private boolean otpVerified;
    private boolean userRegisterVerified;
    private String uid;

    public RegistrationContext() {}

    protected RegistrationContext(Parcel in) {
        email = in.readString();
        otp = in.readInt();
        isEmailVerified = in.readByte() != 0;
    }

    public static final Creator<RegistrationContext> CREATOR = new Creator<RegistrationContext>() {
        @Override
        public RegistrationContext createFromParcel(Parcel in) {
            return new RegistrationContext(in);
        }

        @Override
        public RegistrationContext[] newArray(int size) {
            return new RegistrationContext[size];
        }
    };

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getOtp() { return otp; }
    public void setOtp(int otp) { this.otp = otp; }

    public boolean isEmailVerified() { return isEmailVerified; }
    public void setEmailVerified(boolean emailVerified) { isEmailVerified = emailVerified; }

    public boolean isOtpVerified() {
        return otpVerified;
    }

    public void setOtpVerified(boolean otpVerified) {
        this.otpVerified = otpVerified;
    }
    public boolean isUserRegisterVerified() {return userRegisterVerified; }
    public void setUserRegistered(boolean userRegisterVerified) {this.userRegisterVerified = userRegisterVerified;}
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeInt(otp);
        parcel.writeByte((byte) (isEmailVerified ? 1 : 0));
    }

}
