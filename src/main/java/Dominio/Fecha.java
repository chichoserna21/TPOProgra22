package Dominio;

public class Fecha {
    private int dia;
    private int mes;
    private int anio;

    public Fecha(int dia, int mes, int anio) {
        if (!esFechaValida(dia, mes, anio)) {
            throw new Excepcionees.ErrorDominioInvalido("La fecha ingresada no es válida.");
        }
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    private boolean esFechaValida(int d, int m, int a) {
        if (a < 1900 || a > 2100) return false;
        if (m < 1 || m > 12) return false;
        if (d < 1) return false;
        
        int[] diasPorMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (esBisiesto(a)) {
            diasPorMes[1] = 29;
        }
        
        return d <= diasPorMes[m - 1];
    }

    private boolean esBisiesto(int a) {
        return (a % 4 == 0 && a % 100 != 0) || (a % 400 == 0);
    }

    public boolean esAnteriorA(Fecha otra) {
        if (this.anio < otra.anio) return true;
        if (this.anio > otra.anio) return false;
        if (this.mes < otra.mes) return true;
        if (this.mes > otra.mes) return false;
        return this.dia < otra.dia;
    }

    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", dia, mes, anio);
    }

    public int getDia() { return dia; }
    public int getMes() { return mes; }
    public int getAnio() { return anio; }
}
