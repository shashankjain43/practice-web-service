package com.snapdeal.ums.services.loyalty;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.audit.annotation.AuditableMethod;
import com.snapdeal.ums.constants.ErrorConstants;
import com.snapdeal.ums.core.entity.LoyaltyUploadDO;
import com.snapdeal.ums.dao.loyalty.ILoyaltyUploadDao;
import com.snapdeal.ums.dao.loyalty.ILoyaltyUserDtlsDao;
import com.snapdeal.ums.dao.users.IUsersDao;
import com.snapdeal.ums.loyalty.LoyaltyConstants;
import com.snapdeal.ums.loyalty.LoyaltyConstants.LoyaltyProgram;
import com.snapdeal.ums.loyalty.LoyaltyConstants.LoyaltyStatus;
import com.snapdeal.ums.loyalty.LoyaltyConstants.LoyaltyUploadedFileStatus;
import com.snapdeal.ums.loyalty.LoyaltyUploadRq;
import com.snapdeal.ums.loyalty.LoyaltyUploadRs;
import com.snapdeal.ums.services.ValidationService;

/**
 * 
 * Manager class to handle all the file upload tasks related to loyalty.
 * 
 * @author ashish
 * 
 */
@Service
public class LoyaltyUploadManager
{

    private final static int MAX_ELIGIBILITY_EMAIL_COUNT_PER_UPLOAD = 100000;

    @Autowired
    private ILoyaltyUploadDao loyaltyUploadDao;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private IUsersDao usersDao;

    @Autowired
    private ILoyaltyUserDtlsDao loyaltyUserDtlsDao;

    @Autowired
    private LoyaltyProgramStatusManager loyaltyProgramStatusManager;

    @Autowired
    private SnapBoxEnableEligibilityProcessor snapBoxEnableEligibilityProcessor;

    @Autowired
    private LoyaltyFileUploadValidator fileUploadValidator;

    private static final Logger log = LoggerFactory.getLogger(LoyaltyUploadManager.class);

    private final int EMAIL_PROCESSING_BATCH_SIZE = 1;

    /**
     * Class which captures required user details to avaid and visit the user db
     * multiple times during the long running business flow.
     * 
     * @author ashish
     * 
     */
    public static class UserPrerequisiteInfo
    {

        private String name;

        private int id;

        private String encryptedVerificationCode;

        public UserPrerequisiteInfo(int id, String name, String encryptedVerificationCode)
        {

            this.encryptedVerificationCode = encryptedVerificationCode;
            this.name = name;
            this.id = id;
        }

        public String getEncryptedVerificationCode()
        {

            return encryptedVerificationCode;
        }

        public void setEncryptedVerificationCode(String encryptedVerificationCode)
        {

            this.encryptedVerificationCode = encryptedVerificationCode;
        }

        public String getName()
        {

            return name;
        }

        public void setName(String name)
        {

            this.name = name;
        }

        public int getId()
        {

            return id;
        }

        public void setId(int id)
        {

            this.id = id;
        }

    }

    @AuditableMethod
    @Transactional
    public LoyaltyUploadRs processLoyaltyUploads(LoyaltyUploadRq uploadRq)
    {

        log.info(Thread.currentThread().getName() + " entered processLoyaltyUploads()");
        LoyaltyUploadRs loyaltyUploadRs = null;
        try {
            if (uploadRq == null) {
                log.error("NULL LoyaltyUploadRq recieved for processing loyaltyUPloads!");
                return null;
            }

            loyaltyUploadRs = fileUploadValidator.validateLoyaltyUpload(uploadRq);
            if (loyaltyUploadRs.isSuccessful() == false) {
                log.error("Validations failed for the uplaoded file.");
                return loyaltyUploadRs;
            }

            LoyaltyProgram loyaltyProgram = uploadRq.getLoyaltyProgram();
            LoyaltyStatus loyaltyStatus = uploadRq.getLoyaltyStatus();

            log.info("Will process loyalty uploads for :" + loyaltyProgram + " and status: " + loyaltyStatus);
            if (loyaltyProgram.equals(LoyaltyProgram.SNAPBOX) && loyaltyStatus.equals(LoyaltyStatus.ELIGIBLE)) {

                // Now let us process the request

                // Now process the emailIDs - make them eligible
                loyaltyUploadRs = processLoyaltyEligibilityUploadRq(LoyaltyProgram.SNAPBOX, uploadRq, loyaltyUploadRs);

            }
            else {
                log
                    .error("Unsupported loyalty program and/or status config for processing loyalty uploads. Pls check the request!");
            }
        }
        catch (Exception exception) {
            log.error(ErrorConstants.UNEXPECTED_ERROR.getMsg(), exception);
            loyaltyUploadRs = new LoyaltyUploadRs();
            validationService.addValidationError(loyaltyUploadRs, ErrorConstants.UNEXPECTED_ERROR);
        }

        return loyaltyUploadRs;
    }

