package guru.springframework.recipeproject.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class VariableUtil {

    public Byte[] boxByteArray(byte[] bytes){
        Byte[] bytesBoxed = new Byte[bytes.length];

        int i = 0;

        for(byte primByte : bytes){
            bytesBoxed[i++] = primByte;
        }

        return bytesBoxed;
    }

    public byte[] unboxByteArray(Byte[] bytes){
        byte[] bytesUnboxed = new byte[bytes.length];

        int i = 0;

        for(byte boxedByte : bytes){
            bytesUnboxed[i++] = boxedByte;
        }

        return bytesUnboxed;
    }
}
