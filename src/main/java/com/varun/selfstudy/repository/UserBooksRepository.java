package com.varun.selfstudy.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.varun.selfstudy.vo.UserBooks;
import com.varun.selfstudy.vo.UserBooksPrimaryKey;

@Repository
public interface UserBooksRepository extends CassandraRepository<UserBooks, UserBooksPrimaryKey> {

}
