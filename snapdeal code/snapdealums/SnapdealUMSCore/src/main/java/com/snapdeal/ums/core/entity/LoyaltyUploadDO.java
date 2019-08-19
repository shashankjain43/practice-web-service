package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.snapdeal.ums.loyalty.LoyaltyConstants.LoyaltyUploadedFileStatus;

@Entity
@Table(name = "loyalty_uploads", catalog = "ums")
public class LoyaltyUploadDO
{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_content")
    private byte[] fileContent;

    @Column(name = "invalid_email_count")
    private int invalidEmailCount;

    @Column(name = "valid_email_count")
    private int validEmailCount;

    @Column(name = "existent_email_count")
    private int existentEmailCount;

    @Column(name = "raw_entries_count")
    private int rawEntriesCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LoyaltyUploadedFileStatus status;

    public int getId()
    {

        return id;
    }

    public String getFileName()
    {

        return fileName;
    }

    public byte[] getFileContent()
    {

        return fileContent;
    }

    public int getInvalidEmailCount()
    {

        return invalidEmailCount;
    }

    public int getValidEmailCount()
    {

        return validEmailCount;
    }

    public int getExistentEmailCount()
    {

        return existentEmailCount;
    }

    public int getRawEntriesCount()
    {

        return rawEntriesCount;
    }

    public LoyaltyUploadDO(String fileName, byte[] fileContent, int invalidEmailCount, int validEmailCount,
        int existentEmailCount, int rawEntriesCount, LoyaltyUploadedFileStatus status)
    {

        super();
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.invalidEmailCount = invalidEmailCount;
        this.validEmailCount = validEmailCount;
        this.existentEmailCount = existentEmailCount;
        this.rawEntriesCount = rawEntriesCount;
        this.status = status;
    }

    public LoyaltyUploadedFileStatus getStatus()
    {

        return status;
    }

    public void setStatus(LoyaltyUploadedFileStatus status)
    {

        this.status = status;
    }

//    public LoyaltyUploadDO()
//    {
//
//        super();
//    }

}
