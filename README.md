# AstroNavi
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
    <td>16:53</td>
    <td>RN</td>
    <td>Latest release uploaded. Some fixes related to character size to support as much as possible devices.</td>
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
</table>

To get the longitude: Use watch at GMT. Use a sun clock (for example) to get the point in time when SUN reaches highest point. Just beginn measurement 20 minutes before and after and take the difference.  Remember the SUN travels 15° in an hour. An hour have 3600 seconds. Calculate degree per second and than the difference of time to GMT when SUN reaches highest point at your local position. Calculate seconds of this difference and multiply this with the degree per seconds. Than you will get your Longitude.

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
    <td>Protractor</td>
    <td>AstroNavigator App</td>
    <td>AstroNavigator App</td>
  </tr>
</table>

Nice to have are also: Slide Stick (Rechenstab), Table book of trigonometric functions (Tabellenbuch mit trogonometrischen Funktionen zum Nachschlagen kleiner Winkel, die der Rechenstab nicht hergibt), Pocket Calculator (but well, this may fail). Pencil, a drawing divider, set-square (Geodreieck).
