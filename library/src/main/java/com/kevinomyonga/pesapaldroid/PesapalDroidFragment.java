package com.kevinomyonga.pesapaldroid;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.kevinomyonga.pesapaldroid.exception.PesapalException;
import com.kevinomyonga.pesapaldroid.ipn.DefaultIpnRequest;
import com.kevinomyonga.pesapaldroid.post.PostRequest;

/**
 * Created by Kevin Omyonga on 12/1/2015.
 */
public class PesapalDroidFragment extends Fragment {

    private static final boolean DEBUG = true;
    private static final String TAG = PesapalDroidFragment.class.getSimpleName();
    private static final String HTTP_ERROR = "<html><body><h1>OOps!!</h1><b><p>%s</p></body></html>";

    /**
     * Consumer Key used to identify the application.
     */
    private static String mConsumer_key;//"change_with_your_consumer_key";

    /**
     * Consumer Secret used to identify the application.
     */
    private static String mConsumer_secret;//change_with_your_consumer_secret";

    /**
     * Flag indicating that demo API is enabled\disabled. Default is set to true.
     */
    private boolean is_demo = true;

    /**
     * Flag indicating that mobile ui mode is enabled\disabled. Default is set to true.
     */
    private boolean is_mobile = true;

    String fName, lName, email, phone, desc, amount;

    private PostRequest request;
    private WebViewClient webViewClient = new WebViewClient(){

        /* (non-Javadoc)
         * @see android.webkit.WebViewClient#onReceivedError(android.webkit.WebView, int, java.lang.String, java.lang.String)
         */
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            loadingView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
            txtErrorMsg.setText("Error: " + description);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            portalView.setVisibility(View.GONE);
            loadingView.setVisibility(View.VISIBLE);
            swipeContainer.setRefreshing(false);

            if(DEBUG)
                Log.d(TAG, "onPageStarted:" + url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(DEBUG) Log.d(TAG, "override : " + url);

            String callback  = request.getCallback();
            if(url.startsWith(callback )){
                try {
					/*
					Map<String,String> map = IpnUtil.getIpnParamsFromUrl(url);
					String id = map.get(AbIpnRequest.PARAM_TARCK_ID);
					String reference = map.get(AbIpnRequest.PARAM_MERCHANT_REFERECE);
					if(DEBUG){
						Log.d(Pesapal.TAG, "Reference "+reference);
					}
					*/
                    IRequest ipn;
                    //ipn = new IpnRequestStatus(id,reference);
                    //ipn = new IpnRequestStatusDetail(id,reference);
                    //ipn = new IpnRequestStatusByMerchatRef(reference);
                    ipn = new DefaultIpnRequest(url); // only if no change the default callback!! otherwise not work
                    view.loadUrl(ipn.getURL());
                } catch (PesapalException e) {
                    String msg = String.format(HTTP_ERROR, e.getMessage());
                    view.loadData(msg, "text/html", null);
                }
                return true;
            }

            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (errorView.getVisibility() == View.VISIBLE) {
                portalView.setVisibility(View.GONE);
            } else {
                portalView.setVisibility(View.VISIBLE);
            }
            loadingView.setVisibility(View.GONE);
        }

    };

    WebView webView;
    View portalView, loadingView, errorView;
    SwipeRefreshLayout swipeContainer;

    TextView txtErrorMsg;
    Button btnRetry;

    public PesapalDroidFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Pesapal.initialize(getConsumerKey(), getConsumerSecret());
        Pesapal.setDEMO(isDemoEnabled());

        portalView = view.findViewById(R.id.portal);
        loadingView = view.findViewById(R.id.loading);
        errorView = view.findViewById(R.id.error);

        loadingView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);

        txtErrorMsg = (TextView) view.findViewById(R.id.txtErrorMsg);
        btnRetry = (Button) view.findViewById(R.id.btnRetry);

        webView = (WebView) view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(webViewClient);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.portalSwipeContainer);
        // Setup refresh listener which triggers portal reload
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the portal here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                loadPortal();
            }
        });

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorView.setVisibility(View.GONE);
                loadPortal();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPortal();
    }

    public void loadPortal() {
        request = createPostRequest();

        try {
            String url = request.getURL();
            webView.loadUrl(url);
        } catch (PesapalException e) {
            String msg = String.format(HTTP_ERROR, e.getMessage());
            webView.loadData(msg, "text/html", null);
        }
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PesapalDroidFragment);
        mConsumer_key = typedArray.getString(R.styleable.PesapalDroidFragment_consumer_key);
        mConsumer_secret = typedArray.getString(R.styleable.PesapalDroidFragment_consumer_secret);

        is_demo = typedArray.getBoolean(R.styleable.PesapalDroidFragment_is_demo, true);
        is_mobile = typedArray.getBoolean(R.styleable.PesapalDroidFragment_is_mobile, true);

        typedArray.recycle();
    }

    /**
     * Set the {Consumer Key} that this fragment will use.
     *
     * @param consumer_key Consumer Key to use
     */
    public void setConsumerKey(String consumer_key) {
        mConsumer_key = consumer_key;
    }

    /**
     * @return The consumer key
     */
    public String getConsumerKey() {
        return mConsumer_key;
    }

    /**
     * Set the {Consumer Secret} that this fragment will use.
     *
     * @param consumer_secret Consumer Secret to use
     */
    public void setConsumerSecret(String consumer_secret) {
        mConsumer_secret = consumer_secret;
    }

    /**
     * @return The consumer secret
     */
    public String getConsumerSecret() {
        return mConsumer_secret;
    }

    /**
     * Set demo API enabled flag
     * @param enabled flag value
     */
    public void setDemoEnabled(boolean enabled) {
        is_demo = enabled;
    }

    /**
     * @return true if demo API has been enabled.
     */
    public boolean isDemoEnabled() {
        return is_demo;
    }

    /**
     * Set mobile ui mode enabled flag
     * @param enabled flag value
     */
    public void setMobileEnabled(boolean enabled) {
        is_mobile = enabled;
    }

    /**
     * @return true if mobile ui mode has been enabled.
     */
    public boolean isMobileEnabled() {
        return is_mobile;
    }

    /**
     * Create the payment request
     * Use the builder to create the request
     */
    private PostRequest createPostRequest() {
        PostRequest.Builder builder = new PostRequest.Builder();

        //	Can take it from editText or where there you want
        builder
                .isMobile(isMobileEnabled())
                .amount(getAmount())
                .description(getDesc())
                .phone(getPhone())
                .mail(getEmail())
                .name(getfName(), getlName());

        return builder.build();
    }

    /**
     * Set the {First Name} that this fragment will use.
     *
     * @param fName First Name to use
     */
    public void setfName(String fName) {
        this.fName = fName;
    }

    /**
     * @return The First Name
     */
    public String getfName() {
        return fName;
    }

    /**
     * Set the {Last Name} that this fragment will use.
     *
     * @param lName Last Name to use
     */
    public void setlName(String lName) {
        this.lName = lName;
    }

    /**
     * @return The Last Name
     */
    public String getlName() {
        return lName;
    }

    /**
     * Set the {Email} that this fragment will use.
     *
     * @param email Last Name to use
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The email of the user making the payment
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the {Phone Number} that this fragment will use.
     *
     * @param phone Phone number of the user making the payment
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return The phone number of the user making the payment
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set the {Description} of the transaction
     *
     * @param desc Description of the transaction
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return The description of the transaction
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Set the {Amount} that this fragment will use.
     *
     * @param amount Amount to be paid
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return The amount to be paid
     */
    public String getAmount() {
        return amount;
    }
}
