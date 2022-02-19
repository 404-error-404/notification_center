package general.notification_center.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 小乐乐
 * @date 2022/2/18 10:44
 */
@Component
public class JwtUtil {
    private static final String USER_ID_KEY = "user_id";
    private static final String USERNAME_KEY = "username";
    public static String tokenHeader = "Authorization";
    /**
     * 加解密算法选择
     */
    private static Algorithm ALGORITHM;

    /**
     * 签发jwt
     *
     * @param userId   用户id
     * @param username 用户名
     * @return 签名后的token
     */
    public static String signToken(Integer userId, String username) {
        return JWT.create()
                .withClaim(USER_ID_KEY, userId)
                .withClaim(USERNAME_KEY, username)
                .sign(ALGORITHM);
    }

    /**
     * 验证token是否合法
     *
     * @param token 待验证目标token
     * @return 合法则返回目标用户id，否则返回null
     */
    public static Integer verifyToken(String token) {
        if (null == token) {
            return null;
        }
        try {
            JWTVerifier verifier = JWT.require(ALGORITHM).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim(USER_ID_KEY).asInt();
        } catch (JWTVerificationException exception) {
            // 验证失败，无效的jwt
            return null;
        }
    }

    /**
     * 从配置文件读取参数赋值给静态变量只能使用set方式
     *
     * @param jwtSecret jwt加解密用的秘钥
     */
    @Value("${sys_params.jwt_secret_key}")
    public void setJwtSecret(String jwtSecret) {
        ALGORITHM = Algorithm.HMAC256(jwtSecret);
    }

}
