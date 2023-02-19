package com.nav.astronavigator;

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
        startable[0]=new STARS("acamar",            3.1 ,   315.6232,  -0.00942,  -40.3847 ,  0.004);
        startable[1]=new STARS("achernar",          0.6 ,   335.7575,  -0.00917,  -57.3374 ,  0.005);
        startable[2]=new STARS("acrux"   ,          1.1 ,   173.6225,  -0.0138,   -62.9899,  -0.0055);
        startable[3]=new STARS("adhara"      ,      1.6 ,   255.5374,  -0.00983,  -28.9463,  -0.0014);
        startable[4]=new STARS("aldebaran",         1.1 ,   291.3032,  -0.01425 ,  16.4688 ,  0.002);
        startable[5]=new STARS("alioth",            1.7 ,   166.7119,  -0.0108  ,  56.0672 , -0.0053);
        startable[6]=new STARS("alkaid" ,           1.9 ,   153.3111,  -0.0097  ,  49.4103 , -0.005);
        startable[7]=new STARS("alnair" ,           2.2 ,    28.2531,  -0.0156  , -47.0563 ,  0.0048);
        startable[8]=new STARS("alnilam",           1.8 ,   276.1968,  -0.0126  ,  -1.2157 ,  0.0006);
        startable[9]=new STARS("alphard",           2.2 ,   218.3453,  -0.0122  ,  -8.5739 , -0.004);
        startable[10]=new STARS("alphecca" ,        2.3 ,   126.5376,  -0.0105  ,  26.7818 , -0.0033);
        startable[11]=new STARS("alpheratz",        2.2 ,   358.1578,  -0.0128  ,  28.9814 ,  0.0055);
        startable[12]=new STARS("altair",           0.9 ,    62.5546,  -0.01217 ,   8.8171 ,  0.00267);
        startable[13]=new STARS("ankaa",            2.4 ,   353.6739,  -0.0123  , -42.4132 ,  0.0053);
        startable[14]=new STARS("antares",          1.2 ,   112.9507,  -0.0153  , -26.3876 , -0.00217);
        startable[15]=new STARS("arcturus",         0.2 ,   146.3103,  -0.0113  ,  19.2857 , -0.00517);
        startable[16]=new STARS("atria",            1.9 ,   108.3565,  -0.026   , -68.9915 , -0.00175);
        startable[17]=new STARS("avior",            1.7 ,   234.4704,  -0.0051  , -59.4471 , -0.0032);
        startable[18]=new STARS("bellatrix",        1.7 ,   278.9822,  -0.0133  ,   6.3307 ,  0.0008);
        startable[19]=new STARS("betelgeuse",       0.6 ,   271.4740,  -0.0134  ,   7.4026 ,  0.0002);
        startable[20]=new STARS("canopus",         -0.9 ,   264.1210,  -0.0056  , -52.6869 , -0.0006);
        startable[21]=new STARS("capella",          0.2 ,   281.1924,  -0.0183  ,  45.9774 ,  0.0009);
        startable[22]=new STARS("deneb",            1.3 ,    49.8092,  -0.0084  ,  45.2110 ,  0.0036);
        startable[23]=new STARS("denebola",         2.2 ,   182.9871,  -0.0127  ,  14.6819 , -0.0055);
        startable[24]=new STARS("diphda",           2.2 ,   349.3257,  -0.0125  , -18.0951 ,  0.0054);
        startable[25]=new STARS("dubhe",            2.0 ,   194.3736,  -0.0153  ,  61.8572 , -0.0054);
        startable[26]=new STARS("elnath",           1.8 ,   278.7392,  -0.0157  ,  28.5900 ,  0.0008);
        startable[27]=new STARS("eltanin",          2.4 ,    90.9631,  -0.0058  ,  51.4931 , -0.0001);
        startable[28]=new STARS("enif",             2.5 ,    34.1954,  -0.0122  ,   9.7850 ,  0.0045);
        startable[29]=new STARS("fomalhaut",        1.3 ,    15.8601,  -0.0138  , -29.7264 ,  0.0053);
        startable[30]=new STARS("gacrux",           1.6 ,   172.4811,  -0.0138  , -57.0025 , -0.0055);
        startable[31]=new STARS("gienah",           2.8 ,   176.3022,  -0.0128  , -17.4326 , -0.0055);
        startable[32]=new STARS("hadar",            0.9 ,   149.3918,  -0.0176  , -60.2775 , -0.0048);
        startable[33]=new STARS("hamal",            2.2 ,   328.4849,  -0.014   ,  23.3688 ,  0.0047);
        startable[34]=new STARS("kausastralis",     2.0 ,    84.2844,  -0.0164  , -34.3935 , -0.0006);
        startable[35]=new STARS("kochab",           2.2 ,   137.3182,   0.0007  ,  74.2374 , -0.0041);
        startable[36]=new STARS("markab",           2.6 ,    14.0554,  -0.0124  ,  15.0996 ,  0.0053);
        startable[37]=new STARS("menkar",           2.8 ,   314.6885,  -0.013   ,   4.0117 ,  0.0039);
        startable[38]=new STARS("menkent",          2.3 ,   148.6199,  -0.0146  , -36.2728 , -0.0048);
        startable[39]=new STARS("miaplacidus",      1.8 ,   221.7482,  -0.0028  , -69.6374 , -0.0041);
        startable[40]=new STARS("mirfak",           1.9 ,   309.2719,  -0.0178  ,  49.7907 ,  0.0035);
        startable[41]=new STARS("nunki",            2.1 ,    76.4899,  -0.0154  , -26.3201 ,  0.0013);
        startable[42]=new STARS("peacock",          2.1 ,    53.9778,  -0.0196  , -56.7979 ,  0.0033);
        startable[43]=new STARS("pollux",           1.2 ,   243.9731,  -0.0152  ,  28.0732 , -0.0024);
        startable[44]=new STARS("procyon",          0.5 ,   245.4326,  -0.013   ,   5.2746 , -0.0026);
        startable[45]=new STARS("rasalhague",       2.1 ,    96.4954,  -0.0115  ,  12.5754 , -0.0007);
        startable[46]=new STARS("regulus",          1.3 ,   208.1699,  -0.0133  ,  12.0632 , -0.00492);
        startable[47]=new STARS("rigel",            0.3 ,   281.6028,  -0.012   ,  -8.2256 ,  0.0011);
        startable[48]=new STARS("rigilkentaurus",   0.1 ,   140.4333,  -0.017   , -60.7521 , -0.004);
        startable[49]=new STARS("sabik",            2.6 ,   102.6886,  -0.0143  , -15.6999 , -0.0012);
        startable[50]=new STARS("schedar",          2.5 ,   350.1526,  -0.0142  ,  56.4293 ,  0.0054);
        startable[51]=new STARS("shaula",           1.7 ,    96.9326,  -0.0169  , -37.0886 , -0.0008);
        startable[52]=new STARS("sirius",          -1.6 ,   258.9308,  -0.0109  , -16.6907 , -0.0014);
        startable[53]=new STARS("spica",            1.2 ,   158.9617,  -0.0131  , -11.0578 , -0.0051);
        startable[54]=new STARS("suhail",           2.2 ,   223.1813,  -0.009   , -43.3539 , -0.004);
        startable[55]=new STARS("vega",             0.1 ,    80.9324,  -0.0084  ,  38.7667 ,  0.001);
        startable[56]=new STARS("zubenelgenubi",    2.9 ,   137.5535,  -0.0138  , -15.9592 , -0.0041);

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