    /**
     * Makes the emailIDs in the request, Loyalty program eligible if they are
     * registered and not already eligible/active.
     * 
     * @param emailIDs
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     */
    private LoyaltyUploadRs processLoyaltyEligibilityUploadRq(
        LoyaltyConstants.LoyaltyProgram loyaltyProgramEnum, LoyaltyUploadRq uploadRq, LoyaltyUploadRs loyaltyUploadRs)
        throws FileNotFoundException,
        IOException
    {

        LoyaltyUploadDO loyaltyUploadDO = null;

        if (loyaltyUploadRs == null) {
            loyaltyUploadRs = new LoyaltyUploadRs();
        }

        // String fileNameLoc = "/home/ashish/emailUploadFile.csv";

        List<String> emailIDs = getUniqueEntries(uploadRq.getFileContent());

        if (emailIDs.size() > MAX_ELIGIBILITY_EMAIL_COUNT_PER_UPLOAD) {
            loyaltyUploadRs.setSuccessful(false);
            log.error(ErrorConstants.MAX_ELIGIBILITY_EMAIL_COUNT_PER_UPLOAD_EXCEEDED.getMsg() + emailIDs.size()
                + " are present in the request!");
            return (LoyaltyUploadRs) validationService.addValidationError(loyaltyUploadRs,
                ErrorConstants.MAX_ELIGIBILITY_EMAIL_COUNT_PER_UPLOAD_EXCEEDED);
        }

        Map<String, UserPrerequisiteInfo> activeEmailIDUserIDNameMap, finalEligibleEmailIDUserIDMap;

        if (emailIDs == null || emailIDs.size() < 1) {
            loyaltyUploadRs.setSuccessful(false);
            validationService.addValidationError(
                loyaltyUploadRs, ErrorConstants.EMAIL_IDS_ABSENT_IN_REQ);

        }
        else {
            int rawEntriesCount = emailIDs.size();
            log.info("The uploaded file contains {} unique entries", rawEntriesCount);

            loyaltyUploadRs.setTotalEntriesCount(rawEntriesCount);

            // Step 1
            activeEmailIDUserIDNameMap = getActiveSnapDealEmailAndIDs(emailIDs);

            // WE NOW KNOW -INVALID- IDs
            emailIDs.removeAll(activeEmailIDUserIDNameMap.keySet());
            List<String> invalidEmailIDs = emailIDs;

            loyaltyUploadRs.setInvalidEmailIDs(invalidEmailIDs);
            loyaltyUploadRs.setInvalidCount(invalidEmailIDs.size());
            log.info("The uploaded file contains {} invalid entries", invalidEmailIDs.size());

            // Now let us process activeSnapDealEmailIDs
            List<String> existingLoyaltyEmailIDs = getExistingLoyaltyEmailIDs(activeEmailIDUserIDNameMap.keySet(),
                loyaltyProgramEnum);

            loyaltyUploadRs.setExistingLoyaltyEmails(existingLoyaltyEmailIDs);
            log.info("The uploaded file contains {} existing loyalty entries", existingLoyaltyEmailIDs.size());

            // We now know the -FINAL- list of email IDs to be made loyalty
            // eligible

            if (existingLoyaltyEmailIDs != null) {
                for (int i = 0; i < existingLoyaltyEmailIDs.size(); i++) {
                    activeEmailIDUserIDNameMap.remove(existingLoyaltyEmailIDs.get(i));
                }
            }

            finalEligibleEmailIDUserIDMap = activeEmailIDUserIDNameMap;

            // List<String> finalLoyaltyEligibleEMails = userEMailAndIDList;

            loyaltyUploadRs.setMadeEligibleCount(finalEligibleEmailIDUserIDMap.size());
            log.info("The uploaded file contains {} entries for which action will be taken.",
                finalEligibleEmailIDUserIDMap.size());

            byte[] fileContent = uploadRq.getFileContent();
            String fileName = uploadRq.getFileName();

            int validEmailCount = 0;
            if (finalEligibleEmailIDUserIDMap != null) {
                validEmailCount = finalEligibleEmailIDUserIDMap.size();
            }

            int existentEmailCount = 0;
            if (existingLoyaltyEmailIDs != null) {
                existentEmailCount = existingLoyaltyEmailIDs.size();
                loyaltyUploadRs.setExistentEmailCount(existentEmailCount);
            }

            int invalidEmailCount = invalidEmailIDs.size();

            loyaltyUploadDO = new LoyaltyUploadDO(fileName, fileContent, invalidEmailCount,
                validEmailCount, existentEmailCount, rawEntriesCount, LoyaltyUploadedFileStatus.RECIEVED);
            loyaltyUploadDao.save(loyaltyUploadDO);
            log.info("Finished saving details of the upload file in the db.");

            // ---Finally ----Loyalty program specific processing

            switch (loyaltyProgramEnum) {
            case SNAPBOX:
                snapBoxEnableEligibilityProcessor.processSnapBoxEligibilityRq(finalEligibleEmailIDUserIDMap);
                break;
            }

        }
        if (loyaltyUploadDO != null) {
            this.loyaltyUploadDao.updateStatus(loyaltyUploadDO.getId(), LoyaltyUploadedFileStatus.FINISHED_PROCESSING);
        }
        return loyaltyUploadRs;
    }

