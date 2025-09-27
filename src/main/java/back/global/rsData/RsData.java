package back.global.rsData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.NonNull;

public record RsData<T>(
        @NonNull String resultCode,
        @JsonIgnore int statusCode,
        @NonNull String message,
        @NonNull T data) {
    public RsData(String resultCode, String message){this(resultCode,message,null);}
    public RsData(String resultCode, String message, T data){
        this(resultCode, Integer.parseInt(resultCode.split("-", 2)[0]), message, data);
    }
}
