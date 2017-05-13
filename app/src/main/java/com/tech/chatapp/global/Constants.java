package com.tech.chatapp.global;

/**
 * Created by arbaz on 6/2/17.
 */

public class Constants {

    public static final String BOLD_FONT = "SFD-Bold.otf";
    public static final String REGULAR_FONT = "SFD-Regular.otf";
    public static final String THIN_FONT = "SFD-Thin.otf";
    public static final String DEFAULT_URI = "http://www.nribridesgrooms.com/slider/slider_2.jpg";
    public static final String DEFAULT_AVTAR = "http://i.imgur.com/Zp8RQt8.jpg";
    public static final String IS_CATEGORY = "Category";
    public static final String IS_COUNTY = "Country";
    public static final String IS_STATE = "State";
    public static final String IS_DISTRICT = "District";
    public static final String IS_CITY = "City";
    public static final String IS_AREA = "Area";
    public static final String IS_EDU_LEVEL = "EduLevel";
    public static final String IS_EDU_FIELD = "EduField";
    public static final String IS_HEIGHT = "HeightRequest";
    public static final String IS_NONE = "None";
    public static final String IS_PHYSICAL = "Physical Disability";
    public static final String IS_MATTER = "Doesn't Matter";
    public static final String IS_NOT_MATTER = "Don/'t Include Profiles with Disability";

    //ADVERTISEMENTS
    public static final String DATABASE_NAME = "jainSamajDB";
    //TimeStamp Table
    public static final String TABLE_TIME_STAMP= "jsTimeStamp";

    public static final String TABLE_TIME_STAMP_DATE= "timeStamp";
    public static final String TABLE_TIME_STAMP_IS_FIRST_TIME= "isFirstTime";


    //AdvertisementMain Table
    public static final String TABLE_ADVERTISEMENTS = "jsAdvertisement";

    public static final String ADVERTISEMENTS_TABLE_KEY_ID = "id";
    public static final String ADVERTISEMENTS_TABLE_KEY_C_DATETIME = "createdDateTime";
    public static final String ADVERTISEMENTS_TABLE_KEY_C_USER = "createdUser";
    public static final String ADVERTISEMENTS_TABLE_KEY_U_DATETIME = "updatedDateTime";
    public static final String ADVERTISEMENTS_TABLE_KEY_STATUS = "status";
    public static final String ADVERTISEMENTS_TABLE_KEY_TITLE = "title";
    public static final String ADVERTISEMENTS_TABLE_KEY_DESCRIPTION = "description";
    public static final String ADVERTISEMENTS_TABLE_KEY_COMPANY_NAME = "companyName";
    public static final String ADVERTISEMENTS_TABLE_KEY_COMPANY_ADDRESS = "companyAddress";
    public static final String ADVERTISEMENTS_TABLE_KEY_COMPANY_CONTACT = "companyContact";
    public static final String ADVERTISEMENTS_TABLE_KEY_COMPANY_EMAIL = "companyEmail";
    public static final String ADVERTISEMENTS_TABLE_KEY_ACTIVE = "active";

    //Attachment Table
    public static final String TABLE_ATTACHMENT_LINK = "jsAttachmentImageLink";
    public static final String ATTACHMENT_TABLE_ID = "attachmentId";
    public static final String ATTACHMENT_TABLE_URL = "attachmentUrl";
    public static final String ATTACHMENT_TABLE_COMPANY_URL = "companyUrl";

    //Medium Image Table
    public static final String TABLE_MEDIUM_IMAGE_LINK = "jsMediumImageLink";
    public static final String MEDIUM_TABLE_ID = "mediumId";
    public static final String MEDIUM_TABLE_URL = "mediumUrl";
    public static final String MEDIUM_TABLE_COMPANY_URL = "companyUrl";
    //Small Image Table
    public static final String TABLE_SMALL_IMAGE_LINK = "jsSmallImageLink";
    public static final String SMALL_TABLE_ID = "smallImageId";
    public static final String SMALL_TABLE_URL = "companyImageUrl";
    public static final String SMALL_TABLE_COMPANY_URL = "companyUrl";

    /*Wall PostType*/
    public static final String WALL_POST_CANDIDATE = "Matrimonial";
    public static final String WALL_POST_EVENT = "Event";
    public static final String WALL_POST_JOB = "JobPost";
    public static final String WALL_POST_PRODUCT = "ProductServicePost";
    public static final String WALL_POST_TEMPLE_FINDER= "TempleFinder";

    public static final int WALL_ADVERTISEMENT = 4;
    public static final int WALL_POST_CANDIDATE_INT = 1000;
    public static final int WALL_POST_EVENT_INT = 2000;
    public static final int WALL_POST_JOB_INT = 3000;
    public static final int WALL_POST_PRODUCT_INT = 4000;
    public static final int WALL_POST_UNKNOWN = 5000;
    public static final int WALL_POST_TEMPLE_FINDER_INT= 6000;


    public static final String USER_IMAGE="updateImages";
}
