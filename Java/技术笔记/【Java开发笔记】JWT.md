# 【Java开发笔记】JWT

## 1 基本介绍

**JWT(Json Web Token)** 是一种用于双方之间传递安全信息的简洁的、URL 安全的表述性声明规范。JWT 作为一个开放的标准，定义了一种简洁的，自包含的方法用于通信双方之间以 Json 对象的形式安全的传递信息。因为数字签名的存在，这些信息是可信的，JWT 可以使用 HMAC 算法或者是 RSA 的公私秘钥对进行签名。

- **简洁 (Compact)**: 可以通过 URL，POST 参数或者在 HTTP header 发送，因为数据量小，传输速度也很快
- **自包含 (Self-contained)**：负载中包含了所有用户所需要的信息，避免了多次查询数据库

特别适用于分布式站点的单点登入。

## 2 JWT组成

JWT 由三部分组成。

### 2.1 头部（Header）

> 头部不能存储敏感信息！通常存放声明式信息。

JWT头部分是一个 **描述** JWT 元数据的 JSON 对象，通常如下所示。

```json
{ "typ": "JWT","alg": "HS256"}
```

在头部指明了签名算法是 `HS256` 算法，我们进行 `BASE64` 编码（https://base64.us/），编码后的字符串如下：

```
eyAidHlwIjogIkpXVCIsImFsZyI6ICJIUzI1NiJ9
```

### 2.2 载荷（Payload）

> 载荷也是不能存放敏感信息的。

载荷是存放有效信息的地方，该部分信息是可以自己定义的。例如：

```json
{ "sub": "1234567890","name": "Song Jian","admin":true}
```

同样，经过 `BASE64` 编码，编码后的字符串如下：

```
eyAic3ViIjogIjEyMzQ1Njc4OTAiLCJuYW1lIjogIlNvbmcgSmlhbiIsImFkbWluIjp0cnVlfQ==
```

### 2.3 签证（signature）

签证信息由三部分组成：

``` 
签名算法（header（base64后的）.payload（base64后的）.secret）
```

这部分需要 `BASE64` 编码后的 header 和 `BASE64` 编码后的 payload 使用 `.` 连接组成的字符串，然后通过 header 中声明的加密方式对 secret 进行加密，然后构成了 JWT 的第三部分。

```
eyAidHlwIjogIkpXVCIsImFsZyI6ICJIUzI1NiJ9.eyAic3ViIjogIjEyMzQ1Njc4OTAiLCJuYW1lIjogIlNvbmcgSmlhbiIsImFkbWluIjp0cnVlfQ==.ZXlBaWMzVmlJam9nSWpFeU16UTFOamM0T1RBaUw=
```

## 3 入门

### 3.1 生成Token

引入依赖：

```xml
<!--  JWT依赖  -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
</dependency>
```

### 3.2 使用JWT生成token

```java
public class TestJWT {

    @Test
    public void testCreatToken() {
        // 声明私钥
        // 私钥是颁发 token 凭证方私有，不能泄漏！
        String secu = "SongJian";
        // 基于构建者模式，生成Token
        String token = Jwts.builder()
                .setId(UUID.randomUUID().toString()) // 设置唯一标识，一般是用户id
                .setSubject("JRZS") // 设置token主题
                .claim("name", "zhangsan")  // 通过claim自定义数据，保证是 key-value
                .claim("age", "18")
                .setIssuedAt(new Date()) // 设置票据颁发时间
                .setExpiration(new Date()) // 设置票据失效时间
                .signWith(SignatureAlgorithm.HS256, secu) // 设置加密算法 和 私钥
                .compact();
        System.out.println(token);
    }
}
```

生成：

```
eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjNDZhMTc5OS03ODQyLTQ4OWEtYjNjZS1hNGNjMWE2OTQxODAiLCJzdWIiOiJKUlpTIiwibmFtZSI6InpoYW5nc2FuIiwiYWdlIjoiMTgiLCJpYXQiOjE2NzY0NjI2MDMsImV4cCI6MTY3NjQ2MjYwM30.8krVtWzlcSZr-_r_UxVxzD9KTv8bVVEydiXliGPTv2Q
```

注意：这里的票据颁发时间不同，会导致token的生成结果不同！

### 3.3 校验令牌

```java
@Test
public void testVerify(){
    String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjNDZhMTc5OS03ODQyLTQ4OWEtYjNjZS1hNGNjMWE2OTQxODAiLCJzdWIiOiJKUlpTIiwibmFtZSI6InpoYW5nc2FuIiwiYWdlIjoiMTgiLCJpYXQiOjE2NzY0NjI2MDMsImV4cCI6MTY3NjQ2MjYwM30.8krVtWzlcSZr-_r_UxVxzD9KTv8bVVEydiXliGPTv2Q";
    Claims claims = Jwts.parser().setSigningKey("SongJian").parseClaimsJws(jwt).getBody();
    System.out.println(claims);
}
```

当我们对令牌进行任何部分 `(header , payload , signature)` 任何部分进行篡改, 都会造成令牌解析失效。

![image-20230215201148972](https://p.ipic.vip/t0jutp.png)