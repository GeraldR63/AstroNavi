package com.nav.astronavigator;

    /*
       (w) 2022 to 2023 by Gerald Roehrbein
       This code is under the GPL 2.0
       You can reuse it by yourself but I expect that you will leave a hint on me.
       I publish since 40 years code an I know the way others take it an make their own property from it.
       Girls, that's stupid. It's very stupid to hinder the creative ones, use their code and intellectual work
       and let them die anonymous. That's why, especially here in Germany nothing works anymore because
       all of the thefts and liar have to work today by themself.

       Sorry. I'm very upset to this. I love the people in the US. The only ones ever paid for my ShareWare
       in the past 40 years came from the USA. Germans pay for nothing. But they pay now a price. A high price.
     */

class STARS {
    String name;
    String name_ger;
    double magn;
    double semidiam;
    double dist;
    double sha;
    double shacor;
    double decl;
    double declcor;

    STARS (String name, double magn,  double sha, double shacor, double decl, double declcor)
    {
            this.name=name;
            this.magn=magn;
            this.semidiam=0.0;
            this.dist=0.0;
            this.sha=sha;
            this.shacor=shacor;
            this.decl=decl;
            this.declcor=declcor;
    }
};


public class CelestialBodys {

com.nav.astronavigator.calculus c;
STARS startable[]=new STARS[70];
planets planets=new planets();

int selectedStar[]= new int[3];  // Welcher Stern wurde jeweils fuer 1,2,3 gewaehlt
                             // Es koennen 3 Sterne gewaehlt werden
                             // In dem Array steht drin welcher Erster, Zweiter und Dritter ist.
                             // Damit dann mit denen gerechnet werden kann.


CelestialBodys()
{
    initStartable();
}

void initStartable()
    {

        /*
             Data taken from Nautical Almanac 2023.
             01.01.2023

             The table contains: Magnitude of the star.
                                 SHA und DECL as given by NA2023 plus correction multiplier for SHA and DECLINATION.
         */
        //startable[0]=new STARS("acamar",            3.1 ,   315.6232,  -0.00942,  -40.3847 ,  0.004);
        startable[0]=new STARS("acamar",            3.1 ,   c.DMS2Real("315°12.8'00.0\""),  -0.00942,  c.DMS2Real("S040°13.0'00.0\"") ,  0.004);
        startable[1]=new STARS("achernar",          0.6 ,   c.DMS2Real("335°21.3'00.0\""),  -0.00917,  c.DMS2Real("S057°07.5'00.0\"") ,  0.005);
        startable[2]=new STARS("acrux"   ,          1.1 ,   c.DMS2Real("173°01.9'00.0\""),  -0.0138,   c.DMS2Real("S063°13.2'00.0\""),  -0.0055);
        startable[3]=new STARS("adhara"      ,      1.6 ,   c.DMS2Real("255°06.8'00.0\""),  -0.00983,  c.DMS2Real("S029°00.2'00.0\""),  -0.0014);
        startable[4]=new STARS("aldebaran",         1.1 ,   c.DMS2Real("290°41.2'00.0\""),  -0.01425 ,  c.DMS2Real("016°33.3'00.0\"") ,  0.002);

        startable[5]=new STARS("alioth",            1.7 ,   c.DMS2Real("166°14.5'00.0\""),  -0.0108  ,  c.DMS2Real("055°49.9'00.0\"") , -0.0053);
        startable[6]=new STARS("alkaid" ,           1.9 ,   c.DMS2Real("152°53.5'00.0\""),  -0.0097  ,  c.DMS2Real("049°11.7'00.0\"") , -0.005);
        startable[7]=new STARS("alnair" ,           2.2 ,   c.DMS2Real("027°35.3'00.0\""),  -0.0156  , c.DMS2Real("S046°51.2'00.0\"") ,  0.0048);
        startable[8]=new STARS("alnilam",           1.8 ,   c.DMS2Real("275°39.1'00.0\""),  -0.0126  ,  c.DMS2Real("S001°11.3'00.0\"") ,  0.0006);
        startable[9]=new STARS("alphard",           2.2 ,   c.DMS2Real("217°49.1'00.0\""),  -0.0122  ,  c.DMS2Real("S008°45.4'00.0\"") , -0.004);

        startable[10]=new STARS("alphecca" ,        2.3 ,   c.DMS2Real("126°05.4'00.0\""),  -0.0105  ,  c.DMS2Real("026°38.1'00.0\"") , -0.0033);
        startable[11]=new STARS("alpheratz",        2.2 ,   c.DMS2Real("357°36.6'00.0\""),  -0.0128  ,  c.DMS2Real("029°13.1'00.0\"") ,  0.0055);
        startable[12]=new STARS("altair",           0.9 ,   c.DMS2Real("062°01.9'00.0\""),  -0.01217 ,   c.DMS2Real("008°55.7'00.0\"") ,  0.00267);
        startable[13]=new STARS("ankaa",            2.4 ,   c.DMS2Real("353°08.8'00.0\""),  -0.0123  , c.DMS2Real("S042°11.2'00.0\"") ,  0.0053);
        startable[14]=new STARS("antares",          1.2 ,   c.DMS2Real("112°18.2'00.0\""),  -0.0153  , c.DMS2Real("S026°28.9'00.0\"") , -0.00217);

        startable[15]=new STARS("arcturus",         0.2 ,   c.DMS2Real("145°49.6'00.0\""),  -0.0113  ,  c.DMS2Real("019°03.7'00.0\"") , -0.00517);
        startable[16]=new STARS("atria",            1.9 ,   c.DMS2Real("107°14.5'00.0\""),  -0.026   , c.DMS2Real("S069°03.9'00.0\"") , -0.00175);
        startable[17]=new STARS("avior",            1.7 ,   c.DMS2Real("234°14.8'00.0\""),  -0.0051  , c.DMS2Real("S059°34.8'00.0\"") , -0.0032);
        startable[18]=new STARS("bellatrix",        1.7 ,   c.DMS2Real("278°24.3'00.0\""),  -0.0133  ,   c.DMS2Real("006°22.2'00.0\"") ,  0.0008);
        startable[19]=new STARS("betelgeuse",       0.6 ,   c.DMS2Real("270°53.6'00.0\""),  -0.0134  ,   c.DMS2Real("007°24.7'00.0\"") ,  0.0002);

        startable[20]=new STARS("canopus",         -0.9 ,   c.DMS2Real("263°52.6'00.0\""),  -0.0056  , c.DMS2Real("S052°42.5'00.0\"") , -0.0006);
        startable[21]=new STARS("capella",          0.2 ,   c.DMS2Real("280°23.9'00.0\""),  -0.0183  , c.DMS2Real("046°01.3'00.0\"") ,  0.0009);
        startable[22]=new STARS("deneb",            1.3 ,   c.DMS2Real("049°27.4'00.0\""),  -0.0084 , c.DMS2Real("045°21.8'00.0\"") ,  0.0036);
        startable[23]=new STARS("denebola",         2.2 ,   c.DMS2Real("182°26.6'00.0\""),  -0.0127  ,  c.DMS2Real("014°26.6'00.0\"") , -0.0055);
        startable[24]=new STARS("diphda",           2.2 ,   c.DMS2Real("348°49.0'00.0\""),  -0.0125  , c.DMS2Real("S017°51.8'00.0\"") ,  0.0054);

        startable[25]=new STARS("dubhe",            2.0 ,   c.DMS2Real("193°42.8'00.0\""),  -0.0153  ,  c.DMS2Real("061°37.4'00.0\"") , -0.0054);
        startable[26]=new STARS("elnath",           1.8 ,   c.DMS2Real("278°03.6'00.0\""),  -0.0157  ,  c.DMS2Real("028°37.6'00.0\"") ,  0.0008);
        startable[27]=new STARS("eltanin",          2.4 ,    c.DMS2Real("090°43.5'00.0\""),  -0.0058  ,  c.DMS2Real("051°29.0'00.0\"") , -0.0001);
        startable[28]=new STARS("enif",             2.5 ,    c.DMS2Real("033°40.7'00.0\""),  -0.0122  ,   c.DMS2Real("009°58.8'00.0\"") ,  0.0045);
        startable[29]=new STARS("fomalhaut",        1.3 ,    c.DMS2Real("015°16.5'00.0\""),  -0.0138  , c.DMS2Real("S029°30.3'00.0\"") ,  0.0053);

        startable[30]=new STARS("gacrux",           1.6 ,   c.DMS2Real("171°53.5'00.0\""),  -0.0138  , c.DMS2Real("S057°14.2'00.0\"") , -0.0055);
        startable[31]=new STARS("gienah",           2.8 ,   c.DMS2Real("175°45.3'00.0\""),  -0.0128  , c.DMS2Real("S017°40.0'00.0\""), -0.0055);
        startable[32]=new STARS("hadar",            0.9 ,   c.DMS2Real("148°38.6'00.0\""),  -0.0176  , c.DMS2Real("S060°28.7'00.0\"") , -0.0048);
        startable[33]=new STARS("hamal",            2.2 ,   c.DMS2Real("327°52.9'00.0\""),  -0.014   ,  c.DMS2Real("023°34.3'00.0\"") ,  0.0047);
        startable[34]=new STARS("kaus australis",   2.0 ,    c.DMS2Real("083°35.1'00.0\""),  -0.0164  , c.DMS2Real("S034°22.4'00.0\"") , -0.0006);

        startable[35]=new STARS("kochab",           2.2 ,   c.DMS2Real("137°20.4'00.0\""),   0.0007  ,  c.DMS2Real("074°03.4'00.0\"") , -0.0041);
        startable[36]=new STARS("markab",           2.6 ,   c.DMS2Real("013°31.7'00.0\""),  -0.0124  ,  c.DMS2Real("015°19.7'00.0\"") ,  0.0053);
        startable[37]=new STARS("menkar",           2.8 ,   c.DMS2Real("314°07.7'00.0\""),  -0.013   ,   c.DMS2Real("004°10.7'00.0\"") ,  0.0039);
        startable[38]=new STARS("menkent",          2.3 ,   c.DMS2Real("147°59.7'00.0\""),  -0.0146  , c.DMS2Real("S036°28.8'00.0\"") , -0.0048);
        startable[39]=new STARS("miaplacidus",      1.8 ,   c.DMS2Real("221°37.8'00.0\""),  -0.0028  , c.DMS2Real("S069°48.4'00.0\"") , -0.0041);

        startable[40]=new STARS("mirfak",           1.9 ,   c.DMS2Real("308°30.3'00.0\""),  -0.0178  ,  c.DMS2Real("049°56.7'00.0\"") ,  0.0035);
        startable[41]=new STARS("nunki",            2.1 ,   c.DMS2Real("075°50.2'00.0\""),  -0.0154  , c.DMS2Real("S026°16.1'00.0\"") ,  0.0013);
        startable[42]=new STARS("peacock",          2.1 ,   c.DMS2Real("053°08.9'00.0\""),  -0.0196  , c.DMS2Real("S056°39.8'00.0\"") ,  0.0033);
        startable[43]=new STARS("pollux",           1.2 ,   c.DMS2Real("243°19.0'00.0\""),  -0.0152  ,  c.DMS2Real("027°58.2'00.0\"") , -0.0024);
        startable[44]=new STARS("procyon",          0.5 ,   c.DMS2Real("244°52.3'00.0\""),  -0.013   ,   c.DMS2Real("005°09.9'00.0\"") , -0.0026);

        startable[45]=new STARS("rasalhague",       2.1 ,    c.DMS2Real("096°00.4'00.0\""),  -0.0115  ,  c.DMS2Real("012°32.5'00.0\"") , -0.0007);
        startable[46]=new STARS("regulus",          1.3 ,    c.DMS2Real("207°36.0'00.0\""),  -0.0133  ,  c.DMS2Real("011°51.3'00.0\"") , -0.00492);
        startable[47]=new STARS("rigel",            0.3 ,    c.DMS2Real("281°05.2'00.0\""),  -0.012   ,  c.DMS2Real("S008°10.6'00.0\"") ,  0.0011);
        startable[48]=new STARS("rigil kentaurus",  0.1 ,    c.DMS2Real("139°42.9'00.0\""),  -0.017   , c.DMS2Real("S060°55.5'00.0\"") , -0.004);
        startable[49]=new STARS("sabik",            2.6 ,   c.DMS2Real("102°05.0'00.0\""),  -0.0143  , c.DMS2Real("S015°45.2'00.0\"") , -0.0012);

        startable[50]=new STARS("schedar",          2.5 ,    c.DMS2Real("349°32.9'00.0\""),  -0.0142  ,  c.DMS2Real("056°40.0'00.0\"") ,  0.0054);
        startable[51]=new STARS("shaula",           1.7 ,    c.DMS2Real("096°13.1'00.0\""),  -0.0169  , c.DMS2Real("S037°07.2'00.0\"") , -0.0008);
        startable[52]=new STARS("sirius",          -1.6 ,    c.DMS2Real("258°27.4'00.0\""),  -0.0109  , c.DMS2Real("S016°44.9'00.0\"") , -0.0014);
        startable[53]=new STARS("spica",            1.2 ,   c.DMS2Real("158°24.1'00.0\""),  -0.0131  , c.DMS2Real("S011°16.8'00.0\"") , -0.0051);
        startable[54]=new STARS("suhail",           2.2 ,   c.DMS2Real("222°47.2'00.0\""),  -0.009   , c.DMS2Real("S043°31.3'00.0\"") , -0.004);

        startable[55]=new STARS("vega",             0.1 ,    c.DMS2Real("080°34.8'00.0\""),  -0.0084  ,  c.DMS2Real("038°48.2'00.0\"") ,  0.001);
        startable[56]=new STARS("zuben el genubi",  2.9 ,    c.DMS2Real("136°58.1'00.0\""),  -0.0138  , c.DMS2Real("S016°08.1'00.0\"") , -0.0041);

    };

