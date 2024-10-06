package utils;

public class ValidarCPF {

    public static boolean validarCPF(String cpf) {
        return cpf != null && cpf.matches("\\d{11}");
    }
}
