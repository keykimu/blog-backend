package com.example.portfolio.mapper;

import com.example.portfolio.entity.Certificate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CertificateMapper {
    List<Certificate> findAll();
    void insert(Certificate certificate);
    void deleteAll();
}
