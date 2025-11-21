package main.uade.edu.ar.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private DateUtil(){
    }


    /**
     * Formatea una fecha para mostrar solo día/mes/año y hora
     * @param fecha La fecha a formatear
     * @return String con formato dd/MM/yyyy HH:mm
     */
    public static String formatDateWithTime(Date fecha) {
        if (fecha == null) {
            return "";
        }
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
        return formatoFecha.format(fecha);
    }

}
