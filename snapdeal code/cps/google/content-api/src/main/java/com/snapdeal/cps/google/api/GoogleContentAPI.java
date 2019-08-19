package com.snapdeal.cps.google.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.xml.atom.AtomContent;
import com.google.api.client.xml.XmlNamespaceDictionary;
import com.snapdeal.cps.google.atom.GoogleShoppingItem;
import com.snapdeal.cps.google.atom.ProductFeed;
import com.snapdeal.cps.google.atom.SubAccount;
import com.snapdeal.cps.google.oauth.GoogleOAuth;

/**
 * This class defines wrapper methods for making API call to the Google Content
 * API/Google Merchant Center It exposes the calls via well defined methods and
 * encapsulates all the details of making OAuth authenticated HTTP requests
 * 
 * This assumes that Google OAuth has already been setup.
 * 
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 * 
 */
public class GoogleContentAPI {

    private GoogleOAuth gOAuth;
    private Properties apiProperties;
    private String requestBaseUrl;

    private XmlNamespaceDictionary namespaceDictionary;

    private String path;

    /**
     * This private method initializes the Content API request
     * 
     * @param props
     *            : A Properties object
     */
    private void initializeRequestProperties(Properties props) {
        try {
            apiProperties = props;
            gOAuth = new GoogleOAuth();
            requestBaseUrl = apiProperties.getProperty("BASE_URL");

            namespaceDictionary = new XmlNamespaceDictionary().set("", "http://www.w3.org/2005/Atom")
                    .set("app", "http://www.w3.org/2007/app").set("gd", "http://schemas.google.com/g/2005")
                    .set("sc", "http://schemas.google.com/structuredcontent/2009")
                    .set("scp", "http://schemas.google.com/structuredcontent/2009/products")
                    .set("xml", "http://www.w3.org/XML/1998/namespace");

        } catch (Exception e) {
            System.out.println("Unable to initialize API request properties. Exiting with failure.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * This constructor loads the properties file and calls the
     * <i>initializeRequestProperties</i> method
     * 
     */
    public GoogleContentAPI() {
        Properties props = new Properties();
        try {
            props.load(GoogleContentAPI.class.getClassLoader().getResourceAsStream("api.properties"));
            initializeRequestProperties(props);
        } catch (FileNotFoundException e) {
            System.out.println("Initialization Faliure: Unable to find properties file");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Initialization Faliure: Unable to read properties file");
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            System.out.println("Unknown Exception while initializing api");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String getAPIProperty(String key) {
        return apiProperties.getProperty(key);
    }

    public String getRequestBaseUrl() {
        return this.requestBaseUrl;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public XmlNamespaceDictionary getNamespaceDictionary() {
        return this.namespaceDictionary;
    }

    /**
     * This method creates a bare authorized request
     * 
     * @return A bare HTTP request with a valid authorization token
     * @throws Exception
     */
    private HttpRequest createBareRequestFactory() throws Exception {
        HttpRequestFactory requestFactory = gOAuth.getHttpTransport().createRequestFactory();
        HttpRequest bareRequest = requestFactory.buildRequest(null, null, null);
        bareRequest.setHeaders(new HttpHeaders().setAuthorization("Bearer " + gOAuth.getAccessToken()));
        return bareRequest;
    }

    /**
     * This method takes a request path, appends it to the base url, builds and
     * returns a HTTP GET request
     * 
     * @param requestPath
     * @return An Authorized HTTP GET Request
     * @throws Exception
     */
    public HttpRequest buildGetRequest(String requestPath) throws Exception {
        String requestUrl = getRequestBaseUrl() + requestPath;
        HttpRequest httpRequest = createBareRequestFactory();
        httpRequest.setRequestMethod("GET");
        httpRequest.setUrl(new GenericUrl(requestUrl));
        return httpRequest;
    }

    /**
     * @param requestPath
     *            : Will be appended to the base url to complete the URL
     * @param content
     *            : Atom Content which needs to be posted to the GMC
     * @return An authorized HTTP POST request
     * @throws Exception
     */
    public HttpRequest buildPostRequest(String requestPath, AtomContent content) throws Exception {
        String requestUrl = getRequestBaseUrl() + requestPath;
        HttpRequest httpRequest = createBareRequestFactory();
        httpRequest.setRequestMethod("POST");
        httpRequest.setContent(content);
        httpRequest.setUrl(new GenericUrl(requestUrl));

        return httpRequest;
    }

    /**
     * @param requestPath
     *            : Will be appended to the base url to complete the URL
     * @param content
     *            : Atom Content which needs to be updated on the GMC
     * @return An authorized HTTP PUT request
     * @throws Exception
     */
    public HttpRequest buildPutRequest(String requestPath, AtomContent content) throws Exception {
        String requestUrl = getRequestBaseUrl() + requestPath;
        HttpRequest httpRequest = createBareRequestFactory();
        httpRequest.setRequestMethod("PUT");
        httpRequest.setContent(content);
        httpRequest.setUrl(new GenericUrl(requestUrl));

        return httpRequest;
    }

    /**
     * @param requestPath
     *            : Will be appended to the base url to complete the URL
     * @return An authorized HTTP DELETE request
     * @throws Exception
     */
    public HttpRequest buildDeleteRequest(String requestPath) throws Exception {
        String requestUrl = getRequestBaseUrl() + requestPath;
        HttpRequest httpRequest = createBareRequestFactory();
        httpRequest.setRequestMethod("DELETE");
        httpRequest.setUrl(new GenericUrl(requestUrl));

        return httpRequest;
    }

    /**
     * This method makes an API Call for fetching a single product corresponding
     * to a sub account
     * 
     * @param vendorId
     *            : Google sub account ID
     * @param productId
     *            : Id of the product
     * @return A HttpResponse object
     * @throws Exception
     */

    public HttpResponse getSingleProduct(String vendorId, String productId) throws Exception {
        String requestPath = vendorId + this.getAPIProperty("GET_SINGLE_ITEM").replace("PRODUCT_ID", productId);
        HttpRequest httpRequest = buildGetRequest(requestPath);
        return httpRequest.execute();
    }

    /**
     * This method makes an API Call for fetching a all products corresponding
     * to a sub account
     * 
     * @param vendorId
     *            : Google sub account ID
     * @param maxResults
     *            : Maximum number of results (cannot be more than 250)
     * @return A HttpResponse object
     * @throws Exception
     */

    public HttpResponse getProducts(String vendorId, int maxResults) throws Exception {
        if (maxResults > 250) {
            maxResults = 250;
        }

        String requestPath = vendorId + this.getAPIProperty("GET_ALL_ITEMS") + "?max-results=" + maxResults;
        HttpRequest httpRequest = buildGetRequest(requestPath);
        return httpRequest.execute();
    }

    /**
     * This method makes an API Call for inserting a single product
     * corresponding to a sub account
     * 
     * @param vendorId
     *            : Google sub account ID for which the insert has to be made
     * @param googleShoppingItem
     *            : A Product object
     * @param enableDryRun
     *            : if true, the method makes a test call
     * @return A HttpResponse object
     * @throws Exception
     */
    public HttpResponse insertSingleProduct(String vendorId, GoogleShoppingItem googleShoppingItem, boolean enableDryRun) throws Exception {
        String requestPath = vendorId + this.getAPIProperty("INSERT_SINGLE_ITEM");
        if (enableDryRun) {
            requestPath = requestPath + "?dry-run";
        }

        // Create Atom Content
        AtomContent atomContent = AtomContent.forEntry(this.getNamespaceDictionary(), googleShoppingItem);
        HttpRequest httpRequest = buildPostRequest(requestPath, atomContent);
        return httpRequest.execute();
    }

    /**
     * This method makes an API Call for updating a single product corresponding
     * to a sub account
     * 
     * @param vendorId
     *            : Google sub account ID
     * @param googleShoppingItem
     *            : A Product object
     * @param enableDryRun
     *            : if true, the method makes a test call
     * @return A HttpResponse object
     * @throws Exception
     */

    public HttpResponse updateSingleProduct(String vendorId, GoogleShoppingItem googleShoppingItem, boolean enableDryRun) throws Exception {
        String requestPath = vendorId
                + this.getAPIProperty("UPDATE_SINGLE_ITEM").replace("PRODUCT_ID", googleShoppingItem.getGoogleEntryID());
        if (enableDryRun) {
            requestPath = requestPath + "?dry-run";
        }

        // Create Atom Content
        AtomContent atomContent = AtomContent.forEntry(this.getNamespaceDictionary(), googleShoppingItem);
        HttpRequest httpRequest = buildPutRequest(requestPath, atomContent);
        return httpRequest.execute();
    }

    /**
     * This method makes an API Call for deleting a single product corresponding
     * to a sub account
     * 
     * @param vendorId
     *            : Google sub account ID for which the insert has to be made
     * @param productId
     *            : A product Id which needs to be deleted
     * @param enableDryRun
     *            : if true, the method makes a test call
     * @return A HttpResponse object
     * @throws Exception
     */
    public HttpResponse deleteSingleProduct(String vendorId, String productId, boolean enableDryRun) throws Exception {
        String requestPath = vendorId + this.getAPIProperty("DELETE_SINGLE_ITEM").replace("PRODUCT_ID", productId);
        if (enableDryRun) {
            requestPath = requestPath + "?dry-run";
        }

        HttpRequest httpRequest = buildDeleteRequest(requestPath);
        return httpRequest.execute();

    }

    /**
     * This method makes an API Call for batch operations under a sub account
     * 
     * @param vendorId
     *            : Google sub account ID for which the batch operation has to
     *            be made
     * @param productFeed
     *            : A ProductFeed object
     * @param enableDryRun
     *            : if true, the method makes a test call
     * @return A HttpResponse object
     * @throws Exception
     */
    public HttpResponse executeProductBatchOperations(String vendorId, ProductFeed productFeed, boolean enableDryRun)
            throws Exception {
        String requestPath = vendorId + this.getAPIProperty("ITEMS_BATCH_OPERATION");
        if (enableDryRun) {
            requestPath = requestPath + "?dry-run";
        }

        // Create Atom Content
        AtomContent atomContent = AtomContent.forFeed(
                this.getNamespaceDictionary().set("batch", "http://schemas.google.com/gdata/batch"), productFeed);
        HttpRequest httpRequest = buildPostRequest(requestPath, atomContent);
        return httpRequest.execute();
    }

    /**
     * This method creates a sub account under the GMC main account The sub
     * account ID can be extracted from the HttpResponse object which is
     * returned
     * 
     * @param vendorName
     *            : Vendor Name for which the sub account has to be created
     * @return A HttpResponse Object
     * @throws Exception
     */
    public HttpResponse createSubAccount(String vendorName) throws Exception {
        String requestPath = this.getAPIProperty("SNAPDEAL_MASTER_ACCOUNT") + this.getAPIProperty("CREATE_SUBACCOUNT");

        // Create Atom Content
        AtomContent atomContent = AtomContent.forEntry(this.getNamespaceDictionary(), new SubAccount(vendorName));
        HttpRequest httpRequest = buildPostRequest(requestPath, atomContent);
        return httpRequest.execute();
    }

    /**
     * This method updates a sub account under the GMC main account
     * 
     * @param vendorId
     *            : VendorId for which the details have to be updated
     * @param subAccount
     *            : A SubAccount object with the required data
     * @return A HttpResponse Object
     * @throws Exception
     */
    public HttpResponse updateSubAccount(String vendorId, SubAccount subAccount) throws Exception {
        String requestPath = this.getAPIProperty("SNAPDEAL_MASTER_ACCOUNT")
                + this.getAPIProperty("UPDATE_SUBACCOUNT").replace("SUBACCOUNT_ID", vendorId);

        // Create Atom Content
        AtomContent atomContent = AtomContent.forEntry(this.getNamespaceDictionary(), subAccount);
        HttpRequest httpRequest = buildPutRequest(requestPath, atomContent);
        return httpRequest.execute();
    }

    /**
     * This method deletes a sub account under the GMC main account. Use this
     * method with caution. Deleting a sub account deletes all the products in
     * it.
     * 
     * @param vendorId
     *            : VendorId which has to be deleted
     * @return A HttpResponse Object
     * @throws Exception
     */
    public HttpResponse deleteSubAccount(String vendorId) throws Exception {
        String requestPath = this.getAPIProperty("SNAPDEAL_MASTER_ACCOUNT")
                + this.getAPIProperty("DELETE_SUBACCOUNT").replace("SUBACCOUNT_ID", vendorId);

        // Create Request
        HttpRequest httpRequest = buildDeleteRequest(requestPath);
        return httpRequest.execute();
    }

    /**
     * This method get details of the specified sub account
     * 
     * @param vendorId
     * @return A HttpResponse object
     * @throws Exception
     */
    public HttpResponse getSingleSubAccount(String vendorId) throws Exception {
        String requestPath = this.getAPIProperty("SNAPDEAL_MASTER_ACCOUNT")
                + this.getAPIProperty("GET_SINGLE_SUBACCOUNT").replace("SUBACCOUNT_ID", vendorId);

        // Create Request
        HttpRequest httpRequest = buildGetRequest(requestPath);
        return httpRequest.execute();
    }

    /**
     * This method retrieves all the information for subaccounts
     * 
     * @param maxResults
     *            : number of sub accounts in one call (cannot be more than 100)
     * @return A HttpResponse Object
     * @throws Exception
     */
    public HttpResponse getSubAccounts(int maxResults) throws Exception {
        if (maxResults > 100) {
            maxResults = 100;
        }

        String requestPath = this.getAPIProperty("SNAPDEAL_MASTER_ACCOUNT")
                + this.getAPIProperty("GET_ALL_SUBACCOUNTS") + "?max-results=" + maxResults;

        // Create Request
        HttpRequest httpRequest = buildGetRequest(requestPath);
        return httpRequest.execute();
    }

}
