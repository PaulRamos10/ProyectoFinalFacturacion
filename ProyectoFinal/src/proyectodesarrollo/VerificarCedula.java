/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectodesarrollo;

/**
 *
 * @author Esparqui
 */
public class VerificarCedula {

    public boolean VerificarCedula(String cedula) {
        if (cedula.equals("0000000000") || cedula.equals("1111111111") ||cedula.equals("2222222222") || cedula.equals("3333333333") || cedula.equals("4444444444") || cedula.equals("5555555555") || cedula.equals("6666666666") || cedula.equals("7777777777") || cedula.equals("8888888888") || cedula.equals("9999999999")) {
            return false;
        } else {
            int total = 0;
            int tamanoLongitudCedula = 10;
            int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
            int numeroProvincias = 24;
            int tercerDigito = 6;
            if (cedula.matches("[0-9]*") && cedula.length() == tamanoLongitudCedula) {

                int provincia = Integer.parseInt(cedula.charAt(0) + "" + cedula.charAt(1));
                int digitoTres = Integer.parseInt(cedula.charAt(2) + "");

                if ((provincia > 0 && provincia <= numeroProvincias)
                        && digitoTres < tercerDigito) {
                    int digitoVerificadorRecibido = Integer.parseInt(cedula.charAt(9) + "");
                    for (int i = 0; i < coeficientes.length; i++) {
                        int valor = Integer.parseInt(coeficientes[i] + "")
                                * Integer.parseInt(cedula.charAt(i) + "");
                        total = valor >= 10 ? total + (valor - 9) : total + valor;
                    }
                    int digitoVerificadorObtenido = total >= 10
                            ? (total % 10) != 0
                                    ? 10 - (total % 10)
                                    : (total % 10) : total;

                    if (digitoVerificadorObtenido == digitoVerificadorRecibido) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        }
    }

}
