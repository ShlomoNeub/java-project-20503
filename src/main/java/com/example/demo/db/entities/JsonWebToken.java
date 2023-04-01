package com.example.demo.db.entities;

import com.example.demo.db.entities.interfaces.IEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Entity
public class JsonWebToken implements Serializable, IEntity<JsonWebToken,Integer> {

    static  final long VALID_DURATION = 5*60*1000; // 5 Minutes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(name = "uid")
    private Integer uid;

    @Basic
    @Column(name = "jwt")
    private UUID jwt;

    @Basic
    @Column(name = "valid",unique = false)
    private boolean valid = true;

    @Basic
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false,
            updatable = false)
    @CreationTimestamp
    java.sql.Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "uid",referencedColumnName = "id",columnDefinition = "uid",insertable = false,
            updatable = false)
    private Users user;

    public JsonWebToken() {
        this.jwt = UUID.randomUUID();
        // if null the valid is true
        // if not null we check if now is later then timestamp

    }

    public boolean getValid() {
        boolean stillValid = timestamp == null ||
                new Timestamp(System.currentTimeMillis()).before(new Timestamp(timestamp.getTime()+VALID_DURATION));
        return valid && stillValid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public UUID getJwt() {
        return jwt;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Users getUser() {
        return user;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JsonWebToken that)) return false;
        return getId().equals(that.getId()) && getUid().equals(that.getUid()) && getJwt().equals(that.getJwt()) && getTimestamp().equals(that.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUid(), getJwt(), getTimestamp());
   }

    @Override
    public String toString() {
        return "JsonWebToken{" +
                "id=" + id +
                ", jwt=" + jwt +
                ", timestamp=" + timestamp +
                ", user=" + user +
                '}';
    }

    @Override
    public int compareTo(JsonWebToken o) {
        if (o != null) {
            try{
                return equals(o)?0:this.getId().compareTo(o.getId());
            }catch (Exception e){
                return 1;
            }
        }
        else return 1;
    }

    @Override
    public boolean isValid(JsonWebToken toValidate) {
        return true;
    }
}