    /**
     * Returns existing loyalty customers in the database. Unless the emailID is
     * in-active/deleted (out of scope as of now, 28-April-2014) the email cant
     * be made eligible for the loyalty program
     * 
     * @param masterEmailIDs
     * @param loyaltyProgram
     * @return
     */
    private List<String> getExistingLoyaltyEmailIDs(Set<String> masterEmailIDs,
        LoyaltyConstants.LoyaltyProgram loyaltyProgram)
    {

        List<String> existingLoyaltyEmailIDs = new ArrayList<String>();

        List<String> tmpList = new ArrayList<String>(EMAIL_PROCESSING_BATCH_SIZE);

        List<String> tmpExistingLoyaltyUsers = null;

        int currentIndex = 0, endIndex = Math.min(currentIndex + EMAIL_PROCESSING_BATCH_SIZE - 1,
            masterEmailIDs.size() - 1);
        Iterator<String> iterator = masterEmailIDs.iterator();
        while (currentIndex <= masterEmailIDs.size() - 1) {

            for (; currentIndex <= endIndex; currentIndex++) {
                tmpList.add(iterator.next());

            }

            tmpExistingLoyaltyUsers =
                loyaltyUserDtlsDao.getExistingLoyaltyEmailIDs(tmpList,
                    loyaltyProgramStatusManager.getLoyaltyProgram(loyaltyProgram));

            if (tmpExistingLoyaltyUsers != null) {
                existingLoyaltyEmailIDs.addAll(tmpExistingLoyaltyUsers);
            }

            tmpList.clear();
            // Break away from the WHILE LOOP once all the IDs in masterEmailIDs
            // are processed!
            if (endIndex == masterEmailIDs.size() - 1) {
                break;
            }
            endIndex = Math.min(currentIndex + EMAIL_PROCESSING_BATCH_SIZE - 1, masterEmailIDs.size() - 1);
        }

        return existingLoyaltyEmailIDs;

    }