    double fpavals[] = { /* first point of aries, 1980 - 1999 */
            98.8256,
            99.5713,
            99.3317,
            99.0926,
            98.8540,
            99.6017,
            99.3641,
            99.1268,
            98.8897,
            99.6382,
            99.4008,
            99.1631,
            98.9250,
            99.6719,
            99.4326,
            99.1929,
            98.9529,
            99.6982,
            99.4579,
            99.2177
    };

    double meananom[] = { /* solar mean anomaly, 1980 - 1999 */
            -3.7737,
            -3.0452,
            -3.3020,
            -3.5583,
            -3.8140,
            -3.0836,
            -3.3383,
            -3.5927,
            -3.8470,
            -3.1157,
            -3.3702,
            -3.6251,
            -3.8804,
            -3.1507,
            -3.4071,
            -3.6640,
            -3.9212,
            -3.1930,
            -3.4505,
            -3.7078
    };

    double solperi[] = { /* sun's perihelion point, 1980 - 1999 */
            77.4006,
            77.3835,
            77.3663,
            77.3491,
            77.3320,
            77.3148,
            77.2976,
            77.2805,
            77.2633,
            77.2461,
            77.2289,
            77.2118,
            77.1946,
            77.1774,
            77.1603,
            77.1431,
            77.1260,
            77.1087,
            77.0916,
            77.0744
    };




