package utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class CryptoUtils {

    private static SecretKey chave;

    static {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            chave = keyGen.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar chave AES.", e);
        }
    }

    public static String criptografar(String dados) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, chave);
        byte[] bytes = cipher.doFinal(dados.getBytes());
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String descriptografar(String dadosCriptografados) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, chave);
        byte[] bytes = Base64.getDecoder().decode(dadosCriptografados);
        return new String(cipher.doFinal(bytes));
    }
}
