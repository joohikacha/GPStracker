package com.joohi.gpstracker;

/**
 * Created by joohi_k on 11-06-2015.
 */
public class pointInPolygon {
    public boolean FindPoint(double X, double Y,coordinates[] segment)
    {
        int sides = segment.length - 1;
        int j = sides - 1;
        boolean pointStatus = false;
        for (int i = 0; i < sides; i++)
        {
            if (segment[i].longitude < Y && segment[j].longitude >= Y ||
                    segment[j].longitude < Y && segment[i].longitude >= Y)
            {
                if (segment[i].latitude + (Y - segment[i].longitude) /
                        (segment[j].longitude - segment[i].longitude) * (segment[j].latitude - segment[i].latitude) < X)
                {
                    pointStatus = !pointStatus ;
                }
            }
            j = i;
        }
        return pointStatus;
    }
}
