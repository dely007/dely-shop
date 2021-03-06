package net.dely.shop.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import net.dely.shop.model.LoginUser;

import java.util.Date;

/**
 * 实际开发需要结合redis 作为对刷新过期时间
 * @author dely
 */
@Slf4j
public class JWTUtil {


    /**
     * token 过期时间，正常是7天，方便测试我们改为70
     */
    private static final long EXPIRE = 1000 * 60 * 60 * 24 * 7 * 10;

    /**
     * 加密的秘钥
     */
    private static final String SECRET = "delyshop.net";

    /**
     * 令牌前缀
     */
    private static final String TOKEN_PREFIX = "Authorization";

    /**
     * subject
     */
    private static final String SUBJECT = "delyshop";


    /**
     * 根据用户信息，生成令牌
     *
     * @param loginUser
     * @return
     */
    public static String geneJsonWebToken(LoginUser loginUser) {

        if (loginUser == null) {
            throw new NullPointerException("loginUser对象为空");
        }

        String token = Jwts.builder().setSubject(SUBJECT)
                //payload  JWT令牌泄露解决方案-避免盗用后被恶意使用  添加ip控制
                .claim("headImg", loginUser.getHeadImg())
                .claim("id", loginUser.getId())
                .claim("name", loginUser.getName())
                .claim("mail", loginUser.getMail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();

        token = TOKEN_PREFIX + token;
        return token;
    }


    /**
     * 校验token的方法
     *
     * @param token
     * @return
     */
    public static Claims checkJWT(String token) {

        try {

            final Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();

            return claims;

        } catch (Exception e) {
            log.info("jwt token解密失败");
            return null;
        }

    }


}