    class HMS
    {
        int  hr,mn,sc;
        HMS(int hr, int mn, int sc)
        {
            this.hr=hr;
            this.mn=mn;
            this.sc=sc;
        }



    };

    class planets {
        private static final int SUN = 0;
        private static final int EARTH = 0;
        private static final int MERCURY = 1;
        private static final int VENUS = 2;
        private static final int MARS = 3;
        private static final int JUPITER = 4;
        private static final int SATURN = 5;
        private static final int URANUS = 6;
        private static final int NEPTUNE = 7;
        private static final int PLUTO = 8;


        HMS ascending_node[]=new HMS[9];
        HMS perihelion[]=new HMS[9];
        HMS inclination[]=new HMS[9];
        HMS epoch_longitude[]=new HMS[9];

        String planet_name []=new String[9];
        double orbital_period[]=new double[9];
        double eccentricity[]=new double[9];
        double distance[]=new double[9];
        double albedo[]=new double[9];
        double radius[]=new double[9];

        void initPlanetName()
        {
            planet_name[SUN] = new String("SUN");
            planet_name[MERCURY] = new String("MERCURY");
            planet_name[VENUS] = new String("VENUS");
            planet_name[MARS] = new String("MARS");
            planet_name[JUPITER] = new String("JUPITER");
            planet_name[SATURN] = new String("SATURN");
            planet_name[URANUS] = new String("URANUS");
            planet_name[NEPTUNE] = new String("NEPTUNE");
            planet_name[PLUTO] = new String("PLUTO");

            /*
                 ToDo: Defaults setzen!!!
             */
            startable[57]=new STARS("SUN",    0 ,   0,  0  , 0 , 0);
            startable[58]=new STARS("MERCURY",    0 ,   0,  0  , 0 , 0);
            startable[59]=new STARS("MARS",    0 ,   0,  0  , 0 , 0);
            startable[60]=new STARS("JUPITER",    0 ,   0,  0  , 0 , 0);
            startable[61]=new STARS("SATURN",    0 ,   0,  0  , 0 , 0);
            startable[62]=new STARS("URANUS",    0 ,   0,  0  , 0 , 0);
            startable[63]=new STARS("NEPTUNE",    0 ,   0,  0  , 0 , 0);
            startable[64]=new STARS("PLUTO",    0 ,   0,  0  , 0 , 0);
            startable[65]=new STARS("MOON",    0 ,   0,  0  , 0 , 0);


        }