    /**
     * Returns those emailIDs which are registered with SNAPDEAL
     * 
     * @param masterEmailIDs
     * @return
     */
    Map<String, UserPrerequisiteInfo> getActiveSnapDealEmailAndIDs(List<String> masterEmailIDs)
    {

        Map<String, UserPrerequisiteInfo> activeSDUserEmailIDUserIDMap = new HashMap<String, UserPrerequisiteInfo>();

        if (masterEmailIDs == null || masterEmailIDs.size() == 0) {
            return activeSDUserEmailIDUserIDMap;
        }
        // masterEmailIDs could run into 1L +
        // Let us call usersDao.getActiveSDUsers(masterEmailIDs) in batches!
        List<Object[]> finalActiveSnapDealEmailAndIDs = new ArrayList<Object[]>();

        List<String> tmpList = new ArrayList<String>(EMAIL_PROCESSING_BATCH_SIZE);

        int currentIndex = 0, endIndex = Math.min(currentIndex + EMAIL_PROCESSING_BATCH_SIZE - 1,
            masterEmailIDs.size() - 1);

        while (currentIndex <= masterEmailIDs.size() - 1) {

            for (; currentIndex <= endIndex; currentIndex++) {
                tmpList.add(masterEmailIDs.get(currentIndex));

            }

            List<Object[]> tempActiveSnapDealEmailAndIDs = usersDao.getActiveSDUserIDNameEmail(tmpList);

            if (tempActiveSnapDealEmailAndIDs != null) {
                finalActiveSnapDealEmailAndIDs.addAll(tempActiveSnapDealEmailAndIDs);
            }

            tmpList.clear();
            // Break away from the WHILE LOOP once all the IDs in masterEmailIDs
            // are processed!
            if (endIndex == masterEmailIDs.size() - 1) {
                break;
            }
            endIndex = Math.min(currentIndex + EMAIL_PROCESSING_BATCH_SIZE - 1, masterEmailIDs.size() - 1);

        }
        if (finalActiveSnapDealEmailAndIDs != null)
        {
            Iterator<Object[]> iterator = finalActiveSnapDealEmailAndIDs.iterator();

            while (iterator.hasNext()) {
                // Object[0] = ID, Object[1]=email for the users in request
                // which are registered with
                // Snapdeal.
                Object[] tmpUserEMailAndID = iterator.next();

                activeSDUserEmailIDUserIDMap.put((String) tmpUserEMailAndID[1], new UserPrerequisiteInfo(
                    (Integer) tmpUserEMailAndID[0], (String) tmpUserEMailAndID[2], null));

            }

        }

        // TODO:FOR_LOCAL_TESTING - when locally LOAD TESTING, enable the below
        // code so that the user emailIDs need not be present in the user table!
        // activeSDUserEmailIDUserIDMap = new HashMap<String,
        // LoyaltyUploadManager.UserPrerequisiteInfo>();
        // int counter = 1;
        // for (String s : masterEmailIDs) {
        // activeSDUserEmailIDUserIDMap.put(s, new
        // UserPrerequisiteInfo(counter++, s.toUpperCase().substring(0, 5),
        // null));
        // }
        // -----testing stub over

        return activeSDUserEmailIDUserIDMap;

    }

    // Reads the file from the inputStream and returns email addresses in it
    private List<String> getUniqueEntries(byte[] fileContent) throws IOException
    {

        if (fileContent == null) {
            return null;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(fileContent)));

        // Using linked list to handle scaling issues. The input could be a few
        // hundred email IDs or a lakh +
        Set<String> uniquieEntries = new HashSet<String>();

        try {

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                String trimmedStringEntry = sCurrentLine.trim();
                if (trimmedStringEntry.length() > 0) {
                    uniquieEntries.add(trimmedStringEntry);
                }
            }

        }
        finally {
            try {
                if (br != null)
                    br.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return new ArrayList<String>(uniquieEntries);

    }

    /**
     * FOR local testing: Method for creating a lot of emails to test the code
     * with. Not deleting as it could be re-used when scaling/working with the module
     * again!
     */
    // TODO:FOR_LOCAL_TESTING
    private static void writeEmailUploadFile()
    {

        FileOutputStream fop = null;
        File file;
        String content = "emailID123456789_";

        try {
            file = new File("/home/abc/emailUploadFile.csv");
            fop = new FileOutputStream(file);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes

            for (int i = 1; i <= 100000; i++) {
                byte[] contentInBytes = (content + i + "@snapdeal.com\n").getBytes();
                fop.write(contentInBytes);
            }
            fop.flush();
            fop.close();

            System.out.println("Done");

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
