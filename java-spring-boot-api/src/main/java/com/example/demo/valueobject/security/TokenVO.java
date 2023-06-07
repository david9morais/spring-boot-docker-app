package com.example.demo.valueobject.security;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class TokenVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -8705612976740954352L;
    private String username;

    private Boolean Authenticated;

    private Date createdDate;

    private Date expirationDate;

    private String accessToken;

    private String refreshToken;

    public TokenVO() {
    }

    public TokenVO(String username, Boolean authenticated, Date createdDate, Date expirationDate, String accessToken, String refreshToken) {
        this.username = username;
        Authenticated = authenticated;
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAuthenticated() {
        return Authenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        Authenticated = authenticated;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenVO tokenVO = (TokenVO) o;
        return Objects.equals(username, tokenVO.username) && Objects.equals(Authenticated, tokenVO.Authenticated) && Objects.equals(createdDate, tokenVO.createdDate) && Objects.equals(expirationDate, tokenVO.expirationDate) && Objects.equals(accessToken, tokenVO.accessToken) && Objects.equals(refreshToken, tokenVO.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, Authenticated, createdDate, expirationDate, accessToken, refreshToken);
    }
}