        void initOrbital_Period()
        {
              orbital_period[EARTH]=365.2596; /* Earth */
              orbital_period[MERCURY]=87.9693; /* Mercury */
              orbital_period[VENUS]=224.7009; /* Venus */
              orbital_period[MARS]=686.9796; /* Mars */
              orbital_period[JUPITER]=4332.1248; /* Jupiter */
              orbital_period[SATURN]=10825.863; /* Saturn */
              orbital_period[URANUS]=30676.15; /* Uranus */
              orbital_period[NEPTUNE]=59911.13; /* Neptune */
              orbital_period[PLUTO]=90824.2; /* Pluto */
        }

        void initEccentricity()
        {
               eccentricity[EARTH]=.016755; /* Earth */
               eccentricity[MERCURY]=.205636; /* Mercury */
               eccentricity[VENUS]=.006759; /* Venus */
               eccentricity[MARS]=.093348; /* Mars */
               eccentricity[JUPITER]=.048061; /* Jupiter */
               eccentricity[SATURN]=.050822; /* Saturn */
               eccentricity[URANUS]=.047552; /* Uranus */
               eccentricity[NEPTUNE]=.006608; /* Neptune */
               eccentricity[PLUTO]=.253364; /* Pluto */

        }


        void initDistance()
        {
            distance[EARTH]=.999994; /* Earth */
            distance[MERCURY]=.387098; /* Mercury */
            distance[VENUS]=.723330; /* Venus */
            distance[MARS]=1.523712; /* Mars */
            distance[JUPITER]=5.20270; /* Jupiter */
            distance[SATURN]=9.57542; /* Saturn */
            distance[URANUS]=19.2998; /* Uranus */
            distance[NEPTUNE]=30.2813; /* Neptune */
            distance[PLUTO]=39.7138;   /* Pluto */

        }
        void initAlbedo()
        {
            albedo[EARTH]= 0.39; /* Earth */
            albedo[MERCURY]=.06; /* Mercury */
            albedo[VENUS]=.72; /* Venus */
            albedo[MARS]=.16; /* Mars */
            albedo[JUPITER]=.70; /* Jupiter */
            albedo[SATURN]=.75; /* Saturn */
            albedo[URANUS]=.90; /* Uranus */
            albedo[NEPTUNE]=.82; /* Neptune */
            albedo[PLUTO]=.14;   /* Pluto */

        }


