package com.mobium.testproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobItem implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("company_logo")
    @Expose
    private String companyLogo;

    public JobItem(String id, String company, String location, String title, String description, String companyLogo) {
        this.id = id;
        this.company = company;
        this.location = location;
        this.title = title;
        this.description = description;
        this.companyLogo = companyLogo;
    }

    protected JobItem(Parcel in) {
        id = in.readString();
        company = in.readString();
        location = in.readString();
        title = in.readString();
        description = in.readString();
        companyLogo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(company);
        dest.writeString(location);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(companyLogo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<JobItem> CREATOR = new Creator<JobItem>() {
        @Override
        public JobItem createFromParcel(Parcel in) {
            return new JobItem(in);
        }

        @Override
        public JobItem[] newArray(int size) {
            return new JobItem[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }
}
