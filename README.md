# AstroNavi


<center>
 
<a href="https://www.hamqsl.com/solar.html" title="Click to add Solar-Terrestrial Data to your website!"><img src="https://www.hamqsl.com/solar101vhfpic.php"></a>
</center>

<center>
 
<a href="https://www.hamqsl.com/solar.html" title="Click to add Solar-Terrestrial Data to your website!"><img src="https://www.hamqsl.com/solarmuf.php"></a>
</center>



<br>
README

One point at the beginning: All of this looks very complicated but it isn't. You can solve the Astronavigational Problem simple up to as complex as possible. It depends on your sighting and requirements. If you are at open water than it's probably not required to know your position exactly. If you are nearby shallow water or a reef it probably matters. However this method give you the chance to have a very fast calculation using just basic arithmetic and also much more complex spheric geometry. But look at the Excel. Even someone having no clue of spheric geometry can use the template to do all of the math. Print the spreadsheet and go on. This software is designed to check humans calculation.

Version History Log:

 <table>
  <tr>
    <th>Date</th>
    <th>Time</th>
    <th>Author</th>
    <th>Comments</th>
  </tr>
  <tr>
    <td>20230220</td>
    <td>16:53 GMT</td>
    <td>RN</td>
    <td>Latest release uploaded. Some fixes related to character size to support as much as possible devices.</td>
  </tr>
  <tr>
    <td>20230222</td>
    <td>18:32 GMT</td>
    <td>RN</td>
    <td>Added a printable Sight Reduction Form to the app (at my mobile using Epson printer it works weired. It's required to repeat steps example->calc->SRF a few times. Debugging says "errors" in external classes.</td>
  </tr>
  <tr>
    <td>20230223</td>
    <td>09:28 GMT</td>
    <td>RN</td>
    <td>Try to fix issues with SRF printing. Sometimes app crashes or there seams to be a bufferoverflow failure or something I know from programming in C/C++. Behaves like a missing or false/NULL pointer because it seems that the printer function can override the shared memory and than many strange things happen. It's pure JAVA. Done a lot in JAVA but never seen an application which behaves unpredictable. Seems to work now. </td>
  </tr>
    <tr>
    <td>20230223</td>
    <td>13:30 GMT</td>
    <td>RN</td>
    <td>The reason for most of the problems related to Sight Reduction Form printing are special design of Android mobiles. "Debug Message: The application may be doing too much work on its main thread". Well, I removed one ressource not really necessary from the Simple Navigation dialog. The recommendation to realy solve this issue is not to have complex calculations in main thread. Well. What's this application calculating without any user action? Nothing. Poor Android Design. My opinion. If you have issues with that buy faster mobile phone. I do not see reasons in my code for this. It's just the way it work. Repeat Printing until you got it on paper.</td>
  </tr>
  </tr>
    <tr>
    <td>20230224</td>
    <td>12:00 GMT</td>
    <td>RN</td>
    <td>Some minor changes. Added the image at the start dialog but from a different source directory and lesser size to avoid "The application may be doing too much work on its main thread" This seem to solve this issue. In my opinion an Android Studio issue. They offer the complete code behind the scene than they should deal with this stuff and for example forbid to large images or images placed in the false "res directory".</td>
  </tr> 
    <tr>
    <td>20230225</td>
    <td>10:00 GMT</td>
    <td>RN</td>
    <td>Some minor changes related to colors. Removed "force white background". Some users/tester had false default color theme at their mobiles and thought that my application had a poor color scheme. (Black background and white chars). If a user don't see the colors of the examples setup of his device is the reason.</td>
  </tr>
  </tr> 
    <tr>
    <td>20230225</td>
    <td>16:00 GMT</td>
    <td>RN</td>
    <td>Added a simple "help" feature. Documentation to the app. I started to show a PDF but this did not work as expected. Now it's a test on how to use assets and HTML inside an app. This do not work the way expected. A JEditorPane in Swing is much more powerful.</td>
  </tr>
    <tr>
    <td>20230228</td>
    <td>19:00 GMT</td>
    <td>RN</td>
    <td>Added first step to calculate GHA, SHA and DECLINATION for the stars. Not Planets, Moon and Sun. If a user press PREV or NEXT in "Nautical Almanac" dialog than GHA, SHA and DECLINATION is calculated. I have to add a key listener also for Date and Time Observed to calculate these figures. However this most difficult thing seems to work so far more or less good. The SHA and DECLINATION is up to now not corrected. I will add required code tomorrow.</td>
  </tr>
    <tr>
    <td>20230228</td>
    <td>23:40 GMT</td>
    <td>RN</td>
    <td>Added the key press events to Observed Time and Observed Date fields to update GHA while typing. Now a user can enter GHA Aries and GHA Aries+1 from Nautical Almanac or let the AstroNavigator calculate these figures The daily corrections are in progress. They do not work. Figures are good for 2023. If a user search for a star by using "PREV" and "NEXT" button the SHA and DECL the software will also compute these figures by itself. Corrrections are also in progress. These figures are good for 2023.</td> 
  </tr>
    <tr>
    <td>20230301</td>
    <td>06:00 GMT</td>
    <td>RN</td>
    <td>Checked the "correction" for SHA and DECLINATION. If you use the data calculated by this application than the failure seem to be nearby one or two nautical miles. Maybe I will add an improvement to the application to allow users to set the correcions for SHA und DECL by themself. Today I work on an algorithm which allow to calculate the corrections because it takes a lot of time. In my opinion this is required once every 50 years.</td>
  </tr>
    <tr>
    <td>20230302</td>
    <td>07:00 GMT</td>
    <td>RN</td>
    <td>Minor bugfix related to form management and restart of the application. After restart of the application false CB names shown in "Nautical Almanac" dialog. This came from  changes required to have the internal calculations of GHA, SHA and DECLINATION because the displayed names come from internal ARRAY and not from SharedPreferences anymore. I will have to change the whole code to reduce complexity of display management. However. It works now.</td>
  </tr>
    <tr>
    <td>20230303</td>
    <td>00:00 GMT</td>
    <td>RN</td>
    <td>Added a dialog to allow a user to change GHA,GHAcor, DECLINATION, DECcorr for all of the Celestial Bodys to have as best as possible accuracy.</td>
  </tr>
    <tr>
    <td>20230304</td>
    <td>11:11 GMT</td>
    <td>RN</td>
    <td>Some bug fixes applied and changes to the user interface to avoid unnecessary steps when going from and back to a dialog. This software must be as user friendly as possible. However, the code behind the scenes is wired. It's just rapid development and I started with the idea to solve a small problem and than it grew.</td>
  </tr>
    <tr>
    <td>20230304</td>
    <td>13:16 GMT</td>
    <td>RN</td>
    <td>Shit happened. Previous release worked only in emulator and had massive problems at the real device. Sextant Correction crashed the app. Fixed this and a recalculation issue.</td>
  </tr>
    <tr>
    <td>20230306</td>
    <td>16:00 GMT</td>
    <td>RN</td>
    <td>Added a dialog to have the calculation of the SUN's declination. I work an the PSA and SPA code to get better results. The current formula is nearby but it will be up to 1 or 2 degrees false. That's up to 220km! Inacceptable. However, it's a demo. I will solve this issue. Take some time.</td>
  </tr>
    <tr>
    <td>20230307</td>
    <td>16:00 GMT</td>
    <td>RN</td>
    <td>Added some "fancy gizmos". A timer that refreshs the date and time in the "Celestial Body Correction" and the "Get declination of SUN" dialog. That's easy going and makes sense in this context. But in my opinion "fancy gizmo". However, I need some time for this Astro Math stuff.</td>
  </tr>
    <tr>
    <td>20230309</td>
    <td>00:33 GMT</td>
    <td>RN</td>
    <td>Added another "fancy gizmo". Now the software show the Bearing and Elevation of the Sun if you are located in Greenwich. Next update this will use the calculated position in the Nautical Almanac dialog.</td>
  </tr>
    <tr>
    <td>20230310</td>
    <td>12:00 GMT</td>
    <td>RN</td>
    <td>Added standard application behaviour (+ and - button) and persistence after closing a dialog to the "Get declination of SUN" dialog.</td>
  </tr>
   <tr>
    <td>20230310</td>
    <td>17:29 GMT</td>
    <td>RN</td>
    <td>Fixed issues related to usage of TimeZone. The application was designed to work only with GMT and not with TimeZones. To add TZ made some trouble.</td>
  </tr>
     <tr>
    <td>20230310</td>
    <td>21:00 GMT</td>
    <td>RN</td>
    <td>Fixed some minor bugs with DMS input to avoid crashes forced by false user input. It's also allowed now to have individual character size for all of the dialogs.</td>
  </tr>
     <tr>
    <td>20230311</td>
    <td>21:00 GMT</td>
    <td>RN</td>
    <td>Calculating a proper declination. This value is much better than all of the ones calculated before. Not 100% those of the NA but nearby perfect. I will now work on the GHA problem. After that I will add as best as possible calculation.</td>
  </tr>
     <tr>
    <td>20230311</td>
    <td>21:00 GMT</td>
    <td>RN</td>
    <td>Added a parser for DMS input. I want to force the users to enter valid data into the fields because there are some stupids which try decimal format and wonder about what happens.</td>
     <tr>
    <td>20230312</td>
    <td>01:01 GMT</td>
    <td>RN</td>
    <td>DMS parser is working with some small faults.</td>    
  </tr>
     <tr>
    <td>20230312</td>
    <td>01:01 GMT</td>
    <td>RN</td>
    <td>DMS parser is working more or less perfect but one issue: If you enter a dot in degree...than shit happens.</td>    
  </tr>


  

</table> 


MOST RECENT VERSION WILL BE FOUND IN THE MASTER BRANCH! The MAIN branch will be updated by me only after major changes.

This is a simple AstroNavigation utility.
It's designed for Blue Water Sailors and Professionals using Nautical Almanac or Long Term Almanac
plus a Watch, Sextant.


This App works on Android Mobile Phones like HUAWEI ATU-L21. That's my development platform.
Maybe it will work on other devices but I was not able to check this.


This software come with no warranty.
It's designed as best as possible. The given example of the Nautical Almanac 2022 is calculated correct.
Small differences come from using up to 16 figures after the decimal point. The NA calculates with 5.


Download just "app-release.apk". I guess that your mobile will install it automatically.
See: https://github.com/GeraldR63/AstroNavi/blob/master/app/release/app-release.apk for most recent.

This APP is under construction. Planned are to generate a complete "Sight Reduction Form" as required for a logbook plus a calculation without using a book like Nautical Almanac or Long Term Almanac. But that's bad practise. Computers help humans. We are not the slaves of computers. That's the major mistake younger people make by using computers. My software supports Navigators but the goal is not to make them stupids.

I attached also an ooCalc document which can also be used by Navigators. It's doing the same but shows each calculation step. It also contains an empty sheet with instructions how to calculate everything by yourself.

See: https://github.com/GeraldR63/AstroNavi/blob/main/Position.20211217.inkl.2.Iteration.Grad.Minutes.BestOf.ods

Process flow to get and calculate a position by classic Astronavigation.

<img src="https://github.com/GeraldR63/AstroNavi/blob/main/process%20flow.png" width="900" height="550">

<hr>
Example of very Simple Latitude Calculation:
(This example is from an unknown source and not by me.)

 <table>
  <tr>
    <th>Situation</th>
    <th>Basic</th>
    <th>Basic</th>
    <th>21.08.1981 16:00GMT</th>
    <th>21.08.1981 16:30GMT</th>
  </tr>
  <tr>
    <td>Height observed Ho</td>
    <td>75°</td>
    <td>43°</td>
    <td>85°</td>
    <td>55°37.8'</td>
  </tr>
  <tr>
    <td>Zenith Distance (90°-Ho)</td>
    <td>15°</td>
    <td>47°</td>
    <td>5°</td>
    <td>34°22.2'</td>
  </tr>
    <tr>
    <td>Declination of SUN</td>
    <td>N10°</td>
    <td>S10°</td>
    <td>N12°</td>
    <td>N11°59.6'</td>
  </tr>
  </tr>
    <tr>
    <td>Latitude</td>
    <td>N25°</td>
    <td>N37°</td>
    <td>N7°</td>
    <td>N46°21.8'</td>
  </tr>
  </tr>
    <tr>
    <td>Explanation</td>
    <td>SUN above Equator<br>but below vessels position</td>
    <td>SUN below Equator<br>and below vessels position</td>
    <td>SUN above Equator<br>and above vessels position </td>
    <td>SUN below Equator<br>and below vessels position</td>  
  </tr>
    <tr>
    <td>Rules</td>
    <td>Latitude= (90º – Ho) + declination (SAME)</td>
    <td>Latitude= (90º – Ho) – declination (CONTRARY)</td>
    <td>Latitude= declination – (90º – Ho)</td>
    <td>Latitude= (90º – Ho) – declination (CONTRARY)</td>  
  </tr>
  
</table>

<!--
Rules to Calculate Latitude
1- Latitude and declination Same name but latitude is greater than declination:
                    ‣ Latitude= (90º – Ho) + declination
2- Latitude and declination Same name but declination greater than latitude:
                    ‣ Latitude= declination – (90º – Ho)
3- Latitude and declination Contrary name:
                    ‣ Latitude= (90º – Ho) – declination>>
-->                    
                    
                    
To get the longitude: Use watch at GMT. Use a sun clock (for example) to get the point in time when SUN reaches highest point. Just beginn measurement 20 minutes before and after and take the difference and divide this by two. Than you should know when the sun reached highest point. Remember the SUN travels 15° in an hour. An hour have 3600 seconds. Calculate degree per second and than the difference of time to GMT when SUN reaches highest point at your local position. Calculate seconds of this difference and multiply this with the degree per seconds. Than you will get your Longitude.

That's the simplest method to get valid Latitude and Longitude. One secret: You can also measure at night the North Star (Polarstern). The angle gives nearby directly your Latitude but this works only at the Northern Hemisphere.

<hr>
Equipement an Astronavigator should own:

 <table>
   <tr>
    <th>Equipement</th>
    <th>Almanac</th>
    <th>Clock</th>
    <th>Protractor</th>    
    <th>Paper I</th>
    <th>Paper II</th>
  </tr>
  <tr>
    <td>Starfinder 2102-D</th>
    <td>Nautical Almanac The Crown</th>
    <td>Mechanical Watch at GMT</th>
    <td>Sextant</th>
    <td>Sight Reduction Form</th>
    <td>Universal Plotting Sheet</th>
  </tr>
  <tr>
    <td>Kosmos-Sternkarte</td>
    <td>Long Term Almanac Geoffrey Kolbe</td>
    <td>Any watch at GMT</td>
    <td>Simple Protractor</td>
    <td>AstroNavigator App</td>
    <td>AstroNavigator App</td>
  </tr>
</table>

Nice to have are also: Slide Stick (Rechenstab), Table book of trigonometric functions (Tabellenbuch mit trigonometrischen Funktionen zum Nachschlagen kleiner Winkel, die der Rechenstab nicht hergibt), Pocket Calculator (but well, this may fail). Pencil, a drawing divider, set-square (Geodreieck).

One good joke:
Don't try this to become more independent from technology. You can become independent from electrical power. That's all. GPS can have failures. Electronics can have failures after a solar storm but a sextant can break, a slide ruler can break, a table book can burn. To become independent from technology is not the goal of Astronavigation it's just to become independent from electronics for a few days, weeks or month.


New 20230222 Printable Sight Reduction Form

After having had a valid calculation it's possible to print, after pressing the new SRF Button at the Nautical Almanac Form, a Sight Reduction Form. So far I remember the data at this sheet is good to have also a fix by using an Universal Plotting Sheet.

Image below is a scan from the printed sheet.

<img src="https://github.com/GeraldR63/AstroNavi/blob/main/Sight%20Reduction%20Form.jpg" width="900" height="1024">


<hr>
New 20230306 Calculation of SUN's declination for "Simple" navigation.

The image below shows all of the dialogs this app offer and the flow between them. The new dialog "Get declination of SUN" is available via  "Simple Navigation (SUN)". This dialog do not offer high precesion. Precision  seem to be at noon high and at midnight low. Compared the calculation with Nautical Almanac and for the tested days I had a difference of a few minutes which do not matter up to one degree and more. This matters. One degree is a huge failure. I'm working on a much better solution but now I have to  use "Jean Meeus Astronomical Algorithms" to fix this. This will take a few days to get a working solution.

<img src="https://github.com/GeraldR63/AstroNavi/blob/main/application.png" width="900" height="1024">