        void initRadius()
        {

            radius[SUN]=432560.0; /* Sun */
            radius[MERCURY]=1515.0; /* Mercury */
            radius[VENUS]=3760.0; /* Venus */
            radius[MARS]=2108.4; /* Mars */
            radius[JUPITER]=44362.0; /* Jupiter */
            radius[SATURN]=37280.0; /* Saturn */
            radius[URANUS]=15800.0; /* Uranus */
            radius[NEPTUNE]=15100.0; /* Neptune */
            radius[PLUTO]=930.0;   /* Pluto */

        }

        void initAscending_Node()
        {
            ascending_node[EARTH]=new HMS(  0,0,0); /* Earth */
            ascending_node[MERCURY]=new HMS(48,9,4); /* Mercury */
            ascending_node[VENUS]=new HMS(76,32,42); /* Venus */
            ascending_node[MARS]=new HMS(49,26,35); /* Mars */
            ascending_node[JUPITER]=new HMS(100,20,20); /* Jupiter */
            ascending_node[SATURN]=new HMS(113,32,20); /* Saturn */
            ascending_node[URANUS]=new HMS(73,59,17); /* Uranus */
            ascending_node[NEPTUNE]=new HMS(131,37,34); /* Neptune */
            ascending_node[PLUTO]=new HMS(110,12,58);   /* Pluto */
        }
        void initPerihelion()
        {
            perihelion[EARTH]=new HMS(  102,42,22); /* Earth */
            perihelion[MERCURY]=new HMS(77,13,19); /* Mercury */
            perihelion[VENUS]=new HMS(131,29, 24); /* Venus */
            perihelion[MARS]=new HMS(335,43,24); /* Mars */
            perihelion[JUPITER]=new HMS(15,23,56); /* Jupiter */
            perihelion[SATURN]=new HMS(93,29,13); /* Saturn */
            perihelion[URANUS]=new HMS(177,7,23); /* Uranus */
            perihelion[NEPTUNE]=new HMS(354,40,12); /* Neptune */
            perihelion[PLUTO]=new HMS(224,15,0);   /* Pluto */

        }

