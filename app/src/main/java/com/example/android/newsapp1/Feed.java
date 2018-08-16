package com.example.android.newsapp1;

import java.util.Date;

public class Feed {
    public Date mDate;
    private String mUrl;
    private String mAuthorFullName;
    private String mSectionName;
    private String mTitleName;

    /**
     * Construct a new {@link Feed} object.
     *
     * @param date
     * @param authorFullName
     * @param sectionName
     * @param titleName
     * @param url
     */
    public Feed(Date date, String authorFullName, String sectionName, String titleName, String url) {
        mDate = date;
        mAuthorFullName = authorFullName;
        mSectionName = sectionName;
        mTitleName = titleName;
        mUrl = url;
    }

    public Date getDate() {
        return mDate;
    }
    public String getAuthorFullName() {
        return mAuthorFullName;
    }
    public String getSectionName() {
        return mSectionName;
    }
    public String getTitleName() {
        return mTitleName;
    }
    public String getUrl() {
        return mUrl;
    }
}

