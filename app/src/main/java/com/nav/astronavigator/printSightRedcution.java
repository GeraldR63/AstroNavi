package com.nav.astronavigator;

import android.app.Activity;
import android.content.Context;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import kotlinx.coroutines.Delay;

public class printSightRedcution {

    private WebView mWebView;
    Activity activity;
    NADataAndCalc NADataAndCalc;
    calculus calculus;

    printSightRedcution (NADataAndCalc  naDataAndCalc)
    {
        this.NADataAndCalc=naDataAndCalc;
    }

    public void doWebViewPrint(Activity activity) {
        // Create a WebView object specifically for printing
        final WebView webView = new WebView(activity);
        //final WebView webView = (WebView)activity.findViewById(R.id.SecondFragment);
        this.activity=activity;
        // Generate an HTML document on the fly:
        String htmlDocument = "<html>" +
                "<head>"+
                "<style>"+
                "table, th, td {"+
                "table-layout: fixed ;"+
                "border: 2px solid black ;"+
                "border-collapse: collapse;"+
                "}"+
                "</style>"+
                " </head>"+
                "<body >"+
                "<h1>Sight Reduction Form</h1>" +
                "<table style=\"width:100%\" ; >"+
                "<tr><th>DR Data</th>                      <th></th>       <th></th>               <th></th>                    </tr>"+
                "<tr><td>Date</td>                         <td>"+NADataAndCalc.NAData[0].Date    +"</td>       <td>Time</td>           <td>"+NADataAndCalc.NAData[0].Time    +"</td>    </tr>"+
                "<tr><td>DR Latitude</td>                  <td>"+NADataAndCalc.NAData[0].DRLat   +"</td>       <td>DR Longitude</td>   <td>"+NADataAndCalc.NAData[0].DRLong+"</td>    </tr>"+
                "<tr><td>Heading</td>                      <td>"+NADataAndCalc.NAData[0].Heading+"</td>        <td>Speed</td>          <td>"+NADataAndCalc.NAData[0].Speed+"</td>     </tr>"+
                "<tr><th>Sextant Ho2Hc</th>                <th></th>                                  <th></th>               <th></th>                                               </tr>"+
                "<tr><th></th>                             <th>CB#1</th>                              <th>CB#2</th>           <th>CB#3</th>                                           </tr>"+
                "<tr><th>CB Name</th>                      <th>"+NADataAndCalc.NAData[0].CBName+"</th><th>"+NADataAndCalc.NAData[1].CBName+"</th><th>"+NADataAndCalc.NAData[2].CBName+"</th></tr>"+
                "<tr><td>Ho</td>                           <td></td>   <td></td>           <td></td>                  </tr>"+
                "<tr><td>Index Error IC</td>               <td></td>       <td></td>               <td></td>                     </tr>"+
                "<tr><td>DIP</td>                          <td></td>       <td></td>               <td></td>                      </tr>"+
                "<tr><td>Sextant Altitude SA</td>          <td></td>       <td></td>               <td></td>                      </tr>"+
                "<tr><td>LIMB (SUN/MOON)</td>              <td></td>       <td></td>               <td></td>                      </tr>"+
                "<tr><td>Atmospheric Correction</td>       <td></td>       <td></td>               <td></td>                     </tr>"+
                "<tr><td>Additional Corrections</td>       <td></td>       <td></td>               <td></td>                      </tr>"+
                "<tr><td>Hc Sextant DMS</td>               <td>"+calculus.Real2DMS(NADataAndCalc.NAData[0].Ho)+"</td>   <td>"+calculus.Real2DMS(NADataAndCalc.NAData[1].Ho)+"</td>           <td>"+calculus.Real2DMS(NADataAndCalc.NAData[2].Ho)+"</td>                  </tr>"+
                "<tr><td>Hc Sextant Decimal</td>           <td>"+NADataAndCalc.NAData[0].Ho+"</td>   <td>"+NADataAndCalc.NAData[1].Ho+"</td>           <td>"+NADataAndCalc.NAData[2].Ho+"</td>                  </tr>"+
                "<tr><th></th>                             <th>CB#1</th>   <th>CB#2</th>           <th>CB#3</th>                  </tr>"+
                "<tr><th>CB Name</th>                      <th>"+NADataAndCalc.NAData[0].CBName+"</th><th>"+NADataAndCalc.NAData[1].CBName+"</th><th>"+NADataAndCalc.NAData[2].CBName+"</th></tr>"+
                "<tr><td>Fix (Time)</td>                   <td>"+NADataAndCalc.NAData[0].Fix+"</td>       <td></td>               <td></td>                      </tr>"+
                "<tr><td>Observed Date</td>                <td>"+NADataAndCalc.NAData[0].ObservationDate+"</td><td>"+NADataAndCalc.NAData[1].ObservationDate+"</td><td>"+NADataAndCalc.NAData[2].ObservationDate+"</td></tr>"+
                "<tr><td>Observed Time</td>                <td>"+NADataAndCalc.NAData[0].ObservationTime+"</td><td>"+NADataAndCalc.NAData[1].ObservationTime+"</td><td>"+NADataAndCalc.NAData[2].ObservationTime+"</td></tr>"+
                "<tr><td>Hc.omput</td>                     <td>"+NADataAndCalc.NAData[0].Ho+"</td><td>"+NADataAndCalc.NAData[1].Ho+"</td><td>"+NADataAndCalc.NAData[2].Ho+"</td></tr>"+
                "<tr><td>GHA Aries</td>                    <td>"+NADataAndCalc.NAData[0].GHAAries+"</td>       <td>"+NADataAndCalc.NAData[1].GHAAries+"</td>               <td>"+NADataAndCalc.NAData[2].GHAAries+"</td>                    </tr>"+
                "<tr><td>SHA</td>                          <td>"+NADataAndCalc.NAData[0].SHA+"</td><td>"+NADataAndCalc.NAData[1].SHA+"</td><td>"+NADataAndCalc.NAData[2].SHA+"</td></tr>"+
                "<tr><td>GHA</td>                          <td>"+NADataAndCalc.NAData[0].GHA+"</td>       <td>"+NADataAndCalc.NAData[1].GHA+"</td>               <td>"+NADataAndCalc.NAData[2].GHA+"</td>                    </tr>"+
                "<tr><td>LHA Aries</td>                    <td>"+NADataAndCalc.NAData[0].LHAAries+"</td>       <td>"+NADataAndCalc.NAData[1].LHAAries+"</td>               <td>"+NADataAndCalc.NAData[2].LHAAries+"</td>                    </tr>"+
                "<tr><td>LHA H.O.2102-D</td>               <td>"+NADataAndCalc.NAData[0].LHAHO2102D+"</td>       <td>"+NADataAndCalc.NAData[1].LHAHO2102D+"</td>               <td>"+NADataAndCalc.NAData[2].LHAHO2102D+"</td>                    </tr>"+
                "<tr><td>Declination</td>                  <td>"+NADataAndCalc.NAData[0].Declination+"</td><td>"+NADataAndCalc.NAData[1].Declination+"</td><td>"+NADataAndCalc.NAData[2].Declination+"</td></tr>"+
                "<tr><td>p (Intercept)</td>                <td>"+NADataAndCalc.NAData[0].pIntercept+"</td>       <td>"+NADataAndCalc.NAData[1].pIntercept+"</td>  <td>"+NADataAndCalc.NAData[2].pIntercept+"</td>                    </tr>"+
                "<tr><td>Z (Azimuth)</td>                  <td>"+NADataAndCalc.NAData[0].Z+"</td>       <td>"+NADataAndCalc.NAData[1].Z+"</td><td>"+NADataAndCalc.NAData[2].Z+"</td>                    </tr>"+
                "<tr><td>Hc</td>                           <td>"+NADataAndCalc.NAData[0].Hc+"</td>       <td>"+NADataAndCalc.NAData[1].Hc+"</td> <td>"+NADataAndCalc.NAData[2].Hc+"</td>                    </tr>"+
                "<tr><th>Position</th>                     <th></th>       <th></th>               <th></th>                    </tr>"+
                "<tr><td>Longitude DMS</td>                <td>"+calculus.Real2DMS(NADataAndCalc.L1).replace('-','E')+"</td>       <td>Latitide DMS</td>  <td>"+calculus.Real2DMS(NADataAndCalc.B1).replace('-','E')+"</td> </tr>"+
                "<tr><th>Sign</th>                         <th></th>       <th></th>               <th></th></tr>"+
                "<tr><th>Name</th>                         <th></th>       <th>Date</th>           <th></th></tr>"+
                "</table>"+
                "</body></html>";

        //calculus.Real2DMS(L1).replace('-','E')+" "+calculus.Real2DMS(B1).replace('-','S');
        webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null);
        // Keep a reference to WebView object until you pass the PrintDocumentAdapter
        // to the PrintManager
        mWebView = webView;
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //Log.i(TAG, "page finished loading " + url);
                System.out.println("onPageFinished");
                if( mWebView != null) {
                    createWebPrintJob(view);
                    mWebView = null;
                }
            }
        });



    }

    private void createWebPrintJob(WebView webView) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            PrintManager printManager = (PrintManager) this.activity.getSystemService(Context.PRINT_SERVICE);

            PrintDocumentAdapter printAdapter =
                    null;
            printAdapter = webView.createPrintDocumentAdapter("SightReductionForm");
            String jobName = this.activity.getString(R.string.app_name) + " SRF";

            printManager.print(jobName, printAdapter,
                    new PrintAttributes.Builder().build());
        }
        else{
            Toast.makeText(this.activity, "Print job has been canceled! ", Toast.LENGTH_SHORT).show();
        }

    }

    /*

    private void createWebPrintJob(WebView webView) {

          if( webView != null) {
              // Get a PrintManager instance
              PrintManager printManager = (PrintManager) activity.getSystemService(Context.PRINT_SERVICE);
              String jobName = "SightReductionForm Document";

              //String jobName = getString(R.string.app_name) + " Document";

              // Get a print adapter instance
              PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);

              // Create a print job with name and adapter instance
              PrintJob printJob = printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());

              // Save the job object for later status checking
              // printJob.add(printJob);
          }
        }

     */

    public void prepare ()
    {

    }

    public void print ()
    {

    }
}
