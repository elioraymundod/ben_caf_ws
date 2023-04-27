/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gt.umg.beneficiocafe.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elio Raymundo
 */
public class ManejoFechas {
    private static final long serialVersionUID = -3881987058323059260L;

    public ManejoFechas() {
    }
    
    /*
	 * public static void main(String pArg[]) {
	 * System.out.println(ManejoFechas.dateToString( new
	 * Date(System.currentTimeMillis()), "dd 'de' MMMMMM 'de' yyyy")); }
     */
    /**
     * Metodo para convertir Date a String
     *
     * @param pFechaDate fecha a convertir a String
     * @return String
     */
    public static String dateToString(Date pFechaDate) {
        if (pFechaDate != null) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String fechaString = dateFormat.format(pFechaDate);
            return fechaString;
        } else {
            return "";
        }
    }

    /**
     * Metodo para obtener anio de un Date
     *
     * @param pFechaDate fecha a obtener anio
     * @return anio de fecha consultada
     */
    public static int yearOfdate(Date pFechaDate) {
        if (pFechaDate != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy");
            String fechaString = dateFormat.format(pFechaDate);
            return Integer.valueOf(fechaString);
        } else {
            return 2018;
        }
    }

    public static int monthOfdate(Date pFechaDate) {
        if (pFechaDate != null) {
            DateFormat dateFormat = new SimpleDateFormat("MM");
            String fechaString = dateFormat.format(pFechaDate);
            return Integer.valueOf(fechaString);
        } else {
            return -1;
        }
    }

    /**
     *
     * @param month numero del mes de 1 a 12;
     * @return nombre del mes
     */
    public static String nameOfMonth(int month) {
        switch (month) {
            case 0:
                return "Enero";
            case 1:
                return "Febrero";
            case 2:
                return "Marzo";
            case 3:
                return "Abril";
            case 4:
                return "Mayo";
            case 5:
                return "Junio";
            case 6:
                return "Julio";
            case 7:
                return "Agosto";
            case 8:
                return "Septiembre";
            case 9:
                return "Octubre";
            case 10:
                return "Noviembre";
            case 11:
                return "Diciembre";
            default:
                return "Indefinido";
        }
    }

    public static String dateToString(Date pFechaDate, String pFormato) {
        if (pFechaDate != null) {
            DateFormat dateFormat = new SimpleDateFormat(pFormato, new Locale(
                    "es"));
            String fechaString = dateFormat.format(pFechaDate);
            return fechaString;
        } else {
            return "";
        }
    }

    /**
     * Metodo para convertir String a Date
     *
     * @param pFechaString fecha a convertir
     * @return Date
     */
    public static Date stringToDate(String pFechaString) {
        Date fecha = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            fecha = formatter.parse(pFechaString);
        } catch (ParseException e) {
            System.out
                    .println("ManejoFechas-StringToDate. Error al parser fecha." + pFechaString);
        }
        return fecha;
    }

    /**
     * Metodo para convertir String a Date
     *
     * @param pFechaString fecha a convertir
     * @param pFormato Formato a convertir
     * @return Date
     */
    public static Date stringToDate(String pFechaString, String pFormato) {
        Date fecha = null;
        SimpleDateFormat formatter = new SimpleDateFormat(pFormato);
        try {
            fecha = formatter.parse(pFechaString);
        } catch (ParseException e) {
            System.out
                    .println("ManejoFechas-StringToDate. Error al parser fecha." + pFechaString);
        }
        return fecha;
    }

    /**
     * Metodo para agregar o disminuir dias, meses o anios a Date
     *
     * @param pFecha fecha a convertir
     * @param pdias numero dias a aumentar o disminuir
     * @param pMes numero meses a aumentar o disminuir
     * @param pAnios numero anios a aumentar o disminuir
     * @return Date nuevo Date convertido segun parametro ingresados
     */
    public static Date agregarDiasMesAnioFecha(Date pFecha, int pdias,
            int pMes, int pAnios) {
        Date fechaModificada = null;
        Calendar fecha = GregorianCalendar.getInstance();
        fecha.setTime(pFecha);
        fecha.add(Calendar.DAY_OF_YEAR, pdias);
        fecha.add(Calendar.MONTH, pMes);
        fecha.add(Calendar.YEAR, pAnios);
        fechaModificada = fecha.getTime();
        return fechaModificada;
    }

    /**
     * Metodo para agregar o disminuir dias, meses o anios a Date
     *
     * @param pFecha fecha a convertir
     * @param pHoras numero horas a aumentar o disminuir
     * @param pMinutos numero minutos a aumentar o disminuir
     * @param pSegundos numero segundos a aumentar o disminuir
     * @return Date nuevo Date convertido segun parametro ingresados
     */
    public static Date cambiarHoraMinutoSegundoFecha(Date pFecha, int pHoras,
            int pMinutos, int pSegundos) {
        Calendar fecha = GregorianCalendar.getInstance();
        fecha.setTime(pFecha);
        fecha.set(Calendar.HOUR, pHoras);
        fecha.set(Calendar.MINUTE, pMinutos);
        fecha.set(Calendar.SECOND, pSegundos);
        return fecha.getTime();
    }

    /**
     * Metodo utilizado para compara que dos fechas son iguales o son el mismo
     * dia, con formto tipo date
     *
     * @param date1 Fecha 1
     * @param date2 Fecha 2 para compara con la fecha 1
     * @return true si las fechas son iguales, false caso contrario.
     */
    public boolean isSameDay(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return true;
        } else if (date1 == null || date2 == null) {
            return false;
            //throw new IllegalArgumentException("Error en las fechas, tiene datos null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    /**
     * Metodo utilizado para compara que dos fechas son iguales o son el mismo
     * dia, con formto tipo calendar
     *
     * @param cal1 calendar 1
     * @param cal2 calendar 2 para compara con el calendar 1
     * @return true si las fechas de tipo calendar son el mismo dia, false caso
     * contrario.
     */
    public boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null && cal2 == null) {
            return true;
        } else if (cal1 == null || cal2 == null) {
            return false;
//            throw new IllegalArgumentException("Error en las fechas, tiene datos null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    /**
     * Metodo utilizado para convertir Date a Calendar
     *
     * @param date Fecha a convertir
     * @return fecha en formato Calendar
     */
    public static Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

    /**
     * Metodo utilizado para setear la zona horaria a una fecha dia, con formto
     * tipo calendar
     *
     * @param fechaTransform que a setear zona horaria
     * @return nueva fecha con la zona horaria.
     * @throws java.text.ParseException
     */
    public static Date setTimeZoneDateGT(Date fechaTransform) {
        try {
            TimeZone tzGt = TimeZone.getTimeZone("America/Guatemala");
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(tzGt);
            String formattedDate = formatter.format(fechaTransform);
            Date fechaActual = formatter.parse(formattedDate);
            return fechaActual;
        } catch (ParseException ex) {
            Logger.getLogger(ManejoFechas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Metodo para obtener diferencia entre fechas en minutos
     *
     * @param fechaFinal fecha final
     * @param fechaInicio fecha inicio
     * @return cantidad de meses entre las dos fechas
     */
    public static int getDiferenciaEntreFechasEnMeses(Date fechaFinal, Date fechaInicio) {
        try {
            Calendar inicio = new GregorianCalendar();
            Calendar fin = new GregorianCalendar();
            inicio.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(dateToString(fechaInicio, "dd/MM/yyyy")));
            fin.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(dateToString(fechaFinal, "dd/MM/yyyy")));
            int difA = fin.get(Calendar.YEAR) - inicio.get(Calendar.YEAR);
            int difM = difA * 12 + fin.get(Calendar.MONTH) - inicio.get(Calendar.MONTH);
            return difM;
        } catch (ParseException ex) {

        }
        return 0;
    }

    /**
     * Metodo para obtener diferencia entre fechas en minutos
     *
     * @param fechaFinal fecha final
     * @param fechaInicio fecha inicio
     * @return cantidad de meses entre las dos fechas
     */
    public static int getDiferenciaEntreFechasEnDias(Date fechaFinal, Date fechaInicio) {
        LocalDate inicio = LocalDate.parse(new SimpleDateFormat("yyyy/MM/dd").format(dateToString(fechaInicio, "yyyy/MM/aa")));
        LocalDate fin = LocalDate.parse(new SimpleDateFormat("yyyy/MM/dd").format(dateToString(fechaInicio, "yyyy/MM/aa")));

        return (int) ChronoUnit.DAYS.between(inicio, fin);
    }
}