        void initInclination()
        {
            inclination[EARTH]=new HMS(  0, 0, 0); /* Earth */
            inclination[MERCURY]=new HMS(7,0,17); /* Mercury */
            inclination[VENUS]=new HMS(3,23,40); /* Venus */
            inclination[MARS]=new HMS(1,50,59); /* Mars */
            inclination[JUPITER]=new HMS(1,18,19); /* Jupiter */
            inclination[SATURN]=new HMS(2,29,8); /* Saturn */
            inclination[URANUS]=new HMS(0,46,27); /* Uranus */
            inclination[NEPTUNE]=new HMS(1,46,15); /* Neptune */
            inclination[PLUTO]=new HMS(17,7,56);   /* Pluto */

        }

        void initEpoch_Longitude()
        {
            epoch_longitude[EARTH]=new HMS(  35, 32, 32); /* Earth */
            epoch_longitude[MERCURY]=new HMS(242, 6, 54); /* Mercury */
            epoch_longitude[VENUS]=new HMS(298, 45, 24); /* Venus */
            epoch_longitude[MARS]=new HMS(329, 44, 5); /* Mars */
            epoch_longitude[JUPITER]=new HMS(293, 28, 58); /* Jupiter */
            epoch_longitude[SATURN]=new HMS(224, 21, 44); /* Saturn */
            epoch_longitude[URANUS]=new HMS(248, 5, 3); /* Uranus */
            epoch_longitude[NEPTUNE]=new HMS(271, 32, 56); /* Neptune */
            epoch_longitude[PLUTO]=new HMS(216, 55, 28);   /* Pluto */

        }

        planets()
        {
            /*
               Init Planets with their RAW data
             */
            initPlanetName();
            initAlbedo();
            initAscending_Node();
            initDistance();
            initAscending_Node();
            initInclination();
            initAscending_Node();
            initEpoch_Longitude();
            initOrbital_Period();
            initPerihelion();
            initRadius();
        }

    }




}
