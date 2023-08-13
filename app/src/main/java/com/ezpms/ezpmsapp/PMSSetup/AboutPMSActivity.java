package com.ezpms.ezpmsapp.PMSSetup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezpms.ezpmsapp.R;

public class AboutPMSActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView Tv_Title, aboutPMS_Tv;
    private ImageView backMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_p_m_s);
        mContext = this;

        getLayouUiId();
        setTextOfAbout();

        backMenu.setOnClickListener(this);
        Tv_Title.setText("About PMS");
    }

    private void setTextOfAbout() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                aboutPMS_Tv.setText(Html.fromHtml("About Vatech EzPMS\n" +
                        "     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br />\n" +
                        "        Version <strong>1.10</strong> - Updated 02022020<br /> <br /> <strong>1. Introduction</strong><br /> Vatech India Pvt. Ltd.\n" +
                        "        (&quot;Company&quot; &quot;us,&quot; &quot;we,&quot; or &quot;Vatech India PMS&quot;) is the owner of Vatech India Practice Management System for\n" +
                        "        Dental Professionals that works on Windows Operating System and on Android mobile apps platform as Vatech India PMS App - referred individually\n" +
                        "        is committed to respecting the privacy rights of our customers, visitors, and other users of Vatech India PMS on applications, services and mobile\n" +
                        "        applications provided by Vatech India PMS and on/in which this Privacy Policy is posted or referenced (collectively, the &quot;Services&quot;).&nbsp;&nbsp;&nbsp;<br /> We created this Privacy Policy to assure and give you the confidence as you use the Services and also to demonstrate our commitment\n" +
                        "        to the protection of privacy.<br /> This Privacy Policy is only applicable to the Services. This Privacy Policy does not apply to any other website or\n" +
                        "        digital service that you may be able to access the Services or any website or digital services of Vatech India PMS&#39;s business partners, each of which\n" +
                        "        may have data collection, storage and use practices and policies that may materially differ from this Privacy Policy.<br /> Your use of the Services is\n" +
                        "        governed by this Privacy Policy and the Agreement (as the term &quot;Agreement&quot; is defined in our Terms of Use). Any capitalized term used but not\n" +
                        "        defined in this Privacy Policy shall have the meaning in the Agreement.<br /> <br /> BY USING THE SERVICES, YOU AGREE TO THE PRACTICES AND POLICIES OUTLINED\n" +
                        "        IN THIS PRIVACY POLICY, AND YOU HEREBY CONSENT TO THE COLLECTION, USE, AND SHARING OF YOUR INFORMATION AS DESCRIBED IN THIS PRIVACY POLICY.&nbsp;<br />\n" +
                        "        IF YOU DO NOT AGREE WITH THIS PRIVACY POLICY, YOU CANNOT USE THE SERVICES. IF YOU USE THE SERVICES ON BEHALF OF SOMEONE ELSE (SUCH AS YOUR CHILD) OR AN\n" +
                        "        ENTITY (SUCH AS YOUR EMPLOYER), YOU REPRESENT THAT YOU ARE AUTHORIZED BY SUCH INDIVIDUAL OR ENTITY TO ACCEPT THIS PRIVACY POLICY ON SUCH INDIVIDUAL&#39;S\n" +
                        "        OR ENTITY&#39;S BEHALF.<br /> <br /> <strong>Privacy Policy</strong><br /> <strong>2. Information We Collect</strong><br /> <strong>2.1 Personal\n" +
                        "            Information<br /> 2.1.1 Personal Information Generally</strong><br /> Some of the Services require us to know, so that we can best meet your needs.\n" +
                        "        When you access these Services, we may ask you to voluntarily provide us certain information that personally identifies (or could be used to determine\n" +
                        "        individually) you (&quot;Personal Information&quot;). Personal Information includes but is not limited to the following categories\n" +
                        "        of information:&nbsp;<br /> a. Demographic information (such as your gender, your date of birth and your zip code);&nbsp;<br /> b.\n" +
                        "        Contact data (such as your e-mail address and phone number);&nbsp;<br /> c. Medical data (such as the doctors, dentists or other health care\n" +
                        "        providers (&quot;Healthcare Providers&quot;) you have visited, your reasons for visit, your dates of visit, your medical history, and other medical\n" +
                        "        and health information you choose to share with us), and&nbsp;<br /> d. In future there might be an option of uploading health / medical information\n" +
                        "        on the Website in order to enable your Administrator / Doctor or Health Care Specialist to access your previous medical history. While Vatech India PMS\n" +
                        "        takes utmost care of the security of the information you decide to upload, you understand that any information that you disclose on the PMS is at your risk.\n" +
                        "        By uploading/sharing / disclosing your medical information on the PMS, you with this give your consent to Vatech India PMS to store such health / medical\n" +
                        "        information on Vatech India PMS;s Servers. Vatech India PMS will retain such medical / health information you upload on the Servers, for as long as it is\n" +
                        "        needed to fulfil the service you seek to avail on the Website. If you delete your account, Vatech India PMS will delete such medical /\n" +
                        "        health information.&nbsp;<br /> <strong>2.2 Traffic Data</strong><br /> We also may automatically collect certain data when you use the Services, such\n" +
                        "        as (1) IP address; (2) domain server; (3) type of device(s) used to access the Services; (4) web browser(s) used to access the Services; We may also\n" +
                        "        collect additional information, which may be Personal Information, as otherwise described to you at the point of collection or according to your\n" +
                        "        consent.<br /> <br /> <strong>3. How We Collect Information</strong><br /> We collect information (including Personal Information and Traffic Data)\n" +
                        "        when you use and interact with the Services, and in some cases from third party sources.&nbsp;<br /> <br /> <strong>4. Tracking Tools and &quot;Do Not\n" +
                        "            Track.&quot;</strong><br /> <strong>4.1. Tracking Tools</strong><br /> We may use tools outlined below to better understand users.<br /> Cookies:\n" +
                        "        Cookies are small computer files transferred to your computing device that contain information such as user ID, user preferences, lists of pages visited\n" +
                        "        and activities conducted while using the Services. We use Cookies to help us improve or tailor the Services by tracking your navigation habits, storing\n" +
                        "        your authentication status, so you do not have to re-enter your credentials each time you use the Services, customizing your experience with the Services\n" +
                        "        and for analytics and fraud prevention.<br /> For more information on cookies, visit http://www.allaboutcookies.org.<br /> Analytics: We may use third-party\n" +
                        "        software analytics services in connection with the Services, including, for example, to register mouse clicks, mouse movements, scrolling activity and\n" +
                        "        text that you type into the Site. This website analytics services do not collect Personal Information unless you voluntarily provide it and do not\n" +
                        "        track your browsing habits across websites which do not use their services. We use the information gathered from these services to help make the\n" +
                        "        Services easier to use and as otherwise outlined in Section 6 (Use of Information).<br /> Mobile Device Identifiers: Mobile device identifiers are\n" +
                        "        data stored on your mobile device that may track mobile device and data and activities occurring on and through it, as well as the applications\n" +
                        "        installed on it. Mobile device identifiers enable collection of Personal Information (such as media access control, address, and location) and Traffic\n" +
                        "        Data. As with other Tracking Tools, mobile device identifiers help Vatech India PMS learn more about our users demographics and Internet behaviors.<br />\n" +
                        "        <strong>4.2. Options for Opting out of Cookies and Mobile Device Identifiers</strong><br /> Some web browsers (including some mobile web browsers) allow\n" +
                        "        you to reject Cookies or to alert you when a Cookie is placed on your computer, tablet or mobile device. You may be able to reject mobile device identifiers\n" +
                        "        by activating the appropriate setting on your mobile device. Although you are not required to accept Vatech India PMSs Cookies or mobile device identifiers,\n" +
                        "        if you block or reject them, you may not have access to all features available through the Services.<br /> <br />\n" +
                        "        <strong>5. Use of Information</strong><br /> We use your information, including Personal Information, to provide the Services to you and to help\n" +
                        "        improve them, including to:<br /> a. Provide you with the products, services and information you request and respond to correspondence that we\n" +
                        "        receive from you;<br /> b. Provide, maintain, administer or expand the Services, perform business analyses, or for other internal purposes\n" +
                        "        to support, improve or enhance our business, the Services, and other products and services we offer;<br /> c. Notify you about certain\n" +
                        "        resources or Healthcare Providers we think you may be interested in learning more about;<br /> &nbsp;&nbsp; &nbsp;d. Send you information\n" +
                        "        about Vatech India PMS or our products or Services;<br /> e. Contact you when necessary or requested, including to remind you of an upcoming\n" +
                        "        appointment;<br /> f. Customize and tailor your experience of the Services, which may include sending personalized messages or showing you Sponsored\n" +
                        "        Results that may be of interest to you based on information collected by this Privacy Policy;<br /> g. Send emails, and other communications that display\n" +
                        "        content that we think will interest you and according to your preferences;<br /> h. Combine information received from third parties with the information\n" +
                        "        that we have from or about you and use the combined information for any of the purposes described in this Privacy Policy;<br /> i. Use statistical data\n" +
                        "        that we collect in any way permitted by law, including from third parties in connection with their commercial and marketing efforts; and<br /> j. Prevent,\n" +
                        "        detect and investigate security breaches and potentially illegal or prohibited activities.<br /> We may use information that is neither Personal Information\n" +
                        "        nor PHI (including non-PHI Personal Information that has been de-identified and aggregated) to understand better who uses Vatech India PMS and how we can\n" +
                        "        deliver a better healthcare experience.<br /> <br /> <strong>6. Disclosure of Information</strong><br /> We do not disclose any information or sell email\n" +
                        "        addresses to third parties.<br /> We store and process your information on our private cloud servers in the Cloud (AWS)<br /> <br /> <strong>7. Public\n" +
                        "            Information</strong><br /> Any information that you may reveal in a review posting or online discussion or forum is intentionally open to the public\n" +
                        "        and is not in any way private. We recommend that you carefully consider whether to disclose any Personal Information in any public posting or forum. What\n" +
                        "        you have written may be seen and collected by third parties and may be used by others in ways we are unable to control or predict.<br /> <br /> <strong>8.\n" +
                        "            Storage and Security of Information</strong><br /> The security of your Personal Information is important to us. We endeavor to follow accepted industry\n" +
                        "        standards to protect the Personal Information submitted to us, both during transmission and in storage.<br /> Although we make good faith efforts to store\n" +
                        "        Personal Information in a secure operating environment that is not open to the public, we do not and cannot guarantee the security of your Personal\n" +
                        "        Information. If we become aware that your Personal Information has been disclosed in a manner, not by this Privacy Policy, we will use reasonable efforts\n" +
                        "        to notify you of the nature and extent of the disclosure (to the extent we know that information) as soon as reasonably possible and as permitted or\n" +
                        "        required by law.<br /> <br /> <strong>9. Controlling Your Personal Information</strong><br /> If you are a registered user of the Services, you can modify\n" +
                        "        some of the Personal Information you have included in your profile or change your username by logging in and accessing your account. If you wish to close\n" +
                        "        your account, please email us at contactus@VatechIndia.in<br /> Vatech India PMS will delete your account and the related information at your request as\n" +
                        "        soon as reasonably possible. Please note, however, that Vatech India PMS reserves the right to retain information from closed accounts, including complying\n" +
                        "        with the law, preventing fraud, resolving disputes, enforcing the Agreement and taking other actions permitted by law.<br /> You must promptly notify us if\n" +
                        "        any of your account data is lost, stolen or used without permission.<br /> <br /> <strong>10. Links to Other Websites</strong><br /> The Services contain\n" +
                        "        links to third party websites with which Vatech India PMS has no affiliation.&nbsp;<br /> <br /> <strong>11. Updates and Changes to Privacy Policy</strong>\n" +
                        "        <br /> The effective date of this Privacy Policy is set forth at the top of this web page. We will notify you of any material change by posting a notice\n" +
                        "        on this web page. Your continued use of the Services after the effective date constitutes your acceptance of the amended Privacy Policy. We encourage you\n" +
                        "        to periodically review this page for the latest information on our privacy practices. The revised Privacy Policy supersedes all previous\n" +
                        "        versions.&nbsp;<br /> <br /> <strong>12. Contacts</strong><br /> If you have any comments, concerns or questions, please contact us at 959 998 12206 or\n" +
                        "        contactus@vatechindia.in &nbsp;or register your concern on www.vatechindia.in/vatech123<br /> <br /> <strong>Assuring You theBest</strong> !!&nbsp;&nbsp;", Html.FROM_HTML_MODE_LEGACY));
            } else {
                aboutPMS_Tv.setText(Html.fromHtml("About Vatech EzPMS\n" +
                        "     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br />\n" +
                        "        Version <strong>1.10</strong> - Updated 02022020<br /> <br /> <strong>1. Introduction</strong><br /> Vatech India Pvt. Ltd.\n" +
                        "        (&quot;Company&quot; &quot;us,&quot; &quot;we,&quot; or &quot;Vatech India PMS&quot;) is the owner of Vatech India Practice Management System for\n" +
                        "        Dental Professionals that works on Windows Operating System and on Android mobile apps platform as Vatech India PMS App - referred individually\n" +
                        "        is committed to respecting the privacy rights of our customers, visitors, and other users of Vatech India PMS on applications, services and mobile\n" +
                        "        applications provided by Vatech India PMS and on/in which this Privacy Policy is posted or referenced (collectively, the &quot;Services&quot;).&nbsp;&nbsp;&nbsp;<br /> We created this Privacy Policy to assure and give you the confidence as you use the Services and also to demonstrate our commitment\n" +
                        "        to the protection of privacy.<br /> This Privacy Policy is only applicable to the Services. This Privacy Policy does not apply to any other website or\n" +
                        "        digital service that you may be able to access the Services or any website or digital services of Vatech India PMS&#39;s business partners, each of which\n" +
                        "        may have data collection, storage and use practices and policies that may materially differ from this Privacy Policy.<br /> Your use of the Services is\n" +
                        "        governed by this Privacy Policy and the Agreement (as the term &quot;Agreement&quot; is defined in our Terms of Use). Any capitalized term used but not\n" +
                        "        defined in this Privacy Policy shall have the meaning in the Agreement.<br /> <br /> BY USING THE SERVICES, YOU AGREE TO THE PRACTICES AND POLICIES OUTLINED\n" +
                        "        IN THIS PRIVACY POLICY, AND YOU HEREBY CONSENT TO THE COLLECTION, USE, AND SHARING OF YOUR INFORMATION AS DESCRIBED IN THIS PRIVACY POLICY.&nbsp;<br />\n" +
                        "        IF YOU DO NOT AGREE WITH THIS PRIVACY POLICY, YOU CANNOT USE THE SERVICES. IF YOU USE THE SERVICES ON BEHALF OF SOMEONE ELSE (SUCH AS YOUR CHILD) OR AN\n" +
                        "        ENTITY (SUCH AS YOUR EMPLOYER), YOU REPRESENT THAT YOU ARE AUTHORIZED BY SUCH INDIVIDUAL OR ENTITY TO ACCEPT THIS PRIVACY POLICY ON SUCH INDIVIDUAL&#39;S\n" +
                        "        OR ENTITY&#39;S BEHALF.<br /> <br /> <strong>Privacy Policy</strong><br /> <strong>2. Information We Collect</strong><br /> <strong>2.1 Personal\n" +
                        "            Information<br /> 2.1.1 Personal Information Generally</strong><br /> Some of the Services require us to know, so that we can best meet your needs.\n" +
                        "        When you access these Services, we may ask you to voluntarily provide us certain information that personally identifies (or could be used to determine\n" +
                        "        individually) you (&quot;Personal Information&quot;). Personal Information includes but is not limited to the following categories\n" +
                        "        of information:&nbsp;<br /> a. Demographic information (such as your gender, your date of birth and your zip code);&nbsp;<br /> b.\n" +
                        "        Contact data (such as your e-mail address and phone number);&nbsp;<br /> c. Medical data (such as the doctors, dentists or other health care\n" +
                        "        providers (&quot;Healthcare Providers&quot;) you have visited, your reasons for visit, your dates of visit, your medical history, and other medical\n" +
                        "        and health information you choose to share with us), and&nbsp;<br /> d. In future there might be an option of uploading health / medical information\n" +
                        "        on the Website in order to enable your Administrator / Doctor or Health Care Specialist to access your previous medical history. While Vatech India PMS\n" +
                        "        takes utmost care of the security of the information you decide to upload, you understand that any information that you disclose on the PMS is at your risk.\n" +
                        "        By uploading/sharing / disclosing your medical information on the PMS, you with this give your consent to Vatech India PMS to store such health / medical\n" +
                        "        information on Vatech India PMS;s Servers. Vatech India PMS will retain such medical / health information you upload on the Servers, for as long as it is\n" +
                        "        needed to fulfil the service you seek to avail on the Website. If you delete your account, Vatech India PMS will delete such medical /\n" +
                        "        health information.&nbsp;<br /> <strong>2.2 Traffic Data</strong><br /> We also may automatically collect certain data when you use the Services, such\n" +
                        "        as (1) IP address; (2) domain server; (3) type of device(s) used to access the Services; (4) web browser(s) used to access the Services; We may also\n" +
                        "        collect additional information, which may be Personal Information, as otherwise described to you at the point of collection or according to your\n" +
                        "        consent.<br /> <br /> <strong>3. How We Collect Information</strong><br /> We collect information (including Personal Information and Traffic Data)\n" +
                        "        when you use and interact with the Services, and in some cases from third party sources.&nbsp;<br /> <br /> <strong>4. Tracking Tools and &quot;Do Not\n" +
                        "            Track.&quot;</strong><br /> <strong>4.1. Tracking Tools</strong><br /> We may use tools outlined below to better understand users.<br /> Cookies:\n" +
                        "        Cookies are small computer files transferred to your computing device that contain information such as user ID, user preferences, lists of pages visited\n" +
                        "        and activities conducted while using the Services. We use Cookies to help us improve or tailor the Services by tracking your navigation habits, storing\n" +
                        "        your authentication status, so you do not have to re-enter your credentials each time you use the Services, customizing your experience with the Services\n" +
                        "        and for analytics and fraud prevention.<br /> For more information on cookies, visit http://www.allaboutcookies.org.<br /> Analytics: We may use third-party\n" +
                        "        software analytics services in connection with the Services, including, for example, to register mouse clicks, mouse movements, scrolling activity and\n" +
                        "        text that you type into the Site. This website analytics services do not collect Personal Information unless you voluntarily provide it and do not\n" +
                        "        track your browsing habits across websites which do not use their services. We use the information gathered from these services to help make the\n" +
                        "        Services easier to use and as otherwise outlined in Section 6 (Use of Information).<br /> Mobile Device Identifiers: Mobile device identifiers are\n" +
                        "        data stored on your mobile device that may track mobile device and data and activities occurring on and through it, as well as the applications\n" +
                        "        installed on it. Mobile device identifiers enable collection of Personal Information (such as media access control, address, and location) and Traffic\n" +
                        "        Data. As with other Tracking Tools, mobile device identifiers help Vatech India PMS learn more about our users demographics and Internet behaviors.<br />\n" +
                        "        <strong>4.2. Options for Opting out of Cookies and Mobile Device Identifiers</strong><br /> Some web browsers (including some mobile web browsers) allow\n" +
                        "        you to reject Cookies or to alert you when a Cookie is placed on your computer, tablet or mobile device. You may be able to reject mobile device identifiers\n" +
                        "        by activating the appropriate setting on your mobile device. Although you are not required to accept Vatech India PMSs Cookies or mobile device identifiers,\n" +
                        "        if you block or reject them, you may not have access to all features available through the Services.<br /> <br />\n" +
                        "        <strong>5. Use of Information</strong><br /> We use your information, including Personal Information, to provide the Services to you and to help\n" +
                        "        improve them, including to:<br /> a. Provide you with the products, services and information you request and respond to correspondence that we\n" +
                        "        receive from you;<br /> b. Provide, maintain, administer or expand the Services, perform business analyses, or for other internal purposes\n" +
                        "        to support, improve or enhance our business, the Services, and other products and services we offer;<br /> c. Notify you about certain\n" +
                        "        resources or Healthcare Providers we think you may be interested in learning more about;<br /> &nbsp;&nbsp; &nbsp;d. Send you information\n" +
                        "        about Vatech India PMS or our products or Services;<br /> e. Contact you when necessary or requested, including to remind you of an upcoming\n" +
                        "        appointment;<br /> f. Customize and tailor your experience of the Services, which may include sending personalized messages or showing you Sponsored\n" +
                        "        Results that may be of interest to you based on information collected by this Privacy Policy;<br /> g. Send emails, and other communications that display\n" +
                        "        content that we think will interest you and according to your preferences;<br /> h. Combine information received from third parties with the information\n" +
                        "        that we have from or about you and use the combined information for any of the purposes described in this Privacy Policy;<br /> i. Use statistical data\n" +
                        "        that we collect in any way permitted by law, including from third parties in connection with their commercial and marketing efforts; and<br /> j. Prevent,\n" +
                        "        detect and investigate security breaches and potentially illegal or prohibited activities.<br /> We may use information that is neither Personal Information\n" +
                        "        nor PHI (including non-PHI Personal Information that has been de-identified and aggregated) to understand better who uses Vatech India PMS and how we can\n" +
                        "        deliver a better healthcare experience.<br /> <br /> <strong>6. Disclosure of Information</strong><br /> We do not disclose any information or sell email\n" +
                        "        addresses to third parties.<br /> We store and process your information on our private cloud servers in the Cloud (AWS)<br /> <br /> <strong>7. Public\n" +
                        "            Information</strong><br /> Any information that you may reveal in a review posting or online discussion or forum is intentionally open to the public\n" +
                        "        and is not in any way private. We recommend that you carefully consider whether to disclose any Personal Information in any public posting or forum. What\n" +
                        "        you have written may be seen and collected by third parties and may be used by others in ways we are unable to control or predict.<br /> <br /> <strong>8.\n" +
                        "            Storage and Security of Information</strong><br /> The security of your Personal Information is important to us. We endeavor to follow accepted industry\n" +
                        "        standards to protect the Personal Information submitted to us, both during transmission and in storage.<br /> Although we make good faith efforts to store\n" +
                        "        Personal Information in a secure operating environment that is not open to the public, we do not and cannot guarantee the security of your Personal\n" +
                        "        Information. If we become aware that your Personal Information has been disclosed in a manner, not by this Privacy Policy, we will use reasonable efforts\n" +
                        "        to notify you of the nature and extent of the disclosure (to the extent we know that information) as soon as reasonably possible and as permitted or\n" +
                        "        required by law.<br /> <br /> <strong>9. Controlling Your Personal Information</strong><br /> If you are a registered user of the Services, you can modify\n" +
                        "        some of the Personal Information you have included in your profile or change your username by logging in and accessing your account. If you wish to close\n" +
                        "        your account, please email us at contactus@VatechIndia.in<br /> Vatech India PMS will delete your account and the related information at your request as\n" +
                        "        soon as reasonably possible. Please note, however, that Vatech India PMS reserves the right to retain information from closed accounts, including complying\n" +
                        "        with the law, preventing fraud, resolving disputes, enforcing the Agreement and taking other actions permitted by law.<br /> You must promptly notify us if\n" +
                        "        any of your account data is lost, stolen or used without permission.<br /> <br /> <strong>10. Links to Other Websites</strong><br /> The Services contain\n" +
                        "        links to third party websites with which Vatech India PMS has no affiliation.&nbsp;<br /> <br /> <strong>11. Updates and Changes to Privacy Policy</strong>\n" +
                        "        <br /> The effective date of this Privacy Policy is set forth at the top of this web page. We will notify you of any material change by posting a notice\n" +
                        "        on this web page. Your continued use of the Services after the effective date constitutes your acceptance of the amended Privacy Policy. We encourage you\n" +
                        "        to periodically review this page for the latest information on our privacy practices. The revised Privacy Policy supersedes all previous\n" +
                        "        versions.&nbsp;<br /> <br /> <strong>12. Contacts</strong><br /> If you have any comments, concerns or questions, please contact us at 959 998 12206 or\n" +
                        "        contactus@vatechindia.in &nbsp;or register your concern on www.vatechindia.in/vatech123<br /> <br /> <strong>Assuring You theBest</strong> !!&nbsp;&nbsp;"));
            }
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    private void getLayouUiId() {
        try {
            Tv_Title = (TextView) findViewById(R.id.Tv_Title);
            aboutPMS_Tv = (TextView) findViewById(R.id.aboutPMS_Tv);
            backMenu = (ImageView) findViewById(R.id.backMenu);
        } catch (Exception exp) {
            exp.getStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backMenu:
                onBackPressed();
                break;
        }
    }
}