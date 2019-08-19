package com.snapdeal.ums.loyalty;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

public class LoyaltyConstants implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 765328264547L;

    /**
     * ALL THE LOYALTY PROGRAMS SUPPORTED BY SNAPDEAL. toString() is what gets
     * stored in the DB.
     * 
     * NOTE: DO NOT RENAME THE VALUE of the enum!!!!
     */
    public enum LoyaltyProgram
    {
        SNAPBOX("SNAPBOX");

        private String value;

        LoyaltyProgram(String value)
        {

            this.value = value;
        }

        @Override
        public String toString()
        {

            return value;
        }

        @JsonValue
        public String toJson()
        {

            return name().toUpperCase();
        }

        @JsonCreator
        public static LoyaltyProgram fromJson(String text)
        {

            return valueOf(text.toUpperCase());
        }

    }

    /**
     * ALL THE LOYALTY STATUS SUPPORTED BY SNAPDEAL. getValue() is what gets
     * stored in the DB.
     * 
     * NOTE: DO NOT RENAME THE VALUE of the enum
     */

    public enum LoyaltyStatus
    {
        ELIGIBLE("ELIGIBLE"), ACTIVE("ACTIVE"), INELIGIBLE("INELIGIBLE");

        private String value;

        LoyaltyStatus(String value)
        {

            this.value = value;
        }

        @Override
        public String toString()
        {

            return value;
        }

        @JsonValue
        public String toJson()
        {

            return name().toUpperCase();
        }

        @JsonCreator
        public static LoyaltyStatus fromJson(String text)
        {

            return valueOf(text.toUpperCase());
        }

        // public LoyaltyStatus getLoyaltyStatus(String name)
        // {
        //
        // return getLoyaltyStatus(name);
        // }

    }

    /**
     * Status of the file uploaded for loyalty program
     * 
     * NOTE: DO NOT RENAME THE VALUE of the enum!!!!
     */
    public enum LoyaltyUploadedFileStatus
    {
        RECIEVED("RECIEVED"),
        FINISHED_PROCESSING("FINISHED_PROCESSING");

        private String value;

        LoyaltyUploadedFileStatus(String value)
        {

            this.value = value;
        }

        @Override
        public String toString()
        {

            return value;
        }

        @JsonValue
        public String toJson()
        {

            return name().toUpperCase();
        }

        @JsonCreator
        public static LoyaltyUploadedFileStatus fromJson(String text)
        {

            return valueOf(text.toUpperCase());
        }

    }

}
